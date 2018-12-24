package com.daveboy.marms4j.frame.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.InflateException;

import com.daveboy.marms4j.frame.base.IActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *@description activity基类
 *@author mxm
 *@creatTime 2018/11/22  14:18
 */
public abstract class BaseActivity<P extends IContract.IPresenter> extends RxActivity implements IContract.IView,IActivity {

    private Unbinder mUnbinder;
    protected P presenter;
    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mUnbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.attachView(this);
        initPreData();
        initView();
        initData();
    }

    protected abstract P createPresenter();

    /**
     * 处理某些初始化view需要的数据
     */
    protected void initPreData(){}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        if (presenter != null) {
            presenter.detachView();
        }
        EventBus.getDefault().unregister(this);
        this.mUnbinder = null;
        this.presenter = null;
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
