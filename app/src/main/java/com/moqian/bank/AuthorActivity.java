package com.moqian.bank;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moqian.bank.utils.CommonUtils;

public class AuthorActivity extends Activity {

    private EditText authorId;
    private EditText authorPass;
    private Button authorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        authorId = (EditText) findViewById(R.id.author_id);
        authorPass = (EditText) findViewById(R.id.author_pass);
        authorBtn = (Button) findViewById(R.id.author_btn);
        authorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClick();
            }
        });
    }

    private void doClick() {
        String publicKey = authorId.getText().toString().trim();
        String author = authorPass.getText().toString().trim();

        if (TextUtils.isEmpty(publicKey) || TextUtils.isEmpty(author)) {
            Toast.makeText(AuthorActivity.this, "请填写正确的授权码！", Toast.LENGTH_SHORT).show();
        } else {
            String resultCode = CommonUtils.RSADecode(author.getBytes(), publicKey.getBytes());
            SharedPreferences authorTime = getSharedPreferences("author_time", 0);
            SharedPreferences.Editor editor = authorTime.edit();

            editor.putString(CommonUtils.SP_AUTHOR_TYPE,resultCode);

            if (CommonUtils.AUTHOR_NONE.equals(resultCode)) {
                Toast.makeText(AuthorActivity.this, "请输入正确的注册码！", Toast.LENGTH_LONG)
                        .show();
            } else {
                editor.putBoolean(CommonUtils.SP_IS_SET, true);

                if (CommonUtils.AUTHOR_ONE.equals(resultCode)) {
                    Toast.makeText(AuthorActivity.this, "恭喜你，有30天的试用期！", Toast.LENGTH_LONG)
                            .show();

                    editor.putLong(CommonUtils.SP_TIME, System.currentTimeMillis());
                    editor.putLong(CommonUtils.SP_DEADLINE, (long) 1000 * 60 * 60 * 24 * 30);

                } else if (CommonUtils.AUTHOR_EVER.equals(resultCode)) {
                    Toast.makeText(AuthorActivity.this, "恭喜你，获得永久使用权！", Toast.LENGTH_LONG)
                            .show();
                }
            }

            editor.apply();

            Log.e("tag","deadline="+authorTime.getLong(CommonUtils.SP_DEADLINE,0));

            if(CommonUtils.AUTHOR_ONE.equals(resultCode) || CommonUtils.AUTHOR_EVER.equals(resultCode)){
                intentToLogin();
            }
        }

    }

    private void intentToLogin() {
        Intent intent = new Intent(AuthorActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
