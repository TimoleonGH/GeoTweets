package com.leon.lamti.sylvester.tweetsStuff;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.lamti.sylvester.R;

import java.util.Collections;
import java.util.List;

public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder> {

    //List<Data> list = Collections.emptyList();
    List<TweetsData> list = Collections.emptyList();
    Context context;

    /*public Recycler_View_Adapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }*/

    public Recycler_View_Adapter(List<TweetsData> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.number.setText(Integer.toString(list.get(position).tweetNumber));
        holder.user.setText(list.get(position).tweetUser);
        holder.text.setText(list.get(position).tweetText);
        holder.geo.setText(list.get(position).tweetGeo);
        holder.date.setText(list.get(position).tweetDate);
        holder.imageView.setImageResource(list.get(position).imageId);
        //notifyDataSetChanged();
        //animate(holder);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display

        return list.size();
        //return 2;
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