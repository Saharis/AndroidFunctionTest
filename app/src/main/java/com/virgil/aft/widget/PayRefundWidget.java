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
import com.virgil.aft.component.CtripDialogCallBackContainer;
import com.virgil.aft.component.CtripDialogExchangeModel;
import com.virgil.aft.component.CtripDialogManager;
import com.virgil.aft.component.CtripDialogType;
import com.virgil.aft.component.ExcuteCallback;
import com.virgil.aft.component.IOnReundCallback;
import com.virgil.aft.framework.BaseFragmentManager;
import com.virgil.aft.framework.BasicActivity;
import com.virgil.aft.util.DateUtil;
import com.virgil.aft.util.DeviceUtil;
import com.virgil.aft.util.LogUtil;
import com.virgil.aft.util.PayUtil;
import com.virgil.aft.util.StringUtil;

/**
 * 退款进度组件
 * Created by liuwujing on 15/2/2.
 */
public class PayRefundWidget {

    /**
     * orderID为空
     */
    private static int ERROR_EMPTY_ORDERID = 3101;
    /**
     * businessType为空
     */
    private static int ERROR_EMPTY_BUSINESSTYPE = 3102;
    /**
     * externalNO和billNO同时为空
     */
    private static int ERROR_EMPTY_EXTERNALNO_WITHB_ILLNO = 3103;
    /**
     * externalNO和billNO同时存在
     */
    private static int ERROR_EXIST_EXTERNALNOWithBill = 3104;
    /**
     * Context为null
     */
    private static int ERROR_EMPTY_CONTEXT = 3105;

    /**
     * Context
     */
    private Context mContext;
    /**
     * 消除自身的回调
     */
    private IOnReundCallback mOnReundCallback;
    /**
     * 退款信息的数据
     */
    private List<RefundInformationModel> mRefundInfosList;
    /**
     * 子视图中的ScrollView可滚动的最小高度
     */
    private int MAX_HEIGTH;
    /**
     * 根View
     */
    private View rootView;

    /**
     * 所有卡片视图的高度总和
     */
    private int totalCardHight;
    /**
     * 所有卡片视图的数量
     */
    private int totalCardNum;
    /**
     * 已Observe的卡片视图数量
     */
    private int watchCardNum;

    private String TAG_FRAGMENT_REFUND = "TAG_FRAGMENT_REFUND";

    /**
     * 构造方法，接收context和callBack
     *
     * @param context         Context
     * @param onReundCallback IOnReundCallback
     */
    private PayRefundWidget(Context context, IOnReundCallback onReundCallback) {
        this.mContext = context;
        this.mOnReundCallback = onReundCallback;
        initMAXHight();
    }

    /**
     * 初始化当前视图允许的最大高度，目前是当前DecorView高度的一半
     */
    private void initMAXHight() {
        if (mContext instanceof Activity) {
            Rect rectgle = new Rect();
            Window window = ((Activity) mContext).getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
            MAX_HEIGTH = (rectgle.bottom - rectgle.top) / 2;
        } else {
            MAX_HEIGTH = DeviceUtil.getPixelFromDip(310);
        }

    }

    /**
     * 将当前视图show出来
     */
    private void show() {
        if (mContext instanceof Activity) {
            initView();
            FragmentManager manager = ((Activity) mContext).getFragmentManager();
            CtripDialogExchangeModel.CtripDialogExchangeModelBuilder builder = new CtripDialogExchangeModel.CtripDialogExchangeModelBuilder(CtripDialogType.CUSTOMER, TAG_FRAGMENT_REFUND);
            CtripDialogExchangeModel ctripDialogExchangeModel = builder.setBackable(true).setSpaceable(true).creat();

            CtripDialogCallBackContainer container = new CtripDialogCallBackContainer();
            if (mOnReundCallback != null) {
                container.dismissCallBack = (new ExcuteCallback() {
                    @Override
                    public void callBack() {
                        mOnReundCallback.onRefundCallback();
                    }
                });
            }
            container.customView = rootView;
            CtripDialogManager.showDialogFragment(manager, ctripDialogExchangeModel, container, null, (BasicActivity) mContext);
        }
    }

