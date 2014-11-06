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
public class Fragment3 extends BasicFragment{
    final String TAG = "Fragment3检查触摸的坐标";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, null);

        return view;
    }
}
