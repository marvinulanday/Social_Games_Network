package com.stucom.socialgamesnetwork.videogamesDetailsFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.stucom.socialgamesnetwork.CustomPageAdapter.VideogameDetailPageAdapter;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.R;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.List;

public class VideogameDetailsFragment extends Fragment {

    Videogame videogame;
    IgdbDAO dao;
    IgdbCallback callback;
    ImageView ivVideogameImage;


    TabLayout tabLayout;
    ViewPager viewPager;
    VideogameDetailPageAdapter pageAdapter;
    TabItem tabInfo;
    TabItem tabOpinion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videogame_details, null);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabInfo = view.findViewById(R.id.tabInformation);
        tabOpinion = view.findViewById(R.id.tabOpinion);
        viewPager = view.findViewById(R.id.viewPager);
        pageAdapter = new VideogameDetailPageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
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
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Bundle bundle = getArguments();
        videogame = (Videogame) bundle.getSerializable("videogame");

        dao = new IgdbDAO();
        callback = new IgdbCallback() {
            @Override
            public void findGenres(Context context, List<Genre> genresAPI) {

            }

            @Override
            public void findGames(Context context, List<Videogame> videogamesAPI) {

            }

            @Override
            public void getGame(Context context, Videogame videogameAPI) {
                videogame = videogameAPI;
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(videogame.getName());
                String img = "https://images.igdb.com/igdb/image/upload/t_cover_small_2x/" + videogame.getCover().getImageId() + ".jpg";
                Picasso.get().load(img).into(ivVideogameImage);
            }
        };

        ivVideogameImage = view.findViewById(R.id.ivGameImage);

        dao.getGameById(getContext(), callback, videogame);

        return view;
    }
}
