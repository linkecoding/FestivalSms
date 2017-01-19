package com.codekong.festival_sms.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codekong.festival_sms.R;
import com.codekong.festival_sms.config.Config;
import com.codekong.festival_sms.view.FlowLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by szh on 2017/1/19.
 */

public class SmsHistoryFragment extends ListFragment {
    private static final String TAG = "pyh";
    private static final int LOAD_ID = 1;
    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;

    private  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        initLoader();
        setupListAdapter();
    }

    private void setupListAdapter() {
        Log.d(TAG, "setupListAdapter: 1");
        mCursorAdapter = new CursorAdapter(getActivity(), null, false){
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return mInflater.inflate(R.layout.sended_msg_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                Log.d(TAG, "bindView: ");
                TextView msg = (TextView) view.findViewById(R.id.id_tv_msg);
                FlowLayout fl = (FlowLayout) view.findViewById(R.id.id_flt_contacts);
                TextView fes = (TextView) view.findViewById(R.id.id_tv_fes);
                TextView date = (TextView) view.findViewById(R.id.id_tv_date);

                msg.setText(cursor.getString(cursor.getColumnIndex(Config.COLUMN_MSG)));
                fes.setText(cursor.getString(cursor.getColumnIndex(Config.COLUMN_FESTIVAL_NAME)));
                long dateVal = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_DATE));
                date.setText(parseDate(dateVal));

                String names = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NAMES));

                Log.d(TAG, "bindView: " + cursor.getString(cursor.getColumnIndex(Config.COLUMN_MSG)) + " " + dateVal);

                if (TextUtils.isEmpty(names)){
                    Log.d(TAG, "bindView: null");
                    return;
                }
                fl.removeAllViews();
                String[] nameList = names.split(";");
                for (String name : nameList){
                    addTag(name, fl);
                }
            }
        };
        Log.d(TAG, "setupListAdapter: 2");
        setListAdapter(mCursorAdapter);
        Log.d(TAG, "setupListAdapter: 3");
    }

    private String parseDate(long dateVal) {
        Log.d(TAG, "parseDate: ");
        return df.format(dateVal);
    }

    /**
     * 往流式布局中添加标签(联系人姓名)
     * @param name
     * @param fl
     */
    private void addTag(String name, FlowLayout fl) {
        Log.d(TAG, "addTag: ");
        TextView tv = (TextView) mInflater.inflate(R.layout.tag, fl, false);
        tv.setText(name);
        fl.addView(tv);
    }

    private void initLoader() {
        getLoaderManager().initLoader(LOAD_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(getActivity(), Config.URI_SMS_ALL, null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (loader.getId() == LOAD_ID){
                    mCursorAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCursorAdapter.swapCursor(null);
            }
        });
    }
}
