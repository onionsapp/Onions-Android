package com.bennyguitar.onions_android.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Utilities.UIHelpers;

/**
 * Created by BenG on 5/31/14.
 */
public class OnionFragment extends android.support.v4.app.Fragment {
    public OnionFragment() {
    }

    // Main Constructor
    public View newOnionFragment(int fragmentId, LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(fragmentId, container, false);
        buildBackground(rootView);
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    public void buildButtonUI(Button[] buttons) {
        for (Button button : buttons) {
            UIHelpers.styleOnionButton(button, true);
        }
    }

    public void buildBackground(View view) {
        view.setBackground(UIHelpers.purpleGradient());
    }
}
