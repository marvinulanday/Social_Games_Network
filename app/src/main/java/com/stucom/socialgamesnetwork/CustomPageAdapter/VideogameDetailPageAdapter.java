package com.stucom.socialgamesnetwork.CustomPageAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stucom.socialgamesnetwork.model.Videogame;
import com.stucom.socialgamesnetwork.videogamesDetailsFragments.VideogameDetailsInfoFragment;
import com.stucom.socialgamesnetwork.videogamesDetailsFragments.VideogameDetailsOpinionFragment;

public class VideogameDetailPageAdapter extends FragmentPagerAdapter {
    private int numTabs;

    private Videogame videogame;

    public VideogameDetailPageAdapter(@NonNull FragmentManager fm, int numTabs, Videogame videogmae) {
        super(fm);
        this.numTabs = numTabs;
        this.videogame = videogmae;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("videogame", videogame);
        switch (position) {
            case 0:
                VideogameDetailsInfoFragment videogameDetailsInfoFragment = new VideogameDetailsInfoFragment();
                videogameDetailsInfoFragment.setArguments(bundle);
                return videogameDetailsInfoFragment;
            case 1:
                VideogameDetailsOpinionFragment videogameDetailsOpinionFragment = new VideogameDetailsOpinionFragment();
                videogameDetailsOpinionFragment.setArguments(bundle);
                return videogameDetailsOpinionFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
