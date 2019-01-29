package com.daveboy.marms4j.frame.network;

import android.webkit.URLUtil;

import com.blankj.utilcode.util.StringUtils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *@description 网络请求工具类,必须先调用RetrofitManager.getInstance().init(url)进行初始化,建议在application中初始化
 *@author mxm
 *@creatTime 2018/11/21  13:56
 */
public class RetrofitManager {
    private static RetrofitManager mInstance;
    private Retrofit retrofit;
    private Object service;
    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 初始化
     * @param url baseurl
     */
    public void init(String url) {
        if(StringUtils.isEmpty(url)||!(URLUtil.isHttpUrl(url)||URLUtil.isHttpsUrl(url))){
            throw new IllegalArgumentException(String.format("the fucking url %s is not correct",url));
        }
        // 初始化okhttp
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        BaseParamsInterceptor baseParamsInterceptor = new BaseParamsInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(baseParamsInterceptor)
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * @param serviceClass 网络请求接口
     * @return
     */
    public  <T> T  getRequest(Class<T>  serviceClass) {
        if(retrofit==null){
            throw new RuntimeException("必须先调用Marms4j.init进行初始化");
        }
        if(!serviceClass.isInterface()){
            throw new RuntimeException("必须传入网络请求接口的class");
        }
        if(service==null) {
            service = retrofit.create(serviceClass);
        }
        /**
         * 这里这么写主要是泛型无法保存下来，只能保存一个class，谁有什么好方法留个言，求你了....
         * 主要是service依赖注入的同时要能保留下来，小菜鸡确实没啥好办法了
         */
        return serviceClass.cast(service);
    }
}
