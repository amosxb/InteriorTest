package com.ys.administrator.ysinteriortest.ui.event;

/**
 * EventBus发送消息类
 * Created by amos on 2017/8/2.
 */

public class WifiListEvent {

    private String ssid;

    public WifiListEvent(String ssid)
    {
        this.ssid = ssid;

    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
