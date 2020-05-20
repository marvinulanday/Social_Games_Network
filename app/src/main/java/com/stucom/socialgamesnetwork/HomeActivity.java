package com.stucom.socialgamesnetwork;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private IgdbDAO dao;

    private DrawerLayout drawer;
    private ExpandableListView expListView;
    private FrameLayout frameLayout;
    private ExpandableListAdapter listAdapterExpandable;
    private LinearLayout lnrLytFilterGames;
    private NavigationView navigationView;

    private FloatingActionButton btnFilter;
    private Button searchBtn;

    private HashMap<String, List<String>> data;
    private List<Genre> genres;
    private List<String> groups;

    private IgdbCallback callback;
    private CustomCallback customCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        customCallback = new CustomCallback() {
            @Override
            public void accessFragment(int containerViewId, Fragment fragment) {
                getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
            }
        };

        drawer = findViewById(R.id.drawer_layout);
        searchBtn = findViewById(R.id.buttonId);

        expListView = findViewById(R.id.expandable_list);
        lnrLytFilterGames = findViewById(R.id.lnrLytFilterGames);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = findViewById(R.id.fragment_container);
        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(lnrLytFilterGames);
            }
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_nav_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigationView);
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                frameLayout.removeView(btnFilter);
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


    private void logOut() {
        SharedPrefsManagement.deleteData(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
