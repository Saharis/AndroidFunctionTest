package com.virgil.aft.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
public class CtripCustomerDialogFragmentV2 extends CtripBaseDialogFragmentV2 {
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
        } else {
            if (getTargetFragment() != null && getTargetFragment() instanceof CtripCustomerFragmentCallBack) {
                mCtripContentView = ((CtripCustomerFragmentCallBack) getTargetFragment()).getCustomerView(mDialogTag);
            } else if (getActivity() != null && getActivity() instanceof CtripCustomerFragmentCallBack) {
                mCtripContentView = ((CtripCustomerFragmentCallBack) getActivity()).getCustomerView(mDialogTag);
            }
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
