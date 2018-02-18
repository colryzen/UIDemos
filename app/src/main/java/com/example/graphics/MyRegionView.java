package com.example.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MyRegionView extends View {

	Region rgn = null;
	boolean isCollsion;
	Context mContext;

	public MyRegionView(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);
        
		
		// 初始化Paint
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		
		// 构造一个椭圆路径
//		Path ovalPath = new Path();
//		RectF rect = new RectF(50, 50, 500, 500);
//		ovalPath.addOval(rect, Direction.CCW);
//		// SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集
//		rgn = new Region();
//		rgn.setPath(ovalPath, new Region(50, 50, 500, 1000));
//		// 画出路径
//		drawRegion(canvas, rgn, paint);

		// 梯形
		Path tiPath = getMutilPath();
		// SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集
		rgn = new Region();
		rgn.setPath(tiPath, new Region(50, 50, 500, 500));
		// 画出路径
		drawRegion(canvas, rgn, paint);

	}

	// 这个函数不懂没关系，下面会细讲
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
		if (rgn.contains((int) event.getX(), (int) event.getY())) {
			isCollsion = true;
			Toast.makeText(mContext, "点击了区域内", Toast.LENGTH_SHORT).show();
		} else {
			isCollsion = false;
			Toast.makeText(mContext, "点击了区域外", Toast.LENGTH_SHORT).show();
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
	
	private Path getTrianglePath() {
		Path path4 = new Path();

		path4.moveTo(170, 330);

		path4.lineTo(230, 330);

		path4.lineTo(200, 270);
		path4.close();

		return path4;
	}


	private Path getMutilPath() {
		Path path = new Path();
		path.moveTo(100, 300);
		path.lineTo(230, 330);
		path.lineTo(260, 370);
		path.lineTo(235, 350);
		path.lineTo(330, 470);
		path.lineTo(330, 570);
		path.lineTo(230, 570);
		path.lineTo(230, 470);
	    path.close();
		return path;
	}
	
}