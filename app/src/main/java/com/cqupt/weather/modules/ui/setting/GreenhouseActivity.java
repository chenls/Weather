package com.cqupt.weather.modules.ui.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cqupt.weather.R;
import com.cqupt.weather.base.BaseActivity;
import com.cqupt.weather.bean.historyData;
import com.cqupt.weather.common.Util;
import com.cqupt.weather.modules.adatper.GreenhouseAdapter;
import com.cqupt.weather.modules.domain.Setting;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

public class GreenhouseActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse);
        initView();
        initRecyclerView();
        queryGreenhouse(GreenhouseActivity.this);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView banner = (ImageView) findViewById(R.id.banner);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setStatusBarColorForKitkat(R.color.colorSunrise);
        if (mSetting.getInt(Setting.HOUR, 0) < 6 || mSetting.getInt(Setting.HOUR, 0) > 18) {
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.colorSunset));
            Glide.with(this)
                    .load(R.mipmap.city_night)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(banner);
            setStatusBarColorForKitkat(R.color.colorSunset);
        }
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        //fab
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFabDialog();
            }
        });
        collapsingToolbarLayout.setTitle("花房历史数据");
    }

    private void showFabDialog() {
        new AlertDialog.Builder(GreenhouseActivity.this).setTitle("点赞")
                .setMessage("去项目地址给作者个Star，鼓励下作者୧(๑•̀⌄•́๑)૭✧")
                .setPositiveButton("好叻", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse(getString(R.string.app_html));   //指定网址
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);           //指定Action
                        intent.setData(uri);                            //设置Uri
                        GreenhouseActivity.this.startActivity(intent);        //启动Activity
                    }
                })
                .show();
    }

    public void setStatusBarColorForKitkat(int color) {
        /**
         * Android4.4
         */
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    private void queryGreenhouse(final Context context) {
        if (!Util.isNetworkConnected(context)) {
            return;
        }
        final cn.bmob.v3.BmobQuery<historyData> bmobQuery = new cn.bmob.v3.BmobQuery<>();
        bmobQuery.setLimit(15);
        bmobQuery.order("-updatedAt");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(context, historyData.class);
        if (isCache) {
            bmobQuery.setCachePolicy(cn.bmob.v3.BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 先从缓存取数据，如果没有的话，再从网络取。
        } else {
            bmobQuery.setCachePolicy(cn.bmob.v3.BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则先从网络中取
        }
        bmobQuery.findObjects(context, new FindListener<historyData>() {

            @Override
            public void onSuccess(List<historyData> historyDataList) {
                mProgressBar.setVisibility(View.INVISIBLE);
                GreenhouseAdapter mAdapter = new GreenhouseAdapter(GreenhouseActivity.this, historyDataList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(int code, String msg) {
                Log.d("myLog", code + msg);
            }
        });
    }
}
