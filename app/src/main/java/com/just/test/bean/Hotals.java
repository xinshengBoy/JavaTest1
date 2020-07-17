package com.just.test.bean;

import com.yalantis.beamazingtoday.interfaces.BatModel;

/**
 * todolist模型
 * Created by admin on 2017/6/23.
 */

public class Hotals implements BatModel{

    private String name;
    private boolean isChecked;

    public Hotals(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public String getText() {
        return getName();
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
