package com.example.error;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.strongsoft.ui.R;

public class ActC extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	TextView tv=new TextView(this);
    	tv.setText("测试ActC");
    	setContentView(tv);

		setContentView(R.layout.activity_error_c_test);
		Button btn= (Button) findViewById(R.id.btn1);
		btn.setText("返回a ： C--》B—》A");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
    }
}
