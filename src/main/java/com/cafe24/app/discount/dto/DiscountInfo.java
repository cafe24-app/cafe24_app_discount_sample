package com.cafe24.app.discount.dto;

public class DiscountInfo extends BaseModel {

    private int value;
    private int shop_no;
    private String title;
    private String value_type;
    private String allocation_method;
    private int condition;
    private String starts_at;
    private String ends_at;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getShop_no() {
        return shop_no;
    }

    public void setShop_no(int shop_no) {
        this.shop_no = shop_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue_type() {
        return value_type;
    }

    public void setValue_type(String value_type) {
        this.value_type = value_type;
    }

    public String getAllocation_method() {
        return allocation_method;
    }

    public void setAllocation_method(String allocation_method) {
        this.allocation_method = allocation_method;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public String getStarts_at() {
        return starts_at;
    }

    public void setStarts_at(String starts_at) {
        this.starts_at = starts_at;
    }

    public String getEnds_at() {
        return ends_at;
    }

    public void setEnds_at(String ends_at) {
        this.ends_at = ends_at;
    }
}
