package com.khakin.valentin.calorie_counter.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.widget.Toast;

import com.khakin.valentin.calorie_counter.FoodActivity;
import com.khakin.valentin.calorie_counter.R;
import com.khakin.valentin.calorie_counter.SportActivity;
import com.khakin.valentin.calorie_counter.adapter.MainAdapter;
import com.khakin.valentin.calorie_counter.provider.MainClass;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainFragment extends Fragment implements LoaderCallbacks<Cursor> {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static final String LOG_TAG = "LOG";

    int pageNumber;
    String date;
    ListView listView;
    private MainAdapter mAdapter;
    private Cursor mCursor, pCursor;

    public static MainFragment newInstance(int page){
        MainFragment pageFragment = new MainFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);

        final TextView textView = (TextView) view.findViewById(R.id.date);
        String s = getDate();
        textView.setText(s);

        date = s;
        date = date.replaceAll("Сегодня: ", "");
        date = date.replaceAll("Вчера: ", "");
        date = date.substring(0, 10);

        mCursor = getActivity().getContentResolver().query(
                MainClass.Main.CONTENT_URI,
                MainClass.Main.DEFAULT_PROJECTION,
                MainClass.Main.COLUMN_DATE + "=?",
                new String[] {"" + date},
                null
        );

        System.out.println(mCursor.getCount());

        String[] selectionArgs = new String[mCursor.getCount()];
        int i = 0;

        if (mCursor.moveToFirst())
            do {
                String string = mCursor.getString(mCursor.getColumnIndex(MainClass.Main.COLUMN_PRODUCT_ID));
                selectionArgs[i] = string;
                i++;
            } while (mCursor.moveToNext());

//        pCursor = getActivity().getContentResolver().query(
//                MainClass.Products.CONTENT_URI,
//                MainClass.Products.DEFAULT_PROJECTION,
//                MainClass.COLUMN_ID + "=?",
//                selectionArgs,
//                null
//        );

        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
        mAdapter = new MainAdapter(getActivity(), mCursor, 0);

        listView = (ListView) view.findViewById(R.id.mainListView);
        listView.setAdapter(mAdapter);

        Button food = (Button) view.findViewById(R.id.food);
        food.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), FoodActivity.class);
                        //Toast.makeText(getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", textView.getText());
                        startActivity(intent);
                    }
                }
        );
        Button sport = (Button) view.findViewById(R.id.sport);
        sport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), SportActivity.class);
                        startActivity(intent);
                    }
                }
        );

        return view;
    }

    private void aVoid(){
        mCursor = getActivity().getContentResolver().query(
                MainClass.Main.CONTENT_URI,
                MainClass.Main.DEFAULT_PROJECTION,
                MainClass.Main.COLUMN_DATE + "=?",
                new String[] {"" + date},
                null
        );

        if (mCursor != null){
            if (mCursor.moveToFirst()){
                do {
                    String pID = mCursor.getString(mCursor.getColumnIndex(MainClass.Main.COLUMN_PRODUCT_ID));
                    System.out.println(pID);

                    Uri uri = Uri.parse(MainClass.Products.CONTENT_ID_URI_BASE + pID);

                    pCursor = getActivity().getContentResolver().query(
                            uri,
                            MainClass.Products.DEFAULT_PROJECTION,
                            null,
                            null,
                            null
                    );

                    if (pCursor != null){
                        //System.out.println("pCursor: ");

                        if (pCursor.moveToFirst()){
                            do {
                                String name = pCursor.getString(pCursor.getColumnIndex(MainClass.Products.COLUMN_NAME));
                                //System.out.println(name);
                            } while (pCursor.moveToNext());
                        }
                    }

                } while (mCursor.moveToNext());
            }
        }
    }

    private String getDate(){
        String s;

        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, E");

        switch (6 - pageNumber){
            case 0:
                //aVoid();
                s = "Сегодня: " + simpleDateFormat.format(currentDate.getTime() - (1000 * 60 * 60 * 24 * (6 - pageNumber)) );
                break;
            case 1:
                //aVoid();
                s = "Вчера: " + simpleDateFormat.format(currentDate.getTime() - (1000 * 60 * 60 * 24 * (6 - pageNumber)) );
                break;
            default:
                //aVoid();
                s = simpleDateFormat.format(currentDate.getTime() - (1000 * 60 * 60 * 24 * (6 - pageNumber)) );
                break;
        }

        return s;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.d(LOG_TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d(LOG_TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d(LOG_TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d(LOG_TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d(LOG_TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.d(LOG_TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.d(LOG_TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.d(LOG_TAG, "onDetach");
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // В этом методе мы создаем CursorLoader c определенным sql-запросом.
        // В данном случае нам нужно выбрать все записи из таблицы Products,
        // и вместо условий выборки и сортировки задаем null.
        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                MainClass.Products.CONTENT_URI,
                MainClass.Products.DEFAULT_PROJECTION,
                null,
                null,
                null
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor newdata) {
        //этот метод вызывается после получения данных из БД. Адаптеру
        //посылаются новые данные в виде Cursor и сообщение о том, что
        //данные обновились и нужно заново отобразить список.
        mAdapter.swapCursor(newdata);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //если в полученном результате sql-запроса нет никаких строк,
        //то говорим адаптеру, что список нужно очистить
        mAdapter.swapCursor(null);
    }
}
