package com.virgil.aft.business.view;

import android.app.ActivityManager;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.widget.PayRefundWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwujing on 14-7-15.
 */
public class Fragment2 extends BasicFragment {
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view=inflater.inflate(R.layout.fragment2, null);
        int size=1;
        ArrayList<Button> btList=new ArrayList<Button>();
        while (btList.size()<size){
            Button button = new Button(context);
            btList.add(button);
            button.setText("Button"+btList.size());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayRefundWidget.initRefundWidget(getActivity(), "", null);
                }
            });

        }
        Button fragment2_button=(Button)view.findViewById(R.id.fragment2_button);
        fragment2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect rectgle= new Rect();
                Window window= getActivity().getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
                int hight=rectgle.bottom-rectgle.top;
                PayRefundWidget.initRefundWidget(getActivity(), "", null);

            }
        });
        Spinner sp=(Spinner)view.findViewById(R.id.idlistSpinner);
        List<String> idList=new ArrayList<String>();
        idList.add("liuwj");
        idList.add("ywyao");
        idList.add("dtliu");
        idList.add("qyi");
//        SpinnerAdapter spinnerAdapter=new SpinnerAdapter() {
//            List<String> mIdList;
//            public void setIdList(List<String> idList){
//                mIdList=idList;
//            }
//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                if(convertView==null){
//
//                }else{
//                    return convertView;
//                }
//                return null;
//            }
//
//            @Override
//            public void registerDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public void unregisterDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public int getCount() {
//                return 0;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public boolean hasStableIds() {
//                return false;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return null;
//            }
//
//            @Override
//            public int getItemViewType(int position) {
//                return 0;
//            }
//
//            @Override
//            public int getViewTypeCount() {
//                return 0;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//        };
//        sp.setAdapter(new ArrayAdapter<String>(getActivity(),0,idList));
        return view;
    }
    private class SpinnerItem extends LinearLayout{
        public SpinnerItem(Context context) {
            super(context);
            initView(context);
        }
        private void initView(Context context){
            setOrientation(HORIZONTAL);
            EditText editText=new EditText((context));
            addView(editText);

        }
    }
    private void getTopActivity(Context context){
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getRunningTasks(1);
    }
}
