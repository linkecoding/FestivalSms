package com.codekong.festival_sms.bean;

/**
 * Created by szh on 2016/12/23.
 * 消息Bean
 */

public class Msg {
    //消息序号
    private int id;
    //节日id
    private int festivalId;
    //消息内容
    private String content;

    public Msg(int id, int festivalId, String content) {
        this.id = id;
        this.festivalId = festivalId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(int festivalId) {
        this.festivalId = festivalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
