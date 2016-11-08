package com.moqian.bank;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.moqian.bank.utils.CommonUtils;

public class LoginActivity extends Activity implements OnClickListener {
    private ImageView back;
    private EditText pwd;
    private LinearLayout login;
    ProgressDialog pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        pro = new ProgressDialog(this);
        setContentView(R.layout.login_activity);
        back = (ImageView) findViewById(R.id.back);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (LinearLayout) findViewById(R.id.login);
        login.setOnClickListener(this);
        back.setOnClickListener(this);

        CommonUtils.testRSA("1");

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.login:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        if (!(pwd.getText().toString().equals(""))) {
            Log.i("TAG", String.valueOf(!pwd.getText().equals("")));
            Handler handler = new Handler();
            pro.show();
            Runnable updateThread = new Runnable() {
                public void run() {
                    Intent intent = new Intent(LoginActivity.this,
                            MenuActivity.class);
                    startActivity(intent);
                    pro.cancel();
                    finish();
                }
            };
            handler.postDelayed(updateThread, 2 * 1000);
        } else {
            Log.i("TAG_1", String.valueOf(!pwd.getText().equals("")));
            Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_LONG)
                    .show();

        }
    }

}
