package com.keshav.TOTP_Demo.Service;

import com.keshav.TOTP_Demo.Constraints.TotpConstraints;
import com.keshav.TOTP_Demo.Model.TotpUsersJPA;
import com.keshav.TOTP_Demo.Bean.UserBean;
import com.keshav.TOTP_Demo.Bean.UserInitResponseBean;
import com.keshav.TOTP_Demo.Repo.TotpUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {


    @Autowired
    TotpUsersRepo totpUsersRepo;

    @Autowired
    UserInitResponseBean userInitResponseBean;


    public Object userInit(UserBean userBean) {
        LocalDate localDate = LocalDate.now();
        userBean.setDatetime(localDate.toString());
        userBean.setUserStatus(TotpConstraints.ACTIVATE);
        TotpUsersJPA totpUsersJPA = new TotpUsersJPA();
        String user = userBean.getUserName();
        totpUsersJPA.setUserName(user);
        totpUsersJPA.setPassword(userBean.getPassword());
        totpUsersJPA.setDate(userBean.getDatetime());
        totpUsersJPA.setUserStatus(userBean.getUserStatus());
        if (totpUsersRepo.findById(user).isEmpty()) {
            totpUsersRepo.save(totpUsersJPA);


            if (totpUsersRepo.findById(user).isPresent()) {
                userInitResponseBean.setUserName(user);
                userInitResponseBean.setMessage(TotpConstraints.USER_ADDED);
                userInitResponseBean.setStatus(TotpConstraints.SUCCESS);
                return userInitResponseBean;
            } else {
                userInitResponseBean.setUserName(user);
                userInitResponseBean.setMessage(TotpConstraints.USER_NOT_ADDED);
                userInitResponseBean.setStatus(TotpConstraints.FAILED);
                return userInitResponseBean;
            }
        }else {
            if(totpUsersRepo.findById(user).get().getUserStatus().equals(TotpConstraints.LOCK)){
                totpUsersRepo.save(totpUsersJPA);
                userInitResponseBean.setUserName(user);
                userInitResponseBean.setMessage(TotpConstraints.UNLOCKED);
                userInitResponseBean.setStatus(TotpConstraints.SUCCESS);

            }else {
                userInitResponseBean.setUserName(user);
                userInitResponseBean.setMessage(TotpConstraints.NOT_UNLOCKED);
                userInitResponseBean.setStatus(TotpConstraints.FAILED);
            }
            return userInitResponseBean;
        }

    }

}
