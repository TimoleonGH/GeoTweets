package com.leon.lamti.sylvester;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lamti.sylvester.animationPack.RevealLayout;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.Searches;
import com.leon.lamti.sylvester.storagedSearches.StoragedSearches;
import com.leon.lamti.sylvester.utilities.OnSwipeTouchListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class SylvesterBrain extends AppCompatActivity {

    // R e a l m
    Realm realm;
    RealmResults<Searches> realmResults;

    // V i e w s
    Button mSendTweetB, mSearchTweetB;
    EditText mTweetET;
    RelativeLayout mMainRL;
    TextView daysTV;
    TextView date;
    ProgressDialog dialog;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7;
    LinearLayout daysLL;

    // Animation
    RevealLayout mRevealLayout;
    View mViewToReveal;
    View mRevealView;

    private boolean doubleBackToExitPressedOnce = false;

    private CheckBox dsBox;

    private String userText = "-";
    private String[] days = {"Search tweets one day before", "Search tweets two days before", "Search tweets three days before"
            , "Search tweets four days before", "Search tweets five days before", "Search tweets six days before"
            , "Search tweets seven days before"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }*/

        setContentView(R.layout.activity_sylvester_brain);

        //mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        //mRevealView = findViewById(R.id.reveal_view);
        //mViewToReveal = (GridView) findViewById(R.id.gridView);

        findViews();
        touchListener();

        //sendTweet();
        goToStoragedSearches();
        goToDsSearch();
        daysCheckBoxes();
    }

    private void daysCheckBoxes() {

        SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
        date.setText(dateMethod(sP.getInt("n", -1)));
        daysTV.setText(days[0]);

        if ( sP.getInt("n", -1) == -2 ) {

            cb2.setChecked(true);
            cb1.setChecked(false);
            daysTV.setText(days[1]);

        } else if ( sP.getInt("n", -1) == -3 ) {

            cb3.setChecked(true);
            cb1.setChecked(false);
            daysTV.setText(days[2]);

        } else if ( sP.getInt("n", -1) == -4 ) {

            cb4.setChecked(true);
            cb1.setChecked(false);
            daysTV.setText(days[3]);

        } else if ( sP.getInt("n", -1) == -5 ) {

            cb5.setChecked(true);
            cb1.setChecked(false);
            daysTV.setText(days[4]);

        } else if ( sP.getInt("n", -1) == -6 ) {

            cb6.setChecked(true);
            cb1.setChecked(false);
            daysTV.setText(days[5]);

        } else if ( sP.getInt("n", -1) == -7 ) {

            cb7.setChecked(true);
            cb1.setChecked(false);
            daysTV.setText(days[6]);

        }

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb1.isChecked()) {

                    SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sP.edit();
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    cb7.setChecked(false);
                    editor.putInt("n", -1);
                    editor.commit();
                    daysTV.setText(days[0]);
                    date.setText(dateMethod(sP.getInt("n", -1)));
                }
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb2.isChecked()) {

                    SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sP.edit();
                    cb1.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    cb7.setChecked(false);
                    editor.putInt("n", -2);
                    editor.commit();
                    daysTV.setText(days[1]);
                    date.setText(dateMethod(sP.getInt("n", -1)));
                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb3.isChecked()) {

                    SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sP.edit();
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    cb7.setChecked(false);
                    editor.putInt("n", -3);
                    editor.commit();
                    daysTV.setText(days[2]);
                    date.setText(dateMethod(sP.getInt("n", -1)));
                }
            }
        });

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb4.isChecked()) {

                    SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sP.edit();
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    cb7.setChecked(false);
                    editor.putInt("n", -4);
                    editor.commit();
                    daysTV.setText(days[3]);
                    date.setText(dateMethod(sP.getInt("n", -1)));
                }
            }
        });

        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb5.isChecked()) {

                    SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sP.edit();
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb6.setChecked(false);
                    cb7.setChecked(false);
                    editor.putInt("n", -5);
                    editor.commit();
                    daysTV.setText(days[4]);
                    date.setText(dateMethod(sP.getInt("n", -1)));
                }
            }
        });

        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb6.isChecked()) {

                    SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sP.edit();
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb7.setChecked(false);
                    editor.putInt("n", -6);
                    editor.commit();
                    daysTV.setText(days[5]);
                    date.setText(dateMethod(sP.getInt("n", -1)));
                }
            }
        });

        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb7.isChecked()) {

                    SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sP.edit();
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);

                    editor.putInt("n", -7);
                    editor.commit();
                    daysTV.setText(days[6]);
                    date.setText(dateMethod(sP.getInt("n", -1)));
                }
            }
        });
    }

    private void findViews() {

        mMainRL = (RelativeLayout) findViewById( R.id.mainRelativeLayout );

        mSendTweetB = (Button) findViewById(R.id.sendTweetButton);
        mSearchTweetB = (Button) findViewById(R.id.searchTweetButton);

        mTweetET = (EditText) findViewById(R.id.tweetEditText);

        dsBox = (CheckBox) findViewById(R.id.dsBox);

        daysTV = (TextView) findViewById(R.id.daysTV);

        daysLL = (LinearLayout) findViewById(R.id.daysLL);
        daysLL.setVisibility(View.GONE);

        cb1 = (CheckBox) findViewById(R.id.checkBox1);
        cb2 = (CheckBox) findViewById(R.id.checkBox2);
        cb3 = (CheckBox) findViewById(R.id.checkBox3);
        cb4 = (CheckBox) findViewById(R.id.checkBox4);
        cb5 = (CheckBox) findViewById(R.id.checkBox5);
        cb6 = (CheckBox) findViewById(R.id.checkBox6);
        cb7 = (CheckBox) findViewById(R.id.checkBox7);

        SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);

        if ( cb1.isChecked() ) {

            SharedPreferences.Editor editor = sP.edit();
            editor.putInt("n", -1);
        }

        date = (TextView) findViewById(R.id.dateTV);
    }

    private void touchListener() {

        mMainRL.setOnTouchListener(new OnSwipeTouchListener(SylvesterBrain.this) {
            public void onSwipeTop() {

                date.setVisibility(View.GONE);
                //Toast.makeText(Messenger.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {

                daysLL.setVisibility(View.VISIBLE);

                /*SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sP.edit();

                if ( sP.getInt("n", -1) <= -1 ) {

                    daysTV.setText(days[ days.length - (sP.getInt("n", -1) * -1 + 1) ]);
                    editor.putInt("n", sP.getInt("n", -1) + 1);
                    editor.commit();
                    Log.e("swipe right", "n = " + sP.getInt("n", -1));
                }*/

                //dsBox.setVisibility(View.VISIBLE);
                //Toast.makeText(Messenger.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {

                daysLL.setVisibility(View.GONE);

                /*SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sP.edit();

                if ( sP.getInt("n", -1) >= -7 ) {

                    daysTV.setText(days[ days.length - (sP.getInt("n", -1) * -1 + 1) ]);
                    editor.putInt("n", sP.getInt("n", -1) - 1);
                    editor.commit();
                    Log.e("swipe left", "n = " + sP.getInt("n", -1));
                }*/

                //dsBox.setVisibility(View.GONE);
                //Intent intent = new Intent(SylvesterBrain.this, MapActivity.class);
                //startActivity(intent);
                //Toast.makeText(Messenger.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {

                date.setVisibility(View.VISIBLE);
                //Toast.makeText(Messenger.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void goToSearch() {

        final SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);

        mSearchTweetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm = Realm.getDefaultInstance();
                realmResults = realm.where(Searches.class).contains("nameId", getUserText())
                        .contains("date", dateMethod(sP.getInt("n", -1))).findAll();

                if ( getUserText().equals("cut") ) {

                    Toast.makeText(SylvesterBrain.this, "Type a word.", Toast.LENGTH_SHORT).show();

                } else {

                    //Toast.makeText(SylvesterBrain.this, "GO SEARCH!" + getUserText(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SylvesterBrain.this, SearchActivity.class);
                    intent.putExtra("userText", getUserText());
                    startActivity(intent);
                }
            }
        });
    }

    private void goToDsSearch() {

        mSearchTweetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);

                realm = Realm.getDefaultInstance();
                realmResults = realm.where(Searches.class).contains("nameId", getUserText())
                        .contains("date", dateMethod(sP.getInt("n", -1))).findAll();

                if (realmResults.size() >= 1) {

                    Toast.makeText(SylvesterBrain.this, "Search already exists!"
                            , Toast.LENGTH_SHORT).show();

                } else if ( getUserText().equals("cut") ) {

                    Toast.makeText(SylvesterBrain.this, "Type a word.", Toast.LENGTH_SHORT).show();

                } else {

                    //Toast.makeText(SylvesterBrain.this, "GO SEARCH!" + getUserText(), Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sP.edit();

                    Intent intent = new Intent(SylvesterBrain.this, SearchActivity.class);
                    intent.putExtra("userText", getUserText());
                    startActivity(intent);
                }
            }
        });
    }

    public String dateMethod ( int n) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        String yesterdayAsString = dateFormat.format(calendar.getTime());

        Log.e("SearchFrag", "date - " + yesterdayAsString);
        return yesterdayAsString;
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sP = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE);

        if( sP.getBoolean("searchDone", true) ) {

            SharedPreferences.Editor editor = getSharedPreferences("oneTimeSearch", Context.MODE_PRIVATE).edit();
            editor.putBoolean("searchDone", false);
            editor.commit();
        }
    }

    public String getUserText() {

        userText = mTweetET.getText().toString();
        String cut = "cut";

        if ( !(userText.equals(" ") || userText.equals("")) ) {

            if ( userText.substring(userText.length() - 1).equals(" ") ) {

                cut = userText.substring(0, 1).toUpperCase() + userText.substring(1, userText.length() - 1);

            } else {

                cut = userText.substring(0, 1).toUpperCase() + userText.substring(1);
            }
        }
        return cut;
    }

