package com.codekong.festival_sms.config;

import android.net.Uri;

/**
 * Created by szh on 2016/12/23.
 */

public class Config {
    public static final String FESTIVAL_ID = "festival_id";
    public static final String MSG_ID = "msg_id";

    /*********************数据库相关配置*************************/
    //数据库版本
    public static final int DB_VERSION = 1;
    //数据库名称
    public static final String DB_NAME = "sms.db";
    public static final String TABLE_NAME = "tb_sended_msg";
    public static final String COLUMN_MSG = "msg";
    public static final String COLUMN_NUMBERS = "numbers";
    public static final String COLUMN_NAMES = "names";
    public static final String COLUMN_FESTIVAL_NAME = "festivalName";
    public static final String COLUMN_DATE = "date";

    /*********************数据库相关配置*************************/
    /*********************ContentProvider配置*************************/
    public static final String AUTHORITY = "com.codekong.festival_sms.provider.SmsProvider";
    public static final Uri URI_SMS_ALL = Uri.parse("content://" + AUTHORITY + "/sms");
    /*********************ContentProvider配置*************************/

}
