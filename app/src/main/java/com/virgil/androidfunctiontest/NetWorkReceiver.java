package com.virgil.androidfunctiontest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by liuwj on 2014/6/25.
 */
public class NetWorkReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo acNetworkInfo=connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobileNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(acNetworkInfo!=null){
            Toast.makeText(context,"活动网络类型为："+acNetworkInfo.getTypeName(),Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"没有检测到活动网络链接",Toast.LENGTH_LONG).show();
        }
        if(mobileNetworkInfo!=null){
            Toast.makeText(context,"移动网络类型为："+mobileNetworkInfo.getTypeName(),Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"没有检测到移动网络链接",Toast.LENGTH_LONG).show();
        }
    }
}
