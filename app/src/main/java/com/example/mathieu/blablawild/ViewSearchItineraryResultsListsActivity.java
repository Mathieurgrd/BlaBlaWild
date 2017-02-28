package com.example.mathieu.blablawild;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewSearchItineraryResultsListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_itinerary_results_lists);

        Intent II = getIntent();
        String mDépart = II.getStringExtra("départ");
        String mArrivée= II.getStringExtra("arrivée");
        String mDate = II.getStringExtra("Date");
        this.setTitle(mDépart +" "+ mArrivée +" "+ mDate);


    }
    }
