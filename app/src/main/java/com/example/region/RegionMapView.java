/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.region;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class RegionMapView extends View {

	private final String TAG=RegionMapView.class.getSimpleName();
	
	private Context mContext;

	private RegionEntriy mRegionEntriy;

	private ArrayList<RegionItem> mRegionList = new ArrayList<RegionItem>();

	private String mCurrentAreacode = "";

	public RegionMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		setFocusable(true); // make sure we get key events
	}

	public RegionMapView(Context context) {
		super(context);
		mContext=context;
		doTest();
		setFocusable(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		doDraw(canvas);
	}
	
	private void doDraw(Canvas canvas) {
		long btm=System.currentTimeMillis();
		canvas.drawColor(Color.TRANSPARENT);
		// canvas.save();
		final int length = mRegionList != null ? mRegionList.size() : 0;
		for (int i = 0; i < length; i++) {
			drawPloyLine(canvas, mRegionList.get(i));
		}
		// 绘制文字
		for (int i = 0; i < length; i++) {
			drawTextRegionCenter(canvas, mRegionList.get(i));
		}
		// canvas.restore();
		long etm=System.currentTimeMillis();
		Log.d(TAG, "绘制耗时："+(etm-btm));
	}

	/**
	 * 绘制边界轮廓
	 * 
	 * @param canvas
	 * @param regionItem
	 */
	private void drawPloyLine(Canvas canvas, RegionItem regionItem) {
		Paint paint = new Paint();
		paint.setColor(mCurrentAreacode.equals(regionItem.areacode) ? Color.YELLOW
				: regionItem.color);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(2);
		if (regionItem.region == null) {
			regionItem.region = new Region();
		}
		Path path = regionItem.path;
		if (path == null) {
			path = RegionUtil.getPathFromAssets(mContext, mRegionEntriy,
					regionItem);
		}
		regionItem.region.setPath(path, new Region((int) mRegionEntriy.scrLeft,
				(int) mRegionEntriy.scrTop, (int) mRegionEntriy.scrRight,
				(int) mRegionEntriy.scrBottom));
		drawRegion(canvas, regionItem.region, paint);
	}

	
	/**
	 * 绘制行政区划文字
	 * 
	 * @param canvas
	 * @param regionItem
	 */
	private void drawTextRegionCenter(Canvas canvas, RegionItem regionItem) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(24);
		paint.setStyle(Style.FILL);
		
		if (regionItem.region == null) {
			return;
		}
		float textWidth=paint.measureText(regionItem.areaname); 
		Rect rect = regionItem.region.getBounds();
		float x = (float) ((rect.left + rect.right-textWidth)/ 2  + 0.5);
		float y = (float) ((rect.top + rect.bottom) / 2 + 0.5);
		canvas.drawText(regionItem.areaname, x, y, paint);
	}
	
	private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
		RegionIterator iter = new RegionIterator(rgn);
		Rect r = new Rect();

		while (iter.next(r)) {
			canvas.drawRect(r, paint);
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int length = mRegionList != null ? mRegionList.size() : 0;
		int action = event.getActionMasked();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, "ACTION_DOWN!!!");
//			for (int i = 0; i < length; i++) {
//				RegionItem regionItem = mRegionList.get(i);
//				Region rgn = regionItem.region;
//				if (rgn != null
//						&& rgn.contains((int) event.getX(), (int) event.getY())) {
//					//选中区域
//					mCurrentAreacode = regionItem.areacode;
//					return true;
//				}
//			}
			return true;
		case MotionEvent.ACTION_UP:
			Log.d(TAG, "ACTION_UP!!!");
			mCurrentAreacode = "";
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, "ACTION_MOVE!!!");
			mCurrentAreacode = "";
			break;
		default:
			break;
		}
		invalidate();
		return super.onTouchEvent(event);
	}
	
	
	/***
	 * 初始化整个地图的基本属性
	 */
	private void initReginEntriy() {
		mRegionEntriy = new RegionEntriy();
		mRegionEntriy.scrRight = 1000;
		mRegionEntriy.scrBottom = 830;
	}
	

	private void doTest() {
		initReginEntriy();
		RegionItem regionItem = new RegionItem();
		regionItem.areacode = "469021";
		regionItem.areaname = "定安";
		regionItem.color = Color.argb(200, 208, 140, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469025";
		regionItem.areaname = "白沙";
		regionItem.color = Color.argb(200, 208, 120, 240);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469029";
		regionItem.areaname = "保亭";
		regionItem.color = Color.argb(200, 228, 120, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469026";
		regionItem.areaname = "昌江";
		regionItem.color = Color.argb(200, 208, 110, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469023";
		regionItem.areaname = "澄迈";
		regionItem.color = Color.argb(200, 218, 100, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "460400";
		regionItem.areaname = "儋州";
		regionItem.color =Color.argb(200, 228, 140, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469007";
		regionItem.areaname = "东方";
		regionItem.color = Color.argb(200, 208, 140, 240);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "460100";
		regionItem.areaname = "海口";
		regionItem.color = Color.argb(200, 228, 120, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469027";
		regionItem.areaname = "乐东";
		regionItem.color = Color.argb(200, 208, 130, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469024";
		regionItem.areaname = "临高";
		regionItem.color = Color.argb(200, 208, 100, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469028";
		regionItem.areaname = "陵水";
		regionItem.color = Color.argb(200, 208, 140, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469002";
		regionItem.areaname = "琼海";
		regionItem.color = Color.argb(200, 208, 130, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469030";
		regionItem.areaname = "琼中";
		regionItem.color = Color.argb(200, 218, 120, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "460200";
		regionItem.areaname = "三亚";
		regionItem.color = Color.argb(200, 208, 110, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469022";
		regionItem.areaname = "屯昌";
		regionItem.color = Color.argb(200, 208, 140, 250);
		mRegionList.add(regionItem);
		
		regionItem = new RegionItem();
		regionItem.areacode = "469006";
		regionItem.areaname = "万宁";
		regionItem.color = Color.argb(200, 190, 130, 250);
		mRegionList.add(regionItem);

		regionItem = new RegionItem();
		regionItem.areacode = "469005";
		regionItem.areaname = "文昌";
		regionItem.color = Color.argb(200, 208, 120, 250);
		mRegionList.add(regionItem);
		

		regionItem = new RegionItem();
		regionItem.areacode = "469001";
		regionItem.areaname = "五指山";
		regionItem.color = Color.argb(200, 180, 110, 250);
		mRegionList.add(regionItem);
	}
	
	
	
}
