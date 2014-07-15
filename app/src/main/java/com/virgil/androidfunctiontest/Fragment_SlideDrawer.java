package com.virgil.androidfunctiontest;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.virgil.androidfunctiontest.framework.BasicFragment;
import com.virgil.androidfunctiontest.widget.CreatView;
import com.virgil.androidfunctiontest.widget.Panel;

import java.util.ArrayList;

/**
 * Created by Virgil on 2014/7/9.
 */
public class Fragment_SlideDrawer extends BasicFragment implements Panel.OnPanelListener {
    public static final String Tag = "CreatView";
    int lastY = 0;
    float[] location;
    private ScrollView scroll1;
    private TextView dragme;
    private CreatView mCreatView;
    private WindowManager wvm;
    private ViewGroup.LayoutParams layoutParams_drag;
    private LinearLayout.LayoutParams layoutParams_scroll1;
    private Context mContext;
    private Rect rc;
    private LinearLayout.LayoutParams layoutParams;
    private Panel topPanel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_slidedraw, null);
        mContext = getActivity();
//        scroll1 = (ScrollView) view.findViewById(R.id.scroll_1);
        mCreatView = CreatView.getCreatView(mContext);
        dragme = (TextView) view.findViewById(R.id.drag_me1);
        location = new float[2];
        wvm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Panel panel;
        ScrollView second_scroll = (ScrollView) view.findViewById(R.id.second_scroll);
//        topPanel = panel = (Panel) view.findViewById(R.id.mySliding);
//        panel.setOnPanelListener(this);
//        panel.setInterpolator(new BounceInterpolator(BounceInterpolator.Type.OUT));

//        dragme.setOnTouchListener(new TouchListener());
        rc = new Rect(rc);
        view.findViewById(R.id.button_slide1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("getY的值是：" + dragme.getY() + "\ngetScrollY的值是：" + dragme.getScrollY() + "\n" + "getLocationInWindow的值是：x:" + location[0] + ";y:" + location[1]);
            }
        });
        view.findViewById(R.id.button_slide2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationInWindow(location);
                mCreatView.setLocation(location);
                mCreatView.addViewToScreen();
            }
        });

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.myconten);
        final ArrayList<Button> buttonList = new ArrayList<Button>();
        while (buttonList.size() < 6) {
            Button button = new Button(getActivity());
            buttonList.add(button);
            button.setWidth(200);
            button.setHeight(80);
            button.setText("TextAdd" + buttonList.size());
            button.setVisibility(View.VISIBLE);
            linearLayout.addView(button);
        }
//        getActivity().onKeyDown()
        return view;
    }

    public void onPanelClosed(Panel panel) {
        String panelName = getResources().getResourceEntryName(panel.getId());
        Log.d("TestPanels", "Panel [" + panelName + "] closed");
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_T) {
//            topPanel.setOpen(!topPanel.isOpen(), false);
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    public void onPanelOpened(Panel panel) {
        String panelName = getResources().getResourceEntryName(panel.getId());
        Log.d("TestPanels", "Panel [" + panelName + "] opened");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        location[0] = dragme.getX();
        location[1] = dragme.getY();
    }

    class TouchListener implements View.OnTouchListener {
        int lastX;
        int lastY;
        int screenWidth;
        int screenHeight;

        public TouchListener() {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
            Log.d(Tag, "screen width =" + screenWidth + ",screen height="
                    + screenHeight);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(Tag, "TouchListener -- onTouch");
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    Log.d(Tag, "down x=" + lastX + ", y=" + lastY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    Log.d(Tag, "move dx=" + dx + ",  dy=" + dy);
                    int left = v.getLeft() + dx;
                    int top = v.getTop() + dy;
                    int right = v.getRight() + dx;
                    int bottom = v.getBottom() + dy;
                    Log.d(Tag, "view  left=" + left + ", top=" + top + ", right="
                            + right + ",bottom=" + bottom);
                    // set bound
                    if (left < 0) {
                        left = 0;
                        right = left + v.getWidth();
                    }
                    if (right > screenWidth) {
                        right = screenWidth;
                        left = right - v.getWidth();
                    }
                    if (top < 0) {
                        top = 0;
                        bottom = top + v.getHeight();
                    }
                    if (bottom > screenHeight) {
                        bottom = screenHeight;
                        top = bottom - v.getHeight();
                    }
                    v.layout(left, top, right, bottom);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    dragme.setX(location[0]);
                    dragme.setY(event.getYPrecision());
                    layoutParams_drag = dragme.getLayoutParams();
//                    layoutParams_drag.height;
                    dragme.setLayoutParams(layoutParams_drag);
//                    dragme.setLayoutParams(wlp);
//                    wvm.updateViewLayout(v, wlp);

                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        }
    }
}
