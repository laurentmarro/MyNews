package com.example.android.mynews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.android.mynews.Controllers.Fragments.*;

public class FragmentAdapter extends FragmentPagerAdapter {

    // Default Constructor
    public FragmentAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public int getCount() {
        return(3); // Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return TopStoriesFragment.newInstance();
            case 1: //Page number 2
                return MostPopularFragment.newInstance();
            case 2: //Page number 3
                return BusinessFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "TOP STORIES";
            case 1: //Page number 2
                return "MOST POPULAR";
            case 2: //Page number 3
                return "BUSINESS";
            default:
                return null;
        }
    }
}