    /**
     * 初始化视图，触发视图的构建
     */
    private void initView() {
        rootView = buildRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                LogUtil.i("REFUND==" + "rootView.getMeasuredHeight()=" + rootView.getMeasuredHeight() + ";MAX_HEIGTH=" + MAX_HEIGTH);

            }
        });
    }

    /**
     * 构建根视图
     *
     * @return View
     */
    private View buildRootView() {
//        RelativeLayout root = new RelativeLayout(mContext);
        LinearLayout root = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.pay_refund_root_layout, null);

        FrameLayout.LayoutParams lp_root = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        lp_root.leftMargin = (int) mContext.getResources().getDimension(R.dimen.DP_15);
        lp_root.rightMargin = (int) mContext.getResources().getDimension(R.dimen.DP_15);
        root.setLayoutParams(lp_root);
        root.setGravity(Gravity.CENTER);

        root.setBackgroundResource(R.drawable.bg_window);
        TextView title;
        ImageButton cancelButton = (ImageButton) root.findViewById(R.id.refund_img_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseFragmentManager.removeFragment(((Activity) mContext).getFragmentManager(), TAG_FRAGMENT_REFUND);
            }
        });
        ImageView blueline;

        ScrollView scroll;
        LinearLayout sonScroll;

        sonScroll = (LinearLayout) root.findViewById(R.id.refund_scroll_son);
        LinearLayout.LayoutParams lp_sonScroll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_sonScroll.leftMargin = (int) mContext.getResources().getDimension(R.dimen.DP_15);
        lp_sonScroll.rightMargin = (int) mContext.getResources().getDimension(R.dimen.DP_15);

        if (mRefundInfosList != null && mRefundInfosList.size() > 0) {
            for (int index = 0; index < mRefundInfosList.size(); index++) {
                final View cardView = buildBillRefundCardView(mRefundInfosList.get(index), mRefundInfosList);
                if (cardView != null) {
                    totalCardNum++;
                    cardView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            totalCardHight += cardView.getMeasuredHeight();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            } else {
                                cardView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            }
                            watchCardNum++;
                            LogUtil.i("REFUND==" + "totalCardNum=" + totalCardNum + ";watchCardNum=" + watchCardNum + ";cardView.getMeasuredHeight()=" + cardView.getMeasuredHeight() + ";totalCardHight" + totalCardHight + ";");
                            if (totalCardNum == watchCardNum) {
                                fixScrollViewHight();
                            }
                        }
                    });
                    sonScroll.addView(cardView, lp_sonScroll);
                    if (index != (mRefundInfosList.size() - 1)) {
                        ImageView split_line = new ImageView(mContext);
                        LinearLayout.LayoutParams lp_split_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.DP_1));
                        split_line.setImageDrawable(mContext.getResources().getDrawable(R.color.color_ffe0e0e0));
                        lp_split_line.leftMargin = (int) mContext.getResources().getDimension(R.dimen.DP_17);
                        sonScroll.addView(split_line, lp_split_line);
                    }
                }
            }
        }
        return root;
    }

    /**
     * 判断视图高度，满足条件时，将子视图ScrollView设置为可以滚动
     */
    private void fixScrollViewHight() {
        if (totalCardHight > MAX_HEIGTH) {
            ScrollView scroll = (ScrollView) rootView.findViewById(R.id.card_scroll);
            ViewGroup.LayoutParams layoutParams = scroll.getLayoutParams();
            layoutParams.height = MAX_HEIGTH;
            scroll.setLayoutParams(layoutParams);

        }
    }

    /**
     * 构建单张卡片的视图
     *
     * @param card             RefundInformationModel|数据
     * @param mRefundInfosList List<RefundInformationModel>|全部的卡片数据，用于判断是否需要显示金额
     * @return View|构建的单张卡片的View
     */
    private View buildBillRefundCardView(RefundInformationModel card, List<RefundInformationModel> mRefundInfosList) {
        LinearLayout root = null;
        if (card != null && mRefundInfosList != null && mRefundInfosList.size() > 0) {
            root = new LinearLayout(mContext);
            root.setOrientation(LinearLayout.VERTICAL);
            if (mRefundInfosList.size() > 1) {
                TextView amout = new TextView(mContext);
                SpannableString sp_amout = new SpannableString("金额:" + " " + StringUtil.getFormatCurrency(card.currency) + " " + PayUtil.toDecimalString(card.amount.priceValue));
                sp_amout.setSpan(new TextAppearanceSpan(mContext, R.style.text_15_333333), 0, sp_amout.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                amout.setText(sp_amout);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.topMargin = (int) mContext.getResources().getDimension(R.dimen.DP_15);
                root.addView(amout, lp);
            }
            if (card.refundProcessInfoList != null && card.refundProcessInfoList.size() > 0) {
                for (int i = 0; i < card.refundProcessInfoList.size(); i++) {
                    View itemView = buildCardItemView(card.refundProcessInfoList.get(i), card.refundProcessInfoList);
                    if (i == 0) {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = (int) mContext.getResources().getDimension(R.dimen.DP_15);
                        root.addView(itemView, lp);
                    } else {
                        root.addView(itemView);
                    }
                }
            }
        }

        return root;
    }

    private View buildCardItemView(RefundProcessInformationModel item, List<RefundProcessInformationModel> itemList) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pay_layout_refund_item, null);
        TextView tv_Desc = (TextView) view.findViewById(R.id.refund_desc);
        TextView tv_Note = (TextView) view.findViewById(R.id.refund_note);
        ImageView icon = (ImageView) view.findViewById(R.id.refund_icon);
        ImageView line_inner_top = (ImageView) view.findViewById(R.id.line_top);
        ImageView line_inner_bottom = (ImageView) view.findViewById(R.id.line_bottom);
        ImageView line_bottom = (ImageView) view.findViewById(R.id.line);
        tv_Desc.setText(getItemDesc(item));
        tv_Note.setText(getItemNote(item));
        getIconView(icon, item);
