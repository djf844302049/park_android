package com.anyidc.cloudpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by linwenxiong on 2016/3/30.
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list = new ArrayList<>();
    public BaseFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        if(list != null){
            this.list.addAll(list);
        }
    }
    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
