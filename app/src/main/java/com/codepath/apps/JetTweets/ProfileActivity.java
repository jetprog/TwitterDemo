package com.codepath.apps.JetTweets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.JetTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = TwitterApplication.getRestClient();

        //get the screename from the other activity;
        String screenNanme = getIntent().getStringExtra("screen_name");

        //get the account info
        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = user.fromJSON(response);
                getSupportActionBar().setTitle(user.getScreenName());
            }
        });

        if(savedInstanceState == null){
            //create the user timeline fragment
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenNanme);
            //display the user fragmeent with this activity
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //repalce the content flContainer
            ft.replace(R.id.flContainer, userTimelineFragment);
            //changes
            ft.commit();
        }

        //populateProfileHeader(user);

    }

    /*private void populateProfileHeader(User user) {

        //Find views
        TextView tvName = (TextView) findViewById(R.id.tvUserName);
        //TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        //TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        //TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        //set views
        tvName.setText(user.getScreenName());
        //tvTagLine.setText(user.getTagLine());
        //tvFollowers.setText(user.getFollowersCount() + " Followers");
        //tvFollowing.setText(user.getFriendsCount() + "Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.makeTweet:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}