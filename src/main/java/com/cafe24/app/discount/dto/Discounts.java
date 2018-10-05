package com.cafe24.app.discount.dto;

import java.util.List;

public class Discounts extends BaseModel {
    List<DiscountInfo> discounts;

    public Discounts() {
    }

    public List<DiscountInfo> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountInfo> discounts) {
        this.discounts = discounts;
    }
}
