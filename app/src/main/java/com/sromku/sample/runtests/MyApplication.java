package com.sromku.sample.runtests;

import android.app.Application;

import com.sromku.sample.runtests.model.DatabaseManager;

/**
 * Created by sromku
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.init(this);
    }

}
