package com.example.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;

@SuppressLint("NewApi")
public class BitmapUtil {
	
	public int getBitmapSize(Bitmap bitmap){
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){    //API 19
	        return bitmap.getAllocationByteCount();
	    }
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
	        return bitmap.getByteCount();
	    }
	    return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
	}
}
