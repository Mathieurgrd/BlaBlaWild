package com.example.mathieu.blablawild;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.mathieu.blablawild.R.id.Arrivée;


public class SearchItineraryActivity extends AppCompatActivity {


    public final static String TRAJET = "trajet";
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_itinerary);




        final EditText départ = (EditText) findViewById(R.id.départ);
        final EditText arrivée =(EditText) findViewById(Arrivée);
        final EditText Date =  (EditText) findViewById(R.id.Date);



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



        final Button buttonSearch = (Button) findViewById(R.id.buttonSearch);
        départ.setEnabled(true);
        arrivée.setEnabled(true);
        Date.setEnabled(true);
        buttonSearch.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                if (départ.length() != 0 && arrivée.length() != 0)
                {

                    String mDépart = départ.getText().toString();
                    String mArrivée=arrivée.getText().toString();
                    String mDate=Date.getText().toString();
                    SearchRequestModel trajet = new SearchRequestModel(mDépart, mArrivée, mDate);
                    Intent i = new Intent(SearchItineraryActivity.this, ViewSearchItineraryResultsListsActivity.class);

                    i.putExtra(SearchItineraryActivity.TRAJET, trajet );
                    startActivity(i);}

                else { Toast.makeText(SearchItineraryActivity.this, getString(R.string.Toast), Toast.LENGTH_SHORT).show();

            }






            }  } );  }  }


