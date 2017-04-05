package com.leon.lamti.sylvester.storagedSearches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leon.lamti.sylvester.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private ArrayList<StoragedSearchObject> list;
    private Context context;

    public GridAdapter (Context context, ArrayList<StoragedSearchObject> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        GridHolder gridHolder = null;

        if ( convertView == null ) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.grid_object, parent, false);
            gridHolder = new GridHolder(row);
            row.setTag(gridHolder);

        } else {

            gridHolder = (GridHolder) row.getTag();

            /*Intent intent = new Intent( context, SearchActivity.class);
            intent.putExtra("userText", "domino's Search");
            intent.putExtra("Search", false);
            context.startActivity(intent);*/
        }

        gridHolder.mNameTV.setText(list.get(position).getName());
        gridHolder.mDateTV.setText(list.get(position).getDate());

        StoragedSearchObject temp = list.get(position);
        gridHolder.mNameTV.setTag(temp);

        return row;
    }
}
