package com.ys.administrator.ysinteriortest.iview;

import com.administrator.ysinteriortest.model.bean.SchoolInfoBean;

/**
 * 学校列表
 * Created by amos on 2017/7/25.
 */

public interface IschoolInfoView extends BaseView{

    /**
     * 接口返回的数据
     * */
    void loginResult(SchoolInfoBean schoolInfoBean);
}
