package com.leon.lamti.sylvester.tweetsStuff;


import android.graphics.Bitmap;

import io.realm.RealmObject;

public class TweetsData {
    public int tweetNumber;
    public String tweetUser;
    public String tweetText;
    public String tweetPlace;
    public String tweetGeo;
    public String tweetDate;
    public int imageId;
    //private Bitmap bmp;

    public TweetsData() {

    }

    public TweetsData(int number, String user, String text, String place, String geo, String date, int imageId) {

        this.tweetNumber = number;
        this.tweetUser = user;
        this.tweetText = text;
        this.tweetPlace = place;
        this.tweetGeo = geo;
        this.tweetDate = date;
        this.imageId = imageId;

    }

    public int getTweetNumber() {
        return tweetNumber;
    }

    public void setTweetNumber(int tweetNumber) {
        this.tweetNumber = tweetNumber;
    }

    public String getTweetUser() {
        return tweetUser;
    }

    public void setTweetUser(String tweetUser) {
        this.tweetUser = tweetUser;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getTweetPlace() {
        return tweetPlace;
    }

    public void setTweetPlace(String tweetPlace) {
        this.tweetPlace = tweetPlace;
    }

    public String getTweetGeo() {
        return tweetGeo;
    }

    public void setTweetGeo(String tweetGeo) {
        this.tweetGeo = tweetGeo;
    }

    public String getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(String tweetDate) {
        this.tweetDate = tweetDate;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}