package com.example.mathieu.blablawild;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewSearchItineraryResultsListsActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView itemsListView;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_itinerary_results_lists);

        Intent i = getIntent();
        SearchRequestModel trajet = getIntent().getExtras().getParcelable(SearchItineraryActivity.TRAJET);
        this.setTitle(trajet.getmDepart() + " " + trajet.getmDestination());


        Toast.makeText(ViewSearchItineraryResultsListsActivity.this, getString(R.string.Toast2) + trajet.getmDate(),
                Toast.LENGTH_LONG).show();




        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };



        // LISTVIEW //


        mDatabase = FirebaseDatabase.getInstance().getReference("Itineraries"); // APPELLE LA BASE DE DONNEES

        TripResultAdapter mTripResultAdapter = new TripResultAdapter(mDatabase, this, R.layout.trip_item ); // APPELLE L'ADAPTER

        itemsListView = (ListView) findViewById(R.id.listviewsearchresults); //APPELLE LA LISTE .XML
        itemsListView.setAdapter(mTripResultAdapter); //FUSION LIST ET ADAPTER
    }

}