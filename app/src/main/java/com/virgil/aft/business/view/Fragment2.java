package com.virgil.aft.business.view;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.widget.Panel2;
import com.virgil.aft.widget.PayRefundWdget;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liuwujing on 14-7-15.
 */
public class Fragment2 extends BasicFragment {
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view=inflater.inflate(R.layout.fragment2, null);
        int size=1;
        ArrayList<Button> btList=new ArrayList<Button>();
        while (btList.size()<size){
            Button button = new Button(context);
            btList.add(button);
            button.setText("Button"+btList.size());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTopActivity(context);
                }
            });
        }
        PayRefundWdget.initRefundWidget(getActivity(),"",null);
        return view;
    }

    private void getTopActivity(Context context){
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getRunningTasks(1);
    }
}
