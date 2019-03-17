package com.khakin.valentin.calorie_counter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.khakin.valentin.calorie_counter.FoodActivity;
import com.khakin.valentin.calorie_counter.R;
import com.khakin.valentin.calorie_counter.SportActivity;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);

        TextView textView = (TextView) view.findViewById(R.id.date);
        String s = getDate();
        textView.setText(s);

        Button food = (Button) view.findViewById(R.id.food);
        food.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), FoodActivity.class);
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

    private String getDate(){
        String s;

        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, E");

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
