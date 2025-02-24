package vn.edu.iuh.fit.user.services;

import vn.edu.iuh.fit.user.exceptions.UserException;

public interface OTPService {
    public String generateOTP(String phoneNumber);
    public boolean validateOTP(String phoneNumber, String otp) throws UserException;
}
