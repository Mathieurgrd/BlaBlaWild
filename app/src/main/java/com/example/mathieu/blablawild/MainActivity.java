package com.example.mathieu.blablawild;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonAccueil;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonSubmit = (Button) findViewById(R.id.button3);
        buttonAccueil = (Button)findViewById(R.id.button4);

        buttonAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Search = new Intent(MainActivity.this, SearchItineraryActivity.class);
                startActivity(Search);

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Submit = new Intent(MainActivity.this, SubmitItineraryActivity.class);
                startActivity(Submit);
            }

            })

            ;
    }






}