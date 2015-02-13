package com.virgil.aft.component;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.virgil.aft.R;
import com.virgil.aft.util.StringUtil;

/**
 * 实现显示加载进度框效果。
 * 
 */
public class CtripProcessDialogFragmentV2 extends CtripBaseDialogFragmentV2  {

	private TextView mDlgContent;
	private View mDlgButton;
	public String mBussinessCode;// 服务token
	private boolean bIsBussinessCancelable;// 服务是否可取消
	private RelativeLayout mMainLayout;
	/** 提示文本是否多行显示 */
	private boolean bIsSingleLine = true;
	private int iWidth;
	private int iHeight;

	public static CtripProcessDialogFragmentV2 getInstance(Bundle bundle) {
		CtripProcessDialogFragmentV2 baseDialogFragment = new CtripProcessDialogFragmentV2();
		baseDialogFragment.setArguments(bundle);
		return baseDialogFragment;
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
				mBussinessCode = ctripDialogExchangeModel.getTag();
				mContentTxt = ctripDialogExchangeModel.getDialogContext();
				bIsBussinessCancelable = ctripDialogExchangeModel.isBussinessCancleable();
				bIsSingleLine = ctripDialogExchangeModel.isSingleLine();
				iWidth = ctripDialogExchangeModel.getWidth();
				iHeight = ctripDialogExchangeModel.getHeight();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.process_load_data_layout_v2, container, false);
		layoutView.setOnClickListener(mSpaceClickListener);
		mMainLayout = (RelativeLayout) layoutView.findViewById(R.id.process_main_layout);
		mDlgContent = (TextView) layoutView.findViewById(R.id.tip);
		if (!TextUtils.isEmpty(mContentTxt)) {
			mDlgContent.setText(mContentTxt);
		}
		// 设置对话框大小
		if (iWidth != 0 && iHeight != 0) {
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iWidth, iHeight);
			layoutParams.gravity = Gravity.CENTER;
			mMainLayout.setLayoutParams(layoutParams);
		}
		// 设置是否单行显示
		mDlgContent.setSingleLine(bIsSingleLine);

		mDlgButton = layoutView.findViewById(R.id.btn_cancel);
		mDlgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// CtripActionLogUtil.logCode("ClickCode");//67
				Fragment tarFragment = getTargetFragment();
				Activity activity = getActivity();
				dismissSelf();
				if (tarFragment != null && tarFragment instanceof CtripSingleDialogFragmentCallBack) {
					((CtripSingleDialogFragmentCallBack) tarFragment).onSingleBtnClick(mDialogTag);
				} else if (activity != null && activity instanceof CtripSingleDialogFragmentCallBack) {
					((CtripSingleDialogFragmentCallBack) activity).onSingleBtnClick(mDialogTag);
				}
				// CtripDialogFragmentCallBack callBack =
				// getCtripDialogFragmentCallBack();
				// dismissSelf();
				// if(callBack!=null){
				// callBack.onSingleBtnClick(mDialogTag);
				// }
			}
		});
		if (!bIsBussinessCancelable) {
			mDlgButton.setVisibility(View.GONE);
		} else {
			mDlgButton.setVisibility(View.VISIBLE);
		}
		// ColorDrawable colorDrawable = new
		// ColorDrawable(getResources().getColor(R.color.background_dialog));
		// layoutView.setBackgroundDrawable(colorDrawable);
		return layoutView;
	}

	/**
	 * 方法描述 动态显示文本
	 * 
	 * @param content
	 * @author yechen @date 2014年7月16日 上午10:20:39
	 */
	public void setContentText(String content) {
		this.mContentTxt = content;
        if (mDlgContent != null) {
            mDlgContent.setText(this.mContentTxt);
        }
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void dismissSelf() {

		super.dismissSelf();
	}
}
