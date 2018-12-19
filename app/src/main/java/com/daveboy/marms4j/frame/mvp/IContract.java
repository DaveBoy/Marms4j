package com.daveboy.marms4j.frame.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface IContract {
    interface IModel{

    }
    interface IPresenter<V extends IView>{
        void attachView(V view);
        void detachView();
    }
    interface IView{
        /**
         * 显示加载中
         */
        void showLoading();

        /**
         * 隐藏加载
         */
        void hideLoading();

        /**
         * 绑定生命周期，如果不想写实现可以写成<T> LifecycleTransformer<T> bindToLifecycle()
         */
        <T>LifecycleTransformer<T> bindLifecycle();
    }
}
