package com.just.test.bean;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {

	private String appLabel;    //应用程序标签
	private Drawable appIcon ;  //应用程序图像
	private Intent intent ;     //启动应用程序的Intent ，一般是Action为Main和Category为Lancher的Activity
	private String pkgName ;    //应用程序所对应的包名
	private String phoneName;   //联系人姓名
	private String phoneNumber;   //联系人号码

	public String getAppLabel() {
		return appLabel;
	}
	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
