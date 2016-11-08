package com.moqian.bank;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.moqian.bank.db.MessageManager;
import com.moqian.bank.db.TransManager;
import com.moqian.bank.model.MessageRecordInfo;
import com.moqian.bank.model.TransRecordInfo;
import com.moqian.bank.utils.CardUtils;
import com.moqian.bank.utils.DateUtils;
import com.moqian.bank.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Kszz extends Activity {
	static String TAG = "Kszz";
	private ImageView next, back;
	private EditText skzh, zzje, sk_name, pwd, ye;// 收款账户、收款银行�?�转账金�??,姓名、密�??
	private TextView skyh;
	private Context context;
	private PopupWindow pop;
	private ListView lv;
	private String x;
	private EditText bankEidText;
	private TextView moneyPrefix;
	SharedPreferences sp;
	ProgressDialog pro;
	private EditText limitEditText;
	private TextView limitMoneyPrefix;
	private double mCurrentAmount = 0;
	private boolean needGetCurrentAmount = true;
	private final static String MONEY_PREFIX = "¥ ";
	String bank[] = new String[] { "中国工商银行", "中国农业银行", "中国银行", "中国建设银行",
			"交通银行", "招商银行", "上海浦东发展银行", "广发银行", "中国民生银行", "中国邮政储蓄银行", "兴业银行",
			"平安银行", "光大银行", "中信银行 " };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.khzz_activity);

		initView();
		initPop();
	}

	@SuppressLint({ "InflateParams", "InlinedApi" })
	private void initPop() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.popu, null);
		lv = (ListView) view.findViewById(R.id.lv);
		pop = new PopupWindow(view);
		pop.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事�??
		List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < bank.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tv", bank[i]);
			lists.add(map);
		}
		View bankEditView = getLayoutInflater().inflate(
				R.layout.list_foot_view, lv, false);
		lv.addFooterView(bankEditView);
		bankEidText = (EditText) bankEditView.findViewById(R.id.bank_edit);
		Button bankCommitButton = (Button) bankEditView
				.findViewById(R.id.bank_edit_commit);
		bankCommitButton.setOnClickListener(new Click());
		lv.setAdapter(new SimpleAdapter(Kszz.this, lists, R.layout.item,
				new String[] { "tv" }, new int[] { R.id.tv }));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				x = bank[position];
				pop.dismiss();
				skyh.setText(x.toString());
			}
		});
		lv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.MATCH_PARENT);
		ColorDrawable color = new ColorDrawable(0x808080);
		pop.setBackgroundDrawable(color);
		// 控制popupwindow点击屏幕其他地方消失
		// pop.setBackgroundDrawable(this.getResources().getDrawable(
		// R.drawable.bg_login));// 设置背景图片，不能在布局中设置，要�?�过代码来设�??
		pop.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上

	}

	@SuppressLint("NewApi")
	class Click implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.skyh:
				if (pop.isShowing()) {
					pop.dismiss();
				} else {
					pop.showAsDropDown(findViewById(R.id.tvv));
				}
				break;
			case R.id.next:
				if (!skzh.getText().toString().equals("")
						&& !skyh.getText().toString().equals("")
						&& !zzje.getText().toString().equals("")
						&& !sk_name.getText().toString().equals("")
						&& !pwd.getText().toString().equals("")) {

					String transferAmount = zzje.getText().toString();
					if (Kszz.this.needGetCurrentAmount) {
						String currentAmount = Kszz.this.ye.getText()
								.toString();
						currentAmount = currentAmount.replace(",", "");
						Kszz.this.mCurrentAmount = Double
								.valueOf(currentAmount);
						needGetCurrentAmount = false;
					}

					double amount1 = 0;
					try {
						double transfer = Double.valueOf(transferAmount);

						 amount1 = mCurrentAmount * 100 - transfer * 100;
						amount1 = amount1 / 100;
//						Kszz.this.ye.setText(String.valueOf(amount1));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String canUseMoney = String.valueOf(amount1);
					ShareManager manager = new ShareManager(Kszz.this);
					manager.setData(ShareConstats.YU_E, canUseMoney);
					Handler handler = new Handler();
					pro.show();
					Runnable updateThread = new Runnable() {
						public void run() {
							TransRecordInfo info = new TransRecordInfo();
							info.setAmount(zzje.getText().toString());
							info.setCollectionAccount(skzh.getText().toString());
							info.setCollectionBank(skyh.getText().toString());
							info.setName(sk_name.getText().toString());
							long mills = System.currentTimeMillis();
							String date = String.valueOf(mills);
							info.setDate(date);
							info.setState(getString(R.string.defalut_state));
							TransManager manager = new TransManager(
									Kszz.this.getApplicationContext());
							manager.insertRecord(info);

							MessageManager messageManager = new MessageManager(
									getApplicationContext());

							MessageRecordInfo value = new MessageRecordInfo();
							value.setTailNumber(CardUtils
									.getCardLastFourBits(skzh.getText()
											.toString()));
							value.setTel("95526");
							value.setDate(date);
							value.setPayAmount(zzje.getText().toString());
							String currentAmount = Kszz.this.ye.getText()
									.toString();
							currentAmount = currentAmount.replace(",", "");
							value.setAmount(currentAmount);// 可用余额
							messageManager.insert(value);
							String content = null;
							content = DateUtils
									.generateMessage(mills,
											getApplicationContext(), zzje
													.getText().toString(),
											"1100",
											currentAmount);
							// intent
							Intent intent = new Intent(Kszz.this,
									resultActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("message", content);
							bundle.putString("skyh", skyh.getText().toString());
							bundle.putString("skzh", skzh.getText().toString());
							bundle.putString("password", pwd.getText()
									.toString().trim());
							bundle.putString("zzje", "¥ "
									+ afterTextChanged(zzje.getText()
											.toString()));
							intent.putExtras(bundle);
							startActivity(intent);
							pro.cancel();
							finish();
						}
					};
					handler.postDelayed(updateThread, 2 * 1000);

				} else {
					Toast.makeText(Kszz.this, "请将信息填写完整哦~", Toast.LENGTH_LONG)
							.show();
				}
				break;

			case R.id.back:
				onBackPressed();
				break;
			case R.id.bank_edit_commit:
				x = bankEidText.getText().toString();
				pop.dismiss();
				skyh.setText(x.toString());
				break;
			}
		}
	}

	private static class TextChange implements TextWatcher {

		int beforeTextLength = 0;
		int onTextLength = 0;
		boolean isChanged = false;

		int location = 0;// 记录光标的位置
		private char[] tempChar;
		private StringBuffer buffer = new StringBuffer();
		int konggeNumberB = 0;
		EditText skzh;
		private TextView moneyPrefix;

		public TextChange(EditText et, TextView tv) {
			skzh = et;
			moneyPrefix = tv;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			Log.d(TAG, "before text changed " + s.toString());
			beforeTextLength = getIntegerLength(s);
			if (buffer.length() > 0) {
				Log.d(TAG, "BUFFER DELETE " + buffer.toString());
				buffer.delete(0, buffer.length());

			}
			konggeNumberB = 0;
			for (int i = 0; i < beforeTextLength; i++) {
				if (s.charAt(i) == ',') {
					konggeNumberB++;
				}
			}
		}

		private int getIntegerLength(CharSequence source) {
			String string = source.toString();
			int length = string.length();
			int indexOfPoint = string.lastIndexOf(".");
			if (indexOfPoint != -1) {
				length = indexOfPoint;
			}
			return length;

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			Log.d(TAG, "on text changed " + s.toString());
			onTextLength = getIntegerLength(s);
			buffer.append(s.toString());
			if (onTextLength == beforeTextLength || onTextLength <= 3
					|| isChanged) {
				isChanged = false;
				return;
			}
			Log.d(TAG, "on text chaged ischanged = true");
			isChanged = true;
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (isChanged) {
				location = skzh.getSelectionEnd();
				int index = 0;
				while (index < buffer.length()) {
					if (buffer.charAt(index) == ',') {
						buffer.deleteCharAt(index);
					} else {
						index++;
					}
				}

				index = 0;
				int konggeNumberC = 0;
				int len = getIntegerLength(buffer.toString());

				Log.d(TAG,
						"after text change before while insert"
								+ buffer.toString() + "length=" + len);
				while (len > 0) {
					if (index % 4 == 3) {
						buffer.insert(len, ',');
						Log.d(TAG, "after text change while insert");
						konggeNumberC++;
						index = 1;
					} else {
						index++;
					}

					len--;
				}

				if (konggeNumberC > konggeNumberB) {
					location += (konggeNumberC - konggeNumberB);
				}

				tempChar = new char[buffer.length()];
				buffer.getChars(0, buffer.length(), tempChar, 0);
				String str = buffer.toString();
				if (str.contains(".")) {
					int indexOfPoint = str.indexOf(".");
					if (indexOfPoint < str.length() - 3) {
						System.out.println("execute here");
						str = str.substring(0, indexOfPoint + 3);

					}

				}
				if ("".equals(str)) {
					moneyPrefix.setVisibility(View.GONE);
				} else {
					moneyPrefix.setVisibility(View.VISIBLE);
				}
				if (location > str.length()) {
					location = str.length();
				} else if (location < 0) {
					location = 0;
				}
				if (location > str.length()) {
					location = str.length();
				} else if (location < 0) {
					location = 0;
				}

				Log.d(TAG, "after chage string " + str);
				Log.d(TAG,
						"after chage string in char array "
								+ String.valueOf(tempChar));
				skzh.setText(str);
				Editable etable = skzh.getText();
				Selection.setSelection(etable, location);
				isChanged = false;
			}

		}
	}

	private void initView() {
		context = Kszz.this;
		sp = context.getSharedPreferences("sp", MODE_PRIVATE);
		pro = new ProgressDialog(this);
		next = (ImageView) findViewById(R.id.next);

		next.setOnClickListener(new Click());
		skzh = (EditText) findViewById(R.id.skzh);
		skzh.requestFocus();
		skzh.addTextChangedListener(new TextWatcher() {

			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 3
						|| isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				beforeTextLength = s.length();
				if (buffer.length() > 0) {
					buffer.delete(0, buffer.length());
				}
				konggeNumberB = 0;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						konggeNumberB++;
					}
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (isChanged) {
					location = skzh.getSelectionEnd();
					int index = 0;
					while (index < buffer.length()) {
						if (buffer.charAt(index) == ' ') {
							buffer.deleteCharAt(index);
						} else {
							index++;
						}
					}

					index = 0;
					int konggeNumberC = 0;
					while (index < buffer.length()) {
						if (index % 5 == 4) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
						index++;
					}

					if (konggeNumberC > konggeNumberB) {
						location += (konggeNumberC - konggeNumberB);
					}

					tempChar = new char[buffer.length()];
					buffer.getChars(0, buffer.length(), tempChar, 0);
					String str = buffer.toString();
					location = str.length();
					skzh.setText(str);
					Editable etable = skzh.getText();
					Selection.setSelection(etable, location);
					isChanged = false;
				}
			}
		});
		skyh = (TextView) findViewById(R.id.skyh);
		skyh.setOnClickListener(new Click());
		zzje = (EditText) findViewById(R.id.zzje);
		zzje.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// String transferAmount = zzje.getText().toString();
				// if(Kszz.this.needGetCurrentAmount){
				// String currentAmount = Kszz.this.ye.getText().toString();
				// currentAmount=currentAmount.replace(",","");
				// Kszz.this.mCurrentAmount = Double.valueOf(currentAmount);
				// needGetCurrentAmount = false;
				// }
				//
				// try {
				// double transfer = Double.valueOf(transferAmount);
				//
				// double amount1 = mCurrentAmount*100 - transfer*100;
				// amount1 = amount1/100;
				// Kszz.this.ye.setText(String.valueOf(amount1));
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		sk_name = (EditText) findViewById(R.id.sk_name);
		pwd = (EditText) findViewById(R.id.pwd);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new Click());
		moneyPrefix = (TextView) findViewById(R.id.money_prefix);
		ye = (EditText) findViewById(R.id.ye);
		ye.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.d(TAG, "on text changed start = " + start + "before = "
						+ before + "count=" + count + "sequece =" + s);
				String string = s.toString();
				if (string.contains(".")) {
					int indexOfPoint = string.indexOf(".");
					if (indexOfPoint < string.length() - 3) {
						System.out.println("execute here");
						string = string.substring(0, indexOfPoint + 3);
						ye.setText(string);
						ye.setSelection(string.length());

					}

				}
				if ("".equals(string)) {
					moneyPrefix.setVisibility(View.GONE);
				} else {
					moneyPrefix.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.d(TAG, "beforeTextChanged = " + start + "after = " + after
						+ "count=" + count + "sequece =" + s);

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.d(TAG, "afterTextChanged" + s.toString());
			}
		});
		ye.addTextChangedListener(new TextChange(ye, moneyPrefix));

		ye.setText(MoneyUtils.formatMoneyWithoutSymbol(new ShareManager(this)
				.getStringData(ShareConstats.YU_E, "")));
		ye.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				needGetCurrentAmount = true;

			}
		});
		ye.setSelection(ye.length());
		limitEditText = (EditText) findViewById(R.id.zhuang_zhang_yu_e);
		limitMoneyPrefix = (TextView) findViewById(R.id.money_prefix1);
		limitEditText.addTextChangedListener(new TextChange(limitEditText,
				limitMoneyPrefix));
		limitEditText.setText("1000000.00");
		limitEditText.setSelection(limitEditText.length());
	}

	public String afterTextChanged(String str) {
		StringBuffer sb = new StringBuffer(str);
		int length = str.length();
		int S, Y;
		Y = length % 3;
		S = (length / 3);
		if (length > 3) {
			if (Y == 1) {
				sb.insert(1, ',');
				for (int i = 1; i <= S - 1; i++) {
					sb.insert(4 * i + 1, ',');
				}
			} else if (Y == 2) {
				sb.insert(2, ',');
				for (int i = 1; i <= S - 1; i++) {
					sb.insert(4 * i + 2, ',');
				}
			} else {
				sb.insert(3, ',');
				for (int i = 2; i <= S - 1; i++) {
					sb.insert(4 * i - 1, ',');
				}
			}

		}

		return sb.toString();
	}

}
