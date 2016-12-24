package com.codekong.festival_sms.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.codekong.festival_sms.R;
import com.codekong.festival_sms.bean.FestivalLab;
import com.codekong.festival_sms.bean.Msg;
import com.codekong.festival_sms.config.Config;


public class ChooseMsgActivity extends AppCompatActivity {
    private ListView mMsgListView;
    private FloatingActionButton mFabToSend;
    private ArrayAdapter<Msg> mAdapter;
    private LayoutInflater mInflater;

    //节日类别Id
    private int mFestivalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);
        mInflater = LayoutInflater.from(this);
        mFestivalId = getIntent().getIntExtra(Config.FESTIVAL_ID, -1);

        setTitle(FestivalLab.getInstance().getFestivalById(mFestivalId).getName());

        initView();
        initEvent();
    }

    private void initEvent() {
        mFabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMsgActivity.toActivity(ChooseMsgActivity.this, mFestivalId, -1);
            }
        });
    }


    private void initView() {
        mMsgListView = (ListView) findViewById(R.id.id_lv_msgs);
        mFabToSend = (FloatingActionButton) findViewById(R.id.id_fab_toSend);

        mMsgListView.setAdapter(mAdapter = new ArrayAdapter<Msg>(this, -1, FestivalLab.getInstance().getMsgsByFestivalId(mFestivalId)){
            @NonNull
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null){
                    viewHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.msg_item, parent, false);
                    viewHolder.contentTv = (TextView) convertView.findViewById(R.id.id_tv_content);
                    viewHolder.toSendBtn = (Button) convertView.findViewById(R.id.id_btn_toSend);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.contentTv.setText("    " + getItem(position).getContent());
                viewHolder.toSendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMsgActivity.toActivity(ChooseMsgActivity.this, mFestivalId, getItem(position).getId());
                    }
                });
                return convertView;
            }
        });
    }

    class ViewHolder{
        TextView contentTv;
        Button toSendBtn;
    }
}
