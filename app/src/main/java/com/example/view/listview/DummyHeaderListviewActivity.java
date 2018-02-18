package com.example.view.listview;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.strongsoft.ui.R;
import com.strongsoft.ui.widget.DummyHeadListView;


public class DummyHeaderListviewActivity extends Activity {

	DummyHeadListView mListview;
	
	boolean mIsShow=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_main);
		mListview=(DummyHeadListView)findViewById(R.id.list);
		
		TextView tv= new TextView(this);
		tv.setTextColor(Color.RED);
		tv.setText("分组");
		tv.setBackgroundColor(Color.CYAN);
		mListview.setSelectionFromTop(0, 0);
		
		LinearLayout.LayoutParams  params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(params);
		mListview.setDumyGroupView(tv);
		mListview.setHeaderVisible(true);
		
		ArrayList<String> list =new ArrayList<String>(6);
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		
		ArrayAdapter<String> adatAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		mListview.setAdapter(adatAdapter);
		
	}
	
   public void	onClick(View v){
	       Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
	       mIsShow=!mIsShow;
		   mListview.setHeaderVisible(mIsShow);
	}
}
