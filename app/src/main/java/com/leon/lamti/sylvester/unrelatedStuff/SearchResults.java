package com.leon.lamti.sylvester.unrelatedStuff;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.leon.lamti.sylvester.SylvesterBrain;
import com.leon.lamti.sylvester.utilities.Constants;
import com.leon.lamti.sylvester.utilities.FastScroller;
import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.tweetsStuff.Recycler_View_Adapter;
import com.leon.lamti.sylvester.tweetsStuff.TweetsData;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SearchResults extends AppCompatActivity {

    ScrollView mScrollView;

    private String mUserInput;
    private int searchCounter = 0;

    SylvesterBrain sb = new SylvesterBrain();
    List<TweetsData> mTweetsData;

    ProgressDialog dialog;

    final List<TweetsData> tweetsData = new ArrayList<>();
    private SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        mUserInput = intent.getStringExtra("userText");

        doSearch();
        Firebase.setAndroidContext(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();



    }

    private class asyncThing extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... unused) {

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            //Toast.makeText(SylvesterBrain.this, "Tweet sent!", Toast.LENGTH_SHORT).show();
            //updateUIwithData();
            //Toast.makeText(SearchResults.this, "~~> " + mTweetsData, Toast.LENGTH_SHORT).show();
            Log.d("twitter", "" + mTweetsData);
            Toast.makeText(SearchResults.this, "Tweets Searched: " + searchCounter, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    private void updateUIwithData(List<TweetsData> tweetsData) {

//  ~~~~~~~~~~~~~~~ fill with tweets data ~~~~~~~~~~~~~~~

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(tweetsData, getApplication());

        if ( recyclerView != null && adapter != null ) {

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //  Animations
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setAddDuration(1000);
            itemAnimator.setRemoveDuration(1000);
            recyclerView.setItemAnimator(itemAnimator);

            // scroll faster
            FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller);
            fastScroller.setRecyclerView(recyclerView);
        }
    }

    private void updateUIwithNewItem( Recycler_View_Adapter adapter, TweetsData tweetsData ) {

        adapter.insert( adapter.getItemCount(),tweetsData);
    }

    private void doSearch() {

        dialog = ProgressDialog.show(SearchResults.this, "Searching Tweets", "Please wait...", true);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Searching Tweets");
        builder.setMessage("please wait");
        //builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();*/

        new Thread( new Runnable() {
            public void run() {

                Twitter twitter = sb.authentication();

                //Toast.makeText(SylvesterBrain.this, "before Search", Toast.LENGTH_SHORT).show();
                searchTweets(twitter);

                //new asyncThing().execute();
            }
        }).start();
    }

    private void searchTweets( Twitter twitter ) {

        try {



            Query query = new Query( mUserInput );
            // .since("2016-01-01") .geoCode(new GeoLocation(39.07421,21.82431), 50.00000, "Greece")
            query.setCount(100);
            QueryResult result;

            // date search
            if (false) {

                query.setSince("2016-01-01");
                query.setUntil("2016-05-15");
            }



            do {

                result = twitter.search(query);
                List<Status> tweets = result.getTweets();



                for (Status tweet : tweets) {

                    // geo search
                    if (true) {

                        try {
                            Log.i( "lalala", "" + tweet.getGeoLocation().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                        //searchCounter++;

                        //tweet.getUser().getProfileImageURL()
                        //tweet.getUser().getScreenName()

                        //URL url = new URL(tweet.getUser().getProfileImageURL());
                        //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        //imageView.setImageBitmap(bmp);



                    String x = "No Geo";
                    try {
                        x = tweet.getLang().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String y = "No time";
                    try {
                        //y = tweet.getUser().getTimeZone().toString();
                        y = tweet.getGeoLocation().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if ( tweet.getLang().toString().equalsIgnoreCase("el") ){
                    //if ( true ){

                        searchCounter++;

                    tweetsData.add(new TweetsData(searchCounter, tweet.getUser().getScreenName(),
                                tweet.getText(), x, x,
                                y, R.mipmap.ic_twitter_icon));
                    }


                }
                    //Log.d("twitter", " tweets: " + tweet.getText().toString());
                    //Log.d("twitter", " ==--> : " + tweetsData.get(0));

            } while ((query = result.nextQuery()) != null);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    updateUIwithData(tweetsData);
                    dialog.dismiss();

                    mSharedPref = getSharedPreferences("firebaseChildren", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPref.edit();
                    editor.putInt("an",( mSharedPref.getInt("an", 0) + 1) );
                    editor.commit();

                    String childName = "Tweet Child " + mSharedPref.getInt("an", 0);
                    storeDataToFirebase(childName, tweetsData);
                }
            });
        } catch(TwitterException te) {
            te.printStackTrace();
        }
    }

    private void storeDataToFirebase( String childName, List<TweetsData> listTweetsData ) {

        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
        firebase.child( childName ).setValue(listTweetsData);
    }

    public List<TweetsData> fill_with_tweet_data( int tweetNumber, String tweetUser, String tweetText, String tweetGeo, String tweetDate ) {

        List<TweetsData> tweetsData = new ArrayList<>();

        tweetsData.add(new TweetsData(tweetNumber, tweetUser, tweetText, "YOLO", tweetGeo, tweetDate, R.drawable.ic_action_movie));
        return tweetsData;
    }

    public void setTweetsData(  List<TweetsData> tweetsData0) {

       mTweetsData = tweetsData0;
    }

    public List<TweetsData> getTweetsData() {

        return mTweetsData;
    }

    public List<Data> fill_with_data() {

        List<Data> data = new ArrayList<>();

        data.add(new Data("Batman vs Superman", "Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman ", R.drawable.ic_action_movie));
        data.add(new Data("X-Men: Apocalypse", "X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics ", R.drawable.ic_action_movie));
        data.add(new Data("Captain America: Civil War", "A feud between Captain America and Iron Man leaves the Avengers in turmoil.  ", R.drawable.ic_action_movie));
        data.add(new Data("Kung Fu Panda 3", "After reuniting with his long-lost father, Po  must train a village of pandas", R.drawable.ic_action_movie));
        data.add(new Data("Warcraft", "Fleeing their dying home to colonize another, fearsome orc warriors invade the peaceful realm of Azeroth. ", R.drawable.ic_action_movie));
        data.add(new Data("Alice in Wonderland", "Alice in Wonderland: Through the Looking Glass ", R.drawable.ic_action_movie));

        return data;
    }

    public List<TweetsData> fill_with_tweetsData() {

        List<TweetsData> tweetsData = new ArrayList<>();

        /*tweetsData.add( new TweetsData( 1, "tim", "tims tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 2, "geo", "geos tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 3, "mot", "mots tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 4, "chr", "chrs tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 5, "pap", "paps tweet", R.drawable.ic_action_movie ) );

        tweetsData.add( new TweetsData( 6, "tim", "tims tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 7, "geo", "geos tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 8, "mot", "mots tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 9, "chr", "chrs tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 10, "pap", "paps tweet", R.drawable.ic_action_movie ) );

        tweetsData.add( new TweetsData( 11, "tim", "tims tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 12, "geo", "geos tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 13, "mot", "mots tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 14, "chr", "chrs tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 15, "pap", "paps tweet", R.drawable.ic_action_movie ) );

        tweetsData.add( new TweetsData( 16, "tim", "tims tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 17, "geo", "geos tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 18, "mot", "mots tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 19, "chr", "chrs tweet", R.drawable.ic_action_movie ) );
        tweetsData.add( new TweetsData( 20, "pap", "paps tweet", R.drawable.ic_action_movie ) );*/

        return tweetsData;
    }

}
