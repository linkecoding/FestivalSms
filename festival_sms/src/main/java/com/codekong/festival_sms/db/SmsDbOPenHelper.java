package com.codekong.festival_sms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codekong.festival_sms.config.Config;

/**
 * Created by szh on 2017/1/16.
 */

public class SmsDbOPenHelper extends SQLiteOpenHelper{
    public SmsDbOPenHelper(Context context) {
        super(context, Config.DB_NAME, null, Config.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Config.TABLE_NAME + "(" +
                "_id integer primary key autoincrement," +
                Config.COLUMN_MSG + " text," +
                Config.COLUMN_NUMBERS + " text," +
                Config.COLUMN_NAMES + " text," +
                Config.COLUMN_FESTIVAL_NAME + " text," +
                Config.COLUMN_DATE + " integer" +
                ")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
