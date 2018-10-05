package com.cafe24.app.discount.dto;


import com.cafe24.app.discount.utils.JSONFormatter;

public class BaseModel {

    public String toJSON() {
        return JSONFormatter.toJSON(this);
    }

    @Override
    public String toString() {
        return JSONFormatter.convertJsonNode(toJSON());
    }
}
