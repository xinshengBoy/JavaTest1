package com.just.test.bean;

public class Dish {

	private String imageUrl;//图片地址
	private String name;//菜名
	private String price;//菜价

	public Dish(String imageUrl,String name,String price){
		this.imageUrl = imageUrl;
		this.name = name;
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}


}
