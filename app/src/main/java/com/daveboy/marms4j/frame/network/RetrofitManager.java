package com.daveboy.marms4j.frame.network;

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
     * @param service 网络请求接口
     * @param <T>
     * @return
     */
    public  <T>T getRequest(Class<T> service) {
        if(retrofit==null){
            throw new RuntimeException("必须先调用RetrofitManager.getInstance().init(url)进行初始化");
        }
        if(!service.isInterface()){
            throw new RuntimeException("必须传入网络请求接口的class");
        }
        return retrofit.create(service);
    }
}
