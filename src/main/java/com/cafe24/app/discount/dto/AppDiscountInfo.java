package com.cafe24.app.discount.dto;

public class AppDiscountInfo extends BaseModel {

    private int no;
    private int price;
    private String discount_unit;

    public AppDiscountInfo(int no, int price, String discount_unit) {
        this.no = no;
        this.price = price;
        this.discount_unit = discount_unit;
    }
}
