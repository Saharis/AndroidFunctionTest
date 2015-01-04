package com.virgil.aft.business.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment_Calender extends BasicFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater);
    }

    private View initView(LayoutInflater inflater) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.layout_calendar, null);
        Context context = getActivity();
        if (rootView != null) {
            EditText editText = new EditText(context);
            watchEditTextAsIDCardInput(editText);
            rootView.addView(editText);
        }
        return rootView;

    }

    private void watchEditTextAsIDCardInput(EditText param) {
        final EditText editText = param;
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {
                int mCount=-1;
                int mBefore=-1;
                int mStart=-1;
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    LogUtil.i("beforeTextChanged:editText=" + editText.getText().toString() + ";s=" + s + ";start=" + start + ";after=" + after + ";count=" + count);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCount=count;
                    mBefore=before;
                    mStart=start;
                    LogUtil.i("onTextChanged:editText=" + editText.getText().toString() + ";s=" + s + ";start=" + start + ";before=" + before + ";count=" + count);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    int length = s.length();

                    if(mCount>0){
                        editText.removeTextChangedListener(this);
                        editText.setText(formatIDCardInput(s, false));
                        if((mStart+1)==length){
                            editText.setSelection(editText.getEditableText().toString().length());
                        }else{
                            editText.setSelection(mStart+1);
                        }
                        editText.addTextChangedListener(this);
                    } else if (mBefore > 0) {
                        editText.removeTextChangedListener(this);
                        editText.setText(formatIDCardInput(s, true));
                        if(mStart==length){
                            editText.setSelection(editText.getEditableText().toString().length());
                        }else {
                            editText.setSelection(mStart);
                        }
                        editText.addTextChangedListener(this);
                    }
                    LogUtil.i("afterTextChanged:editText=" + editText.getText().toString() + ";s=" + s.toString());
                }
            });

        }
    }

    private String formatIDCardInput(CharSequence input, boolean delete) {
        StringBuilder outPut = new StringBuilder(input.toString().replace(" ", ""));
        if (!TextUtils.isEmpty(input)) {
            if (delete) {
                if (outPut.length() <= 6) {
                    return outPut.toString();
                }
                if (outPut.length() > 6) {
                    outPut.insert(6, " ");
                }
                if (outPut.length() > 15) {
                    outPut.insert(15, " ");
                }
            } else {
                if (outPut.length() >= 6) {
                    outPut.insert(6, " ");
                }
                if (outPut.length() >= 15) {
                    outPut.insert(15, " ");
                }
            }
        }
        return outPut.toString();
    }

}
