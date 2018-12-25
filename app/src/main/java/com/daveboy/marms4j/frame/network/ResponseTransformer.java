package com.daveboy.marms4j.frame.network;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author mxm
 * @description 网络请求结果的统一预处理
 * @creatTime 2018/11/21  14:49
 */
public class ResponseTransformer {
    public static int SUCCESS_CODE=200;
    /**
     * 异常处理、线程切换、结果预处理,这里的context也可以在预处理中或者异常中做toast显示信息
     */
    
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult(final LifecycleTransformer transformer) {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                Observable observable= upstream
                        .onErrorResumeNext(new ErrorResumeFunction<BaseResponse<T>>())
                        .flatMap(new ResponseFunction<T>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
                if(transformer==null){
                    return observable;
                }
                return observable.compose(transformer);
            }
        };
    }
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return handleResult(null);
    }
    /**
     * 绑定生命周期  因为需要context  暂时废弃
     */
    @Deprecated
    private static <T> ObservableSource<T> composeContext(Context context, Observable<T> observable) {
        if(context instanceof RxActivity) {
            return (ObservableSource<T>) observable.compose(((RxActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        } else if(context instanceof RxFragmentActivity){
            return (ObservableSource<T>) observable.compose(((RxFragmentActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        }else if(context instanceof RxAppCompatActivity){
            return (ObservableSource<T>) observable.compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        }else {
            return observable;
        }
    }
    
    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends T>> {

        @Override
        public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     * 这里可以做结果预处理
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> tResponse) {
            int code = tResponse.getCode();
            String message = tResponse.getMsg();
            if (code == SUCCESS_CODE) {
                return Observable.just(tResponse.getData());
            } else {
                //特殊状态码可以在这判断
                return Observable.error(new ApiException(code, message));
            }
        }
    }
}
