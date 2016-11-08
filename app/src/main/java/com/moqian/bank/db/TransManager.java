package com.moqian.bank.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.moqian.bank.db.TransRecordConstants.Columns;
import com.moqian.bank.model.TransRecordInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class TransManager extends DataBaseManager {

	public TransManager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	String TAG = "TransManger";

	public ContentValues modelToValues(TransRecordInfo info) {
		ContentValues values = new ContentValues();
		values.put(TransRecordConstants.Columns.AMOUNT, info.getAmount());
		values.put(TransRecordConstants.Columns.COLLECTION_ACCOUNT,
				info.getCollectionAccount());
		values.put(TransRecordConstants.Columns.COLLECTION_BANK,
				info.getCollectionBank());
		values.put(TransRecordConstants.Columns.NAME, info.getName());
		values.put(TransRecordConstants.Columns.STATE, info.getState());
		values.put(Columns.DATE, info.getDate());
		return values;

	}

	public void insertRecord(TransRecordInfo value) {
		ContentValues data = modelToValues(value);
		db.insert(TransRecordConstants.RECORD_TABLE_NAME, null, data);
	}

	public TransRecordInfo cursorToModel(Cursor cursor) {

		String amount = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.AMOUNT));
		String collectionAcount = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.COLLECTION_ACCOUNT));
		String collectionBank = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.COLLECTION_BANK));
		String date = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.DATE));
		String name = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.NAME));
		String state = cursor.getString(cursor
				.getColumnIndexOrThrow(Columns.STATE));
		int id = cursor.getInt(cursor.getColumnIndexOrThrow(Columns._ID));
		TransRecordInfo value = new TransRecordInfo();
		value.setAmount(amount);
		value.setCollectionAccount(collectionAcount);
		value.setCollectionBank(collectionBank);
		value.setDate(date);
		value.setName(name);
		value.setState(state);
		value.setId(id);
		return value;
	}

	public List<TransRecordInfo> getAllRecordOrderByDate() {
		List<TransRecordInfo> infos = new ArrayList<TransRecordInfo>();
		Cursor cursor = db.query(true, TransRecordConstants.RECORD_TABLE_NAME,
				TransRecordConstants.Columns.WITHWOUT_ID_COLUMNS, null, null,
				null, null, TransRecordConstants.Columns.DATE + " desc", null);
		try {
			if (cursor != null && cursor.moveToFirst()) {
				do {
					TransRecordInfo value = cursorToModel(cursor);
					infos.add(value);

				} while (cursor.moveToNext());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return infos;
	}

	public void deleteByDate(String date) {
		db.delete(TransRecordConstants.RECORD_TABLE_NAME, Columns.DATE + "=?",
				new String[] { date });

	}

	public TransRecordInfo getInfoByDate(String date) {
		Cursor cursor = db.query(true, TransRecordConstants.RECORD_TABLE_NAME,
				TransRecordConstants.Columns.WITHWOUT_ID_COLUMNS, "date = "
						+ date, null, null, null, null, null);
		TransRecordInfo info = null;
		if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
			info = cursorToModel(cursor);

		}
		if (cursor != null) {
			cursor.close();
		}
		return info;

	}
	
	

	public void updateStates(Map<String, String> changedState) {
		if (changedState != null) {
			Set<Entry<String, String>> set = changedState.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iterator
						.next();
				String key = entry.getKey();
				String value = entry.getValue();

				Log.d(TAG, "update key =" + key + ", value = " + value);
			
				TransRecordInfo info = getInfoByDate(key);
				info.setState(value);
				ContentValues values = modelToValues(info);
				 db.update(TransRecordConstants.RECORD_TABLE_NAME, values,
				 Columns.DATE+" = "+key, null);
				// db.execSQL("update transformRecord set state = "+ value +
				// " where date = " + key);

			}
		}

	}

}
