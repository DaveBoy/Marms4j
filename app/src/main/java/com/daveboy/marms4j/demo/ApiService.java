package com.daveboy.marms4j.demo;

import com.daveboy.marms4j.demo.model.Article;
import com.daveboy.marms4j.demo.model.Login;
import com.daveboy.marms4j.demo.model.PageData;
import com.daveboy.marms4j.frame.network.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *@description 接口服务
 *@author mxm
 *@creatTime 2018/11/22  14:03
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseResponse<Login>> login(@Field("username") String username);

    @GET("article/list/{page_index}/json")
    Observable<BaseResponse<PageData<Article>>> getArticleList(@Path("page_index")int pageIndex);
}
