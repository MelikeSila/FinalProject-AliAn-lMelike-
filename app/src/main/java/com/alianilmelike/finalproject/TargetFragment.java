package com.alianilmelike.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import Module.TargetModel;

/**
 * Created by mafy on 09.05.2017.
 */

public class TargetFragment extends Fragment{

    //lokasyonu burda alıcaz adamın ama adamın olacak adam olacak bi de lokasyon alınan
    private TargetModel targetModel;
    public static final String KEY_TARGET = "KeyTarget";
    public ImageView targetPhotoImageView;
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(targetModel != null){

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = Math.round(getResources().getDimension(R.dimen.photo_height) * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

            Picasso.with(getContext()).load(targetModel.photoUrl).resize(width, height).centerInside().into(targetPhotoImageView);
        }
    }
}
