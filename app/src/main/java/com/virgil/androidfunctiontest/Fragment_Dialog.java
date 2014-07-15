package com.virgil.androidfunctiontest;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.virgil.androidfunctiontest.framework.BasicFragment;

/**
 * Created by virgil on 6/20/14.
 */
public class Fragment_Dialog extends BasicFragment implements GestureDetector.OnGestureListener {
    final String TAG = "检查触摸的坐标";
    int scrollDistance=0;
    private GestureDetector mGestureDetector;
    private RelativeLayout.LayoutParams lp;
    private Button button2;
    private int mTopMargin;
    private boolean isScrolling;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog1, container, false);
        Button button1 = (Button) view.findViewById(R.id.button1);
        Button button3 = (Button) view.findViewById(R.id.button3);
        button2 = (Button) view.findViewById(R.id.button2);
        lp = (RelativeLayout.LayoutParams) button2.getLayoutParams();
        mGestureDetector = new GestureDetector(getActivity(),this);
        mGestureDetector.setIsLongpressEnabled(false);
        lp.addRule(RelativeLayout.BELOW, R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.topMargin += -10;
                button2.setLayoutParams(lp);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.topMargin += 10;
                button2.setLayoutParams(lp);
            }
        });
        button3.setOnTouchListener(touchListener);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTopMargin=button2.getHeight()-30;
        Log.e(TAG, "Button2的Height是："+button2.getHeight()+"mTOPmargin是："+mTopMargin);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTopMargin=button2.getHeight()-30;
        Log.e(TAG, "Button2的Height是："+button2.getHeight()+"mTOPmargin是："+mTopMargin);
    }

    private View.OnTouchListener touchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            if(event.getAction()==MotionEvent.ACTION_UP && //onScroll时的ACTION_UP
//                    mIsScrolling==true)
//            {
//                if (lp.topMargin >= (-mTopMargin/2)) {//往左超过一半
//                    new AsynMove().execute(new Integer[] { MOVE_WIDTH });// 正数展开
//                }
//                else if (lp.rightMargin < (-mTopMargin/2)) {//往右拖拉
//                    new AsynMove().execute(new Integer[] { -MOVE_WIDTH });// 负数收缩
//                }
//            }
            return mGestureDetector.onTouchEvent(event);
        }
    };
    @Override
    public boolean onDown(MotionEvent e) {
        isScrolling=false;
        scrollDistance=0;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        isScrolling=true;
        scrollDistance+=distanceY;
        mTopMargin=button2.getHeight();
        Log.e(TAG, "Button2的Height是："+button2.getHeight()+"mTOPmargin是："+mTopMargin);
        Log.e(TAG, "当期topMargin是："+lp.topMargin+";滚动距离是："+scrollDistance);
        if (lp.topMargin >-mTopMargin && scrollDistance > 0) {//往左拖拉
            lp.topMargin = Math.max((lp.topMargin - scrollDistance), -mTopMargin);
//            lp.topMargin=lp.topMargin -(int) scrollDistance;
            button2.setLayoutParams(lp);
            Log.e(TAG,"我在向上");
        }else if (/*lp.topMargin > -(mTopMargin) &&*/ scrollDistance < 0) {//往右拖拉
//            lp.topMargin = Math.max((lp.topMargin + (int) scrollDistance),-mTopMargin);
            lp.topMargin=lp.topMargin - (int) scrollDistance;
            button2.setLayoutParams(lp);
            Log.e(TAG,"我在向下");
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
