package com.codekong.festival_sms.biz;

import android.app.PendingIntent;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by szh on 2017/1/15.
 * 短信业务类
 */

public class SmsBiz {
    /**
     * 发送短信给一个号码
     * @param number
     * @param msg
     * @param sendPi
     * @param deliverPi
     * @return
     */
    public int sendMsg(String number, String msg, PendingIntent sendPi, PendingIntent deliverPi){
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String>  contents = smsManager.divideMessage(msg);
        for (String content : contents){
            smsManager.sendTextMessage(number, number, content, sendPi, deliverPi);
        }
        return contents.size();
    }

    /**
     * 发送一条短信给多个人
     * @param numbers
     * @param msg
     * @param sendPi
     * @param deliverPi
     * @return
     */
    public int sendMsg(Set<String> numbers, String msg, PendingIntent sendPi, PendingIntent deliverPi){
        SmsManager smsManager = SmsManager.getDefault();
        int result = 0;
        for (String number : numbers){
            int count = sendMsg(number, msg, sendPi, deliverPi);
            result += count;
        }
        return result;
    }
}