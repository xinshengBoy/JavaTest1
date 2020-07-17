package com.just.test.bean;

/**
 * 全国行政区域划分
 * Created by admin on 2017/6/1.
 */

public class ProvinceDivice {

    private String area_id;//地区id
    private String parent_id;//父类id
    private String name;//区域名称
    private String level;//所属级别
    private String seq;//区域代号

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
