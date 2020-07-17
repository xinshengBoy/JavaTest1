package com.just.test.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2017/5/23.
 */

public class Fruits extends DataSupport{

    private int fruitID;//id
    private String fruitName;//水果名称
    private String fruitAddress;//水果产地
    private String fruitColor;//水果颜色
    private String fruitDate;//水果季节
    private int fruitBG;//背景

    public Fruits(){

    }
    public Fruits(int fruitID,String fruitName, String fruitAddress, String fruitColor, String fruitDate,int fruitBG) {
        this.fruitID = fruitID;
        this.fruitName = fruitName;
        this.fruitAddress = fruitAddress;
        this.fruitColor = fruitColor;
        this.fruitDate = fruitDate;
        this.fruitBG = fruitBG;
    }

    public int getFruitID() {
        return fruitID;
    }

    public void setFruitID(int fruitID) {
        this.fruitID = fruitID;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitAddress() {
        return fruitAddress;
    }

    public void setFruitAddress(String fruitAddress) {
        this.fruitAddress = fruitAddress;
    }

    public String getFruitColor() {
        return fruitColor;
    }

    public void setFruitColor(String fruitColor) {
        this.fruitColor = fruitColor;
    }

    public String getFruitDate() {
        return fruitDate;
    }

    public void setFruitDate(String fruitDate) {
        this.fruitDate = fruitDate;
    }

    public int getFruitBG() {
        return fruitBG;
    }

    public void setFruitBG(int fruitBG) {
        this.fruitBG = fruitBG;
    }
}
