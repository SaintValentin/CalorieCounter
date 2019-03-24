package com.khakin.valentin.calorie_counter.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.khakin.valentin.calorie_counter.db.MainDB;

import java.util.HashMap;
import java.util.Objects;

public class MainProvider extends ContentProvider {

    private static final int DATABASE_VERSION = 1;
    private static HashMap<String, String> sMainProjectionMap;
    private static HashMap<String, String> sProductsProjectionMap;
    private static final int MAIN = 1;
    private static final int MAIN_ID = 2;
    public static final int PRODUCTS = 3;
    private static final int PRODUCTS_ID = 4;
    private static final UriMatcher sUriMatcher;
    private MainDB mainDB;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(MainClass.AUTHORITY, "main", MAIN);
        sUriMatcher.addURI(MainClass.AUTHORITY, "main/#", MAIN_ID);
        sUriMatcher.addURI(MainClass.AUTHORITY, "products", PRODUCTS);
        sUriMatcher.addURI(MainClass.AUTHORITY, "products/#", PRODUCTS_ID);
        sMainProjectionMap = new HashMap<String, String>();
        for(int i=0; i < MainClass.Main.DEFAULT_PROJECTION.length; i++) {
            sMainProjectionMap.put(
                    MainClass.Main.DEFAULT_PROJECTION[i],
                    MainClass.Main.DEFAULT_PROJECTION[i]);
        }
        sProductsProjectionMap = new HashMap<String, String>();
        for(int i=0; i < MainClass.Products.DEFAULT_PROJECTION.length; i++) {
            sProductsProjectionMap.put(
                    MainClass.Products.DEFAULT_PROJECTION[i],
                    MainClass.Products.DEFAULT_PROJECTION[i]);
        }
    }

    @Override
    public boolean onCreate() {
        mainDB = new MainDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = null;
        switch (sUriMatcher.match(uri)) {
            case MAIN:
                qb.setTables(MainClass.Main.TABLE_NAME);
                qb.setProjectionMap(sMainProjectionMap);
                orderBy = MainClass.Main.DEFAULT_SORT_ORDER;
                break;
            case MAIN_ID:
                qb.setTables(MainClass.Main.TABLE_NAME);
                qb.setProjectionMap(sMainProjectionMap);
                qb.appendWhere(MainClass.Main._ID + "=" + uri.getPathSegments().get(MainClass.Main.MAIN_ID_PATH_POSITION));
                orderBy = MainClass.Main.DEFAULT_SORT_ORDER;
                break;
            case PRODUCTS:
                qb.setTables(MainClass.Products.TABLE_NAME);
                qb.setProjectionMap(sProductsProjectionMap);
                orderBy = MainClass.Products.DEFAULT_SORT_ORDER;
                break;
            case PRODUCTS_ID:
                qb.setTables(MainClass.Products.TABLE_NAME);
                qb.setProjectionMap(sProductsProjectionMap);
                qb.appendWhere(MainClass.Products._ID + "=" + uri.getPathSegments().get(MainClass.Products.PRODUCTS_ID_PATH_POSITION));
                orderBy = MainClass.Products.DEFAULT_SORT_ORDER;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = mainDB.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MAIN:
                return MainClass.Main.CONTENT_TYPE;
            case MAIN_ID:
                return MainClass.Main.CONTENT_ITEM_TYPE;
            case PRODUCTS:
                return MainClass.Products.CONTENT_TYPE;
            case PRODUCTS_ID:
                return MainClass.Products.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (
                sUriMatcher.match(uri) != MAIN && sUriMatcher.match(uri) != PRODUCTS
        ) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = mainDB.getWritableDatabase();
        ContentValues values;
        if (contentValues != null) {
            values = new ContentValues(contentValues);
        }
        else {
            values = new ContentValues();
        }
        long rowId = -1;
        Uri rowUri = Uri.EMPTY;
        switch (sUriMatcher.match(uri)) {
            case MAIN:
                rowId = db.insert(MainClass.Main.TABLE_NAME,
                        MainClass.Main.COLUMN_DATE,
                        values);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(MainClass.Main.CONTENT_ID_URI_BASE, rowId);
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(rowUri, null);
                }
                break;
            case PRODUCTS:
                rowId = db.insert(MainClass.Products.TABLE_NAME,
                        MainClass.Products.COLUMN_NAME,
                        values);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(MainClass.Products.CONTENT_ID_URI_BASE, rowId);
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(rowUri, null);
                }
                break;
        }
        return rowUri;
    }

    public static final String _ID = "_id";

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db = mainDB.getWritableDatabase();
        String finalWhere;
        int count;
        String LOG = "delete";
        switch (sUriMatcher.match(uri)) {
            case MAIN:
                count = db.delete(MainClass.Main.TABLE_NAME,where,whereArgs);
                break;
            case MAIN_ID:
                finalWhere = MainClass.Main._ID + " = " + uri.getPathSegments().get(MainClass.Main.MAIN_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(MainClass.Main.TABLE_NAME,finalWhere,whereArgs);
                break;
            case PRODUCTS:
                count = db.delete(MainClass.Products.TABLE_NAME,where,whereArgs);
                Log.d(LOG, "delete, PRODUCTS");
                break;
            case PRODUCTS_ID:
                finalWhere = MainClass.Products._ID + " = " + uri.getPathSegments().get(MainClass.Products.PRODUCTS_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(MainClass.Products.TABLE_NAME,finalWhere,whereArgs);
                Log.d(LOG, "delete, PRODUCTS_ID");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db = mainDB.getWritableDatabase();
        int count;
        String finalWhere;
        String id;
        switch (sUriMatcher.match(uri)) {
            case MAIN:
                count = db.update(MainClass.Main.TABLE_NAME, values, where, whereArgs);
                break;
            case MAIN_ID:
                id = uri.getPathSegments().get(MainClass.Main.MAIN_ID_PATH_POSITION);
                finalWhere = MainClass.Main._ID + " = " + id;
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(MainClass.Main.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            case PRODUCTS:
                count = db.update(MainClass.Products.TABLE_NAME, values, where, whereArgs);
                break;
            case PRODUCTS_ID:
                id = uri.getPathSegments().get(MainClass.Products.PRODUCTS_ID_PATH_POSITION);
                finalWhere = MainClass.Products._ID + " = " + id;
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(MainClass.Products.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }
}
