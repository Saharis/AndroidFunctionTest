package com.virgil.aft.business.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.virgil.aft.IAFTSerAIDL;
import com.virgil.aft.component.TaskConstants;
import com.virgil.aft.framework.BasicActivity;

/**
 * FourthActivity
 * Created by liuwujing on 15/2/26.
 */
public class FourthActivity extends BasicActivity{
    private IAFTSerAIDL serAIDL;
    private ServiceConnection serviceCon=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serAIDL=IAFTSerAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout rootView=new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setGravity(Gravity.CENTER);

        TextView text=new TextView(this);
        text.setText("This is the fourthActivity");
        rootView.addView(text);

        Button button =new Button(this);
        button.setText("Press");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serAIDL!=null){
                    try {
                        serAIDL.excute(TaskConstants.AFTSERVICE_BUSINESS_TYPE_EXCHANGE_ACTIVITY);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rootView.addView(button);
        setContentView(rootView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent mIntent=new Intent();
        mIntent.setAction("com.virgil.aft.framework.CountService");
        mIntent.setPackage(getPackageName());
        this.bindService(mIntent,serviceCon , BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(serviceCon);
        serviceCon=null;
        serAIDL=null;
    }
}
