package com.keshav.TOTP_Demo.Controller;

import com.keshav.TOTP_Demo.Bean.UserBean;
import com.keshav.TOTP_Demo.Service.ActivateOtpService;
import com.keshav.TOTP_Demo.Service.GenerateOtpService;
import com.keshav.TOTP_Demo.Service.UserService;
import com.keshav.TOTP_Demo.Service.ValidateOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TotpController {

    @Autowired
    UserService userService;
    @Autowired
    ActivateOtpService activate;
    @Autowired
    GenerateOtpService generate;
    @Autowired
    ValidateOtpService validate;


    @PostMapping("/usersInit")
    public ResponseEntity<Object> users(@RequestBody UserBean userBean){

        return new ResponseEntity<>(userService.userInit(userBean), HttpStatus.OK);
    }

    @PostMapping("/activateOtp")
    public ResponseEntity<Object> activateOtp(@RequestBody UserBean userBean){

        return new ResponseEntity<>(activate.activate(userBean), HttpStatus.OK);
    }

    @PostMapping("/generateOtp")
    public ResponseEntity<Object> generateOtp(@RequestBody UserBean userBean){
        return new ResponseEntity<>(generate.generate(userBean), HttpStatus.OK);
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<Object> validateOtp(@RequestBody UserBean userBean){
        return new ResponseEntity<>(validate.validate(userBean), HttpStatus.OK);
    }

}
