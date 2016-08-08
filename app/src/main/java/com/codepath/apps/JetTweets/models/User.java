package com.codepath.apps.JetTweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by SET JETRO on 7/30/2016.
 */
public class User implements Serializable {
    public String getName() {
        return name;
    }

    public Long getuId() {
        return uId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private String name;
    private Long uId;
    private String screenName;
    private String profileImageUrl;

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingCount;
    }

    private String tagLine;
    private int followersCount;
    private int followingCount;

    //deserialize the user from json
    public static User fromJSON(JSONObject jsonObject){
        User u = new User();
        //fill user
        try {
            u.name = jsonObject.getString("name");
            u.uId = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.tagLine = jsonObject.getString("description");
            u.followersCount = jsonObject.getInt("followers_count");
            u.followingCount = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }
}
