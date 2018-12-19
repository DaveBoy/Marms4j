package com.daveboy.marms4j.frame.base;

import android.support.annotation.LayoutRes;

public interface IView {
    void initView();
    void initData();
    @LayoutRes
    int getLayoutResId();
}
