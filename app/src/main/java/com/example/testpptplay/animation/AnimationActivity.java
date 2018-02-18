package com.example.testpptplay.animation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.example.testpptplay.PlayEntriy;
import com.example.testpptplay.PlayUtils;
import com.example.view.AutoScrollTextView;
import com.example.view.MarqueeTextView;
import com.strongsoft.ui.R;

public class AnimationActivity extends Activity implements
		OnCompletionListener, ViewSwitcher.ViewFactory,OnPreparedListener {

	private static final String TAG = AnimationActivity.class.getSimpleName();

	AutoScrollTextView mTvMsg;
	VideoView mVideoView;
	LinearLayout mLLyImageView;
	ImageSwitcher mImageSwitcher;
	PlayPicHandler mPlayHandler;
	ArrayList<PlayEntriy> mPlayList = new ArrayList<PlayEntriy>();

	private int mCurIndex = -1;
	private boolean mRecycle = true;

	private static final int MSG_PLAY_PIC = 0;
	private static final int MSG_PLAY_VIDEO = 1;
	private static final int MSG_PLAY_HTML = 2;

	private static final int MSG_PLAY_WORD = 3;
	private static final int MSG_PLAY_PPT = 4;

	private String playStr = "rtsp://xgrammyawardsx.is.livestream-api.com/livestreamiphone/grammyawards";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_main_layout_2);
		initView();
		initData();
	}

	private void initView() {
		mTvMsg = (AutoScrollTextView) findViewById(R.id.tv_msg);
		mVideoView = (VideoView) findViewById(R.id.videoview);
		mLLyImageView = (LinearLayout) findViewById(R.id.lly_play_pic);
		// mVideoView.setMediaController(new MediaController(this));
		mVideoView.setOnCompletionListener(this);
		
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.imgsw_play_pic);
		mImageSwitcher.setFactory(this);
		mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
	                android.R.anim.fade_in));
		mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
	                android.R.anim.fade_out));
	}

	private void initData() {
		mPlayHandler = new PlayPicHandler(this);
		createTestPlayList();
		startPlay();
		
		// if(path!=null){
		// autoScrollTextView.initScrollTextView(this.getWindowManager(),
		// path);
		// autoScrollTextView.starScroll();
		// }else{
		// autoScrollTextView.initScrollTextView(this.getWindowManager(),
		// "欢迎使用远程点播");
		// autoScrollTextView.starScroll();
		//
		// }
		mTvMsg.initScrollTextView(this.getWindowManager(),  
				getResources().getString(R.string.test_msg));  
		mTvMsg.starScroll();
	}

	private void startPlay() {
		PlayEntriy playEntriy = mPlayList.get(0);
		Message msg = mPlayHandler.obtainMessage();
		msg.obj = playEntriy;
		if ("image".equals(playEntriy.getType())) {
			msg.what = MSG_PLAY_PIC;
			mPlayHandler.sendMessage(msg);
		} else if ("video".equals(playEntriy.getType())) {
			msg.what = MSG_PLAY_VIDEO;
			mPlayHandler.sendMessage(msg);
		}
	}

	private void playPic(PlayEntriy playEntriy) {
		String url = playEntriy.getUrl();
		int imageRes = -1;
		mLLyImageView.setVisibility(View.VISIBLE);
		mVideoView.setVisibility(View.GONE);
		if (!TextUtils.isEmpty(url)) {
			// 图片
			imageRes = PlayUtils.GetRFromDrawable(url, Drawable.class);
			mImageSwitcher.setImageResource(imageRes);
			// 播放下一个
			playNext(true);
		}
	}

	private void playNext(boolean isDelay) {
		mCurIndex++;
		if (mCurIndex == mPlayList.size() && mRecycle) {
			mCurIndex = 0;
		}
		if (mCurIndex >= 0 && mCurIndex < mPlayList.size()) {
			PlayEntriy playEntriy = mPlayList.get(mCurIndex);
			Message msg = mPlayHandler.obtainMessage();
			msg.obj = playEntriy;
			if ("image".equals(playEntriy.getType())) {
				msg.what = MSG_PLAY_PIC;
				mPlayHandler.sendMessageDelayed(msg, playEntriy.getPlayTime());
			} else if ("video".equals(playEntriy.getType())) {
				msg.what = MSG_PLAY_VIDEO;
				mPlayHandler.sendMessageDelayed(msg,
						isDelay ? playEntriy.getPlayTime() : 0);
			}
		}
	}

	private void playVideo(PlayEntriy playEntriy) {
		mLLyImageView.setVisibility(View.GONE);
		mVideoView.setVisibility(View.VISIBLE);

		Uri uri = Uri.parse(playEntriy.getUrl());
		mVideoView.setVideoURI(uri);
		mVideoView.start();
		mVideoView.requestFocus();
	}

	private static class PlayPicHandler extends Handler {
		AnimationActivity activity = null;
		WeakReference<AnimationActivity> activitys = null;

		public PlayPicHandler(AnimationActivity activity) {
			activitys = new WeakReference<AnimationActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			AnimationActivity act = activitys.get();
			if (act != null) {
				switch (msg.what) {
				case MSG_PLAY_PIC:
					PlayEntriy playEntriy = (PlayEntriy) msg.obj;
					act.playPic(playEntriy);
					break;
				case MSG_PLAY_VIDEO:
					playEntriy = (PlayEntriy) msg.obj;
					act.playVideo(playEntriy);
					break;
				default:
					break;
				}

			}
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		// 视频播放完
		Log.e(TAG, "onCompletion  视频播放");
		playNext(false);
	}

	private void createTestPlayList() {
		PlayEntriy playEntriy = new PlayEntriy();
		playEntriy.setType("image");
		playEntriy.setPlayTime(1 * 1000);
		playEntriy.setUrl("img1");
		mPlayList.add(playEntriy);

		// String uri = "android.resource://" + getPackageName() + "/"
		// + R.raw.sunny;
		// playEntriy = new PlayEntriy();
		// playEntriy.setType("video");
		// playEntriy.setPlayTime(2 * 1000);
		// playEntriy.setUrl(uri);
		// mPlayList.add(playEntriy);

		playEntriy = new PlayEntriy();
		playEntriy.setType("image");
		playEntriy.setPlayTime(1 * 1000);
		playEntriy.setUrl("img2");
		mPlayList.add(playEntriy);

		playEntriy = new PlayEntriy();
		playEntriy.setType("image");
		playEntriy.setPlayTime(1 * 1000);
		playEntriy.setUrl("img3");
		mPlayList.add(playEntriy);

		// playEntriy = new PlayEntriy();
		// playEntriy.setType("video");
		// playEntriy.setPlayTime(2 * 1000);
		// uri = "android.resource://" + getPackageName() + "/" + R.raw.cloudy;
		// playEntriy.setUrl(uri);
		// mPlayList.add(playEntriy);
		//
		// playEntriy = new PlayEntriy();
		// playEntriy.setType("video");
		// playEntriy.setPlayTime(2 * 1000);
		// uri = "android.resource://" + getPackageName() + "/" + R.raw.rain;
		// playEntriy.setUrl(uri);
		// mPlayList.add(playEntriy);

		// playEntriy = new PlayEntriy();
		// playEntriy.setType("video");
		// playEntriy.setPlayTime(1 * 1000);
		// uri =
		// "http://static.zqgame.com/html/playvideo.html?name=http://lom.zqgame.com/v1/video/LOM_Promo~2.flv";
		// playEntriy.setUrl(uri);
		// mPlayList.add(playEntriy);

	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mVideoView.setVisibility(View.VISIBLE);
	}
}
