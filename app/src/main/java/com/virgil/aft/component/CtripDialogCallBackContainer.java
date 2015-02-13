package com.virgil.aft.component;

import android.view.View;

/**
 * 保存DialogFragment各种CallBack的容器
 * Created by liuwujing on 15/2/10.
 */
public class CtripDialogCallBackContainer {
    public CtripDialogCallBackContainer() {

    }

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

    /**
     * Fragmet在Dismss的时候，进行回调
     * added by liuwj 2015-02-10
     */
    public ExcuteCallback dismissCallBack;
    /**
     * 自定义的View
     * added by liuwj 2014-11-27
     */
    public View customView;

}
