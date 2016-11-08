package com.moqian.bank.adpter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moqian.bank.Jlch;
import com.moqian.bank.R;
import com.moqian.bank.db.TransManager;
import com.moqian.bank.model.TransRecordInfo;
import com.moqian.bank.utils.DateUtils;
import com.moqian.bank.utils.MoneyUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class TransRecordAdapter extends BaseAdapter {

	private List<TransRecordInfo> mInfos;
	private Jlch context;
	private Map<Integer, String> changeValues;
	private Map<Integer, String> originValues;
	private Map<String, String> changeStates;
	String TAG = "TransRecord";

	public Map<String, String> getChangedState() {
		Log.d(TAG,"getchaged state"+changeStates.toString());
		return changeStates;
	}

	public TransRecordAdapter(Jlch context, List<TransRecordInfo> info) {
		this.context = context;
		this.mInfos = info;
		changeValues = new HashMap<Integer, String>();
		originValues = new HashMap<Integer, String>();
		changeStates = new HashMap<String, String>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mInfos.size();
	}

	@Override
	public TransRecordInfo getItem(int position) {
		// TODO Auto-generated method stub
		return mInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder = new ViewHolder();

		convertView = LayoutInflater.from(context).inflate(
				R.layout.view_trans_record_item, parent, false);
		holder.sk_tv = (TextView) convertView.findViewById(R.id.sk_tv);
		holder.sk_bank = (TextView) convertView.findViewById(R.id.sk_bank);
		holder.sk_name = (TextView) convertView.findViewById(R.id.sk_name);
		holder.sk_je = (TextView) convertView.findViewById(R.id.sk_je);
		holder.sk_time = (TextView) convertView.findViewById(R.id.sk_time);
		holder.statEditText = (EditText) convertView
				.findViewById(R.id.deal_state);
		convertView.setTag(holder);
		TransRecordInfo info = mInfos.get(position);
		holder.sk_tv.setText(info.getCollectionAccount());
		holder.sk_bank.setText(info.getCollectionBank());
		holder.sk_name.setText(info.getName());
		holder.sk_je.setText(MoneyUtils.formatMoney(info.getAmount()));
		holder.date = info.getDate();
		holder.sk_time.setText(DateUtils.getDate(info.getDate()));
		holder.statEditText.setTag(info.getDate());
		holder.statEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				String content = s.toString();
				String date = (String) holder.statEditText.getTag();
				if (date != null && content != null) {
					changeStates.put(date, content);
				}
				String newValue = changeValues.get(position);
				if (content != null && content.equals(newValue)) {
					
					return;

				}
				String oldValue = originValues.get(position);
				if (content != null && content.equals(oldValue)) {

					holder.statEditText.setText(newValue);
					
					
					
				} else {
					changeValues.put(position, content);
				}
				Log.d("after text", content + "postion=" + position);

				Log.d("after text", changeValues.toString());

				// TODO Auto-generated method stub

			}
		});
		String changeValue = changeValues.get(position);
		if (changeValue != null) {
			holder.statEditText.setText(info.getState());
			Log.d("set text", " get no null");
		} else {
			Log.d("set text", " get null");
			holder.statEditText.setText(info.getState());
			originValues.put(position, info.getState());
		}
		convertView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(final View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(R.string.cao_zuo);
				builder.setMessage(R.string.shan_chu_ji_lu);
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						ViewHolder holder = (ViewHolder) v.getTag();
						Log.d("hello", holder.date);

						new DeleteTask().execute(holder.date);
					}

				};
				builder.setPositiveButton(R.string.shan_chu, listener);
				builder.setNegativeButton(R.string.qu_xiao, null);
				AlertDialog dialog = builder.create();
				dialog.show();
				return false;
			}
		});
		return convertView;
	}

	private class DeleteTask extends
			AsyncTask<String, Void, List<TransRecordInfo>> {

		@Override
		protected List<TransRecordInfo> doInBackground(String... params) {
			TransManager manager = new TransManager(context);
			manager.deleteByDate(params[0]);
			return manager.getAllRecordOrderByDate();
		}

		@Override
		protected void onPostExecute(List<TransRecordInfo> result) {
			context.recordlListView.setAdapter(new TransRecordAdapter(context,
					result));
		}

	}

	private static class ViewHolder {
		private TextView sk_tv, sk_bank, sk_name, sk_je, sk_time;
		private EditText statEditText;
		private String date;
	}

}
