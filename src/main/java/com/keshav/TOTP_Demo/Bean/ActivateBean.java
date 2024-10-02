package com.keshav.TOTP_Demo.Bean;

import org.springframework.stereotype.Component;

@Component
public class ActivateBean {

    String userName;
    String secret;
    String msg;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
