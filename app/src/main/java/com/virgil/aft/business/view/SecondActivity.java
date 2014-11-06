package com.virgil.aft.business.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicActivity;

import java.util.ArrayList;

/**
 * Created by liuwj on 2014/6/18.
 */
public class SecondActivity extends BasicActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View view=getLayoutInflater().inflate(R.layout.second_layout,null);
        super.onCreate(savedInstanceState);
        final Button button1=new Button(this);
//        button1.setWidth(DensityUtil.DIPToSP(100));
//        button1.setHeight(DensityUtil.DIPToSP(100));
        button1.setWidth(200);
        button1.setHeight(200);
        button1.setText("TextAdd");
        LinearLayout layout=(LinearLayout)view.findViewById(R.id.second_root);
        layout.addView(button1);
        final ArrayList<Button> buttonList=new ArrayList<Button>();
        while (buttonList.size()<4){
            Button button=new Button(this);
            buttonList.add(button);
            button.setWidth(200);
            button.setHeight(200);
            button.setText("TextAdd" + buttonList.size());
            button.setVisibility(View.GONE);
            layout.addView(button);
        }
        buttonList.get(2).setVisibility(View.VISIBLE);
//        buttonList.get(2).
        buttonList.get(3).setVisibility(View.VISIBLE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation translateAnimation=new TranslateAnimation(0,0,-80,0);
                translateAnimation.setDuration(500);
                translateAnimation.setStartOffset(50);
                buttonList.get(1).startAnimation(translateAnimation);
                buttonList.get(1).setVisibility(View.VISIBLE);
            }
        });
        setContentView(view);

    }
    public static void initFragment(FragmentManager fragmentManager,Fragment targetFragment,String tag,int postion){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(postion, targetFragment, tag);
        transaction.commitAllowingStateLoss();
    }
}
