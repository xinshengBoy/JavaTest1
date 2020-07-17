package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.BankInfo;
import com.just.test.widget.MyActionBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断银行卡号
 * @author user
 *
 */
public class CheckBankNumber extends Activity {
	private String bankName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_check_banknumber);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "判断银行卡号");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		et_bankNumber = (EditText)findViewById(R.id.et_bankNumber);
		bt_checkBankNumber = (Button)findViewById(R.id.bt_checkBankNumber);
		txt_result_checkBank = (TextView)findViewById(R.id.txt_result_checkBank);

		bt_checkBankNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String bankNum = et_bankNumber.getText().toString().trim();
				bankNum = bankNum.replace(" ", "");//判断是否为银行卡号的时候要将空格移除
				boolean isBank = checkBankCard(bankNum);
				char [] bank = bankNum.trim().toCharArray();
				bankName = BankInfo.getNameOfBank(bank, 0);//判断卡号所属银行及卡种
				Message message = new Message();
				message.what = 0;
				message.obj = isBank;
//				message.obj = bankName;
				handler.sendMessage(message);
			}
		});
		//每四位自动空一格
		et_bankNumber.addTextChangedListener(new TextWatcher() {
			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
				if (isChanged) {
					location = et_bankNumber.getSelectionEnd();
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
						// if (index % 5 == 4) {
						//      buffer.insert(index, ' ');
						//      konggeNumberC++;
						// }
						if (index == 4 || index == 9 || index == 14 || index == 19) {
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
					if (location > str.length()) {
						location = str.length();
					} else if (location < 0) {
						location = 0;
					}

					et_bankNumber.setText(str);
					Editable etable = et_bankNumber.getText();
					Selection.setSelection(etable, location);
					isChanged = false;
				}
			}
		});
	}

	/**
	 * 判断输入的是否为银行卡号
	 * @param card
	 * @return
	 */
	private boolean checkBankCard(String card) {
		if (!isNumber(card)) {
			return false;
		}

		char bit = getBankCardCheck(card.substring(0, card.length()-1));
		return card.charAt(card.length()-1) == bit;
	}

	/**
	 * 判断输入的是否为数字
	 * @param card
	 * @return
	 */
	private boolean isNumber(String card) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(card);
		if (matcher.matches()) {
			return true;
		}

		return false;
	}

	private char getBankCardCheck(String card) {
		if (card == null || card.length() == 0 || !card.matches("\\d+")) {
//			Toast.makeText(CheckBankNumber.this, "您输入的不是银行卡号", Toast.LENGTH_LONG).show();
			throw new IllegalArgumentException("您输入的不是银行卡号!");
		}else {
			char [] chs = card.trim().toCharArray();
			int sum = 0;
			for (int i = chs.length-1,j=0; i >= 0 ; i--,j++) {
				int k = chs[i] - '0';
				if (j % 2 == 0) {
					k *= 2;
					k = k / 10 + k % 10 ;
				}
				sum += k ;
			}
			return (sum % 10 == 0) ? '0' : (char)((10 - sum%10)+'0');
		}
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				boolean isBankNum = (Boolean) msg.obj;
				if (isBankNum) {
					txt_result_checkBank.setText("恭喜您，您输入的是银行卡号,您的卡号所属银行为："+bankName);
				}else {
					txt_result_checkBank.setText("对不起，您输入的不是银行卡号");
				}
			}
		};
	};
	private EditText et_bankNumber;
	private TextView txt_result_checkBank;
	private Button bt_checkBankNumber;
}
