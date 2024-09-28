package com.keshav.TOTP_Demo.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "activates")
public class TotpActivateJPA {
    @Id
    String userName;
    String datetime;
    String secret;
    String otp;
    String password;

    @Override
    public String toString() {
        return "TotpActivateJPA{" +
                "userName='" + userName + '\'' +
                ", datetime='" + datetime + '\'' +
                ", secret='" + secret + '\'' +
                ", otp='" + otp + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TotpActivateJPA(String userName, String datetime, String secret, String otp, String password) {
        this.userName = userName;
        this.datetime = datetime;
        this.secret = secret;
        this.otp = otp;
        this.password = password;
    }
}
