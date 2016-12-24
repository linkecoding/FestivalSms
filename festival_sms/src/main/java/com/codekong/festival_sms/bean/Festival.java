package com.codekong.festival_sms.bean;

import java.util.Date;

/**
 * Created by szh on 2016/12/22.
 */

public class Festival {
    private int id;
    private String name;
    private String desc;
    private Date date;

    public Festival(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
