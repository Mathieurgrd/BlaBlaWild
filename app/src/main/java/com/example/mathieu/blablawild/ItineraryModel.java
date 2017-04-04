package com.example.mathieu.blablawild;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mathieu on 16/03/17.
 */

public class ItineraryModel {

    private String mUserId;
    private FirebaseAuth mAuth;
    private String mDriverLastName;
    private String mDriverFirstName;
    private String mDepartureDate;
    private String mPrice;
    private String mDeparture;
    private String mArrival;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String TAG = "EmailPassword";

    public ItineraryModel() {
    }


    protected ItineraryModel(String UserId, String DepartureDate, String Price, String Departure, String Arrival) {

        this.mUserId = UserId;
        mDriverFirstName = "Bernard";
        mDriverLastName = "Tapis";
        this.mDepartureDate = DepartureDate;
        this.mPrice = Price;
        this.mDeparture = Departure;
        this.mArrival = Arrival;
    }


    public String getmUserId() {
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return mUserId ;
    }

    public String getmDriverLastName() {
        return mDriverLastName;
    }

    public String getmDriverFirstName() {
        return mDriverFirstName;
    }

    public String getmDepartureDate() {
        return mDepartureDate;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmDeparture() {
        return mDeparture;
    }

    public String getmArrival() {
        return mArrival;
    }

    public void setmArrival(String mArrival) {
        this.mArrival = mArrival;
    }

    public void setmDeparture(String mDeparture) {
        this.mDeparture = mDeparture;
    }

    public void setmDriverLastName(String mDriverLastName) {
        this.mDriverLastName = mDriverLastName;
    }

    public void setmDriverFirstName(String mDriverFirstName) {
        this.mDriverFirstName = mDriverFirstName;
    }

    public void setmDepartureDate(String mDepartureDate) {
        this.mDepartureDate = mDepartureDate;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public void setmUserId(String mUserId) {

        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.mUserId = mUserId;
    }
}
