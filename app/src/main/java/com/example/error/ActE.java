package com.example.error;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ActE extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	TextView tv=new TextView(this);
    	tv.setText("测试ActE");
    	if(getIntent()!=null){
    		String  data=getIntent().getData().toString();
    		tv.append(" uri="+data);
		}
    	setContentView(tv);



    }
}
