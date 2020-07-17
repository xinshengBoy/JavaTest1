package com.just.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.List;

/**
 * 创建桌面快捷方式
 * @author user
 */
public class CreateLauncher extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_createlauncher);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "创建桌面快捷方式");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		Button btn_createLauncher = (Button) findViewById(R.id.btn_createLauncher);
		Button btn_deleteLauncher = (Button) findViewById(R.id.btn_deleteLauncher);


		// 创建
		btn_createLauncher.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				boolean flag = isAddShortCut(CreateLauncher.this);
//				Toast.makeText(CreateLauncher.this, "flag:"+flag, Toast.LENGTH_LONG).show();
//				if (flag == false) {// 不存在则可以创建快捷方式
				addShortCut();
//				} else {
//					Toast.makeText(CreateLauncher.this, "桌面快捷方式已创建",
//							Toast.LENGTH_SHORT).show();
//				}
			}
		});
		// 删除
		btn_deleteLauncher.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				boolean flag = isAddShortCut(CreateLauncher.this);
//				Toast.makeText(CreateLauncher.this, "flag:"+flag, Toast.LENGTH_LONG).show();
//				if (flag) {
				deleteShortCut();
//				} else {
//					Toast.makeText(CreateLauncher.this, "还未创建桌面快捷方式",
//							Toast.LENGTH_SHORT).show();
//				}

			}
		});
	}

	/**
	 * 创建桌面快捷方式
	 */
	private void addShortCut() {
		// 跳转
		Intent addIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 设置要创建的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				CreateLauncher.this, R.drawable.sms);
		// 设置点击图标的跳转
		Intent myIntent = new Intent(CreateLauncher.this, MainActivity.class);
		// 添加快捷方式的名称
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "海淘");
		// 添加图片
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 添加跳转的地方
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
		// 是否允许重复创建
		addIntent.putExtra("duplicate", false);
		sendBroadcast(addIntent);

		handler.sendEmptyMessage(0);
	}

	/**
	 * 删除已经存在的桌面快捷方式
	 */
	private void deleteShortCut() {
		Intent intent = new Intent(
				"com.android.launcher.action.UNINSTALL_SHORTCUT");
		// 获取当前的应用名称
		String title = null;
		PackageManager pm = CreateLauncher.this.getPackageManager();
		try {
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(CreateLauncher.this.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		// 快捷方式名称
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);

		Intent shortCut = CreateLauncher.this
				.getPackageManager()
				.getLaunchIntentForPackage(CreateLauncher.this.getPackageName());
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCut);
		CreateLauncher.this.sendBroadcast(intent);

	}

	/**
	 * 判断桌面是否已经创建了此快捷方式
	 */
	private boolean isAddShortCut(Activity activity) {
		String url = "";
		url = "content://"
				+ getAuthorityFromPermission(activity,
				"com.android.launcher.permission.READ_SETTINGS")
				+ "/favorites?notify=true";
		ContentResolver resolver = activity.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), new String[] { "title",
						"iconResource" }, "title=?",
				new String[] { getString(R.string.app_name).trim() }, null);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	private String getAuthorityFromPermission(Context context, String permission) {
		if (permission == null)
			return null;
		List<PackageInfo> packs = context.getPackageManager()
				.getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs != null) {
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						if (permission.equals(provider.readPermission))
							return provider.authority;
						if (permission.equals(provider.writePermission))
							return provider.authority;
					}
				}
			}
		}
		return null;
	}

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CreateLauncher.this).setTitle("通知").setMessage("快捷方式创建成功")
						.setPositiveButton("确定", new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		};
	};
}
