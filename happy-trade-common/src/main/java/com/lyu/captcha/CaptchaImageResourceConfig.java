package com.lyu.captcha;

import cloud.tianai.captcha.generator.ImageCaptchaGenerator;
import cloud.tianai.captcha.generator.ImageTransform;
import cloud.tianai.captcha.generator.impl.MultiImageCaptchaGenerator;
import cloud.tianai.captcha.generator.impl.transform.Base64ImageTransform;
import cloud.tianai.captcha.resource.ImageCaptchaResourceManager;
import cloud.tianai.captcha.resource.impl.DefaultImageCaptchaResourceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LEE
 * @time 2023/2/26 14:56
 */
@Configuration
public class CaptchaImageResourceConfig {
    @Bean("imageCaptchaGenerator")
    public ImageCaptchaGenerator getImageCaptchaGenerator() {
        ImageCaptchaResourceManager imageCaptchaResourceManager = new DefaultImageCaptchaResourceManager();
        // 通过资源管理器或者资源存储器
//        ResourceStore resourceStore = imageCaptchaResourceManager.getResourceStore();
        // 添加远程url图片资源
//        resourceStore.addResource(CaptchaTypeConstant.SLIDER, new Resource(URLResourceProvider.NAME, "http://www.xx.com/aa.jpg"));
        ImageTransform imageTransform = new Base64ImageTransform();
        return new MultiImageCaptchaGenerator(imageCaptchaResourceManager, imageTransform).init(true);

    }


}
