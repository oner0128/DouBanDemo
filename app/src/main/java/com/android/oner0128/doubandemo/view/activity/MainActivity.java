package com.android.oner0128.doubandemo.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.view.fragment.InTheatersFragment;
import com.android.oner0128.doubandemo.view.fragment.Top250Fragment;
import com.android.oner0128.doubandemo.view.fragment.Top250FragmentLinear;
import com.android.oner0128.doubandemo.util.ActivtyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //FindViewById
    @BindView(R.id.fragment_container)
    FrameLayout frameLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    long exitTime = 0;
    Fragment currentFragment;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top250");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager=getSupportFragmentManager();
        currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (currentFragment == null) {
            currentFragment = Top250FragmentLinear.newINSTANCE();
            ActivtyUtils.addFragmentToActivity(fragmentManager, currentFragment, R.id.fragment_container);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Snackbar.make(frameLayout, "再点一次，退出", Snackbar.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else
                super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Top250_grid) {
            if (currentFragment != fragmentManager.findFragmentById(R.id.fragment_top250)){
                ActivtyUtils.switchFragment(fragmentManager, Top250Fragment.newINSTANCE(), R.id.fragment_container);
            toolbar.setTitle("Top250");}
        } else if (id == R.id.nav_in_theaters) {
            if (currentFragment != fragmentManager.findFragmentById(R.id.fragment_in_theaters)){
                ActivtyUtils.switchFragment(fragmentManager, InTheatersFragment.newINSTANCE(), R.id.fragment_container);
            toolbar.setTitle("正在上映");}
        } else if (id == R.id.nav_top250_linear) {
            if (currentFragment != fragmentManager.findFragmentById(R.id.fragment_top250_linear)){
                ActivtyUtils.switchFragment(fragmentManager, Top250FragmentLinear.newINSTANCE(), R.id.fragment_container);
            toolbar.setTitle("Top250");}
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public interface loadingMore{
        void loadingStart();
        void loadingEnd();
    }
}
