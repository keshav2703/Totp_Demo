package com.keshav.TOTP_Demo.Service;

import com.keshav.TOTP_Demo.Bean.GenerateOtpBean;
import com.keshav.TOTP_Demo.Bean.UserBean;
import com.keshav.TOTP_Demo.Bean.ValidateOtpBean;
import com.keshav.TOTP_Demo.Constraints.TotpConstraints;
import com.keshav.TOTP_Demo.Encryption.CreateHMAC;
import com.keshav.TOTP_Demo.Encryption.Encrypt;
import com.keshav.TOTP_Demo.Model.TotpActivateDAO;
import com.keshav.TOTP_Demo.Model.TotpUsersDAO;
import com.keshav.TOTP_Demo.Repo.TotpActivateRepo;
import com.keshav.TOTP_Demo.Repo.TotpUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.keshav.TOTP_Demo.Constraints.TotpConstraints.OTP_DIGITS;
import static com.keshav.TOTP_Demo.Constraints.TotpConstraints.TIME_STEP;

@Service
public class ValidateOtpService {
    @Autowired
    ValidateOtpBean validateOtpBean;
    @Autowired
    TotpActivateRepo totpActivateRepo;
    @Autowired
    Encrypt encrypt;
    @Autowired
    TotpUsersRepo totpUsersRepo;


    public Object validate(UserBean userBean) {

        String userName = userBean.getUserName();
        String otp = userBean.getOtp();
        if (totpActivateRepo.findById(userName).isPresent()) {
            if (!totpUsersRepo.findById(userName).get().getUserStatus().equals(TotpConstraints.LOCK)) {
                String decodedSecret = encrypt.getDecodedSecret(totpActivateRepo.findById(userName).get().getSecret(), totpActivateRepo.findById(userName).get().getPassword());
                String con = userName.concat(decodedSecret);
                long timeCounter = System.currentTimeMillis() / 1000 / TIME_STEP;
                byte[] hash = CreateHMAC.hmacHash(con, timeCounter);
                int otp1 = CreateHMAC.truncateHash(hash) % (int) Math.pow(10, OTP_DIGITS);
                String sixDigit = String.format("%06d", otp1);
                System.out.println(otp + "\n" + sixDigit);
                if (sixDigit.equals(otp)) {
                    if (!otp.equals(totpActivateRepo.findById(userName).get().getOtp())) {
                        TotpActivateDAO totpActivateDAO = new TotpActivateDAO();
                        totpActivateDAO.setOtp(otp);
                        totpActivateDAO.setDatetime(totpActivateRepo.findById(userName).get().getDatetime());
                        totpActivateDAO.setSecret(totpActivateRepo.findById(userName).get().getSecret());
                        totpActivateDAO.setUserName(totpActivateRepo.findById(userName).get().getUserName());
                        totpActivateDAO.setPassword(totpActivateRepo.findById(userName).get().getPassword());
                        totpActivateRepo.save(totpActivateDAO);
                        validateOtpBean.setAuth(true);
                        validateOtpBean.setMsg(TotpConstraints.Authenticated);
                        validateOtpBean.setUserName(userName);
                        return validateOtpBean;
                    }else {
                        validateOtpBean.setAuth(false);
                        validateOtpBean.setMsg(TotpConstraints.Reused);
                        validateOtpBean.setUserName(userName);
                        return validateOtpBean;
                    }
                } else {
                    if (totpUsersRepo.findById(userName).get().getUserStatus().equals(TotpConstraints.ACTIVATE)) {
                        TotpUsersDAO totpUsersDAO = new TotpUsersDAO();
                        totpUsersDAO.setUserName(userName);
                        totpUsersDAO.setPassword(totpUsersRepo.findById(userName).get().getPassword());
                        totpUsersDAO.setDate(totpUsersRepo.findById(userName).get().getDate());
                        totpUsersDAO.setUserStatus("Attempt1");
                        totpUsersRepo.save(totpUsersDAO);
                        validateOtpBean.setAuth(false);
                        validateOtpBean.setMsg(TotpConstraints.InvalidAttempt);
                        validateOtpBean.setUserName(userName);

                        return validateOtpBean;
                    } else if (totpUsersRepo.findById(userName).get().getUserStatus().equals("Attempt1")) {
                        TotpUsersDAO totpUsersDAO = new TotpUsersDAO();
                        totpUsersDAO.setUserName(userName);
                        totpUsersDAO.setPassword(totpUsersRepo.findById(userName).get().getPassword());
                        totpUsersDAO.setDate(totpUsersRepo.findById(userName).get().getDate());
                        totpUsersDAO.setUserStatus("Attempt2");
                        totpUsersRepo.save(totpUsersDAO);
                        validateOtpBean.setAuth(false);
                        validateOtpBean.setMsg(TotpConstraints.InvalidAttempt2);
                        validateOtpBean.setUserName(userName);

                        return validateOtpBean;
                    } else if (totpUsersRepo.findById(userName).get().getUserStatus().equals("Attempt2")) {
                        TotpUsersDAO totpUsersDAO = new TotpUsersDAO();
                        totpUsersDAO.setUserName(userName);
                        totpUsersDAO.setPassword(totpUsersRepo.findById(userName).get().getPassword());
                        totpUsersDAO.setDate(totpUsersRepo.findById(userName).get().getDate());
                        totpUsersDAO.setUserStatus(TotpConstraints.LOCK);
                        totpUsersRepo.save(totpUsersDAO);
                        validateOtpBean.setAuth(false);
                        validateOtpBean.setMsg(TotpConstraints.InvalidAttempt3);
                        validateOtpBean.setUserName(userName);

                        return validateOtpBean;
                    }
                }

            }else {
                validateOtpBean.setAuth(false);
                validateOtpBean.setMsg(TotpConstraints.AccLock);
                validateOtpBean.setUserName(userName);
                return validateOtpBean;
            }
        }
        return null;
    }
}
