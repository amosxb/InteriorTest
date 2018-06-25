package com.ys.administrator.ysinteriortest.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.administrator.ysinteriortest.model.bean.ApkInfoBean;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.base.BaseActivity;
import com.ys.administrator.ysinteriortest.ui.event.DownloadApkEvent;
import com.ys.administrator.ysinteriortest.iview.IdownloadapkView;
import com.ys.administrator.ysinteriortest.presenter.DownloadApkPresenter;
import com.ys.administrator.ysinteriortest.ui.adapter.LoadAdapter;
import com.ys.administrator.ysinteriortest.ui.event.LoadApkEvent;
import com.ys.administrator.ysinteriortest.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 下载apk类:
 * User: amos
 */
public class LoadApkActivity extends BaseActivity implements IdownloadapkView{

    private DownloadApkPresenter downloadApkPresenter;

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.rv_load)
    RecyclerView rvLoad;

    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;

    private LoadAdapter mLoadAdapter;
    private List<ApkInfoBean.DataBean> mList = new ArrayList<>();
    private String mSchool_id;

    public static void startActivity(Context context)
    {
        context.startActivity(new Intent(context, LoadApkActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }



    @Override
    public void initViews(Bundle savedInstanceState) {
        //注册eventbus
        EventBus.getDefault().register(this);
        downloadApkPresenter = new DownloadApkPresenter(mContext, this);

        mLoadAdapter = new LoadAdapter(LoadApkActivity.this ,mList);
        rvLoad.setAdapter(mLoadAdapter);
        rvLoad.setLayoutManager(new LinearLayoutManager(LoadApkActivity.this));

        //调用接口
        mParam.put("suffix_filter", "apk");
        mParam.put("school_id", mSchool_id);
        downloadApkPresenter.getApkList(mParam);

        initEvent();
    }

    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    public void onMainEvent(LoadApkEvent loadApkEvent){
        if(loadApkEvent != null){
            mSchool_id = loadApkEvent.getSchoolId();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_load_apk;
    }

    @Override
    public void loginResult(ApkInfoBean apkInfoBean) {
        if(apkInfoBean.getRetCode().equals("SUCCESS"))
        {
            if(apkInfoBean.getData() != null && apkInfoBean.getData().size() > 0)
            {
                List<ApkInfoBean.DataBean> apkinfos = apkInfoBean.getData();
                mList.clear();
                mList.addAll(apkinfos);
                //更新数据s
                mLoadAdapter.notifyDataSetChanged();
            }else
            {
                ToastUtil.showShortToast(this, getString(R.string.no_data));
            }

        }else
        {
            ToastUtil.showShortToast(this, getString(R.string.get_the_apk_list_failed));
        }
    }

    @Override
    public void loading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void cancelLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    private void  initEvent(){
        mLoadAdapter.setOnItemClickListener((view, position) -> new AlertDialog.Builder(LoadApkActivity.this).setTitle(R.string.choose_the_apk_download).setMessage(R.string.download_the_APK)
        .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
        .setPositiveButton(R.string.confirm, (dialog, which) -> {
            String name = mList.get(position).getApp_name();
            String version = mList.get(position).getVersion();
            String file_path = mList.get(position).getFile_path();

            EventBus.getDefault().post(new DownloadApkEvent(name , version ,file_path));

            finish();
        }).show());
    }
}
