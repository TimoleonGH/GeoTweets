package com.leon.lamti.sylvester.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.SearchActivity;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.Searches;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.TweetRealmObject;
import com.leon.lamti.sylvester.SylvesterBrain;
import com.leon.lamti.sylvester.unrelatedStuff.IntAdapter;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.SearchAdapter;
import com.leon.lamti.sylvester.tweetsStuff.TweetsData;
import com.leon.lamti.sylvester.utilities.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;


public class SearchFragment extends Fragment {

    // R e a l m
    Realm realm;
    RealmResults<Searches> realmResults;
    RealmList<TweetRealmObject> tList;

    // V i e w s
    private ProgressDialog dialog;
    private RecyclerView recyclerView;
    public static SearchAdapter searchAdapter;

    private static  String mUserInput;
    private boolean mSearch;
    private boolean done = false;
    private boolean canceled = false;
    private int searchCounter = 0;
    private int geoNumber = 0;

    /*public static String getmUserInput() {
        return mUserInput;
    }
    public void setmUserInput(String mUserInput) {
        this.mUserInput = mUserInput;
    }*/

    @Override
    public void onStart() {
        super.onStart();

        realmResults.addChangeListener(realmChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        realmResults.removeChangeListener(realmChangeListener);
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {

            //dialog.dismiss();
            Log.d("ChangeListener", " was called!");
            searchAdapter.update(tList);
            //searchAdapter.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // D e l e t e    A l l   R  e a l m    S t o r  a g e
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();

                //Intent intent = new Intent(getContext(), SearchActivity.class);
                //startActivity(intent);
            }
        });

        // RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        /*// Adapter
        adapter = new SearchAdapter(this.getActivity(), tweetsList);
        recyclerView.setAdapter(adapter);*/

        Intent intent = getActivity().getIntent();
        mUserInput = intent.getStringExtra("userText");
        mSearch = intent.getBooleanExtra("search", true);

        SharedPreferences sP = getActivity().getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);

        if( !sP.getBoolean("searchDone", false) && mSearch ) {

            searchTweets(mUserInput);

            SharedPreferences.Editor editor = getActivity().getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE).edit();
            editor.putBoolean("searchDone", true);
            editor.commit();
        }

        // Updater Adapter


        realmResults = realm.where(Searches.class).contains("nameId", mUserInput).findAllSorted("date");

        tList = new RealmList();

        for (int i=0; i<realmResults.size(); i++) {
            for (int j=0; j<realmResults.get(i).getTweetList().size(); j++) {

                tList.add(realmResults.get(i).getTweetList().get(j));
            }

        }

        searchAdapter = new SearchAdapter(this.getActivity(), tList);
        recyclerView.setAdapter(searchAdapter);

        return rootView;
    }

    private List<TweetsData> searchTweets (final String userInput){

        dialog = new ProgressDialog(this.getActivity());
        dialog.setTitle("Searching Tweets");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        //dialog.getWindow().setBackgroundDrawableResource(R.drawable.radious_dark);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                canceled = true;
                getActivity().finish();
            }
        });
        dialog.show();

        threadSearch(userInput);

        return null;
    }

    private void threadSearch(final String userInput) {

        final Thread searchT;

        searchT = new Thread( new Runnable() {
            public void run() {

                Twitter twitter = SylvesterBrain.authentication();
                geoNumber = 0;

                SharedPreferences sP = getActivity().getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);

                Query query = new Query(userInput);
                query.setCount(100);
                query.setSince(dateMethod2(1, sP.getInt("n", -1)));
                query.setUntil(dateMethod2(2, sP.getInt("n", -1)));
                QueryResult result = null;

                RealmList<TweetRealmObject> realmList = new RealmList();

                do {

                    List<Status> tweets;
                    try {
                        result = twitter.search(query);

                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }


                    if ( result != null ) {

                        tweets = result.getTweets();

                        for (final Status tweet : tweets) {

                            String date = "No date";
                            try {
                                date = tweet.getCreatedAt().toString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            String place = "No place";
                            try {
                                place = tweet.getPlace().getCountry().toString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            String geoLoc = "No geolocation";
                            try {
                                geoLoc = tweet.getGeoLocation().toString();
                                ++geoNumber;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            final String finalDate = date;
                            final String finalPlace = place;
                            final String finalGeoLoc = geoLoc;
                            final Query finalQuery = query;
                            final int finalGeoNumber = geoNumber;


                            //TweetRealmObject tweetRealmObject = realm.createObject(TweetRealmObject.class);
                            TweetRealmObject tweetRealmObject = new TweetRealmObject();
                            tweetRealmObject.setTweetNumber(searchCounter);
                            tweetRealmObject.setTweetUser(tweet.getUser().getScreenName());
                            tweetRealmObject.setTweetText(tweet.getText());
                            tweetRealmObject.setTweetPlace(finalPlace);
                            tweetRealmObject.setTweetGeo(finalGeoLoc);
                            tweetRealmObject.setTweetDate(finalDate);
                            tweetRealmObject.setImageId(6);

                            ++searchCounter;
                            realmList.add(tweetRealmObject);
                            Log.e("sf", " user: " + tweet.getUser().getScreenName().toString());
                            Log.e("sf", " added: " + realmList.size());

                            if ( realmList.size() >= 3500  || canceled ) {

                                if ( canceled ) {

                                    //getActivity().finish();
                                    //deleteObject(mUserInput);
                                }

                                done = true;
                                Log.e("sf", " added: " + realmList.size() + " ------ break!");
                            }
                        }
                    } else {

                        try {
                            Toast.makeText(getActivity(), "Twitter is not responding, try again later.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } while ( (query = result.nextQuery()) != null && !done );

                // Toast.makeText(getActivity(), "Search Done", Toast.LENGTH_SHORT).show();
                Log.i("YOLO TAG", "Toast --> Search done!");

                final Searches searches = new Searches();

                searches.setNameId( mUserInput + ".ss");
                searches.setTweetList(realmList);

                String d = dateMethod(sP.getInt("n", -1));
                searches.setDate(d);
                searches.setNum(searchCounter);
                searches.setGeoNum(geoNumber);

                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            realm.beginTransaction();

                            Searches realmSearches = realm.copyToRealm(searches);

                            realm.commitTransaction();

                            dialog.dismiss();
                            SearchActivity.update();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        searchT.start();
    }

    private void deleteObject( final String name ) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                RealmResults f = realm.where(Searches.class).contains("nameId", name).findAll();

                realm.beginTransaction();
                f.deleteAllFromRealm();
                realm.commitTransaction();
            }


        });
    }

    public String dateMethod ( int n ) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        String yesterdayAsString = dateFormat.format(calendar.getTime());

        Log.e("SearchFrag", "date-" + yesterdayAsString);
        return yesterdayAsString;
    }

    public String dateMethod2 (int x, int n) {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String yesterdayAsString;

        if ( x == 1 ) {
            calendar.add(Calendar.DATE, n - 1);
            yesterdayAsString = dateFormat.format(calendar.getTime());
            Log.e("SearchFrag", "date1 " + yesterdayAsString);
            return yesterdayAsString;

        } else {

            calendar.add(Calendar.DATE, n);
            yesterdayAsString = dateFormat.format(calendar.getTime());
            Log.e("SearchFrag", "date2 " + yesterdayAsString);
            return yesterdayAsString;
        }
    }
}
