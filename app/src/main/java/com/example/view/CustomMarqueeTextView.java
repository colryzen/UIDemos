package com.example.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 
 * 一直走马灯效果控件
 * 
 * @author zuolongsnail
 */
public class CustomMarqueeTextView extends AppCompatTextView {

  
	public CustomMarqueeTextView(Context context) {
		super(context);
		createView();
	}

	public CustomMarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		createView();
	}

	public CustomMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		createView();
	}

	private void createView() {
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
		setFocusableInTouchMode(true);
		
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		if (focused) {
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean focused) {
		if (focused) {
			super.onWindowFocusChanged(focused);
		}
	}

	@Override
	public boolean isFocused() {
		return true;
	}
	
	private void marquee(){
		String text =getText().toString();
		setText(text);
	}
	
	
}