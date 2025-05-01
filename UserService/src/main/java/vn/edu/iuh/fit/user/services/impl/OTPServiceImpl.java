package vn.edu.iuh.fit.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.services.OTPService;
import vn.edu.iuh.fit.user.utils.SystemConstraints;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public String generateOTP(String phoneNumber) {
        // Check if the phone number is already in the cache
        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(900000));
        redisTemplate.opsForValue().set(phoneNumber, otp, 1, TimeUnit.MINUTES);
        return otp;
    }

    @Override
    public boolean validateOTP(String phoneNumber, String otp) throws UserException {
        // Nếu phoneNumber 0 -> + 84
        if (phoneNumber.startsWith("0") && phoneNumber.length() == 10) {
            phoneNumber = "+84" + phoneNumber.substring(1);
        }
        String storedOtp = (String) redisTemplate.opsForValue().get(phoneNumber);
        // Check if the OTP is expired
        if (storedOtp == null) {
            throw new UserException(SystemConstraints.OTP_EXPIRED);
        }
        if (storedOtp.equals(otp)) {
            redisTemplate.delete(phoneNumber);
            return true;
        }
        return false;
    }

}
