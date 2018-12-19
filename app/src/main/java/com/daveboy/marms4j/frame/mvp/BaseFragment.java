package com.daveboy.marms4j.frame.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daveboy.marms4j.frame.base.IActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *@description activity基类
 *@author mxm
 *@creatTime 2018/11/22  14:18
 */
public abstract class BaseFragment<P extends IContract.IPresenter> extends RxFragment implements IContract.IView,IActivity {
    public View rootView;
    private Unbinder mUnbinder;
    protected P presenter;
    private Unbinder unbinder;

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResId(), container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        if (presenter != null) {
            presenter.attachView(this);
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
