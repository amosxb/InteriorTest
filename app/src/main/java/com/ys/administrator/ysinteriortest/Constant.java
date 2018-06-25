package com.ys.administrator.ysinteriortest;

import com.eaglesoul.eplatform.common.link.PublishType;

/**
 * description: 常量管理类
 * Created by amos on 2016/5/20.
 */
public class Constant {

    public static PublishType sPublishType;

    //基础url
    public static String BASE_URL = "";

    //下载apk链接地址
    public static String DOWNLOAD_APK_URL = "";

    //获取服务器列表的接口地址，该地址是唯一不变的
    public static final String BASE_SERVICE_LIST_URL = "http://qa.ys100.com:90/api_v3/rest/v3/";

    public static final String MQTT_HOST = "qamq.ys100.com";

    public static final int MQTT_PORT = 1883;

    public static final String SEED = "YS100@#eaglesoul";

    public static final String MQTT_SWITCH_SERVER_TOPIC = "t/testtools";

    public static  String MQTT_USER;

    public static  String MQTT_PASSWORD;





    //向个人中心推送指令
    public static final String COMMAND_WIFI = "wifi";				//连接WiFi指令
    public static final String COMMAND_CLEARLOGIN = "clearlogin";	//清除数据库信息（测试机个人中心数据）
    public static final String COMMAND_SHUTDOWN = "shutdown";		//关机
    public static final String APK_LOAD = "loadapk";		//下载apk
    public static final String COMMAND_SWITCH_SERVER = "switch_server";
    public static final String COMMAND_CLOSE_TEST = "controlled_off";



    /**
     * 网络切换
     * @param publishType 该对象包含服务器的所有信息
     * */
    public static void netSwitch(PublishType publishType)
    {
        sPublishType = publishType;

        //推送用户名
        MQTT_USER = sPublishType.getMqttUser();

        //推送用户密码
        MQTT_PASSWORD = sPublishType.getMqttPassword();

        //url
        BASE_URL = publishType.getBaseUrl() + "v3/";

        DOWNLOAD_APK_URL = BASE_URL + "apk_version/download_app";
    }
}
