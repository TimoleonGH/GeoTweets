package com.leon.lamti.sylvester.unrelatedStuff;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leon.lamti.sylvester.R;

public class IntHolder extends RecyclerView.ViewHolder {

    TextView holderNumber;
    TextView holderText;


    public IntHolder (View itemView) {
        super(itemView);

        holderNumber  =   (TextView) itemView.findViewById(R.id.intNumber);
        holderText    =   (TextView) itemView.findViewById(R.id.intTextView);
    }
}
