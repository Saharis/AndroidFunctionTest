package com.virgil.androidfunctiontest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by Virgil on 2014/7/9.
 */
public class CustomScrollView extends ScrollView {
    public GestureDetector sonGesture;
    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context) {
        super(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(sonGesture!=null){
//            sonGesture.onTouchEvent(ev);
//            super.dispatchTouchEvent(ev);
//            return false;
//        }else{
            return super.dispatchTouchEvent(ev);
//        }
    }
}
