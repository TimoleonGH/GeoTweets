package com.leon.lamti.sylvester.searchRecyclerViewPackage;



import io.realm.RealmObject;

public class TweetRealmObject extends RealmObject{

    private int tweetNumber;
    private String tweetUser;
    private String tweetText;
    private String tweetPlace;
    private String tweetGeo;
    private String tweetDate;
    private int imageId;

    public TweetRealmObject() {

    }

    public TweetRealmObject(int number, String user, String text, String place, String geo, String date, int imageId) {

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