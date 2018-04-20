package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.strongsoft.ui.R;

/**
 * TODO: document your custom view class.
 */
public class WebStatusView extends FrameLayout {
    private String mShowString;
    private int mTextColor = -1;
    private int mImageSize = -1;
    private Drawable mImageDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;


    private TextView mTvShowMsg;
    private ImageView mImgPic;
    private View mRootView;

    public ImageViewOnClikListener getImageViewOnClikListener() {
        return mImageViewOnClikListener;
    }

    public void setImageViewOnClikListener(ImageViewOnClikListener mImageViewOnClikListener) {
        this.mImageViewOnClikListener = mImageViewOnClikListener;
    }

    ImageViewOnClikListener mImageViewOnClikListener;

    public WebStatusView(Context context) {
        super(context);
        init(null, 0);
    }

    public WebStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public WebStatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.WebStatusView, defStyle, 0);

        if (a.hasValue(R.styleable.WebStatusView_showTextString)) {
            mShowString = a.getString(
                    R.styleable.WebStatusView_showTextString);
        }
        if (a.hasValue(R.styleable.WebStatusView_textColor)) {
            mTextColor = a.getColor(
                    R.styleable.WebStatusView_textColor,
                    mTextColor);
        }

        if (a.hasValue(R.styleable.WebStatusView_imageSize)) {

            mImageSize=a.getDimensionPixelOffset(R.styleable.WebStatusView_imageSize,mImageSize);
        }

        if (a.hasValue(R.styleable.WebStatusView_showDrawable)) {
            mImageDrawable = a.getDrawable(
                    R.styleable.WebStatusView_showDrawable);
            mImageDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        initView();


    }

//    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mImageSize);
//        mTextPaint.setColor(mTextColor);
//        mTextWidth = mTextPaint.measureText(mShowString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }

    
    private void initView(){
//        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater.from(getContext()).inflate(R.layout.view_webstatus_layout,this);
        mTvShowMsg= (TextView) findViewById(R.id.tv_error_msg);
        mImgPic=(ImageView)findViewById(R.id.img_pic);
        if(!TextUtils.isEmpty(mShowString)){
            mTvShowMsg.setText(mShowString);
        }
        
        if(mImageDrawable!=null){
            mImgPic.setImageDrawable(mImageDrawable);
        }

        if(mImageSize!=-1){
            ViewGroup.LayoutParams  layoutParams= mImgPic.getLayoutParams();
            layoutParams.height=mImageSize;
            layoutParams.width=mImageSize;
            mImgPic.setLayoutParams(layoutParams);
        }

         mImgPic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mImageViewOnClikListener!=null){
                    mImageViewOnClikListener.onClick(v);
                }
            }
        });
    }
    


    public  interface  ImageViewOnClikListener{
        void onClick(View v);
    }

}
