package com.tihom.secutity.core.properties;

/**
 * @author TiHom
 */
public class ValidateCodeProperties {

    private SmsCodeProperties sms = new SmsCodeProperties();

    private ImageCodeProperties image = new ImageCodeProperties();

    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
