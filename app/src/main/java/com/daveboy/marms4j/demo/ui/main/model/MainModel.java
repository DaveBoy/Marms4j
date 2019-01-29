package com.daveboy.marms4j.demo.ui.main.model;

import com.daveboy.marms4j.demo.ApiService;
import com.daveboy.marms4j.demo.model.Article;
import com.daveboy.marms4j.demo.model.Login;
import com.daveboy.marms4j.demo.model.PageData;
import com.daveboy.marms4j.demo.ui.main.contract.MainContract;
import com.daveboy.marms4j.frame.mvp.BaseModel;
import com.daveboy.marms4j.frame.network.CustomObsever;
import com.daveboy.marms4j.frame.network.ResponseTransformer;
import com.daveboy.marms4j.frame.network.RetrofitManager;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

public class MainModel extends BaseModel implements MainContract.Model {

    @Override
    public void login(String userName, String passWord, CustomObsever<Login> customObsever, LifecycleTransformer lifecycleTransformer) {
        RetrofitManager.getInstance().getRequest(ApiService.class)
                .login(userName)
                .compose(ResponseTransformer.<Login>handleResult(lifecycleTransformer))
                .subscribe(customObsever);
    }

    @Override
    public void getArticleList(int index, CustomObsever<PageData<Article>> customObsever, LifecycleTransformer lifecycleTransformer) {
        RetrofitManager.getInstance().getRequest(ApiService.class)
                .getArticleList(index)
                .compose(ResponseTransformer.<PageData<Article>>handleResult(lifecycleTransformer))
                .subscribe(customObsever);
    }
}
