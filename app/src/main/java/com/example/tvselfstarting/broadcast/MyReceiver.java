package com.example.tvselfstarting.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.tvselfstarting.MainActivity;
import com.example.tvselfstarting.utils.AppUtils;
import com.example.tvselfstarting.utils.Contants;
import com.example.tvselfstarting.utils.DataUtils;

/**
 * ClassName : MyReceiver
 * createTime: 2017/11/11 15:39
 * author: shimy
 * description:捕捉Android 设备开机启动时的广播 然后启动要启动的app
 */
public class MyReceiver extends BroadcastReceiver {
    //Android设备开机是会发送的一个广播
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    private String appPackageName;
    private String firstActivityName;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            appPackageName = DataUtils.getInstance(context).getString(Contants.packageNameKey);
            firstActivityName = DataUtils.getInstance(context).getString(Contants.firstActivityKey);
            if (!TextUtils.isEmpty(appPackageName) && !TextUtils.isEmpty(firstActivityName)) {
                Toast.makeText(context, "正在启动程序请稍安勿躁", Toast.LENGTH_SHORT).show();
                AppUtils.startApp(context, appPackageName, firstActivityName);
            }
        }
    }
}
