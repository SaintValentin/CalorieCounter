package com.khakin.valentin.calorie_counter.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MainClass {
    public static final String AUTHORITY = "com.khakin.valentin.calorie_counter.provider.MainClass";

    private MainClass() {}

    public static String COLUMN_ID = "_id";

    public static final class Main implements BaseColumns {
        private Main() {}
        static final String TABLE_NAME ="main";
        private static final String SCHEME = "content://";
        private static final String PATH_MAIN = "/main";
        private static final String PATH_MAIN_ID = "/main/";
        static final int MAIN_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_MAIN);
        static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_MAIN_ID);
        static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.calorie_counter.main";
        static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.calorie_counter.main";
        static final String DEFAULT_SORT_ORDER = "date ASC";

        public static final String COLUMN_DATE   = "date";
        public static final String COLUMN_PRODUCT_ID   = "fk_product_id";
        public static final String COLUMN_WEIGHT   = "product_weight";

        public static final String[] DEFAULT_PROJECTION = new String[] {
                MainClass.Main._ID,
                MainClass.Main.COLUMN_DATE,
                MainClass.Main.COLUMN_PRODUCT_ID,
                MainClass.Main.COLUMN_WEIGHT
        };
    }

    public static final class Products implements BaseColumns {
        private Products(){}
        static final String TABLE_NAME = "products";
        public static final String SCHEME = "content://";
        public static final String PATH_PRODUCTS = "/products";
        public static final String PATH_PRODUCTS_ID = "/products/";
        static final int PRODUCTS_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_PRODUCTS);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_PRODUCTS_ID);
        static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.calorie_counter.products";
        static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.calorie_counter.products";
        static final String DEFAULT_SORT_ORDER = "name ASC";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_P = "p";
        public static final String COLUMN_F = "f";
        public static final String COLUMN_C = "c";
        public static final String COLUMN_CCAL = "total";
        public static final String COLUMN_PRODUCT_WEIGHT = "product_weight";

        public static final String[] DEFAULT_PROJECTION = new String[] {
                MainClass.Products._ID,
                MainClass.Products.COLUMN_NAME,
                MainClass.Products.COLUMN_P,
                MainClass.Products.COLUMN_F,
                MainClass.Products.COLUMN_C,
                MainClass.Products.COLUMN_CCAL,
                MainClass.Products.COLUMN_PRODUCT_WEIGHT
        };
    }
}
