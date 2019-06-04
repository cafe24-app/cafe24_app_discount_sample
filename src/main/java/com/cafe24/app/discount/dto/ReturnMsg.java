package com.cafe24.app.discount.dto;

import org.springframework.http.HttpStatus;

public class ReturnMsg extends BaseModel {

    private int code = HttpStatus.OK.value();
    private String message = HttpStatus.OK.name();
    private Object data;

    public ReturnMsg() {
    }

    public ReturnMsg(Object data) {
        this.data = data;
    }

    public ReturnMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}