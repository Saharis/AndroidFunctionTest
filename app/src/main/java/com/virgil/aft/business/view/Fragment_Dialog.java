package com.virgil.aft.business.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.widget.CustomScrollView;

/**
 * Created by virgil on 6/20/14.
 */
public class Fragment_Dialog extends BasicFragment implements GestureDetector.OnGestureListener {
    final String TAG = "检查触摸的坐标";
    int scrollDistance=0;
    private GestureDetector mGestureDetector;
    private RelativeLayout.LayoutParams lp;
    private View button2;
    private  View button1;
    private LinearLayout button3;
    private int mTopMargin;
    private boolean isScrolling;
    private int height_content;
    private int height_handle;
    private RelativeLayout.LayoutParams lp_handle;
    private boolean mExpanded=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog1, container, false);
         button1 = (View) view.findViewById(R.id.button1);
        button3 = (LinearLayout) view.findViewById(R.id.button3);
        button2 = (View) view.findViewById(R.id.button2);
        button3.setClickable(true);
        button3.setFocusable(true);
        lp = (RelativeLayout.LayoutParams) button2.getLayoutParams();
        mGestureDetector = new GestureDetector(getActivity(),this);
        mGestureDetector.setIsLongpressEnabled(false);
//        lp.addRule(RelativeLayout.BELOW, R.id.button1);
        lp_handle=(RelativeLayout.LayoutParams)button3.getLayoutParams();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation a;
                if (mExpanded) {
                    a = new ExpandAnimation(0, button2.getMeasuredHeight());
                } else {
                    a = new ExpandAnimation( button2.getMeasuredHeight(), 0);
                }
                a.setDuration(500);
                button3.startAnimation(a);
                mExpanded = !mExpanded;
            }
        });
        Button button01=(Button)view.findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.topMargin += 1;
                Log.e(TAG, "当前lp.topMargin" + lp.topMargin);
                Log.e(TAG, "当前lp_handle.topMargin" + lp_handle.topMargin);
                button2.setLayoutParams(lp);
            }
        });
        CustomScrollView custom_scrollvie=(CustomScrollView)view.findViewById(R.id.custom_scrollview);
        custom_scrollvie.sonGesture=mGestureDetector;

        button3.setOnTouchListener(touchListener);
        return view;
    }
    private class ExpandAnimation extends Animation {
        private final int mStartHeight;
        private final int mDeltaHeight;

        public ExpandAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mDeltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams )button3.getLayoutParams();
            lp.topMargin = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
            button3.setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            // TODO Auto-generated method stub
            return true;
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        button2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height_content =button2.getHeight();
                Log.e(TAG, "onStart：button2.getHeight()是：" + button2.getHeight());
                Log.e(TAG, "onStart：执行时间是：" + System.currentTimeMillis());
            }
        });
        button3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button3.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height_handle =button3.getHeight();
                Log.e(TAG, "onStart：button3.getHeight()是：" + button3.getHeight());
                Log.e(TAG, "onStart：执行时间是：" + System.currentTimeMillis());
            }
        });
        button1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.e(TAG, "onStart：button1.getHeight()是：" + button1.getHeight());
                Log.e(TAG, "onStart：执行时间是：" + System.currentTimeMillis());
            }
        });
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        int temp= height_content - height_handle;
        if(temp>0){
            mTopMargin=temp;
        }else{
            mTopMargin= height_content;
        }
        mTopMargin=500;
        lp.topMargin=-142;
        button2.setLayoutParams(lp);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "OnResume:button2.getHeight()是："+button2.getHeight()+"mTOPmargin是："+mTopMargin);
    }


    private View.OnTouchListener touchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            if(event.getAction()==MotionEvent.ACTION_UP && //onScroll时的ACTION_UP
//                    isScrolling==true)
//            {
//                if (scrollDistance<0) {//往下超过一半
//                    new AsynMove().execute(new Integer[] { -10 });// 正数展开
//                }
//                else if (scrollDistance>0) {//往右拖拉
//                    new AsynMove().execute(new Integer[] { 10 });// 往上收缩
//                }else if(scrollDistance==0){
//                    if (lp.topMargin >= (-mTopMargin/2)) {//往下超过一半
//                        new AsynMove().execute(new Integer[] { -10 });// 正数展开
//                    }else
//                    if(lp.topMargin < (-mTopMargin/2)){
//                        new AsynMove().execute(new Integer[] { 10 });// 往上收缩
//                    }
//                }
//            }
//            else if(event.getAction()==MotionEvent.ACTION_UP&&!isScrolling){
//                if(lp.topMargin==0){
//                    new AsynMove().execute(new Integer[] {10});// 正数展开
//                }else  if(lp.topMargin==-mTopMargin){
//                    new AsynMove().execute(new Integer[] {-10});// 正数展开
//                }
//            }
            if(v.getId()==R.id.button3){
                return mGestureDetector.onTouchEvent(event);
            }else{
                return false;
            }

        }
    };
    /**
     * 异步移动Panel
     * @author hellogv
     */
    class AsynMove extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int times;
            if (mTopMargin % Math.abs(params[0]) == 0)// 整除
                times = mTopMargin / Math.abs(params[0]);
            else
                // 有余数
                times = mTopMargin / Math.abs(params[0]) + 1;

            for (int i = 0; i < times; i++) {
                publishProgress(params);
                try {
                    Thread.sleep(Math.abs(params[0]));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... params) {
            if (params[0] < 0)
                lp.topMargin = Math.min(lp.topMargin - params[0],0
                        );
            else
                lp.topMargin = Math.max(lp.topMargin - params[0], (-mTopMargin));
//            if(lp.topMargin<=mTopMargin&&lp.topMargin>=-mTopMargin){
                button2.setLayoutParams(lp);
//            }
        }
    }
    @Override
    public boolean onDown(MotionEvent e) {
        isScrolling=false;
        scrollDistance=0;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//if(lp.topMargin==0){
//    new AsynMove().execute(new Integer[] { 10 });// 正数展开
//}else if(lp.topMargin==mTopMargin){
//    new AsynMove().execute(new Integer[] { -10 });// 正数展开
//}

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            isScrolling=true;
            scrollDistance+=distanceY;

            Log.e(TAG, "button2.getHeight()是："+button2.getHeight()+"mTopMargin是："+mTopMargin);
            Log.e(TAG, "当前lp.topMargin："+lp.topMargin+";scrollDistance是："+scrollDistance);
            Log.e(TAG, "当前lp.topMargin："+lp.topMargin);
            if (/*lp.topMargin >-mTopMargin&&lp.topMargin<=0 &&*/ scrollDistance > 0) {//往上拖拉
                lp.topMargin = Math.max((lp.topMargin - scrollDistance), -mTopMargin);
//            lp.topMargin=lp.topMargin -(int) scrollDistance;
                button2.setLayoutParams(lp);
                Log.e(TAG,"我在向上");
            }else if (/*lp.topMargin<0&& */scrollDistance < 0) {//往下拖拉
//            lp.topMargin = Math.max((lp.topMargin + (int) scrollDistance),-mTopMargin);
                lp.topMargin=lp.topMargin - (int) scrollDistance;
                button2.setLayoutParams(lp);
                Log.e(TAG,"我在向下");
            }
            button2.setVisibility(View.VISIBLE);

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}
