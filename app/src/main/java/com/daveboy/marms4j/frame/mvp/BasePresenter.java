package com.daveboy.marms4j.frame.mvp;


import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author mxm
 * @description presenter基类
 * @creatTime 2018/11/22  11:47
 */
public abstract class BasePresenter<V extends IContract.IView, M extends IContract.IModel> implements IContract.IPresenter<V> {
    protected M model;
    protected V view;
    protected LifecycleTransformer lifecycleTransformer;
    /**
     * 初始化model
     */
    protected abstract M getModel();

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    @Override
    public void attachView(V view) {
        this.view = view;
        this.model = getModel();
        lifecycleTransformer=view.bindLifecycle();
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    @Override
    public void detachView() {
        this.view = null;
        this.model = null;
    }

    /**
     * View是否绑定
     */
    public boolean isAttached() {
        return view != null;
    }

}
