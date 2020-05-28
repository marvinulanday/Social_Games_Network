package com.stucom.socialgamesnetwork.profileFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.stucom.socialgamesnetwork.CustomPageAdapter.ProfilePageAdapter;
import com.stucom.socialgamesnetwork.R;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;

public class ProfileFragment extends Fragment {

    private ImageView ivAvatar;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProfilePageAdapter pageAdapter;
    private TabItem tabProfileInformation;
    private TabItem tabVideogameList;

    private CustomCallback customCallback;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        ivAvatar = view.findViewById(R.id.ivAvatarProfile);
        ivAvatar.setImageResource(R.drawable.user);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabProfileInformation = view.findViewById(R.id.tabProfileInformation);
        tabVideogameList = view.findViewById(R.id.tabVideogameList);
        viewPager = view.findViewById(R.id.viewPager);

        Bundle bundle = getArguments();
        customCallback = (CustomCallback) bundle.getSerializable("callback");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        pageAdapter = new ProfilePageAdapter(getChildFragmentManager(), tabLayout.getTabCount(), customCallback);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return view;
    }
}
