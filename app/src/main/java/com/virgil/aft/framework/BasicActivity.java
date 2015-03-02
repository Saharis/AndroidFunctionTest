package com.virgil.aft.framework;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.virgil.aft.util.LogUtil;

/**
 * Created by liuwj on 2014/6/18.
 */
public class BasicActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("LifeCycle-" +this.getClass().getSimpleName()+ "-onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("LifeCycle-" +this.getClass().getSimpleName()+ "-onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i("LifeCycle-" +this.getClass().getSimpleName()+ "-onRestart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("LifeCycle-" +this.getClass().getSimpleName()+ "-onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("LifeCycle-" +this.getClass().getSimpleName()+ "-onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("LifeCycle-" +this.getClass().getSimpleName()+ "-onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("LifeCycle-" +this.getClass().getSimpleName()+ "-onDestroy");

    }
}
