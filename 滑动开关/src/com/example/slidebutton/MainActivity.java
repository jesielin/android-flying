package com.example.slidebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SlideButton slide_bn = (SlideButton) findViewById(R.id.slide_bn);
	}
}
