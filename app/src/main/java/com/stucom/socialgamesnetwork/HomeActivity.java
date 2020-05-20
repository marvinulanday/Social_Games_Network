package com.stucom.socialgamesnetwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.stucom.socialgamesnetwork.CustomExpandableListView.ExpandableListAdapter;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.DAO.SharedPrefsManagement;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private IgdbDAO dao;

    private DrawerLayout drawer;
    private ExpandableListView expListView;
    private FrameLayout frameLayout;
    private ExpandableListAdapter listAdapterExpandable;
    private LinearLayout lnrLytFilterGames;

    private FloatingActionButton btnFilter;
    private Button searchBtn;

    private HashMap<String, List<String>> data;
    private List<Genre> genres;
    private List<String> groups;

    private IgdbCallback callback;

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

            @Override
            public void findGames(Context context, List<Videogame> videogamesAPI) {

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

        frameLayout = findViewById(R.id.fragment_container);
        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                frameLayout.removeView(btnFilter);
                CustomCallback customCallback = new CustomCallback() {
                    @Override
                    public void accessFragment(int containerViewId, Fragment fragment) {
                        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
                    }
                };
                Bundle bundle = new Bundle();
                bundle.putSerializable("callback", customCallback);
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                break;
            case R.id.nav_explore:
                if (frameLayout.indexOfChild(btnFilter) == -1)
                    frameLayout.addView(btnFilter);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();

                break;
            case R.id.nav_ranking:
                if (frameLayout.indexOfChild(btnFilter) == -1)
                    frameLayout.addView(btnFilter);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RankingFragment()).commit();
                break;
            case R.id.nav_logout:
                logOut();
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

    private void logOut() {
        SharedPrefsManagement.deleteData(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
