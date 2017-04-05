package com.leon.lamti.sylvester.tweetsStuff;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leon.lamti.sylvester.R;

/*public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView title;
    TextView description;
    ImageView imageView;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}*/

public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView number;
    TextView user;
    TextView text;
    TextView geo;
    TextView date;
    ImageView imageView;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        number = (TextView) itemView.findViewById(R.id.number);
        user = (TextView) itemView.findViewById(R.id.title);
        text = (TextView) itemView.findViewById(R.id.description);
        geo = (TextView) itemView.findViewById(R.id.geoTextView);
        date = (TextView) itemView.findViewById(R.id.dateTextView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}