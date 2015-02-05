package com.virgil.aft.component;

import java.io.Serializable;

import android.view.Gravity;
import android.view.View;

/**
 * Created by liuwujing on 15/2/4.
 */
public class CtripDialogExchangeModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3685432164096360692L;
    public CtripDialogExchangeModelBuilder ctripDialogExchangeModelBuilder;

    public View.OnClickListener compatibilityListener;// 错误弹框 按键点击事件

    public View.OnClickListener compatibilityPositiveListener;

    public View.OnClickListener compatibilityNegativeListener;

    public CtripDialogExchangeModel(CtripDialogExchangeModelBuilder ctripDialogExchangeModelBuilder) {
        this.ctripDialogExchangeModelBuilder = ctripDialogExchangeModelBuilder;
    }

    public CtripDialogType getDialogType() {
        return ctripDialogExchangeModelBuilder.dialogType;
    }

    public String getDialogTitle() {
        return ctripDialogExchangeModelBuilder.dialogTitle;
    }

    public String getDialogContext() {
        return ctripDialogExchangeModelBuilder.dialogContext;
    }

    public boolean isHasTitle() {
        return ctripDialogExchangeModelBuilder.hasTitle;
    }

    public String getPostiveText() {
        return ctripDialogExchangeModelBuilder.postiveText;
    }

    public String getNegativeText() {
        return ctripDialogExchangeModelBuilder.negativeText;
    }

    public String getTag() {
        return ctripDialogExchangeModelBuilder.tag;
    }

    public String getSingleText() {
        return ctripDialogExchangeModelBuilder.singleText;
    }

    public boolean isBussinessCancleable() {
        return ctripDialogExchangeModelBuilder.isBussinessCancleable;
    }

    public boolean isBackable() {
        return ctripDialogExchangeModelBuilder.isBackable;
    }

    public boolean isSpaceable() {
        return ctripDialogExchangeModelBuilder.isSpaceable;
    }

    public int getGravity() {
        return ctripDialogExchangeModelBuilder.gravity;
    }

    public String getOldTag() {
        return ctripDialogExchangeModelBuilder.oldTag;
    }

    public CtripDialogType getOldCtripDialogType() {
        return ctripDialogExchangeModelBuilder.oldDialogType;
    }

    public boolean isSingleLine() {
        return ctripDialogExchangeModelBuilder.isSingleLine;
    }

    public int getWidth() {
        return ctripDialogExchangeModelBuilder.iWidth;
    }

    public int getHeight() {
        return ctripDialogExchangeModelBuilder.iHeight;
    }

    /**
     * added by liuwj 2014-11-27
     * @return ExcuteCallback
     */
    public ExcuteCallback getSigleClickCallBack() {
        return ctripDialogExchangeModelBuilder.singleClickCallBack;
    }

    /**
     * added by liuwj 2014-11-27
     * @return ExcuteCallback
     */
    public ExcuteCallback getPositiveClickCallBack() {
        return ctripDialogExchangeModelBuilder.positiveClickCallBack;
    }

    /**
     * added by liuwj 2014-11-27
     * @return ExcuteCallback
     */
    public ExcuteCallback getNegativeClickCallBack() {
        return ctripDialogExchangeModelBuilder.negativeClickCallBack;
    }

    /**
     * added by liuwj 2014-11-27
     * @return View
     */
    public View getCustomView() {
        return ctripDialogExchangeModelBuilder.customView;
    }
    public static class CtripDialogExchangeModelBuilder implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = -3685432164096360693L;
        /**
         * 弹出框类型
         */
        private CtripDialogType dialogType = CtripDialogType.SINGLE;
        /**
         * 弹出框类型
         */
        private CtripDialogType oldDialogType = CtripDialogType.SINGLE;
        /**
         * 弹出框标题
         */
        private String dialogTitle = "";
        /**
         * 弹出框内容
         */
        private String dialogContext = "";
        /**
         * 是否显示标题
         */
        private boolean hasTitle = true;
        /**
         * 确认按键
         */
        private String postiveText = "";
        /**
         * 取消按键
         */
        private String negativeText = "";
        /**
         * 单按键
         */
        private String singleText = "";
        /**
         * tag
         */
        private String tag = "";
        /**
         * 转换前oldTag
         */
        private String oldTag = "";
        /**
         * back可点（默认可点）
         */
        private boolean isBackable = true;
        /**
         * 空白可点（默认可点）
         */
        private boolean isSpaceable = true;
        /**
         * 服务可取消(默认可取消)
         */
        private boolean isBussinessCancleable = true;

        private int gravity = Gravity.CENTER;

        /**
         * 是否单行显示用于多行显示提示信息:火车票 yechen add
         */
        private boolean isSingleLine = true;

        private int iWidth;
        private int iHeight;

        public View.OnClickListener compatibilityListener;// 错误弹框 按键点击事件

        public View.OnClickListener compatibilityPositiveListener;

        public View.OnClickListener compatibilityNegativeListener;

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
         * 自定义的View
         * added by liuwj 2014-11-27
         */
        public View customView;
        public CtripDialogExchangeModelBuilder(CtripDialogType ctripHDDialogType, String tag) {
            this.dialogType = ctripHDDialogType;
            this.tag = tag;
        }

        public CtripDialogExchangeModelBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public CtripDialogExchangeModelBuilder setOldTag(String oldTag) {
            this.oldTag = oldTag;
            return this;
        }

        public CtripDialogExchangeModelBuilder setOldCtripDialogType(CtripDialogType oldDialogType) {
            this.oldDialogType = oldDialogType;
            return this;
        }

        public CtripDialogExchangeModelBuilder setDialogType(CtripDialogType ctripHDDialogType) {
            this.dialogType = ctripHDDialogType;
            return this;
        }

        public CtripDialogExchangeModelBuilder setDialogTitle(String dialogTitle) {
            this.dialogTitle = dialogTitle;
            return this;
        }

        public CtripDialogExchangeModelBuilder setDialogContext(String dialogContext) {
            this.dialogContext = dialogContext;
            return this;
        }

        public CtripDialogExchangeModelBuilder setHasTitle(boolean hasTitle) {
            this.hasTitle = hasTitle;
            return this;
        }

        public CtripDialogExchangeModelBuilder setPostiveText(String postiveText) {
            this.postiveText = postiveText;
            return this;
        }

        public CtripDialogExchangeModelBuilder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public CtripDialogExchangeModelBuilder setSingleText(String singleText) {
            this.singleText = singleText;
            return this;
        }

        public CtripDialogExchangeModelBuilder setBackable(boolean isBackable) {
            this.isBackable = isBackable;
            return this;
        }

        public CtripDialogExchangeModelBuilder setBussinessCancleable(boolean isBussinessCancleable) {
            this.isBussinessCancleable = isBussinessCancleable;
            return this;
        }

        public CtripDialogExchangeModelBuilder setSpaceable(boolean isSpaceable) {
            this.isSpaceable = isSpaceable;
            return this;
        }

        public CtripDialogExchangeModelBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public CtripDialogExchangeModelBuilder setCompatibilityListener(View.OnClickListener compatibilityListener) {
            this.compatibilityListener = compatibilityListener;
            return this;
        }

        public CtripDialogExchangeModelBuilder setCompatibilityPositiveListener(View.OnClickListener compatibilityPositiveListener) {
            this.compatibilityPositiveListener = compatibilityPositiveListener;
            return this;
        }

        public CtripDialogExchangeModelBuilder setCompatibilityNegativeListener(View.OnClickListener compatibilityNegativeListener) {
            this.compatibilityNegativeListener = compatibilityNegativeListener;
            return this;
        }

        /**
         * added by liuwj 2014-11-27
         * @param singleClickCallBack ExcuteCallback
         */
        public CtripDialogExchangeModelBuilder setSingleClickCallBack(ExcuteCallback singleClickCallBack) {
            this.singleClickCallBack = singleClickCallBack;
            return this;
        }

        /**
         * added by liuwj 2014-11-27
         * @param positiveClickCallBack ExcuteCallback
         */
        public CtripDialogExchangeModelBuilder setPositiveClickCallBack(ExcuteCallback positiveClickCallBack) {
            this.positiveClickCallBack = positiveClickCallBack;
            return this;
        }

        /**
         * added by liuwj 2014-11-27
         * @param negativeClickCallBack ExcuteCallback
         */
        public CtripDialogExchangeModelBuilder setNegativeClickCallBack(ExcuteCallback negativeClickCallBack) {
            this.negativeClickCallBack = negativeClickCallBack;
            return this;
        }

        /**
         * added by liuwj 2014-11-27
         * @param customView View
         */
        public CtripDialogExchangeModelBuilder setCustomView(View customView) {
            this.customView = customView;
            return this;
        }

        /**
         *
         * 方法描述 提示文本是否换行
         *
         * @param isSingleLine
         * @return
         * @author yechen @date 2014年8月27日 上午10:58:25
         */
        public CtripDialogExchangeModelBuilder setIsSingleLine(boolean isSingleLine) {
            this.isSingleLine = isSingleLine;
            return this;
        }

        /**
         *
         * 方法描述 设置对话框大小
         *
         * @param width
         * @param height
         * @return
         * @author yechen @date 2014年8月27日 上午11:14:04
         */
        public CtripDialogExchangeModelBuilder setLayoutParams(int width, int height) {
            this.iWidth = width;
            this.iHeight = height;
            return this;
        }

        public CtripDialogExchangeModel creat() {
            return new CtripDialogExchangeModel(this);
        }
    }
}
