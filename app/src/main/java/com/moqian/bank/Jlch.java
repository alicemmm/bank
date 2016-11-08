package com.moqian.bank;

import java.util.Calendar;
import java.util.List;
import java.util.Map;



import com.moqian.bank.R;
import com.moqian.bank.adpter.MessageAdapter;
import com.moqian.bank.adpter.TransRecordAdapter;
import com.moqian.bank.db.MessageManager;
import com.moqian.bank.db.TransManager;
import com.moqian.bank.model.MessageRecordInfo;
import com.moqian.bank.model.TransRecordInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class Jlch extends Activity {
	
	 public ListView recordlListView;
	private ImageView back;
	Calendar c = Calendar.getInstance();
	private TransRecordAdapter adapter = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jl_activity);
		
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adapter != null) {
					Map<String, String> changedState= adapter.getChangedState();
					TransManager manager = new TransManager(Jlch.this);
					manager.updateStates(changedState);
					
				}
				onBackPressed();
			}
		});
		recordlListView = (ListView) findViewById(R.id.list_record);
		startLoadDataBase();
	}
	private void startLoadDataBase(){
	AsyncTask<Void, Void, List<TransRecordInfo>>task = new AsyncTask<Void, Void, List<TransRecordInfo>>(){

		@Override
		protected List<TransRecordInfo> doInBackground(Void... params) {
			TransManager manager = new TransManager(Jlch.this);
			return manager.getAllRecordOrderByDate();
			
		}

		@Override
		protected void onPostExecute(List<TransRecordInfo> result) {
			adapter = new TransRecordAdapter(Jlch.this, result);
			recordlListView.setAdapter(adapter);
			
		}
		
		
	};
	task.execute();
	}
}
