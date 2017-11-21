package com.example.tvselfstarting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvselfstarting.adapter.ProgramListAdapter;
import com.example.tvselfstarting.domain.ProgramInfo;
import com.example.tvselfstarting.interfaces.OnRcyItemClickListener;
import com.example.tvselfstarting.utils.AppUtils;
import com.example.tvselfstarting.utils.Contants;
import com.example.tvselfstarting.utils.DataUtils;
import com.example.tvselfstarting.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName : ProgramListActivity
 * createTime: 2017/11/11 10:32
 * author: shimy
 * description: 获取所有的程序
 */
public class ProgramListActivity extends Activity implements View.OnClickListener {

    private TextView tvSize;
    private LinearLayout layoutProgress;

    private RecyclerView recyclerView;
    private List<ProgramInfo> list;
    private ProgramListAdapter adapter;
    private PackageManager packageManager;

    private EditText etSeacher;
    private Button btnSeacher;
    private List<ProgramInfo> seacherList;
    private TextView tvProgress;

    private Button btnTvWay;
    private Button btnAppWay;
    private Button btnGoneEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_list);
        initView();
    }

    private void initView() {
        tvSize = findViewById(R.id.tv_programlist_size);
        layoutProgress = findViewById(R.id.layout_programlist_progress);
        etSeacher = findViewById(R.id.et_programlist_seachcontent);
        btnSeacher = findViewById(R.id.btn_programlist_seacher);
        btnSeacher.setOnClickListener(this);
        seacherList = new ArrayList<>();
        tvProgress = findViewById(R.id.tv_programlist_progress);
        btnTvWay = findViewById(R.id.btn_programlist_tvway);
        btnTvWay.setOnClickListener(this);
        btnAppWay = findViewById(R.id.btn_programlist_tvapp);
        btnAppWay.setOnClickListener(this);
        btnGoneEdit = findViewById(R.id.btn_programlist_visableedit);
        btnGoneEdit.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerview_programlist);
        list = new ArrayList<>();
        adapter = new ProgramListAdapter(seacherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setOnRcyItemClickListener(new MyOnRcyItemClickListener());
        recyclerView.setAdapter(adapter);
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_programlist_seacher:
                if (list.size() <= 0) {
                    Toast.makeText(this, "没有检索到应用程序", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!TextUtils.isEmpty(etSeacher.getText().toString()))
                    seacherApp(etSeacher.getText().toString());
                else
                    Toast.makeText(this, "请输入要搜索的app name 支持模糊查询", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_programlist_tvway:
                Toast.makeText(this, "tv自启动方式", Toast.LENGTH_SHORT).show();
                DataUtils.getInstance(ProgramListActivity.this)
                        .putString(Contants.startWayKey, Contants.startTvWay);
                break;
            case R.id.btn_programlist_tvapp:
                Toast.makeText(this, "app自启动方式", Toast.LENGTH_SHORT).show();
                DataUtils.getInstance(ProgramListActivity.this)
                        .putString(Contants.startWayKey, Contants.startAppWay);
                break;
            case R.id.btn_programlist_visableedit:
                etSeacher.setVisibility(View.VISIBLE);
                break;
        }
    }


    class MyOnRcyItemClickListener implements OnRcyItemClickListener<ProgramInfo> {

        @Override
        public void onItemClick(RecyclerView recyclerView, ProgramInfo data, int position) {
            DataUtils.getInstance(ProgramListActivity.this).clear();
            DataUtils.getInstance(ProgramListActivity.this)
                    .putString(Contants.packageNameKey, data.getPackageName())
                    .putString(Contants.firstActivityKey, data.getFirstActivityName())
                    .putString(Contants.appNameKey, data.getName());
//            Toast.makeText(ProgramListActivity.this,
//                    DataUtils.getInstance(ProgramListActivity.this).getString(Contants.packageNameKey)
//                    +"\n"+DataUtils.getInstance(ProgramListActivity.this).getString(Contants.appNameKey)
//                    , Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void getData() {
        tvProgress.setText("正在加载数据···");
        new Thread(new Runnable() {
            @Override
            public void run() {
                packageManager = getPackageManager();
//        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        // get all apps
//        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
                List<PackageInfo> packInfos = packageManager.getInstalledPackages(0);
                tvSize.setText("本设备安装的应用程序数量为：" + packInfos.size() + "个");
                for (PackageInfo packageInfo : packInfos) {
                    ProgramInfo programInfo = new ProgramInfo();
                    programInfo.setName((String) packageInfo.applicationInfo.loadLabel(packageManager));
                    programInfo.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                    programInfo.setPackageName(packageInfo.packageName);
                    Intent localTvIntent = new Intent("android.intent.action.MAIN", null);
                    localTvIntent.addCategory("android.intent.category.LEANBACK_LAUNCHER");

                    Intent localAppIntent = new Intent("android.intent.action.MAIN", null);
                    localAppIntent.addCategory("android.intent.category.LAUNCHER");
                    String firstActivityName = AppUtils.getActivities(ProgramListActivity.this, packageInfo.packageName, localTvIntent);
                    if (TextUtils.isEmpty(firstActivityName)) {
                        firstActivityName = AppUtils.getActivities(ProgramListActivity.this, packageInfo.packageName, localAppIntent);
                    }
                    programInfo.setFirstActivityName(firstActivityName);
                    list.add(programInfo);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seacherList.clear();
                        seacherList.addAll(list);
                        layoutProgress.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }


    private void seacherApp(final String appName) {
        tvProgress.setText("检索中···");
        layoutProgress.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                seacherList.clear();
                for (ProgramInfo programInfo : list) {
                    if (PinyinUtils.getPingYin(programInfo.getName()).toUpperCase().contains(PinyinUtils.getPingYin(appName).toUpperCase())) {
                        seacherList.add(programInfo);
                    }

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        layoutProgress.setVisibility(View.GONE);
                    }
                });

            }
        }).start();
    }


}
