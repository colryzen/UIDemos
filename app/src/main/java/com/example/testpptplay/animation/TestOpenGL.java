package com.example.testpptplay.animation;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.opengl.FontRenderer;

public class TestOpenGL extends Activity {
	 GLSurfaceView gView ;  
	   FontRenderer fontRenderer;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.test_opengl);
		   gView = new GLSurfaceView(this);  
	        fontRenderer = new FontRenderer();  
	        gView.setRenderer(fontRenderer);  
	        setContentView(gView); 
		
	}
}
