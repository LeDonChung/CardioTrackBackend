package vn.edu.iuh.fit.notification.services;

import vn.edu.iuh.fit.notification.exceptions.NotificationException;

public interface SMSService {
    String sendOTP(String phoneNumber, String otp) throws NotificationException;
}
