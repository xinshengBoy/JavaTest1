package com.just.test.bean;

import java.io.Serializable;

/**
 * 列表勾选
 * Created by admin on 2017/8/2.
 */

public class CheckList implements Serializable {

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
