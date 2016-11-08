package com.moqian.bank;

import com.moqian.bank.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Handler handler = new Handler();
		Runnable updateThread = new Runnable() {
			public void run() {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		};
		if(Configure.debug){
			Intent intent = new Intent(MainActivity.this, MenuActivity.class);
			startActivity(intent);
			
		}else{
			handler.postDelayed(updateThread, 2*1000);
		}
		
	}
}
