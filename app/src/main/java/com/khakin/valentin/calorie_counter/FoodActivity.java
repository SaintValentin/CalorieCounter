package com.khakin.valentin.calorie_counter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.khakin.valentin.calorie_counter.bean.Product;
import com.khakin.valentin.calorie_counter.db.MainDB;
import com.khakin.valentin.calorie_counter.fragment.MainFragment;
import com.khakin.valentin.calorie_counter.parse.ProductsParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    ListView listView;
    MainDB mainDB;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        date = date.replaceAll("Сегодня: ", "");
        date = date.replaceAll("Вчера: ", "");
        date = date.substring(0, 10);


        listView = (ListView) findViewById(R.id.listFood);

        mainDB = new MainDB(this);

        int count = mainDB.getProductsCount();
        if (count == 0) {
            XmlPullParser xpp = getResources().getXml(R.xml.products_default);
            ProductsParser productsParser = new ProductsParser();
            ArrayList<Product> products = productsParser.getProducts();
            if (productsParser.parse(xpp)) {
                for (Product prod : productsParser.getProducts()) {
                    mainDB.addProductFrom(prod);
                    System.out.println(prod.getName());
                    Log.d("XML", prod.toString());
                }
            }
        }

        List<String> list = mainDB.selectAllProducts();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "Номер: " + l, Toast.LENGTH_SHORT).show();
                fromActivity((int) (l));
            }
        });
    }

    public void fromActivity(int _id){
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(_id));
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        this.finish();
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

//        db.close();
//        productsCursor.close();
    }
}
