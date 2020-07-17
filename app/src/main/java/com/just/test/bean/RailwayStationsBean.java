package com.just.test.bean;

/**
 * 全国火车站点
 * Created by admin on 2017/6/17.
 */

public class RailwayStationsBean {
    private String code;//火车站编号
    private String name;//火车站名称
    private String py;//火车站名称拼音缩写
    private String pinyin;//火车站名拼音

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
