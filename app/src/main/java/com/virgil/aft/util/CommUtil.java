package com.virgil.aft.util;

import android.widget.Toast;

import com.virgil.aft.core.ApplicationCache;

/**
 * Created by liuwujing on 14/12/12.
 */
public class CommUtil {
    public static void showToast(String context){
        if(ApplicationCache.getInstance().getApplication()!=null){
            Toast.makeText(ApplicationCache.getInstance().getApplication(),context,Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLogInToast(String context){
        showToast(context);
    }
}
