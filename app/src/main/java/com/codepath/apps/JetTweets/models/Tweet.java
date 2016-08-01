package com.codepath.apps.JetTweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SET JETRO on 7/30/2016.
 */
public class Tweet implements Serializable{
    public String getBody() {
        return body;
    }

    public long getuId() {
        return uId;
    }

    public String getCreateAt() {
        return createAt;
    }

    private String body;
    private long uId; //unique id for the tweet

    public User getUser() {
        return user;
    }

    private User user;
    private String createAt;

    //parse json to model

    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();

        //Etract value from the json
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uId = jsonObject.getLong("id");
            tweet.createAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //return tweet
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();

        //iterate the json array and create tweet
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }
}
