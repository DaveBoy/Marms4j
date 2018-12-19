package com.daveboy.marms4j.demo.ui.main.presenter;

import com.daveboy.marms4j.demo.model.Article;
import com.daveboy.marms4j.demo.model.Login;
import com.daveboy.marms4j.demo.model.PageData;
import com.daveboy.marms4j.demo.ui.main.contract.MainContract;
import com.daveboy.marms4j.demo.ui.main.model.MainModel;
import com.daveboy.marms4j.frame.mvp.BasePresenter;
import com.daveboy.marms4j.frame.network.CustomObsever;

public class MainPresenter extends BasePresenter<MainContract.View,MainContract.Model> implements MainContract.Presenter {
    @Override
    protected MainContract.Model getModel() {
        return new MainModel();
    }

    @Override
    public void login(String userName, String passWord) {
        view.showLoading();
        model.login(userName,passWord, new CustomObsever<Login>() {
            @Override
            protected void onSuccessful(Login data) {
                view.onSuccess(data.toString());
            }

            @Override
            public void onEnd() {
                view.hideLoading();
            }
        },lifecycleTransformer);
    }

    @Override
    public void getArticleList(int index) {
        view.showLoading();
        model.getArticleList(index, new CustomObsever<PageData<Article>>() {


            @Override
            public void onEnd() {
                view.hideLoading();
            }

            @Override
            protected void onSuccessful(PageData<Article> data) {
                view.onSuccess(data.toString());
            }
        },lifecycleTransformer);
    }
}
