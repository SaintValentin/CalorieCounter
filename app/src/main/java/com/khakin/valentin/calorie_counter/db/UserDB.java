package com.khakin.valentin.calorie_counter.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

public class UserDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";

    private static final String COLUMN_ID = "id";

    private static final String TABLE_USER_INFO_NAME = "user_info";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_SEX = "sex";
    private static final String COLUMN_BIRTHDAY = "birthday";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_ACTIVITY = "activity";

    private static final String TABLE_USER_NUTRIENTS_NAME = "user_nutrients";
    private static final String COLUMN_P = "p";
    private static final String COLUMN_F = "f";
    private static final String COLUMN_C = "c";
    private static final String COLUMN_TOTAL = "total";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TABLE_USER_INFO_NAME (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT, "
                + COLUMN_SEX + " TEXT, "
                + COLUMN_BIRTHDAY + " TEXT, "
                + COLUMN_HEIGHT + " TEXT, "
                + COLUMN_WEIGHT + " TEXT, "
                + COLUMN_ACTIVITY + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE TABLE_USER_NUTRIENTS_NAME (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_P + " TEXT, "
                + COLUMN_F + " TEXT, "
                + COLUMN_C + " TEXT, "
                + COLUMN_TOTAL + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NUTRIENTS_NAME);
        onCreate(sqLiteDatabase);
    }

    public void init(){
        initFirst();
        initSecond();
    }

    private void initFirst() {
        ContentValues contentValues = new ContentValues();
        Calendar calendar = Calendar.getInstance();

        contentValues.put(COLUMN_DATE, "");
        contentValues.put(COLUMN_SEX, "0");
        contentValues.put(COLUMN_BIRTHDAY, "");
        contentValues.put(COLUMN_HEIGHT, "187");
        contentValues.put(COLUMN_WEIGHT, "85.0");
        contentValues.put(COLUMN_ACTIVITY, "3");
    }

    private void initSecond() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_P, "");
        contentValues.put(COLUMN_F, "");
        contentValues.put(COLUMN_C, "");
        contentValues.put(COLUMN_TOTAL, "");
    }
}
