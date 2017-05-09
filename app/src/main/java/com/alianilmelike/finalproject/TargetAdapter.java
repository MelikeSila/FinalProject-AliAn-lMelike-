package com.alianilmelike.finalproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import Module.TargetModel;

/**
 * Created by mafy on 09.05.2017.
 */

public class TargetAdapter extends FragmentStatePagerAdapter {
    List <TargetModel> targetModelList = new ArrayList<>(0);
    public TargetAdapter(FragmentManager fm, List <TargetModel> TargetList){
        super(fm);
        if(TargetList != null){
            this.targetModelList.addAll(TargetList);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return TargetFragment.newInstance(targetModelList.get(position));
    }

    @Override
    public int getCount() {
        return targetModelList.size();
    }
}
