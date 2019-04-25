package com.nyc.schools.schools;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyc.schools.R;
import com.nyc.schools.utils.Constants;
import com.nyc.schools.utils.NavigationUtil;

import static com.nyc.schools.utils.NavigationUtil.getActivityTag;
import static com.nyc.schools.utils.NavigationUtil.setActivityTag;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class SchoolsActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView drawerNavView;
    private ActionBarDrawerToggle drawerToggle;
    private boolean drawerOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools_list);

        initializeNavigationView();

        NavigationUtil.loadListFragment(getSupportFragmentManager(), "all");
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp() {

        //Enable Up button only if there are entries in the back stack
        //boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initializeNavigationView() {

        toolbar = findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
        setSupportActionBar(toolbar);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(true);

        shouldDisplayHomeUp();

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerNavView = findViewById(R.id.drawer);
        drawerNavView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.home, R.string.home) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
                drawerOpened = false;
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
                drawerOpened = true;
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View headerLayout = drawerNavView.getHeaderView(0);
        TextView title = headerLayout.findViewById(R.id.title);
        ImageView image = headerLayout.findViewById(R.id.image);

        image.setImageResource(R.mipmap.ic_launcher);
        title.setText(getString(R.string.app_name));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String type = "all";
        if (item.getItemId() == R.id.fav) {
            type = "fav";
        } else if (item.getItemId() == R.id.sort_name) {
            type = "sort_name";
        } else if (item.getItemId() == R.id.sort_grade) {
            type = "sort_grade";
        }

        if (getActivityTag() != null && getActivityTag() == Constants.ActivityTag.SCHOOL_LIST) {
            ListFragment frag = (ListFragment) getSupportFragmentManager().findFragmentByTag(Constants.ActivityTag.SCHOOL_LIST);
            frag.updateList(type);
        }
        drawerLayout.closeDrawers();

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle != null && drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public boolean isDrawerOpened() {
        return drawerOpened;
    }

    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        drawerToggle.setDrawerIndicatorEnabled(enabled);
    }
}
