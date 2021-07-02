package com.codepath.apps.restclienttemplate;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class composeTweetActivity extends AppCompatActivity {

    public static final String TAG = "composeTweetActivity";

    public static final int MAX_TWEET_LENGTH = 140;

    EditText etCompose;
    Button btnTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        client = TwitterApplication.getRestClient(this);
        // declare etCompose and btnTweet
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        //set a click listener on the button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get tweet content
                String tweetContent = etCompose.getText().toString();

                //check if tweet is empty
                if(tweetContent.isEmpty()){
                    Toast.makeText(composeTweetActivity.this, "Sorry, your Tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // check if tweet character size is greater than the limit, 140
                if(tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(composeTweetActivity.this, "Sorry, your Tweet is too long", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(composeTweetActivity.this, tweetContent, Toast.LENGTH_LONG).show();
                //call twitter api to publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published Tweet says: " +tweet);

                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                            // set result code and bundle data for response
                            setResult(RESULT_OK, intent);

                            // closes the activity, pass data to parent
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet" + response, throwable);
                    }
                });


            }
        });

    }
}