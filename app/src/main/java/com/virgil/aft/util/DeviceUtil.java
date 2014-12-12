package com.virgil.aft.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.virgil.aft.core.ApplicationCache;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

public class DeviceUtil {

	/**
	 * 
	 * 获取设备的型号
	 * 
	 */
	public static String getDeviceModel() {
		String model = Build.MODEL;

		if (model == null) {
			return "";
		} else {
			return model;
		}
	}

	/**
	 * 
	 * 获取设备 SDK版本名
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static String getSDKVersion() {
		return Build.VERSION.SDK;
	}

	/**
	 * 
	 * 获取设备 SDK版本号
	 * 
	 */
	public static int getSDKVersionInt() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 判断是否存在SD卡
	 * @return
	 */
	public static boolean isSdCardExist()
    {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }
	/**
	 * 
	 * 获取设备的IMEI号
	 * 
	 */
	public static String getIMEI(Context context) {
		TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return teleMgr.getDeviceId();
	}

	/**
	 * 
	 * 获取设备的IMSI号
	 * 
	 */
	public static String getIMSI(Context context) {
		TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return teleMgr.getSubscriberId();
	}

	/**
	 * 获取设备号 wifi mac + imei + cpu serial
	 * 
	 * @return 设备号
	 * 
	 */
	public static String getMobileUUID(Context context) {
		String uuid = "";
		// 先获取mac
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		/* 获取mac地址 */
		if (wifiMgr != null) {
			WifiInfo info = wifiMgr.getConnectionInfo();
			if (info != null && info.getMacAddress() != null) {
				uuid = info.getMacAddress().replace(":", "");
			}
		}

		// 再加上imei
		TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = teleMgr.getDeviceId();
		uuid += imei;

		// 最后再加上cpu
		String str = "", strCPU = "", cpuAddress = "";
		try {
			String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
			ProcessBuilder cmd = new ProcessBuilder(args);
			Process pp = cmd.start();
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("Serial") > -1) {
						strCPU = str.substring(str.indexOf(":") + 1, str.length());
						cpuAddress = strCPU.trim();
						break;
					}
				} else {
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		uuid += cpuAddress;

		// 如果三个加在一起超过64位的话就截取
		if (uuid != null && uuid.length() > 64) {
			uuid = uuid.substring(0, 64);
		}
		return uuid;
	}

	/**获取动画设置*/
	@SuppressWarnings("deprecation")
	public static boolean getAnimationSetting(){
		 ContentResolver cv = ApplicationCache.getInstance().getApplication().getContentResolver();
	     String animation = android.provider.Settings.System.getString(cv, android.provider.Settings.System.TRANSITION_ANIMATION_SCALE);
	     return false;
	}

	/**
	 * 获取屏幕的宽高
	 * 
	 * @param dm
	 *            设备显示对象描述
	 * @return int数组, int[0] - width, int[1] - height
	 */
	public static int[] getScreenSize(DisplayMetrics dm) {
		int[] result = new int[2];
		result[0] = dm.widthPixels;
		result[1] = dm.heightPixels;
		return result;
	}

	/**
	 * Dip转换为实际屏幕的像素值
	 * 
	 * @param dm
	 *            设备显示对象描述
	 * @param dip
	 *            dip值
	 * @return 匹配当前屏幕的像素值
	 */
	public static int getPixelFromDip(DisplayMetrics dm, float dip) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
	}

	/**
	 * 判断当前设备的数据服务是否有效
	 * 
	 * @return true - 有效，false - 无效
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectMgr == null) {
			return false;
		}

		NetworkInfo nwInfo = connectMgr.getActiveNetworkInfo();
		if (nwInfo == null || !nwInfo.isAvailable()) {
			return false;
		}
		return true;
	}

	public static boolean isAppInstalled(Context context, String pkgName) {
		if (context == null) {
			return false;
		}

		try {
			context.getPackageManager().getPackageInfo(pkgName, PackageManager.PERMISSION_GRANTED);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * (检查终端是否支持指定的action)
	 */
	public static boolean isIntentAvailable(Context context, String action) {
//		PackageManager packageManager = context.getPackageManager();
//		Intent intent = new Intent(action);
//		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//		return list.size() > 0;
		return isIntentAvailable(context, new Intent(action));
	}

	/**
	 * (检查终端是否支持指定的intent)
	 */
	public static boolean isIntentAvailable(Context context, Intent intent) {
		if (context == null || intent == null) {
			return false;
		}

		PackageManager pkgManager = context.getPackageManager();
		if (pkgManager == null) {
			return false;
		}
		List<ResolveInfo> list = pkgManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public static int getPixelFromDip(float f) {
		// TODO Auto-generated method stub
		return getPixelFromDip(ApplicationCache.getInstance().getApplication().getResources().getDisplayMetrics(),f);
	}
}