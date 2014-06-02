package com.bennyguitar.onions_android;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
        }
    };

    // Callback
    private Handler.Callback registerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            boolean didRegister = message.arg1 != 0;
            if (didRegister) {
                navigateToShowOnions();
            }

            // Reset UI
            setUIForRegister(false);

            // We're done here.
            return true;
        }
    };

    // Navigate
    private void navigateToShowOnions() {
        usernameTextField.setText("");
        passwordTextField.setText("");
        ShowOnionsFragment showOnionsFragment = new ShowOnionsFragment();
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.beginTransaction().replace(getId(), showOnionsFragment).addToBackStack("Register").commit();
    }
}
