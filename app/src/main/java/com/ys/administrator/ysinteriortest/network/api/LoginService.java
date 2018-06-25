package com.ys.administrator.ysinteriortest.network.api;

import com.administrator.ysinteriortest.model.LoginResult;
import com.administrator.ysinteriortest.model.ServiceBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 获取登录接口
 * Created by amos on 2017/7/24.
 */

public interface LoginService {

    /**
     * 登录接口返回的信息
     * */
    @POST("account/login")
    Observable<LoginResult> getLoginInfo(@QueryMap Map<String,String> option);

    /**
     * 获取服务器列表信息
     * */
    @GET("test/autologin/server_list")
    Observable<ServiceBean> getService();
}
