package com.virgil.aft.core;

import java.util.ArrayList;

import android.app.Application;

import com.virgil.aft.ICountSerAIDL;
import com.virgil.aft.framework.BasicActivity;

/**
 * s
 * Basic class,Application Level.
 * Created by liuwj on 2014/6/18.
 */
public class ApplicationCache {
    public ArrayList<BasicActivity> activityStack = new ArrayList<BasicActivity>();
    private BaseApplication application;
    private ICountSerAIDL iCountSer = null;

    private static ApplicationCache instance = null;

    public static ApplicationCache getInstance() {
        if (instance == null) {
            instance = new ApplicationCache();
        }
        return instance;
    }

    /**
     * @return
     */
    public Application getApplication() {
        return application;
    }

    public void setApplication(BaseApplication application) {
        this.application = application;
    }

    public ICountSerAIDL getiCountSer() {
        return iCountSer;
    }

    public void setiCountSer(ICountSerAIDL iCountSer) {
        this.iCountSer = iCountSer;
    }
}
