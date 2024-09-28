package com.keshav.TOTP_Demo.Service;

import com.keshav.TOTP_Demo.Model.TotpUsersJPA;
import com.keshav.TOTP_Demo.Model.UserBean;
import com.keshav.TOTP_Demo.Model.UserInitBean;
import com.keshav.TOTP_Demo.Repo.TotpUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {


    @Autowired
    TotpUsersRepo totpUsersRepo;
    @Autowired
    TotpUsersJPA totpUsersJPA;
    @Autowired
    UserInitBean userInitBean;


    public Object userInit(UserBean userBean) {
        LocalDate localDate = LocalDate.now();
        userBean.setDatetime(localDate.toString());
        userBean.setUserStatus("Active");
        totpUsersJPA.setUserName(userBean.getUserName());
        totpUsersJPA.setPassword(userBean.getPassword());
        totpUsersJPA.setDate(userBean.getDatetime());
        totpUsersJPA.setUserStatus(userBean.getUserStatus());
        totpUsersRepo.save(totpUsersJPA);

        String user = userBean.getUserName();
        if (totpUsersRepo.findById(user).isPresent()){
            userInitBean.setUserName(user);
            userInitBean.setMessage("User Added to Central DB");
            userInitBean.setStatus("Success");
            return userBean;
        }else {
            userInitBean.setUserName(user);
            userInitBean.setMessage("User Not Able to Add Central DB");
            userInitBean.setStatus("Failed");
            return user;
        }

    }

}
