package com.leon.lamti.sylvester.storagedSearches;

public class StoragedSearchObject {

    private String name;
    private String date;

    public StoragedSearchObject() {

    }

    public StoragedSearchObject(String name, String date ) {

        this.name = name;
        this.date = date;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
