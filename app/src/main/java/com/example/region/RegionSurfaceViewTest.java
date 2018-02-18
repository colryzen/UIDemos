package com.example.region;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable.Callback;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RegionSurfaceViewTest extends SurfaceView implements Callback,Runnable, android.view.SurfaceHolder.Callback {
    Paint paint;
    SurfaceHolder surfaceHolder;
    Thread thread;
    boolean flag;
    int sleeptime=100;
    boolean isCollsion;
    /*定义一个矩形*/
    Rect rect=new Rect(0, 0, 100, 100);
    /*定义一个Region*/
    Region region=new Region(rect);
    public RegionSurfaceViewTest(Context context) {
        super(context);
        paint=new Paint();
        paint.setStyle(Style.STROKE);
        paint.setAntiAlias(true);
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);    
        thread=new Thread(this);
    }
    
    private void mydraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(rect, paint);
        if(isCollsion){
            paint.setColor(Color.RED);
            canvas.drawText("点击碰撞了", 10, 10, paint);
         
        }
        else{
            paint.setColor(Color.BLACK);
            canvas.drawText("没有碰撞了", 10, 10, paint);
        }
    }
    
    
    /**
     * 触点事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(region.contains((int)event.getX(), (int)event.getY())){
            isCollsion=true;
        }
        else{
            isCollsion=false;
        }
        return super.onTouchEvent(event);
    }
    
    @Override
    public void run() {
        Canvas canvas=null;
        while (flag) {
            try {
                canvas=surfaceHolder.lockCanvas();
                synchronized (canvas) {
                    mydraw(canvas);
                }
            } catch (Exception e) {
                Log.e("region",e.getMessage());
            }
            finally{
                if(canvas!=null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(sleeptime);
            } catch (Exception e) {
                Log.e("region",e.getMessage());
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag=true;
        if(!thread.isAlive()){
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag=false;
        
    }

}
