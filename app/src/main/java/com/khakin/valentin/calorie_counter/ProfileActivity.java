package com.khakin.valentin.calorie_counter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.khakin.valentin.calorie_counter.bean.UserInfo;
import com.khakin.valentin.calorie_counter.db.UserDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    Calendar dayBirthday = Calendar.getInstance();
    TextView sex, birthday, height, weight, activity, proteins, fats, carbohydrates, total;

    UserDB userDB;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userDB = new UserDB(this);
        userDB.init();

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

        String s = String.valueOf(birthday.getText());

        userInfo.setBirthday(s);
        userDB.updateUserBirthday(userInfo);
    }

    private void changeHeight() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        String s = String.valueOf(height.getText());

        int c1 = 1, c2 = 8, c3 = 7;

        if (height.length() != 0) {
            c1 = Character.getNumericValue(s.charAt(0));
            c2 = Character.getNumericValue(s.charAt(1));
            c3 = Character.getNumericValue(s.charAt(2));
        }

        builder.setTitle(R.string.profile_height_name);

        View layout = getLayoutInflater().inflate(R.layout.view_height_dialog, null);
        builder.setView(layout);

        final NumberPicker numberPicker1 = layout.findViewById(R.id.height_picker1);
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(2);
        numberPicker1.setValue(c1);
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker1.setWrapSelectorWheel(false);

        final NumberPicker numberPicker2 = layout.findViewById(R.id.height_picker2);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setValue(c2);
        numberPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker2.setWrapSelectorWheel(false);

        final NumberPicker numberPicker3 = layout.findViewById(R.id.height_picker3);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);
        numberPicker3.setValue(c3);
        numberPicker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker3.setWrapSelectorWheel(false);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int n1 = numberPicker1.getValue();
                        int n2 = numberPicker2.getValue();
                        int n3 = numberPicker3.getValue();

                        int total = n1 * 100 + n2 * 10 + n3;

                        String s = String.valueOf(total);
                        height.setText(s);

                        userInfo.setHeight(s);
                        userDB.updateUserHeight(userInfo);

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        String s = String.valueOf(weight.getText());
        String s1, s2;

        int c1 = 0, c2 = 8, c3 = 7, c4 = 0;

        String[] subStr;
        String delimeter = "\\."; // Разделитель
        subStr = s.split(delimeter); // Разделения строки str с помощью метода split()
        // Вывод результата на экран
        s1 = subStr[0];
        s2 = subStr[1];

        if (s1.length() == 2) {
            c1 = 0;
            c2 = Character.getNumericValue(s1.charAt(0));
            c3 = Character.getNumericValue(s1.charAt(1));
        } else if (s1.length() == 3) {
            c1 = Character.getNumericValue(s1.charAt(0));
            c2 = Character.getNumericValue(s1.charAt(1));
            c3 = Character.getNumericValue(s1.charAt(2));
        }

        c4 = Character.getNumericValue(s2.charAt(0));

        builder.setTitle(R.string.profile_weight_name);

        View layout = getLayoutInflater().inflate(R.layout.view_weight_dialog, null);
        builder.setView(layout);

        final NumberPicker numberPicker1 = layout.findViewById(R.id.weight_picker1);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(3);
        numberPicker1.setValue(c1);
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker1.setWrapSelectorWheel(false);

        final NumberPicker numberPicker2 = layout.findViewById(R.id.weight_picker2);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setValue(c2);
        numberPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker2.setWrapSelectorWheel(false);

        final NumberPicker numberPicker3 = layout.findViewById(R.id.weight_picker3);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);
        numberPicker3.setValue(c3);
        numberPicker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker3.setWrapSelectorWheel(false);

        final NumberPicker numberPicker4 = layout.findViewById(R.id.weight_picker4);
        numberPicker4.setMinValue(0);
        numberPicker4.setMaxValue(9);
        numberPicker4.setValue(c4);
        numberPicker4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker4.setWrapSelectorWheel(false);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int n1 = numberPicker1.getValue();
                        int n2 = numberPicker2.getValue();
                        int n3 = numberPicker3.getValue();
                        int n4 = numberPicker4.getValue();

                        int total = n1 * 100 + n2 * 10 + n3;

                        String s = String.valueOf(total + "." + n4);
                        weight.setText(s);

                        userInfo.setWeight(s);
                        userDB.updateUserBirthday(userInfo);

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

    private void changeActivity() {

    }

    private void changeProteins() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        String s = String.valueOf(proteins.getText());
        String s1, s2;

        int c1 = 0, c2 = 8, c3 = 7, c4 = 0;

        String[] subStr;
        String delimeter = "\\."; // Разделитель
        subStr = s.split(delimeter); // Разделения строки str с помощью метода split()
        // Вывод результата на экран
        s1 = subStr[0];
        s2 = subStr[1];

        if (s1.length() == 2) {
            c1 = 0;
            c2 = Character.getNumericValue(s1.charAt(0));
            c3 = Character.getNumericValue(s1.charAt(1));
        } else if (s1.length() == 3) {
            c1 = Character.getNumericValue(s1.charAt(0));
            c2 = Character.getNumericValue(s1.charAt(1));
            c3 = Character.getNumericValue(s1.charAt(2));
        }

        c4 = Character.getNumericValue(s2.charAt(0));

        builder.setTitle(R.string.profile_proteins);

        View layout = getLayoutInflater().inflate(R.layout.view_weight_dialog, null);
        builder.setView(layout);

        final NumberPicker numberPicker1 = layout.findViewById(R.id.weight_picker1);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(3);
        numberPicker1.setValue(c1);
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker1.setWrapSelectorWheel(false);

        final NumberPicker numberPicker2 = layout.findViewById(R.id.weight_picker2);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setValue(c2);
        numberPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker2.setWrapSelectorWheel(false);

        final NumberPicker numberPicker3 = layout.findViewById(R.id.weight_picker3);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);
        numberPicker3.setValue(c3);
        numberPicker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker3.setWrapSelectorWheel(false);

        final NumberPicker numberPicker4 = layout.findViewById(R.id.weight_picker4);
        numberPicker4.setMinValue(0);
        numberPicker4.setMaxValue(9);
        numberPicker4.setValue(c4);
        numberPicker4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker4.setWrapSelectorWheel(false);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int n1 = numberPicker1.getValue();
                        int n2 = numberPicker2.getValue();
                        int n3 = numberPicker3.getValue();
                        int n4 = numberPicker4.getValue();

                        int total = n1 * 100 + n2 * 10 + n3;

                        String s = String.valueOf(total + "." + n4);
                        proteins.setText(s);
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

    private void changeFats() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        String s = String.valueOf(fats.getText());
        String s1, s2;

        int c1 = 0, c2 = 8, c3 = 7, c4 = 0;

        String[] subStr;
        String delimeter = "\\."; // Разделитель
        subStr = s.split(delimeter); // Разделения строки str с помощью метода split()
        // Вывод результата на экран
        s1 = subStr[0];
        s2 = subStr[1];

        if (s1.length() == 2) {
            c1 = 0;
            c2 = Character.getNumericValue(s1.charAt(0));
            c3 = Character.getNumericValue(s1.charAt(1));
        } else if (s1.length() == 3) {
            c1 = Character.getNumericValue(s1.charAt(0));
            c2 = Character.getNumericValue(s1.charAt(1));
            c3 = Character.getNumericValue(s1.charAt(2));
        }

        c4 = Character.getNumericValue(s2.charAt(0));

        builder.setTitle(R.string.profile_fats);

        View layout = getLayoutInflater().inflate(R.layout.view_weight_dialog, null);
        builder.setView(layout);

        final NumberPicker numberPicker1 = layout.findViewById(R.id.weight_picker1);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(3);
        numberPicker1.setValue(c1);
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker1.setWrapSelectorWheel(false);

        final NumberPicker numberPicker2 = layout.findViewById(R.id.weight_picker2);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setValue(c2);
        numberPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker2.setWrapSelectorWheel(false);

        final NumberPicker numberPicker3 = layout.findViewById(R.id.weight_picker3);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);
        numberPicker3.setValue(c3);
        numberPicker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker3.setWrapSelectorWheel(false);

        final NumberPicker numberPicker4 = layout.findViewById(R.id.weight_picker4);
        numberPicker4.setMinValue(0);
        numberPicker4.setMaxValue(9);
        numberPicker4.setValue(c4);
        numberPicker4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker4.setWrapSelectorWheel(false);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int n1 = numberPicker1.getValue();
                        int n2 = numberPicker2.getValue();
                        int n3 = numberPicker3.getValue();
                        int n4 = numberPicker4.getValue();

                        int total = n1 * 100 + n2 * 10 + n3;

                        String s = String.valueOf(total + "." + n4);
                        fats.setText(s);
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

    private void changeCarbohydrates() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        String s = String.valueOf(carbohydrates.getText());
        String s1, s2;

        int c1 = 0, c2 = 8, c3 = 7, c4 = 0;

        String[] subStr;
        String delimeter = "\\."; // Разделитель
        subStr = s.split(delimeter); // Разделения строки str с помощью метода split()
        // Вывод результата на экран
        s1 = subStr[0];
        s2 = subStr[1];

        if (s1.length() == 2) {
            c1 = 0;
            c2 = Character.getNumericValue(s1.charAt(0));
            c3 = Character.getNumericValue(s1.charAt(1));
        } else if (s1.length() == 3) {
            c1 = Character.getNumericValue(s1.charAt(0));
            c2 = Character.getNumericValue(s1.charAt(1));
            c3 = Character.getNumericValue(s1.charAt(2));
        }

        c4 = Character.getNumericValue(s2.charAt(0));

        builder.setTitle(R.string.profile_carbohydrates);

        View layout = getLayoutInflater().inflate(R.layout.view_weight_dialog, null);
        builder.setView(layout);

        final NumberPicker numberPicker1 = layout.findViewById(R.id.weight_picker1);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(3);
        numberPicker1.setValue(c1);
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker1.setWrapSelectorWheel(false);

        final NumberPicker numberPicker2 = layout.findViewById(R.id.weight_picker2);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setValue(c2);
        numberPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker2.setWrapSelectorWheel(false);

        final NumberPicker numberPicker3 = layout.findViewById(R.id.weight_picker3);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);
        numberPicker3.setValue(c3);
        numberPicker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker3.setWrapSelectorWheel(false);

        final NumberPicker numberPicker4 = layout.findViewById(R.id.weight_picker4);
        numberPicker4.setMinValue(0);
        numberPicker4.setMaxValue(9);
        numberPicker4.setValue(c4);
        numberPicker4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker4.setWrapSelectorWheel(false);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int n1 = numberPicker1.getValue();
                        int n2 = numberPicker2.getValue();
                        int n3 = numberPicker3.getValue();
                        int n4 = numberPicker4.getValue();

                        int total = n1 * 100 + n2 * 10 + n3;

                        String s = String.valueOf(total + "." + n4);
                        carbohydrates.setText(s);
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

    public void setDate(){
        int mYear = dayBirthday.get(Calendar.YEAR);
        int mMonth = dayBirthday.get(Calendar.MONTH);
        int mDay = dayBirthday.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dayBirthday.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        String date = simpleDateFormat.format(dayBirthday.getTime());
                        birthday.setText(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
