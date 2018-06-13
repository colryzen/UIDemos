package com.example.error;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.AndroidUtil;
import com.strongsoft.ui.R;

public class ActA extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	TextView tv=new TextView(this);
    	tv.setText("测试ActA: 系统版本号："+ AndroidUtil.getSdkVersionName());

    	tv.setText(Html.fromHtml("<p><a href=\"http://www.microsoft.com/\">本文本</a> 是一个指向万维网上的页面的链接。</p>"));
    	tv.append(Html.fromHtml("http://www.baidu.com"));
    	setContentView(tv);
//        Button btn= (Button) findViewById(R.id.btn1);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it =new Intent(ActA.this,ActB.class);
//                startActivityForResult(it,1);
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Toast.makeText(this,"回掉A，",Toast.LENGTH_SHORT).show();
        }
    }
}
