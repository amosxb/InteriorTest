package com.ys.administrator.ysinteriortest.presenter;

import android.content.Context;

import com.administrator.ysinteriortest.model.bean.StudentAddInfoBean;
import com.administrator.ysinteriortest.model.bean.StudentInfoBean;
import com.trello.rxlifecycle.ActivityEvent;
import com.ys.administrator.ysinteriortest.iview.IstudentInfoView;
import com.ys.administrator.ysinteriortest.network.RetrofitHelper;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 学生列表
 * Created by amos on 2017/7/25.
 */

public class StudentInfoPresenter extends BasePresenter{



    private IstudentInfoView istudentInfoView;

    public StudentInfoPresenter(Context mContext ,IstudentInfoView istudentInfoView)
    {
        super(mContext);
        this.istudentInfoView = istudentInfoView;
    }

    public void getStudentInfo(Map<String,String> option)
    {
        istudentInfoView.loading();

        RetrofitHelper.getStudentInfo().getStudentInfo(option)
                .compose(getActivityLifecycleProvider().<StudentInfoBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StudentInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        istudentInfoView.cancelLoading();
                        doError(e);
                    }

                    @Override
                    public void onNext(StudentInfoBean studentInfoBean) {
                        istudentInfoView.cancelLoading();
                        istudentInfoView.loginResult(studentInfoBean);
                    }
                });
    }

    public void getStudentPanelInfo(Map<String,String> option){

        istudentInfoView.loading();

        RetrofitHelper.getStudentInfo().getStudentPanelInfo(option)
                .compose(getActivityLifecycleProvider().<StudentAddInfoBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StudentAddInfoBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        istudentInfoView.cancelLoading();
                        doError(e);
                    }

                    @Override
                    public void onNext(StudentAddInfoBean studAddInfo) {
                        istudentInfoView.cancelLoading();
                        istudentInfoView.loginResult(studAddInfo);
                    }
                });
    }

    public void clearStudentInfo(Map<String,String> option)
    {
        istudentInfoView.loading();

        RetrofitHelper.getStudentInfo().clearStudentInfo(option)
                .compose(getActivityLifecycleProvider().<StudentInfoBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StudentInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        istudentInfoView.cancelLoading();
                        doError(e);
                    }

                    @Override
                    public void onNext(StudentInfoBean studentinfoBean) {
                        istudentInfoView.cancelLoading();
                        istudentInfoView.loginResult(studentinfoBean);
                    }
                });
    }
}
