package com.virgil.aft.framework;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.virgil.aft.component.ICountService;
import com.virgil.aft.util.LogUtil;

/**
 * Created by liuwujing on 14/11/6.
 */
public class CountService extends Service implements ICountService{
    private boolean stopCounting=false;
    private int count=0;
    private CountServiceBinder serBinder=new CountServiceBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopCounting){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    LogUtil.i("CountService-Current count is====" + count);
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serBinder;
    }

    @Override
    public void onDestroy() {
        stopCounting=true;
        LogUtil.i("CountService-stopped");
        super.onDestroy();
    }

    @Override
    public int getInfo() {
        return count;
    }

    @Override
    public void setInfo(Object ob) {
        if(ob instanceof Integer){
            count=(Integer)ob;
        }
    }
    class CountServiceBinder extends Binder implements ICountService{
        @Override
        public int getInfo() {
            return count;
        }

        @Override
        public void setInfo(Object ob) {
            if(ob instanceof Integer){
                count=(Integer)ob;
            }
        }
    }
}
