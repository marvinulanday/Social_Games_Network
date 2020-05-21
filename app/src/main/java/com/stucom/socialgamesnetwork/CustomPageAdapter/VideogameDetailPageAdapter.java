package com.stucom.socialgamesnetwork.CustomPageAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stucom.socialgamesnetwork.videogamesDetailsFragments.VideogameDetailsInfoFragment;
import com.stucom.socialgamesnetwork.videogamesDetailsFragments.VideogameDetailsOpinionFragment;

public class VideogameDetailPageAdapter extends FragmentPagerAdapter {
    private int numTabs;

    public VideogameDetailPageAdapter(@NonNull FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new VideogameDetailsInfoFragment();
            case 1:
                return new VideogameDetailsOpinionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
