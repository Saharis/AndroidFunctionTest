package com.virgil.androidfunctiontest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.virgil.androidfunctiontest.framework.BasicFragment;
import com.virgil.androidfunctiontest.widget.InnerScrollView;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment_Calender extends BasicFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_calendar,container,false);
        ScrollView parentScroll=(ScrollView)view.findViewById(R.id.scroll_parent);
        InnerScrollView childScroll2=(InnerScrollView)view.findViewById(R.id.scroll_son2);
        childScroll2.parentScrollView=parentScroll;

        return view;
    }

}
