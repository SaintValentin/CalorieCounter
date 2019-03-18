package com.khakin.valentin.calorie_counter.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.khakin.valentin.calorie_counter.bean.Product;

public class MainDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "main.db";

    private static final String COLUMN_ID = "id";

    private static final String TABLE_MAIN_SCREEN = "main_screen";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_WEIGHT = "product_weight";

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_P = "p";
    private static final String COLUMN_F = "f";
    private static final String COLUMN_C = "c";
    private static final String COLUMN_CCAL = "total";
    private static final String COLUMN_PRODUCT_WEIGHT = "product_weight";

    public MainDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE main_screen (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT, "
                + COLUMN_PRODUCT_ID + " TEXT, "
                + COLUMN_WEIGHT + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE products (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_P + " TEXT, "
                + COLUMN_F + " TEXT, "
                + COLUMN_C + " TEXT, "
                + COLUMN_CCAL + " TEXT, "
                + COLUMN_PRODUCT_WEIGHT + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN_SCREEN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }

    public void init(){
        int count = this.getProductsCount();
        if (count == 0) {

        }
    }

    private void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_P, product.getP());
        values.put(COLUMN_F, product.getF());
        values.put(COLUMN_C, product.getC());
        values.put(COLUMN_CCAL, product.getCcal());
        values.put(COLUMN_PRODUCT_WEIGHT, product.getWeight());

        // Inserting Row
        db.insert(TABLE_PRODUCTS, null, values);

        // Closing database connection
        db.close();
    }

    public Product getProduct(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_PRODUCTS, new String[] {
                        COLUMN_ID, COLUMN_NAME, COLUMN_P, COLUMN_F, COLUMN_C, COLUMN_CCAL, COLUMN_PRODUCT_WEIGHT},
                COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Product product = new Product(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));
        // return note
        return product;
    }

    public int getProductsCount() {
        String countQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
}
