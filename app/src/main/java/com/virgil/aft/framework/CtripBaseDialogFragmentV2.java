package com.virgil.aft.framework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.virgil.aft.R;
import com.virgil.aft.component.CtripDialogExchangeModel;
import com.virgil.aft.component.CtripSpaceAndCancelCallBack;
import com.virgil.aft.component.ExcuteCallback;
import com.virgil.aft.util.DateUtil;
import com.virgil.aft.util.DeviceUtil;
import com.virgil.aft.util.StringUtil;

/**
 * Created by liuwujing on 15/2/4.
 */
public class CtripBaseDialogFragmentV2 extends DialogFragment {
    public final static String TAG = "CtripHDBaseDialogFragment";
    protected String mDialogTag;// 标记
    protected String mTitleTxt;// 标题
    protected String mPositiveBtnTxt;// 确认操作
    protected String mNegativeBtnTxt;// 取消操作
    protected String mSingleBtnTxt;// 单个button文字
    protected String mContentTxt;// 内容
    public boolean bIsBackable;// 是否back取消
    public boolean bIsSpaceable;// 是否空白取消
    public View.OnClickListener compatibilityListener;// 错误弹框 按键点击事件
    public View.OnClickListener compatibilityPositiveListener, compatibilityNegativeListener;
    public int gravity = Gravity.CENTER;


    /**
     * 单选按钮的点击回调
     * added by liuwj 2014-11-27
     */
    public ExcuteCallback singleClickCallBack;
    /**
     * Postive按钮的点击回调
     * added by liuwj 2014-11-27
     */
    public ExcuteCallback positiveClickCallBack;
    /**
     * Negative按钮的点击回调
     * added by liuwj 2014-11-27
     */
    public ExcuteCallback negativeClickCallBack;
    protected View.OnClickListener mSpaceClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //CtripActionLogUtil.logCode("ClickCode");//45

            if (bIsSpaceable) {
                Fragment tarFragment = getTargetFragment();
                Activity activity = getActivity();
                dismissSelf();
                if (tarFragment != null && tarFragment instanceof CtripSpaceAndCancelCallBack) {
                    ((CtripSpaceAndCancelCallBack) tarFragment).onSpaceClick(mDialogTag);
                } else if (activity != null && activity instanceof CtripSpaceAndCancelCallBack) {
                    ((CtripSpaceAndCancelCallBack) activity).onSpaceClick(mDialogTag);
                }
            }
        }
    };

    public static CtripBaseDialogFragmentV2 getInstance(Bundle bundle) {
        CtripBaseDialogFragmentV2 baseDialogFragment = new CtripBaseDialogFragmentV2();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public CtripBaseDialogFragmentV2() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ThemeHolo);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            CtripDialogExchangeModel ctripDialogExchangeModel = ((CtripDialogExchangeModel.CtripDialogExchangeModelBuilder) bundle.getSerializable(TAG)).creat();
            if (ctripDialogExchangeModel != null) {
                mDialogTag = ctripDialogExchangeModel.getTag();
                bIsBackable = ctripDialogExchangeModel.isBackable();
                bIsSpaceable = ctripDialogExchangeModel.isSpaceable();
                mContentTxt = ctripDialogExchangeModel.getDialogContext();

                setCancelable(bIsBackable);
            }
        }
    }
    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return show(transaction, tag, true);
    }

    public int show(FragmentTransaction transaction, String tag, boolean allowStateLoss) {
        transaction.add(this, tag);
        int mBackStackId = allowStateLoss ? transaction.commitAllowingStateLoss() : transaction.commit();
        return mBackStackId;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // TODO Auto-generated method stub
        super.onCancel(dialog);
        Activity activity = getActivity();
        if (getTargetFragment() != null && getTargetFragment() instanceof CtripSpaceAndCancelCallBack) {
            ((CtripSpaceAndCancelCallBack) getTargetFragment()).onCanceled(mDialogTag);
        } else if (activity != null && activity instanceof CtripSpaceAndCancelCallBack) {
            ((CtripSpaceAndCancelCallBack) activity).onCanceled(mDialogTag);
        }
    }

    public void dismissSelf() {
        // TODO Auto-generated method stub
        // String tag = "";
        // tag=getTag();
        BaseFragmentManager.removeFragment(getFragmentManager(), this);
        // try {
        // if(tag!=null&&getFragmentManager()!=null&&getFragmentManager().findFragmentByTag(tag)!=null)
        // getFragmentManager().popBackStack(tag,
        // FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // // }
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
    }
}
