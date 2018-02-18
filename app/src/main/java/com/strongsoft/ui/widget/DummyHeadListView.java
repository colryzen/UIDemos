package com.strongsoft.ui.widget;



import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class DummyHeadListView extends ListView {
 private View mDumyGroupView;
 private boolean mHeaderVisible = false;

 public DummyHeadListView(Context context) {
     super(context);
 }

 public DummyHeadListView(Context context, AttributeSet attrs) {
     super(context, attrs);
 }

 public void setDumyGroupView(View view) {
     this.mDumyGroupView = view;
     if(this.mDumyGroupView != null) {
         this.mDumyGroupView.setVisibility(View.GONE);
         this.setFadingEdgeLength(0);
     }

     this.requestLayout();
 }

 protected void dispatchDraw(Canvas canvas) {
     super.dispatchDraw(canvas);
 }

 public void setHeaderVisible(boolean mHeaderVisible) {
     this.mHeaderVisible = mHeaderVisible;
     if(mHeaderVisible) {
         if(this.mDumyGroupView != null) {
             this.mDumyGroupView.setVisibility(View.VISIBLE);
         }
     } else if(this.mDumyGroupView != null) {
         this.mDumyGroupView.setVisibility(View.GONE);
     }

 }

 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
 }
}

