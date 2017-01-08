package com.lwx.study.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description ：
 * project name：Study
 * author : Vincent
 * creation date: 2017/1/8 22:44
 *
 * @version 1.0
 */
public class LoginResultEntity implements Serializable {


    /**
     * status : SUCCESS
     * data : [{"accesstoken":"d8ca0ff9d4295dc4c8c4d025267407fca70fa00c3847f6a7d12a4f3b","role":1}]
     */

    private String status;
    private String msg;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * accesstoken : d8ca0ff9d4295dc4c8c4d025267407fca70fa00c3847f6a7d12a4f3b
         * role : 1
         */

        private String accesstoken;
        private int role;

        public String getAccesstoken() {
            return accesstoken;
        }

        public void setAccesstoken(String accesstoken) {
            this.accesstoken = accesstoken;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }
    }
}
