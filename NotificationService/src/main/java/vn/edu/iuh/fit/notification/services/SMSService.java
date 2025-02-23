package vn.edu.iuh.fit.notification.services;

public interface SMSService {
    String sendOTP(String phoneNumber, String otp);
}
