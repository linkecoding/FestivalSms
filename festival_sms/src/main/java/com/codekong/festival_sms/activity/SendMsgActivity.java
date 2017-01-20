package com.codekong.festival_sms.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codekong.festival_sms.R;
import com.codekong.festival_sms.bean.Festival;
import com.codekong.festival_sms.bean.FestivalLab;
import com.codekong.festival_sms.bean.Msg;
import com.codekong.festival_sms.bean.SendedMsg;
import com.codekong.festival_sms.biz.SmsBiz;
import com.codekong.festival_sms.config.Config;
import com.codekong.festival_sms.view.FlowLayout;

import java.util.HashSet;
import java.util.concurrent.atomic.DoubleAccumulator;

public class SendMsgActivity extends BaseActivity implements View.OnClickListener {
    private String mMsgContent;
    //节日id
    private int mFestivalId;
    //消息id
    private int mMsgId;

    //节日
    private Festival mFestival;
    //消息
    private Msg mMsg;

    //短信输入框
    private EditText mEdMsg;
    //添加联系人按钮
    private Button mBtnAddContact;
    //浮动布局显示联系人
    private FlowLayout mFlContacts;
    //发送短信按钮
    private FloatingActionButton mFabSend;
    //加载布局
    private View mLayoutLoading;
    private static  final int CODE_REQUEST = 1;

    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();

    private LayoutInflater mInflater;

    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";

    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;

    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverroadcastReceiver;

    private SmsBiz mSmsBiz;

    //已经发送的短信的数目
    private int mMsgSendCount;
    private int mTotalCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        mInflater = LayoutInflater.from(this);
        mSmsBiz = new SmsBiz(this);
        initDatas();
        initViews();
        initEvents();
        initReceivers();
    }

    /**
     * 初始化广播接收器
     */
    private void initReceivers() {
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mMsgSendCount ++;
                if (getResultCode() == RESULT_OK){
                    Log.d("TAG", "onReceive: 短信发送成功");
                }else{
                    Log.d("TAG", "onReceive: 短信发送失败");
                }
                Toast.makeText(context, (mMsgSendCount + "/" + mTotalCount) + "短信发送成功 ", Toast.LENGTH_SHORT).show();

                if (mMsgSendCount == mTotalCount){
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == RESULT_OK){
                    Log.d("TAG", "onReceive: 联系人已经接收到短信");
                }else{
                    Log.d("TAG", "onReceive: 联系人没有接收到短信");
                }
            }
        }, new IntentFilter(ACTION_DELIVER_MSG));
    }


    private void initEvents() {
        mBtnAddContact.setOnClickListener(this);

        mFabSend.setOnClickListener(this);
        /*mFabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        });*/
    }

    private SendedMsg buildSendedMsg(String msg) {
        SendedMsg sendedMsg = new SendedMsg();
        sendedMsg.setMsg(msg);
        sendedMsg.setFestivalName(mFestival.getName());
        String names = "";
        String numbers = "";
        for(String name : mContactNames){
            names += name + ";";
        }
        for (String number : mContactNums){
            numbers += number + ";";
        }
        sendedMsg.setNames(names.substring(0, names.length() - 1));
        sendedMsg.setNumbers(numbers.substring(0, numbers.length() - 1));
        return sendedMsg;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST){
            if (resultCode == RESULT_OK){
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                    String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    String number = getContactNumber(cursor);
                    if (!TextUtils.isEmpty(number)){
                        mContactNums.add(number);
                        mContactNames.add(contactName);
                        addTag(contactName);
                    }
                }
            }
        }
    }

    /**
     * 将联系人名字加入浮动布局中
     * @param contactName
     */
    private void addTag(String contactName) {
        TextView textView = (TextView) mInflater.inflate(R.layout.tag, mFlContacts, false);
        textView.setText(contactName);
        mFlContacts.addView(textView);
    }

    /**
     * 获得对应联系人的手机号
     * @param cursor
     * @return
     */
    private String getContactNumber(Cursor cursor) {
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;
        if (numberCount > 0){
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        cursor.close();
        return number;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverroadcastReceiver);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_addContact:
                addContacts();
                break;
            case R.id.id_fab_send:
                if (mContactNums.size() == 0){
                    Toast.makeText(SendMsgActivity.this, "请先选择联系人", Toast.LENGTH_SHORT).show();
                    return;
                }
                mMsgContent = mEdMsg.getText().toString();
                if (TextUtils.isEmpty(mMsgContent)){
                    Toast.makeText(SendMsgActivity.this, "短信内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMsg(mMsgContent);
                mMsgSendCount = 0;
                break;
            default:
                break;
        }
    }

    /**
     * 发送短信
     * @param msg
     */
    private void sendMsg(String msg){
        if (hasPermission(Manifest.permission.SEND_SMS)){
            mLayoutLoading.setVisibility(View.VISIBLE);
            doSendMsg();
        }else{
            requestPermission(Config.SEND_SMS_CODE, Manifest.permission.SEND_SMS);
        }
    }

    /**
     * 添加联系人
     */
    private void addContacts(){
        if (hasPermission(Manifest.permission.READ_CONTACTS)){
            doReadContacts();
        }else{
            requestPermission(Config.READ_CONTACTS_CODE, Manifest.permission.READ_CONTACTS);
        }
    }

    @Override
    public void doSendMsg() {
        mTotalCount = mSmsBiz.sendMsg(mContactNums, buildSendedMsg(mMsgContent), mSendPi, mDeliverPi);
    }

    @Override
    public void doReadContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CODE_REQUEST);
    }
}
