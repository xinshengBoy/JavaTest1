package com.just.test.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.bean.AppInfo;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取应用列表并可点击打开
 *
 * @author user
 *
 */
public class GetPhoneContactList extends Activity {
	private List<AppInfo> mList = new ArrayList<AppInfo>();
	private ListView list_packageManager;
	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[]{
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};
	private static final int PHONES_DISPLAY_NAME_INDEX = 0; // 联系人显示名称
	private static final int PHONES_NUMBER_INDEX = 1; // 电话号码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_getpackage_list);
		getPackageInfo();// 获取程序包的信息
		list_packageManager = (ListView) findViewById(R.id.list_packageManager);
		PackageAdapter adapter = new PackageAdapter(GetPhoneContactList.this,
				mList, R.layout.layout_jsonvolley_item);
		list_packageManager.setAdapter(adapter);
		list_packageManager.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mList.get(position).getPhoneNumber()));
				if (ActivityCompat.checkSelfPermission(GetPhoneContactList.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				startActivity(intent);
			}
		});
	}
	// 获取手机联系人
	private void getPackageInfo() {
		ContentResolver resolver = getContentResolver();
		try {
			Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
			if (phoneCursor != null) {
				while (phoneCursor.moveToNext()) {
					// 得到手机号码
					String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
//					Toast.makeText(this, phoneNumber, Toast.LENGTH_LONG).show();
					// 当手机号码为空的或者为空字段 跳过当前循环
					if (TextUtils.isEmpty(phoneNumber))
						continue;
					// 得到联系人名称
					String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
					System.out.println("名字："+contactName+";号码："+phoneNumber);
					AppInfo appInfo = new AppInfo();
					appInfo.setPhoneName(contactName);
					appInfo.setPhoneNumber(phoneNumber);
					mList.add(appInfo);
				}
				phoneCursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class PackageAdapter extends CommonAdapter<AppInfo> {

		public PackageAdapter(Context context, List<AppInfo> mDatas,
							  int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, AppInfo item) {
			TextView txt_jsonTitle = helper.getView(R.id.txt_jsonTitle);
			TextView txt_jsonTime = helper.getView(R.id.txt_jsonTime);

			txt_jsonTitle.setText(item.getPhoneName());
			txt_jsonTime.setText(item.getPhoneNumber());
		}

	}

}
