package com.codekong.festival_sms.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.codekong.festival_sms.config.Config;
import com.codekong.festival_sms.db.SmsDbOpenHelper;

/**
 * Created by szh on 2017/1/18.
 */

public class SmsProvider extends ContentProvider {
    private static UriMatcher matcher;
    private static final int SMS_ALL = 0;
    private static final int SMS_ONE = 1;

    private SmsDbOpenHelper mHelper;
    private SQLiteDatabase mDb;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Config.AUTHORITY, "sms", SMS_ALL);
        matcher.addURI(Config.AUTHORITY, "sms/#", SMS_ONE);
    }
    @Override
    public boolean onCreate() {
        mHelper = SmsDbOpenHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = matcher.match(uri);
        switch(match){
            case SMS_ALL:
                break;
            case SMS_ONE:
                long id = ContentUris.parseId(uri);
                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        mDb = mHelper.getReadableDatabase();
        Cursor cursor = mDb.query(Config.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), Config.URI_SMS_ALL);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = matcher.match(uri);
        if(match != SMS_ALL){
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        mDb = mHelper.getWritableDatabase();
        long rowId = mDb.insert(Config.TABLE_NAME, null, values);
        if (rowId > 0){
            notifyDataSetChange();
            return ContentUris.withAppendedId(uri, rowId);
        }
        return null;
    }

    private void notifyDataSetChange() {
        getContext().getContentResolver().notifyChange(Config.URI_SMS_ALL, null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
