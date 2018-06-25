package com.ys.administrator.ysinteriortest.presenter;

import android.content.Context;

import com.administrator.ysinteriortest.model.LoginResult;
import com.administrator.ysinteriortest.model.ServiceBean;
import com.trello.rxlifecycle.ActivityEvent;
import com.ys.administrator.ysinteriortest.iview.IloginView;
import com.ys.administrator.ysinteriortest.network.RetrofitHelper;
import com.ys.administrator.ysinteriortest.util.LogXmc;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登录
 * Created by amos on 2017/7/24.
 */

public class LoginPresenter extends BasePresenter {

    private IloginView<Object> mIloginView;

    public LoginPresenter(Context mContext,IloginView<Object> iloginView) {
        super(mContext);

        this.mIloginView = iloginView;
    }

    /**
     * 获取登录返回的数据
     * @param option 参数集合
     * */
    public void getLoginResult(Map<String,String> option)
    {
        mIloginView.loading();

        RetrofitHelper.getLoginApi()
                .getLoginInfo(option)
                .compose(getActivityLifecycleProvider().<LoginResult>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        LogXmc.i("onError");
                        mIloginView.cancelLoading();
                        doError(e);
                    }

                    @Override
                    public void onNext(LoginResult loginResult)
                    {
                        mIloginView.cancelLoading();
                        mIloginView.loginResult(loginResult);
                    }
                });
    }

    /**
     * 获取服务器列表
     * */
    public void getService(){
        RetrofitHelper.getServiceApi().getService()
                .compose(getActivityLifecycleProvider().<ServiceBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogXmc.i("onError");
                        mIloginView.cancelLoading();
                        doError(e);
                    }

                    @Override
                    public void onNext(ServiceBean serviceBean) {
                        mIloginView.cancelLoading();
                        mIloginView.loginResult(serviceBean);
                    }
                });
    }
}
