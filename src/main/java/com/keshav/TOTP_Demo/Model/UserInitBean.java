package com.keshav.TOTP_Demo.Model;

import org.springframework.stereotype.Component;

@Component
public class UserInitBean {
    String userName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String status;
    String message;
}
