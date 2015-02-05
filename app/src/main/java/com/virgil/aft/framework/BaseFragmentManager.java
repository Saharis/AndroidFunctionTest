package com.virgil.aft.framework;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.virgil.aft.component.CtripDialogExchangeModel;
import com.virgil.aft.component.CtripDialogType;

/**
 * Created by liuwujing on 15/2/4.
 */
public class BaseFragmentManager {
    public static void removeFragment(FragmentManager fragmentManager, Fragment targetFragment) {
        // TODO Auto-generated method stub
        if(fragmentManager!=null){
            String tag = targetFragment.getTag();
            try {
                try {
                    if (fragmentManager != null && fragmentManager.findFragmentByTag(tag) != null) {
                        fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                } catch (Exception e) {
                }
                FragmentTransaction localFragmentTransaction = fragmentManager.beginTransaction();
                localFragmentTransaction.remove(targetFragment);
                localFragmentTransaction.commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();

                Fragment fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment != null) {
                    // FragmentTransaction localFragmentTransaction = fragmentManager.beginTransaction();
                    localFragmentTransaction.remove(fragment);
                    localFragmentTransaction.commitAllowingStateLoss();
                    fragmentManager.executePendingTransactions();
                }
            } catch (Exception e) {
            }
        }
    }


    public static CtripBaseDialogFragmentV2 showDialogFragment(FragmentManager fragmentManager, CtripDialogExchangeModel ctripDialogExchangeModel, Fragment fragment, Activity baseActivityV2) {

        CtripBaseDialogFragmentV2 baseDialogFragment = null;
        if (ctripDialogExchangeModel != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("CtripHDBaseDialogFragment", ctripDialogExchangeModel.ctripDialogExchangeModelBuilder);
            CtripDialogType ctripHDDialogType = ctripDialogExchangeModel.getDialogType();
            String contextStr = ctripDialogExchangeModel.getDialogContext();

             if (ctripHDDialogType == CtripDialogType.CUSTOMER) {
                baseDialogFragment = CtripCustomerDialogFragmentV2.getInstance(bundle);
            }
        }
        if (baseDialogFragment != null) {
            baseDialogFragment.compatibilityListener = ctripDialogExchangeModel.compatibilityListener;
            baseDialogFragment.compatibilityNegativeListener = ctripDialogExchangeModel.compatibilityNegativeListener;
            baseDialogFragment.compatibilityPositiveListener = ctripDialogExchangeModel.compatibilityPositiveListener;
            baseDialogFragment.singleClickCallBack = ctripDialogExchangeModel.getSigleClickCallBack();
            baseDialogFragment.positiveClickCallBack = ctripDialogExchangeModel.getPositiveClickCallBack();
            baseDialogFragment.negativeClickCallBack = ctripDialogExchangeModel.getNegativeClickCallBack();
            if (baseDialogFragment instanceof CtripCustomerDialogFragmentV2) {
                ((CtripCustomerDialogFragmentV2) baseDialogFragment).customView = ctripDialogExchangeModel.getCustomView();
            }
        }
        if (baseDialogFragment != null) {
            if (fragment != null) {
                baseDialogFragment.setTargetFragment(fragment, 0x2001);
            }

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(baseDialogFragment, ctripDialogExchangeModel.getTag());
            ft.commitAllowingStateLoss();
        }
        return baseDialogFragment;
    }
}
