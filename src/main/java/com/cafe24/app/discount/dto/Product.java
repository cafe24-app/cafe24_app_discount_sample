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
    private List<AppDiscountInfo> app_discount_info;

    /**
     * 추가적으로 사용가능한 변수 목록
     */

 /*
    private String delvtype;
    private String main_cate_no;
    private String opt_id;
    private String product_type;
    private String naver_used_exception;
    private String quantity;
    private String check_quantity;
    private String check_quantity_type;
    private String option_add;
    private String product_min;
    private String product_max_type;
    private String product_max;
    private String product_code;l
    private String product_sum_price;
    private String product_name;
    private String opt_str;
    private String option_type;
    private String has_option;
    private String has_option_add;
    private String is_set_product;
    private String set_product_name;
    private String set_product_no;
    private String item_listing_type;
    private String is_oversea_able;
    private String set_product_list;
    private String buy_unit;
    private String check_buy_unit_type;
    private String wish_selected_item;
    private String wish_save_data;
    private String olink_data;
    private String product_paymethod;
    private String option_attached_file_info_json;
    private String total_unit_add_sale;
    private String use_store_pickup;
    private String layer_option_str;
    private String sIsBenefitEventProduct;
    private String check_buy_unit;
*/

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
        return app_discount_info;
    }

    public void setApp_discount_info(List<AppDiscountInfo> app_discount_info) {
        this.app_discount_info = app_discount_info;
    }
}
