package com.just.test.bean;

/**
 * Created by admin on 2017/5/3.
 */

public class Films {
    private String name;
    private String author;
    private String time;
    private String dictor;
    private String yanyuan;
    private String country;
    private String type;

    public Films(String name,String author,String time,String dictor,String yanyuan,String country,String type){
        this.name = name;
        this.author = author;
        this.time = time;
        this.dictor = dictor;
        this.yanyuan = yanyuan;
        this.country = country;
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDictor() {
        return dictor;
    }

    public void setDictor(String dictor) {
        this.dictor = dictor;
    }

    public String getYanyuan() {
        return yanyuan;
    }

    public void setYanyuan(String yanyuan) {
        this.yanyuan = yanyuan;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
