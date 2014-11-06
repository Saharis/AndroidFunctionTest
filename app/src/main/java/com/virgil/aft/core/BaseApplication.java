package com.virgil.aft.core;

import android.app.Application;

/**
 * Created by virgil on 7/6/14.
 */
public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationCache.setApplication(this);
    }
}
