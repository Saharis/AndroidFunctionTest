package com.virgil.aft.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by liuwujing on 14-8-1.
 */
public class MyLinearLayout extends LinearLayout{
    public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        scrollTo(getScrollX(),event.getRawY());
        return super.onTouchEvent(event);
    }
}
