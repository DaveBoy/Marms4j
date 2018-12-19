package com.daveboy.marms4j.frame.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

/**
 *@description activity生命周期回调
 *@author mxm
 *@creatTime 2018/11/22  15:11
 */
public class ActivityLifeCycle implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.i(activity.getClass().getSimpleName()+" onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Logger.i(activity.getClass().getSimpleName()+" onActivityDestroyed");

    }
}
