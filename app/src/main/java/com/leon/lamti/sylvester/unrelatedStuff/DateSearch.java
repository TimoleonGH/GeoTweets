package com.leon.lamti.sylvester.unrelatedStuff;

import com.leon.lamti.sylvester.searchRecyclerViewPackage.TweetRealmObject;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DateSearch extends RealmObject{

    private String idName;
    private String idDate;
    public RealmList<TweetRealmObject> searches;
    private int dailyTweetsNumber;
    private int dailyGeoTweetsNumber;

    public DateSearch() {

    }

    public DateSearch (String idName, String idDate, RealmList<TweetRealmObject> searches, int dailyTweetsNumber, int dailyGeoTweetsNumber ) {

        this.idName = idName;

        this.idDate = idDate;
        this.searches = searches;
        this.dailyTweetsNumber = dailyTweetsNumber;
        this.dailyGeoTweetsNumber = dailyGeoTweetsNumber;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdDate() {
        return idDate;
    }

    public void setIdDate(String idDate) {
        this.idDate = idDate;
    }

    public RealmList<TweetRealmObject> getSearches() {
        return searches;
    }

    public void setSearches(RealmList<TweetRealmObject> searches) {
        this.searches = searches;
    }

    public int getDailyTweetsNumber() {
        return dailyTweetsNumber;
    }

    public void setDailyTweetsNumber(int dailyTweetsNumber) {
        this.dailyTweetsNumber = dailyTweetsNumber;
    }

    public int getDailyGeoTweetsNumber() {
        return dailyGeoTweetsNumber;
    }

    public void setDailyGeoTweetsNumber(int dailyGeoTweetsNumber) {
        this.dailyGeoTweetsNumber = dailyGeoTweetsNumber;
    }

   /* public boolean getAllTweetsTexts( String tweetText) {

        //ArrayList<String> list = null;

        for( int i=0; i<searches.size(); i++ ) {

          // list.add(searches.get(i).toString());
            if ( searches.contains(tweetText)) {

                return true;
            }
        }

        return false;
    }*/
}
