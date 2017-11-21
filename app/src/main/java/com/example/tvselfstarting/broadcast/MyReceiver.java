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
    private String startWay;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
        Toast.makeText(context, "自启动", Toast.LENGTH_SHORT).show();
        appPackageName = DataUtils.getInstance(context).getString(Contants.packageNameKey);
        firstActivityName = DataUtils.getInstance(context).getString(Contants.firstActivityKey);
        startWay = DataUtils.getInstance(context).getString(Contants.startWayKey);
//            if (!TextUtils.isEmpty(appPackageName) && !TextUtils.isEmpty(firstActivityName)) {
        if (startWay.equals(Contants.startAppWay)) {
            Toast.makeText(context, "正在启动程序请稍安勿躁", Toast.LENGTH_SHORT).show();
            AppUtils.startApp(context, appPackageName, firstActivityName);
        } else if (startWay.equals(Contants.startTvWay)) {
            Toast.makeText(context, "正在启动程序请稍安勿躁", Toast.LENGTH_SHORT).show();
            AppUtils.startTvApp(context, appPackageName, firstActivityName);
        } else {
            Toast.makeText(context, "正在启动程序请稍安勿躁", Toast.LENGTH_SHORT).show();
            AppUtils.startApp(context, appPackageName, firstActivityName);
        }
//            }
        }
    }
}
