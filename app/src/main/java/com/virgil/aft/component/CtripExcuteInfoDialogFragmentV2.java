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
import com.virgil.aft.util.StringUtil;

public class CtripExcuteInfoDialogFragmentV2 extends CtripBaseDialogFragmentV2 {

	private TextView mDlgContent, mBtnLeft , mDlgTitle, mRightBtn;
	private OnClickListener mExcuitePositiveListener, mExcuiteNegativeListener;
	private String mOldTag;
	private CtripDialogType mOldDialogTag;
	public static CtripExcuteInfoDialogFragmentV2 getInstance(Bundle bundle) {
		CtripExcuteInfoDialogFragmentV2 baseDialogFragment = new CtripExcuteInfoDialogFragmentV2();
		baseDialogFragment.setArguments(bundle);
		return baseDialogFragment;
	}

	public CtripExcuteInfoDialogFragmentV2() {

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
				mOldTag = ctripDialogExchangeModel.getOldTag();
				mOldDialogTag = ctripDialogExchangeModel.getOldCtripDialogType();
				mTitleTxt = ctripDialogExchangeModel.getDialogTitle();
				mPositiveBtnTxt = ctripDialogExchangeModel.getPostiveText();
				mNegativeBtnTxt = ctripDialogExchangeModel.getNegativeText();
				mContentTxt = ctripDialogExchangeModel.getDialogContext();
				gravity = ctripDialogExchangeModel.getGravity();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_excute_layout, container, false);
		view.setOnClickListener(mSpaceClickListener);
		 mDlgTitle = (TextView) view.findViewById(R.id.titel_text);
		mDlgContent = (TextView) view.findViewById(R.id.content_text);
		if (!TextUtils.isEmpty(mContentTxt)) {
			mDlgContent.setText(mContentTxt);
			if (gravity != -1) {
				mDlgContent.setGravity(gravity);
			}
		}
		mBtnLeft = (TextView) view.findViewById(R.id.lef_btn);
		mRightBtn = (TextView) view.findViewById(R.id.right_btn);

		if (!TextUtils.isEmpty(mTitleTxt) && mHasTitle) {
	         mDlgTitle.setVisibility(View.VISIBLE);
	         mDlgTitle.setText(mTitleTxt);
	         //mDlgTitle.getPaint().setFakeBoldText(true);
	         //mRightBtn.getPaint().setFakeBoldText(true);
//	         int paddingTop = DeviceUtil.getPixelFromDip(8);
//	         mDlgContent.setMinHeight(DeviceUtil.getPixelFromDip(40));
//	         mDlgContent.setPadding(mDlgContent.getPaddingLeft(),
//	                 paddingTop, mDlgContent.getPaddingRight(), mDlgContent.getPaddingBottom());
//	         mDlgContent.requestLayout();
	         }
		mExcuitePositiveListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// CtripDialogFragmentCallBack callBack =
				// getCtripDialogFragmentCallBack();
				Fragment tarFragment = getTargetFragment();
				Activity activity = getActivity();
                try {
                    if (positiveClickCallBack != null) {
                        positiveClickCallBack.callBack();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
				if (mDialogTag.equals(CtripDialogManager.CONTENT_HAS_NUMBERINFO)) {
					dismissSelf();

//					else if (getActivity() != null && getActivity() instanceof CtripBaseActivity) {
//						((CtripBaseActivity) getActivity()).goCall("4000086666");
//					}

				} else if (mDialogTag.equals(CtripDialogManager.VOIP_CHECK_COMMON_DIALOG)) {
					if (getTargetFragment() != null && getTargetFragment() instanceof CtripExcuteDialogFragmentCallBack) {
						((CtripExcuteDialogFragmentCallBack) getTargetFragment()).onPositiveBtnClick(mDialogTag);
					} else if (activity != null && activity instanceof CtripExcuteDialogFragmentCallBack) {
						((CtripExcuteDialogFragmentCallBack) activity).onPositiveBtnClick(mDialogTag);
					}
					dismissSelf();
				} else if (mDialogTag.equals(CtripDialogManager.VOIP_CHECK_ERROR_DIALOG)) {

//					else if (getActivity() != null && getActivity() instanceof CtripBaseActivity) {
//						((CtripBaseActivity) getActivity()).goCall();
//					}
                    // wanglaian解耦: CtripBaseMapActivity是CtripBaseActivityV2的子类，所以此处不需要了。
//					else if (getActivity() != null && getActivity() instanceof CtripBaseMapActivity) {
//						CtripCallManager.goCall(ChannelManageUtil.getTelNumberStr(), true, (CtripBaseMapActivity)getActivity());
//					}
					dismissSelf();
				} 
//				else if (mDialogTag.equals(CtripDialogManager.COMMON_BUSSINESS_ERROR_DIALOG_WITH_CALL) || mDialogTag.equals(CtripDialogManager.COMMON_BUSSINESS_ERROR_DIALOG)) {
//					dismissSelf();
//					if (compatibilityPositiveListener != null) {
//						compatibilityPositiveListener.onClick(v);
//					}
//				}
				else {
					dismissSelf();
					if (tarFragment != null && tarFragment instanceof CtripExcuteDialogFragmentCallBack) {
						((CtripExcuteDialogFragmentCallBack) tarFragment).onPositiveBtnClick(mDialogTag);
					} else if (activity != null && activity instanceof CtripExcuteDialogFragmentCallBack) {
						((CtripExcuteDialogFragmentCallBack) activity).onPositiveBtnClick(mDialogTag);
					}
					// if(callBack!=null){
					// callBack.onPositiveBtnClick(mDialogTag);
					// }
				}
			}
		};

		mExcuiteNegativeListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
                    try {
                        if (negativeClickCallBack != null) {
                            negativeClickCallBack.callBack();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mDialogTag.equals(CtripDialogManager.CONTENT_HAS_NUMBERINFO)) {
					Fragment tarFragment = getTargetFragment();
					Activity activity = getActivity();
					dismissSelf();
					if (mOldDialogTag == CtripDialogType.SINGLE) {
                        try {
                            if (singleClickCallBack != null) {
                                singleClickCallBack.callBack();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
						if (tarFragment != null && tarFragment instanceof CtripSingleDialogFragmentCallBack) {
							((CtripSingleDialogFragmentCallBack) tarFragment).onSingleBtnClick(mOldTag);
						} else if (activity != null && activity instanceof CtripSingleDialogFragmentCallBack) {
							((CtripSingleDialogFragmentCallBack) activity).onSingleBtnClick(mOldTag);
						}
					} else if (mOldDialogTag == CtripDialogType.EXCUTE) {
						if (tarFragment != null && tarFragment instanceof CtripExcuteDialogFragmentCallBack) {
							((CtripExcuteDialogFragmentCallBack) tarFragment).onNegtiveBtnClick(mOldTag);
						} else if (activity != null && activity instanceof CtripExcuteDialogFragmentCallBack) {
							((CtripExcuteDialogFragmentCallBack) activity).onNegtiveBtnClick(mOldTag);
						}
					}
				}
//				else if (mDialogTag.equals(CtripDialogManager.COMMON_BUSSINESS_ERROR_DIALOG_WITH_CALL)) {
//					dismissSelf();
//					if (compatibilityNegativeListener != null) {
//						compatibilityNegativeListener.onClick(v);
//					}
//				}
				else {
					if (mDialogTag.equals(CtripDialogManager.VOIP_CHECK_COMMON_DIALOG)) {
					}
					Fragment tarFragment = getTargetFragment();
					Activity activity = getActivity();
					dismissSelf();
					if (tarFragment != null && tarFragment instanceof CtripExcuteDialogFragmentCallBack) {
						((CtripExcuteDialogFragmentCallBack) tarFragment).onNegtiveBtnClick(mDialogTag);
					} else if (activity != null && activity instanceof CtripExcuteDialogFragmentCallBack) {
						((CtripExcuteDialogFragmentCallBack) activity).onNegtiveBtnClick(mDialogTag);
					}
				}
			}
		};

		if (android.os.Build.VERSION.SDK_INT >= 14) {
			if (!TextUtils.isEmpty(mPositiveBtnTxt)) {
				mRightBtn.setText(mPositiveBtnTxt);
			} else {
				mRightBtn.setText(R.string.ok);
			}
			mRightBtn.setOnClickListener(mExcuitePositiveListener);
			mRightBtn.setBackgroundResource(R.drawable.btn_dialog_selector);
			// mRightBtn.setShadowLayer(1, 0, 1, Color.parseColor("#FF79351b"));
			// mRightBtn.setTextAppearance(getActivity(),
			// R.style.text_15_ffffff_sdw);

			if (!TextUtils.isEmpty(mNegativeBtnTxt)) {
				mBtnLeft.setText(mNegativeBtnTxt);
			} else {
				mBtnLeft.setText(R.string.cancel);
			}
			mBtnLeft.setOnClickListener(mExcuiteNegativeListener);
			mBtnLeft.setBackgroundResource(R.drawable.btn_dialog_selector);
			// mBtnLeft.setShadowLayer(1, 0, 1, Color.WHITE);
			// mBtnLeft.setTextAppearance(getActivity(),
			// R.style.text_15_666666_sdw);
		} else {
			if (!TextUtils.isEmpty(mPositiveBtnTxt)) {
				mBtnLeft.setText(mPositiveBtnTxt);
			} else {
				mBtnLeft.setText(R.string.ok);
			}
			mBtnLeft.setOnClickListener(mExcuitePositiveListener);
			mBtnLeft.setBackgroundResource(R.drawable.btn_dialog_selector);
			// mBtnLeft.setShadowLayer(1, 0, 1, Color.parseColor("#FF79351b"));
			// mBtnLeft.setTextAppearance(getActivity(),
			// R.style.text_15_ffffff_sdw);

			if (!TextUtils.isEmpty(mNegativeBtnTxt)) {
				mRightBtn.setText(mNegativeBtnTxt);
			} else {
				mRightBtn.setText(R.string.cancel);
			}
			mRightBtn.setOnClickListener(mExcuiteNegativeListener);
			mRightBtn.setBackgroundResource(R.drawable.btn_dialog_selector);
			// mRightBtn.setShadowLayer(1, 0, 1, Color.WHITE);
			// mRightBtn.setTextAppearance(getActivity(),
			// R.style.text_15_666666_sdw);
		}
		// ColorDrawable colorDrawable = new
		// ColorDrawable(getResources().getColor(R.color.background_dialog));
		// view.setBackgroundDrawable(colorDrawable);
		return view;
	}

}
