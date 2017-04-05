package com.leon.lamti.sylvester.tweetsStuff;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leon.lamti.sylvester.R;

public class View_Tweets_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView number;
    TextView user;
    TextView text;
    ImageView imageView;

    View_Tweets_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        number = (TextView) itemView.findViewById(R.id.number);
        user = (TextView) itemView.findViewById(R.id.title);
        text = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
