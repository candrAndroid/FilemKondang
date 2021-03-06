package com.vpresa.filemkondang.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vpresa.filemkondang.R;
import com.vpresa.filemkondang.view.fragment.AboutFragment;
import com.vpresa.filemkondang.view.fragment.ComingSoonFragment;
import com.vpresa.filemkondang.view.fragment.HomeFragment;
import com.vpresa.filemkondang.view.fragment.NowPlayingFragment;
import com.vpresa.filemkondang.view.fragment.PopularFragment;
import com.vpresa.filemkondang.view.fragment.SearchResultFragment;
import com.vpresa.filemkondang.view.fragment.TopRatedFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout navDrawerLayout;
    private Fragment lastFragment;
    private SearchView mainSearch;
    private boolean isSearching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Toolbar headerToolbar = (Toolbar) findViewById(R.id.header_toolbar);
        setSupportActionBar(headerToolbar);

        navDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawerLayout, headerToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(getNavigationItemListener());

        setFragment(new NowPlayingFragment(), "Now Playing");
    }

    private void setFragment(Fragment fragment, String title) {
        setFragment(fragment, title, new Bundle());
    }

    private void setFragment(Fragment fragment, String title, Bundle bundle) {
        if (lastFragment == null || !lastFragment.getClass().equals(fragment.getClass()) || isSearching) {
            lastFragment = fragment;

            fragment.setArguments(bundle);

            if (mainSearch != null) mainSearch.onActionViewCollapsed();
            isSearching = false;
            setTitle(title);

            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager.replace(R.id.main_fragment, fragment);
            fragmentManager.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuIcon = menu.findItem(R.id.action_search);

        mainSearch = (SearchView) menuIcon.getActionView();
        mainSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSearching = true;
                navDrawerLayout.closeDrawer(Gravity.START);
            }
        });

        mainSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearching = false;
                return false;
            }
        });

        mainSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                setFragment(new SearchResultFragment(), query, bundle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private NavigationView.OnNavigationItemSelectedListener getNavigationItemListener() {
        return new MainNavigationItemListener();
    }

    private class MainNavigationItemListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    setFragment(new HomeFragment(), "Home");
                    break;
                case R.id.nav_now_playing:
                    setFragment(new NowPlayingFragment(), "Now Playing");
                    break;
                case R.id.nav_popular:
                    setFragment(new PopularFragment(), "Popular");
                    break;
                case R.id.nav_top_rated:
                    setFragment(new TopRatedFragment(), "Top Rated");
                    break;
                case R.id.nav_coming_soon:
                    setFragment(new ComingSoonFragment(), "Coming Soon");
                    break;
                case R.id.nav_about:
                    setFragment(new AboutFragment(), "About");
                    break;
                default:
                    break;
            }
            navDrawerLayout.closeDrawer(Gravity.START);
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (navDrawerLayout.isDrawerOpen(Gravity.START))
            navDrawerLayout.closeDrawer(Gravity.START);
        else if (!lastFragment.getClass().equals(NowPlayingFragment.class)) {
            setFragment(new NowPlayingFragment(), "Now Playing");
        } else super.onBackPressed();
    }
}