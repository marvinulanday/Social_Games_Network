package com.stucom.socialgamesnetwork;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.stucom.socialgamesnetwork.CustomExpandableListView.ExpandableListAdapter;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.model.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    IgdbDAO dao;

    private DrawerLayout drawer;
    private ExpandableListView expListView;
    ExpandableListAdapter listAdapterExpandable;
    LinearLayout lnrLytFilterGames;
    List<String> groups;
    HashMap<String, List<String>> data;
    List<Genre> genres;
    Button searchBtn;

    IgdbCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dao = new IgdbDAO();

        callback = new IgdbCallback() {
            @Override
            public void findGenres(Context context, List<Genre> genresAPI) {

                genres = genresAPI;

                groups = new ArrayList<String>();
                data = new HashMap<String, List<String>>();

                groups.add("Genre");

                List<String> genres2 = new ArrayList<>();
                for (Genre genre : genresAPI) {
                    genres2.add(genre.getName());
                }
                data.put(groups.get(0), genres2);

                listAdapterExpandable = new ExpandableListAdapter(context, groups, data);

                expListView.setAdapter(listAdapterExpandable);
            }
        };


        drawer = findViewById(R.id.drawer_layout);
        searchBtn = findViewById(R.id.buttonId);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterGames();
            }
        });

        expListView = (ExpandableListView) findViewById(R.id.expandable_list);
        lnrLytFilterGames = findViewById(R.id.lnrLytFilterGames);


        dao.getGenres(this, callback);

        String username = getIntent().getStringExtra("username");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Abre directamente el profile fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RankingFragment()).commit();
            //navigationView.setCheckedItem(R.id.nav_ranking);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                CustomCallback customCallback = new CustomCallback() {
                    @Override
                    public void customMethod() {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RankingFragment()).commit();
                    }
                };
                Bundle bundle = new Bundle();
                bundle.putSerializable("callback", customCallback);
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                break;
            case R.id.nav_explore:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
                break;
            case R.id.nav_ranking:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RankingFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void filterGames() {
        HashMap<String, Set<Genre>> filterGames = new HashMap<>();
        HashMap<String, Set<String>> x = listAdapterExpandable.getItemsChecked();
        for (Map.Entry<String, Set<String>> entry : x.entrySet()) {
            HashSet<Genre> z = new HashSet<>();
            filterGames.put(entry.getKey(), z);
            HashSet<String> y = (HashSet<String>) entry.getValue();
            for (String genre : y) {
                for (Genre genre1 : genres) {
                    if (genre1.getName().equals(genre)) {
                        HashSet<Genre> c = (HashSet<Genre>) filterGames.get(entry.getKey());
                        c.add(genre1);
                        filterGames.put(entry.getKey(), c);
                        break;
                    }
                }
            }
        }

        Log.d("SGN", filterGames.toString());
        dao.getGamesByGenre(this, null, filterGames.get("Genre"));
    }
}
