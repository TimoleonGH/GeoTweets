package com.leon.lamti.sylvester;

import android.content.Context;
import android.support.multidex.MultiDex;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SylvesterHeart extends android.app.Application {

    private static SylvesterHeart instance;

    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();


        instance = this;

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).name("SylvesterRealm").build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static SylvesterHeart getInstance() {

        return instance;
    }

}
