package com.stucom.socialgamesnetwork.CustomPageAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stucom.socialgamesnetwork.profileFragments.ProfileInformationFragment;
import com.stucom.socialgamesnetwork.profileFragments.ProfileVideogameListFragment;

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
                ProfileInformationFragment profile = new ProfileInformationFragment();
                return profile;
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
