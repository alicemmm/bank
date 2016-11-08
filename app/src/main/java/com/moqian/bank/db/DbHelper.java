package com.moqian.bank.db;

import com.moqian.bank.db.MessageRecordConstants.Columns;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	
	
	
	public DbHelper(Context context) {
		super(context, DataBaseManager.DB_NAME, null, DataBaseManager.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "DROP TABLE IF EXISTS  "+TransRecordConstants.RECORD_TABLE_NAME+";";
		db.execSQL(sql);
		sql = "create table "+TransRecordConstants.RECORD_TABLE_NAME+"(" +
				TransRecordConstants.Columns._ID +" integer primary key autoincrement,"
				+TransRecordConstants.Columns.AMOUNT +" text,"+TransRecordConstants.Columns
				.COLLECTION_ACCOUNT +" text,"+TransRecordConstants.Columns.NAME+" text,"+
				TransRecordConstants.Columns.STATE +" text,"+ 
				TransRecordConstants.Columns.DATE+" text,"+TransRecordConstants.Columns.COLLECTION_BANK+" text)";
		db.execSQL(sql);
		sql = "drop table if exists "+ MessageRecordConstants.MESSAGE_TABLE_NAME+";";
		db.execSQL(sql);
		sql = "create table "+ MessageRecordConstants.MESSAGE_TABLE_NAME+"("+MessageRecordConstants
				.Columns._ID +" integer primary key autoincrement,"+
				Columns.AMOUNT+" text,"+ Columns.PAY_AMOUNT+
			" text,"+ Columns.TAIL_NUMBER+" text,"+Columns.TEL+" text,"+ Columns.DATE +" text)";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}
