package com.sromku.sample.runtests;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sromku.sample.runtests.model.DatabaseManager;

/**
 * Created by sromku
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.init(this);
        incrementLaunchNum();
    }

    private void incrementLaunchNum() {
        String key = "LAUNCH_COUNT";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int count = preferences.getInt(key, 0);
        preferences.edit().putInt(key, ++count).apply();
    }

}
