package com.lwx.study.bean;

/**
 * description ：
 * project name：Network
 * author : Vincent
 * creation date: 2017/1/9 11:18
 *
 * @version 1.0
 */

public class Bean {

    private int num;
    private String msg;

    public Bean(String msg,int num) {
        this.num = num;
        this.msg = msg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
