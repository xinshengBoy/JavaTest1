package com.just.test.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class StudentInformation implements Serializable{

    private String name;
    private boolean isCheck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
