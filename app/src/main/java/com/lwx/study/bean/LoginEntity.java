package com.lwx.study.bean;

import java.io.Serializable;

/**
 * description ：
 * project name：Study
 * author : Vincent
 * creation date: 2017/1/8 22:43
 *
 * @version 1.0
 */
public class LoginEntity implements Serializable {
    /**
     * current_version : 1.0
     * client_token : AjD0BlcUEQMMJeN32G24VFRMvW7lRmlWkZSsuXUfl6xk
     * client_type : android
     * signature : MTU1MjU1MTE0NDQ6dHJyZmZm

     */

    private String current_version;
    private String client_token;
    private String client_type;
    private String signature;

    public String getCurrent_version() {
        return current_version;
    }

    public void setCurrent_version(String current_version) {
        this.current_version = current_version;
    }

    public String getClient_token() {
        return client_token;
    }

    public void setClient_token(String client_token) {
        this.client_token = client_token;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
