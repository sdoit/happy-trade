package com.lyu.config;

/**
 * @author LEE
 * @time 2023/1/12 20:35
 */

import com.alipay.api.CertAlipayRequest;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String notifyUrl;
    private String rootCertContent;
    private String certContent;
    private String alipayPublicCertContent;
    private String rootCertPath;
    private String certPath;
    private String alipayPublicCertPath;

    @PostConstruct
    public void init() {
        // 设置参数（全局只需设置一次）
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";
        config.appId = this.appId;
        config.notifyUrl = this.notifyUrl;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayCertPath = alipayPublicCertPath;
        config.merchantCertPath = certPath;
        config.alipayRootCertPath = rootCertPath;

        Factory.setOptions(config);


        System.out.println("=======支付宝SDK初始化成功=======");
    }

    @Bean(name = "certAlipayRequest")
    public CertAlipayRequest getCertAlipayRequest() {
        //alipay通用API
        CertAlipayRequest alipayConfig = new CertAlipayRequest();
        alipayConfig.setPrivateKey(this.appPrivateKey);

        alipayConfig.setCertContent(certContent);
        alipayConfig.setAlipayPublicCertContent(alipayPublicCertContent);
        alipayConfig.setRootCertContent(rootCertContent);

        alipayConfig.setServerUrl("https://openapi.alipaydev.com/gateway.do");
        alipayConfig.setAppId(this.appId);
        alipayConfig.setCharset("UTF8");
        alipayConfig.setSignType("RSA2");
        alipayConfig.setEncryptor("");
        alipayConfig.setFormat("json");
        return alipayConfig;
    }



}
