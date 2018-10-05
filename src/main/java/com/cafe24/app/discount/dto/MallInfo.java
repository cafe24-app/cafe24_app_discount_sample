package com.cafe24.app.discount.dto;

public class MallInfo extends BaseModel {

    private String is_multi_shop;
    private String lang;
    private String mall_id;
    private String shop_no;
    private String timestamp;
    private String user_id;
    private String user_name;
    private String user_type;
    private String hmac;

    public String getIs_multi_shop() {
        return is_multi_shop;
    }

    public void setIs_multi_shop(String is_multi_shop) {
        this.is_multi_shop = is_multi_shop;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMall_id() {
        return mall_id;
    }

    public void setMall_id(String mall_id) {
        this.mall_id = mall_id;
    }

    public String getShop_no() {
        return shop_no;
    }

    public void setShop_no(String shop_no) {
        this.shop_no = shop_no;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }
}
