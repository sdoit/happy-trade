package com.lyu.util.aliyun;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.lyu.exception.SmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阿里云短信服务
 *
 * @author LEE
 * @time 2023/3/6 9:07
 */
@Component
public class Sms {
    public enum SmsNotifyType {

        /**
         * 商品有了新出价
         */
        NewBid,
        /**
         * 用户支付了你的订单
         */
        PayOrder,
        /**
         * 订单被确定收货
         */
        OrderComplete,
        /**
         * 出价被同意
         */
        BidRejected,
        /**
         * 出价被拒绝
         */
        BidAgreed,
        /**
         * 求购被下架或删除
         */
        RequestCancel,

    }

    private final Client client;
    @Value("${aliyun.signName}")
    private String signName;
    @Value("${aliyun.templateCodeSendCode}")
    private String templateCodeSendCode;
    @Value("${aliyun.templateCodeSendPaySuccessNotification}")
    private String templateCodeSendPaySuccessNotification;

    public Sms(@Value("${aliyun.accessKeyId}") String accessKeyId,
               @Value("${aliyun.accessKeySecret}") String accessKeySecret) throws Exception {
        // Configure Credentials authentication information, including ak, secret, token
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        this.client = new Client(config);
    }

    public SendSmsResponse sendCode(String phoneNumber, String code) throws Exception {

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(signName)
                .setTemplateCode(templateCodeSendCode)
                .setTemplateParam("{\"code\":\"" + code + "\"}");

        RuntimeOptions runtime = new RuntimeOptions();
        return client.sendSmsWithOptions(sendSmsRequest, runtime);
    }

    public SendSmsResponse sendNotification(String phoneNumber, SmsNotifyType smsNotifyType) throws SmsException {
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(signName);
        switch (smsNotifyType) {
            case PayOrder:
                sendSmsRequest.setTemplateCode(templateCodeSendPaySuccessNotification);
                break;
            case OrderComplete:
                //模板未申请
                return null;
            case BidRejected:
                //模板未申请
                return null;

            case BidAgreed:
                //模板未申请
                return null;
            default:
                return null;
        }
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            return client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (Exception e) {
            throw new SmsException(0, e.getMessage());
        }
    }


}
