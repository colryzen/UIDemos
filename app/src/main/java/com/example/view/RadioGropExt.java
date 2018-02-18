package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Administrator on 2016/12/1.
 */

public class RadioGropExt extends RadioGroup {

    private final String TAG= RadioGropExt.class.getSimpleName();

    private int mLastCheckedId = -1;
    // when true, mOnCheckedChangeListener discards events
    private boolean mProtectFromCheckedChange = false;


    public RadioGropExt(Context context) {
        super(context);
    }
    public RadioGropExt(Context context, AttributeSet attrs) {
        super(context,attrs);
        initView();
    }

    private void initView(){

     }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initChildRadioButton(this);
    }

    private void initChildRadioButton(ViewGroup viewGroup){
        final int childCount= viewGroup.getChildCount();
        for(int i=0;i<childCount;i++){
            //循环View 为 RadioButton 添加监听
            View childView =viewGroup.getChildAt(i);
            if(childView instanceof RadioButton){
                ((RadioButton) childView).setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                             Log.d(TAG, "onCheckedChanged: "+ buttonView.getText()+"  = "+ isChecked);
                                         setCheckButton(buttonView.getId());


                    }
                });
            }else  if(childView instanceof ViewGroup){
                  initChildRadioButton((ViewGroup) childView);
            }
        }
    }

    private void setCheckButton(int checkedId){
        if (mProtectFromCheckedChange) {
            return;
        }
        if (checkedId != -1) {
            mProtectFromCheckedChange = true;
            if(mLastCheckedId!=-1){
                setCheckedStateForView(mLastCheckedId,false);
            }
            setCheckedStateForView(checkedId, true);
            mProtectFromCheckedChange = false;
        }
        check(checkedId);
        mLastCheckedId=checkedId;
    }



    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton) checkedView).setChecked(checked);
        }
    }

}
