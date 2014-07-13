package com.virgil.androidfunctiontest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.virgil.androidfunctiontest.datamodel.pay.Pay_TitleInfoModel;
import com.virgil.androidfunctiontest.framework.BasicActivity;
import com.virgil.androidfunctiontest.framework.BasicFragment;
import com.virgil.androidfunctiontest.util.DensityUtil;
import com.virgil.androidfunctiontest.widget.Line;

import java.util.ArrayList;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment1 extends BasicFragment{
    public Pay_TitleInfoModel titleInfoModel=null;
    private  LinearLayout frame;
    private TextView tvdong;
    private TextView tvjing;
    private ScrollView sv;
    int[] location = new int[2];
    int[] location2 = new int[2];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.mytest,null);
    initview(view);
        return view;
    }
    private void initview(View view) {
        //在滚动的一个TextView
        tvdong = (TextView) view.findViewById(R.id.dong);
        //一开始是View.GONE的一个静止的TextView
        tvjing = (TextView) view.findViewById(R.id.jing);
        sv = (ScrollView) view.findViewById(R.id.eee);
        tvjing.setVisibility(View.GONE);

        sv.setOnTouchListener(new View.OnTouchListener() {
            private int lastY = 0;
            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == touchEventId) {
                        if (lastY != sv.getScrollY()) {
                            //scrollview一直在滚动，会触发
                            handler.sendMessageDelayed(
                                    handler.obtainMessage(touchEventId, sv), 5);
                            lastY = sv.getScrollY();
                            tvdong.getLocationOnScreen(location);
                            tvjing.getLocationOnScreen(location2);
                            //动的到静的位置时，静的显示。动的实际上还是网上滚动，但我们看到的是静止的那个
                            if (location[1] <= location2[1]) {
                                tvjing.setVisibility(View.VISIBLE);
                            } else {
                                //静止的隐藏了
                                tvjing.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            };

            public boolean onTouch(View v, MotionEvent event) {
                //必须两个都搞上，不然会有瑕疵。
                //没有这段，手指按住拖动的时候没有效果
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    handler.sendMessageDelayed(
                            handler.obtainMessage(touchEventId, v), 5);
                }
                //没有这段，手指松开scroll继续滚动的时候，没有效果
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(
                            handler.obtainMessage(touchEventId, v), 5);
                }
                return false;
            }
        });
    }
}
