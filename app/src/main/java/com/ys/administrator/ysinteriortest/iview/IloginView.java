package com.ys.administrator.ysinteriortest.iview;

/**
 * 登录
 * Created by amos on 2017/7/24.
 */

public interface IloginView<T> extends BaseView{

    /**
     * 登录接口返回的数据
     * */
    void loginResult(T t);
}
