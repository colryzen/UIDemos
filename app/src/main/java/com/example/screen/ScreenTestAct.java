package com.example.screen;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.strongsoft.ui.R;

public class ScreenTestAct extends Activity {
	
	TextView mTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen);
		mTv=(TextView) findViewById(R.id.tv_screen);
		
		
		DisplayMetrics dm = new DisplayMetrics();  

	    
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		String scrMsg="dm.density=" + dm.density + " dm.widthPixels="
				+ dm.widthPixels + " dm.heightPixels=" + dm.heightPixels
				+ " dm.densityDpi=" + dm.densityDpi + " dm.scaledDensity="
				+ dm.scaledDensity;
	    
 	   int screen[] =ScreenUtil.getScreenWidthAndSizeInPx(this);
 	   StringBuffer strBuf=new StringBuffer();
 	   strBuf.append("屏幕  width="+screen[0] +" , heigh="+ screen[1]).append("\n");
 	   strBuf.append("标题栏高度："+ScreenUtil.getTitleHeight(this)).append("\n");
 	   strBuf.append("状态栏高度 高度："+ScreenUtil.getStateHeight(this)).append("\n");
		strBuf.append("状态栏高度 高度2："+ScreenUtil.getStatusBarHeight(this)).append("\n");
 	   strBuf.append("NavigationBar高度："+ScreenUtil.getNavigationBarHeight(this)).append("\n");
		strBuf.append("NavigationBar高度（方法2）："+ScreenUtil.getNavigationBarHeight2(this)).append("\n");
 	   strBuf.append("其他信息:"+scrMsg).append("\n");;



		Context c = this;
		Resources r;

		if (c == null)
			r = Resources.getSystem();
		else
			r = c.getResources();

		DisplayMetrics dm2=r.getDisplayMetrics();

		String scrMsg_2="dm.density=" + dm2.density + " dm2.widthPixels="
				+ dm.widthPixels + " dm.heightPixels=" + dm2.heightPixels
				+ " dm2.densityDpi=" + dm2.densityDpi + " dm2.scaledDensity="
				+ dm.scaledDensity;

		strBuf.append("其他信息2:"+scrMsg_2).append("\n");

		Configuration conf = getResources().getConfiguration();
		strBuf.append("字体信息："+conf.fontScale).append("\n");

		mTv.setText(strBuf.toString());
 	    System.out.println( strBuf.toString() );
		
	}
}
