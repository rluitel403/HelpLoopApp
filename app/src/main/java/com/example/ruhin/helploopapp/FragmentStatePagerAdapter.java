package com.example.ruhin.helploopapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ruhin on 5/7/2018.
 */

public class FragmentStatePagerAdapter extends FragmentPagerAdapter {
    public FragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new AssignmentFragment();
            case 1:
                return new ChatFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Assignments";
            case 1:
                return "Chats";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
