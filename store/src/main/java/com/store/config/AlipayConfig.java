package com.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Configuration
@ConfigurationProperties(prefix = "alipayconfig",ignoreUnknownFields = false)
@PropertySource("classpath:alipay.properties")//读取配置文件
@Component
public class AlipayConfig implements Serializable {
    // 应用ID
    private String app_id;
    //商户私钥
    private String merchant_private_key;
    //支付宝公钥
    private String alipay_public_key;
    //服务器异步通知页面路径
    private String notify_url;
    //页面跳转同步通知页面路径
    private String return_url;
    //签名方式
    private String sign_type;
    //字符编码格式
    private String charset;
    //支付宝网关
    private String gatewayUrl;
    /** 格式 */
    private String format = "json";

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMerchant_private_key() {
        return merchant_private_key;
    }

    public void setMerchant_private_key(String merchant_private_key) {
        this.merchant_private_key = merchant_private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
