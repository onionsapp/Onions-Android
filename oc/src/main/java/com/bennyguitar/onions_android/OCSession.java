package com.bennyguitar.onions_android;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by BenG on 5/31/14.
 */
public class OCSession {
    // Properties
    public List Onions;
    public String Password;
    public String UserId;
    public String Username;

    // Singleton Instance
    public static OCSession mainSession;
    public static OCSession getInstance(Context c) {
        if (mainSession == null) {
            mainSession = new OCSession();
            mainSession.createSession(c);
        }

        return mainSession;
    }

    // Set Up
    public static void setUp(Context c) {
        if (mainSession == null) {
            mainSession = new OCSession();
            mainSession.createSession(c);
        }
    }

    // Start Parse
    public void createSession(Context c) {
        ParseObject.registerSubclass(Onion.class);
        Parse.initialize(c, ParseConstants.parseAppId(), ParseConstants.parseClientKey());
    }

    // Login
    public void loginWithUserAndPass(final String username, final String pass, final Handler.Callback callback) {
        AsyncTask bgTask = new AsyncTask() {
            // Set up
            Handler loginHandler = new Handler(callback);
            int success = 0;
            Message loginMsg = new Message();
            String u = username;
            String p = pass;

            @Override
            protected Object doInBackground(Object[] objects) {
                String stretchedUser = OCSecurity.stretchedSha256String(u, OCSecurity.defaultShaIterations);
                String stretchedPass = OCSecurity.stretchedSha256String(p + u, OCSecurity.defaultShaIterations);

                ParseUser.logInInBackground(stretchedUser, stretchedPass, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // Hooray! The user is logged in.
                            Username = username;
                            UserId = user.getObjectId();
                            Password = p;
                            success = 1;
                            onPostExecute();
                        }
                    }
                });

                return null;
            }

            protected void onPostExecute() {
                // Send it
                loginMsg.arg1 = success;
                loginHandler.sendMessage(loginMsg);
            }
        };

        // Execute It
        bgTask.execute();
    }

    // Load Onions
    public void loadOnionsFromParse(final Handler.Callback callback) {
        // Set Up
        final Handler loadHandler = new Handler(callback);

        // Must be logged in
        if (UserId == null) {
            Message msg = new Message();
            loadHandler.sendMessage(msg);
            return;
        }

        // Load the objects
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Onion");
        query.whereEqualTo("userId", UserId);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> onionList, ParseException e) {
                Message msg = new Message();
                if (e == null) {
                    Onions = onionList;
                    msg.arg1 = 1;
                }
                loadHandler.sendMessage(msg);
            }
        });
    }

    // Decrypt Onions
    public void decryptOnions(final Handler.Callback callback) {
        AsyncTask bgTask = new AsyncTask() {
            // Set Up
            Handler decryptHandler = new Handler(callback);
            Message msg = new Message();

            // Background
            @Override
            protected Object doInBackground(Object[] objects) {
                for (int i = 0; i < Onions.size(); i++) {
                    Onion onion = (Onion)Onions.get(i);
                    onion.decryptOnion();
                }

                msg.arg1 = 1;
                onPostExecute();
                return null;
            }

            // Finished
            protected void onPostExecute() {
                decryptHandler.sendMessage(msg);
            }
        };

        bgTask.execute();
    }

    // Logout
    public void logout() {
        Onions = null;
        Password = null;
        UserId = null;
        Username = null;
    }
}
