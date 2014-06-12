package com.bennyguitar.onions_android.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bennyguitar.onions_android.MainActivity;
import com.bennyguitar.onions_android.Objects.Onion;
import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Session.OCSession;
import com.bennyguitar.onions_android.Utilities.UIHelpers;

import java.util.List;

/**
 * Created by BenG on 6/12/14.
 */
public class AccountInfoFragment extends OnionFragment {
    private Button deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the View
        View V = newOnionFragment(R.layout.fragment_account_info, inflater, container);

        // Build UI
        buildUI(V);

        // Return the View
        return V;
    }

    // UI
    private void buildUI(View view) {
        // Button
        deleteButton = (Button)view.findViewById(R.id.deleteAccountButton);
        deleteButton.setOnClickListener(deleteListener);
        UIHelpers.styleOnionButton(deleteButton, true);
    }

    // Button Listener
    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDeleteDialog();
        }
    };

    // Delete
    private void showDeleteDialog() {
        AlertDialog alert = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK)
                .setTitle("Delete Account")
                .setMessage("This is non-reversible. You cannot get a refund if your account has been upgraded to Pro. I hate to see you leave.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    private void deleteAccount() {
        // Delete Account
        OCSession.mainSession.User.deleteInBackground(null);

        // Delete Onions
        for (Onion onion : (List<Onion>)OCSession.mainSession.Onions) {
            onion.deleteInBackground(null);
        }

        // Logout
        OCSession.mainSession.logout();

        // Go Home :(
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
