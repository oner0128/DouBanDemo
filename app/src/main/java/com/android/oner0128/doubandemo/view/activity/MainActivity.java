package com.android.oner0128.doubandemo.view.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.ViewPagerAdapter;
import com.android.oner0128.doubandemo.view.fragment.AboutDialog;
import com.android.oner0128.doubandemo.view.fragment.ComingsoonFragment;
import com.android.oner0128.doubandemo.view.fragment.InTheatersFragment;
import com.android.oner0128.doubandemo.view.fragment.Top250Fragment;
import com.android.oner0128.doubandemo.view.fragment.ZhihuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_share)
    FloatingActionButton mFloatingActionButton;
    SearchView mSearchView;
    long exitTime = 0;
    private Top250Fragment mTop250Fragment;
    private ComingsoonFragment mComingsoonFragment;
    private InTheatersFragment mInTheatersFragment;
    private ZhihuFragment mZhihuFragment;
//    private android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //set title
        getSupportActionBar().setTitle(getString(R.string.title_inTheater));
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupViewPager(mViewPager);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mTop250Fragment = Top250Fragment.getInstance();
        mComingsoonFragment = ComingsoonFragment.getInstance();
        mInTheatersFragment = InTheatersFragment.getInstance();
        mZhihuFragment = ZhihuFragment.getInstance();

        adapter.addFragment(mInTheatersFragment);
        adapter.addFragment(mTop250Fragment);
        adapter.addFragment(mComingsoonFragment);
        adapter.addFragment(mZhihuFragment);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    private MenuItem prevMenuItem;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                mBottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
//            Log.d("page", "onPageSelected: " + position);
            mBottomNavigationView.getMenu().getItem(position).setChecked(true);
            switch (position) {
                case 0:
                    getSupportActionBar().setTitle(getString(R.string.title_inTheater));
                    break;
                case 1:
                    getSupportActionBar().setTitle(getString(R.string.title_Top250));
                    break;
                case 2:
                    getSupportActionBar().setTitle(getString(R.string.title_comingsoon));
                    break;
                case 3:
                    getSupportActionBar().setTitle("zhihu");
                    break;
            }
            prevMenuItem = mBottomNavigationView.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mViewPager.setCurrentItem(item.getOrder());
            switch (item.getOrder()) {
                case 0:
                    getSupportActionBar().setTitle(getString(R.string.title_inTheater));
                    return true;
                case 1:
                    getSupportActionBar().setTitle(getString(R.string.title_Top250));
                    return true;
                case 2:
                    getSupportActionBar().setTitle(getString(R.string.title_comingsoon));
                    return true;
                case 3:
                    getSupportActionBar().setTitle("zhihu");
                    return true;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        // Assumes current activity is the searchable activity
//        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        mSearchView.setQueryHint("影片名称...");
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchMovieActivity.class);
                intent.putExtra("SearchString", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
//            DialogFragment newFragment = new AboutFragment();
//            newFragment.show(getFragmentManager(), "about");
            showDialog();
            return true;
        }
        if (id == R.id.search_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        DialogFragment newFragment = new AboutDialog();
        newFragment.show(getFragmentManager(), "about");
    }

    @Override
    public void onBackPressed() {
            if (System.currentTimeMillis() - exitTime > 2000) {
//                Snackbar.make(frameLayout, "再点一次，退出", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "再点一次，退出", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
//                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
    }
}
