package com.virgil.androidfunctiontest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Virgil on 2014/6/26.
 */
public class Line extends View{
    private Paint linePaint;
    public Line(Context context) {
        super(context);initView(context);
    }
    public void initView(Context context){
        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(1);
        linePaint.setARGB(0xff, 0, 0, 0);
        linePaint.setAntiAlias(true);

//        this.add
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(linePaint);
    }
}
