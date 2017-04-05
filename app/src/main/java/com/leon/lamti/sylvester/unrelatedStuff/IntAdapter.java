package com.leon.lamti.sylvester.unrelatedStuff;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.lamti.sylvester.R;

import java.util.Collections;
import java.util.List;

public class IntAdapter extends RecyclerView.Adapter<IntHolder> {

    List<Integer> list = Collections.emptyList();
    Context context;

    public IntAdapter (Context context, List<Integer> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public IntHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.int_row_layout, parent, false);
        IntHolder holder = new IntHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(IntHolder holder, int position) {

        holder.holderNumber.setText(Integer.toString(list.get(position)));
        holder.holderText.setText( "Item position: " + list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, int num) {
        list.add(position, num);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(int num) {
        int position = list.indexOf(num);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
