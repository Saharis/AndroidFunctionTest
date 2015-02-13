package com.virgil.aft.component;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.virgil.aft.R;

public class CtripSingleInfoDialogFragmentV2 extends CtripBaseDialogFragmentV2 {

	private TextView mDlgContent, mDlgButton/* , mDlgTitle */;
	public static CtripSingleInfoDialogFragmentV2 getInstance(Bundle bundle){
		CtripSingleInfoDialogFragmentV2 baseDialogFragment = new CtripSingleInfoDialogFragmentV2();
		baseDialogFragment.setArguments(bundle);
		return baseDialogFragment;
	}
	public CtripSingleInfoDialogFragmentV2(){
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			Bundle bundle = getArguments();
			CtripDialogExchangeModel ctripDialogExchangeModel = ((CtripDialogExchangeModel.CtripDialogExchangeModelBuilder) bundle.getSerializable(TAG)).creat();
			if(ctripDialogExchangeModel!=null){
				mDialogTag = ctripDialogExchangeModel.getTag();
				mTitleTxt = ctripDialogExchangeModel.getDialogTitle();
				mSingleBtnTxt = ctripDialogExchangeModel.getSingleText();
				mContentTxt = ctripDialogExchangeModel.getDialogContext();
				gravity = ctripDialogExchangeModel.getGravity();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.dialog_error_layout, container, false);
		contentView.setOnClickListener(mSpaceClickListener);

		// mDlgTitle = (TextView) contentView.findViewById(R.id.titel_text);
		// if (!TextUtils.isEmpty(mTitleTxt)) {
		// mDlgTitle.setVisibility(View.VISIBLE);
		// mDlgTitle.setText(mTitleTxt);
		// }
		mDlgContent = (TextView) contentView.findViewById(R.id.content_text);
		if (!TextUtils.isEmpty(mContentTxt)) {
			mDlgContent.setText(mContentTxt);
			mDlgContent.setGravity(gravity);
		}

		mDlgButton = (TextView) contentView.findViewById(R.id.single_btn);
		mDlgButton.setClickable(true);
		if (!TextUtils.isEmpty(mSingleBtnTxt)) {
			mDlgButton.setText(mSingleBtnTxt);
		}

		mDlgButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (CtripDialogManager.COMMON_BUSSINESS_ERROR_DIALOG_WITH_CALL.equals(mDialogTag) || CtripDialogManager.COMMON_BUSSINESS_ERROR_DIALOG.equals(mDialogTag)) {
//					dismissSelf();
//					if (compatibilityListener != null) {
//						compatibilityListener.onClick(v);
//					}
//				} else {
					Fragment tarFragment = getTargetFragment();
					Activity activity = getActivity();
					dismissSelf();
                //added by liuwj 2014-11-27
                try {
                    if (singleClickCallBack != null) {
                        singleClickCallBack.callBack();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
					if (tarFragment != null && tarFragment instanceof CtripSingleDialogFragmentCallBack) {
						((CtripSingleDialogFragmentCallBack) tarFragment).onSingleBtnClick(mDialogTag);
					} else if (activity != null && activity instanceof CtripSingleDialogFragmentCallBack) {
						((CtripSingleDialogFragmentCallBack) activity).onSingleBtnClick(mDialogTag);
					}
//				}
				// CtripDialogFragmentCallBack callBack =
				// getCtripDialogFragmentCallBack();
				// dismissSelf();
				// if(callBack!=null){
				// callBack.onSingleBtnClick(mDialogTag);
				// }
			}
		});
//		ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.background_dialog));
//		contentView.setBackgroundDrawable(colorDrawable);
		return contentView;
	}
}
