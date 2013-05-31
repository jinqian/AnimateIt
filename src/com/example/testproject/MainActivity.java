package com.example.testproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
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
	
	public void onClickStart(View view) {
		Button startBtn = (Button) findViewById(R.id.start);
//		startBtn.setVisibility(View.INVISIBLE);
		ImageView loadingImage = (ImageView) findViewById(R.id.loading);
		loadingImage.setVisibility(View.VISIBLE);
		
		startBtn.setBackgroundResource(R.drawable.btn_mask_default);
		Animation loadingAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);
		loadingImage.startAnimation(loadingAnimation);
		loadingAnimation.setInterpolator(new CycleInterpolator(0));
//		AnimationDrawable buttonAnimation = (AnimationDrawable) startBtn.getBackground();
		
//		buttonAnimation.start();
		startBtn.setText("Loading...");
	}
}
