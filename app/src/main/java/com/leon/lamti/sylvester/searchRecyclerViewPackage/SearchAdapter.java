package com.leon.lamti.sylvester.searchRecyclerViewPackage;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.SearchActivity;
import com.leon.lamti.sylvester.fragments.MapsFragment;
import com.leon.lamti.sylvester.tweetsStuff.TweetsData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

    // R e a l m
    RealmResults<Searches> realmResults;
    RealmList<TweetRealmObject> realmList;
    ArrayList<RealmList> realmArrayList;
    List<TweetsData> list = Collections.emptyList();
    Context context;

    SearchHolder holder;

    public SearchAdapter (FragmentActivity context, RealmResults<Searches> realmResults) {

        this.context = context;
        this.realmResults = realmResults;
    }

    public SearchAdapter (FragmentActivity context, RealmList<TweetRealmObject> realmList) {

        this.context = context;
        //this.realmList = realmList;
        update(realmList);
    }

    public SearchAdapter (FragmentActivity context, ArrayList<RealmList> arrayList) {

        this.context = context;
        this.realmArrayList = arrayList;
    }

    public void update ( RealmList<TweetRealmObject> realmList ) {

        this.realmList = realmList;
        notifyDataSetChanged();
    }

    public void changeGeoColor() {

        //for ( int i=0; i<arList.size(); i++ ) {

            holder.relayRL.setBackgroundResource(R.drawable.radious_geo);
            holder.user.setTextColor(Color.parseColor("#303F9F"));
            holder.text.setTextColor(Color.parseColor("#303F9F"));
//        }
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        holder = new SearchHolder(v);

        return holder;
    }

    ArrayList arList = new ArrayList();
    TweetRealmObject tweetRealmObject;

    @Override
    public void onBindViewHolder(SearchHolder holder, final int position) {



        tweetRealmObject = realmList.get(position);

        holder.number.setText(Integer.toString(tweetRealmObject.getTweetNumber()));
        holder.user.setText(tweetRealmObject.getTweetUser());
        holder.text.setText(tweetRealmObject.getTweetText());
        holder.geo.setText(tweetRealmObject.getTweetGeo());
        holder.date.setText(tweetRealmObject.getTweetDate().substring(0, 16));
        holder.relayRL.setBackgroundResource(R.drawable.radious);
        holder.user.setTextColor(Color.parseColor("#ffffff"));
        holder.text.setTextColor(Color.parseColor("#ffffff"));
        holder.cardView.setClickable(false);

        if ( !tweetRealmObject.getTweetGeo().equals("No geolocation") &&
                !arList.contains(position)) {

            holder.imageView.setImageResource(R.mipmap.ic_marker);
            holder.relayRL.setBackgroundResource(R.drawable.radious_geo);
            holder.user.setTextColor(Color.parseColor("#303F9F"));
            holder.text.setTextColor(Color.parseColor("#303F9F"));

            holder.cardView.setClickable(true);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SearchActivity.changeTab(1);

                    String geoString = realmList.get(position).getTweetGeo();
                    String fgs = geoString.replace(", longitude", "").replace("}", "");
                    Log.e("Map", "geoString: " + fgs );
                    String splStr[] = fgs.split("=");
                    double x = Double.parseDouble(splStr[1]);
                    double y = Double.parseDouble(splStr[2]);

                    MapsFragment.moveCameraTo(x, y);
                }
            });


        } else {

            holder.imageView.setImageResource(R.mipmap.ic_tweet);
        }
    }

    @Override
    public int getItemCount() {
        return realmList.size();
        //return realmResults.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, TweetsData tweetsData) {
        list.add(position, tweetsData);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(TweetsData tweetsData) {
        int position = list.indexOf(tweetsData);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
