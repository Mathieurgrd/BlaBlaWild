package com.example.mathieu.blablawild;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private TextView NameTextView;
    private TextView EmailtextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        findViewById(R.id.buttonSignOut).setOnClickListener(this);
        NameTextView = (TextView) findViewById(R.id.textViewDisplayName);
        EmailtextView = (TextView) findViewById(R.id.textViewDisplayEmail);
        ImageView imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        findViewById(R.id.button2Main).setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();

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
                    startActivity(new Intent(AccountActivity.this, SignInActivity.class));
                }
                // ...

            }
        };

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            //Bitmap ProfilePhoto = BitmapFactory.decodeResource(getResources(), R.drawable.index);


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            EmailtextView.setText(email);
            NameTextView.setText(name);
            //imageViewProfile.setImageBitmap(ProfilePhoto);
           // DontMindAboutMe gifImageView = (DontMindAboutMe) findViewById(R.id.GifImageView);
            //gifImageView.setGifImageResource(R.drawable.gifImage);


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }



    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void signOut() {
        mAuth.signOut();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSignOut) {
            signOut();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        } else if (i == R.id.button2Main){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
