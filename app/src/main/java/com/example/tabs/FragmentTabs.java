package com.example.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.strongsoft.ui.R;
import com.strongsoft.webview.inputfile.WebViewSystemFragment;
import com.strongsoft.webview.uploadfile.WebViewUplaodFragment;

/**
 * Created by mayongge on 15-9-22.
 */
public class FragmentTabs extends FragmentActivity    implements  RadioGroup.OnCheckedChangeListener{

    public static final String TAG = "FragmentTabs";
    public static final String LOGIN_SUCCESS = "loginSuccess";

    public final static String EXTRA_URL = "EXTRA_URL";
    
    private RadioGroup mRgSwithcBar;

    private FragmentManager mFrManager;
    private Fragment mLastTab;

    public static final String TAB_MESSAGE = "message";
    public static final String TAB_CONTACT = "contact";
    public static final String TAB_TRIBE = "tribe";
    public static final String TAB_MORE = "more";
    public static final String TAB_YUNAPP = "yunapp";

    private TextView mUnread;
    
    private String WEB_URL_FXY="http://202.109.200.36:6080/grid/login?username=zpp&password=12345678&back_url=http://202.109.200.36:6080/grid/projects/0001c/issues?hideHeader=true";
    
    // private String WEB_URL_FXY="http://zh.hainan.gov.cn/SYPDA/html/disaster-report.html?user_id=5";

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tabs_demo);
        mContext=this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mRgSwithcBar=(RadioGroup) findViewById(R.id.rg_switcherLayout);
//        mUnread = (TextView) findViewById(R.id.unread);
        mRgSwithcBar.setOnCheckedChangeListener(this);
        mFrManager=getSupportFragmentManager();

        setCurrentTab(TAB_MESSAGE);
        int childCount= mRgSwithcBar.getChildCount();
        Log.d(TAG, "onCreate: "+childCount);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static final String SYSTEM_TRIBE_CONVERSATION="sysTribe";
    public static final String SYSTEM_FRIEND_REQ_CONVERSATION="sysfrdreq";



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra(LOGIN_SUCCESS) != null){
            // listener.onTabChanged(TAB_MESSAGE);
            setCurrentTab(TAB_MESSAGE);
            getIntent().removeExtra(LOGIN_SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.rb_contact:
                setCurrentTab(TAB_CONTACT);
                break;
            case R.id.rb_me:
                setCurrentTab(TAB_MORE);
                break;
            case R.id.rb_message:
                setCurrentTab(TAB_MESSAGE);
                break;
        }
    }
    private  void  setCurrentTab(String tag){
        FragmentTransaction  ft= mFrManager.beginTransaction();
        Fragment fragment = mFrManager.findFragmentByTag(tag);
        if (mLastTab!= null&&mLastTab.isVisible()&&mLastTab!=fragment) {
            //隐藏上一个
            ft.detach(mLastTab);
        }
        if (fragment != null &&fragment.isDetached()) {
            //已经存在显示
            ft.attach(fragment);
        }else{
            Fragment newFragemt=null;
            if(TAB_MESSAGE.equals(tag)){
            	Bundle bundle =new Bundle();
            	bundle.putString(WebViewUplaodFragment.EXTRA_URL,WEB_URL_FXY);
                newFragemt =Fragment.instantiate(this,WebViewUplaodFragment.class.getName(),bundle);
            }else if(TAB_CONTACT.equals(tag)){
                newFragemt =Fragment.instantiate(this,FragmentContainer.class.getName(),null);
            }else if(TAB_MORE.equals(tag)){
            	Bundle bundle =new Bundle();
            	bundle.putString(WebViewUplaodFragment.EXTRA_URL,WEB_URL_FXY);
                newFragemt =Fragment.instantiate(this,WebViewSystemFragment.class.getName(),bundle);
            }
            ft.add(R.id.realtabcontent,newFragemt, tag);
            mLastTab=newFragemt;
        }
        ft.commit();
        mFrManager.executePendingTransactions();
    }

}
