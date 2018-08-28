package com.example.install;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.util.AndroidUtil;
import com.strongsoft.ui.R;

public class InstallActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_install);
	}

	public void click(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			// action_send without category
			Intent it = new Intent();
			String packageName = "com.sina.weibo";
//			packageName = "net.strongsoft.shzh";
			packageName = "com.strongsoft.fjfxt_v2";
			PackageManager mager = getPackageManager();
			Intent intent = mager.getLaunchIntentForPackage(packageName);
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (intent != null) {
				startActivity(intent);
			} else {

			}
			// ActD
			break;
		case R.id.btn2:
			String packageName2 = "cloud.app.yingyanggaishanyun";
			packageName2="com.strongsoft.ui";
			mager = InstallActivity.this.getPackageManager();
			intent = mager.getLaunchIntentForPackage(packageName2);
			if (intent != null) {
				// 判断是否已经安装了，已经安装调用代码卸载！！！
				AndroidUtil.uninstallAPK(InstallActivity.this, packageName2);
			} else {
				Toast.makeText(this, "手机上不存在该应用了", Toast.LENGTH_SHORT).show();
			}
			// 无匹配项 ，Intent 会默认加入DEFAULT
			break;
		case R.id.btn3:
//			    it = new Intent(this,RecorderAudioActivity.class);
//				it.putExtra(ExtraConfig.EXTRA_PATH, "/sdcard/test");
//				startActivity(it);
//				throw new NullPointerException("eeor");
		//	break;
		default:
			break;
		}
	}

}
