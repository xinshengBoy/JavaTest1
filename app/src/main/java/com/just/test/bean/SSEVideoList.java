package com.just.test.bean;

/**
 * gsonFormat工具类，可复制一段json自动生成bean类，
 * https://github.com/zzz40500/GsonFormat     详细介绍及使用过程
 * 第一步
 * File > Settings > Plugins > Browse repositories... > Search for "GsonFormat" > Install Plugin
 * 第二步
 * 安装--》重启studio--》新建一个bean类--》右键--generate--》gsonformat--》输入或者粘贴一段json，点击ok就生成下面的bean了
 * Created by admin on 2017/5/16.
 */

public class SSEVideoList {

    /**
     * createTime : 2016-01-26 14:47:48
     * docURL : http://mobile.sseinfo.com/options/course/optionGM/c/4048126.shtml
     * docTitle : 第一集：期权基础ABC
     * extLINK : http://edu.sse.com.cn/col/option/optionGM/a/1.mp4
     * extSSE_YJCB_SFJB : http://edu.sse.com.cn/col/option/open-class/optionGM/images/optionGM1.jpg
     * extStarttest_link : http://edu.sse.com.cn/col/option/optionGM/a/1.mp4
     */

    private String createTime;
    private String docURL;
    private String docTitle;
    private String extLINK;
    private String extSSE_YJCB_SFJB;
    private String extStarttest_link;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDocURL() {
        return docURL;
    }

    public void setDocURL(String docURL) {
        this.docURL = docURL;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getExtLINK() {
        return extLINK;
    }

    public void setExtLINK(String extLINK) {
        this.extLINK = extLINK;
    }

    public String getExtSSE_YJCB_SFJB() {
        return extSSE_YJCB_SFJB;
    }

    public void setExtSSE_YJCB_SFJB(String extSSE_YJCB_SFJB) {
        this.extSSE_YJCB_SFJB = extSSE_YJCB_SFJB;
    }

    public String getExtStarttest_link() {
        return extStarttest_link;
    }

    public void setExtStarttest_link(String extStarttest_link) {
        this.extStarttest_link = extStarttest_link;
    }
}
