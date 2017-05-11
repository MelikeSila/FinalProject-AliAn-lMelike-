package com.alianilmelike.finalproject;

import android.content.Context;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import Module.TargetModel;
import Module.UserModel;

/**
 * Created by mafy on 09.05.2017.
 */

public class TargetFragment extends Fragment implements View.OnClickListener, LocationListener {

    //lokasyonu burda alıcaz adamın ama adamın olacak adam olacak bi de lokasyon alınan
    private TargetModel targetModel;
    private UserModel userModel;
    public static final String KEY_TARGET = "KeyTarget";
    public ImageView targetPhotoImageView;
    public Button takeTargetPhoto, setTargetLocation, uploadTarget;
    public static final int SET_TARGET_LOCATION_REQUEST_CODE=8888;
    private double targetLatitude, targetLongitude;
    private double userLatitude, userLongitude;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    public static TargetFragment newInstance(TargetModel targetModel){
        TargetFragment targetFragment = new TargetFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_TARGET, Parcels.wrap(targetModel));
        targetFragment.setArguments(bundle);
        return targetFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            targetModel = Parcels.unwrap( getArguments().getParcelable(KEY_TARGET));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
            R.layout.fragment_target, container, false);
            targetPhotoImageView = (ImageView) rootView.findViewById(R.id.targetPhoto);



        LocationManager lm= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, (android.location.LocationListener) this);

            takeTargetPhoto = (Button) rootView.findViewById(R.id.takeTargetPhotoButton);
            setTargetLocation = (Button) rootView.findViewById(R.id.setTargetLocationButton);
            uploadTarget = (Button) rootView.findViewById(R.id.uploadTargetButton);
            takeTargetPhoto.setOnClickListener(this);
            setTargetLocation.setOnClickListener(this);
            uploadTarget.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(targetModel != null){
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = Math.round(getResources().getDimension(R.dimen.photo_height) * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
            targetLatitude = targetModel.targetLatitude;
            targetLongitude = targetModel.targetLongitude;
            Picasso.with(getContext()).load(targetModel.photoUrl).resize(width, height).centerInside().into(targetPhotoImageView);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setTargetLocationButton:
                SetTargetLocation();
                if(userModel != null){
                    String userId = userModel.userId;
                    userLatitude = userModel.userLatitude;
                    userLatitude = userModel.userLongitude;
                    Toast.makeText(getContext(), String.valueOf(userLatitude) + "****", Toast.LENGTH_SHORT).show();
                }
                LocationManager mgr = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                //mgr.getLastKnownLocation("mm");
                Toast.makeText(getContext(), "Target Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.takeTargetPhotoButton:
                //TakeTargetPhoto();
                Toast.makeText(getContext(), "Target Photo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uploadTargetButton:
                //UploadTArgetButton();
                Toast.makeText(getContext(), "Target Upload", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void SetTargetLocation(){

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
            Toast.makeText(getActivity().getApplicationContext(), "Location Cannot Token", Toast.LENGTH_SHORT).show();
        }
        else {
            //float nCurrentSpeed=location.getSpeed();
            userModel.userId="adasdasd";
            userModel.userLatitude = location.getLatitude();
            userModel.userLongitude = location.getLongitude();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
