package com.codekong.festival_sms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codekong.festival_sms.R;
import com.codekong.festival_sms.bean.Festival;
import com.codekong.festival_sms.bean.FestivalLab;
import com.codekong.festival_sms.bean.Msg;
import com.codekong.festival_sms.config.Config;
import com.codekong.festival_sms.view.FlowLayout;

public class SendMsgActivity extends AppCompatActivity {
    private int mFestivalId;
    private int mMsgId;

    private Festival mFestival;
    private Msg mMsg;

    private EditText mEdMsg;
    private Button mBtnAddContact;
    private FlowLayout mFlContacts;
    private FloatingActionButton mFabSend;
    private View mLayoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        initDatas();
        initViews();
    }


    private void initViews() {
        mEdMsg = (EditText) findViewById(R.id.id_et_content);
        mBtnAddContact = (Button) findViewById(R.id.id_btn_addContact);
        mFlContacts = (FlowLayout) findViewById(R.id.id_fl_contacts);
        mFabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        mLayoutLoading = findViewById(R.id.id_layout_loading);
        mLayoutLoading.setVisibility(View.GONE);

        if (mMsgId != -1){
            mMsg = FestivalLab.getInstance().getMsgByMsgId(mMsgId);
            mEdMsg.setText(mMsg.getContent());
        }
    }


    private void initDatas() {
        mFestivalId = getIntent().getIntExtra(Config.FESTIVAL_ID, -1);
        mMsgId = getIntent().getIntExtra(Config.MSG_ID, -1);

        mFestival = FestivalLab.getInstance().getFestivalById(mFestivalId);
        setTitle(mFestival.getName());
    }

    public static void toActivity(Context context, int festivalId, int msgId){
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(Config.FESTIVAL_ID, festivalId);
        intent.putExtra(Config.MSG_ID,msgId);
        context.startActivity(intent);
    }
}
