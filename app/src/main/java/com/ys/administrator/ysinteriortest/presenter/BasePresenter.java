package com.ys.administrator.ysinteriortest.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.ys.administrator.ysinteriortest.util.LogXmc;
import com.ys.administrator.ysinteriortest.util.ToastUtil;

/**
 * presenter基类
 * Created by amos on 30/7/16.
 */
public class BasePresenter {

    protected Context mContext;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 对 ACTIVITY 生命周期进行管理
     * @return
     */
    protected ActivityLifecycleProvider getActivityLifecycleProvider() {
        ActivityLifecycleProvider provider = null;
        if (null != mContext && mContext instanceof ActivityLifecycleProvider) {
            provider =  (ActivityLifecycleProvider)mContext;
        }
        return provider;
    }

    /**
     * 错误消息处理
     * @param error
     * */
    protected void doError(Throwable error){
        LogXmc.i("error");
        ToastUtil.showShortToast(mContext, error.getMessage());
    }

    public void doDestroy(){
        this.mContext = null;
    }
}
