package com.virgil.aft.util;

/**
 * Created by liuwujing on 15/2/4.
 */
public class PayUtil {
    public static String toDecimalString(int value) {
        if (value < 0) {
            return "";
        }

        return String.format("%1$.2f", value / 100.0f);
    }
}
