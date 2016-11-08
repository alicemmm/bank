package com.moqian.bank.db;

import java.util.ArrayList;
import java.util.List;

import com.moqian.bank.R.string;
import com.moqian.bank.resultActivity;
import com.moqian.bank.db.MessageRecordConstants.Columns;
import com.moqian.bank.model.MessageRecordInfo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

@SuppressLint("NewApi")
public class MessageManager extends DataBaseManager {

	public MessageManager(Context context) {
		super(context);
	}

	public ContentValues modeltoContentValues(MessageRecordInfo info) {
		ContentValues values = new ContentValues();
		values.put(Columns.AMOUNT, info.getAmount());
		values.put(Columns.PAY_AMOUNT, info.getPayAmount());
		values.put(Columns.TAIL_NUMBER, info.getTailNumber());
		values.put(Columns.TEL, info.getTel());
		values.put(Columns.DATE, info.getDate());
		return values;
	}

	public static MessageRecordInfo cursorTomodel(Cursor cursor) {
		String amount = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.AMOUNT));
		String date = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.DATE));
		String payAmountString = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.PAY_AMOUNT));
		String tailNumberString = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.TAIL_NUMBER));
		String tel = cursor
				.getString(cursor.getColumnIndexOrThrow(Columns.TEL));
		String id = String.valueOf(cursor.getInt(cursor
				.getColumnIndexOrThrow(Columns._ID)));
		MessageRecordInfo value = new MessageRecordInfo();
		value.setAmount(amount);
		value.setDate(date);
		value.setPayAmount(payAmountString);
		value.setTailNumber(tailNumberString);
		value.setTel(tel);
		value.setId(id);
		return value;
	}

	public List<MessageRecordInfo> getRecordOrderByDate() {
		List<MessageRecordInfo> infos = new ArrayList<MessageRecordInfo>();
		Cursor cursor = db.query(true,
				MessageRecordConstants.MESSAGE_TABLE_NAME, null,
				null, null, null, null, Columns.DATE + " asc", null, null);
		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						MessageRecordInfo value = cursorTomodel(cursor);
						infos.add(value);
					} while (cursor.moveToNext());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return infos;

	}

	public void insert(MessageRecordInfo value) {

		ContentValues values = modeltoContentValues(value);
		long id = db.insert(MessageRecordConstants.MESSAGE_TABLE_NAME, null,
				values);
		Log.d("MessageManager", "" + id);
	}

	public void deleteById(String id) {
		db.delete(MessageRecordConstants.MESSAGE_TABLE_NAME,
				Columns._ID + "=?", new String[] { id });
	}

}
