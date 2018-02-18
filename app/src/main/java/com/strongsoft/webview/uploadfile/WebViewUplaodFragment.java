package com.strongsoft.webview.uploadfile;

import java.lang.reflect.Method;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.strongsoft.ui.R;

public class WebViewUplaodFragment extends Fragment {

	
	private String mUrl = "http://202.109.200.36:6080/grid/login?username=zpp&password=12345678&back_url=http://202.109.200.36:6080/grid/projects/0001c/issues?hideHeader=true";

	public final static String EXTRA_URL = "EXTRA_URL";

	private Handler mHandler;
	private WebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.web, null);
		webView = (WebView) view.findViewById(R.id.webview);
		initWebView(webView);
		loadUrl();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
	}

	private void loadUrl() {
		if (getArguments() != null) {
			mUrl = getArguments().getString(EXTRA_URL);
		}
	  //  mUrl = "http://www.baidu.com";
		webView.loadUrl(mUrl);
	}

	private final static Object methodInvoke(Object obj, String method,
			Class<?>[] parameterTypes, Object[] args) {
		try {
			Method m = obj.getClass().getMethod(method,
					new Class[] { boolean.class });
			m.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void initWebView(WebView webView) {

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setAllowFileAccess(true);
		settings.setDomStorageEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		settings.setSupportZoom(true);
		// settings.setPluginsEnabled(true);
		
		/*
		methodInvoke(settings, "setPluginsEnabled",
				new Class[] { boolean.class }, new Object[] { true });
		// settings.setPluginState(PluginState.ON);
		methodInvoke(settings, "setPluginState",
				new Class[] { PluginState.class },
				new Object[] { PluginState.ON });
		// settings.setPluginsEnabled(true);
		methodInvoke(settings, "setPluginsEnabled",
				new Class[] { boolean.class }, new Object[] { true });
		// settings.setAllowUniversalAccessFromFileURLs(true);
		methodInvoke(settings, "setAllowUniversalAccessFromFileURLs",
				new Class[] { boolean.class }, new Object[] { true });
		// settings.setAllowFileAccessFromFileURLs(true);
		methodInvoke(settings, "setAllowFileAccessFromFileURLs",
				new Class[] { boolean.class }, new Object[] { true });
        */
		
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.clearHistory();
		webView.clearFormData();
		webView.clearCache(true);

		webView.addJavascriptInterface(new JavaScriptinterface(), "Android");

		webView.setWebChromeClient(new MyWebChromeClient());
		webView.setWebViewClient(new WebViewClient());
		
		// webView.setDownloadListener(downloadListener);
	}

	UploadHandler mUploadHandler;

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode,
	// Intent intent) {
	//
	// if (requestCode == Controller.FILE_SELECTED) {
	// // Chose a file from the file picker.
	// if (mUploadHandler != null) {
	// mUploadHandler.onResult(resultCode, intent);
	// }
	// }
	//
	// super.onActivityResult(requestCode, resultCode, intent);
	// }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == Controller.FILE_SELECTED) {
			// Chose a file from the file picker.
			if (mUploadHandler != null) {
				mUploadHandler.onResult(resultCode, data);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

	}
	
	
	
	class myWebViewClient extends WebViewClient{
		 @Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return super.shouldOverrideUrlLoading(view, url);
		}
	}

	class MyWebChromeClient extends WebChromeClient {
		public MyWebChromeClient() {

		}

		private String getTitleFromUrl(String url) {
			String title = url;
			try {
				URL urlObj = new URL(url);
				String host = urlObj.getHost();
				if (host != null && !host.isEmpty()) {
					return urlObj.getProtocol() + "://" + host;
				}
				if (url.startsWith("file:")) {
					String fileName = urlObj.getFile();
					if (fileName != null && !fileName.isEmpty()) {
						return fileName;
					}
				}
			} catch (Exception e) {
				// ignore
			}

			return title;
		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			String newTitle = getTitleFromUrl(url);

			new AlertDialog.Builder(getActivity())
					.setTitle(newTitle)
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									result.confirm();
								}
							}).setCancelable(false).create().show();
			return true;
			// return super.onJsAlert(view, url, message, result);
		}

		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				final JsResult result) {

			String newTitle = getTitleFromUrl(url);

			new AlertDialog.Builder(getActivity())
					.setTitle(newTitle)
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									result.confirm();
								}
							})
					.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.cancel();
								}
							}).setCancelable(false).create().show();
			return true;

			// return super.onJsConfirm(view, url, message, result);
		}

		// Android 2.x
		public void openFileChooser(ValueCallback<Uri> uploadMsg) {
			openFileChooser(uploadMsg, "");
		}

		// Android 3.0
		public void openFileChooser(ValueCallback<Uri> uploadMsg,
				String acceptType) {
			openFileChooser(uploadMsg, "", "filesystem");
		}

		// Android 4.1
		public void openFileChooser(ValueCallback<Uri> uploadMsg,
				String acceptType, String capture) {
			mUploadHandler = new UploadHandler(new Controller());
			mUploadHandler.openFileChooser(uploadMsg, acceptType, capture);
		}

		// Android 4.4, 4.4.1, 4.4.2
		// openFileChooser function is not called on Android 4.4, 4.4.1, 4.4.2,
		// you may use your own java script interface or other hybrid framework.

		// Android 5.0.1
		@SuppressLint("NewApi")
		public boolean onShowFileChooser(WebView webView,
				ValueCallback<Uri[]> filePathCallback,
				FileChooserParams fileChooserParams) {

			String acceptTypes[] = fileChooserParams.getAcceptTypes();

			String acceptType = "";
			for (int i = 0; i < acceptTypes.length; ++i) {
				if (acceptTypes[i] != null && acceptTypes[i].length() != 0)
					acceptType += acceptTypes[i] + ";";
			}
			if (acceptType.length() == 0)
				acceptType = "*/*";

			final ValueCallback<Uri[]> finalFilePathCallback = filePathCallback;

			ValueCallback<Uri> vc = new ValueCallback<Uri>() {

				@Override
				public void onReceiveValue(Uri value) {

					Uri[] result;
					if (value != null)
						result = new Uri[] { value };
					else
						result = null;

					finalFilePathCallback.onReceiveValue(result);

				}
			};

			openFileChooser(vc, acceptType, "filesystem");

			return true;
		}
	};

	// class Controller {
	// final static int FILE_SELECTED = 4;
	//
	// Activity getActivity() {
	// return WebViewUplaodFragment.this;
	// }
	// }

	class Controller implements IController {

		@Override
		public Activity getActivity() {
			// TODO Auto-generated method stub
			return getActivity();
		}

	}

	/**
	 * 调用回调
	 * 
	 * @author Administrator
	 *
	 */
	public class JavaScriptinterface {

		@JavascriptInterface
		public void goBack() {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (webView.canGoBack()) {
						webView.goBack();
					} else {
						getActivity().finish();
					}
				}
			});
		}
	}

}