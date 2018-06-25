package com.ys.administrator.ysinteriortest.ui.event;

/**
 * 下载apk eventbus传递类
 * Created by amos on 2017/8/22.
 */

public class LoadApkEvent {

    private String school_id;

    public  LoadApkEvent(String school_id){
        this.school_id = school_id;
    }

    public String getSchoolId(){
        return school_id;
    }
}
