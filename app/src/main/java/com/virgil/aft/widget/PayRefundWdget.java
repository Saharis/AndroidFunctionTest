package com.virgil.aft.widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.virgil.aft.R;
import com.virgil.aft.business.view.bean.RefundInformationModel;
import com.virgil.aft.business.view.bean.RefundProcessInformationModel;
import com.virgil.aft.component.CtripDialogExchangeModel;
import com.virgil.aft.component.CtripDialogType;
import com.virgil.aft.component.IOnReundCallback;
import com.virgil.aft.framework.BaseFragmentManager;
import com.virgil.aft.util.DateUtil;
import com.virgil.aft.util.DeviceUtil;
import com.virgil.aft.util.LogUtil;
import com.virgil.aft.util.PayUtil;
import com.virgil.aft.util.StringUtil;

/**
 * 退款进度组件
 * Created by liuwujing on 15/2/2.
 */
public class PayRefundWdget {

    private Context mContext;
    private IOnReundCallback mOnReundCallback ;
    private List<RefundInformationModel> mRefundInfosList;
    private int MAX_HEIGTH;
    private View rootView;
    private int ROOT_MARGIN_LR_BOUNDS=DeviceUtil.getPixelFromDip(15);
    private int CARD_MARGIN_LR_BOUNDS=DeviceUtil.getPixelFromDip(10);
    private int totalCardHight;
    private int totalCardNum;
    private int watchCardNum;
    private PayRefundWdget(Context context, IOnReundCallback onReundCallback, List<RefundInformationModel> refundInforList) {
        this.mContext = context;
        this.mOnReundCallback = onReundCallback;
        this.mRefundInfosList = refundInforList;
        initMAXHight();
    }
    private void initMAXHight(){
        if(mContext instanceof Activity){
            Rect rectgle= new Rect();
            Window window= ((Activity)mContext).getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
            MAX_HEIGTH=(rectgle.bottom-rectgle.top)/2;
        }else{
            MAX_HEIGTH=DeviceUtil.getPixelFromDip(310);
        }

    }

    private void show() {
        if (mContext instanceof Activity) {
            initView();
            FragmentManager manager = ((Activity) mContext).getFragmentManager();
            CtripDialogExchangeModel.CtripDialogExchangeModelBuilder builder = new CtripDialogExchangeModel.CtripDialogExchangeModelBuilder(CtripDialogType.CUSTOMER, "asda");
            CtripDialogExchangeModel ctripDialogExchangeModel = builder.setBackable(true).setSpaceable(true).setCustomView(rootView).creat();
            BaseFragmentManager.showDialogFragment(manager, ctripDialogExchangeModel, null, (Activity) mContext);
        }
    }

