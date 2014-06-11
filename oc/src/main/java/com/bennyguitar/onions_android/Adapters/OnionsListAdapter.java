package com.bennyguitar.onions_android.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bennyguitar.onions_android.Objects.Onion;
import com.bennyguitar.onions_android.R;
import com.bennyguitar.onions_android.Utilities.UIHelpers;

import java.util.List;

/**
 * Created by BenG on 6/1/14.
 */
public class OnionsListAdapter extends ArrayAdapter {
    int layoutResourceId;
    Context context;
    List data;

    public OnionsListAdapter(Context context, int layoutResourceId, List<Onion> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    static class ViewHolder
    {
        TextView titleTextView;
    }

    @Override
    public int getCount(){
        return data!=null ? (data.size() == 0 ? 1 : data.size()) : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = (TextView)convertView.findViewById(R.id.titleTextView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        // Set Data
        String title = "";
        if (data == null) {
            title = "Loading and Decrypting Onions...";
        }
        else if (data.size() == 0) {
            title = "Create your first Onion!";
        }
        else {
            Onion onion = (Onion)data.get(position);
            title = onion.plainTextTitle;
        }

        holder.titleTextView.setText(title);
        holder.titleTextView.setBackgroundColor(UIHelpers.cellColor(position));

        return convertView;
    }

}
