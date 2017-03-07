package com.example.mathieu.blablawild;

import android.widget.ArrayAdapter;

/**
 * Created by mathieu on 06/03/17.
 */

class TripResultModel {

    public String toString() {return this.getDateDepart() + " "+ this.getHeure() +" "+ this.getPrix();}
    private String DateDepart;
    private String Prix;
    private String Heure;

    public TripResultModel(String DateDepart, String Prix, String Heure) {

        this.DateDepart= DateDepart;
        this.Prix=Prix;
        this.Heure=Heure;
    }
    public String getDateDepart() {return DateDepart;}
    public String getPrix() {return Prix;}
    public String getHeure() {return Heure;}
}
