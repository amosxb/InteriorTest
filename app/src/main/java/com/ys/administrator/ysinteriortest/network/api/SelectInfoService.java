package com.ys.administrator.ysinteriortest.network.api;

import com.administrator.ysinteriortest.model.bean.SchoolInfoBean;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 学校列表接口
 * Created by amos on 2017/7/25.
 */

public interface SelectInfoService {

    @POST("test/autologin/school_info")
    Observable<SchoolInfoBean> getSelectInfo(@QueryMap Map<String,String> option);
}
