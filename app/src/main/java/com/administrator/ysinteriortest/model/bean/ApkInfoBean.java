package com.administrator.ysinteriortest.model.bean;

import java.util.List;

/**
 * Created by amos on 2016/11/7.
 */

public class ApkInfoBean {


    /**
     * retCode : SUCCESS
     * data : [{"app_name":"ysnedut","app_name_zh":"教学平台教师端","app_type":"ysnedut","update_desc":"正式发布","version":"3.3.7170","publish_date":"2017-08-23 16:24:04","file_path":"/ysnedut/ysnedut_3.3.7170_20170823162405_767094.apk","id":67,"page_size":0,"page_number":0,"offset":0,"total_size":0},{"app_name":"ysnedus","app_name_zh":"教学平台学生端","app_type":"ysnedus","update_desc":"正式发布","version":"3.3.1759","publish_date":"2017-08-23 16:24:05","file_path":"/ysnedus/ysnedus_3.3.1759_20170823162406_209926.apk","id":68,"page_size":0,"page_number":0,"offset":0,"total_size":0},{"app_name":"ysperson","app_name_zh":"个人中心","app_type":"ysperson","update_desc":"正式发布","version":"1.0.7007","publish_date":"2017-08-23 16:24:05","file_path":"/ysperson/ysperson_1.0.7007_20170823162407_738878.apk","id":69,"page_size":0,"page_number":0,"offset":0,"total_size":0}]
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
         * app_name : ysnedut
         * app_name_zh : 教学平台教师端
         * app_type : ysnedut
         * update_desc : 正式发布
         * version : 3.3.7170
         * publish_date : 2017-08-23 16:24:04
         * file_path : /ysnedut/ysnedut_3.3.7170_20170823162405_767094.apk
         * id : 67
         * page_size : 0
         * page_number : 0
         * offset : 0
         * total_size : 0
         */

        private String app_name;
        private String app_name_zh;
        private String app_type;
        private String update_desc;
        private String version;
        private String publish_date;
        private String file_path;
        private int id;
        private int page_size;
        private int page_number;
        private int offset;
        private int total_size;

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getApp_name_zh() {
            return app_name_zh;
        }

        public void setApp_name_zh(String app_name_zh) {
            this.app_name_zh = app_name_zh;
        }

        public String getApp_type() {
            return app_type;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public String getUpdate_desc() {
            return update_desc;
        }

        public void setUpdate_desc(String update_desc) {
            this.update_desc = update_desc;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPublish_date() {
            return publish_date;
        }

        public void setPublish_date(String publish_date) {
            this.publish_date = publish_date;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_number() {
            return page_number;
        }

        public void setPage_number(int page_number) {
            this.page_number = page_number;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getTotal_size() {
            return total_size;
        }

        public void setTotal_size(int total_size) {
            this.total_size = total_size;
        }
    }
}
