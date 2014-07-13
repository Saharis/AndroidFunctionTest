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
public class CustomScrollView extends LinearLayout {
    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context) {
        super(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView(context);
    }

    private void setUpView(Context context) {
        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final ArrayList<Button> buttonList = new ArrayList<Button>();
        while (buttonList.size() < 4) {
            Button button = new Button(context);
            buttonList.add(button);
            button.setLayoutParams(lp);
            button.setText("TextAdd" + buttonList.size());
            addView(button);
        }
        buttonList.get(3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                float heightFrom = buttonList.get(3).getY();
                float heightTo = buttonList.get(2).getY();
                int ychange = (int) (heightTo - heightFrom);
                int x = (int) buttonList.get(3).getX();
                buttonList.get(3).scrollBy(x, ychange);
            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
