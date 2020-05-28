package com.stucom.socialgamesnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.DAO.SharedPrefsManagement;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.profileFragments.ProfileFragment;
import com.stucom.socialgamesnetwork.videogamesDetailsFragments.VideogameDetailsFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private IgdbDAO dao;

    private DrawerLayout drawer;
    private ExpandableListView expListView;
    private FrameLayout frameLayout;
    private LinearLayout lnrLytFilterGames;
    private NavigationView navigationView;
    private TextView drawerUsername;


    private FloatingActionButton btnFilter;
    private Button searchBtn;

    private CustomCallback customCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        customCallback = new CustomCallback() {
            @Override
            public void accessFragment(int containerViewId, Fragment fragment) {
                if (fragment instanceof VideogameDetailsFragment) {
                    frameLayout.removeView(btnFilter);
                    drawer.removeView(lnrLytFilterGames);
                }
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

        View v = navigationView.getHeaderView(0);
        String username = SharedPrefsManagement.getData(this, "email");
        drawerUsername = v.findViewById(R.id.drawerUsername);
        drawerUsername.setText(username);
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
            Bundle bundle = new Bundle();
            bundle.putSerializable("callback", customCallback);
            ExploreFragment exploreFragment = new ExploreFragment();
            exploreFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exploreFragment).commit();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Bundle bundle = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                frameLayout.removeView(btnFilter);
                drawer.removeView(lnrLytFilterGames);
                bundle.putSerializable("callback", customCallback);
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_explore:
                if (frameLayout.indexOfChild(btnFilter) == -1)
                    frameLayout.addView(btnFilter);
                if (drawer.indexOfChild(lnrLytFilterGames) == -1)
                    drawer.addView(lnrLytFilterGames);
                bundle.putSerializable("callback", customCallback);
                ExploreFragment exploreFragment = new ExploreFragment();
                exploreFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exploreFragment).commit();

                break;
            case R.id.nav_history:
                frameLayout.removeView(btnFilter);
                drawer.removeView(lnrLytFilterGames);
                bundle.putSerializable("callback", customCallback);
                HistoryFragment historyFragment = new HistoryFragment();
                historyFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, historyFragment).commit();
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
            this.getFragmentManager().popBackStackImmediate();
        }
    }


    private void logOut() {
        SharedPrefsManagement.deleteData(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
