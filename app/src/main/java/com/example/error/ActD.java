package com.example.error;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ActD extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	TextView tv=new TextView(this);
    	tv.setText("测试ActD");
    	setContentView(tv);
    }
}
