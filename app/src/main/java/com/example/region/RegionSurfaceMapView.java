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
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RegionSurfaceMapView extends SurfaceView implements
		SurfaceHolder.Callback {
	private final String TAG=RegionMapView.class.getSimpleName();
	
	private Context mContext;
	private LunarThread thread;

	private RegionEntriy mRegionEntriy;

	private ArrayList<RegionItem> mRegionList = new ArrayList<RegionItem>();

	private String mCurrentAreacode = "";
	
	private boolean isPress=false;
	private boolean hasDrawMark=false;

	public RegionSurfaceMapView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		// create thread only; it's started in surfaceCreated()
		thread = new LunarThread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {

			}
		});
		setFocusable(true); // make sure we get key events
	}

	public RegionSurfaceMapView(Context context) {
		super(context);
		doTest();
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		// create thread only; it's started in surfaceCreated()
		thread = new LunarThread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {

			}
		});
		setFocusable(true);
	}

	/**
	 * Fetches the animation thread corresponding to this LunarView.
	 *
	 * @return the animation thread
	 */
	public LunarThread getThread() {
		return thread;
	}

	/* Callback invoked when the surface dimensions change. */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	/*
	 * Callback invoked when the Surface has been created and is ready to be
	 * used.
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated!!!");
		if(thread.isAlive()){
			thread.setRunning(false);
			thread.interrupt();
		}
//		setZOrderOnTop(true);
//		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		thread.setRunning(true);
		thread.start();
		
	}

	/*
	 * Callback invoked when the Surface has been destroyed and must no longer
	 * be touched. WARNING: after this method returns, the Surface/Canvas must
	 * never be touched again!
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed!!!");
		thread.setRunning(false);
		thread.interrupt();
	}

	class LunarThread extends Thread {
		/**
		 * Current height of the surface/canvas.
		 *
		 * @see #setSurfaceSize
		 */
		private int mCanvasHeight = 1;
		/**
		 * Current width of the surface/canvas.
		 *
		 * @see #setSurfaceSize
		 */
		private int mCanvasWidth = 1;
		/** Message handler used by thread to interact with TextView */
		private Handler mHandler;
		/** Indicate whether the surface has been created & is ready to draw */
		private boolean mRun = false;
		private final Object mRunLock = new Object();
		/** Handle to the surface manager object we interact with */
		private SurfaceHolder mSurfaceHolder;

		public LunarThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			// get handles to some important objects
			mSurfaceHolder = surfaceHolder;
			mHandler = handler;
			mContext = context;
		}

		public synchronized void restoreState(Bundle savedState) {
			synchronized (mSurfaceHolder) {

			}
		}

		@Override
		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						synchronized (mRunLock) {
							if (mRun)
								doDraw(c);
						}
					}
				} finally {
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
//					try {
//						Thread.sleep(20);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//						mRun = false;
//					}
				}
			}
		}

		public Bundle saveState(Bundle map) {
			synchronized (mSurfaceHolder) {
				if (map != null) {

				}
			}
			return map;
		}

		/**
		 * Used to signal the thread whether it should be running or not.
		 * Passing true allows the thread to run; passing false will shut it
		 * down if it's already running. Calling start() after this was most
		 * recently called with false will result in an immediate shutdown.
		 *
		 * @param b
		 *            true to run, false to shut down
		 */
		public void setRunning(boolean b) {
			// Do not allow mRun to be modified while any canvas operations
			// are potentially in-flight. See doDraw().
			synchronized (mRunLock) {
				mRun = b;
			}
		}

		public void setSurfaceSize(int width, int height) {
			synchronized (mSurfaceHolder) {
				mCanvasWidth = width;
				mCanvasHeight = height;
			}
		}

		private void doDraw(Canvas canvas) {
			long btm=System.currentTimeMillis();
			canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
			// canvas.save();
			final int length = mRegionList != null ? mRegionList.size() : 0;
			for (int i = 0; i < length; i++) {
				drawPloyLine(canvas, mRegionList.get(i));
			}
			// 绘制文字
			for (int i = 0; i < length; i++) {
				drawTextRegionCenter(canvas, mRegionList.get(i));
			}
			
//			canvas.translate(0, 300);
//			for (int i = 0; i < length; i++) {
//				drawPloyLine(canvas, mRegionList.get(i));
//			}
//			// 绘制文字
//			for (int i = 0; i < length; i++) {
//				drawTextRegionCenter(canvas, mRegionList.get(i));
//			}
//			
//			canvas.translate(150, 1000);
//			for (int i = 0; i < length; i++) {
//				drawPloyLine(canvas, mRegionList.get(i));
//			}
//			// 绘制文字
//			for (int i = 0; i < length; i++) {
//				drawTextRegionCenter(canvas, mRegionList.get(i));
//			}
//			
//			canvas.translate(150, 1800);
//			for (int i = 0; i < length; i++) {
//				drawPloyLine(canvas, mRegionList.get(i));
//			}
//			// 绘制文字
//			for (int i = 0; i < length; i++) {
//				drawTextRegionCenter(canvas, mRegionList.get(i));
//			}
//			
//			
			
			long etm=System.currentTimeMillis();
			Log.d(TAG, "绘制耗时："+(etm-btm));
			
			// canvas.restore();
		}
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
		
		
		Rect rect = regionItem.region.getBounds();
		float x = (float) ((rect.left + rect.right) / 2 + 0.5);
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

	/**
	 * 触点事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int length = mRegionList != null ? mRegionList.size() : 0;
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			for (int i = 0; i < length; i++) {
				RegionItem regionItem = mRegionList.get(i);
				Region rgn = regionItem.region;
				if (rgn != null
						&& rgn.contains((int) event.getX(), (int) event.getY())) {
					//选中区域
					mCurrentAreacode = regionItem.areacode;
					isPress=true;
					return true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		    isPress=false;
		    mCurrentAreacode="";
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
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

	/***
	 * 初始化整个地图的基本属性
	 */
	private void initReginEntriy() {
		mRegionEntriy = new RegionEntriy();
		mRegionEntriy.scrRight = 1000;
		mRegionEntriy.scrBottom = 830;
	}
}
