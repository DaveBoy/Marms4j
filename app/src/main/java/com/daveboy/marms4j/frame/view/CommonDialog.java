package com.daveboy.marms4j.frame.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by shaohui on 16/10/11.
 */

public class CommonDialog extends BaseDialog {

    private static final String KEY_LAYOUT_RES = "dialog_layout_res";
    private static final String KEY_HEIGHT = "dialog_height";
    private static final String KEY_WIDTH = "dialog_width";
    private static final String KEY_DIM = "dialog_dim";
    private static final String KEY_GRAVITY = "dialog_gravity";
    private static final String KEY_CANCEL_OUTSIDE = "dialog_cancel_outside";

    private FragmentManager mFragmentManager;

    private boolean mIsCancelOutside = super.getCancelOutside();

    private String mTag = super.getFragmentTag();

    private float mDimAmount = super.getDimAmount();

    private int mHeight = super.getHeight();

    private int mGravity = super.getGravity();

    @LayoutRes
    private int mLayoutRes;

    private ViewListener mViewListener;
    private int width;

    public static CommonDialog create(FragmentManager manager) {
        CommonDialog dialog = new CommonDialog();
        dialog.setFragmentManager(manager);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLayoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES);
            mHeight = savedInstanceState.getInt(KEY_HEIGHT);
            mHeight = savedInstanceState.getInt(KEY_WIDTH);
            mGravity = savedInstanceState.getInt(KEY_GRAVITY);
            mDimAmount = savedInstanceState.getFloat(KEY_DIM);
            mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_RES, mLayoutRes);
        outState.putInt(KEY_HEIGHT, mHeight);
        outState.putInt(KEY_HEIGHT, width);
        outState.putInt(KEY_GRAVITY, mGravity);
        outState.putFloat(KEY_DIM, mDimAmount);
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void bindView(View v) {
        if (mViewListener != null) {
            mViewListener.bindView(v);
        }
    }

    @Override
    public int getLayoutRes() {
        return mLayoutRes;
    }

    public CommonDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    public CommonDialog setViewListener(ViewListener listener) {
        mViewListener = listener;
        return this;
    }

    public CommonDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public CommonDialog setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public CommonDialog setTag(String tag) {
        mTag = tag;
        return this;
    }

    public CommonDialog setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    public CommonDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public CommonDialog setHeight(int heightPx) {
        mHeight = heightPx;
        return this;
    }

    public CommonDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    @Override
    public float getDimAmount() {
        return mDimAmount;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public boolean getCancelOutside() {
        return mIsCancelOutside;
    }

    @Override
    public String getFragmentTag() {
        return mTag;
    }

    @Override
    public int getGravity() {
        return mGravity;
    }

    public interface ViewListener {
        void bindView(View v);
    }

    public BaseDialog show() {
        show(mFragmentManager);
        return this;
    }
}
