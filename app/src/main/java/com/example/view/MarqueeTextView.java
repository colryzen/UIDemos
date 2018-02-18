package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
 
/**
 * 自定义跑马灯文本框，支持拖拽查看文本内容，点击暂停文字 先设置要显示文本，然后调用Start()方法运行跑马灯
 *
 * @author sy
 */
public class MarqueeTextView extends TextView implements Runnable
         {
    public MarqueeTextView(Context context) {
        super(context);
    }
 
    /** 是否停止滚动 */
    private boolean mStopMarquee;
    private String mText;
    public int mCoordinateX;
    private int mTextWidth;
 
    private int mWidth;
    private boolean  isFrist=true;
    private Handler mHandler=null;
    
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    /**
     * 开始滚动
     *
     * @param text
     */
    public void Start() {
        mStopMarquee = false;
        mCoordinateX=-mWidth;
        mHandler=new Handler();
        mText = this.getText().toString();// 获取文本框文本
        Log.d("-----------", "mText="+mText + " weight="+ getWidth());
        mTextWidth = (int) Math.abs(getPaint().measureText(mText));
        Log.d("------mTextWidth----", "mTextWidth="+mTextWidth);
        post(this);
    }
 
    @Override
    public void run() {
        if (!mStopMarquee) {
            mCoordinateX += 5;// 滚动速度
            scrollTo(mCoordinateX, 0);
            if (mCoordinateX > mTextWidth) {
                scrollTo(0, 0);
             //   mCoordinateX = -mWidth;
            }
            postDelayed(this, 50);
        }
    }
 
    // 继续滚动
    public void Continue() {
        if (mStopMarquee) {
            mStopMarquee = false;
            post(this);
        }
    }
 
    // 暂停滚动
    public void Paush() {
        mStopMarquee = true;
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
    	// TODO Auto-generated method stub
    	super.onDraw(canvas);
    //	Log.d("-------onDraw----", "onDraw()");
    	if(isFrist){
    		isFrist=false;
    		mWidth=getWidth();
    		Log.d("-------onDrawWidth----", "mWidth="+mWidth);
    	}
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
    		int bottom) {
    	// TODO Auto-generated method stub
    	super.onLayout(changed, left, top, right, bottom);
    	Log.d("-------onLayout----", "onLayout()  changed="+changed);
    }
    
} 