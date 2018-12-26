package com.daveboy.marms4j.frame.network;

import android.widget.Toast;


import com.daveboy.marms4j.frame.base.BaseApplication;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class CustomObsever<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        if(!NetworkUtil.isNetworkAvailable(BaseApplication.getInstance())){
            d.dispose();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccessful(t);
    }

    @Override
    public void onError(Throwable t) {
        Logger.i("onError: %s", t == null  ? "": t.toString());
        if (t instanceof ApiException) {
            Toast.makeText(BaseApplication.getInstance(), ((ApiException) t).getDisplayMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BaseApplication.getInstance(), t == null ? "网络错误" : t.toString(), Toast.LENGTH_SHORT).show();
        }
        onEnd();
    }

    @Override
    public void onComplete() {
        onEnd();
    }

    public void onEnd() {
    }

    protected abstract void onSuccessful(T data);
}
