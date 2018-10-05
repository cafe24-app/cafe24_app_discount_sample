package com.cafe24.app.discount.dto;

public class AppDiscountInfo extends BaseModel {

    private int no;
    private int price;

    public AppDiscountInfo(int no, int price) {
        this.no = no;
        this.price = price;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
