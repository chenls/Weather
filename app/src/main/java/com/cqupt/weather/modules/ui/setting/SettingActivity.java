package com.cqupt.weather.modules.ui.setting;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cqupt.weather.R;
import com.cqupt.weather.base.BaseActivity;
import com.cqupt.weather.modules.domain.Setting;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setStatusBarColor(R.color.colorPrimary);
        if (mSetting.getInt(Setting.HOUR, 0) < 6 || mSetting.getInt(Setting.HOUR, 0) > 18) {
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSunset));
            setStatusBarColor(R.color.colorSunset);
        }
        getFragmentManager().beginTransaction().replace(R.id.framelayout, new SettingFragment()).commit();
    }
}
