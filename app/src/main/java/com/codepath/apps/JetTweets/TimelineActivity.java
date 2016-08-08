package com.codepath.apps.JetTweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.JetTweets.models.Tweet;
import com.codepath.apps.JetTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import fragments.HomeTimelineFragment;
import fragments.MentionsTimelineFragment;
import fragments.TweetsListFragment;

public class TimelineActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    TwitterClient client;
    User authUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the veiw pager adapter
        ViewPager vpPage = (ViewPager) findViewById(R.id.viewpager);
        //set the view pager for the viewpagerAdapter
        vpPage.setAdapter(new TweetsPageAdapter(getSupportFragmentManager()));
        //find the sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //attach the pager
        tabStrip.setViewPager(vpPage);

      /*  lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                fetchTimelineAsync(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });*/


        // Lookup the swipe container view
      /* swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        */


       //fetchAuthUser();
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP

        //populateTimeline();
    }


     public void fetchAuthUser() {
        client.getUserInfo(new JsonHttpResponseHandler() {
            // on success
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                //Deserialize json
                //Create model and add then to the adapter
                //Load the modal data intolistview
                authUser = User.fromJSON(response);
            }

            //on fail
            public void onFail(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponce) {
                Log.d("DEBUG", errorResponce.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.makeTweet:
                onCompose();
                return true;
            case R.id.profile:
                showProfileView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCompose() {
        // handle click here
        Intent i = new Intent(this, ComposeTweetActivity.class);
        i.putExtra("authUser", authUser);
        startActivityForResult(i, 100);
    }

    public void showProfileView(){

        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }

    //return the order of the fragments
    public class TweetsPageAdapter extends FragmentPagerAdapter{

        final int PAGE_COUNT = 2;
        String TabTitles[] = {"Home","Mentions"};


        public TweetsPageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeTimelineFragment();
            }
            else if(position == 1){
                return new MentionsTimelineFragment();
            }
            else{
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TabTitles[position];
        }

        @Override
        public int getCount() {
            return TabTitles.length;
        }
    }


}
