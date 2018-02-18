package com.strongsoft.webview.inputfile;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tabs.FragmentTabs;
import com.strongsoft.ui.R;

public class WebViewSystemFragment extends Fragment{
	
	private static final String TAG =WebViewSystemFragment.class.getSimpleName();	
	
	private WebView webView;
	
	private String mUrl="http://202.109.200.36:6080/grid/issues/1757";
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.web, null);
		webView = (WebView) view.findViewById(R.id.webview);
		initWebView();
		loadUrl();
		return view;
	}
	
	
	
	public void initWebView(){
		WebSettings mWebSettings = webView.getSettings();
	    mWebSettings.setJavaScriptEnabled(true);
	    mWebSettings.setSupportZoom(false);
	    mWebSettings.setAllowFileAccess(true);
	    mWebSettings.setAllowFileAccess(true);
	    mWebSettings.setAllowContentAccess(true);
	    
		webView.setWebChromeClient(mWebChromeClient);
		webView.setWebViewClient(new WebViewClient(){
	           
			@Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i(TAG, "shouldOverrideUrlLoading url="+url);
	            view.loadUrl(url);
	            return true;
	        }
	     });
		
		if(Build.VERSION.SDK_INT>  Build.VERSION_CODES.LOLLIPOP){
			
		}
		
		Log.i(TAG, "start");
	}
	
	 
	private void loadUrl() {
		if (getArguments() != null) {
			mUrl = getArguments().getString(FragmentTabs.EXTRA_URL);
		}
		//mUrl = "http://www.baidu.com";
		go();
	}
	
	public void go()
	{
		Log.i(TAG, "go");
		webView.loadUrl(mUrl);
		webView.getSettings().setJavaScriptEnabled(true);
		
	}
	
	public static final int INPUT_FILE_REQUEST_CODE = 1;
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 2;
	private ValueCallback<Uri[]> mFilePathCallback;

	private String mCameraPhotoPath;
	
	//��sdcard����������ͼ
	//createImageFileInSdcard
    @SuppressLint("SdCardPath")
	private File createImageFile() {
    	//mCameraPhotoPath="/mnt/sdcard/tmp.png";
    	File file=new File(Environment.getExternalStorageDirectory()+"/","tmp.png");
    	mCameraPhotoPath=file.getAbsolutePath();
    	if(!file.exists())
    	{
    		try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return file;
	}
    
	private WebChromeClient mWebChromeClient = new WebChromeClient() {


	    // android 5.0 ������Ҫʹ��android5.0 sdk
	    public boolean onShowFileChooser(
	            WebView webView, ValueCallback<Uri[]> filePathCallback,
	            WebChromeClient.FileChooserParams fileChooserParams) {
	    	
	    	Log.d(TAG, "onShowFileChooser");
	        if (mFilePathCallback != null) {
	            mFilePathCallback.onReceiveValue(null);
	        }
	        
	        
	        mFilePathCallback = filePathCallback;
	        /**
	      	Open Declaration   String android.provider.MediaStore.ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE"
	    	Standard Intent action that can be sent to have the camera application capture an image and return it. 
	    	The caller may pass an extra EXTRA_OUTPUT to control where this image will be written. If the EXTRA_OUTPUT is not present, then a small sized image is returned as a Bitmap object in the extra field. This is useful for applications that only need a small image. If the EXTRA_OUTPUT is present, then the full-sized image will be written to the Uri value of EXTRA_OUTPUT. As of android.os.Build.VERSION_CODES.LOLLIPOP, this uri can also be supplied through android.content.Intent.setClipData(ClipData). If using this approach, you still must supply the uri through the EXTRA_OUTPUT field for compatibility with old applications. If you don't set a ClipData, it will be copied there for you when calling Context.startActivity(Intent).
	    	See Also:EXTRA_OUTPUT
	    	标准意图，被发送到相机应用程序捕获一个图像，并返回它。通过一个额外的extra_output控制这个图像将被写入。如果extra_output是不存在的，
	    	那么一个小尺寸的图像作为位图对象返回。如果extra_output是存在的，那么全尺寸的图像将被写入extra_output URI值。*/
	        
	        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
	            // Create the File where the photo should go
	            File photoFile = null;
	            try {
	            	//����MediaStore.EXTRA_OUTPUT·��,�������д���ȫ·��
	                photoFile = createImageFile();
	                takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
	            } catch (Exception ex) {
	                // Error occurred while creating the File
	                Log.e("WebViewSetting", "Unable to create Image File", ex);
	            }

	            // Continue only if the File was successfully created
	            if (photoFile != null) {
	                mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
	                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                        Uri.fromFile(photoFile));
	                System.out.println(mCameraPhotoPath);
	            } else {
	                takePictureIntent = null;
	            }
	        }

	        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
	        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
	        contentSelectionIntent.setType("*/*");

	        Intent[] intentArray;
	        if (takePictureIntent != null) {
	            intentArray = new Intent[]{takePictureIntent};
	            System.out.println(takePictureIntent);
	        } else {
	            intentArray = new Intent[0];
	        }

	        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
	        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
	        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
	        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

	        startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

	        return true;
	    }


		//The undocumented magic method override
	    //Eclipse will swear at you if you try to put @Override here
	    // For Android 3.0+
	    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
	    	Log.d(TAG, "openFileChooser1");
	    	
	        mUploadMessage = uploadMsg;
	        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	        i.addCategory(Intent.CATEGORY_OPENABLE);
	        i.setType("image/*");
	        WebViewSystemFragment.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILECHOOSER_RESULTCODE);

	    }

	    // For Android 3.0+
	    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
	    	Log.d(TAG, "openFileChooser2");
	        mUploadMessage = uploadMsg;
	        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	        i.addCategory(Intent.CATEGORY_OPENABLE);
	        i.setType("image/*");
	        WebViewSystemFragment.this.startActivityForResult(
	                Intent.createChooser(i, "Image Chooser"),
	                FILECHOOSER_RESULTCODE);
	    }

	    //For Android 4.1
	    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
	    	Log.d(TAG, "openFileChooser3");
	    	
	        mUploadMessage = uploadMsg;
	        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	        i.addCategory(Intent.CATEGORY_OPENABLE);
	        i.setType("image/*");
	        WebViewSystemFragment.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), WebViewSystemFragment.FILECHOOSER_RESULTCODE);

	    }
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult");
		
	    if (requestCode == FILECHOOSER_RESULTCODE) {
	        if (null == mUploadMessage) return;
	        Uri result = data == null || resultCode != Activity.RESULT_OK ? null
	                : data.getData();
	        if (result != null) {
	            String imagePath = ImageFilePath.getPath(getActivity(), result);
	            if (!TextUtils.isEmpty(imagePath)) {
	                result = Uri.parse("file:///" + imagePath);
	            }
	        }
	        mUploadMessage.onReceiveValue(result);
	        mUploadMessage = null;
	    } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
	        // 5.0�Ļص�
	        Uri[] results = null;

	        // Check that the response is a good one
	        if (resultCode == Activity.RESULT_OK) {
	            if (data == null) {
	                // If there is not data, then we may have taken a photo
	                if (mCameraPhotoPath != null) {
	                    Logger.d("camera_photo_path", mCameraPhotoPath);
	                    results = new Uri[]{Uri.parse(mCameraPhotoPath)};
	                }
	            } else {
	                String dataString = data.getDataString();
	                Logger.d("camera_dataString", dataString);
	                if (dataString != null) {
	                    results = new Uri[]{Uri.parse(dataString)};
	                }
	            }
	        }

	        mFilePathCallback.onReceiveValue(results);
	        mFilePathCallback = null;
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	        return;
	    }
	}
	

	
}
