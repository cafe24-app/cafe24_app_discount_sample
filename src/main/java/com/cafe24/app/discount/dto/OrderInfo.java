package com.cafe24.app.discount.dto;

import java.util.List;

public class OrderInfo extends BaseModel {

    private String mall_id;
    private int shop_no;
    private String member_id;
    private String guest_key;
    private String member_group_no;
    private String time;
    private List<Product> product;

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

    public String getGuest_key() {
        return guest_key;
    }

    public void setGuest_key(String guest_key) {
        this.guest_key = guest_key;
    }

    public String getMember_group_no() {
        return member_group_no;
    }

    public void setMember_group_no(String member_group_no) {
        this.member_group_no = member_group_no;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
