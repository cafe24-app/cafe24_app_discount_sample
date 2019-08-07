package com.cafe24.app.discount.dto;

public class OrderDiscount extends BaseModel {

    private int no;
    private int price;
    private String apply_product;

    public OrderDiscount(int no, int price, String apply_product) {
        this.no = no;
        this.price = price;
        this.apply_product = apply_product;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getApply_product() {
        return apply_product;
    }

    public void setApply_product(String apply_product) {
        this.apply_product = apply_product;
    }
}
