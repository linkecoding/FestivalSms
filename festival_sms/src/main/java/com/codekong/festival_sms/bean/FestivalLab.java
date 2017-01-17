package com.codekong.festival_sms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szh on 2016/12/22.
 * 节日类别
 */

public class FestivalLab {
    //节日类别
    private List<Festival> mFestivals = new ArrayList<>();
    //消息类
    private List<Msg> mMsgs = new ArrayList<>();

    //单例模式
    private static FestivalLab mInstance;
    private FestivalLab(){
        mFestivals.add(new Festival(1, "国庆节"));
        mFestivals.add(new Festival(2, "中秋节"));
        mFestivals.add(new Festival(3, "元旦"));
        mFestivals.add(new Festival(4, "元宵节"));
        mFestivals.add(new Festival(5, "春节"));
        mFestivals.add(new Festival(6, "圣诞节"));
        mFestivals.add(new Festival(7, "万圣节"));

        mMsgs.add(new Msg(1, 1, "111111111111111111111111111"));
        mMsgs.add(new Msg(2, 1, "111111111111111111111111111"));
        mMsgs.add(new Msg(3, 1, "111111111111111111111111111"));
        mMsgs.add(new Msg(4, 1, "111111111111111111111111111"));
        mMsgs.add(new Msg(5, 1, "111111111111111111111111111"));
        mMsgs.add(new Msg(6, 1, "111111111111111111111111111"));
        mMsgs.add(new Msg(7, 1, "111111111111111111111111111"));
    }

    public static FestivalLab getInstance(){
        if (mInstance == null){
            synchronized (FestivalLab.class){
                if (mInstance == null){
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }

    public List<Festival> getFestivals(){
        //返回一个副本
        return new ArrayList<>(mFestivals);
    }

    public Festival getFestivalById(int fesId){
        Festival resFestival = null;
        for(Festival festival : mFestivals){
            if (fesId == festival.getId()){
                resFestival = festival;
            }
        }
        return resFestival;
    }

    public List<Msg> getMsgsByFestivalId(int fesId){
        List<Msg> msgs = new ArrayList<>();
        for (Msg msg : mMsgs){
            if (msg.getFestivalId() == fesId){
                msgs.add(msg);
            }
        }
        return msgs;
    }

    public Msg getMsgByMsgId(int id){
        Msg resMsg = null;
        for (Msg msg : mMsgs){
            if (msg.getId() == id){
                resMsg = msg;
            }
        }
        return resMsg;
    }
}
