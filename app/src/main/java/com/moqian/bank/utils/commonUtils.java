package com.moqian.bank.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class CommonUtils {
    public static final String AUTHOR_NONE = "author_none";
    public static final String AUTHOR_ONE = "author_one";
    public static final String AUTHOR_EVER = "author_ever";

    public static final String SP_IS_SET = "author_set";
    public static final String SP_AUTHOR_TYPE = "author_type";

    public static final String SP_TIME = "sp_time";
    public static final String SP_DEADLINE = "sp_deadline";

    public static boolean isAuthorization(Context context) {
        SharedPreferences authorTime = context.getSharedPreferences("author_time", 0);

        boolean isSet = authorTime.getBoolean(SP_IS_SET, false);
        if (!isSet) {
            return false;
        }

        long time = authorTime.getLong(SP_TIME, 0);
        long deadLine = authorTime.getLong(SP_DEADLINE, 0);
        String authorType = authorTime.getString(SP_AUTHOR_TYPE, AUTHOR_NONE);

        if (authorType.equals(AUTHOR_EVER)) {
            return true;
        }

        if (authorType.equals(AUTHOR_NONE)) {
            return false;
        }

        if (time == 0 || deadLine == 0) {
            return false;
        }

        long nowTime = System.currentTimeMillis();
        long myTime = nowTime - time;
        if (myTime > deadLine) {
            return false;
        } else {
            return true;
        }

    }

    public static String RSADecode(byte[] codes, byte[] publicKey) {
        String resultCode = AUTHOR_NONE;

        //解码
        byte[] base64code = Base64.decode(codes, Base64.DEFAULT);
        byte[] base64publicKey = Base64.decode(publicKey, Base64.DEFAULT);

        //解密
        byte[] decode1 = RSA.decryptByPublicKey(base64code, base64publicKey);

        if (decode1 != null) {
            String lastDecode = new String(decode1);
            int lastCode = 0;
            try {
                lastCode = Integer.parseInt(lastDecode);
            } catch (Exception e) {
                lastCode = 0;
                e.printStackTrace();
            }

            if (lastCode > 0 && lastCode < 10000) {
                resultCode = AUTHOR_ONE;
            } else if (lastCode >= 10000 && lastCode < 20000) {
                resultCode = AUTHOR_EVER;
            }
        }

        return resultCode;
    }

//    public static void testRAS(String secret) {
//        Map<String, Object> keyMap = RSA.initKey();
//        //公钥
//        byte[] publicKey = RSA.getPublicKey(keyMap);
//
//        //私钥
//        byte[] privateKey = RSA.getPrivateKey(keyMap);
//
//        byte[] publicKeys = Base64.encode(publicKey, Base64.DEFAULT);
//        String sPublicKey = new String(publicKeys);
//
//        Log.e("tag", "public key = " + sPublicKey);
//
//        byte[] code1 = RSA.encryptByPrivateKey(secret.getBytes(), privateKey);
//        byte[] base64code1 = Base64.encode(code1, Base64.DEFAULT);
//
//        String passKey = new String(base64code1);
//
//        Log.e("tag", "passKey = " + passKey);
//
//        String result = RSADecode(passKey.getBytes(), sPublicKey.getBytes());
//
//        Log.e("tag","result=" + result);
//
//    }

}
