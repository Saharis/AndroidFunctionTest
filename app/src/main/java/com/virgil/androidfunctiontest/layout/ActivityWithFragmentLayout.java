package com.virgil.androidfunctiontest.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by liuwj on 2014/6/19.
 */
public class ActivityWithFragmentLayout extends LinearLayout{
    public ActivityWithFragmentLayout(Context context) {
        super(context);
        initView(context);
    }

    public ActivityWithFragmentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ActivityWithFragmentLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }
    private void initView(Context context){
        setOrientation(VERTICAL);
        setPadding(4,4,4,4);
        FrameLayout frameLayout=new FrameLayout(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,0));
        setWeightSum(1);
    }

}