//        icon.setImageDrawable(getIconView(item));
        Drawable dr_bottomLine = getItemLine(item, itemList);
        if (dr_bottomLine != null) {
            line_bottom.setVisibility(View.VISIBLE);
            line_bottom.setImageDrawable(dr_bottomLine);

            Drawable dr_bottomLine2 = getItemLine(item, itemList);

            line_inner_bottom.setVisibility(View.VISIBLE);
            line_inner_bottom.setImageDrawable(dr_bottomLine2);
        }
        int currentIndex = itemList.indexOf(item);
        if (currentIndex == 0) {
            line_inner_top.setVisibility(View.GONE);

        } else {
            Drawable dr_topline = getItemLine(itemList.get(currentIndex - 1), itemList);
            line_inner_top.setVisibility(View.VISIBLE);
            line_inner_top.setImageDrawable(dr_topline);
            if (currentIndex == (itemList.size() - 1)) {
                line_inner_bottom.setVisibility(View.GONE);
                line_bottom.setVisibility(View.GONE);
            }
        }

        return view;
    }

    /**
     * 根据状态获取相匹配的Drawable
     *
     * @param item RefundProcessInformationModel
     * @return Drawable
     */
    private Drawable getIconView(ImageView view,RefundProcessInformationModel item) {
        if (item != null) {
            Drawable icon;
            int status = item.processStatus;
            RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams)view.getLayoutParams();
            switch (status) {
                case 1:
                    icon = mContext.getResources().getDrawable(R.drawable.pay_refund_ico_finish);
                    lp.width=(int) mContext.getResources().getDimension(R.dimen.DP_19);
                    lp.height=(int) mContext.getResources().getDimension(R.dimen.DP_19);

                    break;
                case 2:
                    icon = mContext.getResources().getDrawable(R.drawable.pay_refund_ico_failed);
                    lp.width=(int) mContext.getResources().getDimension(R.dimen.DP_19);
                    lp.height=(int) mContext.getResources().getDimension(R.dimen.DP_19);
                    break;
                case 4:
                    icon = mContext.getResources().getDrawable(R.drawable.pay_refund_ico_ing);
                    lp.width=(int) mContext.getResources().getDimension(R.dimen.DP_19);
                    lp.height=(int) mContext.getResources().getDimension(R.dimen.DP_19);
                    break;
                case 8:
                    icon = mContext.getResources().getDrawable(R.drawable.pay_refund_ico_unprocessed);
                    lp.width=(int) mContext.getResources().getDimension(R.dimen.DP_10);
                    lp.height=(int) mContext.getResources().getDimension(R.dimen.DP_10);
                    break;
                default:
                    icon = mContext.getResources().getDrawable(R.drawable.pay_refund_ico_unprocessed);
                    lp.width=(int) mContext.getResources().getDimension(R.dimen.DP_10);
                    lp.height=(int) mContext.getResources().getDimension(R.dimen.DP_10);
                    break;
            }
            view.setImageDrawable(icon);
            view.setLayoutParams(lp);

            return icon;
        } else {
            return null;
        }
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
            LogUtil.showToast("系统繁忙,请稍后重试（" + checkParamReuslt + "）");
        } else {
            try {
                final PayRefundWidget payRefundWidget = new PayRefundWidget(context, onReundCallback);


                sendQueryRefundInfoService(context, 0, 0, "", "", payRefundWidget);
                payRefundWidget.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private static void sendQueryRefundInfoService(Context context, long orderID, int businessType, String externalNO, String billNO, final PayRefundWidget view) {
        List<RefundInformationModel> refundInforList = new ArrayList<RefundInformationModel>();
        RefundInformationModel refundInfo1 = new RefundInformationModel();
        refundInfo1.amount.priceValue = 111111;
        refundInfo1.refundProcessInfoList = new ArrayList<RefundProcessInformationModel>();
        RefundProcessInformationModel refundInfo1_1 = new RefundProcessInformationModel();
        refundInfo1_1.processStatus = 4;
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
        refundInfo1_3.processOpTime = "";
        refundInfo1.refundProcessInfoList.add(refundInfo1_3);


        RefundInformationModel refundInfo2 = new RefundInformationModel();
        refundInfo2.amount.priceValue = 222222;
        RefundProcessInformationModel refundInfo2_1 = new RefundProcessInformationModel();
        refundInfo2_1.processStatus = 4;
        refundInfo2_1.processName = "退款申请已受理";
        refundInfo2_1.processDesc = "请致电95588与银行联系";
//        refundInfo2_1.processOpTime = "20150428140911";
        refundInfo2.refundProcessInfoList.add(refundInfo2_1);

        RefundProcessInformationModel refundInfo2_2 = new RefundProcessInformationModel();
        refundInfo2_2.processStatus = 8;
        refundInfo2_2.processName = "银行处理退款申请";
        refundInfo2_2.processDesc = "";
//        refundInfo2_2.processOpTime = "20150515140911";
        refundInfo2.refundProcessInfoList.add(refundInfo2_2);

        RefundProcessInformationModel refundInfo2_3 = new RefundProcessInformationModel();
        refundInfo2_3.processStatus = 8;
        refundInfo2_3.processName = "退款成功";
//        refundInfo2_3.processDesc = "预计到账需要15个工作日";
        refundInfo2_3.processOpTime = "";
        refundInfo2.refundProcessInfoList.add(refundInfo2_3);


        RefundInformationModel refundInfo3 = new RefundInformationModel();
        refundInfo3.amount.priceValue = 333333;
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
        refundInfo3_3.processDesc = "";
//        refundInfo3_3.processOpTime = "20160901111111";
        refundInfo3.refundProcessInfoList.add(refundInfo3_3);


        RefundInformationModel refundInfo4 = new RefundInformationModel();
        refundInfo4.amount.priceValue = 333333;
        refundInfo4.refundProcessInfoList = new ArrayList<RefundProcessInformationModel>();
        RefundProcessInformationModel refundInfo4_1 = new RefundProcessInformationModel();
        refundInfo4_1.processStatus = 1;
        refundInfo4_1.processName = "退款申请已受理";
//        refundInfo3_1.processDesc = "请致电95588与银行联系";
        refundInfo4_1.processOpTime = "20160728140911";
        refundInfo4.refundProcessInfoList.add(refundInfo4_1);

        RefundProcessInformationModel refundInfo4_2 = new RefundProcessInformationModel();
        refundInfo4_2.processStatus = 1;
        refundInfo4_2.processName = "银行处理退款申请";
        refundInfo4_2.processDesc = "请致电10106666与携程客服联系";
//        refundInfo3_2.processOpTime = "20160831230911";
        refundInfo4.refundProcessInfoList.add(refundInfo4_2);

        RefundProcessInformationModel refundInfo4_3 = new RefundProcessInformationModel();
        refundInfo4_3.processStatus = 1;
        refundInfo4_3.processName = "退款成功";
        refundInfo4_3.processDesc = "预计到账需要15个工作日预计到账需要15个工作日预计到账需要15个工作日";
//        refundInfo3_3.processOpTime = "20160901111111";
        refundInfo4.refundProcessInfoList.add(refundInfo4_3);
        refundInforList.add(refundInfo1);
        refundInforList.add(refundInfo2);
        refundInforList.add(refundInfo3);
        refundInforList.add(refundInfo4);
        view.setRefundInfosList(refundInforList);
    }

    public void setRefundInfosList(List<RefundInformationModel> refundInfosList) {
        mRefundInfosList = refundInfosList;
    }
}
