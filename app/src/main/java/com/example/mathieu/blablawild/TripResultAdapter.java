package com.example.mathieu.blablawild;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathieu on 06/03/17.
 */


class TripResultAdapter extends ArrayAdapter {
    private ArrayList<TripResultModel> results = new ArrayList<>();
    public TripResultAdapter(Context context, int textViewResourceId, ArrayList<TripResultModel> results) {
        super(context, textViewResourceId, results);
        this.results= results;

    }

    @Override
    public int getCount() {

        int count= results.size(); //counts the total number of elements from the arrayList.
        return count;//returns the total count to adapter
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View variableview;
        variableview = convertView ;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        variableview = inflater.inflate(R.layout.trip_item, null);
        TextView textHeure = (TextView) variableview.findViewById(R.id.textHeure);
        TextView textPrix = (TextView) variableview.findViewById(R.id.textPrix);
        TextView textDateDepart = (TextView) variableview.findViewById(R.id.textDateDepart);
        textHeure.setText(results.get(position).getHeure());
        textPrix.setText(results.get(position).getPrix());
        textDateDepart.setText(results.get(position).getDateDepart());
        return variableview;

    }



}




