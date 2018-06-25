package com.ys.administrator.ysinteriortest.iview;

import com.administrator.ysinteriortest.model.bean.ApkInfoBean;

/**
 * Created by amos on 2017/7/28.
 */

public interface IdownloadapkView extends BaseView{

    /**
     * 接口返回的数据
     * */
    void loginResult(ApkInfoBean apkInfoBean);
}