    private void initView() {
        rootView= buildRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                LogUtil.i("REFUND=="+"rootView.getMeasuredHeight()="+rootView.getMeasuredHeight()+";MAX_HEIGTH="+MAX_HEIGTH);

            }
        });
    }

    private View buildRootView() {
//        RelativeLayout root = new RelativeLayout(mContext);
        LinearLayout root =(LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.refund_card,null);
        FrameLayout.LayoutParams lp_root = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
        lp_root.leftMargin = ROOT_MARGIN_LR_BOUNDS;
        lp_root.rightMargin = ROOT_MARGIN_LR_BOUNDS;
        root.setLayoutParams(lp_root);
        root.setGravity(Gravity.CENTER);

        root.setBackgroundResource(R.drawable.bg_window);
        TextView title;
        ImageButton cancelButton;
        ImageView blueline;

        ScrollView scroll;
        LinearLayout sonScroll;

//        title = new TextView(mContext);
//        title.setId((int)System.currentTimeMillis());
//        SpannableString sp_title = new SpannableString("退款进度");
//        sp_title.setSpan(new TextAppearanceSpan(mContext, R.style.text_18_099fde), 0, sp_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        title.setText(sp_title);
//        RelativeLayout.LayoutParams lp_title = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp_title.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        lp_title.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        root.addView(title, lp_title);
//
//        cancelButton = new ImageButton(mContext);
//        cancelButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_cancle_corner));
//        RelativeLayout.LayoutParams lp_cancel = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp_cancel.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lp_cancel.addRule(RelativeLayout.ALIGN_TOP, title.getId());
//        lp_cancel.addRule(RelativeLayout.ALIGN_BOTTOM, title.getId());
//        root.addView(cancelButton);
//
//
//        blueline = new ImageView(mContext);
//        blueline.setId((int)System.currentTimeMillis());
//        blueline.setImageDrawable(mContext.getResources().getDrawable(R.color.text_dialog_title));
//        RelativeLayout.LayoutParams lp_line = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtil.getPixelFromDip(2));
//        lp_line.addRule(RelativeLayout.BELOW, title.getId());
//        root.addView(blueline);

//        scroll = new ScrollView(mContext);
//        scroll=(ScrollView)root.findViewById(R.id.card_scroll);
//        RelativeLayout.LayoutParams lp_scroll = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp_scroll.addRule(RelativeLayout.BELOW,blueline.getId());
//        sonScroll = new LinearLayout(mContext);
        sonScroll=(LinearLayout)root.findViewById(R.id.card_scroll_son);
//        sonScroll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp_sonScroll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_sonScroll.leftMargin=CARD_MARGIN_LR_BOUNDS;
        lp_sonScroll.rightMargin=CARD_MARGIN_LR_BOUNDS;

        if (mRefundInfosList != null && mRefundInfosList.size() > 0) {
            for (int index = 0; index < mRefundInfosList.size(); index++) {
                 final View cardView = buildBillRefundCardView(mRefundInfosList.get(index), mRefundInfosList);
                if (cardView != null) {
                    totalCardNum++;
                    cardView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            totalCardHight+=cardView.getMeasuredHeight();
                            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                                cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }else{
                                cardView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            }
                            watchCardNum++;
                            LogUtil.i("REFUND=="+"totalCardNum="+totalCardNum+";watchCardNum="+watchCardNum+";cardView.getMeasuredHeight()="+cardView.getMeasuredHeight()+";totalCardHight"+totalCardHight+";");
                            if(totalCardNum==watchCardNum){
                                fixScrollViewHight();
                            }
                        }
                    });
                    sonScroll.addView(cardView,lp_sonScroll);
                }
            }
        }
