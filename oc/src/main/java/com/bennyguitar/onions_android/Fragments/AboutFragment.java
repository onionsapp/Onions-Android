package com.bennyguitar.onions_android.Fragments;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Session.OCSession;
import com.bennyguitar.onions_android.Utilities.UIHelpers;

/**
 * Created by BenG on 6/11/14.
 */
public class AboutFragment extends OnionFragment {
    // Properties
    public TextView aboutHeader1;
    public TextView aboutHeader2;
    public TextView aboutHeader3;
    public TextView aboutHeader4;
    public TextView aboutHeader5;
    public TextView aboutBody1;
    public TextView aboutBody2;
    public TextView aboutBody3;
    public TextView aboutBody4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the View
        View V = newOnionFragment(R.layout.fragment_about, inflater, container);

        // Build UI
        buildUI(V);

        // Return the View
        return V;
    }

    // UI
    private void buildUI(View view) {
        // TextViews
        aboutHeader1 = (TextView)view.findViewById(R.id.aboutHeader1);
        aboutHeader2 = (TextView)view.findViewById(R.id.aboutHeader2);
        aboutHeader3 = (TextView)view.findViewById(R.id.aboutHeader3);
        aboutHeader4 = (TextView)view.findViewById(R.id.aboutHeader4);
        aboutHeader5 = (TextView)view.findViewById(R.id.aboutHeader5);
        aboutBody1 = (TextView)view.findViewById(R.id.aboutBody1);
        aboutBody2 = (TextView)view.findViewById(R.id.aboutBody2);
        aboutBody3 = (TextView)view.findViewById(R.id.aboutBody3);
        aboutBody4 = (TextView)view.findViewById(R.id.aboutBody4);
        TextView[] textViews = {aboutBody1, aboutBody2, aboutBody3, aboutBody4};
        for (TextView textView : textViews) {
            Linkify.addLinks(textView, Linkify.ALL);
        }
    }
}
