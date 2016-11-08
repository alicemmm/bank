package com.moqian.bank;

import java.util.List;

import com.moqian.bank.adpter.MessageAdapter;
import com.moqian.bank.db.MessageManager;
import com.moqian.bank.model.MessageRecordInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private View backTextView;
	private ListView messageListView;
	private TextView bankNameTextView;
	private TextView bankNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity);
		initView();
		initEvent();
		new Task().execute();
		messageListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			
			public boolean onItemLongClick(final AdapterView<?> parent, final View view,final  int position, long id) {
				 AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
				builder.setTitle(R.string.cao_zuo);
				builder.setMessage(R.string.shan_chu_ji_lu);
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					MessageAdapter adapter =	(MessageAdapter)parent.getAdapter();
					MessageRecordInfo info = (MessageRecordInfo) adapter.getItem(position);
						new DeleteTask().execute(info.getId());
					}

				};
				builder.setPositiveButton(R.string.shan_chu, listener);
				builder.setNegativeButton(R.string.qu_xiao, null);
				AlertDialog dialog = builder.create();
				dialog.show();
				return false;
			}
			
		});
		
	}
	

	private class DeleteTask extends AsyncTask<String, Void, List<MessageRecordInfo>>{

		@Override
		protected List<MessageRecordInfo> doInBackground(String... params) {
			MessageManager manager = new MessageManager(MessageActivity.this);
			manager.deleteById(params[0]);
			return   manager.getRecordOrderByDate();
		}

		@Override
		protected void onPostExecute(List<MessageRecordInfo> result) {
			messageListView.setAdapter(new MessageAdapter(result, MessageActivity.this));
		}

	}
	private  class Task extends AsyncTask<Void, Void, List<MessageRecordInfo>>{

		@Override
		protected List<MessageRecordInfo> doInBackground(Void... params) {
		   MessageManager manager = new MessageManager(MessageActivity.this.getApplicationContext());
		 return   manager.getRecordOrderByDate();
		}

		@Override
		protected void onPostExecute(List<MessageRecordInfo> result) {
			messageListView.setAdapter(new MessageAdapter(result, MessageActivity.this));
		}
		
		
	}
	private void initView(){
		backTextView =  findViewById(R.id.back);
		messageListView = (ListView) findViewById(R.id.message_list);
		bankNameTextView = (TextView) findViewById(R.id.bank_name);
		bankNumber = (TextView) findViewById(R.id.number);
	}
	
	
	private void initEvent(){
		backTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}
	

}
