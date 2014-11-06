package com.virgil.aft.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.widget.Panel2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liuwujing on 14-7-15.
 */
public class Fragment2 extends BasicFragment {
    public Panel2 panel2;
    public LinearLayout container;
    public GridView gridview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity();
        View view=inflater.inflate(R.layout.fragment2,null);
        gridview = (GridView) view.findViewById(R.id.gridview);
        container = (LinearLayout) view.findViewById(R.id.container);
        panel2 = new Panel2(getActivity(), gridview, LinearLayout.LayoutParams.MATCH_PARENT, 400);
        LinearLayout li = (LinearLayout) container.findViewById(R.id.content);
        li.addView(panel2);//加入Panel控件

        //新建测试组件
        TextView tvTest = new TextView(getActivity());
        tvTest.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tvTest.setText("测试文字");
        tvTest.setTextColor(Color.RED);
        tvTest.setBackgroundColor(Color.WHITE);
        //加入到Panel里面
        panel2.fillPanelContainer(tvTest);

        //往GridView填充测试数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.ic_launcher);
            map.put("ItemText", "NO." + String.valueOf(i));
            lstImageItem.add(map);
        }

        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,
                R.layout.item,
                new String[]{"ItemImage", "ItemText"},
                new int[]{R.id.ItemImage, R.id.ItemText});
        gridview.setAdapter(saImageItems);
        gridview.setOnItemClickListener(new ItemClickListener());
        return view;
    }


    class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> item = (HashMap<String, Object>) arg0
                    .getItemAtPosition(arg2);
            getActivity().setTitle((String) item.get("ItemText"));
        }

    }
}
