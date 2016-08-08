package fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.JetTweets.TwitterApplication;
import com.codepath.apps.JetTweets.TwitterClient;
import com.codepath.apps.JetTweets.models.Tweet;
import com.codepath.apps.JetTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    User authUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the clent
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    // Creates a new fragment given an int and title
    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    //send API request to get timeline
    private void populateTimeline() {
        String screenName = getArguments().getString("screen_name");

        client.getUserTimmeline(screenName, new JsonHttpResponseHandler() {
            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //aTweets.clear();
                addAll(Tweet.fromJSONArray(response));
                //aTweets.notifyDataSetChanged();
            }

            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    }
}
