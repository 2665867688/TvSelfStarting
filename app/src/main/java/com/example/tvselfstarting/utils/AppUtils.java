package com.example.tvselfstarting.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by 中国 on 2017/11/11.
 * 工具类
 */

public class AppUtils {

    /**
     * 获取需要的activity
     * @param activity
     * @param packageName
     * @param localIntent
     * @return
     */
    public static String getActivities(Context activity, String packageName,Intent localIntent) {
        String activityName = "";
//        Intent localIntent = new Intent("android.intent.action.MAIN", null);
//        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> appList = activity.getPackageManager().queryIntentActivities(localIntent, 0);
        for (int i = 0; i < appList.size(); i++) {
            ResolveInfo resolveInfo = appList.get(i);
            String packageStr = resolveInfo.activityInfo.packageName;
            if (packageStr.equals(packageName)) {
                //这个就是你想要的那个Activity
                activityName = resolveInfo.activityInfo.name;
                break;
            }
        }
        return activityName;
    }

    /**
     *
     * @param context
     * @param packageName 包名
     * @param activityName 要启动的activity名称
     * @return
     */
    public static void startApp(Context context,String packageName,String activityName){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        context.startActivity(intent);
    }
    public static void startTvApp(Context context,String packageName,String activityName){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        context.startActivity(intent);
    }
}
