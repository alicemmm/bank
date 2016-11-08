package com.moqian.bank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.moqian.bank.utils.CommonUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonUtils.testRAS("1");

        Handler handler = new Handler();
        Runnable updateThread = new Runnable() {
            public void run() {
                if (CommonUtils.isAuthorization(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        };
        if (Configure.debug) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);

        } else {
            handler.postDelayed(updateThread, 2 * 1000);
        }

    }
}
