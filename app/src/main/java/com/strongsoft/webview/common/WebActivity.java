package com.strongsoft.webview.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.strongsoft.ui.R;


public class WebActivity extends FragmentActivity implements
		SwipeRefreshLayout.OnRefreshListener {
	private String title = "";
	private String url = "";
	private WebView webView;
	private SwipeRefreshLayout refreshLayout;
	// 是否可以下拉刷新
	private boolean mRefreshable = true;

	private final String STRONG_USER_AGENT="strong_typhoon";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onBeforeInit(savedInstanceState);
		initComponent();
		initData();
		onAfterInit(savedInstanceState);
	}

	public void onBeforeInit(Bundle savedInstanceState) {
		if(getIntent()!=null){
			title = this.getIntent().getStringExtra(ExtraConfig.WEB_TITLE);
			url = this.getIntent().getStringExtra(ExtraConfig.WEB_URL);
			mRefreshable = this.getIntent().getBooleanExtra(
					ExtraConfig.WEB_REFRESH_ABLE, true);
		}
		;

       if(TextUtils.isEmpty(url)){
		   url="http://sc.istrongcloud.com:8007/release/index.html";
		   url="http://hnfz.hainan.gov.cn/WeiXin/rainbow-weather-mq.html";
		   url="http://www.baidu.com";
	   }

	   if(TextUtils.isEmpty(title)){
		   title="台风路径";
	   }
	}

	public void onAfterInit(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	public void onConfigurationChanged(Configuration newConfig) {
		try {
			super.onConfigurationChanged(newConfig);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				Log.v("StrongDebug", "onConfigurationChanged_ORIENTATION_LANDSCAPE");
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				Log.v("StrongDebug", "onConfigurationChanged_ORIENTATION_PORTRAIT");
			}
		} catch (Exception ex) {
		}
	}


	public void initComponent() {
		setContentView(R.layout.activity_web_main_layout);
		webView = (WebView) this.findViewById(R.id.wv_show);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		//支持定位
		webSettings.setGeolocationEnabled(true);

		webView.clearCache(false);
		// 设置支持text/cache-manifest
		webSettings.setDomStorageEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setAppCacheEnabled(false);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		String ua=webSettings.getUserAgentString();
		webSettings.setUserAgentString(ua+";"+STRONG_USER_AGENT);


		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			webSettings.setMixedContentMode(webSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		refreshLayout = (SwipeRefreshLayout) this
				.findViewById(R.id.refreshLayout);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setEnabled(mRefreshable);
		this.webView.setWebViewClient(new webViewClient());
		this.webView.setWebChromeClient(mWebChromeClient);
		webView.clearCache(false);
		//url 地址
		if(!TextUtils.isEmpty(url)){
			webView.loadUrl(url);
		}

	}

	public void onRefresh() {
		webView.reload();

	}

	class webViewClient extends WebViewClient {

		// 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			view.loadUrl(url);
			// 如果不需要其他对点击链接事件的处理返回true，否则返回false

			return true;

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			Toast.makeText(WebActivity.this,"ua="+view.getSettings().getUserAgentString(),Toast.LENGTH_SHORT).show();
			refreshLayout.setRefreshing(true);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			refreshLayout.setRefreshing(false);
		}
		
		 public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
			 
			 //handler.cancel(); // Android默认的处理方式
              handler.proceed();  // 接受所有网站的证书
              //handleMessage(Message msg); // 进行其他处理
			 }
		
	}

	WebChromeClient mWebChromeClient = new WebChromeClient() {

		public void onReceivedTitle(WebView view, String title) {
//			if (TextUtils.isEmpty(title)) {
//				setTitle(view.getTitle());
//			}
		};
		
		public void onProgressChanged(WebView view, int newProgress) {
//			if (newProgress==100&&TextUtils.isEmpty(title)) {
//				setTitle(TextUtils.isEmpty(view.getTitle())?"webveiw 标题未设置 ":view.getTitle());
//			}
		};

		public void onGeolocationPermissionsShowPrompt(final String origin, final android.webkit.GeolocationPermissions.Callback callback) {
			final boolean remember = true;
			AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
			builder.setTitle("位置信息");
			builder.setMessage(origin + "允许获取您的地理位置信息吗？").setCancelable(true).setPositiveButton("允许",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
											int id) {
							callback.invoke(origin, true, remember);
						}
					})
					.setNegativeButton("不允许",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
													int id) {
									callback.invoke(origin, false, remember);
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
	};

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
			builder.setTitle("提示");
			builder.setMessage("是否退出？").setCancelable(true).setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
											int id) {
							finish();
						}
					})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
													int id) {

								}
							});
				AlertDialog alert = builder.create();
				alert.show();
		}
	}

	public void initData() {
		setTitle(title);
	}

}
