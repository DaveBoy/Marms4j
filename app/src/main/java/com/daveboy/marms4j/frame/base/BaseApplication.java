package com.daveboy.marms4j.frame.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.daveboy.marms4j.demo.ApiService;
import com.daveboy.marms4j.frame.network.RetrofitManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 *@description 自定义Application
 *@author mxm
 *@creatTime 2018/11/22  15:31
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();

        instance=this;
        Utils.init(this);
        this.registerActivityLifecycleCallbacks(new ActivityLifeCycle());
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(2)
                .methodOffset(7)
                .tag("logger")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    public static Application getInstance(){
        return instance;
    }
}
