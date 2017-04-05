package com.leon.lamti.sylvester.searchRecyclerViewPackage;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.leon.lamti.sylvester.R;

public class SearchHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView number;
    TextView user;
    TextView text;
    TextView geo;
    TextView date;
    ImageView imageView;
    CardView cardView;
    RelativeLayout relayRL;

    public SearchHolder(View itemView) {
        super(itemView);

        cv      =   (CardView) itemView.findViewById(R.id.cardView);
        number  =   (TextView) itemView.findViewById(R.id.number);
        user    =   (TextView) itemView.findViewById(R.id.title);
        text    =   (TextView) itemView.findViewById(R.id.description);
        geo     =   (TextView) itemView.findViewById(R.id.geoTextView);
        date    =   (TextView) itemView.findViewById(R.id.dateTextView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        relayRL = (RelativeLayout) itemView.findViewById(R.id.relayRL);
    }
}
