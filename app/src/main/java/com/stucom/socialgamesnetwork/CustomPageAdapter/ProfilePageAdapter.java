package com.stucom.socialgamesnetwork.CustomPageAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.profileFragments.ProfileInformationFragment;
import com.stucom.socialgamesnetwork.profileFragments.ProfileVideogameListFragment;

public class ProfilePageAdapter extends FragmentPagerAdapter {
    private int numTabs;
    private CustomCallback customCallback;

    public ProfilePageAdapter(@NonNull FragmentManager fm, int numTabs, CustomCallback customCallback) {
        super(fm);
        this.numTabs = numTabs;
        this.customCallback = customCallback;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProfileInformationFragment profile = new ProfileInformationFragment();
                return profile;
            case 1:
                Bundle bundle = new Bundle();
                bundle.putSerializable("callback", customCallback);
                ProfileVideogameListFragment profileVideogameListFragment = new ProfileVideogameListFragment();
                profileVideogameListFragment.setArguments(bundle);
                return profileVideogameListFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
