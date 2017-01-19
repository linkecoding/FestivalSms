package com.codekong.festival_sms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codekong.festival_sms.config.Config;

/**
 * Created by szh on 2017/1/16.
 */

public class SmsDbOpenHelper extends SQLiteOpenHelper{
    //单例模式
    private static SmsDbOpenHelper mHelper;
    private SmsDbOpenHelper(Context context) {
        super(context.getApplicationContext(), Config.DB_NAME, null, Config.DB_VERSION);
    }

    public static SmsDbOpenHelper getInstance(Context context){
        if (mHelper == null){
            synchronized (SmsDbOpenHelper.class){
                if (mHelper == null){
                    mHelper = new SmsDbOpenHelper(context);
                }
            }
        }
        return mHelper;
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
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
