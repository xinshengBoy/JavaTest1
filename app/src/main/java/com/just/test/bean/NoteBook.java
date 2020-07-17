package com.just.test.bean;

import cn.bmob.v3.BmobObject;

public class NoteBook extends BmobObject {

	private String title;//标题
	private String content;//内容
	private String time;//创建时间
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