//  ~~~~~~~~~~~~~~~ Twitter ~~~~~~~~~~~~~~~~

    private class asyncThing extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... unused) {

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            Toast.makeText(SylvesterBrain.this, "Tweet sent!", Toast.LENGTH_SHORT).show();
            //Toast.makeText(SylvesterBrain.this, "Tweets Searched: " + searchCounter, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    private void sendTweet( ) {

        mSendTweetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(SylvesterBrain.this, "Sending Tweet", "Please wait...", true);

                new Thread(new Runnable() {
                    public void run() {

                        Twitter twitter = authentication();

                        try {
                            postTweet(twitter);
                        } catch (TwitterException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        new asyncThing().execute();


                    }
                }).start();
            }
        });
    }

    private void postTweet(Twitter twitter) throws TwitterException {

        //Twitter twitter = TwitterFactory.getSingleton();
        String tweet = mTweetET.getText().toString();
        Status status = twitter.updateStatus(tweet);

        //System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }

    static final public Twitter authentication() {

        String CONSUMER_KEY        = "******************";
        String CONSUMER_SECRET     = "******************";
        String ACCESS_TOKEN        = "******************";
        String ACCESS_TOKEN_SECRET = "******************";

        AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        twitter.setOAuthAccessToken(accessToken);

        return twitter;
    }

//  ~~~~~~~~~~~~~~~ Realm ~~~~~~~~~~~~~~~~

    private void goToStoragedSearches() {

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSS);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SylvesterBrain.this, StoragedSearches.class);
                startActivity(intent);
            }
        });
    }

//  ~~~~~~~~~~~~~~~ Exit App ~~~~~~~~~~~~~~~~

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
