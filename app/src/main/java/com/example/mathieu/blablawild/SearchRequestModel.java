package com.example.mathieu.blablawild;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mathieu on 06/03/17.
 */

public class SearchRequestModel implements Parcelable{

    private String mDepart;
    private String mDestination;
    private String mDate;

    private SearchRequestModel(){

    }

    public SearchRequestModel(String depart, String destination, String date){
        this.mDepart = depart;
        this.mDestination= destination;
        this.mDate= date;
    }

    protected SearchRequestModel(Parcel in) {
        mDepart = in.readString();
        mDestination = in.readString();
        mDate = in.readString();
    }

    public static final Creator<SearchRequestModel> CREATOR = new Creator<SearchRequestModel>() {
        @Override
        public SearchRequestModel createFromParcel(Parcel in) {
            return new SearchRequestModel(in);
        }

        @Override
        public SearchRequestModel[] newArray(int size) {
            return new SearchRequestModel[size];
        }
    };

    public String getmDepart(){
        return mDepart;
    }
    public String getmDestination(){
        return mDestination;
    }
    public String getmDate(){
        return mDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDepart);
        dest.writeString(mDestination);
        dest.writeString(mDate);
    }
}

