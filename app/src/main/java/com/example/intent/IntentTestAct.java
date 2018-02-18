package com.example.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.error.ActA;
import com.example.util.AndroidUtil;
import com.strongsoft.ui.R;

public class IntentTestAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_intent);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                // action_send without category
                Intent it = new Intent();
                it.setAction("com.strongsoft.action.send");
                startActivity(it.createChooser(it, "请选择"));
                // ActA
                break;
            case R.id.btn2:
                // action_send category
                it = new Intent();
                it.setAction("com.strongsoft.action.send");
                it.addCategory("com.strongsoft.category.main");
                startActivity(it.createChooser(it, "请选择"));
                // 无匹配项 ，Intent 会默认加入DEFAULT
                break;
            case R.id.btn3:
                // action_edit without category
                it = new Intent();
                it.setAction("com.strongsoft.action.edit");
                startActivity(it.createChooser(it, "请选择"));
                // ActA ActD
                break;
            case R.id.btn4:
                // action_edit category
                it = new Intent();
                it.setAction("com.strongsoft.action.edit");
                it.addCategory("com.strongsoft.category.main");
                startActivity(it.createChooser(it, "请选择"));
                // ActD
                break;

            case R.id.btn5:
                // action_edit category
                String packageName = "com.sina.weibo";
                packageName = "net.strongsoft.shzh";
                // packageName="com.baidu.BaiduMap";
                PackageManager mager = getPackageManager();
                Intent intent = mager.getLaunchIntentForPackage(packageName);
                // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent != null) {
                    startActivity(intent);
                } else {

                }
                // ActD
                break;

            case R.id.btn6:
                // action_edit category
                String packageName2 = "net.strongsoft.fxt_v3";
                AndroidUtil.uninstallAPK(IntentTestAct.this, packageName2);
                // ActD
                break;
            case R.id.btn7:
                ComponentName componentName = new ComponentName(
                        "net.strongsoft.inspection",
                        "net.strongsoft.inspection.activity.MainActivity_");
                intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("resUrl", "http://www.baidu.com");
                intent.putExtras(bundle);
                intent.setComponent(componentName);
                startActivity(intent.createChooser(intent, "请选择"));
                break;
            case R.id.btn8:
                it = new Intent();
                it.setAction("net.strongsoft.action.skxc");
                bundle = new Bundle();
                bundle.putString("ID", "K001");
                bundle.putString("NAME", "K");
                it.putExtras(bundle);
                startActivity(it.createChooser(it, "请选择"));
                break;
            case R.id.btn9:
                intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS,
                        Uri.parse("package:" + AndroidUtil.getAppName(this)));
                startActivity(intent);
                break;
            case R.id.btn10:
                intent = new Intent(this, ActA.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
