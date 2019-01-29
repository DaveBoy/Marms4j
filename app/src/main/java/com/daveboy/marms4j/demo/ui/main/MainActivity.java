package com.daveboy.marms4j.demo.ui.main;

import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.daveboy.marms4j.R;
import com.daveboy.marms4j.demo.ui.main.contract.MainContract;
import com.daveboy.marms4j.demo.ui.main.presenter.MainPresenter;
import com.daveboy.marms4j.frame.mvp.BaseAppCompatActivity;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.txt_main)
    TextView txtMain;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        RecyclerView;
        ListView
        //presenter.login("15023822235","15023822235");
        presenter.getArticleList(1);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onSuccess(String data) {
        txtMain.setText(data);
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

}
