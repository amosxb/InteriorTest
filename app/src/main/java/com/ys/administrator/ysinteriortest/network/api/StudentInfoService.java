package com.ys.administrator.ysinteriortest.network.api;

import com.administrator.ysinteriortest.model.bean.StudentAddInfoBean;
import com.administrator.ysinteriortest.model.bean.StudentInfoBean;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 学生列表接口
 * Created by amos on 2017/7/25.
 */

public interface StudentInfoService {

    /**
     * 学生列表
     * */
    @POST("test/autologin/student_list")
    Observable<StudentInfoBean> getStudentInfo(@QueryMap Map<String,String> option);

    /**
     * 清除服务器数据
     * */
    @POST("test/autologin/clear_stu_info")
    Observable<StudentInfoBean> clearStudentInfo(@QueryMap Map<String,String> option);

    /**
     *一键登录
     * */
    @POST("test/autologin/stu_add_info")
    Observable<StudentAddInfoBean> getStudentPanelInfo(@QueryMap Map<String ,String> option);
}
