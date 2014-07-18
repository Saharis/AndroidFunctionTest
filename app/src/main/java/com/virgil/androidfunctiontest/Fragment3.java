package com.virgil.androidfunctiontest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.virgil.androidfunctiontest.framework.BasicFragment;

/**
 * Created by liuwujing on 14-7-15.
 */
public class Fragment3 extends BasicFragment implements View.OnTouchListener {
    final String TAG = "Fragment3检查触摸的坐标";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, null);
        final Button button1=(Button)view.findViewById(R.id.button_test1);
        final Button button2=(Button)view.findViewById(R.id.button_test2);
        Button button3=(Button)view.findViewById(R.id.button_test3);

        final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) button2.getLayoutParams();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.topMargin+=5;
                button2.setLayoutParams(lp);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.topMargin+=-5;
                button2.setLayoutParams(lp);
            }
        });
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float lastY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

//                        lastMarginTop=button2.getTop();
                        lastY=event.getRawY();
                Log.d(TAG, "DOWN的Y是：" + lastY);
//                        Log.d(TAG,"button2的MarginTop是："+lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Move的Y是：" + event.getRawY() + "距离上一次Down，移动了" + (event.getRawY() - lastY));

////                        float tempY=lastY-event.getRawY();
//                        float tempY=event.getRawY()-lastY;
//                        lp.topMargin=DensityUtil.SPToPX(tempY);
////                        button2.setTop((int)event.getRawY());
//                        button2.setLayoutParams(lp);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
