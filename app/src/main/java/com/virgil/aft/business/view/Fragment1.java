package com.virgil.aft.business.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.util.LogUtil;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment1 extends BasicFragment{

    private EditText editText;
    private EditText editText2;
    private int currentyeah=14;
    private String changeContent="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.mytest,null);
        editText=(EditText)view.findViewById(R.id.test_keyboard);
        editText2=(EditText)view.findViewById(R.id.test_keyboard2);
        final String tmp="0";

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtil.i("MYTTAG:beforeTextChanged--"+"s:"+s+";start"+start+";count:"+count+";after:"+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.i("MYTTAG:onTextChanged--"+"s:"+s+";start"+start+";count:"+count+";before:"+before);
            }

            @Override
            public void afterTextChanged(Editable s){
                String content=s.toString();
                if(content!=null){
                    if(content.length()==2){
                        if(content.startsWith("0")&&content.endsWith("0")){
                            editText2.setText("0");
                            editText2.setSelection(1);
                            editText2.invalidate();
                        }else if(content.startsWith("1")&&!content.endsWith("0")&&!content.endsWith("1")&&!content.endsWith("2")){
                            editText2.setText("0");
                            editText2.setSelection(1);
                            editText2.invalidate();
                        }
                    }else if(content.length()==4){
                        int a=(int)content.charAt(3);
                        if(!(a*10>currentyeah&&a*10<(currentyeah+25))){
                            editText2.setText(content.substring(0,3));
                            editText2.setSelection(3);
                            editText2.invalidate();
                        }
                    }else if(content.length()==5){
                        int a=(int)content.charAt(3);
                        if(!(a*10>currentyeah&&a*10<(currentyeah+25))){
                            editText2.setText(content.substring(0,4));
                            editText2.setSelection(4);
                            editText2.invalidate();
                        }
                    }
                }
               }
        });
        view.findViewById(R.id.button_keybo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("A");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            int beforeLength=0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeLength=editText.getText().length();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = editText.getText().toString().length();
                try {
                    //输入框从0开始输入，如果第一个数字大于1，则在前面加“0”
                    if (beforeLength == 0 && length == 1 && start == 0 && s.length() == 1 && Integer.parseInt(s + "") >1) {
                        editText.removeTextChangedListener(this);
                        editText.setText("0" + s + "/");
                        editText.setSelection(3);
                        editText.addTextChangedListener(this);
                    }else{
                        if(length==2&&start==1&&count==1){
                            editText.removeTextChangedListener(this);
                            editText.setText( s + "/");
                            editText.setSelection(3);
                            editText.addTextChangedListener(this);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String content=s.toString();
                changeContent=null;
                if(content!=null){
                    if(content.length()==2){
                        if(content.startsWith("0")&&content.endsWith("0")){
                            changeContent=content.substring(0,1);
                        }else if(content.startsWith("1")&&!content.endsWith("0")&&!content.endsWith("1")&&!content.endsWith("2")){
                            changeContent=content.substring(0,1);
                        }
                    }else if(content.length()==4){
                        int a=Integer.valueOf(content.substring(3));
                        if(!((a)>=currentyeah/10&&a<=(currentyeah+25)/10)){
                            changeContent=content.substring(0,3);
                        }
                    }else if(content.length()==5){
                        int a=Integer.valueOf(content.substring(3));
                        if(!(a>=currentyeah&&a<=(currentyeah+25))){
                            changeContent=content.substring(0,4);
                        }
                    }
                }
                if(changeContent!=null){
                    editText.removeTextChangedListener(this);
                    editText.setText(changeContent);
                    editText.setSelection(changeContent.length());
                    editText.invalidate();
                    editText.addTextChangedListener(this);
                }
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                LogUtil.i("MYTTAG:keyKcode--keyCode:"+event.keyCodeToString(keyCode)+";action");
                if(keyCode==KeyEvent.KEYCODE_DEL&&event.getAction()==KeyEvent.ACTION_DOWN){
                    if(editText.getText().length()==3){
                        editText.setText(editText.getText().subSequence(0,1));
                        editText.setSelection(1);
                        return true;
                    }
                }

                return false;
            }
        });
        return view;
    }

}
