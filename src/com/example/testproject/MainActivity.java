package com.example.testproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@SuppressLint("NewApi")
	public void onClickStart(View view) {
		Button startBtn = (Button) findViewById(R.id.start);
		startBtn.setBackgroundResource(R.drawable.btn_mask_default);
		final ImageView loadingImage = (ImageView) findViewById(R.id.loading);
		loadingImage.setVisibility(View.VISIBLE);
		
		final Animation loadingAnimation1 = AnimationUtils.loadAnimation(this, R.anim.anim);
		loadingAnimation1.setInterpolator(new LinearInterpolator());
		loadingAnimation1.setFillEnabled(true);
		loadingAnimation1.setFillBefore(true);
		loadingAnimation1.setFillAfter(true);
		loadingAnimation1.setStartOffset(10);
		loadingImage.invalidate();
		loadingImage.startAnimation(loadingAnimation1);

		startBtn.setText("Loading...");
	}
}
