package com.leon.lamti.sylvester.storagedSearches;

import android.view.View;
import android.widget.TextView;

import com.leon.lamti.sylvester.R;

public class GridHolder {

    TextView mNameTV;
    TextView mDateTV;

    public GridHolder ( View itemView ) {

        mNameTV = (TextView) itemView.findViewById(R.id.nameTV);
        mDateTV = (TextView) itemView.findViewById(R.id.dateTV);
    }
}
