package com.codekong.festival_sms.activity;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codekong.festival_sms.config.Config;

/**
 * Created by szh on 2017/1/20.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * 判断是否有权限
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions){
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * 请求权限
     * @param code
     * @param permissions
     */
    public void requestPermission(int code, String... permissions){
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 读取联系人操作
     */
    public void doReadContacts(){
    }

    /**
     * 写联系人操作
     */
    public void doWriteContacts(){
    }

    /**
     * 发送短信操作操作
     */
    public void doSendMsg(){
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Config.READ_CONTACTS_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    doReadContacts();
                }else{
                    Toast.makeText(this, "请授予该应用读取联系人权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case Config.WRITE_CONTACTS_CODE:
                doWriteContacts();
                break;
            case Config.SEND_SMS_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    doSendMsg();
                }else{
                    Toast.makeText(this, "请授予该应用发送短信权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
