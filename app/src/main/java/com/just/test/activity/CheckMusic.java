package com.just.test.activity;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * 检测是否正在播放音乐
 */
public class CheckMusic extends Activity{
	private TextView txt_checkMusic,txt_PhoneMessage,txt_PhoneVersion;
	private Button btn_checkMusic;
	private static final int CHECKMUSIC = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkmusic);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "检测是否正在播放音乐");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		 TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);       
		 
		txt_checkMusic = (TextView) findViewById(R.id.txt_checkMusic);
		txt_PhoneMessage = (TextView) findViewById(R.id.txt_PhoneMessage);
		txt_PhoneVersion = (TextView) findViewById(R.id.txt_PhoneVersion);
		btn_checkMusic = (Button) findViewById(R.id.btn_checkMusic);
		btn_checkMusic.setOnClickListener(new View.OnClickListener() {
			
			private int isPlaying;

			@Override
			public void onClick(View v) {
				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				boolean musicActive = audioManager.isMusicActive();
				if (musicActive) {
					isPlaying = 1;
				} else {
					isPlaying = 2;
				}
				Message message = new Message();
				message.what = CHECKMUSIC;
				message.arg1 = isPlaying;
				handler.sendMessage(message);
			}
		});
		
		/*    
		19.   * 电话状态：    
		20.   * 1.tm.CALL_STATE_IDLE=0          无活动    
		21.   * 2.tm.CALL_STATE_RINGING=1  响铃    
		22.   * 3.tm.CALL_STATE_OFFHOOK=2  摘机    
		23.   */   
		int callState = tm.getCallState();
		String phoneState = "";
		if (callState == 0) {
			phoneState = "电话状态：无活动";
		}else if (callState == 1) {
			phoneState = "电话状态：响铃";
		}else if (callState == 2) {
			phoneState = "电话状态：摘机";
		}

		//电话方位
		CellLocation cellLocation = tm.getCellLocation();
		//设备id
		String deviceId = tm.getDeviceId();
		//设备的软件版本号： 
		String deviceSoftwareVersion = tm.getDeviceSoftwareVersion();
		//手机号
		String line1Number = tm.getLine1Number();
		//获取ISO标准的国家码，即国际长途区号。
		String networkCountryIso = tm.getNetworkCountryIso();
		//MCC+MNC(mobile country code + mobile network code)
		String networkOperator = tm.getNetworkOperator();
		//按照字母次序的current registered operator(当前已注册的用户)的名字   
		String networkOperatorName = tm.getNetworkOperatorName();
		/*    
		82.   * 当前使用的网络类型：    
		83.   * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0    
		84.     NETWORK_TYPE_GPRS     GPRS网络  1    
		85.     NETWORK_TYPE_EDGE     EDGE网络  2    
		86.     NETWORK_TYPE_UMTS     UMTS网络  3    
		87.     NETWORK_TYPE_HSDPA    HSDPA网络  8     
		88.     NETWORK_TYPE_HSUPA    HSUPA网络  9    
		89.     NETWORK_TYPE_HSPA     HSPA网络  10    
		90.     NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4    
		91.     NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5    
		92.     NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6    
		93.     NETWORK_TYPE_1xRTT    1xRTT网络  7    
		94.   */  
		int networkType = tm.getNetworkType();
		/*    
		98.   * 手机类型：    
		99.   * 例如： PHONE_TYPE_NONE  无信号    
		100.     PHONE_TYPE_GSM   GSM信号    
		101.     PHONE_TYPE_CDMA  CDMA信号    
		102.   */  
		int phoneType = tm.getPhoneType();
		String type_phone = "";
		if (phoneType == 0) {
			type_phone = "手机类型：无信号";
		}else if (phoneType == 1) {
			type_phone = "手机类型：GSM信号";
		}else if (phoneType == 2) {
			type_phone = "手机类型：CDMA信号";
		}
		//获取ISO国家码，相当于提供SIM卡的国家码。
		String simCountryIso = tm.getSimCountryIso();
		//获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
		String simOperator = tm.getSimOperator();
		//服务商名称：
		String simOperatorName = tm.getSimOperatorName();
		//SIM卡的序列号：
		String simSerialNumber = tm.getSimSerialNumber();
		//唯一的用户ID：
		String subscriberId = tm.getSubscriberId();

		txt_PhoneMessage.setText("当前手机信息："+"\n"+phoneState+"\n"
				+"电话方位:"+cellLocation+"\n"
				+"设备id:"+deviceId+"\n"
				+"设备的软件版本号： "+deviceSoftwareVersion+"\n"
				+"手机号:"+line1Number+"\n"
				+"获取ISO标准的国家码，即国际长途区号:"+networkCountryIso+"\n"
				+"MCC+MNC:"+networkOperator+"\n"
				+"当前已注册的用户)的名字   :"+networkOperatorName+"\n"
				+"当前使用的网络类型：   :"+networkType+"\n"
				+type_phone+"\n"
				+"获取ISO国家码，相当于提供SIM卡的国家码:"+simCountryIso+"\n"
				+"移动国家码和移动网络码:"+simOperator+"\n"
				+"服务商名称："+simOperatorName+"\n"
				+"SIM卡的序列号："+simSerialNumber+"\n"
				+"唯一的用户ID："+subscriberId);
		
		//获取手机版本号
		String model = Build.MODEL;//手机型号
		String sdk = Build.VERSION.SDK;//sdk版本
		String release = Build.VERSION.RELEASE;//系统版本
		txt_PhoneVersion.setText("当前手机基本信息："+"\n"
				+"手机型号:"+model+"\n"
				+"sdk版本："+sdk+"\n"
				+"系统版本："+release);
	}
	
	Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case CHECKMUSIC:
				if (msg.arg1 == 1) {
					txt_checkMusic.setText("手机当前正在播放音乐");
				}else if (msg.arg1 == 2) {
					txt_checkMusic.setText("手机当前未播放音乐");
				}
				break;

			default:
				break;
			}
			return false;
		}
	});

}
