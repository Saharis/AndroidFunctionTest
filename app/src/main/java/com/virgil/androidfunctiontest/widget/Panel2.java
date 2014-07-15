package com.virgil.androidfunctiontest.widget;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Panel2 extends LinearLayout implements GestureDetector.OnGestureListener{

    public interface PanelClosedEvent {
        void onPanelClosed(View panel);
    }

    public interface PanelOpenedEvent {
        void onPanelOpened(View panel);
    }

    private final static int HANDLE_HEIGHT=50;
    private final static int MOVE_HEIGHT =60;
    private Button btnHandler;
    private LinearLayout panelContainer;
    private int mBottomMargin =0;
    private Context mContext;
    private GestureDetector mGestureDetector;
    private boolean mIsScrolling=false;
    private float mScrollX;
    private PanelClosedEvent panelClosedEvent=null;
    private PanelOpenedEvent panelOpenedEvent=null;

    public Panel2(Context context, View otherView, int width, int height) {
        super(context);
        this.mContext=context;

        //定义手势识别
        mGestureDetector = new GestureDetector(mContext,this);
        mGestureDetector.setIsLongpressEnabled(false);

        //改变Panel附近组件的属性
        LayoutParams otherLP=(LayoutParams) otherView.getLayoutParams();
        otherLP.weight=1;
        otherView.setLayoutParams(otherLP);

        //设置Panel本身的属性
        LayoutParams lp=new LayoutParams(width, height);
        lp.bottomMargin= HANDLE_HEIGHT;
        mBottomMargin =Math.abs(lp.bottomMargin);
        this.setLayoutParams(lp);
        this.setOrientation(VERTICAL);


        //设置Container的属性
        panelContainer=new LinearLayout(context);
        panelContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        this.addView(panelContainer);
        //设置Handler的属性
        btnHandler=new Button(context);
        btnHandler.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HANDLE_HEIGHT));
        //btnHandler.setOnClickListener(handlerClickEvent);
        btnHandler.setOnTouchListener(handlerTouchEvent);
        this.addView(btnHandler);

    }

    private OnTouchListener handlerTouchEvent=new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_UP && //onScroll时的ACTION_UP
                    mIsScrolling==true)
            {
                LayoutParams lp=(LayoutParams) Panel2.this.getLayoutParams();
                if (lp.bottomMargin >= (-mBottomMargin /2)) {//往左超过一半
                    new AsynMove().execute(new Integer[] {MOVE_HEIGHT});// 正数展开
                }
                else if (lp.bottomMargin < (-mBottomMargin /2)) {//往右拖拉
                    new AsynMove().execute(new Integer[] { -MOVE_HEIGHT});// 负数收缩
                }
            }
            return mGestureDetector.onTouchEvent(event);
        }
    };

    /**
     * 定义收缩时的回调函数
     * @param event
     */
    public void setPanelClosedEvent(PanelClosedEvent event)
    {
        this.panelClosedEvent=event;
    }

    /**
     * 定义展开时的回调函数
     * @param event
     */
    public void setPanelOpenedEvent(PanelOpenedEvent event)
    {
        this.panelOpenedEvent=event;
    }

    /**
     * 把View放在Panel的Container
     * @param v
     */
    public void fillPanelContainer(View v)
    {
        panelContainer.addView(v);
    }

    /**
     * 异步移动Panel
     * @author hellogv
     */
    class AsynMove extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int times;
            if (mBottomMargin % Math.abs(params[0]) == 0)// 整除
                times = mBottomMargin / Math.abs(params[0]);
            else
                // 有余数
                times = mBottomMargin / Math.abs(params[0]) + 1;

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
            LayoutParams lp = (LayoutParams) Panel2.this.getLayoutParams();
            if (params[0] < 0)
                lp.bottomMargin = Math.max(lp.bottomMargin + params[0],
                        (-mBottomMargin));
            else
                lp.bottomMargin = Math.min(lp.bottomMargin + params[0], 0);

            if(lp.bottomMargin==0 && panelOpenedEvent!=null){//展开之后
                panelOpenedEvent.onPanelOpened(Panel2.this);//调用OPEN回调函数
            }
            else if(lp.bottomMargin==-(mBottomMargin) && panelClosedEvent!=null){//收缩之后
                panelClosedEvent.onPanelClosed(Panel2.this);//调用CLOSE回调函数
            }
            Panel2.this.setLayoutParams(lp);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mScrollX=0;
        mIsScrolling=false;
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LayoutParams lp = (LayoutParams) Panel2.this.getLayoutParams();
        if (lp.bottomMargin < 0)// CLOSE的状态
            new AsynMove().execute(new Integer[] {MOVE_HEIGHT});// 正数展开
        else if (lp.bottomMargin >= 0)// OPEN的状态
            new AsynMove().execute(new Integer[] { -MOVE_HEIGHT});// 负数收缩
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        mIsScrolling=true;
        mScrollX+=distanceY;

        LayoutParams lp=(LayoutParams) Panel2.this.getLayoutParams();
        if (lp.bottomMargin < -1 && mScrollX > 0) {//往左拖拉
            lp.bottomMargin = Math.min((lp.bottomMargin + (int) mScrollX),0);
            Panel2.this.setLayoutParams(lp);
            Log.e("onScroll",lp.bottomMargin+"");
        }
        else if (lp.bottomMargin > -(mBottomMargin) && mScrollX < 0) {//往右拖拉
            lp.bottomMargin = Math.max((lp.bottomMargin + (int) mScrollX),-mBottomMargin);
            Panel2.this.setLayoutParams(lp);
        }

        if(lp.bottomMargin==0 && panelOpenedEvent!=null){//展开之后
            panelOpenedEvent.onPanelOpened(Panel2.this);//调用OPEN回调函数
        }
        else if(lp.bottomMargin==-(mBottomMargin) && panelClosedEvent!=null){//收缩之后
            panelClosedEvent.onPanelClosed(Panel2.this);//调用CLOSE回调函数
        }
        Log.e("onScroll", lp.bottomMargin + "");

        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {return false;}
    @Override
    public void onLongPress(MotionEvent e) {}
    @Override
    public void onShowPress(MotionEvent e) {}

}
