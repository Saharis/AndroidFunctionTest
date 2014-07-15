package com.virgil.androidfunctiontest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by liuwujing on 14-7-15.
 */
public class TestScrollView extends View{

    public TestScrollView(Context context) {
        super(context);
    }

    public TestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        canvas.drawColor(Color.GRAY);
        for (int i = 10; i < 500; i++) {
            canvas.drawText("This is the scroll text.", 10, i, paint);
            i = i+15;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        String tag="onMeasure";
        Log.e(tag, "Scroll View on measure...");
        setMeasuredDimension(400, 1600);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        String tag = "onScrollChanged";
        Log.e(tag, "Scroll....");
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
