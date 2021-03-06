package com.bennyguitar.onions_android.Fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bennyguitar.onions_android.MainActivity;
import com.bennyguitar.onions_android.Session.OCSession;
import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Utilities.OnionDialog;
import com.bennyguitar.onions_android.Utilities.UIHelpers;

/**
 * Created by BenG on 6/2/14.
 */
public class RegisterFragment extends OnionFragment {
    Button registerButton;
    EditText usernameTextField;
    EditText passwordTextField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the View
        View V = newOnionFragment(R.layout.fragment_register, inflater, container);

        // Build UI
        buildUI(V);

        // Destroy Session
        OCSession.mainSession.logout();

        // Return the View
        return V;
    }

    // UI
    private void buildUI(View view) {
        // Button
        registerButton = (Button)view.findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(didClickRegister);
        UIHelpers.styleOnionButton(registerButton, true);

        // TextFields
        usernameTextField = (EditText)view.findViewById(R.id.editTextUsername);
        usernameTextField.setBackgroundResource(R.drawable.under_border);
        passwordTextField = (EditText)view.findViewById(R.id.editTextPassword);
        usernameTextField.addTextChangedListener(textWatcher);
        passwordTextField.addTextChangedListener(textWatcher);
    }

    private void setUIForRegister(boolean isRegistering) {
        UIHelpers.styleOnionButton(registerButton, !isRegistering);
    }

    // Listener
    View.OnClickListener didClickRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Register
            setUIForRegister(true);
            registerAccount();
        }
    };

    // Register
    private Handler.Callback registerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            boolean didRegister = message.arg1 != 0;
            if (didRegister) {
                navigateToShowOnions();
                return true;
            }

            // Launch Toast
            OnionDialog.show(getActivity(), "This username already exists. Choose another.", OnionDialog.OnionDialogType.FAILURE, 500);

            // Reset UI
            setUIForRegister(false);

            // We're done here.
            return true;
        }
    };

    private void registerAccount() {
        String user = usernameTextField.getText().toString();
        String pass = passwordTextField.getText().toString();
        OCSession.mainSession.signUpWithUserAndPass(user,pass,registerCallback);
    }

    // Navigate
    private void navigateToShowOnions() {
        usernameTextField.setText("");
        passwordTextField.setText("");
        ShowOnionsFragment showOnionsFragment = new ShowOnionsFragment();
        MainActivity activity = (MainActivity)getActivity();
        activity.animateToFragment(showOnionsFragment, "ShowOnions");
    }

    // Validation
    private boolean registerFormIsValid() {
        return usernameTextField.getText().length() > 0 && passwordTextField.getText().length() > 0;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            setUIForRegister(!registerFormIsValid());
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
}
