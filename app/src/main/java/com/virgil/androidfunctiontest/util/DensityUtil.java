package com.virgil.androidfunctiontest.util;

import android.util.TypedValue;

import com.virgil.androidfunctiontest.core.ApplicationCache;

/**
 * Created by liuwj on 2014/6/18.
 */
public class DensityUtil {

    public static int DIPToSP(int sp) {
        int dpi_value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, ApplicationCache.getApplication().getResources().getDisplayMetrics());
        return dpi_value;
    }
}
