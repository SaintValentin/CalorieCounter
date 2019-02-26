package com.khakin.valentin.calorie_counter.db;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.khakin.valentin.calorie_counter.bean.UserInfo;
import com.khakin.valentin.calorie_counter.bean.UserNutrients;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";

    private static final String COLUMN_ID = "id";

    private static final String TABLE_USER_INFO_NAME = "user_info";
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
        sqLiteDatabase.execSQL("CREATE TABLE user_info (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SEX + " TEXT, "
                + COLUMN_BIRTHDAY + " TEXT, "
                + COLUMN_HEIGHT + " TEXT, "
                + COLUMN_WEIGHT + " TEXT, "
                + COLUMN_ACTIVITY + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE user_nutrients (" + COLUMN_ID
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
        int count = this.getUsersInfoCount();
        if (count == 0) {

            String sex = "0";
            String birthday = "25.05.1998";
            String height = "187";
            String weight = "85.0";
            String activity = "3";

            String age = getAge(birthday);

            String total;
            if (Integer.parseInt(sex) == 0) {
                total = String.valueOf(Math.round(Double.parseDouble(weight) * 9.99 + Integer.parseInt(height) * 6.25 - Integer.parseInt(age) * 4.92 + 5));
            } else {
                total = String.valueOf(Math.round(Double.parseDouble(weight) * 9.99 + Integer.parseInt(height) * 6.25 - Integer.parseInt(age) * 4.92 - 161));
            }

            String p = String.valueOf(Integer.parseInt(total) * 0.35 / 4);
            String f = String.valueOf(Integer.parseInt(total) * 0.20 / 9);
            String c = String.valueOf(Integer.parseInt(total) * 0.45 / 4);

            initFirst(sex, birthday, height, weight, activity);
            initSecond(p, f, c, total);
        }
    }

    private void initFirst(String sex, String birthday, String height, String weight, String activity) {
        UserInfo userInfo = new UserInfo(sex, birthday, height, weight, activity);
        this.addUserInfo(userInfo);
    }

    private void initSecond(String p, String f, String c, String total) {
        UserNutrients userNutrients = new UserNutrients(p, f, c, total);
        this.addUserNutrients(userNutrients);
    }

    private String getAge(String birthday){
        String age;

        Calendar dayBirthday = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            dayBirthday.setTime(simpleDateFormat.parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int years = calendar.YEAR - dayBirthday.YEAR;
        int month, day;
        if (calendar.MONTH > dayBirthday.MONTH){
            years++;
        } else if (calendar.MONTH == dayBirthday.MONTH){
            if (calendar.DAY_OF_MONTH >= dayBirthday.DAY_OF_MONTH){
                years++;
            }
        }

        age = String.valueOf(years);

        return age;
    }

    private void addUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SEX, userInfo.getSex());
        values.put(COLUMN_BIRTHDAY, userInfo.getBirthday());
        values.put(COLUMN_HEIGHT, userInfo.getHeight());
        values.put(COLUMN_WEIGHT, userInfo.getWeight());
        values.put(COLUMN_ACTIVITY, userInfo.getActivity());

        // Inserting Row
        db.insert(TABLE_USER_INFO_NAME, null, values);

        // Closing database connection
        db.close();
    }

    public UserInfo getUserInfo(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_USER_INFO_NAME, new String[] {
                COLUMN_ID, COLUMN_SEX, COLUMN_BIRTHDAY, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_ACTIVITY },
                COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        UserInfo user = new UserInfo(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        // return note
        return user;
    }

    private int getUsersInfoCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER_INFO_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public int updateUserSex(UserInfo userInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SEX, userInfo.getSex());

        // updating row
        return db.update(TABLE_USER_INFO_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userInfo.getId())});
    }

    public int updateUserBirthday(UserInfo userInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BIRTHDAY, userInfo.getBirthday());

        // updating row
        return db.update(TABLE_USER_INFO_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userInfo.getId())});
    }

    public int updateUserHeight(UserInfo userInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HEIGHT, userInfo.getHeight());

        // updating row
        return db.update(TABLE_USER_INFO_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userInfo.getId())});
    }

    public int updateUserWeight(UserInfo userInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, userInfo.getWeight());

        // updating row
        return db.update(TABLE_USER_INFO_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userInfo.getId())});
    }

    public int updateUserActivity(UserInfo userInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY, userInfo.getActivity());

        // updating row
        return db.update(TABLE_USER_INFO_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userInfo.getId())});
    }



    private void addUserNutrients(UserNutrients userNutrients) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_P, userNutrients.getP());
        values.put(COLUMN_F, userNutrients.getF());
        values.put(COLUMN_C, userNutrients.getC());
        values.put(COLUMN_TOTAL, userNutrients.getTotal());

        // Inserting Row
        db.insert(TABLE_USER_NUTRIENTS_NAME, null, values);

        // Closing database connection
        db.close();
    }

    public UserNutrients getUserNutrients(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_USER_NUTRIENTS_NAME, new String[] {
                        COLUMN_ID, COLUMN_P, COLUMN_F, COLUMN_C, COLUMN_TOTAL },
                COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        UserNutrients user = new UserNutrients(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        // return note
        return user;
    }

    private int getUsersNutrientsCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER_NUTRIENTS_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public int updateUserNutrientsP(UserNutrients userNutrients){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_P, userNutrients.getP());

        // updating row
        return db.update(TABLE_USER_NUTRIENTS_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userNutrients.getId())});
    }

    public int updateUserNutrientsF(UserNutrients userNutrients){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_F, userNutrients.getF());

        // updating row
        return db.update(TABLE_USER_NUTRIENTS_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userNutrients.getId())});
    }

    public int updateUserNutrientsC(UserNutrients userNutrients){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_C, userNutrients.getC());

        // updating row
        return db.update(TABLE_USER_NUTRIENTS_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userNutrients.getId())});
    }

    public int updateUserNutrientsTotal(UserNutrients userNutrients){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTAL, userNutrients.getTotal());

        // updating row
        return db.update(TABLE_USER_NUTRIENTS_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(userNutrients.getId())});
    }
}
