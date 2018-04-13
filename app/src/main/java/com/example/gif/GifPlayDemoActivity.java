package com.example.gif;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.strongsoft.ui.R;

public class GifPlayDemoActivity extends AppCompatActivity {


    private ImageView gifImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_play_demo);
        gifImage=(ImageView)findViewById(R.id.img_play_pic);

        gifImage.setImageResource(R.drawable.ic_search);
    }
}
