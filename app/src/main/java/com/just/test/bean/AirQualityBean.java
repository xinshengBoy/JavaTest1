package com.just.test.bean;

/**
 * 全国空气质量查询
 * Created by admin on 2017/6/1.
 */

public class AirQualityBean {

    private String city;//城市名称
    private String inputTime;//发布时间
    private String aqi;//空气质量指数
    private String level;//空气等级
    private String core;//首要污染物

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }
}
