package com.example.tvselfstarting.domain;

import android.app.Activity;
import android.graphics.drawable.Drawable;

/**
 * ClassName : ProgramInfo
 * createTime: 2017/11/11 10:29
 * author: shimy
 * description: 获取到的程序信息
 */
public class ProgramInfo {
    private String name;//程序名
    private Drawable icon;//程序图标
    private Activity firstActivity;//首启动的activity
    private String firstActivityName;//首启动activity的类名
    private String packageName;//程序包名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Activity getFirstActivity() {
        return firstActivity;
    }

    public void setFirstActivity(Activity firstActivity) {
        this.firstActivity = firstActivity;
    }

    public String getFirstActivityName() {
        return firstActivityName;
    }

    public void setFirstActivityName(String firstActivityName) {
        this.firstActivityName = firstActivityName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
