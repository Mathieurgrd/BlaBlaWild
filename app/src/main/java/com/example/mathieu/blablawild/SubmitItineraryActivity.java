package com.example.mathieu.blablawild;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SubmitItineraryActivity extends AppCompatActivity {


    FirebaseDatabase itineraryDatabase;
    DatabaseReference refItinerary;
    EditText dDate, dDepart, aArrivée, pPrice;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String TAG = "EmailPassword";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_itinerary);


        dDepart = (EditText) findViewById(R.id.editTextDeparture);
        aArrivée = (EditText) findViewById(R.id.editTextDestination);
        dDate = (EditText) findViewById(R.id.editTextDate);
        pPrice = (EditText) findViewById(R.id.editTextPrice);
        Button buttonYolo = (Button) findViewById(R.id.buttonValider);

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

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            private void updateLabel() {

                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dDate.setText(sdf.format(myCalendar.getTime()));
            }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SubmitItineraryActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        buttonYolo.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                if (dDepart.length() != 0 && aArrivée.length() != 0) {

                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Itineraries");
                    FirebaseAuth.getInstance().getCurrentUser().getUid();

                    String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                    String sDépart = dDepart.getText().toString();
                    String sArrivée = aArrivée.getText().toString();
                    String sDate = dDate.getText().toString();
                    String sPrice = pPrice.getText().toString();

                    itineraryDatabase = FirebaseDatabase.getInstance(); //APPELLE LA BASE DE DONNEES
                    refItinerary = itineraryDatabase.getReference("Itineraries");

                    ItineraryModel iItineraire = new ItineraryModel(UserId, sDate, sPrice, sDépart, sArrivée);
                    myRef.push().setValue(iItineraire);

                    finish();
                } else {
                    Toast.makeText(com.example.mathieu.blablawild.SubmitItineraryActivity.this, getString(R.string.Toast), Toast.LENGTH_SHORT).show();

                }


            }


        });
    }
}
