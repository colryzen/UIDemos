package com.example.region;

import org.json.JSONArray;

import android.graphics.Path;
import android.graphics.Region;

public class RegionItem {
	 Region region ;
	 Path  path;
	 String areacode;	//区域编码 
	 String areaname;   //区域名字
	 int color; 		//颜色
	 JSONArray latlngList;
	 //文字
	 String title ;
	 String tilteLatLng; 
	 int titleColor;
	 
}
