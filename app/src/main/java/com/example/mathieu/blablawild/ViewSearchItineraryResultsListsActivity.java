package com.example.mathieu.blablawild;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewSearchItineraryResultsListsActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView itemsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_itinerary_results_lists);

        Intent i = getIntent();
        SearchRequestModel trajet = getIntent().getExtras().getParcelable(SearchItineraryActivity.TRAJET);
        this.setTitle(trajet.getmDepart() + " " + trajet.getmDestination());


        Toast.makeText(ViewSearchItineraryResultsListsActivity.this, getString(R.string.Toast2) + trajet.getmDate(),
                Toast.LENGTH_LONG).show();



        // LISTVIEW //


        mDatabase = FirebaseDatabase.getInstance().getReference("Itineraries"); // APPELLE LA BASE DE DONNEES

        TripResultAdapter mTripResultAdapter = new TripResultAdapter(mDatabase, this, R.layout.trip_item ); // APPELLE L'ADAPTER

        itemsListView = (ListView) findViewById(R.id.listviewsearchresults); //APPELLE LA LISTE .XML
        itemsListView.setAdapter(mTripResultAdapter); //FUSION LIST ET ADAPTER
    }

}