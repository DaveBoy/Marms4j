package com.daveboy.marms4j.frame.mvp;

import android.text.TextUtils;

import com.daveboy.marms4j.frame.util.GsonUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ParamsBuilder{
    private Map<String,Object> map;
    ParamsBuilder(){
        this.map=new HashMap<>();
    }
    /**
     * 添加参数
     */
    public ParamsBuilder addParams(String key, Object value) {
        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("key 不能为空");
        }
        map.put(key, value);
        return this;
    }

    /**
     * 添加全局参数
     */
    public ParamsBuilder addCommonMap() {
        //TODO:添加统一参数，暂停没采用这种方式，而是在BaseParamsInterceptor中添加参数
        return this;
    }
    /**
     * 返回map集合
     */
    public Map bulid(){
        return map;
    }
    /**
     * 返回json字符串
     */
    public String toJson(){
        return GsonUtils.getInstance().toJson(map);
    }
    /**
     * 返回RequestBody
     */
    public RequestBody toRequestBody(){
        return RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),toJson());
    }
}
