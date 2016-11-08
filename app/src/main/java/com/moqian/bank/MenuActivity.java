package com.moqian.bank;

import com.moqian.bank.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MenuActivity extends Activity {
	private LinearLayout zjhk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
		zjhk = (LinearLayout) findViewById(R.id.zjhk);
		zjhk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stu
				Intent intent = new Intent(MenuActivity.this, Zjhk.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.look_message).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this, MessageActivity.class);
				startActivity(intent);
				
			}
		});
	}
}
