package com.khakin.valentin.calorie_counter.adapter;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.khakin.valentin.calorie_counter.R;
import com.khakin.valentin.calorie_counter.bean.Product;
import com.khakin.valentin.calorie_counter.provider.MainClass;

public class MainAdapter extends CursorAdapter {

    private LayoutInflater mInflater; //нужен для создания объектов класса View

    String productName;
    String productP;
    String productF;
    String productC;
    String productCcal;
    String productWeight;

    public MainAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindView(View view, Context context, Cursor cur) {
        //cur = mergeCursor(cur);

        long id = cur.getLong(cur.getColumnIndex(MainClass.Main._ID));
        productName = cur.getString(cur.getColumnIndex(MainClass.Main.COLUMN_PRODUCT_ID));
//        productP = cur.getString(cur.getColumnIndex(MainClass.Products.COLUMN_P));
//        productF = cur.getString(cur.getColumnIndex(MainClass.Products.COLUMN_F));
//        productC = cur.getString(cur.getColumnIndex(MainClass.Products.COLUMN_C));
//        productCcal = cur.getString(cur.getColumnIndex(MainClass.Products.COLUMN_CCAL));
//        productWeight = cur.getString(cur.getColumnIndex(MainClass.Products.COLUMN_PRODUCT_WEIGHT));

        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder != null) {
            holder.tvProductName.setText(productName);
            holder.tvProductP.setText("Б: " + productP);
            holder.tvProductF.setText("Ж: " + productF);
            holder.tvProductC.setText("У: " + productC);
            holder.tvProductCcal.setText("Ккал: " + productCcal);
            holder.tvProductWeight.setText("Вес: " + productWeight + "г.");
            holder.productID = id;
        }
    }

    @Override
    public View newView(Context ctx, Cursor cursor, ViewGroup parent) {
        View root = mInflater.inflate(R.layout.main_item_layout, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.tvProductName = (TextView)root.findViewById(R.id.productName);
        holder.tvProductP = (TextView)root.findViewById(R.id.productP);
        holder.tvProductF = (TextView)root.findViewById(R.id.productF);
        holder.tvProductC = (TextView)root.findViewById(R.id.productC);
        holder.tvProductCcal = (TextView)root.findViewById(R.id.productCcal);
        holder.tvProductWeight = (TextView)root.findViewById(R.id.productWeight);
        root.setTag(holder);
        return root;
    }

    public static class ViewHolder {
        TextView tvProductName;
        TextView tvProductP;
        TextView tvProductF;
        TextView tvProductC;
        TextView tvProductCcal;
        TextView tvProductWeight;
        long productID;
    }

//    private Cursor mergeCursor(Cursor c){
//        Cursor [] m = new Cursor[c.getCount()];
//
//        if (c.moveToFirst())
//            for (int k = 0; k<c.getCount();k++){
//                //Find the mobile numbers
//                Cursor u = this.getContentResolver().query(
//                        MainClass.Products.CONTENT_URI,
//                        MainClass.Products.DEFAULT_PROJECTION,
//                        null,
//                        null,
//                        null
//                );
//                int i = 0;
//                if (u.moveToFirst())
//                    m[i++] = u;
//            } //for Each key
//
//        return new MergeCursor(m);
//    }
//
//    private ContentProvider getContentResolver() {
//        return getContentResolver();
//    }
}
