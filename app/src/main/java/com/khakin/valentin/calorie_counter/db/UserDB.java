package com.khakin.valentin.calorie_counter.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "profile";
    private static final String KEY_ID = "id";
    private static final String KEY_SEX = "sex";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_P = "p";
    private static final String KEY_F = "f";
    private static final String KEY_C = "c";
    private static final String KEY_TOTAL = "total";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
