package com.cafe24.app.discount.dto;

import java.util.List;

public class ResOrder extends BaseModel {

    private String mall_id;
    private int shop_no;
    private String member_id;
    private String member_group_no;
    private List<Product> product_discount;
    private List<OrderDiscount> order_discount;
    private List<AppDiscount> app_discount_info;
    private String time;
    private String trace_no;
    private String app_key;
    private String guest_key;
    private String hmac;

    public String getMall_id() {
        return mall_id;
    }

    public void setMall_id(String mall_id) {
        this.mall_id = mall_id;
    }

    public int getShop_no() {
        return shop_no;
    }

    public void setShop_no(int shop_no) {
        this.shop_no = shop_no;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_group_no() {
        return member_group_no;
    }

    public void setMember_group_no(String member_group_no) {
        this.member_group_no = member_group_no;
    }

    public List<Product> getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(List<Product> product_discount) {
        this.product_discount = product_discount;
    }

    public List<OrderDiscount> getOrder_discount() {
        return order_discount;
    }

    public void setOrder_discount(List<OrderDiscount> order_discount) {
        this.order_discount = order_discount;
    }

    public List<AppDiscount> getApp_discount_info() {
        return app_discount_info;
    }

    public void setApp_discount_info(List<AppDiscount> app_discount_info) {
        this.app_discount_info = app_discount_info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrace_no() {
        return trace_no;
    }

    public void setTrace_no(String trace_no) {
        this.trace_no = trace_no;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getGuest_key() {
        return guest_key;
    }

    public void setGuest_key(String guest_key) {
        this.guest_key = guest_key;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }
}

