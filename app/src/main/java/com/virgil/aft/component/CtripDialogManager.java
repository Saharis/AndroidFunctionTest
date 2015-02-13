package com.virgil.aft.component;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;

import android.provider.Settings;
import android.content.Intent;

import com.virgil.aft.framework.BasicActivity;

public class CtripDialogManager {
	public final static int DIALOG_REQUEST_CODE = 0x2001;
	/**
	 * 文案中存在电话号码，转换的弹框
	 */
	public static final String CONTENT_HAS_NUMBERINFO = "content has number";
	/**
	 * Voip确认弹框
	 */
	public static final String VOIP_CHECK_COMMON_DIALOG = "voip common dialog";
	/**
	 * Voip校验错误弹框
	 */
	public static final String VOIP_CHECK_ERROR_DIALOG = "voip error dialog";
	/**
	 * 普遍错误弹框
	 */
	public static final String COMMON_BUSSINESS_ERROR_DIALOG = "error dialog";
	/**
	 * 普遍网络中断弹框
	 */
	public static final String COMMON_BUSSINESS_ERROR_DIALOG_WITH_CALL = "error dialog with call";
    /**
     * 普遍无网络弹框
     */
    public static final String COMMON_NO_NETWORK_DIALOG = "error dialog no network";
	/**
	 * 弹框方法
	 * 
	 * fragment与baseActivityV2不可同时为NULL
	 * 
	 * @param fragmentManager
	 *            (必传字段)
	 * @param ctripDialogExchangeModel
	 *            (必传字段)
	 * @param fragment
	 *            (选传)
	 * @param baseActivityV2
	 *            (选传)
	 * @return CtripBaseDialogFragmentV2对象
	 */
	public static CtripBaseDialogFragmentV2 showDialogFragment(FragmentManager fragmentManager, CtripDialogExchangeModel ctripDialogExchangeModel, Fragment fragment, BasicActivity baseActivityV2) {
		return showDialogFragment(fragmentManager,ctripDialogExchangeModel,null,fragment,baseActivityV2);
	}

    /**
     * 弹框方法
     *
     * fragment与baseActivityV2不可同时为NULL
     * *******************
     * CtripDialogCallBackContainer,添加这个参数是因为，目前DialogFagment将CtripDialogExchangeModel通过SetAguments的形式保存为Fragment的私有变量mArguments。
     * 而mArguments会在onSavedInstance被序列化，如果其中的几个ExcuteCallBack和CustomView被赋值，则会导致序列化失败，直接Crash掉。
     * 所以这几个callBack和view不能通过CtripDialogExchangeModel来赋值，或者说，不能赋值给会被序列化的类。
     * liuwj 2015-02-10
     * *******************
     * @param fragmentManager  FragmentManager
     * @param ctripDialogExchangeModel CtripDialogExchangeModel
     * @param dialogCallBackContainer CtripDialogCallBackContainer|一些CallBack可以通过这个容器传递
     * @param fragment Fragment
     * @param baseActivityV2 CtripBaseActivityV2
     * @return CtripBaseDialogFragmentV2
     */
    public static CtripBaseDialogFragmentV2 showDialogFragment(FragmentManager fragmentManager, CtripDialogExchangeModel ctripDialogExchangeModel,CtripDialogCallBackContainer dialogCallBackContainer,Fragment fragment, BasicActivity baseActivityV2) {

        CtripBaseDialogFragmentV2 baseDialogFragment = null;
        if (ctripDialogExchangeModel != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("CtripHDBaseDialogFragment", ctripDialogExchangeModel.ctripDialogExchangeModelBuilder);
            CtripDialogType ctripHDDialogType = ctripDialogExchangeModel.getDialogType();
            String contextStr = ctripDialogExchangeModel.getDialogContext();
            if (null != contextStr && (contextStr.contains("4000086666") || contextStr.contains("400-008-6666"))) {
                ctripDialogExchangeModel.ctripDialogExchangeModelBuilder.setOldCtripDialogType(ctripHDDialogType);
                ctripHDDialogType = CtripDialogType.EXCUTE;
                ctripDialogExchangeModel.ctripDialogExchangeModelBuilder.setDialogType(CtripDialogType.EXCUTE);
                ctripDialogExchangeModel.ctripDialogExchangeModelBuilder.setPostiveText("呼叫").setNegativeText("知道了");
                ctripDialogExchangeModel.ctripDialogExchangeModelBuilder.setOldTag(ctripDialogExchangeModel.getTag());
                ctripDialogExchangeModel.ctripDialogExchangeModelBuilder.setTag(CtripDialogManager.CONTENT_HAS_NUMBERINFO);
            }
            if (ctripHDDialogType == CtripDialogType.SINGLE) {
                baseDialogFragment = CtripSingleInfoDialogFragmentV2.getInstance(bundle);
            } else if (ctripHDDialogType == CtripDialogType.EXCUTE) {
                baseDialogFragment = CtripExcuteInfoDialogFragmentV2.getInstance(bundle);
            } else if (ctripHDDialogType == CtripDialogType.CUSTOMER) {
                baseDialogFragment = CtripCustomerDialogFragmentV2.getInstance(bundle);
            } else if (ctripHDDialogType == CtripDialogType.PROGRESS) {
                baseDialogFragment = CtripProcessDialogFragmentV2.getInstance(bundle);
            }
        }
        if (baseDialogFragment != null) {
            baseDialogFragment.compatibilityListener = ctripDialogExchangeModel.compatibilityListener;
            baseDialogFragment.compatibilityNegativeListener = ctripDialogExchangeModel.compatibilityNegativeListener;
            baseDialogFragment.compatibilityPositiveListener = ctripDialogExchangeModel.compatibilityPositiveListener;
            if (dialogCallBackContainer != null) {
                baseDialogFragment.singleClickCallBack = dialogCallBackContainer.singleClickCallBack;
                baseDialogFragment.positiveClickCallBack = dialogCallBackContainer.positiveClickCallBack;
                baseDialogFragment.negativeClickCallBack = dialogCallBackContainer.negativeClickCallBack;
                baseDialogFragment.dismissCallBack = dialogCallBackContainer.dismissCallBack;
                if (baseDialogFragment instanceof CtripCustomerDialogFragmentV2) {
                    ((CtripCustomerDialogFragmentV2) baseDialogFragment).customView = dialogCallBackContainer.customView;
                }
            }
        }
        if (baseDialogFragment != null) {
            if (fragment != null) {
                baseDialogFragment.setTargetFragment(fragment, DIALOG_REQUEST_CODE);
            }

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(baseDialogFragment, ctripDialogExchangeModel.getTag());
            ft.commitAllowingStateLoss();
        }
        return baseDialogFragment;
    }



}
