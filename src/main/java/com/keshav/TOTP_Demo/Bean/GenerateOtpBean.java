package com.keshav.TOTP_Demo.Bean;

import org.springframework.stereotype.Component;

@Component
public class GenerateOtpBean {

    String Otp;

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }
}
