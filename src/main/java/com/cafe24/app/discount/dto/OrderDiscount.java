package com.cafe24.app.discount.dto;

public class OrderDiscount extends BaseModel {

    private String no;
    private String price;
    private String apply_product;

    public OrderDiscount(String no, String price, String apply_product) {
        this.no = no;
        this.price = price;
        this.apply_product = apply_product;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getApply_product() {
        return apply_product;
    }

    public void setApply_product(String apply_product) {
        this.apply_product = apply_product;
    }
}
