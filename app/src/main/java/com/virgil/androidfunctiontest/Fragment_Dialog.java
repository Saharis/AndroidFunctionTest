package com.virgil.androidfunctiontest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by virgil on 6/20/14.
 */
public class Fragment_Dialog extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dialog_fragment,container,false);
        return view;
    }

    public void setContent_text(String content_text) {
//        TextView textView=(TextView)findViewById(R.id.dialog_text);
//        textView.setText(content_text);
        setContent_text(content_text);
    }
}
