package com.virgil.aft.framework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.virgil.aft.ICountSerAIDL;
import com.virgil.aft.util.LogUtil;

/**
 * Created by liuwujing on 14/11/6.
 */
public class CountService extends Service {
    private volatile boolean stopCounting = true;
    private int count = 0;
    private ICountSerAIDL.Stub servBinder = new CountStub();

    public class CountStub extends ICountSerAIDL.Stub {
        public CountStub() {

        }

        @Override
        public String getCon() throws RemoteException {
            return String.valueOf(count);
        }

        @Override
        public void setCon(String ob) throws RemoteException {
            if ("stop".equals(ob)) {
                stopCounting = true;
            } else {
                stopCounting = false;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopCounting) {
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
        LogUtil.i("intent.getAction()="+intent.getAction()+"=");
        return servBinder;
    }

    @Override
    public void onDestroy() {
        stopCounting = true;
        LogUtil.i("CountService-stopped");
        super.onDestroy();
    }

}
