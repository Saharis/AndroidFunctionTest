package com.virgil.aft.widget;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.virgil.aft.R;
import com.virgil.aft.business.view.bean.RefundInformationModel;
import com.virgil.aft.business.view.bean.RefundProcessInformationModel;
import com.virgil.aft.component.IOnReundCallback;
import com.virgil.aft.util.DateUtil;
import com.virgil.aft.util.StringUtil;

/**
 * 退款进度组件
 * Created by liuwujing on 15/2/2.
 */
public class PayRefundWdget {

    private Context mContext = null;
    private IOnReundCallback mOnReundCallback = null;
    private List<RefundInformationModel> mRefundInfosList = null;

    private PayRefundWdget(Context context, IOnReundCallback onReundCallback, List<RefundInformationModel> refundInforList) {
        this.mContext = context;
        this.mOnReundCallback = onReundCallback;
        this.mRefundInfosList = refundInforList;
    }

    private void show() {

    }

    private View initView() {
        View view = null;

        return view;
    }

    private View buildRootView() {
        View view = null;

        return view;
    }

    private View buildBillRefundCardView(RefundInformationModel item, boolean withBillAmount) {
        View view = null;
        return view;
    }

    private View buildItemView(RefundProcessInformationModel item, List<RefundProcessInformationModel> refundInfosList) {
        View view = null;
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.refund_item, null);

        if (item != null) {
            RelativeLayout root = new RelativeLayout(mContext);

            //声明
            ImageView icon;
            ImageView line;
            TextView desc;
            TextView note;

            //
            SpannableString descSP = getItemDesc(item);
            SpannableString noteSP = getItemNote(item);
            Drawable iconDrawable = getItemIcon(item);
            Drawable lineDrawable = getItemLine(item, refundInfosList);

            if (iconDrawable != null && lineDrawable != null) {
//                icon = new ImageView(mContext);
//                line = new ImageView(mContext);
                icon = (ImageView) rootView.findViewById(R.id.refund_icon);
                line = (ImageView) rootView.findViewById(R.id.refund_line);
                icon.setImageDrawable(iconDrawable);
                line.setImageDrawable(lineDrawable);
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
                desc = (TextView) rootView.findViewById(R.id.refund_desc);
                desc.setText(descSP);
            }

            if (noteSP != null) {
//                desc=new TextView(mContext);
                note = (TextView) rootView.findViewById(R.id.refund_note);
                note.setText(noteSP);
            }
        }
        return rootView;
    }

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
                icon = mContext.getResources().getDrawable(R.drawable.circle);
                break;
            default:
                icon = mContext.getResources().getDrawable(R.drawable.circle);
                break;
        }
        return icon;
    }

    private Drawable getItemLine(RefundProcessInformationModel item, List<RefundProcessInformationModel> refundInfosList) {
        Drawable line;

        boolean shouldBeGray = false;
        int currentIndex = refundInfosList.indexOf(item);
        if (refundInfosList.size() > (currentIndex + 1)) {
            if (refundInfosList.get(currentIndex + 1).processStatus == 8) {
                shouldBeGray = true;
            }
        }
        if (shouldBeGray) {
            line = mContext.getResources().getDrawable(R.color.light_gray3);
        } else {
            line = mContext.getResources().getDrawable(R.color.light_blue_9);
        }
        return line;
    }

    private SpannableString getItemDesc(RefundProcessInformationModel item) {
        if (item == null) {
            return null;
        }
        SpannableString str = new SpannableString(item.processName);
        if (item.processStatus == 2) {
            str.setSpan(new TextAppearanceSpan(mContext, R.style.text_14_df080c), 0, item.processName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            str.setSpan(new TextAppearanceSpan(mContext, R.style.text_14_666666), 0, item.processName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return str;
    }

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
        if (item.processStatus == 2) {
            str.setSpan(new TextAppearanceSpan(mContext, R.style.text_13_df080c), 0, item.processName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            str.setSpan(new TextAppearanceSpan(mContext, R.style.text_13_858585), 0, item.processName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return str;
    }

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
            List<RefundInformationModel> refundInforList = null;
            sendQueryRefundInfoService();
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

    private static void sendQueryRefundInfoService() {

    }
}
