package com.khakin.valentin.calorie_counter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    int height = 185;
    int weight = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView height_textView = (TextView) findViewById(R.id.profile_height_textView);
        height_textView.setText("" + height);

        TextView weight_textView = (TextView) findViewById(R.id.profile_weight_textView);
        weight_textView.setText("" + weight);
    }

    private void openHeightDialog(){
        Toast toast = Toast.makeText(getApplicationContext(), R.string.profile_height_name, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void openWeightDialog(){
        Toast toast = Toast.makeText(getApplicationContext(), R.string.profile_weight_name, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onRowClick(View view){
        Toast toast;
        switch (view.getId()){
            case R.id.profile_row_sex:
                toast = Toast.makeText(getApplicationContext(), R.string.profile_sex_name, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.profile_row_birthday:
                toast = Toast.makeText(getApplicationContext(), R.string.profile_birthday_name, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.profile_row_height:
                openHeightDialog();
                break;
            case R.id.profile_row_weight:
                openWeightDialog();
                break;
        }
    }
}
