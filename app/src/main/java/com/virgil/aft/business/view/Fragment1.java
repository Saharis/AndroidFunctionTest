package com.virgil.aft.business.view;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.virgil.aft.R;
import com.virgil.aft.core.ApplicationCache;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.util.CommUtil;
import com.virgil.aft.util.LogUtil;

/**
 * Created by liuwj on 2014/6/19.
 */
public class Fragment1 extends BasicFragment {

    private EditText editText;
    private EditText editText2;
    private int currentyeah = 14;
    private String changeContent = "";
    SpannableString str=new SpannableString("开始服务");
    TextAppearanceSpan taSpan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater);
    }

    private View initView(LayoutInflater inflater) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.mytest, null);
        view.setOrientation(LinearLayout.VERTICAL);
        final Context context = getActivity();
        taSpan=new TextAppearanceSpan(context,R.style.text_14_666666_sdw);
        final Button bt_ServiceStart = new Button(context);
        bt_ServiceStart.setText(str);
        bt_ServiceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommUtil.showToast("service send stop");
                try {
                    ApplicationCache.getInstance().getiCountSer().setCon("stop");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Button bt_ServiceStop = new Button(context);
        bt_ServiceStop.setText("结束服务");
        bt_ServiceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommUtil.showToast("service send start");
                str.setSpan(new TextAppearanceSpan(context,R.style.text_14_ff0000),0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                bt_ServiceStart.setText(str);                                                                           try {
                    ApplicationCache.getInstance().getiCountSer().setCon("start");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


        Button bt_Other = new Button(context);
        bt_Other.setText("Other");
        bt_Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str.setSpan(taSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                bt_ServiceStart.setText(str);
                ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
                StringBuilder sb=new StringBuilder();
                for(ActivityManager.RunningAppProcessInfo process:processList){
                    sb.delete(0,sb.capacity());
                    sb.append("process.processName=" + process.processName + " | ");
                    sb.append("process.pid=" + process.pid + " | ");
                    sb.append("process.uid="+process.uid+" | ");
//                    Arrays.asList(process.pkgList);
                    sb.append("process.pkgList="+Arrays.asList(process.pkgList).toString()+" | ");
//                    PrintStream
                    sb.append("process.importance="+process.importance+" | ");
                    sb.append("process.lru="+process.lru+" | ");

                    boolean a=TextUtils.equals("","");
                    LogUtil.i("TAG_PROCESS | "+sb              );
                }
                CommUtil.showLogInToast("ss");
            }
        });
        Button bt_Toast=new Button(context);
        bt_Toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable spannable=null;
                try {
                    spannable=str;
                    Class<?> mClass=spannable.getClass().getSuperclass();
                    Field filed=mClass.getDeclaredField("mSpanCount");
                    filed.setAccessible(true);
                    int mSpans=(int)filed.get(spannable);
                    if(mSpans>0){
                        spannable.removeSpan(taSpan);
                    }else{
                        spannable.setSpan(taSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                bt_ServiceStart.setText(spannable);

                CommUtil.showToast("Application.getFilesDir()=" + ApplicationCache.getInstance().getApplication().getFilesDir().getAbsolutePath());
            }
        });
        bt_Toast.setText("Test");
        view.addView(bt_Other);

        view.addView(bt_ServiceStart);
        view.addView(bt_ServiceStop);
        view.addView(bt_Toast);
        return view;
    }

}
