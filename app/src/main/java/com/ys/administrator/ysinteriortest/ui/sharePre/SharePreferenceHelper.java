package com.ys.administrator.ysinteriortest.ui.sharePre;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ys.administrator.ysinteriortest.App;

/**
 * sharePreference帮助类
 *
 * @author amos
 */
public class SharePreferenceHelper {
    public static SharePreferenceHelper INSTANCE = null;

    private SharedPreferences preferences;

    private SharePreferenceHelper() {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
    }

    static SharePreferenceHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (SharePreferenceHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SharePreferenceHelper();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 储存String值
     *
     * @param key
     * @param value
     */
    public void setStringValue(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    /**
     * 存储String值
     *
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        return preferences.getString(key, "");
    }

    /**
     * 获取String值
     *
     * @param key
     * @return
     */
    public String getStringValue(String key, String devalue) {
        return preferences.getString(key, devalue);
    }

    /**
     * 储存Boolean值
     *
     * @param key
     * @param value
     */
    public void setBooleanValue(String key, Boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取Boolean值
     *
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key) {
        return preferences.getBoolean(key, false);
    }

    /**
     * 获取Long值
     *
     * @param key
     * @return
     */
    public long getLongValue(String key) {
        return preferences.getLong(key, 0L);
    }

    /**
     * 获取Boolean值
     *
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * @throws
     * @Title: get3gValue
     * @Description: 获取默认值为true的方法
     * @param: @param key
     * @param: @return
     * @return: boolean
     */
    public boolean getBoolValue(String key) {
        return preferences.getBoolean(key, true);
    }

    /**
     * 储存int值
     *
     * @param key
     * @param value
     */
    public void setIntValue(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    /**
     * 获取int值
     *
     * @param key
     * @return
     */
    public int getIntValue(String key) {
        return preferences.getInt(key, 0);
    }

    public int getIntValue(String key, int value) {
        return preferences.getInt(key, value);
    }

    public void remove(String name) {
        preferences.edit().remove(name).apply();
    }

    public void clear() {
        preferences.edit().clear().apply();//记得提交
    }

    public void setLongValue(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public float getFloatValue(String key, float value) {
        return preferences.getFloat(key, value);
    }


}
