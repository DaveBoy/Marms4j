package com.daveboy.marms4j.demo;

import android.app.Application;

import com.daveboy.marms4j.frame.base.Marms4j;
import com.daveboy.marms4j.frame.network.BaseParamsInterceptor;
import com.daveboy.marms4j.frame.network.ResponseTransformer;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
         Marms4j.init(this, "http://www.wanandroid.com")
                .setSuccessCode(0)
                .addGlobalPara("password", "15023822235");
    }
}
