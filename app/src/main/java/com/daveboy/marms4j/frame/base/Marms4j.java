package com.daveboy.marms4j.frame.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.daveboy.marms4j.demo.ApiService;
import com.daveboy.marms4j.frame.network.BaseParamsInterceptor;
import com.daveboy.marms4j.frame.network.ResponseTransformer;
import com.daveboy.marms4j.frame.network.RetrofitManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 *@description 工具类的初始化
 *@author mxm
 *@creatTime 2018/11/22  15:31
 */
public class Marms4j{
    private static Application instance;

    /**
     *
     * @param application
     * @param url Retrofit的baseurl
     */
    public static Config init(Application application,String url){

        instance=application;
        Utils.init(application);

        //我也不知道注册这个ActivityLifecycle干嘛  也许后面用得到呢...不喜欢注释了也没事
        application.registerActivityLifecycleCallbacks(new ActivityLifeCycle());

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(2)
                .methodOffset(7)
                .tag("Marms4j")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        RetrofitManager.getInstance().init(url);
        return new Config();
    }
    public static Application getInstance(){
        return instance;
    }
    public static class Config{
        Config(){}
        public Config setSuccessCode(int successCode){
            ResponseTransformer.SUCCESS_CODE=successCode;
            return this;
        }
        public Config addGlobalPara(String key,Object value){
            BaseParamsInterceptor.addGlobalPara(key,value);
            return this;
        }
    }
}
