package com.example.openother;

import android.app.Activity;
import android.os.Bundle;

import com.example.view.CheckboxListView;
import com.strongsoft.ui.R;

public class OpenOtherAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_main);
		CheckboxListView cbList= (CheckboxListView)findViewById(R.id.cb_list);
		String [] strary=new String[] {"we","are"};
		cbList.setCheckBoxValues(strary);
		
		System.out.println("");
		// Intent intent = new Intent(Intent.ACTION_VIEW);
		// String packageName = "net.strongsoft.shzh.hainan";
		// String className = "com.lazytech.projecta.MainActivity";
		// intent.setClassName(packageName, className);
	}
}
