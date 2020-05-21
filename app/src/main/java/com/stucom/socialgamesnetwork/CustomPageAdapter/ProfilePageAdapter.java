package com.stucom.socialgamesnetwork.CustomPageAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stucom.socialgamesnetwork.ProfileFragment;
import com.stucom.socialgamesnetwork.ProfileVideogameListFragment;

public class ProfilePageAdapter extends FragmentPagerAdapter {
    private int numTabs;

    public ProfilePageAdapter(@NonNull FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfileFragment();
            case 1:
                return new ProfileVideogameListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
