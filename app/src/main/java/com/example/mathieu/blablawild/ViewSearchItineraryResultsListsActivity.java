package com.example.mathieu.blablawild;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewSearchItineraryResultsListsActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_itinerary_results_lists);

        ArrayList<TripResultModel> results = new ArrayList<>();
        ListView ListV = (ListView) findViewById(R.id.listviewsearchresults);


        Intent i = getIntent();
        SearchRequestModel trajet = i.getParcelableExtra(SearchItineraryActivity.TRAJET);
        this.setTitle(trajet.getmVilleDepart() + " " + trajet.getmVilleDestination());

        Toast.makeText(ViewSearchItineraryResultsListsActivity.this, getString(R.string.Toast2) + " " + trajet.getmDate(), Toast.LENGTH_LONG).show();



            results.add(new TripResultModel("Bruce", "21/02/2017-15:30", "15"));
            results.add(new TripResultModel("Clark", ("21/02/2017-16:00"), "15"));
            results.add(new TripResultModel("Bary", ("21/02/2017-16:30"), "15"));
            results.add(new TripResultModel("Lex", ("21/02/2017-17:00"), "15"));

       TripResultAdapter mResultsAdapter = new TripResultAdapter(this, R.layout.trip_item, results);

        ListV.setAdapter(mResultsAdapter);
        mResultsAdapter.notifyDataSetChanged();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewsearchitineraryresultsactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
