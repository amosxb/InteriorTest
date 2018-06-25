package com.ys.administrator.ysinteriortest.presenter;

import android.content.Context;

import com.administrator.ysinteriortest.model.bean.ApkInfoBean;
import com.trello.rxlifecycle.ActivityEvent;
import com.ys.administrator.ysinteriortest.iview.IdownloadapkView;
import com.ys.administrator.ysinteriortest.network.RetrofitHelper;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by amos on 2017/7/28.
 */

public class DownloadApkPresenter extends BasePresenter{

    private IdownloadapkView idownloadapkView;


    public DownloadApkPresenter(Context mContext ,IdownloadapkView idownloadapkView) {
        super(mContext);
        this.idownloadapkView = idownloadapkView;
    }

    /**
     * 下载apk方法
     * @param option 参数集合
     * */
    public void getApkList(Map<String,String> option){

        idownloadapkView.loading();

        RetrofitHelper.getApkList().getNewApkList(option)
                .compose(getActivityLifecycleProvider().<ApkInfoBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApkInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        idownloadapkView.cancelLoading();
                        doError(e);
                    }

                    @Override
                    public void onNext(ApkInfoBean apkInfoBean) {
                        idownloadapkView.cancelLoading();
                        idownloadapkView.loginResult(apkInfoBean);
                    }
                });

    }
}
