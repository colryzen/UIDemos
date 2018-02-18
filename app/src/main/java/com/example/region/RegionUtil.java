package com.example.region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Path;
import android.util.Log;

public class RegionUtil {
	public static Path getPloythPath(RegionEntriy regionEntriy,
			RegionItem regionItem) {
		JSONArray ary=regionItem.latlngList;
		Path path = new Path();
		final int length=ary!=null?ary.length():0;
		if(length>0){
			for(int i=0;i<length;i++){
				JSONObject jsonObj=ary.optJSONObject(i);
				double lat=jsonObj.optDouble("lat");
				double lng=jsonObj.optDouble("lng");
				if(i==0){
					path.moveTo((float)regionEntriy.changeX(lng), (float)regionEntriy.changeY(lat));
				}
				path.lineTo((float)regionEntriy.changeX(lng), (float)regionEntriy.changeY(lat));
			}
		}
		path.close();
		return path;
	}
	
	public static Path getPathFromAssets(Context context, RegionEntriy regionEntriy,
			RegionItem regionItem) {
		long btm=System.currentTimeMillis();
		Path path = new Path();
		InputStreamReader inputReader=null;
		String fileName=regionItem.areacode+".txt";
		try {
			 inputReader = new InputStreamReader(context
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			boolean isFristPoint=true;
			while ((line = bufReader.readLine()) != null) {
				String[] point = line.split(",");
				double lat=Double.valueOf(point[1]);
				double lng=Double.valueOf(point[0]);
				if(isFristPoint){
					path.moveTo((float)regionEntriy.changeX(lng), (float)regionEntriy.changeY(lat));
					isFristPoint=false;
				}
				path.lineTo((float)regionEntriy.changeX(lng), (float)regionEntriy.changeY(lat));
			}
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(inputReader!=null){
				try {
					inputReader.close();
					inputReader=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		path.close();
		long etm=System.currentTimeMillis();
		Log.d("SoftUtil", "读取文件："+fileName+" 耗时："+(etm-btm));
		return path;
	}
	
}
