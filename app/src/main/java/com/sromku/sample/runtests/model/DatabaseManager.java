package com.sromku.sample.runtests.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sromku
 */
public class DatabaseManager {

    private static DatabaseManager instance = null;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private DatabaseManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
    }

}
