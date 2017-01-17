package com.codekong.festival_sms.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by szh on 2017/1/16.
 * 已经发送的短信
 */

public class SendedMsg {
    private int id;
    private String msg;
    private String numbers;
    private String names;
    private String festivalName;
    private Date date;
    private String dateStr;

    private DateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        dateStr = dateFormat.format(date);
        return dateStr;
    }
}
