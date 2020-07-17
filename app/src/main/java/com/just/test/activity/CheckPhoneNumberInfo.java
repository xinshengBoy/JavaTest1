package com.just.test.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 查询手机号码归属地
 * @author user
 *
 */
public class CheckPhoneNumberInfo extends Activity implements OnClickListener{

	private EditText et_checkPhoneNumber;
	private Button btn_checkPhoneNumber;
	private TextView txt_checkResult;
	private RequestQueue mQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_check_phonenumber_info);
		mQueue = Volley.newRequestQueue(getApplicationContext());

		et_checkPhoneNumber = (EditText)findViewById(R.id.et_checkPhoneNumber);
		btn_checkPhoneNumber = (Button) findViewById(R.id.btn_checkPhoneNumber);
		txt_checkResult = (TextView)findViewById(R.id.txt_checkResult);

		btn_checkPhoneNumber.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if (v == btn_checkPhoneNumber) {
			String number = et_checkPhoneNumber.getText().toString();
			if (number.equals("")) {
				Toast.makeText(CheckPhoneNumberInfo.this, "您输入的号码为空，请重新输入", Toast.LENGTH_SHORT).show();
			}else if (number.length()<11 || number.length()>11) {
				Toast.makeText(CheckPhoneNumberInfo.this, "您输入的号码不全，请重新输入", Toast.LENGTH_SHORT).show();
			}else {
				queryNumber(number);
			}
		}

	}

	private void queryNumber(String number) {
		String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+number;
		StringRequest request = new StringRequest(url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						//todo 注：正确的json返回格式应该是{}大括号里面的东西，其他的都不要，不然json解析会出错
//						__GetZoneResult_ = {
//							    mts:'1368165',
//							    province:'上海',
//							    catName:'中国移动',
//							    telString:'13681658054',
//								areaVid:'29423',
//								ispVid:'3236139',
//								carrier:'上海移动'
//							}
						try {
							String [] results = response.split("=");
							String result = results[1];
							JSONObject object = new JSONObject(result);
							String province = object.getString("province");
							String catName = object.getString("catName");
							String telString = object.getString("telString");
							String areaVid = object.getString("areaVid");
							String ispVid = object.getString("ispVid");
							txt_checkResult.setText("查询结果：\n此号码"+telString+"属于"+catName+province+"分公司\n地区代码："+areaVid+"\n编号："+ispVid);

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				txt_checkResult.setText("查询结果："+error);
			}
		});

		mQueue.add(request);
	}
}
