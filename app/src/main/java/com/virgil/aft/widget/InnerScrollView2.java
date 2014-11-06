package com.virgil.aft.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by liuwujing on 14-7-15.
 */
public class InnerScrollView2 extends ScrollView{
    public ScrollView parentScrollView;
    public InnerScrollView2(Context context, AttributeSet attrs) {

        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("ScrollView:","t="+t+";oldt="+oldt);
//        Log.i("ScrollView:","l="+l+";t="+t+";oldl="+oldl+";oldt="+oldt);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(parentScrollView!=null){
            if (ev.getAction() == MotionEvent.ACTION_DOWN){
                return super.onTouchEvent(ev);
            }else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                super.onTouchEvent(ev);
                setParentScrollAble(false);
                return true;
            }else if (ev.getAction() == MotionEvent.ACTION_UP) {
                setParentScrollAble(false);
                return true;
            }
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (parentScrollView == null) {
            return super.onInterceptTouchEvent(ev);
        } else {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // 将父scrollview的滚动事件拦截
                setParentScrollAble(false);
                return super.onInterceptTouchEvent(ev);
            } else if (ev.getAction() == MotionEvent.ACTION_UP) {
                // 把滚动事件恢复给父Scrollview
                setParentScrollAble(true);
            } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            }
        }
        return super.onInterceptTouchEvent(ev);

    }
    private void setParentScrollAble(boolean flag){
        parentScrollView.requestDisallowInterceptTouchEvent(!flag);
    }
}
