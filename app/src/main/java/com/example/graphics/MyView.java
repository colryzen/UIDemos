package com.example.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.strongsoft.ui.R;

public class MyView extends View {

	private Bitmap mBitmap;
	private Matrix mMatrix = new Matrix();

	public MyView(Context context) {
		super(context);
		initialize();
	}

	private void initialize() {

		Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.ic_search)).getBitmap();
		mBitmap = bmp;
		/*
		 * 首先，将缩放为100*100。这里scale的参数是比例。有一点要注意，如果直接用100/
		 * bmp.getWidth()的话，会得到0，因为是整型相除，所以必须其中有一个是float型的，直接用100f就好。
		 */
		mMatrix.setScale(100f / bmp.getWidth(), 100f / bmp.getHeight());
		// 平移到（100，100）处
		mMatrix.postTranslate(100, 100);
		// 倾斜x和y轴，以（100，100）为中心。
		mMatrix.postSkew(0.2f, 0.2f, 100, 100);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas); //如果界面上还有其他元素需要绘制，只需要将这句话写上就行了。
		canvas.drawBitmap(mBitmap, mMatrix, null);
	}
}
