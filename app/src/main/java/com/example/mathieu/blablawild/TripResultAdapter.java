package com.example.mathieu.blablawild;

/**
 * Created by mathieu on 20/03/17.
 */

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.Query;


public class TripResultAdapter extends Firebaseadapter<ItineraryModel> {

    TextView ID;
    TextView firstName;
    TextView Lastname;
    TextView departureDate;
    TextView departure;
    TextView destination;
    TextView price;



    public TripResultAdapter(Query ref, Activity activity, int layout) {
        super(ref, ItineraryModel.class, layout, activity);

    }


    @Override
    protected void populateView(View view, ItineraryModel iItineraire) {

        ID = (TextView) view.findViewById(R.id.textViewID);
        departureDate = (TextView)view.findViewById(R.id.textDateDepart);
        departure = (TextView)view.findViewById(R.id.textviewdepart);
        price = (TextView)view.findViewById(R.id.textPrix);
        destination=(TextView)view.findViewById(R.id.textViewarrivée);

        departureDate.setText(String.valueOf(iItineraire.getmDepartureDate()));
        destination.setText(String.valueOf(iItineraire.getmArrival()));
        departure.setText(String.valueOf(iItineraire.getmDeparture()));
        price.setText(String.valueOf(iItineraire.getmPrice()));
        ID.setText(String.valueOf(iItineraire.getmUserId()));


    }
}




