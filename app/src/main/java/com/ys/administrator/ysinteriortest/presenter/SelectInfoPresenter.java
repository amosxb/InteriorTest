package com.ys.administrator.ysinteriortest.presenter;

import android.content.Context;

import com.administrator.ysinteriortest.model.bean.SchoolInfoBean;
import com.trello.rxlifecycle.ActivityEvent;
import com.ys.administrator.ysinteriortest.iview.IschoolInfoView;
import com.ys.administrator.ysinteriortest.network.RetrofitHelper;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 学校列表
 * Created by amos on 2017/7/25.
 */

public class SelectInfoPresenter extends BasePresenter{

    private IschoolInfoView ischoolInfoView;


    public SelectInfoPresenter(Context mContext , IschoolInfoView ischoolInfoView) {
        super(mContext);
        this.ischoolInfoView = ischoolInfoView;
    }

    /**
     * 获取学校列表
     * @param option 参数集
     * */
    public void getSchoolInfo(Map<String,String> option){

        ischoolInfoView.loading();

        RetrofitHelper.getSchoolInfoApi().getSelectInfo(option)
                .compose(getActivityLifecycleProvider().<SchoolInfoBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SchoolInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ischoolInfoView.cancelLoading();
                        doError(e);
                    }

                    @Override
                    public void onNext(SchoolInfoBean schoolInfoBean) {
                        ischoolInfoView.cancelLoading();
                        ischoolInfoView.loginResult(schoolInfoBean);
                    }
                });
    }
}


