package com.cafe24.app.discount.dto;

import java.util.List;

public class Product extends BaseModel {

    private int basket_product_no;
    private int product_no;
    private int quantity;
    private int price;
    private int option_price;
    private int quantity_based_discount;
    private int non_quantity_based_discount;
    private int app_quantity_based_discount;
    private int app_non_quantity_based_discount;
    private String variant_code;
    private List<AppDiscountInfo> app_product_discount_info;

    public int getBasket_product_no() {
        return basket_product_no;
    }

    public void setBasket_product_no(int basket_product_no) {
        this.basket_product_no = basket_product_no;
    }

    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOption_price() {
        return option_price;
    }

    public void setOption_price(int option_price) {
        this.option_price = option_price;
    }

    public int getQuantity_based_discount() {
        return quantity_based_discount;
    }

    public void setQuantity_based_discount(int quantity_based_discount) {
        this.quantity_based_discount = quantity_based_discount;
    }

    public int getApp_non_quantity_based_discount() {
        return app_non_quantity_based_discount;
    }

    public void setApp_non_quantity_based_discount(int app_non_quantity_based_discount) {
        this.app_non_quantity_based_discount = app_non_quantity_based_discount;
    }

    public int getApp_quantity_based_discount() {
        return app_quantity_based_discount;
    }

    public void setApp_quantity_based_discount(int app_quantity_based_discount) {
        this.app_quantity_based_discount = app_quantity_based_discount;
    }

    public String getVariant_code() {
        return variant_code;
    }

    public void setVariant_code(String variant_code) {
        this.variant_code = variant_code;
    }

    public List<AppDiscountInfo> getApp_product_discount_info() {
        return app_product_discount_info;
    }

    public void setApp_product_discount_info(List<AppDiscountInfo> app_product_discount_info) {
        this.app_product_discount_info = app_product_discount_info;
    }
}
