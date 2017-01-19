package com.codekong.festival_sms.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.codekong.festival_sms.bean.SendedMsg;
import com.codekong.festival_sms.config.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by szh on 2017/1/15.
 * 短信业务类
 */

public class SmsBiz {
    private Context context;
    public SmsBiz(Context context){
        this.context = context;
    }

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
    public int sendMsg(Set<String> numbers, SendedMsg msg, PendingIntent sendPi, PendingIntent deliverPi){
        save(msg);
        int result = 0;
        for (String number : numbers){
            int count = sendMsg(number, msg.getMsg(), sendPi, deliverPi);
            result += count;
        }
        return result;
    }

    private void save(SendedMsg sendedMsg){
        sendedMsg.setDate(new Date());
        ContentValues values = new ContentValues();
        values.put(Config.COLUMN_DATE, sendedMsg.getDate().getTime());
        values.put(Config.COLUMN_FESTIVAL_NAME, sendedMsg.getFestivalName());
        values.put(Config.COLUMN_MSG, sendedMsg.getMsg());
        values.put(Config.COLUMN_NAMES, sendedMsg.getNames());
        values.put(Config.COLUMN_NUMBERS, sendedMsg.getNumbers());
        context.getContentResolver().insert(Config.URI_SMS_ALL, values);
    }
}