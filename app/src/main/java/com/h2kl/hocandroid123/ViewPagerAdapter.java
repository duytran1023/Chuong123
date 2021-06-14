package com.h2kl.hocandroid123;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new intro1();
            case 1:
                return new intro2();
            case 2:
                return new intro3();
            default:
                return null;
        }
    }

    public void add(Fragment fr){
        fragments.add(fr);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
}
