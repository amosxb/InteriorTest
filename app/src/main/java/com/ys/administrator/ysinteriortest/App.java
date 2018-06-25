package com.ys.administrator.ysinteriortest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.administrator.ysinteriortest.model.bean.TestCommandBean;
import com.google.gson.Gson;
import com.ys.administrator.ysinteriortest.service.MqttClientService;
import com.ys.administrator.ysinteriortest.util.LogXmc;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 小工具
 * Created by amos on 2016/5/20.
 */
public class App extends Application {

    public static App mApp;
    private String token;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){ return token;}

    /**
     * 获取app在应用中的Context
     */
    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    public static App getInstance() {
        return mApp;
    }


    /**
     * 初始化信息操作
     */
    private void init() {
        if (null == mApp) {
            mApp = this;
        }
        LogXmc.enableDebug(true);
        LogXmc.setLogLevel(Log.VERBOSE);
    }
}
