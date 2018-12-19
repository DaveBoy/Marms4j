package com.daveboy.marms4j.demo.ui.main.contract;

import com.daveboy.marms4j.demo.model.Article;
import com.daveboy.marms4j.demo.model.Login;
import com.daveboy.marms4j.demo.model.PageData;
import com.daveboy.marms4j.frame.mvp.IContract;
import com.daveboy.marms4j.frame.network.CustomObsever;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 *@description 主页Contract
 *@author mxm
 *@creatTime 2018/12/18  18:35
 */
public interface MainContract {
    interface Model extends IContract.IModel {
        void login(String userName, String passWord, CustomObsever<Login> customObsever, LifecycleTransformer lifecycleTransformer);

        void getArticleList(int index, CustomObsever<PageData<Article>> customObsever, LifecycleTransformer lifecycleTransformer);
    }

    interface View extends IContract.IView {
        void onSuccess(String data);
    }

    interface Presenter extends IContract.IPresenter<View> {
        /**登录
         * @param userName
         * @param passWord
         * @return
         */
        void login(String userName,String passWord);

        void getArticleList(int index);
    }
}
