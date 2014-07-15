package com.virgil.androidfunctiontest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.virgil.androidfunctiontest.framework.BasicFragment;
import com.virgil.androidfunctiontest.widget.InnerScrollView;

import java.util.ArrayList;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment_Calender extends BasicFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_calendar,container,false);
        ScrollView parentScroll=(ScrollView)view.findViewById(R.id.scroll_parent);
        InnerScrollView childScroll2=(InnerScrollView)view.findViewById(R.id.scroll_son2);
        InnerScrollView childScroll=(InnerScrollView)view.findViewById(R.id.scroll_son1);
        childScroll2.parentScrollView=parentScroll;
        childScroll.parentScrollView=parentScroll;
        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.drag_me);
        final ArrayList<Button> buttonList=new ArrayList<Button>();
        while (buttonList.size()<6){
            Button button=new Button(getActivity());
            buttonList.add(button);
            button.setWidth(200);
            button.setHeight(80);
            button.setText("待隐藏" + buttonList.size());
            button.setVisibility(View.VISIBLE);
            linearLayout.addView(button);
        }
        return view;
    }

}
