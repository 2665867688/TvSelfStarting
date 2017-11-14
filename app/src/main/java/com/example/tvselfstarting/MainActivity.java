/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.tvselfstarting;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvselfstarting.utils.AppUtils;
import com.example.tvselfstarting.utils.Contants;
import com.example.tvselfstarting.utils.DataUtils;

/**
 * ClassName : MainActivity
 * createTime: 2017/11/11 10:05
 * author: shimy
 * description:
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnThisSelfStart;
    private Button btnTestStart;
    private Button btnProgramList;
    private TextView tvAppDesc;
    private ImageView imgAppIcon;
    private String appName;
    private String appPackageName;
    private String firstActivityName;
    private Drawable drawableIcon;
    private PackageManager pm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pm = getPackageManager();
        btnProgramList = findViewById(R.id.btn_main_setting_program);
        btnProgramList.setOnClickListener(this);
        btnTestStart = findViewById(R.id.btn_main_starapp);
        btnTestStart.setOnClickListener(this);
        btnThisSelfStart = findViewById(R.id.btn_main_thisselfstart);
        btnThisSelfStart.setOnClickListener(this);
        tvAppDesc = findViewById(R.id.tv_main_showappdesc);
        imgAppIcon = findViewById(R.id.img_main_appicon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appName = DataUtils.getInstance(this).getString(Contants.appNameKey);
        appPackageName = DataUtils.getInstance(this).getString(Contants.packageNameKey);
        firstActivityName = DataUtils.getInstance(this).getString(Contants.firstActivityKey);
        tvAppDesc.setText("应用名称：" + appName + "\n应用包名：" + appPackageName + "\n第一个启动的Activity：" + firstActivityName);
//        Toast.makeText(this, ""+appName, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(appPackageName)) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(appPackageName, 0);
                if (packageInfo.applicationInfo.loadIcon(pm) != null) {
                    drawableIcon = packageInfo.applicationInfo.loadIcon(pm);
                } else {
                    drawableIcon = getResources().getDrawable(R.drawable.default_program_round);
                }
                imgAppIcon.setImageDrawable(drawableIcon);
            } catch (PackageManager.NameNotFoundException e) {

            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_setting_program://获取所有程序列表
                startActivity(new Intent(this, ProgramListActivity.class));
                break;
            case R.id.btn_main_starapp://启动app测试
                AppUtils.startApp(this,appPackageName,firstActivityName);
                break;
            case R.id.btn_main_thisselfstart://设置界面
                Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
        }
    }
}
