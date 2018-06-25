package com.ys.administrator.ysinteriortest.ui.sharePre;

/**
 * 轻量级存储器
 * Created by amos on 2017/7/25.
 */

public class SharePrefUtils {

    //用户token
    public static final String TOKEN_KEY = "token";
    //登录用户名
    public static final String ACCOUNT_KEY = "userName";
    //密码
    public static final String PASSWORD_KEY = "password";

    public static final String CHECK_KEY = "isCheck";

    /**
     * 保存选中状态
     * */
    public static void saveCheck(boolean isCheck){
        SharePreferenceHelper.getInstance().setBooleanValue(CHECK_KEY, isCheck);
    }

    /**
     * 获取保存的状态
     * */
    public static boolean getCheck(){
        return SharePreferenceHelper.getInstance().getBooleanValue(CHECK_KEY);
    }

    /**
     * 保存登录用户名
     * */
    public static void saveUserName(String username)
    {
        SharePreferenceHelper.getInstance().setStringValue(ACCOUNT_KEY, username);
    }

    public static String getUserName()
    {
        return SharePreferenceHelper.getInstance().getStringValue(ACCOUNT_KEY);
    }

    /**
     * 保存密码信息
     * */
    public static void savePassword(String password)
    {
        SharePreferenceHelper.getInstance().setStringValue(PASSWORD_KEY, password);
    }

    public static String getPassword()
    {
        return SharePreferenceHelper.getInstance().getStringValue(PASSWORD_KEY);
    }

    /**
     * 保存token信息
     * */
    public static void saveToken(String token)
    {
        SharePreferenceHelper.getInstance().setStringValue(TOKEN_KEY , token);
    }

    public static String getToken()
    {
        return SharePreferenceHelper.getInstance().getStringValue(TOKEN_KEY);
    }
}
