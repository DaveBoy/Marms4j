package com.daveboy.marms4j.demo;

import com.daveboy.marms4j.frame.base.BaseApplication;
import com.daveboy.marms4j.frame.network.BaseParamsInterceptor;
import com.daveboy.marms4j.frame.network.ResponseTransformer;
import com.daveboy.marms4j.frame.network.RetrofitManager;

public class DemoApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.getInstance().init("http://www.wanandroid.com");
        ResponseTransformer.SUCCESS_CODE=0;
        BaseParamsInterceptor.addGlobalPara("password","15023822235");
    }
}
