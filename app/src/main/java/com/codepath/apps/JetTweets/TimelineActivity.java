package com.codepath.apps.JetTweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.JetTweets.models.Tweet;
import com.codepath.apps.JetTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private TwitterClient client;

    User authUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find the listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        //create the arraylist
        tweets = new ArrayList<>();
        //create the adapter
        aTweets = new TweetsArrayAdapter(this, tweets);
        //connect adapter to thr listview
        lvTweets.setAdapter(aTweets);

        //get the clent
        client = TwitterApplication.getRestClient();
        populateTimeline();

        fetchAuthUser();
    }

    public void customLoadMoreDataFromApi(int offset) {
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        populateTimeline();
    }

    public void fetchAuthUser() {
        client.getAuthUser(new JsonHttpResponseHandler() {
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


    //send API request to get timeline
    private void populateTimeline() {
        client.getHomeTimmeline(new JsonHttpResponseHandler(){
             //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                aTweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();
                Log.d("DEBUG", response.toString());

            }

            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
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
            /*case R.id.miProfile:
                showProfileView();
                return true;
                */
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


}
