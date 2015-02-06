package com.virgil.aft.business.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment_Calender extends BasicFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("DDDDD==onCreateView:"+System.currentTimeMillis());

        return initView(inflater);
    }

    private TextView ca_text3;
    private TextView ca_text2;
    boolean x2=false;
    boolean x3=false;
    private View initView(LayoutInflater inflater) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.layout_calendar, null);
        ca_text3=(TextView)rootView.findViewById(R.id.ca_text3);
        ca_text2=(TextView)rootView.findViewById(R.id.ca_text2);

        Context context = getActivity();
        ca_text3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                x3=true;
                ca_text3.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                refreshCenter();
            }
        });
        ca_text2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                x2=true;
                ca_text2.getViewTreeObserver().removeGlobalOnLayoutListener(this);ca_text2.getLineCount();
                refreshCenter();
            }
        });

        return rootView;

    }
    private void refreshCenter(){
        if(x2&&x3) {
            LogUtil.i("dddddd[[");
            RelativeLayout.LayoutParams lp_t3 = (RelativeLayout.LayoutParams) ca_text3.getLayoutParams();
            int ca_2height = ca_text2.getMeasuredHeight();
            int ca_3height = ca_text3.getLineHeight();
            int minus = ca_2height / 2 - ca_3height/2;
            lp_t3.topMargin+=minus;
            ca_text3.setLayoutParams(lp_t3);
        }

    }

}
