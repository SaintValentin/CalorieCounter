package com.khakin.valentin.calorie_counter.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khakin.valentin.calorie_counter.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;

    public static MainFragment newInstance(int page){
        MainFragment pageFragment = new MainFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.main_fragment, null);

        TextView textView = (TextView) view.findViewById(R.id.date);
        String s = getDate();
        textView.setText(s);



        return view;
    }

    private String getDate(){
        String s;

        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, E");

        switch (6 - pageNumber){
            case 0:
                s = "Сегодня: " + simpleDateFormat.format(currentDate.getTime() - (1000 * 60 * 60 * 24 * (6 - pageNumber)) );
                break;
            case 1:
                s = "Вчера: " + simpleDateFormat.format(currentDate.getTime() - (1000 * 60 * 60 * 24 * (6 - pageNumber)) );
                break;
            default:
                s = simpleDateFormat.format(currentDate.getTime() - (1000 * 60 * 60 * 24 * (6 - pageNumber)) );
                break;
        }

        return s;
    }
}
