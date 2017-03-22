package com.example.mathieu.blablawild;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitItineraryActivity extends AppCompatActivity {


    FirebaseDatabase itineraryDatabase;
    DatabaseReference refItinerary;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_submit_itinerary);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);



            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });


            final EditText dDepart = (EditText) findViewById(R.id.etDépart);
            final EditText aArrivée = (EditText) findViewById(R.id.etArrivée);
            final EditText dDate = (EditText) findViewById(R.id.etDate);
            final EditText pPrice = (EditText) findViewById(R.id.etPrice);
            Button buttonYolo = (Button) findViewById(R.id.buttonYolo);










            buttonYolo.setOnClickListener(new View.OnClickListener() {


                public void onClick(View v) {

                    if (dDepart.length() != 0 && aArrivée.length() != 0) {

                        // Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Itineraries");

                        String sDépart = dDepart.getText().toString();
                        String sArrivée = aArrivée.getText().toString();
                        String sDate = dDate.getText().toString();
                        String sPrice = pPrice.getText().toString();

                        itineraryDatabase = FirebaseDatabase.getInstance(); //APPELLE LA BASE DE DONNEES
                        refItinerary = itineraryDatabase.getReference("Itineraries");

                        ItineraryModel iItineraire = new ItineraryModel(sDate, sPrice, sDépart, sArrivée  );
                        myRef.push().setValue(iItineraire);

                      finish();
                    } else {
                        Toast.makeText(com.example.mathieu.blablawild.SubmitItineraryActivity.this, getString(R.string.Toast), Toast.LENGTH_SHORT).show();

                    }


                }


            });
        }
    }
