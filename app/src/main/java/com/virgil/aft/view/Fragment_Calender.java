package com.virgil.aft.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment_Calender extends BasicFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_calendar,container,false);
        final ScrollView scrollView2=(ScrollView)view.findViewById(R.id.myScroll);
        LinearLayout myScroll_son=(LinearLayout)scrollView2.findViewById(R.id.myScroll_son);
        ArrayList<TextView> buttonList=new ArrayList<TextView>();
        while(buttonList.size()<20){
            TextView button =new TextView(getActivity());
            button.setHeight(80);
            button.setText("Button"+buttonList.size());
            buttonList.add(button);
            myScroll_son.addView(button);
        }
        final Button buttonSHow=(Button)view.findViewById(R.id.buttonShow);
        Button button =(Button)view.findViewById(R.id.button_sc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonSHow.getVisibility()!=View.GONE){
                    buttonSHow.setVisibility(View.GONE);
                }else{
                    buttonSHow.setVisibility(View.VISIBLE);
                }
            }
        });
        final LinearLayout layout =(LinearLayout)view.findViewById(R.id.myScroll_son);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightOff=layout.getRootView().getHeight()-layout.getHeight();
                LogUtil.i("MYTAG----heightOff:"+heightOff+"---layout.getRootView().getHeight():");

                if(heightOff>0){
                }
            }
        });
        return view;
    }

}
