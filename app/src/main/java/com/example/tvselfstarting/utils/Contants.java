package com.example.tvselfstarting.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by 中国 on 2017/11/11.
 * 存放常量
 */

public class Contants {
    public static String appName;
    public static String appPackageName;
    public static String appFstActName;
    public static Drawable appIcon;

    /*
     * 存储自启动程序数据的sharepreferences
     */
    public static String sp_name = "sp_selfapp";
    public static String packageNameKey = "packageName";
    public static String firstActivityKey = "firstActivity";
    public static String appNameKey = "appName";
    public static String startWayKey = "startWayKey";

    /*
     * 启动方式
     */
    public static final String startTvWay = "startTvWay";
    public static final String startAppWay = "startAppWay";

}
