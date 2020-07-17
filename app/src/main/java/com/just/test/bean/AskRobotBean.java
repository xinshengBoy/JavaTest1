package com.just.test.bean;

import java.io.Serializable;

/**
 * 聊天机器人聊天记录
 * Created by admin on 2017/6/21.
 */

public class AskRobotBean implements Serializable{

    private String askDetail;//问的内容
    private String answerDetail;//回答内容

    public String getAskDetail() {
        return askDetail;
    }

    public void setAskDetail(String askDetail) {
        this.askDetail = askDetail;
    }

    public String getAnswerDetail() {
        return answerDetail;
    }

    public void setAnswerDetail(String answerDetail) {
        this.answerDetail = answerDetail;
    }
}
