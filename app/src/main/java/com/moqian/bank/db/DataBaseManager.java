package com.moqian.bank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
	public final static String DB_NAME = "bank.db";
	public final static int VERSION = 1;
	protected SQLiteDatabase db;
	public DataBaseManager(Context context){
		DbHelper dbHelper = new DbHelper(context);
		 db = dbHelper.getWritableDatabase();
	}
}
