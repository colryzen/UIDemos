package com.example.media;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.VideoView;

import com.strongsoft.ui.R;

/**
 * Created by ruankerui on 2018/1/16.
 */

public class VideoPlayTestActivity extends Activity {

    private String playUrl = "http://192.168.119.188:8080/video/hsxhn.mp4";

    VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_video_layout);
        mVideoView=  (VideoView) findViewById(R.id.video_play);
        Uri uri = Uri.parse(playUrl);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
        mVideoView.requestFocus();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

    }
}
