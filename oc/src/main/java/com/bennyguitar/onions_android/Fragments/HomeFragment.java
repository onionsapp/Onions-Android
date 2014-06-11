package com.bennyguitar.onions_android.Fragments;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bennyguitar.onions_android.MainActivity;
import com.bennyguitar.onions_android.Session.OCSession;
import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Utilities.UIHelpers;

/**
 * Created by BenG on 5/31/14.
 */
public class HomeFragment extends OnionFragment {
    Button loginButton;
    Button aboutButton;
    Button newAccount;
    EditText usernameTextField;
    EditText passwordTextField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the View
        View V = newOnionFragment(R.layout.fragment_main, inflater, container);

        // Build UI
        buildUI(V);

        // Destroy Session
        OCSession.mainSession.logout();

        // Return the View
        return V;
    }


    // Button Events
    View.OnClickListener didClickNewAccount = new View.OnClickListener() {
        public void onClick(View v) {
            navigateToNewAccount();
        }
    };

    View.OnClickListener didClickAboutUs = new View.OnClickListener() {
        public void onClick(View v) {
            if (OCSession.mainSession.Username != null) {
                Log.d("OC", OCSession.mainSession.Username);
            }
        }
    };

    View.OnClickListener didClickLogin = new View.OnClickListener() {
        public void onClick(View v) {
            setUIForLogin(true);
            login();
        }
    };


    // Login
    public void login() {
        Log.d("OC", "Logging in...");
        OCSession.mainSession.loginWithUserAndPass(usernameTextField.getText().toString(), passwordTextField.getText().toString(), loginCallback);
    }

    private Handler.Callback loginCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            boolean didLogin = message.arg1 != 0;
            Log.d("OC", "Message Received");
            if (didLogin) {
                navigateToShowOnions();
            }

            // Reset UI
            setUIForLogin(false);

            // We're done here.
            return true;
        }
    };

    private void navigateToShowOnions() {
        usernameTextField.setText("");
        passwordTextField.setText("");
        ShowOnionsFragment showOnionsFragment = new ShowOnionsFragment();
        MainActivity activity = (MainActivity)getActivity();
        activity.animateToFragment(showOnionsFragment, "ShowOnions");
    }

    private void navigateToNewAccount() {
        usernameTextField.setText("");
        passwordTextField.setText("");
        RegisterFragment registerFragment = new RegisterFragment();
        MainActivity activity = (MainActivity)getActivity();
        activity.animateToFragment(registerFragment, "NewAccount");
    }

    // UI
    public void buildUI(View V) {
        // Login Button
        loginButton = (Button)V.findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(didClickLogin);
        Button[] buttons = {loginButton};
        buildButtonUI(buttons);

        // New Account/About Us Buttons
        newAccount = (Button)V.findViewById(R.id.newAccount);
        aboutButton = (Button)V.findViewById(R.id.aboutButton);
        UIHelpers.styleClearButton(newAccount, Color.WHITE);
        UIHelpers.styleClearButton(aboutButton, Color.WHITE);
        newAccount.setOnClickListener(didClickNewAccount);
        aboutButton.setOnClickListener(didClickAboutUs);

        // TextFields
        usernameTextField = (EditText)V.findViewById(R.id.usernameTextField);
        usernameTextField.setBackgroundResource(R.drawable.under_border);
        passwordTextField = (EditText)V.findViewById(R.id.passwordTextField);
    }

    public void setUIForLogin(boolean loggingIn) {
        UIHelpers.styleOnionButton(loginButton, !loggingIn);
    }
}
