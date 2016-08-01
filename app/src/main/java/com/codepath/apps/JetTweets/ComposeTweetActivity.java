package com.codepath.apps.JetTweets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.JetTweets.models.Tweet;
import com.codepath.apps.JetTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeTweetActivity extends AppCompatActivity {
    TextView tvuserName;
    ImageView ivProfileCompose;
    EditText edtBody;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        client = TwitterApplication.getRestClient();
        setUpMyView();
    }

    private void setUpMyView() {
        User user = (User) getIntent().getSerializableExtra("authUser");
        edtBody = (EditText) findViewById(R.id.etBody);
        tvuserName = (TextView) findViewById(R.id.tvUserName);;
        ivProfileCompose = (ImageView) findViewById(R.id.ivProfileMakeTweet);

        tvuserName.setText(String.format("@%s",user.getScreenName()));
//        screenNameOfcompose.setText(String.format("@%s",user.getScreenName()));

        String myProfileImg = user.getProfileImageUrl();

        if (!TextUtils.isEmpty(myProfileImg)){
            Picasso.with(this).load(myProfileImg).into(ivProfileCompose);
        }
    }

    public void onCancel(View view) {
        finish();
    }

    public void onTweet(View view){

        String status =  edtBody.getText().toString();
        Toast.makeText(this,"new tweet" + status ,Toast.LENGTH_LONG).show();

        client.postUpdateStatus(status, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet = Tweet.fromJSON(response);
                Intent i = new Intent();
                i.putExtra("tweet" , tweet);
                setResult(RESULT_OK,i);
                finish();
            }

            // on faillure
            @Override
            public void onFailure (int statusCode , Header[] headers, Throwable throwable, JSONObject errorResponse){
                Log.d("DEBUG", errorResponse.toString());
                if(throwable.getMessage().contains("resolve host")){
                    Toast.makeText(ComposeTweetActivity.this, " u got a problemt, try to resolve it", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
