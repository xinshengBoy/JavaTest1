package com.just.test.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.AppInfo;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取应用列表并可点击打开
 * @author user
 *
 */
public class GetPackageList extends Activity {
	private List<AppInfo> mList = new ArrayList<AppInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_getpackage_list);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "获取应用列表");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		LemonBubble.showRoundProgress(GetPackageList.this, "查询中");
		handler.sendEmptyMessageDelayed(0,2000);//线去获取程序相关内容
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0){
				getPackageInfo() ;//获取程序包的信息
			}else if (msg.what == 1){
				ListView list_packageManager = (ListView) findViewById(R.id.list_packageManager);
				PackageAdapter adapter = new PackageAdapter(GetPackageList.this,mList,R.layout.layout_pachage_list_item);
				list_packageManager.setAdapter(adapter);
				LemonBubble.hide();
				list_packageManager.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = mList.get(position).getIntent();//直接打开本应用的第一个activity
						startActivity(intent);
					}
				});
			}
		}
	};
	private void getPackageInfo() {
		//获取包管理器
		PackageManager pm = this.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		//通过查询获取所有的ResolveInfo对象
		List<ResolveInfo> reList = pm.queryIntentActivities(mainIntent, PackageManager.MATCH_ALL);//查询所有应用
		//调用系统排序，根据那么排序，该排序很重要，否则只能显示系统应用，而不能显示第三方应用
		Collections.sort(reList,new ResolveInfo.DisplayNameComparator(pm));
		if (mList != null) {
			mList.clear();//先清空列表内容
			for (ResolveInfo resolveInfo : reList) {
				//获得应用的启动的activity的那么
				String activityName = resolveInfo.activityInfo.name;
				//程序应用包名
				String packageName = resolveInfo.activityInfo.packageName;
				//应用名称
				String appName = (String) resolveInfo.loadLabel(pm);
				//应用图标
				Drawable appIcon = resolveInfo.loadIcon(pm);

				//为程序启动的activity准备intent
				Intent launchIntent = new Intent();
				launchIntent.setComponent(new ComponentName(packageName,activityName));

				AppInfo appInfo = new AppInfo();
				appInfo.setAppLabel(appName);
				appInfo.setPkgName(packageName);
				appInfo.setIntent(launchIntent);
				appInfo.setAppIcon(appIcon);

				mList.add(appInfo);
			}

			handler.sendEmptyMessage(1);
		}
	}

	private class PackageAdapter extends CommonAdapter<AppInfo>{

		private PackageAdapter(Context context, List<AppInfo> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, AppInfo item) {
			ImageView iv_pachage_icon = helper.getView(R.id.iv_pachage_icon);
			TextView txt_appNames = helper.getView(R.id.txt_appNames);
			TextView txt_pachageNames = helper.getView(R.id.txt_pachageNames);

			iv_pachage_icon.setImageDrawable(item.getAppIcon());
			txt_appNames.setText(item.getAppLabel());
			txt_pachageNames.setText(item.getPkgName());
		}

	}

}
