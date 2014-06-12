package com.bennyguitar.onions_android.Fragments;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bennyguitar.onions_android.MainActivity;
import com.bennyguitar.onions_android.Session.OCSession;
import com.bennyguitar.onions_android.Objects.Onion;
import com.bennyguitar.onions_android.Adapters.OnionsListAdapter;
import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Utilities.UIHelpers;


/**
 * Created by BenG on 6/1/14.
 */
public class ShowOnionsFragment extends OnionFragment {
    // Properties
    TextView usernameTextView;
    Button logoutButton;
    Button createButton;
    ListView onionsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the View
        View V = newOnionFragment(R.layout.fragment_show_onions, inflater, container);

        // Build UI
        buildUI(V);

        // Set Adapter
        setOnionsAdapter();

        // Load Onions
        loadOnions();

        // Return the View
        return V;
    }

    // UI
    public void buildUI(View V) {
        // TextView
        usernameTextView = (TextView)V.findViewById(R.id.usernameTextView);
        if (usernameTextView != null) {
            usernameTextView.setText(OCSession.mainSession.Username);
        }

        // Buttons
        logoutButton = (Button)V.findViewById(R.id.logoutButton);
        createButton = (Button)V.findViewById(R.id.createOnionButton);
        logoutButton.setOnClickListener(didClickLogout);
        createButton.setOnClickListener(didClickCreate);
        setButtonsEnabled(false);

        // List View
        onionsListView = (ListView)V.findViewById(R.id.onionsListView);
        onionsListView.setBackgroundColor(Color.WHITE);
        onionsListView.setOnItemClickListener(selectOnionListener);
    }

    private void setButtonsEnabled(boolean enabled) {
        UIHelpers.styleOnionButton(logoutButton, enabled);
        UIHelpers.styleOnionButton(createButton, enabled);
    }

    // Load Onions
    Handler.Callback loadOnionsCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            boolean didLoadOnions = message.arg1 != 0;
            if (didLoadOnions) {
                decryptOnions();
            }
            return true;
        }
    };

    private void loadOnions() {
        if (OCSession.mainSession.Onions != null) {
            setOnionsAdapter();
        }
        else {
            OCSession.mainSession.loadOnionsFromParse(loadOnionsCallback);
        }
    }

    // Decrypt Onions
    Handler.Callback decryptOnionsCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            boolean didDecryptOnions = message.arg1 != 0;
            if (didDecryptOnions) {
                setOnionsAdapter();
                setButtonsEnabled(true);
            }
            return true;
        }
    };

    private void decryptOnions() {
        OCSession.mainSession.decryptOnions(decryptOnionsCallback);
    }

    // Button Click Handlers
    View.OnClickListener didClickLogout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Pop back to Root, clear session
            MainActivity activity = (MainActivity)getActivity();
            activity.goBack();
        }
    };

    View.OnClickListener didClickCreate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            launchToOnionDetail(null);
        }
    };

    // ListView
    ListView.OnItemClickListener selectOnionListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
        int position, long id) {
            if (OCSession.mainSession.Onions != null) {
                // Go to OnionDetail fragment
                Onion onion = (OCSession.mainSession.Onions.size() == 0) ? null : (Onion)OCSession.mainSession.Onions.get(position);
                launchToOnionDetail(onion);
            }
        }
    };

    public void setOnionsAdapter() {
        OnionsListAdapter adapter = new OnionsListAdapter(getActivity(), R.layout.cell_onion, OCSession.mainSession.Onions);
        onionsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // Launch to Onion Detail
    private void launchToOnionDetail(Onion onion) {
        OnionDetailFragment fragment = OnionDetailFragment.fragmentWithOnion(onion);
        MainActivity activity = (MainActivity)getActivity();
        activity.animateToFragment(fragment, "Detail");
    }
}