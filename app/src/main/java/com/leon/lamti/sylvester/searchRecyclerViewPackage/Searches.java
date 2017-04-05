package com.leon.lamti.sylvester.searchRecyclerViewPackage;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Searches extends RealmObject{

    private String nameId;
    public RealmList<TweetRealmObject> tweetList;
    private String date;
    private int num;
    private int geoNum;

    public Searches() {

    }

    public Searches ( String nameId, RealmList<TweetRealmObject> tweetList, String date, int num, int geoNum) {

        this.nameId = nameId;
        this.tweetList = tweetList;
        this.date = date;
        this.num = num;
        this.geoNum = geoNum;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public RealmList<TweetRealmObject> getTweetList() {
        return tweetList;
    }

    public void setTweetList(RealmList<TweetRealmObject> tweetList) {
        this.tweetList = tweetList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getGeoNum() {
        return geoNum;
    }

    public void setGeoNum(int geoNum) {
        this.geoNum = geoNum;
    }
}
