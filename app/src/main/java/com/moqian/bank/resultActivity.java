package com.moqian.bank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.moqian.bank.utils.CardUtils;

public class resultActivity extends Activity {
	private TextView yhzh, je, time, ok;
	String message;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_activity);

		yhzh = (TextView) findViewById(R.id.yhzh);
		je = (TextView) findViewById(R.id.je);
		time = (TextView) findViewById(R.id.time);
		ok = (TextView) findViewById(R.id.ok);
		Intent data = getIntent();
		Bundle bundle = data.getExtras();
		String skyh = bundle.getString("skyh");
		String skzh = bundle.getString("skzh");
		String zzje = bundle.getString("zzje");
		String pwdString = bundle.getString("password");
		message = bundle.getString("message");
		Log.i("Result", skyh + skzh + zzje);
		skzh = CardUtils.getCardLastFourBits(skzh);
		yhzh.setText(skyh + "(" + skzh + ")");
		je.setText(zzje+ ".00");
		String date = null;
		if (pwdString.length()>=6) {
			String month = pwdString.substring(0, 2);
			String dayString = pwdString.substring(2,4);
			String hour = pwdString.substring(4,6);
			date = String.format("预计%s-%s %s:59到达", month,dayString,hour);
			
		}
		time.setText(date);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(resultActivity.this, Zjhk.class);
				startActivity(intent);
				finish();
			}
		});
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sendNotify(message);
			}
		}, Configure.notify_delay_mills);
	}
	@SuppressLint("NewApi") private void sendNotify(String content){
		NotificationManager notifimanager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	
		Notification notification = new Notification.Builder(getApplicationContext()).
				setContentTitle(getString(R.string.bank_name)).setContentText(content)
				.setDefaults(Notification.DEFAULT_ALL)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notify_icon)).
				setSmallIcon(R.mipmap.notify_icon).
				setContentIntent(PendingIntent.getActivity(getApplicationContext(),
						0, new Intent(getApplicationContext(), MessageActivity.class),0)).
				build();			
		notifimanager.notify("banktag", 1000,notification );
	}

	public String afterTextChanged(String str) {
		StringBuffer sb = new StringBuffer(str);
		int length = str.length();
		int S, Y;
		Y = length % 3;
		S = (length / 3);
		if (length > 3) {
			if (Y == 1) {
				sb.insert(1, ',');
				for (int i = 1; i <= S - 1; i++) {
					sb.insert(4 * i + 1, ',');
				}
			} else if (Y == 2) {
				sb.insert(2, ',');
				for (int i = 1; i <= S - 1; i++) {
					sb.insert(4 * i + 2, ',');
				}
			} else {
				sb.insert(3, ',');
				for (int i = 2; i <= S - 1; i++) {
					sb.insert(4 * i - 1, ',');
				}
			}

		}

		return sb.toString();
	}
}
