package com.just.test.bean;

/**
 * Created by admin on 2017/5/8.
 */

public class MessageEvent {
    private int code;
    private String message;

    public MessageEvent(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
