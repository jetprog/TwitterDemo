package fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;

import com.codepath.apps.JetTweets.R;
import com.codepath.apps.JetTweets.TwitterApplication;
import com.codepath.apps.JetTweets.TwitterClient;
import com.codepath.apps.JetTweets.models.Tweet;
import com.codepath.apps.JetTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;


public class HomeTimelineFragment extends TweetsListFragment{

    private TwitterClient client;
    User authUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the clent
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    //send API request to get timeline
    private void populateTimeline() {
        client.getHomeTimmeline(new JsonHttpResponseHandler(){
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

        fetchAuthUser();
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


}


