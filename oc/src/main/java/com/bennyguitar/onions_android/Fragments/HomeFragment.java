package com.bennyguitar.onions_android.Fragments;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bennyguitar.onions_android.MainActivity;
import com.bennyguitar.onions_android.Session.OCSession;
import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Utilities.OnionDialog;
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
            navigateToAboutUs();
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
                return true;
            }

            // Reset UI
            setUIForLogin(false);

            // Show Toast
            OnionDialog.show(getActivity(), "Account/Password mismatch.\nTry again.", OnionDialog.OnionDialogType.FAILURE, Toast.LENGTH_SHORT);

            // We're done here.
            return true;
        }
    };

    private void navigateToShowOnions() {
        navigateToFragment(new ShowOnionsFragment(), "ShowOnions");
    }

    private void navigateToNewAccount() {
        navigateToFragment(new RegisterFragment(), "NewAccount");
    }

    private void navigateToAboutUs() {
        navigateToFragment(new AboutFragment(), "AboutOnions");
    }

    private void navigateToFragment(Fragment fragment, String tag) {
        usernameTextField.setText("");
        passwordTextField.setText("");
        MainActivity activity = (MainActivity)getActivity();
        activity.animateToFragment(fragment, tag);
    }

    // UI
    public void buildUI(View V) {
        // Login Button
        loginButton = (Button)V.findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(didClickLogin);
        UIHelpers.styleOnionButton(loginButton, false);

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
        usernameTextField.addTextChangedListener(textWatcher);
        passwordTextField.addTextChangedListener(textWatcher);
    }

    public void setUIForLogin(boolean loggingIn) {
        UIHelpers.styleOnionButton(loginButton, !loggingIn);
    }

    // Validation
    private boolean loginFormIsValid() {
        return usernameTextField.getText().length() > 0 && passwordTextField.getText().length() > 0;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            setUIForLogin(!loginFormIsValid());
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
}
