package com.example.testproject;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView pattern = (ImageView) findViewById(R.id.pattern);
		pattern.setImageBitmap(drawPattern());
	}
	
	private Bitmap drawPattern() {
		int size = 40;
		Bitmap b = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		int green = getResources().getColor(R.color.theme_green);
		int darkGreen = getResources().getColor(R.color.theme_green_dark);
		
		// draw a rectangle first
		p.setColor(green);
		c.drawRect(0, 0, size, size, p);
		
		// draw a 1st triangle
		Path path1 = new Path(); 
		path1.reset();
		path1.setFillType(Path.FillType.EVEN_ODD);
		path1.moveTo(size, 0);
		path1.lineTo(0, size);
		path1.lineTo(0, 0);
		path1.close();
		p.setColor(darkGreen);
		c.drawPath(path1, p);
		p.setShader(null);
		
		// draw the 2nd triangle
		path1.reset();
		path1.setFillType(Path.FillType.EVEN_ODD);
		path1.moveTo(size / 2, 0);
		path1.lineTo(0, size / 2);
		path1.lineTo(0, 0);
		path1.close();
		p.setColor(green);
		c.drawPath(path1, p);
		
		// draw the 3rd triangle
		path1.reset();
		path1.setFillType(Path.FillType.EVEN_ODD);
		path1.moveTo(size, size / 2);
		path1.lineTo(size / 2, size);
		path1.lineTo(size, size);
		path1.close();
		p.setColor(darkGreen);
		c.drawPath(path1, p);
		p.setShader(null);
		
		return b;
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
		
		initAnimation();
	}
	
	@SuppressLint("NewApi")
	private void initAnimation() {
//		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.stripe_pattern_green);
		final ImageView loadingImage = (ImageView) findViewById(R.id.loading);
		AnimationDrawable shiftedAnimation = getAnimation(drawPattern());
		
		Button startBtn = (Button) findViewById(R.id.start);
		startBtn.setBackgroundResource(R.drawable.btn_mask_default);
		startBtn.setText("loading...");
		
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			loadingImage.setBackgroundDrawable(shiftedAnimation);
		} else {
			loadingImage.setBackground(shiftedAnimation);
		}
		
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
