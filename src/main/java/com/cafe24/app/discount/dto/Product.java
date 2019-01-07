package com.cafe24.app.discount.dto;

import java.util.List;

public class Product extends BaseModel {

    private int basket_prd_no;
    private int product_no;
    private int product_qty;
    private int product_price;
    private int opt_price;
    private int product_sale_price;
    private int discount_price;
    private String item_code;
    private List<AppDiscountInfo> discount_info;

    public int getBasket_prd_no() {
        return basket_prd_no;
    }

    public void setBasket_prd_no(int basket_prd_no) {
        this.basket_prd_no = basket_prd_no;
    }

    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }

    public int getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(int product_qty) {
        this.product_qty = product_qty;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getOpt_price() {
        return opt_price;
    }

    public void setOpt_price(int opt_price) {
        this.opt_price = opt_price;
    }

    public int getProduct_sale_price() {
        return product_sale_price;
    }

    public void setProduct_sale_price(int product_sale_price) {
        this.product_sale_price = product_sale_price;
    }

    public int getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(int discount_price) {
        this.discount_price = discount_price;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public List<AppDiscountInfo> getApp_discount_info() {
        return discount_info;
    }

    public void setApp_discount_info(List<AppDiscountInfo> app_discount_info) {
        this.discount_info = app_discount_info;
    }
}
