package com.ys.administrator.ysinteriortest.network.api;

import com.administrator.ysinteriortest.model.bean.ApkInfoBean;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 下载apk列表接口
 * Created by amos on 2017/7/28.
 */

public interface DownloadApkService {

    @POST("apk_version/list_apk")
    Observable<ApkInfoBean> getApkList(@QueryMap Map<String,String> option);

    //修改下载apk接口链接
    @POST("apk_version/get_apk_list")
    Observable<ApkInfoBean> getNewApkList(@QueryMap Map<String,String> option);
}
