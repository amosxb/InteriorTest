package com.administrator.ysinteriortest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amos on 2016/5/25.
 * 登录返回结果
 */
public class LoginResult {

    @SerializedName("retCode")
    public String mRetCode;

    @SerializedName("message")
    public String mMessage;

    @SerializedName("token")
    public String mToken;
}
