package com.example.screen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;

import java.lang.reflect.Field;

public class ScreenUtil {

	/**
	 * 获取标题栏的高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getTitleHeight(Activity activity) {
		Rect rect = new Rect();
		Window window = activity.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;
		int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		int titleBarHeight = contentViewTop - statusBarHeight;

		return titleBarHeight;
	}
	
	
	
	 /** 
	    *  
	    * 获取状态栏高度 
	    *  
	    * @param activity 
	    * @return 
	    */  
	   public static int getStateHeight(Activity activity) {  
	       Rect rect = new Rect();  
	       activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);  
	       return rect.top;  
	   } 



	 
	     /** 
	      * 获取屏幕宽高 
	      *  
	      * @param activity 
	      * @return int[0] 宽，int[1]高 
	      */  
	     public  static int[] getScreenWidthAndSizeInPx(Activity activity) {  
	         DisplayMetrics displayMetrics = new DisplayMetrics();  
	         activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);  
	         int[] size = new int[2];  
	         size[0] = displayMetrics.widthPixels;  
	         size[1] = displayMetrics.heightPixels;  
	         return size;  
	     } 
	     
	     
	      /** 
	       * 模拟home键 
	       *  
	       * @param context 
	       */  
	      public static void goToDestop(Context context) {  
	          Intent intent = new Intent(Intent.ACTION_MAIN);  
	          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	          intent.addCategory(Intent.CATEGORY_HOME);  
	          context.startActivity(intent);  
	      }  
	      
	      /**
	       * 获得 navigationBar 高度
	       * @param activity
	       * @return
	       */
	      public static int getNavigationBarHeight(Activity activity) {  
	          Resources resources = activity.getResources();  
	          int resourceId = resources.getIdentifier("navigation_bar_height",  
	                  "dimen", "android");  
	          //获取NavigationBar的高度  
	          int height = resources.getDimensionPixelSize(resourceId);  
	          return height;  
	      }

	   // get the hight of StatusBar
	   public static int getStatusBarHeight(Context context) {
		Resources resources = context.getResources();
		int result = 0;
		int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
			result = resources.getDimensionPixelSize(resId);
		}
		return result;
	}


	public static int getNavigationBarHeight2(Context context){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("navigation_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

}
