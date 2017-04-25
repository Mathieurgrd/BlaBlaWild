package com.example.mathieu.blablawild;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";


    private FirebaseAuth mAuth;
    private TextView mStatusTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mPseudoField;
    private StorageReference mPhotoStorage;
    private StorageReference photoRef;
    private Button SignInBouton;
    private Button RegisterBouton;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView imageViewProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        photoRef = FirebaseStorage.getInstance().getReference();
        mPhotoStorage = FirebaseStorage.getInstance().getReference();

        findViewById(R.id.button_register).setOnClickListener(this);
        mEmailField = (EditText) findViewById(R.id.editTextMail);
        mPseudoField = (EditText) findViewById(R.id.editTextpseudo);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        mPasswordField = (EditText) findViewById(R.id.editTextPass);
        mStatusTextView = (TextView) findViewById(R.id.mStatusTextView);
        findViewById(R.id.button_SignIn).setOnClickListener(this);
        findViewById(R.id.buttonverify).setOnClickListener(this);

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            finish();
                        }

                    }
                });
        // [END create_user_with_email]
    }


    private void signOut() {
        mAuth.signOut();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//
    }


    private void uploadPhoto(Uri imageUri) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();


        StorageReference photoRef = mPhotoStorage.child("userPics/" + userId);

        photoRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();





            }
        });

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_register) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(mPseudoField.getText().toString())
                    .build();


        } else if (i == R.id.buttonverify) {
            AlertDialog.Builder a1 = new AlertDialog.Builder(SignUpActivity.this);


            // Setting Dialog Title
            a1.setTitle("Choose an option");

            // Setting Dialog Message
            a1.setMessage("Choose whether your prefer upload a photo by taking a picture with the camera , or uploading an existing from the gallery");

            a1.setPositiveButton("Take a new one",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int button1) {
                            // if this button is clicked, close
                            // current activity
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);//zero can be replaced with any action code

                            dialog.cancel();
                        }


                    });
            a1.setNeutralButton("From Gallery",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int button2) {
                            openGallery();
                            dialog.cancel();

                        }
                    });

            // Showing Alert Message
            AlertDialog alertDialog = a1.create();
            a1.show();


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfil.setImageURI(selectedImage);
                    if (selectedImage != null) {
                        Picasso.with(getApplicationContext())
                                .load(selectedImage)
                                .placeholder(R.drawable.index)
                                .resize(400, 400)
                                .centerCrop()
                                .into(imageViewProfil);


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(selectedImage)
                                .build();


                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");
                                        }
                                    }
                                });




                        uploadPhoto(selectedImage);
                    }
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfil.setImageURI(selectedImage);

                    if (selectedImage != null) {
                        Picasso.with(getApplicationContext())
                                .load(selectedImage)
                                .placeholder(R.drawable.index)
                                .resize(400, 400)
                                .centerCrop()
                                .into(imageViewProfil);


                        uploadPhoto(selectedImage);
                    }


                    // Get and resize profile image
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = SignUpActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);

                    ExifInterface exif = null;
                    try {
                        File pictureFile = new File(picturePath);
                        exif = new ExifInterface(pictureFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int orientation = ExifInterface.ORIENTATION_NORMAL;

                    if (exif != null)
                        orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            loadedBitmap = rotate(loadedBitmap, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            loadedBitmap = rotate(loadedBitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            loadedBitmap = rotate(loadedBitmap, 270);
                            break;
                    }

                }
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
