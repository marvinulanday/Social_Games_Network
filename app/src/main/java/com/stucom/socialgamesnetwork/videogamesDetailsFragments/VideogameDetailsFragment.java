package com.stucom.socialgamesnetwork.videogamesDetailsFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.stucom.socialgamesnetwork.CustomPageAdapter.VideogameDetailPageAdapter;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.DAO.SgnDAO;
import com.stucom.socialgamesnetwork.R;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.callbacks.SgnCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.List;

public class VideogameDetailsFragment extends Fragment {

    private int videogameId;
    private Videogame videogame;

    private IgdbDAO dao;
    private SgnDAO sgnDAO;

    private IgdbCallback callback;
    private SgnCallback sgnCallback;
    private ImageView ivVideogameImage;
    private ToggleButton tbtnFavorite;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VideogameDetailPageAdapter pageAdapter;
    private TabItem tabInfo;
    private TabItem tabOpinion;
    private TextView tvRating;
    private ProgressBar pbRating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videogame_details, null);

        Bundle bundle = getArguments();
        videogameId = bundle.getInt("videogame");
        tbtnFavorite = view.findViewById(R.id.tbtnFavorite);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabInfo = view.findViewById(R.id.tabInformation);
        tabOpinion = view.findViewById(R.id.tabOpinion);
        viewPager = view.findViewById(R.id.viewPager);
        tvRating = view.findViewById(R.id.tvScore);
        pbRating = view.findViewById(R.id.pbVideogameRating);

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

        dao = new IgdbDAO();
        sgnDAO = new SgnDAO();

        sgnDAO.addHistory(getContext(), videogameId);

        callback = new IgdbCallback() {
            @Override
            public void findGenres(Context context, List<Genre> genresAPI) {

            }

            @Override
            public void findGames(Context context, List<Videogame> videogamesAPI, boolean add) {

            }

            @Override
            public void getGame(Context context, Videogame videogameAPI) {
                videogame = videogameAPI;
                pageAdapter = new VideogameDetailPageAdapter(getChildFragmentManager(), tabLayout.getTabCount(), videogame);
                viewPager.setAdapter(pageAdapter);
                int rating = Integer.valueOf((int) videogame.getRating());
                pbRating.setProgress(rating);
                if (rating == 0) {
                    tvRating.setText("N/A");
                } else {
                    tvRating.setText(String.valueOf(rating));
                }
                pbRating.setProgress(rating);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(videogame.getName());
                if (videogame.getCover() == null) {
                    ivVideogameImage.setImageResource(R.drawable.image_not_found);
                } else {
                    String img = "https://images.igdb.com/igdb/image/upload/t_cover_small_2x/" + videogame.getCover().getImageId() + ".jpg";
                    Picasso.get().load(img).into(ivVideogameImage);
                }
            }

            @Override
            public void getFavouriteGame(Context context, Videogame videogame, TextView txtViewTitle, ImageView imgView, ProgressBar pbRating, TextView tvRating, TextView tvGenres) {

            }
        };
        sgnCallback = new SgnCallback() {
            @Override
            public void isGameFavourite(final Context context, boolean isFavourite) {
                if (isFavourite) {
                    tbtnFavorite.setChecked(true);
                    tbtnFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            sgnDAO.deleteFavouriteVideogame(context, sgnCallback, videogameId);
                        }
                    });
                } else {
                    tbtnFavorite.setChecked(false);
                    tbtnFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            sgnDAO.addFavouriteVideogame(context, sgnCallback, videogameId);
                        }
                    });
                }
            }

            @Override
            public void setListGames(Context context, List<Videogame> videogameList) {

            }
        };
        ivVideogameImage = view.findViewById(R.id.ivGameImage);
        // sgnDAO.deleteFavouriteVideogame(getContext(),sgnCallback,videogameId);
        sgnDAO.isVideogameFavourite(getContext(), sgnCallback, videogameId);

        dao.getGameById(getContext(), callback, videogameId);

        return view;
    }
}
