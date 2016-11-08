package com.moqian.bank;

import com.moqian.bank.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Zjhk extends Activity {
	private RelativeLayout kh, jl;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zjhk_activity);
		kh = (RelativeLayout) findViewById(R.id.kh);
		jl = (RelativeLayout) findViewById(R.id.jl);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		kh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Zjhk.this, Kszz.class);
				startActivity(intent);
			}
		});
		jl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Zjhk.this, Jlch.class);
				startActivity(intent);
			}
		});
	}
}
