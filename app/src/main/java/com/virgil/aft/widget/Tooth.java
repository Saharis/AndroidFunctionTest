package com.virgil.aft.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.virgil.aft.R;
import com.virgil.aft.core.ApplicationCache;
import com.virgil.aft.util.DeviceUtil;

/**
 * Created by liuwujing on 14-7-23.
 */
public class Tooth extends LinearLayout{
    public Tooth(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context){
        if(context!=null) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.tooth);
            setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            setLayoutParams(lp);
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.tooth);
            int width = bitmap.getWidth();
            bitmap.recycle();
            //获取屏幕信息
            if(ApplicationCache.getInstance().getApplication()!=null) {
                DisplayMetrics dm = ApplicationCache.getInstance().getApplication().getResources().getDisplayMetrics();

                int screenWidth = DeviceUtil.getScreenSize(dm)[0];
                int times = 0;
                if (screenWidth % width != 0) {
                    times = screenWidth / width + 1;
                } else {
                    times = screenWidth / width;
                }
                for (int i = 0; i < times; i++) {
                    ImageView imageView = new ImageView(context);
                    imageView.setImageDrawable(drawable);
                    imageView.setScaleType(ImageView.ScaleType.MATRIX);
//                    imageView.getDrawable().setAlpha(0);
                    addView(imageView);
                }
            }
        }
    }
}
