package com.example.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;

public class AutoScrOplTextView  extends GLSurfaceView {
    private FontRenderer mrender; 
    public AutoScrOplTextView(Context context) { 
        super(context); 
        // TODO Auto-generated constructor stub 
        //getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mrender = new FontRenderer(); 
        setRenderer(mrender); 
    } 
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }
}