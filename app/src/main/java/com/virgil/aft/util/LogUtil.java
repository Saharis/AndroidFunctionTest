package com.virgil.aft.util;

import android.widget.Toast;

import com.virgil.aft.core.ApplicationCache;

/**
 * Created by liuwj on 2014/6/23.
 */
public class LogUtil {

    private static final String TAG = "AFT-Customize";
    private static final boolean SHOW_LOG=true;
    /**
     * Send a VERBOSE log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void v(String msg) {
        if (SHOW_LOG) {
            android.util.Log.v(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void v(String msg, Throwable thr) {
        if (SHOW_LOG) {
            android.util.Log.e("EXCEPTION", "##异常信息##:[" + msg + "]");
            thr.printStackTrace();
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) {
        if (SHOW_LOG) {
            android.util.Log.d(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void x(String msg) {
        if (SHOW_LOG) {
            android.util.Log.e(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void d(String msg, Throwable thr) {
        if (SHOW_LOG) {
            android.util.Log.e("EXCEPTION", "##异常信息##:[" + msg + "]");
            thr.printStackTrace();
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void i(String msg) {
        if (SHOW_LOG) {
            android.util.Log.i(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void i(String msg, Throwable thr) {
        if (SHOW_LOG) {
            android.util.Log.e("EXCEPTION", "##异常信息##:[" + msg + "]");
            thr.printStackTrace();
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void e(String msg) {
        if (SHOW_LOG) {
            android.util.Log.e(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void w(String msg) {
        if (SHOW_LOG) {
            android.util.Log.w(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void w(String msg, Throwable thr) {
        if (SHOW_LOG) {
            android.util.Log.e("EXCEPTION", "##异常信息##:[" + msg + "]");
            thr.printStackTrace();
        }
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr
     *            An exception to log
     */
    public static void w(Throwable thr) {
        if (SHOW_LOG) {
            android.util.Log.w(TAG, thr);
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void e(String msg, Throwable thr) {
        if (SHOW_LOG) {
            android.util.Log.e("EXCEPTION", "##异常信息##:[" + msg + "]",thr);
            thr.printStackTrace();
        }
    }

    /**
     * Building Message
     *
     * @param msg
     *            The message you would like logged.
     * @return Message String
     */
    private static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return new StringBuilder().append(msg).append("	#").append(caller.getClassName()).append(".").append(caller.getMethodName()).append("()|:").append(caller.getLineNumber()).toString();
    }
    public static void  showToast(String msg){
        Toast.makeText(ApplicationCache.getInstance().getApplication(),msg,Toast.LENGTH_SHORT).show();
    }
}
