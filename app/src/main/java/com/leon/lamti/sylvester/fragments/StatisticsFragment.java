package com.leon.lamti.sylvester.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.Searches;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.TweetRealmObject;

import java.util.ArrayList;
import java.util.Hashtable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class StatisticsFragment extends Fragment {

    private Realm realm;
    private RealmResults<Searches> realmResults;
    private RealmList<TweetRealmObject> tList;
    private Hashtable<String, ArrayList> mTable;
    private ArrayList<String> mListDates;
    private ArrayList<String> mListNums;
    private String mUserInput;

    BarChart chart;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        Intent intent = getActivity().getIntent();
        mUserInput = intent.getStringExtra("userText");
        realmResults = realm.where(Searches.class).contains("nameId", mUserInput).findAll();

        initializeChartBar(rootView);

        /*FrameLayout parent = (FrameLayout) rootView.findViewById(R.id.parentLayout);
        parent.addView(chart);*/
        //getStats();
        //printStats(rootView);


    // P i e   c h a r t

        //TextView pososta = (TextView) rootView.findViewById(R.id.posostaTextView);
        //pososta.setText(SearchFragment.getTablePososta().toString());

        //SharedPreferences sp = getActivity().getSharedPreferences("statisticsSharedPref", getContext().MODE_PRIVATE);

        /*if ( sp.getBoolean("searchDone", false) ) {
            //SearchFragment.getTablePososta();

            SearchFragment.getCountries();
            SearchFragment.getTimes();

            PieChart pieChart = (PieChart) rootView.findViewById(R.id.chart);

            // creating data values
            ArrayList<Entry> entries = new ArrayList<>();

            //entries.add(new Entry(4f, 0));
            for ( int i=0; i < SearchFragment.getCountries().size(); i++) {

                entries.add(new Entry( SearchFragment.getTimes().get(i) , i));
            }


            PieDataSet dataset = new PieDataSet(entries, "# of Calls");

            // creating labels
            ArrayList<String> labels = new ArrayList<String>();
            //labels.add("January");

            for ( int i=0; i < SearchFragment.getCountries().size(); i++) {

                labels.add(SearchFragment.getCountries().get(i));
            }


            PieData data = new PieData(labels, dataset); // initialize Piedata
            pieChart.setData(data); //set data into chart

            pieChart.setDescription("Description");  // set the description

            dataset.setColors(ColorTemplate.COLORFUL_COLORS); // set the color

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("searchDone", false);
            editor.commit();
        }*/

        return rootView;
    }

    private void getStats() {

        Intent intent = getActivity().getIntent();
        mUserInput = intent.getStringExtra("userText");

        realmResults = realm.where(Searches.class).contains("nameId", mUserInput).findAllSorted("date");

        //tList = new RealmList();

        //mTable = new Hashtable();
        mListDates = new ArrayList();
        mListNums = new ArrayList();

        for (int i=0; i<realmResults.size(); i++) {

            mListNums.add(String.valueOf(realmResults.get(i).getNum()));
            mListDates.add(String.valueOf(realmResults.get(i).getDate()));

            Log.e("Stats","numbers: " + mListNums);
            Log.e("Stats","Dates: " + mListDates);
            //mTable.put(realmResults.get(i).getDate(), mList);

            //tList.add(realmResults.get(i).getDate());
            /*for (int j=0; j<realmResults.get(i).getTweetList().size(); j++) {

                tList.add(realmResults.get(i).getTweetList().get(j));
            }*/
        }
    }

    private void printStats( View rootView) {

        ArrayList<BarEntry> entries = new ArrayList();
        ArrayList<String> labels = new ArrayList();

        for ( int i=0; i<mListNums.size(); i++ ) {
            entries.add(new BarEntry(Float.parseFloat(mListNums.get(i)), i));
            labels.add(mListDates.get(i));
        }

        BarDataSet dataset = new BarDataSet(entries, "# of Tweets");

        chart = new BarChart(getContext());
        //rootView.(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("Number of tweets per day");

        Log.i("printStats", " stats: " + mTable);
    }

    private void initializeChartBar( View rootView) {

        BarChart chart = (BarChart) rootView.findViewById(R.id.chartLay);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.setContentDescription("tweets number");
        chart.setDescription("");
    }

    private ArrayList<BarDataSet> getDataSet() {

        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        for (int i=0; i<realmResults.size(); i++) {

            //mListNums.add(String.valueOf(realmResults.get(i).getNum()));
            //mListDates.add(String.valueOf(realmResults.get(i).getDate()));

            //Log.e("Stats","numbers: " + mListNums);
            //Log.e("Stats","Dates: " + mListDates);

            BarEntry v1e1 = new BarEntry(realmResults.get(i).getNum(),i); // Jan
            valueSet1.add(v1e1);

            BarEntry v1e2 = new BarEntry(realmResults.get(i).getGeoNum(),i); // Jan
            valueSet2.add(v1e2);
        }

        /*BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);*/

        /*ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);*/

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Tweets per (daily) search");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Tweets with geolocation");
        //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet2.setColor(Color.parseColor("#FF03A9F4"));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (int i=0; i<realmResults.size(); i++) {

            xAxis.add(realmResults.get(i).getDate());
        }
        /*xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");*/
        return xAxis;
    }
}
