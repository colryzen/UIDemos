package com.example.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.strongsoft.ui.R;

/**
 *多选列表
 * 
 * @author rkr
 * 
 */
public class CheckboxListView extends LinearLayout {
	private Context mContext;
	private View mRootView;
	private LinearLayout mLlyContainer;

	public CheckboxListView(Context context) {
		super(context);
		mContext = context;
	}

	public CheckboxListView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater layoutInflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRootView = layoutInflater.inflate(
				R.layout.temp_checkbox_list_layout, this, true);
		mLlyContainer=(LinearLayout) mRootView.findViewById(R.id.lly_container);
		
	}

	/**
	 * 设置
	 * @param values
	 */
	public void setCheckBoxValues(String[] values) {
		final int length = values != null ? values.length : 0;
		for (int i = 0; i < length; i++) {
			CheckBox cb = new CheckBox(mContext);
			cb.setText(values[i]);
			cb.setChecked(false);
			mLlyContainer.addView(cb);
		}
	}
   
	public ArrayList<String> getCheckValues(){
		ArrayList<String>  values= new ArrayList<String>();
		final int childCount=mLlyContainer.getChildCount();
		for(int i=0;i<childCount;i++){
             View view=mLlyContainer.getChildAt(i);
             if(view instanceof CheckBox){
            	 CheckBox cb =(CheckBox) view;
            	  if(cb.isChecked()){
            		  values.add(cb.getText().toString());
            	  }
             }
		}
		return values;
	}
	
}
