package com.virgil.aft.framework;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;

import com.virgil.aft.component.CtripDialogExchangeModel;
import com.virgil.aft.component.CtripDialogType;

/**
 * Created by liuwujing on 15/2/4.
 */
public class BaseFragmentManager {
    public static void removeFragment(FragmentManager fragmentManager, Fragment targetFragment) {
        // TODO Auto-generated method stub
        if (fragmentManager != null) {
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

    public static void removeFragment(FragmentManager fragmentManager, String tag) {
        if (fragmentManager != null && !TextUtils.isEmpty(tag)) {
            Fragment targment=fragmentManager.findFragmentByTag(tag);
            if(targment!=null){
                fragmentManager.popBackStack(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
        }
    }
}
