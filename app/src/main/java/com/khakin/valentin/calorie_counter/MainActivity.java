package com.khakin.valentin.calorie_counter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, R.string.activity_profile);
        menu.add(0, 2, 0, R.string.activity_graphics);
        menu.add(1, 3, 1, R.string.activity_totalPFC);
        menu.add(1, 4, 2, R.string.activity_insert);
        menu.add(1, 5, 3, R.string.activity_notification);
        menu.add(1, 6, 4, R.string.activity_settings);
        menu.add(1, 7, 5, R.string.action_exit);

        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.getItem(2).setIcon(R.drawable.item_selector);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case 1:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case 2:
                intent = new Intent(MainActivity.this, GraphicsActivity.class);
                startActivity(intent);
                return true;
            case 3:
                intent = new Intent(MainActivity.this, TotalPFCActivity.class);
                startActivity(intent);
                return true;
            case 4:
                intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
                return true;
            case 5:
                intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
                return true;
            case 6:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case 7:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
