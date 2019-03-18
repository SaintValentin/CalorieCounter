package com.khakin.valentin.calorie_counter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.khakin.valentin.calorie_counter.bean.Product;
import com.khakin.valentin.calorie_counter.db.MainDB;
import com.khakin.valentin.calorie_counter.parse.ProductsParser;

import org.xmlpull.v1.XmlPullParser;

public class FoodActivity extends AppCompatActivity {

    ListView listView;

    MainDB mainDB;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        mainDB = new MainDB(this);

        int count = mainDB.getProductsCount();
        if (count == 0) {
            XmlPullParser xpp = getResources().getXml(R.xml.products_default);
            ProductsParser productsParser = new ProductsParser();
            if (productsParser.parse(xpp)) {
                for (Product prod : productsParser.getProducts()) {

                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
