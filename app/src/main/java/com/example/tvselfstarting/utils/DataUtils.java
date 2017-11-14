package com.example.tvselfstarting.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 中国 on 2017/11/11.
 * 存放要首启动的程序包名 及其activity
 */

public class DataUtils {

    private SharedPreferences sp;
    private Context context;
    private static DataUtils dataUtils;

    public DataUtils(Context context) {
        this.context = context;
        sp = context.getApplicationContext().getSharedPreferences(Contants.sp_name, Context.MODE_PRIVATE);
    }

    public static DataUtils getInstance(Context context) {
        if (dataUtils == null) {
            dataUtils = new DataUtils(context);
        }
        return dataUtils;
    }

    public DataUtils putString(String key, String value) {
        if (sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(Contants.sp_name, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
        return dataUtils;
    }

    public String getString(String key) {
        if (sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(Contants.sp_name, Context.MODE_PRIVATE);
        }
        return sp.getString(key, "");
    }

    public void clear(){
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}
