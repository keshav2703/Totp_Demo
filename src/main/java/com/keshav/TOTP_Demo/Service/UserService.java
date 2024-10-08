package com.keshav.TOTP_Demo.Service;

import com.keshav.TOTP_Demo.Constraints.TotpConstraints;
import com.keshav.TOTP_Demo.Model.TotpUsersDAO;
import com.keshav.TOTP_Demo.Bean.UserBean;
import com.keshav.TOTP_Demo.Bean.UserInitResponseBean;
import com.keshav.TOTP_Demo.Repo.TotpUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService {


    @Autowired
    TotpUsersRepo totpUsersRepo;

    @Autowired
    UserInitResponseBean userInitResponseBean;


    public Object userInit(UserBean userBean) {
        LocalDateTime localDate = LocalDateTime.now();
        userBean.setDatetime(localDate.toString());
        userBean.setUserStatus(TotpConstraints.ACTIVATE);
        TotpUsersDAO totpUsersDAO = new TotpUsersDAO();
        String user = userBean.getUserName();
        totpUsersDAO.setUserName(user);
        totpUsersDAO.setPassword(userBean.getPassword());
        totpUsersDAO.setDate(userBean.getDatetime());
        totpUsersDAO.setUserStatus(userBean.getUserStatus());
        if (totpUsersRepo.findById(user).isEmpty()) {
            totpUsersRepo.save(totpUsersDAO);


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
                totpUsersRepo.save(totpUsersDAO);
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
