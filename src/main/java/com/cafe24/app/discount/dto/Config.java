package com.cafe24.app.discount.dto;

public class Config extends BaseModel {

    private int value;
    private String value_type;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getValue_type() {
        return value_type;
    }

    public void setValue_type(String value_type) {
        this.value_type = value_type;
    }
}
