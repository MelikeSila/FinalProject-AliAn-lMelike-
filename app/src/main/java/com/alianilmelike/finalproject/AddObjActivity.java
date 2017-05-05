package com.alianilmelike.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import Module.Game;
import Module.PlayedGame;
import Module.User;

import static com.alianilmelike.finalproject.SetLocationActivity.KEY_LATITUDE;
import static com.alianilmelike.finalproject.SetLocationActivity.KEY_LONGITUDE;
import static com.alianilmelike.finalproject.SetLocationActivity.KEY_STRING;

public class AddObjActivity extends AppCompatActivity  implements View.OnClickListener{
    public static final int SET_LOCATION_REQUEST_CODE = 4444 ;
    private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 3737;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 7171;
    FirebaseUser user;
    //to authantication
    private static String TAG = "AddObjActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public double longitude, latitude;
    public List<String> photoList;
    ImageView imageView;
    Button setLocation, takeButton, uploadButton;

    private String imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_obj);

        //to authantication
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            Toast.makeText(AddObjActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

        ///////////////////////////
        imageView = (ImageView) findViewById(R.id.imageContainer);
        setLocation = (Button) findViewById(R.id.setLocation);
        takeButton = (Button) findViewById(R.id.takePhoto);
        uploadButton = (Button) findViewById(R.id.uploadPhoto);

        setLocation.setOnClickListener(this);
        takeButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setLocation:
                setLocation();
                break;
            case R.id.takePhoto:
                takePhoto();
                break;
            case R.id.uploadPhoto:
                upload();
                break;
        }
    }

    private void setLocation() {
        Intent i = new Intent(getApplicationContext(), SetLocationActivity.class);
        i.putExtra(KEY_STRING,43);
        startActivityForResult(i,SET_LOCATION_REQUEST_CODE);
    }

    private void pickPhoto() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/jpeg");
            startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File image = null;
            try {
                image = FileUtil.createImageFile();
            } catch (Exception e) {
                // Handle with Fabric, show dialog
            }
            if (image != null) {
                String authority = getApplicationContext().getPackageName() + ".provider";

                Uri savedImageUri;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    savedImageUri = Uri.fromFile(image);
                } else {
                    savedImageUri = FileProvider.getUriForFile(this, authority, image);
                }

                imagePath = image.getAbsolutePath();
                if (savedImageUri != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, savedImageUri);
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        } else {
            Toast.makeText(this, "You need to Take/Pick photo!", Toast.LENGTH_LONG).show();
        }
    }
    private void upload(){
        uploadPhoto();
        uploadUser();
        uploadGame();
        uploadPlayedGame();
    }

    private void uploadUser(){

        if (user != null) {
            // User is signed in
            new User(user.getUid(), "userName");
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
            // User is signed out
            new User("gormedi Uid'yi", "userName");
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

    }

    private void uploadGame(){

        //To Date
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int day = c.get(Calendar.DATE);

        new Game("xxx", user.getUid(), latitude, longitude, photoList, minute, hour, day);
    }

    private void uploadPlayedGame(){
        new PlayedGame("playedGameID2", "GameId", "UserId", "Score");
    }

    private void uploadPhoto() {
        //TODO upload dediği zaman setLocationActivity'den mlatitute m longtitute'ları alacağız ve boş mu diye bakacağız.
        //TODO user'in id sini alıcam nerden nasıl bilmiyorum. bos mu degil mi diye kontrol edicem.
        if (imagePath == null) {
            Toast.makeText(this, "You need to Take/Pick photo!", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Upload started...", Toast.LENGTH_LONG).show();
        Uri file = Uri.fromFile(new File(imagePath));

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = storageRef.child("/photos/" + "xxx" + "/" + file.getLastPathSegment()); //burayi oyun id ile degistirecegiz.
        //photo URL to send firebase
        photoList = new ArrayList<String>();
        photoList.add(file.getLastPathSegment());
        photoList.add("deneme");
        photoList.add("deneme");
        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                if (exception != null) {

                }
                Toast.makeText(getApplicationContext(), "Upload Failed!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagePath = null;
                Toast.makeText(getApplicationContext(), "Upload Successful!", Toast.LENGTH_LONG).show();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (downloadUrl != null) {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (data == null) {
                    return;
                }
                Uri pickedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                cursor.close();
                showImage(imagePath);
            } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (imagePath == null) {
                    return;
                }
                Toast.makeText(getApplicationContext(), "Image selected!", Toast.LENGTH_LONG).show();
                showImage(imagePath);
            }  else if(requestCode == SET_LOCATION_REQUEST_CODE){
                latitude = data.getDoubleExtra(KEY_LATITUDE, 0.0);
                longitude = data.getDoubleExtra(KEY_LONGITUDE, 0.0);

            }
        }
    }

    private void showImage(String file) {
        Uri f = Uri.fromFile(new File(file));

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = Math.round(getResources().getDimension(R.dimen.photo_height) * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

        Picasso.with(this).load(f).resize(width, height).centerInside().into(imageView);
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
}