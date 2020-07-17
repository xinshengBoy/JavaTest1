package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.MCrypt;
import com.just.test.tools.MD5;
import com.just.test.widget.MyActionBar;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.IOException;

/**
 * 简单加解密
 *
 * Conceal 是facebook的开源加密工具
 * 参考文章：http://blog.csdn.net/ttdevs/article/details/50753540
 */
public class Encryption extends Activity {

	private EditText et_see;//明文
	private EditText et_NoSee;//暗文
	private EditText et_CanSee;//解密之后的结果
	private Button btn_show;//开始解密
	private TextView txt_md5;//MD5加密
	private EditText et_conceal;
	private TextView txt_conceal;
	private Crypto mCrypto;
	private Entity mEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.encryption);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "简单加解密");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		if (null == mCrypto){
			mCrypto = new Crypto(new SharedPrefsBackedKeyChain(Encryption.this),new SystemNativeCryptoLibrary());
			mEntity = new Entity("ttdevs");
		}
		initView();
	}

	private void initView(){
		et_see = (EditText) findViewById(R.id.et_see);
		et_see.setHint("请输入要加密的字符串");
		et_NoSee = (EditText) findViewById(R.id.et_NoSee);
		et_CanSee = (EditText) findViewById(R.id.et_CanSee);
		btn_show = (Button) findViewById(R.id.btn_show);
		txt_md5 = (TextView) findViewById(R.id.txt_md5);
		et_conceal = (EditText)findViewById(R.id.et_conceal);
		Button btn_conceal = (Button)findViewById(R.id.btn_conceal);
		txt_conceal = (TextView)findViewById(R.id.txt_conceal);

		btn_show.setOnClickListener(new OnClickListener() {
			private MCrypt mCrypt;

			@Override
			public void onClick(View v) {
				String inputString = et_see.getText().toString();

				//MD5加密
				String passMD5 = MD5.MD5(inputString);
				String encryptMD5 = MD5.encryptMD5(passMD5);
				txt_md5.setText("MD5加密结果："+ passMD5 + "-----" +encryptMD5);

				mCrypt = new MCrypt();
				try {
					String encrypted = MCrypt.bytesToHex(mCrypt.encrypt(inputString));
					String decrypted = new String(mCrypt.decrypt(encrypted));
					et_NoSee.setText(encrypted);
					et_CanSee.setText(decrypted);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btn_conceal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String input = et_conceal.getText().toString();
				if (input.equals("")){
					LemonBubble.showError(Encryption.this,"请输入",1000);
					return;
				}else {
					try {
						byte [] a = mCrypto.encrypt(input.getBytes(),mEntity);
						byte [] b = mCrypto.decrypt(a,mEntity);

						txt_conceal.setText("输入的是："+input+"。加密后为："+a+"。解密后为："+b);
					} catch (KeyChainException | CryptoInitializationException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
