package com.administrator.ysinteriortest.model;

import java.util.List;

/**
 * 服务器列表bean
 * Created by amos on 2017/7/24.
 */

public class ServiceBean {

    /**
     * retCode : SUCCESS
     * data : [{"server_name":"鹰硕(公网)","extnet_baseurl":"http://webapiv3.ys100.com/apiv3/rest/","support_extnet":true,"support_extnet_only":true,"redis_host":"manage.ys100.com","redis_port":"6379","mqtt_host":"webapi.ys100.com","mqtt_port":"1883","disp_no":1},{"server_name":"鹰硕(QA)","extnet_baseurl":"http://qa.ys100.com:90/api_v3/rest/","support_extnet":true,"support_extnet_only":true,"redis_host":"qa.ys100.com","redis_port":"6379","mqtt_host":"qa.ys100.com","mqtt_port":"1883","disp_no":2},{"server_name":"鹰硕(电信云公网)","pkg_tail":"_dx_yun_v3_p","extnet_baseurl":"http://58.61.143.213:90/apiv3/rest/","support_extnet":true,"support_extnet_only":true,"redis_host":"58.61.143.213","redis_port":"6379","mqtt_host":"58.61.143.213","mqtt_port":"1883","disp_no":3}]
     */

    private String retCode;
    private List<DataBean> data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * server_name : 鹰硕(公网)
         * extnet_baseurl : http://webapiv3.ys100.com/apiv3/rest/
         * support_extnet : true
         * support_extnet_only : true
         * redis_host : manage.ys100.com
         * redis_port : 6379
         * mqtt_host : webapi.ys100.com
         * mqtt_port : 1883
         * disp_no : 1
         * pkg_tail : _dx_yun_v3_p
         */

        private String server_name;
        private String extnet_baseurl;
        private String intnet_baseurl;
        private boolean support_extnet;
        private boolean support_extnet_only;
        private String redis_host;
        private String redis_port;
        private String mqtt_host;
        private String mqtt_port;
        private int disp_no;
        private String pkg_tail;

        public String getIntnet_baseurl() {
            return intnet_baseurl;
        }

        public void setIntnet_baseurl(String intnet_baseurl) {
            this.intnet_baseurl = intnet_baseurl;
        }

        public String getServer_name() {
            return server_name;
        }

        public void setServer_name(String server_name) {
            this.server_name = server_name;
        }

        public String getExtnet_baseurl() {
            return extnet_baseurl;
        }

        public void setExtnet_baseurl(String extnet_baseurl) {
            this.extnet_baseurl = extnet_baseurl;
        }

        public boolean isSupport_extnet() {
            return support_extnet;
        }

        public void setSupport_extnet(boolean support_extnet) {
            this.support_extnet = support_extnet;
        }

        public boolean isSupport_extnet_only() {
            return support_extnet_only;
        }

        public void setSupport_extnet_only(boolean support_extnet_only) {
            this.support_extnet_only = support_extnet_only;
        }

        public String getRedis_host() {
            return redis_host;
        }

        public void setRedis_host(String redis_host) {
            this.redis_host = redis_host;
        }

        public String getRedis_port() {
            return redis_port;
        }

        public void setRedis_port(String redis_port) {
            this.redis_port = redis_port;
        }

        public String getMqtt_host() {
            return mqtt_host;
        }

        public void setMqtt_host(String mqtt_host) {
            this.mqtt_host = mqtt_host;
        }

        public String getMqtt_port() {
            return mqtt_port;
        }

        public void setMqtt_port(String mqtt_port) {
            this.mqtt_port = mqtt_port;
        }

        public int getDisp_no() {
            return disp_no;
        }

        public void setDisp_no(int disp_no) {
            this.disp_no = disp_no;
        }

        public String getPkg_tail() {
            return pkg_tail;
        }

        public void setPkg_tail(String pkg_tail) {
            this.pkg_tail = pkg_tail;
        }
    }
}
