package com.cqupt.weather.base;

import android.app.Application;
import android.content.Context;

import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.cqupt.weather.common.CrashHandler;
import com.cqupt.weather.component.RetrofitSingleton;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {

//    public static final String APP_ID = "e507c0bcc118a03dc86d3c11548203a1";
    public static final String APP_ID = "a977b306214e65c5ec7042f1a3b60800";
    public static String cacheDir = "";
    public static Context mAppContext = null;


    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化BmobSDK
        Bmob.initialize(getApplicationContext(), APP_ID);
        initConfig(getApplicationContext());

        mAppContext = getApplicationContext();
        // 初始化 retrofit
        RetrofitSingleton.init(getApplicationContext());
        CrashHandler.init(new CrashHandler(getApplicationContext()));

        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();

        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }


    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 初始化文件配置
     *
     * @param context context
     */
    public static void initConfig(Context context) {
        BmobConfiguration config = new BmobConfiguration.Builder(context).customExternalCacheDir("dish").build();
        BmobPro.getInstance(context).initConfig(config);
    }
}
