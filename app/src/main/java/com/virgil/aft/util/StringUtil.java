package com.virgil.aft.util;

/**
 * Created by liuwujing on 15/2/3.
 */
public class StringUtil {
    public static boolean isDateTimeEmpty(String dateTime) {
        final String emptyDate = "00010101";// 8位空日期
        final String emptyDateTime = "00010101000000";// 14位空日期
        if (StringUtil.emptyOrNull(dateTime)) {
            return true;
        }
        // 14位时间
        if (dateTime.equals(emptyDateTime)) {
            return true;
        }
        // 8位日期
        if (dateTime.equals(emptyDate)) {
            return true;
        }
        return false;
    }
    public static boolean emptyOrNull(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 将String转换为int，异常时，返回传入的{@code #defaultValue}
     *
     * @param str          需要转换为int的String
     * @param defaultValue 异常时的默认值
     * @return int
     */
    public static int toInt(String str, int defaultValue) {
        int i;
        try {
            i = Integer.parseInt(str);
        }
        catch (Exception e) {
            i = defaultValue;
        }
        return i;
    }

    public static int toInt(String s) {
        return toInt(s,-1);
    }
}
