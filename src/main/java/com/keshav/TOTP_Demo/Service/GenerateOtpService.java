package com.keshav.TOTP_Demo.Service;

import com.keshav.TOTP_Demo.Bean.GenerateOtpBean;
import com.keshav.TOTP_Demo.Bean.UserBean;
import com.keshav.TOTP_Demo.Encryption.CreateHMAC;
import com.keshav.TOTP_Demo.Repo.TotpActivateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.keshav.TOTP_Demo.Constraints.TotpConstraints.OTP_DIGITS;
import static com.keshav.TOTP_Demo.Constraints.TotpConstraints.TIME_STEP;

@Service
public class GenerateOtpService {

    @Autowired
    GenerateOtpBean generateOtpBean;
    @Autowired
    TotpActivateRepo totpActivateRepo;


    public Object generate(UserBean userBean) {
        String userName = userBean.getUserName();
        String secret = userBean.getSecret();
        String con = userName.concat(secret);
        if (totpActivateRepo.findById(userName).isPresent() && secret.length() == 16){
            long timeCounter = System.currentTimeMillis() / 1000 / TIME_STEP;
            byte[] hash = CreateHMAC.hmacHash(con, timeCounter);
            int otp = CreateHMAC.truncateHash(hash) % (int) Math.pow(10, OTP_DIGITS);
            generateOtpBean.setOtp(String.format("%06d",otp));
            return generateOtpBean;
        }


        return null;
    }
}
