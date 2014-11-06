package com.virgil.aft.core;

import android.app.Application;

import com.virgil.aft.framework.BasicActivity;

import java.util.ArrayList;

/**s
 * Basic class,Application Level.
 * Created by liuwj on 2014/6/18.
 */
public class ApplicationCache {
    public static final ArrayList<BasicActivity> activityStack = new ArrayList<BasicActivity>();
    private static BaseApplication application;
    /**
     * @return
     */
    public static Application getApplication() {
        return application;
    }

    public static void setApplication(BaseApplication application) {
        ApplicationCache.application = application;
    }
}
