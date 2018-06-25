package com.ys.administrator.ysinteriortest.ui.event;

/**
 * eventBus消息传递类
 * Created by amos on 2017/7/31.
 */

public class DownloadApkEvent {

    private String app_name;
    private String version;
    private String file_path;

    public DownloadApkEvent(String app_name, String version ,String file_path){
        this.app_name = app_name;
        this.version = version;
        this.file_path = file_path;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
