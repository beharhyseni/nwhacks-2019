package com.example.nwhacks2019;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
<<<<<<< HEAD
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
=======
import org.json.JSONObject;
>>>>>>> 051069b633ab7ccb3d2c75a3c0a2a4d045a42bd5

import org.json.JSONObject;

public class FeedActivity extends AppCompatActivity implements ActionBar.TabListener {
    private static ViewPager viewPager;//View Pager for setting tabs
    private static ActionBar actionBar;
    private static FragmentManager fragmentManager;//fragment manager to work on fragments

    public JSONObject CallServer() {
        JSONObject results = new JSONObject();
        try {
            ScrapeTask task = new ScrapeTask();
            task.execute();
            results = task.get();  //Add this
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;//Need to get back the result

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
<<<<<<< HEAD
        init();
        setTabs();
    }


    //initialize the view pager
    private void init() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        fragmentManager = getSupportFragmentManager();

        // Setting adapter over view pager
        viewPager.setAdapter(new MyAdapter(fragmentManager));

        // Implementing view pager pagechangelistener to navigate between tabs
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {

                // Setting navigation of tabs to actionbar
                actionBar.setSelectedNavigationItem(pos);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private void setTabs() {
        // Getting actionbar
        actionBar = getSupportActionBar();

        // Setting navigation mode to actionbar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Now adding a new tab to action bar and setting title, icon and
        // implementing listener
        android.support.v7.app.ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("RecyclerView");
        // tab1.setIcon(R.drawable.ic_launcher);
        tab1.setTabListener(this);

        android.support.v7.app.ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("ListView");
        tab2.setTabListener(this);

        // Now finally adding all tabs to actionbar
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);

    }

    @Override
    public void onTabReselected(ActionBar.Tab arg0, FragmentTransaction arg1) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction arg1) {
        // Setting current position of tab to view pager
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab arg0, FragmentTransaction arg1) {


    }

    // My adapter i.e. custom adapter for displaying fragments over view pager
    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int i) {

            // Getting fragments according to selected position
            Fragment fragment = null;
            if (i == 0) {
                fragment = new LinearLayout_Fragment();
            }
            if (i == 1) {
                fragment = new ListViewLayout_Fragment();
            }

            // and finally returning fragments
            return fragment;
        }

        @Override
        public int getCount() {

            // Returning no. of counts of fragments
            return 2;
        }
=======
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        JSONObject results = CallServer();
        mAdapter = new PostsAdapter(results, this);
>>>>>>> 051069b633ab7ccb3d2c75a3c0a2a4d045a42bd5
    }

}

class ScrapeTask extends AsyncTask<Void, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject results = new JSONObject();
        try {
            Scraper scraper = new Scraper();
            results = scraper.runScraper("lysol wipes", 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}