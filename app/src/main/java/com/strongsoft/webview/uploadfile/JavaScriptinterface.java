package com.strongsoft.webview.uploadfile;

import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.strongsoft.webview.inputfile.WebViewActivity;

/**
 * 调用回调
 * 
 * @author Administrator
 *
 */
public class JavaScriptinterface {
	Handler mHandler;
    WebView mWebView;
    
	JavaScriptinterface(WebView webView,Handler handler) {
		mHandler = handler;
		mWebView = webView;
	}

	@JavascriptInterface
	public void goBack() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mWebView.canGoBack()) {
					mWebView.goBack();
				} else {
					//
					//WebViewActivity.this.finish();
				}
			}
		});

	}
}