package com.bennyguitar.onions_android;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BenG on 6/1/14.
 */
public class OnionsListAdapter implements ListAdapter {
    private List dataList;

    // Constructor
    public static OnionsListAdapter listAdapterWithOnions(List onions) {
        OnionsListAdapter adapter = new OnionsListAdapter();
        adapter.dataList = onions;
        return adapter;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return (dataList.size() - 1 > i) ? dataList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Set Up
        View cell = View.inflate(null, R.layout.cell_onion, viewGroup);
        TextView titleView = (TextView)cell.findViewById(R.id.titleTextView);
        Onion onion = (Onion)dataList.get(i);

        // Set Text
        titleView.setText(onion.getString("onionTitle"));

        // Return it
        return cell;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
