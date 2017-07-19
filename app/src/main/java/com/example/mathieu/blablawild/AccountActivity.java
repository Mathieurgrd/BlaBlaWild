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
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private TextView NameTextView;
    private TextView EmailtextView;
    private ImageView imageViewUserProfile;
    private StorageReference mPhotoStorage;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);



        findViewById(R.id.buttonSignOut).setOnClickListener(this);
        NameTextView = (TextView) findViewById(R.id.textViewDisplayName);
        EmailtextView = (TextView) findViewById(R.id.textViewDisplayEmail);
        imageViewUserProfile = (ImageView) findViewById(R.id.imageViewUserProfile);
        findViewById(R.id.buttonupload).setOnClickListener(this);
        findViewById(R.id.button2Main).setOnClickListener(this);
        findViewById(R.id.buttonuploadname).setOnClickListener(this);




        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    String userEmail = user.getEmail();
                    String userName = user.getDisplayName();
                    EmailtextView.setText(userEmail);
                    NameTextView.setText(userName);


                    String userId = user.getUid();

                    mPhotoStorage = FirebaseStorage.getInstance().getReference();
                    StorageReference photoRef = mPhotoStorage.child("userPics/" + userId);




                    Glide.with(AccountActivity.this).using(new FirebaseImageLoader()).load(photoRef).asBitmap().centerCrop()
                            .into(new BitmapImageViewTarget(imageViewUserProfile) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(AccountActivity.this.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imageViewUserProfile.setImageDrawable(circularBitmapDrawable);

                                }
                            });



                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(AccountActivity.this, SignInActivity.class));
                }
                // ...

            }
        };

    }


    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//
    }


    private void uploadPhoto(Uri imageUri) {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        mPhotoStorage = FirebaseStorage.getInstance().getReference();
        final StorageReference photoRef = mPhotoStorage.child("userPics/" + userId);

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




                mPhotoStorage = FirebaseStorage.getInstance().getReference();
                StorageReference photoRef = mPhotoStorage.child("userPics/" + userId);


                imageViewUserProfile.setImageDrawable(null);

                Glide.with(AccountActivity.this).using(new FirebaseImageLoader()).load(photoRef).asBitmap().centerCrop()
                        .into(new BitmapImageViewTarget(imageViewUserProfile) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(AccountActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imageViewUserProfile.setImageDrawable(circularBitmapDrawable);

                            }
                        });
            }
        });

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
        } else if (i == R.id.button2Main) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else if(i == R.id.buttonuploadname){

            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final EditText editTextpseudo = (EditText) findViewById(R.id.editTextpseudo);
            String DisplayName = editTextpseudo.getText().toString();


            if (DisplayName.length() != 0) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(DisplayName)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                    editTextpseudo.setText(user.getDisplayName());
                                }
                            }
                        });

            }else{
                Toast.makeText(AccountActivity.this, "You must enter a pseudo", Toast.LENGTH_LONG).show();
            }




        }else if (i ==R.id.buttonupload ){ AlertDialog.Builder a1 = new AlertDialog.Builder(AccountActivity.this);


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
                    imageViewUserProfile.setImageURI(selectedImage);
                    if (selectedImage != null) {
                        Picasso.with(getApplicationContext())
                                .load(selectedImage)
                                .placeholder(R.drawable.index)
                                .resize(400, 400)
                                .centerCrop()
                                .into(imageViewUserProfile);


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
                    imageViewUserProfile.setImageURI(selectedImage);

                    if (selectedImage != null) {
                        Picasso.with(getApplicationContext())
                                .load(selectedImage)
                                .placeholder(R.drawable.index)
                                .resize(400, 400)
                                .centerCrop()
                                .into(imageViewUserProfile);


                        uploadPhoto(selectedImage);
                    }


                    // Get and resize profile image
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = AccountActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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