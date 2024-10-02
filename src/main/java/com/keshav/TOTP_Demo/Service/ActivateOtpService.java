package com.keshav.TOTP_Demo.Service;

import com.keshav.TOTP_Demo.Bean.ActivateBean;
import com.keshav.TOTP_Demo.Bean.UserBean;
import com.keshav.TOTP_Demo.Constraints.TotpConstraints;
import com.keshav.TOTP_Demo.Encryption.Encrypt;
import com.keshav.TOTP_Demo.Model.TotpActivateDAO;
import com.keshav.TOTP_Demo.Model.TotpUsersDAO;
import com.keshav.TOTP_Demo.Repo.TotpActivateRepo;
import com.keshav.TOTP_Demo.Repo.TotpUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;


@Service
public class ActivateOtpService {

    @Autowired
    TotpUsersRepo totpUsersRepo;

    @Autowired
    Encrypt encrypt;

    @Autowired
    TotpActivateRepo totpActivateRepo;

    @Autowired
    ActivateBean activateBean;

    public Object activate(UserBean userBean) {
        String userName = userBean.getUserName();
        String password = userBean.getPassword();
        String deviceId = userBean.getDeviceId();
        if (totpUsersRepo.findById(userName).isPresent()) {
            String status = totpUsersRepo.findById(userName).get().getUserStatus();
            if (!status.equals(TotpConstraints.LOCK)) {

                String p = totpUsersRepo.findById(userName).get().getPassword();
                System.out.println(p + " " + password);
                if (p.equals(password)) {
                    if (totpActivateRepo.findById(userName).isEmpty()) {
                        String secret = encrypt.generateSecret(userName, password, deviceId);
                        String encodedSecret = encrypt.getEncodedSecret(secret, password);
                        TotpActivateDAO totpActivateDAO = new TotpActivateDAO();
                        totpActivateDAO.setOtp(TotpConstraints.DEFAULTOTP);
                        totpActivateDAO.setDatetime(LocalDateTime.now().toString());
                        totpActivateDAO.setUserName(userName);
                        totpActivateDAO.setPassword(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
                        totpActivateDAO.setSecret(encodedSecret);
                        totpActivateRepo.save(totpActivateDAO);
                        activateBean.setMsg(TotpConstraints.ACTIVATED);
                        activateBean.setSecret(secret);
                        activateBean.setUserName(userName);

                        return activateBean;
                    } else {
                        String secret = encrypt.generateSecret(userName, password, deviceId);
                        String encodedSecret = encrypt.getEncodedSecret(secret, password);
                        TotpActivateDAO totpActivateDAO = new TotpActivateDAO();
                        totpActivateDAO.setOtp(TotpConstraints.DEFAULTOTP);
                        totpActivateDAO.setDatetime(LocalDateTime.now().toString());
                        totpActivateDAO.setUserName(userName);
                        totpActivateDAO.setPassword(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
                        totpActivateDAO.setSecret(encodedSecret);
                        totpActivateRepo.save(totpActivateDAO);
                        activateBean.setMsg(TotpConstraints.REACTIVATED);
                        activateBean.setSecret(secret);
                        activateBean.setUserName(userName);

                        return activateBean;
                    }
                } else {
                    if (totpUsersRepo.findById(userName).get().getUserStatus().equals(TotpConstraints.ACTIVATE)) {
                        TotpUsersDAO totpUsersDAO = new TotpUsersDAO();
                        totpUsersDAO.setUserName(userName);
                        totpUsersDAO.setPassword(totpUsersRepo.findById(userName).get().getPassword());
                        totpUsersDAO.setDate(totpUsersRepo.findById(userName).get().getDate());
                        totpUsersDAO.setUserStatus("Attempt1");
                        totpUsersRepo.save(totpUsersDAO);
                        activateBean.setMsg(TotpConstraints.InvalidAttempt);
                        activateBean.setUserName(userName);

                        return activateBean;
                    } else if (totpUsersRepo.findById(userName).get().getUserStatus().equals("Attempt1")) {
                        TotpUsersDAO totpUsersDAO = new TotpUsersDAO();
                        totpUsersDAO.setUserName(userName);
                        totpUsersDAO.setPassword(totpUsersRepo.findById(userName).get().getPassword());
                        totpUsersDAO.setDate(totpUsersRepo.findById(userName).get().getDate());
                        totpUsersDAO.setUserStatus("Attempt2");
                        totpUsersRepo.save(totpUsersDAO);
                        activateBean.setMsg(TotpConstraints.InvalidAttempt2);
                        activateBean.setUserName(userName);

                        return activateBean;
                    } else if (totpUsersRepo.findById(userName).get().getUserStatus().equals("Attempt2")) {
                        TotpUsersDAO totpUsersDAO = new TotpUsersDAO();
                        totpUsersDAO.setUserName(userName);
                        totpUsersDAO.setPassword(totpUsersRepo.findById(userName).get().getPassword());
                        totpUsersDAO.setDate(totpUsersRepo.findById(userName).get().getDate());
                        totpUsersDAO.setUserStatus(TotpConstraints.LOCK);
                        totpUsersRepo.save(totpUsersDAO);
                        activateBean.setMsg(TotpConstraints.InvalidAttempt3);
                        activateBean.setUserName(userName);

                        return activateBean;
                    }
                }
            } else {
                activateBean.setMsg(TotpConstraints.AccLock);
                activateBean.setSecret(null);
                activateBean.setUserName(userName);

                return activateBean;
            }
        } else {
            activateBean.setMsg(TotpConstraints.NOTFOUND);
            activateBean.setSecret(null);
            activateBean.setUserName(userName);

            return activateBean;
        }

        return null;
    }
}
