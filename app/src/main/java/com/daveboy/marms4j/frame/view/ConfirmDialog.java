package com.daveboy.marms4j.frame.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.daveboy.marms4j.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: Arturia
 * Date: 2018/7/4
 */
public class ConfirmDialog extends BaseDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    private Unbinder unbinder;

    private FragmentManager fragmentManager;
    private OnConfirmClickListener onConfirmClickListener;
    private String title;
    private String content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }

        return view;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_confirm;
    }

    @Override
    public void bindView(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public int getWidth() {
        return ConvertUtils.px2dp(400);
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    public ConfirmDialog setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public ConfirmDialog setOnConfirmClickListenrer(OnConfirmClickListener listenrer) {
        this.onConfirmClickListener = listenrer;
        return this;
    }

    public ConfirmDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ConfirmDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public BaseDialog show() {
        show(fragmentManager);
        return this;
    }

    public interface OnConfirmClickListener {
        void onConfirm();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.onConfirm();
                }
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
