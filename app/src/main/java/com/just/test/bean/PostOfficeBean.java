package com.just.test.bean;

/**
 * 全国邮编查询
 * Created by admin on 2017/6/1.
 */

public class PostOfficeBean {

    private String postnumber;//邮编
    private String address;//详细地址

    public String getPostnumber() {
        return postnumber;
    }

    public void setPostnumber(String postnumber) {
        this.postnumber = postnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
