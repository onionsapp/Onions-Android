package com.bennyguitar.onions_android.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bennyguitar.onions_android.MainActivity;
import com.bennyguitar.onions_android.Objects.Onion;
import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Session.OCSession;
import com.bennyguitar.onions_android.Utilities.OnionDialog;
import com.bennyguitar.onions_android.Utilities.UIHelpers;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by BenG on 6/10/14.
 */
public class OnionDetailFragment extends OnionFragment {
    Button deleteButton;
    Button saveButton;
    EditText onionTitleTextField;
    EditText onionInfoTextField;
    TextView detailUsernameTextView;
    Onion currentOnion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the View
        View V = newOnionFragment(R.layout.fragment_onion_detail, inflater, container);

        // Build UI
        buildUI(V);

        // Return the View
        return V;
    }

    public static OnionDetailFragment fragmentWithOnion(Onion onion) {
        OnionDetailFragment fragment = new OnionDetailFragment();
        fragment.currentOnion = onion;
        return fragment;
    }


    // UI
    private void buildUI(View view) {
        // Buttons
        deleteButton = (Button)view.findViewById(R.id.deleteOnionButton);
        saveButton = (Button)view.findViewById(R.id.saveOnionButton);
        deleteButton.setOnClickListener(didClickDelete);
        saveButton.setOnClickListener(didClickSave);
        UIHelpers.styleClearButton(deleteButton, UIHelpers.lightPurpleColor());
        UIHelpers.styleClearButton(saveButton, UIHelpers.lightPurpleColor());
        deleteButton.setEnabled(currentOnion != null);
        deleteButton.setAlpha(currentOnion != null ? 1.0f : 0.25f);

        // TextFields
        onionTitleTextField = (EditText)view.findViewById(R.id.onionTitleEditText);
        onionTitleTextField.setBackgroundResource(R.drawable.under_border);
        onionInfoTextField = (EditText)view.findViewById(R.id.onionInfoEditText);
        onionInfoTextField.setBackgroundResource(R.drawable.under_border);
        detailUsernameTextView = (TextView)view.findViewById(R.id.detailUsernameTextView);
        detailUsernameTextView.setText(OCSession.mainSession.Username);

        // Values
        if (currentOnion != null) {
            onionTitleTextField.setText(currentOnion.plainTextTitle);
            onionInfoTextField.setText(currentOnion.plainTextInfo);
        }
    }

    private void setUIForAction(boolean working) {
        saveButton.setEnabled(!working);
        saveButton.setAlpha(working ? 0.25f : 1.0f);
        if (currentOnion != null) {
            deleteButton.setEnabled(!working);
            deleteButton.setAlpha(working ? 0.25f : 1.0f);
        }
    }


    // Button Listeners
    private View.OnClickListener didClickDelete = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            launchDeleteAlert();
        }
    };

    private View.OnClickListener didClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setUIForAction(true);
            if (currentOnion == null) {
                currentOnion = Onion.newOnion(onionTitleTextField.getText().toString(), onionInfoTextField.getText().toString());
                OCSession.mainSession.Onions.add(currentOnion);
                currentOnion.saveInBackground(saveCallback);
            }
            else {
                currentOnion.plainTextTitle = onionTitleTextField.getText().toString();
                currentOnion.plainTextInfo = onionInfoTextField.getText().toString();
                currentOnion.encryptOnion();
                currentOnion.saveInBackground(saveCallback);
            }
        }
    };


    // Save Callback
    private SaveCallback saveCallback = new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                showSuccessAndGoBack();
            }
            else {
                // Show Toast
                setUIForAction(false);
                OnionDialog.show(getActivity(), "Failed to Save!", OnionDialog.OnionDialogType.FAILURE, Toast.LENGTH_SHORT);
            }
        }
    };


    // Delete Alert
    private void launchDeleteAlert() {
        AlertDialog alert = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK)
                .setTitle("Delete Onion?!?")
                .setMessage("This action is irreversible, and this Onion will be lost forever. Would you still like to delete it?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOnion();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    private void deleteOnion() {
        if (currentOnion != null) {
            setUIForAction(true);
            currentOnion.deleteInBackground(deleteCallback);
        }
    }

    private DeleteCallback deleteCallback = new DeleteCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                OCSession.mainSession.Onions.remove(currentOnion);
                showSuccessAndGoBack();
            }
            else {
                setUIForAction(false);
                OnionDialog.show(getActivity(), "Sorry, the Onion could not be deleted. Check your internet connection and try again.", OnionDialog.OnionDialogType.FAILURE, Toast.LENGTH_SHORT);
            }
        }
    };


    // Success & Go Back
    private void showSuccessAndGoBack() {
        // Show Toast
        OnionDialog.show(getActivity(), "Success!", OnionDialog.OnionDialogType.SUCCESS, Toast.LENGTH_SHORT);

        // Go Back
        MainActivity activity = (MainActivity)getActivity();
        activity.goBack();
    }
}
