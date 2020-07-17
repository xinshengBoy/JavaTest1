package com.just.test.bean;

public class OptionalBean {
	private String num;
	private String CH_Name;
	private String EN_Name;
	private Boolean checked;
	public OptionalBean() {
		super();
	}



	public OptionalBean(String num, String CH_Name, String EN_Name, Boolean checked) {
		super();
		this.num = num;
		this.CH_Name = CH_Name;
		this.EN_Name = EN_Name;
		this.checked = checked;
	}
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCH_Name() {
		return CH_Name;
	}

	public void setCH_Name(String cH_Name) {
		CH_Name = cH_Name;
	}

	public String getEN_Name() {
		return EN_Name;
	}

	public void setEN_Name(String eN_Name) {
		EN_Name = eN_Name;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}
