package com.lwx.study.bean;

import java.io.Serializable;

/**
 * description ：
 * project name：Study
 * author : Vincent
 * creation date: 2017/1/8 23:22
 *
 * @version 1.0
 */
public class Result2<T> implements Serializable{


    /**
     * status : FAIL
     * error_code : 20003
     * error_msg : 账号不存在
     */

    private String status;
    private String error_code;
    private String error_msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
