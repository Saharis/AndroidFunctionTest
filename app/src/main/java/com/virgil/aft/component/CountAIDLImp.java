package com.virgil.aft.component;

import android.os.IBinder;
import android.os.RemoteException;

import com.virgil.aft.ICountSerAIDL;

/**
 * Created by liuwujing on 14/12/15.
 */
public class CountAIDLImp extends ICountSerAIDL.Stub{
    public CountAIDLImp(){

    }
    @Override
    public String getCon() throws RemoteException {
        return null;
    }

    @Override
    public void setCon(String ob) throws RemoteException {

    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
