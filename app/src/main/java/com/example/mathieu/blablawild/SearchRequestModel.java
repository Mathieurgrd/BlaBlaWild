package com.example.mathieu.blablawild;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mathieu on 06/03/17.
 */

public class SearchRequestModel implements Parcelable{

    private String mVilleDepart ;
    private String mVilleDestination;
    private int mDateP;

    public SearchRequestModel(String mVilleDepart, String mVilleDestination, String mDate) {

        this.mVilleDepart= mVilleDepart;
        this.mVilleDestination=mVilleDestination;
        this.mDateP= Integer.parseInt(mDate);

    }

    public SearchRequestModel(Parcel in) {
        mVilleDepart = in.readString();
        mVilleDestination = in.readString();
        mDateP = in.readInt();
    }



    public String getmVilleDepart() {
        return mVilleDepart;
    }

   public String getmVilleDestination() {
       return mVilleDestination;
   }

    public int getmDate() {
        return mDateP;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mVilleDepart);
        dest.writeString(mVilleDestination);
        dest.writeInt(mDateP);
    }

}
