package com.virgil.aft.framework;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.virgil.aft.ICountSerAIDL;
import com.virgil.aft.business.view.bean.CountBean;
import com.virgil.aft.util.LogUtil;

/**
 * Created by liuwujing on 14/11/6.
 */
public class CountService extends Service{
    private boolean stopCounting=false;
    private int count=0;
    private ICountSerAIDL.Stub servBinder=new ICountSerAIDL.Stub() {
        @Override
        public CountBean getCon() throws RemoteException {
            return new CountBean(count);
        }

        @Override
        public void setCon(CountBean ob) throws RemoteException {

        }
    };
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
        return servBinder;
    }

    @Override
    public void onDestroy() {
        stopCounting=true;
        LogUtil.i("CountService-stopped");
        super.onDestroy();
    }

    class CountServiceBinder extends Binder {

    }
}
