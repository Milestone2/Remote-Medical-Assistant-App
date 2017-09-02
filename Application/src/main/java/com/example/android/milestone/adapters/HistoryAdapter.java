package com.example.android.milestone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.models.Contact;
import com.example.android.milestone.models.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Emmanuel Roodly on 01/09/2017.
 */

public class HistoryAdapter extends ArrayAdapter<History> {

    public HistoryAdapter(Context context, List<History> histories) {
        super(context, android.R.layout.simple_list_item_1, histories);
    }


    public static class ViewHolder{
        TextView tvH_Date;
        TextView tvH_doctor;
        TextView tvH_summary;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        History aHistory = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_history, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvH_Date = (TextView) convertView.findViewById(R.id.tvH_date);

            viewHolder.tvH_doctor = (TextView) convertView.findViewById(R.id.tvH_doctor);
            viewHolder.tvH_summary = (TextView) convertView.findViewById(R.id.tvH_summary);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //Mise en place des information dans les objets view
        viewHolder.tvH_Date.setText(aHistory.getDateH());
        viewHolder.tvH_doctor.setText(aHistory.getDoctor());
        viewHolder.tvH_summary.setText(aHistory.getSummary());

        return convertView;
    }
}
