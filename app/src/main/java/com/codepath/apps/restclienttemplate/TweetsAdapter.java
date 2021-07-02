package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.codepath.apps.restclienttemplate.models.Tweet;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    Context context;
    List<Tweet> tweets;
    //pass in a context and list of tweets

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @NotNull
    @Override
    //inflate the layout of each row
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //bind values based on the position of the element
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //get the data
        Tweet tweet = tweets.get(position);
        //bind the tweet with the viewholder
        holder.bind(tweet);
    }

    @Override

    public int getItemCount() {
        Log.e("itemcount", String.valueOf(tweets.size()));
        return tweets.size();
    }

    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvProfileName;
        TextView tvHandleName;
        ImageView tweetMedia;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvProfileName = itemView.findViewById(R.id.tvProfileName);
            tvHandleName = itemView.findViewById(R.id.tvHandleName);
            tweetMedia = itemView.findViewById(R.id.tweetMedia);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvProfileName.setText(tweet.user.name);
            tvHandleName.setText("@" + tweet.user.screenName);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

            if(tweet.image != "null") {
                Glide.with(context).load(tweet.image).into(tweetMedia);
                tweetMedia.setVisibility(View.VISIBLE);
            }else{
                tweetMedia.setVisibility(View.GONE);
            }

        }
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }
}
