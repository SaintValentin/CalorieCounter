package com.khakin.valentin.calorie_counter;

import android.app.Application;
import android.content.res.Configuration;

public class MyApplication extends Application {
    private static MyApplication singleton;
    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        singleton = this;
    }
    @Override
    public final void onTerminate()
    {
        super.onTerminate();
    }
    @Override
    public  final  void onLowMemory()
    {
        super.onLowMemory();
    }
    @Override
    public final void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }
}
