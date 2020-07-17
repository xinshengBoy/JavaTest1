package com.just.test.bean;

/**
 * 文字滚动的类
 */
public class Sentence {
	private String name;//内容
	private int index;//位置
	
	public Sentence(int index,String name){
		this.name=name;
		this.index=index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
