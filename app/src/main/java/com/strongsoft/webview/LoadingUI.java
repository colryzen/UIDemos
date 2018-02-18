package com.strongsoft.webview;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.strongsoft.ui.R;

/**
 * 
 * @author ljs
 * 
 */
public class LoadingUI {
	View mLoadingView;
	View mLoadfinishView;
	TextView mTvMessage;
	TextView mTvResult;	
	Button mBtnRefresh;
	View mLoadingUI;
	private boolean mIsLoading = false;

	public LoadingUI(View v) {
		mLoadingUI = v;
		mLoadfinishView = v.findViewById(R.id.llloadfinish);
		mLoadingView = v.findViewById(R.id.llloading);
		mTvMessage = (TextView) v.findViewById(R.id.tvMessage);
		mTvResult = (TextView) v.findViewById(R.id.tvResult);
		mBtnRefresh = (Button) v.findViewById(R.id.btnRefresh);
	}

	public LoadingUI(Activity activity) {
		View v = activity.findViewById(R.id.flloadingui);
		if (v == null) {
			return;
		}
		mLoadingUI = v;
		mLoadfinishView = v.findViewById(R.id.llloadfinish);
		mLoadingView = v.findViewById(R.id.llloading);
		mTvMessage = (TextView) v.findViewById(R.id.tvMessage);
		mTvResult = (TextView) v.findViewById(R.id.tvResult);
		mBtnRefresh = (Button) v.findViewById(R.id.btnRefresh);
	}

	public void show() {
		mLoadingUI.setVisibility(View.VISIBLE);
		mIsLoading = true;
	}

	public void hide() {
		mLoadingView.setVisibility(View.GONE);
		mLoadfinishView.setVisibility(View.GONE);
		mLoadingUI.setVisibility(View.GONE);
		mIsLoading = false;
	}

	public int getRefreshId() {
		return R.id.btnRefresh;
	}

	public void showLoading() {
		mIsLoading = true;
		mLoadingUI.setVisibility(View.VISIBLE);
		mLoadingView.setVisibility(View.VISIBLE);
		mLoadfinishView.setVisibility(View.GONE);
	}

	public void showLoading(int id) {
		mTvMessage.setText(id);
		showLoading();
	}

	public void showLoading(String txt) {
		mTvMessage.setText(txt);
		showLoading();
	}

	public void showMsg(int res){
		mTvResult.setText(res);
		mLoadingUI.setVisibility(View.VISIBLE);
		mLoadingView.setVisibility(View.GONE);
		mLoadfinishView.setVisibility(View.VISIBLE);
		mBtnRefresh.setVisibility(View.GONE);
	}
	
	public void showNodataEmpty(){
		showMsg(R.string.nodata);
	}
	
	public void showError() {
		mIsLoading = false;
		mLoadingUI.setVisibility(View.VISIBLE);
		mLoadingView.setVisibility(View.GONE);
		mLoadfinishView.setVisibility(View.VISIBLE);
		mBtnRefresh.setVisibility(View.VISIBLE);
	}

	public void showError(int id) {
		mTvResult.setText(id);
		showError();
	}

	public void showError(String txt) {
		mTvResult.setText(txt);
		showError();
	}

	public void setOnRefreshListener(OnClickListener l) {
		mBtnRefresh.setOnClickListener(l);
	}

	public boolean isLoading() {
		return mIsLoading;
	}
}