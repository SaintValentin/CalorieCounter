package com.khakin.valentin.calorie_counter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.khakin.valentin.calorie_counter.db.UserDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    TextView sex, birthday, height, weight, activity, proteins, fats, carbohydrates, total;

    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userDB = new UserDB(this);

        sex = (TextView) findViewById(R.id.profile_row_top_sex_value);
        birthday = (TextView) findViewById(R.id.birthday_textView);
        height = (TextView) findViewById(R.id.profile_height_textView);
        weight = (TextView) findViewById(R.id.profile_weight_textView);
        activity = (TextView) findViewById(R.id.profile_action_textView);
        proteins = (TextView) findViewById(R.id.proteins_textView);
        fats = (TextView) findViewById(R.id.fats_textView);
        carbohydrates = (TextView) findViewById(R.id.carbohydrates_textView);
        total = (TextView) findViewById(R.id.total_textView);
    }

    public void onRowClick(View view){
        switch (view.getId()){
            case R.id.profile_row_sex:
                changeSex();
                break;
            case R.id.profile_row_birthday:
                changeBirthday();
                break;
            case R.id.profile_row_height:
                changeHeight();
                break;
            case R.id.profile_row_weight:
                changeWeight();
                break;
            case R.id.profile_row_activity:
                changeActivity();
                break;
            case R.id.profile_row_proteins:
                changeProteins();
                break;
            case R.id.profile_row_fats:
                changeFats();
                break;
            case R.id.profile_row_carbohydrates:
                changeCarbohydrates();
                break;
        }
    }

    private void changeSex() {

    }

    private void changeBirthday() {
        setDate();
    }

    private void changeHeight() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        builder.setTitle(R.string.profile_height_name);

        View layout = getLayoutInflater().inflate(R.layout.view_height_dialog, null);
        builder.setView(layout);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        height.setText("qwe");
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

    private void changeWeight() {

    }

    private void changeActivity() {

    }

    private void changeProteins() {

    }

    private void changeFats() {

    }

    private void changeCarbohydrates() {

    }

    public void setDate(){
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear < 10){
                            birthday.setText(dayOfMonth + "." + "0" + (monthOfYear + 1) + "." + year);
                        } else {
                            birthday.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    // отображаем диалоговое окно для выбора даты
//    public void setDate() {
//        new DatePickerDialog(ProfileActivity.this, d,
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH))
//                .show();
//    }
//
//    // установка начальных даты и времени
//    private void setInitialDateTime() {
//        birthday.setText(DateUtils.formatDateTime(this,
//                calendar.getTimeInMillis(),
//                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
//    }
//
//    // установка обработчика выбора даты
//    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            calendar.set(Calendar.YEAR, year);
//            calendar.set(Calendar.MONTH, monthOfYear);
//            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            setInitialDateTime();
//        }
//    };
}
