package com.alianilmelike.finalproject;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Module.PostGame;

import static android.widget.Toast.LENGTH_SHORT;

public class PlayGameActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    public double mLatitude, mLongitude;
    private GoogleMap mMap;
    private static String TAG = "PlayGameActivity";
    private DatabaseReference mDatabase;
    public double latitude=0.0, longitude=0.0;
    PostGame post;
    DatabaseReference mPostReference;
    private Marker mCustomerMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        //firebase
        createDataListener();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(post.location_lat, post.location_long);
        //LatLng sydney = new LatLng(longitude,latitude);

        //LatLng sydney = new LatLng(Double.parseDouble(la), Double.parseDouble(lo));
        //mCustomerMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void goMyLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPerm  issionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            Toast.makeText(this, ""+String.valueOf(mLastLocation.getLatitude())+"-"+String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
            //zoom to current position:
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).zoom(14).build(); //
            //gpsin algıladıgi yere gider
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    VisibleRegion vr = mMap.getProjection().getVisibleRegion();
                    double left = vr.latLngBounds.southwest.longitude;
                    double top = vr.latLngBounds.northeast.latitude;
                    double right = vr.latLngBounds.northeast.longitude;
                    double bottom = vr.latLngBounds.southwest.latitude;

                    Location middleLeftCornerLocation=new Location("middleLeftCornerLocation");
                    middleLeftCornerLocation.setLatitude( (vr.latLngBounds.northeast.latitude-vr.latLngBounds.southwest.latitude)/2+vr.latLngBounds.southwest.latitude);
                    middleLeftCornerLocation.setLongitude( vr.latLngBounds.southwest.longitude);
                    Location center=new Location("center");
                    center.setLatitude(mLastLocation.getLatitude());
                    center.setLongitude( mLastLocation.getLongitude());
                    LatLng mlocation = new LatLng(center.getLatitude(), center.getLongitude());

                    //merkezden ekranin sol orta kosesine olN distance
                    float dis = center.distanceTo(middleLeftCornerLocation);//calculate distane between middleLeftcorner and center
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mlocation));
                    mMap.addCircle(new CircleOptions().center(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).radius(dis).strokeColor(Color.rgb(0,255,255)).fillColor(Color.argb(80, 0, 255,255)));

                    //mMap.addMarker(new MarkerOptions().position(new LatLng(vr.latLngBounds.northeast.latitude,vr.latLngBounds.southwest.longitude)).title("sol üst"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(vr.latLngBounds.northeast.latitude,vr.latLngBounds.northeast.longitude)).title("sağ üst"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(vr.latLngBounds.southwest.latitude,vr.latLngBounds.northeast.longitude)).title("sağ alt"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(vr.latLngBounds.southwest.latitude,vr.latLngBounds.southwest.longitude)).title("sol alt"));
                }

                @Override
                public void onCancel() {

                }
            });

            //lokasyoa gitme butonu
            mMap.setMyLocationEnabled(true);

            //  mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }else{
            Toast.makeText(this, "Cannot take location! ", Toast.LENGTH_SHORT).show();
        }


    }
    public void createDataListener(){

        DatabaseReference mPostReference =  FirebaseDatabase.getInstance().getReference("game/"+"-KjO0twJYp5BSaXigOr9/");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                PostGame post = dataSnapshot.getValue(PostGame.class);
                latitude = post.location_latitude;
                longitude = post.location_longitude;
                Toast.makeText(getApplicationContext(), String.valueOf(post.location_latitude) + ", " + String.valueOf(post.location_longitude),Toast.LENGTH_LONG).show();
                // ...
                //draw the marker on my map again.
                if (mCustomerMarker != null) {
                    mCustomerMarker.remove();
                }
                LatLng mCustomerLatLng = new LatLng(latitude, longitude);
                MarkerOptions options = new MarkerOptions();
                mMap.addMarker(options.position(mCustomerLatLng).title(getResources().getString(R.string.app_name)));
                //options.icon(BitmapDescriptorFactory.fromResource(R.drawable.aaaa));
                mCustomerMarker = mMap.addMarker(options);
                goMyLocation();
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(mCustomerLatLng));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mPostReference.addValueEventListener(postListener);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        goMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
