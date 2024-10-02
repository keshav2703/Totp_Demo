package com.keshav.TOTP_Demo.Bean;

import org.springframework.stereotype.Component;

@Component
public class ValidateOtpBean {
    Boolean Auth;
    String userName;
    String msg;
    public Boolean getAuth() {
        return Auth;
    }

    public void setAuth(Boolean auth) {
        Auth = auth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}