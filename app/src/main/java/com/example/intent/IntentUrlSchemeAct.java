package com.example.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.error.ActB;
import com.strongsoft.ui.R;

public class IntentUrlSchemeAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
//         final String uriStr="istrong.fxy://istrong.com/login?username=15659134597&password=12345678&orgId=jg2017001&appId=cp2017004";
//         final String uriStr="istrong.nbyxt://com.istrong.nbyxt.sea/bdchat?model_name=北斗消息";

     //   final String uriStr="istrong.nbyxt://com.istrong.nbyxt.sea/publish?model_name=北斗发供应信息";



     //   final String uriStr="istrong.jsy://jsy.istrong.com/login";


        final String  uriStr="www.istrong.nbyxt://com.istrong.nbyxt.sea/callPhone?phoneNumber=15659134597";

    	setContentView(R.layout.activity_error_a_test);
        TextView tv=(TextView)findViewById(R.id.tv);
        tv.setText("测试URLScheme 跳转： "+uriStr);
        Button btn= (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriStr));
                startActivity(intent.createChooser(intent, "请选择"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Toast.makeText(this,"回掉A，",Toast.LENGTH_SHORT).show();
        }
    }
}
