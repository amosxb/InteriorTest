package com.administrator.ysinteriortest.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaobin on 2017/7/25.
 */

public class StudentInfoBean {


    /**
     * retCode : SUCCESS
     * data : [{"stu_id":"stu888","password":"123123","stu_name":"Gunn"},{"stu_id":"studentlaixiaoyang","password":"111111","stu_name":"laixiaoyang"},{"stu_id":"studentlaixiaoyang04","password":"111111","stu_name":"lxy"},{"stu_id":"studentlaixiaoyang05","password":"111111","stu_name":"uyghjj"},{"stu_id":"studentlaixiaoyang06","password":"111111","stu_name":"shjhh"},{"stu_id":"studenttest000008","password":"123123","stu_name":"Jerry"},{"stu_id":"studtext01","password":"111111","stu_name":"fghjj"}]
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
         * stu_id : stu888
         * password : 123123
         * stu_name : Gunn
         */

        private String stu_id;
        private String password;
        private String stu_name;
        private boolean is_login;
        private String mac_add;
        private String ip_add;
        private String pad_version;
        private String residual_battery;
        private String ssid;
        private String serial_number;
        private boolean isSelect;

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public boolean getIs_login() {
            return is_login;
        }

        public void setIs_login(boolean is_login) {
            this.is_login = is_login;
        }

        public String getMacAddress() {
            return mac_add;
        }

        public void setMacAddress(String macAddress) {
            this.mac_add = macAddress;
        }

        public String getIpAddress() {
            return ip_add;
        }

        public void setIpAddress(String ipAddress) {
            this.ip_add = ipAddress;
        }

        public String getPadVersion() {
            return pad_version;
        }

        public void setPadVersion(String padVersion) {
            this.pad_version = padVersion;
        }

        public String getResidualBattery() {
            return residual_battery;
        }

        public void setResidualBattery(String residualBattery) {
            this.residual_battery = residualBattery;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getStu_id() {
            return stu_id;
        }

        public void setStu_id(String stu_id) {
            this.stu_id = stu_id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
