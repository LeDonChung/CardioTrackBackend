package vn.edu.iuh.fit.notification.services.impl;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.notification.services.SMSService;

import java.util.Collections;
import java.util.List;

@Service
public class SMSServiceImpl implements SMSService {

    @Value("${infobip.api.key}")
    private String API_KEY;

    @Value("${infobip.base.url}")
    private String BASE_URL;


    @Override
    public String sendOTP(String phoneNumber, String otp) {
        var apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY)).withBaseUrl(BaseUrl.from(BASE_URL)).build();

        var sendSmsApi = new SmsApi(apiClient);

        SmsMessage message = new SmsMessage()
                .sender("InfoSMS")
                .addDestinationsItem(new SmsDestination().to(phoneNumber))
                .content(new SmsTextContent().text(otp));
        SmsRequest smsMessageRequest = new SmsRequest()
                .messages(List.of(message));
        try {
            SmsResponse smsResponse = sendSmsApi.sendSmsMessages(smsMessageRequest).execute();
            System.out.println(smsResponse);
            return "OTP sent successfully";
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
        return "OTP sent failed";
    }
}
