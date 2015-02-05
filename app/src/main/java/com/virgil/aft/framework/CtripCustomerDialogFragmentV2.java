package com.virgil.aft.framework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.virgil.aft.component.CtripDialogExchangeModel;

/**
 * Created by liuwujing on 15/2/4.
 */
public class CtripCustomerDialogFragmentV2 extends CtripBaseDialogFragmentV2{
    public View customView;
    public static CtripCustomerDialogFragmentV2 getInstance(Bundle bundle){
        CtripCustomerDialogFragmentV2 baseDialogFragment = new CtripCustomerDialogFragmentV2();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }
    public CtripCustomerDialogFragmentV2(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            CtripDialogExchangeModel ctripDialogExchangeModel = ((CtripDialogExchangeModel.CtripDialogExchangeModelBuilder) bundle.getSerializable(TAG)).creat();
            if (ctripDialogExchangeModel != null) {
                mDialogTag = ctripDialogExchangeModel.getTag();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mCtripContentView = null;
        if (customView != null) {
            mCtripContentView = customView;
        }
        FrameLayout layout = new FrameLayout(getActivity());
        layout.setClickable(true);
        layout.setOnClickListener(mSpaceClickListener);
        if(mCtripContentView!=null&&mCtripContentView.getLayoutParams()!=null){
            if (null == mCtripContentView.getParent()) {
                layout.addView(mCtripContentView, mCtripContentView.getLayoutParams());
            }
            mCtripContentView.setClickable(true);
        }
        return layout;
    }
}
