package com.moqian.bank.adpter;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.moqian.bank.R;
import com.moqian.bank.R.string;
import com.moqian.bank.model.MessageRecordInfo;
import com.moqian.bank.utils.DateUtils;
import com.moqian.bank.utils.MoneyUtils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	private List<MessageRecordInfo> infos;
	private Context context;

	public MessageAdapter(List<MessageRecordInfo> infos, Context context) {
		super();
		this.infos = infos;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.view_message_item, parent, false);
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MessageRecordInfo value = infos.get(position);
		String amount = MoneyUtils.getPointmoney(value.getAmount());
		String date = value.getDate();
		String payAmount = MoneyUtils.getPointmoney(value.getPayAmount());
		String tailNumber = "1100";
		String year = DateUtils.getFormatDate("yy",date);
		String month = DateUtils.getFormatDate("MM",date);
		String dayString = DateUtils.getFormatDate("dd",date);
		String hour = DateUtils.getFormatDate("HH",date);
		String minute = DateUtils.getFormatDate("mm",date);
		String content = context.getString(string.message_content);
		content = String.format(content, tailNumber,year,month,dayString,hour,minute,payAmount,
				amount);
		holder.content.setText(content);
		holder.content.setAutoLinkMask(Linkify.ALL);
		holder.content.setMovementMethod(LinkMovementMethod.getInstance()); 
		holder.date.setText(DateUtils.getDateWithMinute(date));
		return convertView;
	}

	private class ViewHolder {
		TextView date;
		TextView content;
	}

}
