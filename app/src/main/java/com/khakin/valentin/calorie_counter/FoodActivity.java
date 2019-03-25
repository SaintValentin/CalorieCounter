package com.khakin.valentin.calorie_counter;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khakin.valentin.calorie_counter.adapter.ProductAdapter;
import com.khakin.valentin.calorie_counter.bean.Product;
import com.khakin.valentin.calorie_counter.db.MainDB;
import com.khakin.valentin.calorie_counter.fragment.MainFragment;
import com.khakin.valentin.calorie_counter.parse.ProductsParser;
import com.khakin.valentin.calorie_counter.provider.MainClass;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, OnItemClickListener {

    String date;
    private ListView listView;
    private ProductAdapter mAdapter;
    MainDB mainDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Button add = (Button) findViewById(R.id.add_product);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);

                builder.setTitle("Новый продукт");

                final View layout = getLayoutInflater().inflate(R.layout.add_food, null);
                builder.setView(layout);

                final EditText edProductName = layout.findViewById(R.id.newProductName);
                final EditText edProductP = layout.findViewById(R.id.newProductP);
                final EditText edProductF = layout.findViewById(R.id.newProductF);
                final EditText edProductC = layout.findViewById(R.id.newProductC);
                final EditText edProductCcal = layout.findViewById(R.id.newProductCcal);
                final EditText edProductWeight = layout.findViewById(R.id.newProductWeight);

                builder.setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String newProductName = String.valueOf(edProductName.getText());
                                String newProductP = String.valueOf(edProductP.getText());
                                String newProductF = String.valueOf(edProductF.getText());
                                String newProductC = String.valueOf(edProductC.getText());
                                String newProductCcal = String.valueOf(edProductCcal.getText());
                                String newProductWeight = String.valueOf(edProductWeight.getText());

                                addProduct(newProductName, newProductP, newProductF, newProductC,
                                        newProductCcal, newProductWeight);

                                dialog.dismiss();
                            }
                        })

                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        date = date.replaceAll("Сегодня: ", "");
        date = date.replaceAll("Вчера: ", "");
        date = date.substring(0, 10);
        System.out.println(date);


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
                    Log.d("Парсинг", prod.toString());
                }
            }
        }

        mAdapter = new ProductAdapter(this, null, 0);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
        getSupportLoaderManager().initLoader(0, null, this);
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
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // В этом методе мы создаем CursorLoader c определенным sql-запросом.
        // В данном случае нам нужно выбрать все записи из таблицы Products,
        // и вместо условий выборки и сортировки задаем null.
        return new CursorLoader(
                this,
                MainClass.Products.CONTENT_URI, //uri для таблицы Products
                MainClass.Products.DEFAULT_PROJECTION, //список столбцов, которые должны присутствовать в выборке
                null, // условие WHERE для выборки
                null, // аргументы для условия WHERE
                null); // порядок сортировки
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor newData) {
        //этот метод вызывается после получения данных из БД. Адаптеру
        //посылаются новые данные в виде Cursor и сообщение о том, что
        //данные обновились и нужно заново отобразить список.
        mAdapter.swapCursor(newData);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //если в полученном результате sql-запроса нет никаких строк,
        //то говорим адаптеру, что список нужно очистить
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        Toast.makeText(getApplicationContext(), String.valueOf(id) + " ", Toast.LENGTH_SHORT).show();
    }

    final String LOG_TAG = "myLogs";

    public void selectP(String weight, final long id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MainClass.Main.COLUMN_DATE, date);
        contentValues.put(MainClass.Main.COLUMN_WEIGHT, weight);
        contentValues.put(MainClass.Main.COLUMN_PRODUCT_ID, id);

        Uri i = getContentResolver().insert(MainClass.Main.CONTENT_URI, contentValues);
        Log.d(LOG_TAG, "insert to Main, result Uri : " + i.toString());

        onStop();
        finish();
    }

    public void addProduct(String name, String p, String f, String c, String ccal, String weight){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MainClass.Products.COLUMN_NAME, name);
        contentValues.put(MainClass.Products.COLUMN_P, p);
        contentValues.put(MainClass.Products.COLUMN_F, f);
        contentValues.put(MainClass.Products.COLUMN_C, c);
        contentValues.put(MainClass.Products.COLUMN_CCAL, ccal);
        contentValues.put(MainClass.Products.COLUMN_PRODUCT_WEIGHT, weight);

        Uri newUri = getContentResolver().insert(MainClass.Products.CONTENT_URI, contentValues);
        Log.d(LOG_TAG, "insert, result Uri : " + newUri.toString());
    }

    public void selectProduct(final long id){
        Uri uri = Uri.parse(MainClass.Products.SCHEME + MainClass.AUTHORITY + MainClass.Products.PATH_PRODUCTS_ID + id);
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);

        String sName = null;
        String sWeight = null;

        if(cursor!=null){
            while (cursor.moveToNext()) {
                // получаем каждый контакт
                sName = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_NAME));
                sWeight = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_PRODUCT_WEIGHT));
            }
            cursor.close();
        }

        builder.setTitle("Добавить: " + sName);

        final View layout = getLayoutInflater().inflate(R.layout.add_to_date, null);
        builder.setView(layout);

        //final TextView name = (TextView)layout.findViewById(R.id.selectedProduct);
        final TextView weight = (TextView)layout.findViewById(R.id.selectedWeight);

        //name.setText(sName);
        weight.setText(sWeight);


        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String sWeight = String.valueOf(weight.getText());
                        selectP(sWeight, id);
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
        Log.d(LOG_TAG, "select, result Uri : ");
    }

    public void editProduct(final long id){

        String productName = null;
        String productP = null;
        String productF = null;
        String productC = null;
        String productCcal = null;
        String productWeight = null;

        Uri uri = Uri.parse(MainClass.Products.SCHEME + MainClass.AUTHORITY + MainClass.Products.PATH_PRODUCTS_ID + id);
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);

        builder.setTitle("Редактирование");

        final View layout = getLayoutInflater().inflate(R.layout.add_food, null);
        builder.setView(layout);

        if(cursor!=null){
            while (cursor.moveToNext()) {
                // получаем каждый контакт
                productName = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_NAME));
                productP = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_P));
                productF = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_F));
                productC = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_C));
                productCcal = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_CCAL));
                productWeight = cursor.getString(cursor.getColumnIndex(MainClass.Products.COLUMN_PRODUCT_WEIGHT));
            }
            cursor.close();
        }

        final EditText edProductName = layout.findViewById(R.id.newProductName);
        final EditText edProductP = layout.findViewById(R.id.newProductP);
        final EditText edProductF = layout.findViewById(R.id.newProductF);
        final EditText edProductC = layout.findViewById(R.id.newProductC);
        final EditText edProductCcal = layout.findViewById(R.id.newProductCcal);
        final EditText edProductWeight = layout.findViewById(R.id.newProductWeight);

        edProductName.setText(productName);
        edProductP.setText(productP);
        edProductF.setText(productF);
        edProductC.setText(productC);
        edProductCcal.setText(productCcal);
        edProductWeight.setText(productWeight);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newProductName = String.valueOf(edProductName.getText());
                        String newProductP = String.valueOf(edProductP.getText());
                        String newProductF = String.valueOf(edProductF.getText());
                        String newProductC = String.valueOf(edProductC.getText());
                        String newProductCcal = String.valueOf(edProductCcal.getText());
                        String newProductWeight = String.valueOf(edProductWeight.getText());

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MainClass.Products.COLUMN_NAME, newProductName);
                        contentValues.put(MainClass.Products.COLUMN_P, newProductP);
                        contentValues.put(MainClass.Products.COLUMN_F, newProductF);
                        contentValues.put(MainClass.Products.COLUMN_C, newProductC);
                        contentValues.put(MainClass.Products.COLUMN_CCAL, newProductCcal);
                        contentValues.put(MainClass.Products.COLUMN_PRODUCT_WEIGHT, newProductWeight);

                        Uri uri = ContentUris.withAppendedId(MainClass.Products.CONTENT_URI, id);
                        getContentResolver().update(uri, contentValues, null, null);

                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();

        Log.d(LOG_TAG, "edit, result Uri : ");
    }

    public void delProduct(long id){
        Uri uri = ContentUris.withAppendedId(MainClass.Products.CONTENT_URI, id);
        int cnt = getContentResolver().delete(uri, null, null);
        Log.d(LOG_TAG, "delete, result Uri : " + cnt);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long itemId = info.id;

        switch (item.getItemId())
        {
            case R.id.select:
                selectProduct(itemId);
                break;
            case R.id.edit:
                editProduct(itemId);
                break;
            case R.id.del:
                delProduct(itemId);
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}
