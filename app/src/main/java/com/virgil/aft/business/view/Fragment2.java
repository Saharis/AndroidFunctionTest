package com.virgil.aft.business.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import com.virgil.aft.R;
import com.virgil.aft.core.ApplicationCache;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.util.DeviceUtil;
import com.virgil.aft.widget.PayRefundWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwujing on 14-7-15.
 */
public class Fragment2 extends BasicFragment {
    private Context context;
    private EditText ed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment2, null);
        int size = 1;
        ArrayList<Button> btList = new ArrayList<Button>();
        while (btList.size() < size) {
            Button button = new Button(context);
            btList.add(button);
            button.setText("Button" + btList.size());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayRefundWidget.initRefundWidget(getActivity(), "", null);
                }
            });

        }
        Button fragment2_button = (Button) view.findViewById(R.id.fragment2_button);
        fragment2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect rectgle = new Rect();
                Window window = getActivity().getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
                int hight = rectgle.bottom - rectgle.top;
                PayRefundWidget.initRefundWidget(getActivity(), "", null);

            }
        });
        Button fragment2_button2 = (Button) view.findViewById(R.id.fragment2_button2);
        fragment2_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(getActivity(), FourthActivity.class);
//        in2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(in2);

            }
        });
        ed = (EditText) view.findViewById(R.id.spin_edit);
        Spinner sp = (Spinner) view.findViewById(R.id.idlistSpinner);
        List<String> idList = new ArrayList<String>();
        idList.add("liuwj");
        idList.add("ywyao");
        idList.add("dtliu");
        idList.add("qyi");
        sp.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spiner_item, idList));
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String into = (String) parent.getItemAtPosition(position);
                ed.setText(into);
                ((TextView) view).setText("Selet");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void getTopActivity(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getRunningTasks(1);
    }
}
