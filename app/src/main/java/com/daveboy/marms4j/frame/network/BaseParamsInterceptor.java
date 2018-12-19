package com.daveboy.marms4j.frame.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 *@description 添加公共参数，或者在model基类中添加
 *@author mxm
 *@creatTime 2018/11/21  18:48
 */
public  class BaseParamsInterceptor implements Interceptor {
    /**
     * 请求方法-GET
     */
    private static final String REQUEST_METHOD_GET = "GET";

    /**
     * 请求方法POST
     */
    private static final String REQUEST_METHOD_POST = "POST";


    private Gson gson;
    private static Map<String,Object> map=new HashMap<>();
    public BaseParamsInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原先的请求对象
        Request request = chain.request();
        if (REQUEST_METHOD_GET.equals(request.method())) {
            request = addGetBaseParams(request);
        } else if (REQUEST_METHOD_POST.equals(request.method())) {
            request = addPostBaseParams(request);
        }
        return chain.proceed(request);

    }

    private Request addPostBaseParams(Request request) {
        Request.Builder requestBuilder = request.newBuilder();

        Iterator iterator = map.keySet().iterator();
        RequestBody requestBody = request.body();
        /**
         * @FormUrlEncoded
         */
        if (requestBody instanceof FormBody) {
                FormBody.Builder newFormBodyBuilder = new FormBody.Builder();

                //add basic param
                while (iterator.hasNext()){
                    String key=iterator.next().toString();
                    newFormBodyBuilder.add(key, String.valueOf(map.get(key)));
                }
                //end basic param

                FormBody oldFormBody = (FormBody) request.body();
                int paramSize = oldFormBody.size();
                if (paramSize > 0) {
                    for (int i = 0; i < paramSize; i++) {
                        newFormBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
                    }
                }

                return requestBuilder.post(newFormBodyBuilder.build()).build();
            }else if (requestBody instanceof PostJsonBody) {
                /**
                 * application/json body格式的post请求
                 */
                String content = ((PostJsonBody) requestBody).getContent();
                initGson();
                HashMap<String, Object> hashMap = gson.fromJson(content, new TypeToken<HashMap<String, Object>>(){}.getType());
                //添加统一参数
                while (iterator.hasNext()){
                    String key=iterator.next().toString();
                    hashMap.put(key, String.valueOf(map.get(key)));
                }
                //end

               return requestBuilder.post( RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(hashMap))).build();
            }
            return request;
    }

    private Request addGetBaseParams(Request request) {
        Iterator iterator = map.keySet().iterator();
        HttpUrl.Builder builder = request.url()
                .newBuilder();
        while (iterator.hasNext()){
            String key=iterator.next().toString();
            builder.addQueryParameter(key, String.valueOf(map.get(key)));
        }
        return request.newBuilder().url(builder.build()).build();
    }

    public static void addGlobalPara(String key,Object value){
        map.put(key,value);
    }
    private void initGson(){
        if(gson==null) {
            //用于处理post参数中int转为字符串后转回来时候变为double的问题
            this.gson = new GsonBuilder()
                    .registerTypeAdapter(
                            new TypeToken<HashMap<String, Object>>() {
                            }.getType(),
                            new JsonDeserializer<HashMap<String, Object>>() {
                                @Override
                                public HashMap<String, Object> deserialize(
                                        JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context) throws JsonParseException {

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    JsonObject jsonObject = json.getAsJsonObject();
                                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                                        hashMap.put(entry.getKey(), entry.getValue());
                                    }
                                    return hashMap;
                                }
                            }).create();
        }
    }
}