//        scroll.addView(sonScroll);
//        root.addView(scroll, lp_scroll);

        return root;
    }
    private void fixScrollViewHight(){
        if(totalCardHight>MAX_HEIGTH){
           ScrollView scroll=(ScrollView)rootView.findViewById(R.id.card_scroll);
            ViewGroup.LayoutParams layoutParams=scroll.getLayoutParams();
            layoutParams.height=MAX_HEIGTH;
            scroll.setLayoutParams(layoutParams);

        }
    }

    /**
     * 构建单张卡片的视图
     *
     * @param item             RefundInformationModel|数据
     * @param mRefundInfosList List<RefundInformationModel>|全部的卡片数据，用于判断是否需要显示金额
     * @return View|构建的单张卡片的View
     */
    private View buildBillRefundCardView(RefundInformationModel item, List<RefundInformationModel> mRefundInfosList) {
        LinearLayout cardRoot = null;
        if (item != null && mRefundInfosList != null && mRefundInfosList.size() > 0) {
            cardRoot = new LinearLayout(mContext);
            cardRoot.setOrientation(LinearLayout.VERTICAL);
            FrameLayout.LayoutParams lp_root=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cardRoot.setLayoutParams(lp_root);
            if (mRefundInfosList.size() > 1) {
                TextView amout = new TextView(mContext);
                SpannableString sp_amout = new SpannableString("金额:" + "  " + PayUtil.toDecimalString(item.amount));
                sp_amout.setSpan(new TextAppearanceSpan(mContext, R.style.text_15_333333), 0, sp_amout.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                amout.setText(sp_amout);
                LinearLayout.LayoutParams lp_title=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp_title.topMargin=DeviceUtil.getPixelFromDip(15);
                cardRoot.addView(amout, lp_title);
            }
            for(int index=0;index<item.refundProcessInfoList.size();index++){
                View itemView = buildItemView(item.refundProcessInfoList.get(index), item.refundProcessInfoList);
                if (itemView != null) {
                    if(index==0){
                        LinearLayout.LayoutParams lp_title=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp_title.topMargin=DeviceUtil.getPixelFromDip(15);
                        cardRoot.addView(itemView, lp_title);
                    }else{
                        cardRoot.addView(itemView);
                    }
                }
            }
            int currentIndex = mRefundInfosList.indexOf(item);
            if (currentIndex < (mRefundInfosList.size() - 1)) {
                View line = new View(mContext);
                line.setLayoutParams(new FrameLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtil.getPixelFromDip(1)));
                line.setBackgroundColor(mContext.getResources().getColor(R.color.ui_bg_divider));
                cardRoot.addView(line);
            }
        }

        return cardRoot;
    }

    /**
     * 构建卡片的单行View。
     * 一行的View包括状态icon，icon下面的线，icon右边的描述文案和时间状态文案
     *
     * @param item            RefundProcessInformationModel|单行的数据
     * @param refundInfosList List<RefundProcessInformationModel>|多行的数据，用于判断icon下面的线是否显示和线条的颜色
     * @return View
     */
    private View buildItemView(RefundProcessInformationModel item, List<RefundProcessInformationModel> refundInfosList) {
        View itemRoot = LayoutInflater.from(mContext).inflate(R.layout.refund_item, null);
        //CALLME 这里缺少上下间距
        if (item != null) {
            RelativeLayout root = new RelativeLayout(mContext);

            //声明
            ImageView icon;
            TextView line;
            TextView desc;
            TextView note;

            //
            SpannableString descSP = getItemDesc(item);
            SpannableString noteSP = getItemNote(item);


            Drawable iconDrawable = getItemIcon(item);
            Drawable lineDrawable = getItemLine(item, refundInfosList);
            if (iconDrawable != null) {
//                icon = new ImageView(mContext);
                icon = (ImageView) itemRoot.findViewById(R.id.refund_icon);
                icon.setImageDrawable(iconDrawable);
            }


//            if (icon != null && line != null) {
//                RelativeLayout.LayoutParams lp_Icon=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                lp_Icon.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                root.addView(icon,lp_Icon);
//
//                RelativeLayout.LayoutParams lp_Line=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                lp_Line.addRule(RelativeLayout.BELOW,icon.getId());
//                root.addView(line,lp_Line);
//            }

            if (descSP != null) {
//                desc=new TextView(mContext);
                desc = (TextView) itemRoot.findViewById(R.id.refund_desc);
                desc.setText(descSP);
            }

            if (noteSP != null) {
//                desc=new TextView(mContext);
                note = (TextView) itemRoot.findViewById(R.id.refund_note);
                note.setText(noteSP);
            }

            line = (TextView) itemRoot.findViewById(R.id.refund_line);

            if (lineDrawable != null) {
                //line = new ImageView(mContext);
                line.setBackgroundDrawable(lineDrawable);
//                line.setImageDrawable(lineDrawable);
//                line.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
//                            line.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        }else{
//                            line.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                        }
//                        RelativeLayout.LayoutParams lp_line=(RelativeLayout.LayoutParams)line.getLayoutParams();
//                        lp_line.height=RelativeLayout.LayoutParams.MATCH_PARENT;
//                        line.setLayoutParams(lp_line);
//                    }
//                });
            } else {
                ((View) line.getParent()).setVisibility(View.GONE);
            }
        }
               return itemRoot;
    }

    /**
     * 构建卡片每一行的icon
     *
     * @param item RefundProcessInformationModel|model数据
     * @return Drawable|icon
     */
    private Drawable getItemIcon(RefundProcessInformationModel item) {
        if (item == null) {
            return null;
        }
        Drawable icon;
        int status = item.processStatus;
        switch (status) {
            case 1:
                icon = mContext.getResources().getDrawable(R.drawable.ico_finish);
                break;
            case 2:
                icon = mContext.getResources().getDrawable(R.drawable.ico_failed);
                break;
            case 4:
                icon = mContext.getResources().getDrawable(R.drawable.ico_ing);
                break;
            case 8:
                icon = mContext.getResources().getDrawable(R.drawable.refund_circle);
                break;
            default:
                icon = mContext.getResources().getDrawable(R.drawable.refund_circle);
                break;
        }
        return icon;
    }

    /**
     * 构建icon下面的线
     *
     * @param item            RefundProcessInformationModel|model数据
     * @param refundInfosList List<RefundProcessInformationModel>|用于判断是否显示竖线和显示竖线的时候的颜色
     * @return Drawable|竖线，为null的时候不显示
     */
    private Drawable getItemLine(RefundProcessInformationModel item, List<RefundProcessInformationModel> refundInfosList) {
        Drawable line;

        int currentIndex = refundInfosList.indexOf(item);
        if (refundInfosList.size() > (currentIndex + 1)) {
            if (refundInfosList.get(currentIndex + 1).processStatus == 8) {
                line = mContext.getResources().getDrawable(R.color.light_gray3);
            } else {
                line = mContext.getResources().getDrawable(R.color.light_blue_9);

            }
        } else {
            line = null;
        }
        return line;
    }

    /**
     * 构建文案描述
     *
     * @param item RefundProcessInformationModel|model数据
     * @return SpannableString
     */
    private SpannableString getItemDesc(RefundProcessInformationModel item) {
        if (item == null) {
            return null;
        }
        SpannableString str = new SpannableString(item.processName);
        int style;
        switch (item.processStatus) {
            case 1:
                style = R.style.text_14_666666;
                break;
            case 2:
                style = R.style.text_14_df080c;
                break;
            case 4:
                style = R.style.text_14_099fde;
                break;
            case 8:
                style = R.style.text_14_cccccc;
                break;
            default:
                style = 0;
                break;
        }
        if (style != 0) {
            str.setSpan(new TextAppearanceSpan(mContext, style), 0, item.processName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return str;
    }

    /**
     * 构建注释
     *
     * @param item RefundProcessInformationModel|model数据
     * @return SpannableString|注释的文案，无文案的时候不显示
     */
    private SpannableString getItemNote(RefundProcessInformationModel item) {
        if (item == null) {
            return null;
        }
        String note;
        if (StringUtil.isDateTimeEmpty(item.processOpTime)) {
            note = item.processDesc;
        } else {
            note = formatDateTimeString(item.processOpTime) + item.processDesc;
        }
        SpannableString str = new SpannableString(note);
        int style;
        switch (item.processStatus) {
            case 1:
                style = R.style.text_13_858585;
                break;
            case 2:
                style = R.style.text_13_df080c;
                break;
            case 4:
                style = R.style.text_13_099fde;
                break;
            case 8:
                style = 0;
                break;
            default:
                style = 0;
                break;
        }
        if (style != 0 && !TextUtils.isEmpty(note)) {
            str.setSpan(new TextAppearanceSpan(mContext, style), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return str;
    }

    /**
     * 格式化14位的日期时间(YYYYMMDDHHmmss)为(MM月dd日 HH:mm)的形式
     *
     * @param timeStr String|必须为14位
     * @return String|已格式化好的字符串
     */
    public static String formatDateTimeString(String timeStr) {
        if (timeStr == null) {
            return null;
        }
        if (timeStr.length() < 14) {
            return timeStr;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String str = (dateFormat).format(DateUtil.getCalendarByDateTimeStr(timeStr).getTime());
        return str;
    }

    /**
     * 实例化一个PayRefundWdget并显示出来
     *
     * @param context   Context
     * @param paramJson String
     */
    public static void initRefundWidget(Context context, String paramJson, IOnReundCallback onReundCallback) {
        int checkParamReuslt = checkParam(context, paramJson);
        if (checkParamReuslt != -1) {
//            CommonUtil.showToast(checkParamReuslt + "");
        } else {
            List<RefundInformationModel> refundInforList = new ArrayList<RefundInformationModel>();
            sendQueryRefundInfoService(refundInforList);
            PayRefundWdget payRefundWdget = new PayRefundWdget(context, onReundCallback, refundInforList);
            payRefundWdget.show();
        }
    }

    /**
     * 参数检查
     *
     * @param context   Context
     * @param paramJson String|Json格式的Param
     * @return int|-1，校验Pass；非-1，异常。
     */
    private static int checkParam(Context context, String paramJson) {
        int checkResult = -1;

        return checkResult;
    }

    private static void sendQueryRefundInfoService(List<RefundInformationModel> refundInforList) {
        RefundInformationModel refundInfo1 = new RefundInformationModel();
        refundInfo1.amount = 111111;
        refundInfo1.refundProcessInfoList = new ArrayList<RefundProcessInformationModel>();
        RefundProcessInformationModel refundInfo1_1 = new RefundProcessInformationModel();
        refundInfo1_1.processStatus = 1;
        refundInfo1_1.processName = "退款申请已受理";
//        refundInfo1_1.processDesc = "请致电95588与银行联系";
        refundInfo1_1.processOpTime = "20140128140911";
        refundInfo1.refundProcessInfoList.add(refundInfo1_1);

        RefundProcessInformationModel refundInfo1_2 = new RefundProcessInformationModel();
        refundInfo1_2.processStatus = 4;
        refundInfo1_2.processName = "银行处理退款申请";
        refundInfo1_2.processDesc = "请致电10106666与携程客服联系";
//        refundInfo1_2.processOpTime = "20140228140911";
        refundInfo1.refundProcessInfoList.add(refundInfo1_2);

        RefundProcessInformationModel refundInfo1_3 = new RefundProcessInformationModel();
        refundInfo1_3.processStatus = 8;
        refundInfo1_3.processName = "退款成功";
//        refundInfo1_3.processDesc = "预计到账需要15个工作日";
        refundInfo1_3.processOpTime = "20140328140911";
        refundInfo1.refundProcessInfoList.add(refundInfo1_3);


        RefundInformationModel refundInfo2 = new RefundInformationModel();
        refundInfo2.amount = 222222;
        RefundProcessInformationModel refundInfo2_1 = new RefundProcessInformationModel();
        refundInfo2_1.processStatus = 4;
        refundInfo2_1.processName = "退款申请已受理";
        refundInfo2_1.processDesc = "请致电95588与银行联系";
//        refundInfo2_1.processOpTime = "20150428140911";
        refundInfo2.refundProcessInfoList.add(refundInfo2_1);

        RefundProcessInformationModel refundInfo2_2 = new RefundProcessInformationModel();
        refundInfo2_2.processStatus = 8;
        refundInfo2_2.processName = "银行处理退款申请";
        refundInfo2_2.processDesc = "请致电10106666与携程客服联系";
//        refundInfo2_2.processOpTime = "20150515140911";
        refundInfo2.refundProcessInfoList.add(refundInfo2_2);

        RefundProcessInformationModel refundInfo2_3 = new RefundProcessInformationModel();
        refundInfo2_3.processStatus = 8;
        refundInfo2_3.processName = "退款成功";
//        refundInfo2_3.processDesc = "预计到账需要15个工作日";
        refundInfo2_3.processOpTime = "20150628140911";
        refundInfo2.refundProcessInfoList.add(refundInfo2_3);


        RefundInformationModel refundInfo3 = new RefundInformationModel();
        refundInfo3.amount = 333333;
        refundInfo3.refundProcessInfoList = new ArrayList<RefundProcessInformationModel>();
        RefundProcessInformationModel refundInfo3_1 = new RefundProcessInformationModel();
        refundInfo3_1.processStatus = 1;
        refundInfo3_1.processName = "退款申请已受理";
//        refundInfo3_1.processDesc = "请致电95588与银行联系";
        refundInfo3_1.processOpTime = "20160728140911";
        refundInfo3.refundProcessInfoList.add(refundInfo3_1);

        RefundProcessInformationModel refundInfo3_2 = new RefundProcessInformationModel();
        refundInfo3_2.processStatus = 2;
        refundInfo3_2.processName = "银行处理退款申请";
        refundInfo3_2.processDesc = "请致电10106666与携程客服联系";
//        refundInfo3_2.processOpTime = "20160831230911";
        refundInfo3.refundProcessInfoList.add(refundInfo3_2);

        RefundProcessInformationModel refundInfo3_3 = new RefundProcessInformationModel();
        refundInfo3_3.processStatus = 8;
        refundInfo3_3.processName = "退款成功";
        refundInfo3_3.processDesc = "预计到账需要15个工作日";
//        refundInfo3_3.processOpTime = "20160901111111";
        refundInfo3.refundProcessInfoList.add(refundInfo3_3);

        refundInforList.add(refundInfo1);
        refundInforList.add(refundInfo2);
        refundInforList.add(refundInfo3);


    }
}
