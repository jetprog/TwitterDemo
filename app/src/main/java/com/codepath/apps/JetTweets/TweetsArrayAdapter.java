package com.codepath.apps.JetTweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.JetTweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SET JETRO on 7/30/2016.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvCreateAt = (TextView) convertView.findViewById(R.id.tvTime);

        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());

        String time = ParseRelativeDate.getAbbreviatedTimeAgo(tweet.getCreateAt());
        tvCreateAt.setText(time);

        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return convertView;

    }
}
