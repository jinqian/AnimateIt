package com.example.testproject;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
		startBtn.setBackgroundResource(R.drawable.btn_mask_default);
//		final ImageView loadingImage = (ImageView) findViewById(R.id.loading);
//		loadingImage.setVisibility(View.VISIBLE);
//		
//		final Animation loadingAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);
//		loadingAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//		loadingAnimation.setStartOffset(0);
//		loadingImage.startAnimation(loadingAnimation);
//
//		startBtn.setText("Loading...");
		
		initAnimation();
	}
	
	@SuppressLint("NewApi")
	private void initAnimation() {
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.stripe_pattern_green);
		final ImageView loadingImage = (ImageView) findViewById(R.id.loading);
		AnimationDrawable shiftedAnimation = getAnimation(b);
		
		Button startBtn = (Button) findViewById(R.id.start);
		startBtn.setBackgroundResource(R.drawable.btn_mask_default);
		loadingImage.setBackground(shiftedAnimation);
		
        shiftedAnimation.start();
	}
	
	private Bitmap getShiftedBitmap(Bitmap bitmap, int shiftX) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() * 2, bitmap.getConfig());
		Canvas newBitmapCanvas = new Canvas(newBitmap);
		
		Rect srcRect1 = new Rect(shiftX, 0, bitmap.getWidth(), bitmap.getHeight());
	    Rect destRect1 = new Rect(srcRect1);
	    destRect1.offset(-shiftX, 0);
	    newBitmapCanvas.drawBitmap(bitmap, srcRect1, destRect1, null);
		
		Rect srcRect2 = new Rect(0, 0, shiftX, bitmap.getHeight());
	    Rect destRect2 = new Rect(srcRect2);
	    destRect2.offset(bitmap.getWidth() - shiftX, 0);
	    newBitmapCanvas.drawBitmap(bitmap, srcRect2, destRect2, null);
	    
	    Rect srcRect3 = new Rect(shiftX, 0, bitmap.getWidth(), bitmap.getHeight());
	    Rect destRect3 = new Rect(srcRect3);
	    destRect3.offset(-shiftX, bitmap.getHeight());
	    newBitmapCanvas.drawBitmap(bitmap, srcRect3, destRect3, null);
//	    
	    Rect srcRect4 = new Rect(0, 0, shiftX, bitmap.getHeight());
	    Rect destRect4 = new Rect(srcRect4);
	    destRect4.offset(bitmap.getWidth() - shiftX, bitmap.getHeight());
	    newBitmapCanvas.drawBitmap(bitmap, srcRect4, destRect4, null);
	    
		return newBitmap;
	}
	
	private List<Bitmap> getShiftedBitmaps(Bitmap bitmap) {
	    List<Bitmap> shiftedBitmaps = new ArrayList<Bitmap>();
	    int fragments = 10;
	    int shiftLength = bitmap.getWidth() / fragments;

	    for(int i = 0 ; i < fragments; ++i){
	        shiftedBitmaps.add(getShiftedBitmap(bitmap, shiftLength * i));
	    }

	    return shiftedBitmaps;
	}

	private AnimationDrawable getAnimation(Bitmap bitmap) {
	    AnimationDrawable animation = new AnimationDrawable();
	    animation.setOneShot(false);

	    List<Bitmap> shiftedBitmaps = getShiftedBitmaps(bitmap);
	    int duration = 50;

	    for(Bitmap image: shiftedBitmaps){
	        BitmapDrawable navigationBackground = new BitmapDrawable(getResources(), image);
	        navigationBackground.setTileModeX(TileMode.REPEAT);

	        animation.addFrame(navigationBackground, duration);
	    }
	    return animation;
	}
}
