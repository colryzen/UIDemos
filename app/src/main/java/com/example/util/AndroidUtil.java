package com.example.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.Contacts;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class AndroidUtil {

	/** 创建快捷方式广播 */
	private static final String CREATE_SHORTCUT_ACTION = "com.android.launcher.action.INSTALL_SHORTCUT";
	/** 删除快捷方式广播 */
	private static final String DROP_SHORTCUT_ACTION = "com.android.launcher.action.UNINSTALL_SHORTCUT";

	/**
	 * 
	 * 判断Intent 的Action是否可用，返回true，否则返回 false
	 * 
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * 
	 * @param context
	 *            The application's environment.
	 * @param action
	 *            The Intent action to check for availability.
	 * 
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * MIME类型与文件后缀名的匹配表
	 */
	private static final String[][] MIME_TYPE = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" },
			{ ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" },
			{ ".sh", "text/plain" },
			{ ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" },
			{ ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" },
			{ ".xml", "text/xml" },
			{ ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/zip" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ "", "*/*" } };

	/**
	 * 获取存储卡路径
	 * 
	 * @return 存储卡路径，无SD卡返回空字符串
	 */
	public static String getSDCardPath() {
		if (hasSDcardRead()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			return "";
		}
	}

	public static String getInerCardPath() {
		return Environment.getDataDirectory().getAbsolutePath();
	}


	/**
	 * 是否是WIFI连接
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isWIFI(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		return (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI);

	}

	/**
	 * 是否是3G网络
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean is3G(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		return (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE);
	}

	/**
	 * SD卡是否可读
	 * 
	 * @return boolean
	 */
	public static boolean hasSDcardRead() {

		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 是否有网络环境
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean hasNetEnviroment(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();// 获取网络的连接情况
		if (activeNetInfo != null) {
			return activeNetInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 取自身应用的应用名
	 * 
	 * @param context
	 * @return String
	 */
	public static String getAppName(Context context) {
		return getAppName(context, null);
	}

	/**
	 * 取其他应用的应用名
	 * 
	 * @param context
	 * @param packageName
	 * @return String
	 */
	public static String getAppName(Context context, String packageName) {
		String applicationName;

		if (packageName == null) {
			packageName = context.getPackageName();
		}

		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			applicationName = context
					.getString(packageInfo.applicationInfo.labelRes);
		} catch (Exception e) {
			applicationName = "";
		}

		return applicationName;
	}

	/**
	 * 取自身应用的版本名称,用于给客户显示
	 * 
	 * @param context
	 * @return String
	 */
	public static String getAppVersionName(Context context) {
		return getAppVersionName(context, null);
	}

	/**
	 * 取其他应用的版本名称,用于给客户显示
	 * 
	 * @param context
	 * @return String
	 */
	public static String getAppVersionName(Context context, String packageName) {
		String versionName = "";

		if (packageName == null) {
			packageName = context.getPackageName();
		}

		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			versionName = packageInfo.versionName;
		} catch (Exception e) {
		}

		return versionName;
	}

	/**
	 * 取自身应用的版本号,用于新旧版本的更新
	 * 
	 * @param context
	 * @return int
	 */
	public static int getAppVersionCode(Context context) {
		return getAppVersionCode(context, null);
	}

	/**
	 * 取其他应用的版本号,用于新旧版本的更新
	 * 
	 * @param context
	 * @param packageName
	 * @return int
	 */
	public static int getAppVersionCode(Context context, String packageName) {
		int versionCode = 0;

		if (packageName == null) {
			packageName = context.getPackageName();
		}

		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			versionCode = packageInfo.versionCode;
		} catch (Exception e) {
		}

		return versionCode;
	}

	/**
	 * 取系统SDK版本号
	 * 
	 * @return int
	 */
	public static int getSdkVersion() {
		try {
			return Build.VERSION.SDK_INT;
		} catch (Exception e) {
			return 3;
		}
	}

	/**
	 * 取系统SDK版本号
	 *
	 * @return int
	 */
	public static String getSdkVersionName() {
		try {
			return Build.VERSION.RELEASE;
		} catch (Exception e) {
			return "Android";
		}
	}


	public static boolean isEmulator() {
		return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
	}

	/**
	 * 判断是否已经打开gps定位
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isOpenGPS(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(true);
		criteria.setBearingRequired(true);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			return true;
		} else {
			return false;

		}
	}
	
	/**
	 * 获取 AndroidManifest 下 <strong>meta-data</strong> 的值
	 * 
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	/**
	 * 判断是否已经打开gps定位
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isGPSEnable(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	}

	/**
	 * 判断是否已经打开gps定位,包括网络
	 * 
	 * @param context
	 * @return boolean
	 * 
	 */
	public static boolean isGPSEnable(Context context,boolean orNetGpsEnable ) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
	}
	
	
	/**
	 * 打开GPS设置界面
	 * 
	 * @param context
	 */
	public static void openGPSSetting(Context context) {
		Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		context.startActivity(myIntent);

	}

	
	/**
	 * 创建桌面快捷方式
	 * 
	 * @param context
	 * @param shortcutName
	 *            名称
	 * @param shortcutIconId
	 *            图片资源ID
	 * @param duplicate
	 *            是否多次创建
	 * @param targerClass
	 *            跳转目标ACTIVITY,类名全称
	 * @param params
	 *            需要传的参数
	 */
	public static void createShortcut(Context context, String shortcutName,
			int shortcutIconId, boolean duplicate, String targerClass,
			Intent params) {
		Intent intent = new Intent(CREATE_SHORTCUT_ACTION);
		// 设置快捷方式图片
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource
						.fromContext(context, shortcutIconId));
		// 设置快捷方式名称
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		// 设置是否允许重复创建快捷方式 false表示不允许
		intent.putExtra("duplicate", duplicate);
		// 设置快捷方式要打开的intent
		// 第一种方法创建快捷方式要打开的目标intent
		Intent targetIntent = new Intent();
		if (params != null) {
			targetIntent.putExtras(params);
		}
		// 设置应用程序卸载时同时也删除桌面快捷方式
		targetIntent.setAction(Intent.ACTION_MAIN);
		targetIntent.addCategory("android.intent.category.LAUNCHER");

		ComponentName componentName = new ComponentName(
				context.getPackageName(), targerClass);
		targetIntent.setComponent(componentName);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent);
		// 发送广播
		context.sendBroadcast(intent);
	}

	/**
	 * 删除桌面快捷方式
	 * 
	 * @param context
	 * @param shortcutName
	 *            快捷名称
	 * @param targerClass
	 *            目标ACTIVITY全称
	 */
	public static void deleteShortcut(Context context, String shortcutName,
			String targerClass) {
		Intent intent = new Intent(DROP_SHORTCUT_ACTION);
		// 指定要删除的shortcut名称
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		ComponentName component = new ComponentName(context.getPackageName(),
				targerClass);
		intent.putExtra(
				Intent.EXTRA_SHORTCUT_INTENT,
				new Intent().setAction(Intent.ACTION_MAIN).setComponent(
						component));
		context.sendBroadcast(intent);
	}


	/**
	 * 获取手机型号
	 * 
	 * @return String
	 */
	public static String getPhoneModel() {
		return Build.MODEL;
	}

	/**
	 * 
	 */
	/**
	 * 获取手机牌子
	 * 
	 * @return String
	 */
	public static String getPhoneBrand() {
		return Build.BRAND;
	}

	/**
	 * 获取手机号码
	 */
	public static String getPhoneNumber(Context c) {
		TelephonyManager telephonyManager = (TelephonyManager) c
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
	}



	/**
	 * 卸载程序
	 * 
	 * @param c
	 * @param packageName
	 *            程序包全称
	 */
	public static void uninstallAPK(Context c, String packageName) {
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		c.startActivity(uninstallIntent);
	}

	/**
	 * 打开添加联系人页面
	 * 
	 * @param context
	 */
	public static void openAddContactIntent(Context context) {
		Intent intent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
		context.startActivity(intent);
	}

	/**
	 * 打开web浏览器
	 * 
	 * @param context
	 * @param url
	 *            网页地址
	 */
	public static void openWebIntent(Context context, String url) {
		Uri webUri = Uri.parse(url);
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
		context.startActivity(webIntent);
	}

	/**
	 * 打开发邮件页面
	 * 
	 * @param context
	 * @param email
	 *            邮箱地址
	 */
	public static void openEmailIntent(Context context, String email) {
		Uri uri = Uri.parse("mailto:" + email);
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
		context.startActivity(emailIntent);
	}

	/**
	 * 发送短信
	 * 
	 * @param context
	 * @param phone
	 *            手机号
	 * @param body
	 *            内容
	 */
	public static void sendSms(Context context, String phone, String body) {
		Intent phoneIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"
				+ phone));
		phoneIntent.setType("vnd.android-dir/mms-sms");
		phoneIntent.putExtra("sms_body", body);
		phoneIntent.putExtra("address", phone);
		context.startActivity(phoneIntent);
	}

	/**
	 * 打开短信页
	 * 
	 * @param context
	 * @param phone
	 *            手机号
	 * @param body
	 *            内容
	 */
	public static void openSmsIntent(Context context, String phone, String body) {
		Intent phoneIntent = new Intent(Intent.ACTION_SENDTO,
				Uri.parse("smsto:" + phone));
		phoneIntent.putExtra("sms_body", body);
		phoneIntent.putExtra("address", phone);
		context.startActivity(phoneIntent);
	}

	/**
	 * 拨打电话
	 * 
	 * @param phone
	 *            手机号
	 */
	public static void call(Context context, String phone) {
		context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ phone)));
	};

	/**
	 * 拨打某个电话界面
	 * 
	 * @param phone
	 *            手机号
	 */
	public static void openCallIntent(Context context, String phone) {
		context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ phone)));
	};

	/**
	 * 调用其他外部共享程序
	 * 
	 * @param context
	 * @param packageName
	 *            程序名称
	 */
	public static boolean startOtherApk(Context context, String packageName) {
		PackageManager mager = context.getPackageManager();
		Intent intent = mager.getLaunchIntentForPackage(packageName);
		if (intent != null) {
			context.startActivity(intent);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 调用android market 下载软件
	 * 
	 * @param context
	 * @param packageName
	 *            程序包名
	 * @return
	 */
	public static boolean downloadAppFromMarket(Context context,
			String packageName) {
		Uri uri = Uri.parse("market://details?id=" + packageName);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		try {
			context.startActivity(intent);
			return true;
		} catch (ActivityNotFoundException anfe) {

		}
		return false;
	}

	/**
	 * 显示/隐藏 软键盘
	 */
	public static void showOrVisableSoftInput(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 操作网络开关 需要权限:android.permission.ACCESS_NETWORK_STATE,android.permission.
	 * CHANGE_NETWORK_STATE
	 * 
	 * @param context
	 * @param enabled
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setMobileDataEnabled(Context context, boolean enabled) {
		final String TAG = "setMobileDataEnabled";
		final ConnectivityManager conman = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		Class conmanClass;
		try {
			conmanClass = Class.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass
					.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField
					.get(conman);
			final Class iConnectivityManagerClass = Class
					.forName(iConnectivityManager.getClass().getName());
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass
					.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);

			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		} catch (ClassNotFoundException e) {
			Log.d(TAG, "ClassNotFoundException");
		} catch (NoSuchFieldException e) {
			Log.d(TAG, "NoSuchFieldException");
		} catch (IllegalArgumentException e) {
			Log.d(TAG, "IllegalArgumentException");
		} catch (IllegalAccessException e) {
			Log.d(TAG, "IllegalAccessException");
		} catch (NoSuchMethodException e) {
			Log.d(TAG, "NoSuchMethodException");
		} catch (InvocationTargetException e) {
			Log.d(TAG, "InvocationTargetException");
		} finally {

		}
	}

	/**
	 * MD5加密
	 * 
	 * @param 明文
	 * @return 密文
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}

	public final static String MD5_32bit(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MD5加密 16位
	 * 
	 * @param 明文
	 * @return 密文
	 */
	public static String MD5_16bit(String str, boolean upperCase) {
		String md5_16bit = MD5(str).substring(8, 24);
		;
		return upperCase ? md5_16bit.toUpperCase() : md5_16bit;
	}

	/**
	 * 获取手机牌子
	 */
	public static String getBrand() {
		String brand = new Build().BRAND.toLowerCase();
		if (brand.startsWith("htc"))
			brand = "htc";
		else if (brand.startsWith("moto"))
			brand = "moto";
		return brand;
	}

	/**
	 * 获取手机型号
	 */
	public static String getModel() {
		String model = new Build().MODEL.toLowerCase();
		if (model.startsWith("htc"))
			model = model.replace("htc", "").trim();
		return model;
	}

	/*******************************
	 * 显示与分辨率相关
	 ******************************/

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 获取视频缩略图
	 * 8
	 * @param filePath  视频路径
	 * @return 缩略图
	 */
	public static Bitmap getVideoThumbnail(String filePath) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime();
		} 
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		} 
		catch (RuntimeException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				retriever.release();
			} 
			catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

}
