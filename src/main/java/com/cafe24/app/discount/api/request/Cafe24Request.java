package com.cafe24.app.discount.api.request;

import org.springframework.http.HttpMethod;

import java.util.Map;

public interface Cafe24Request {

    HttpMethod method();

    String url();

    Map<String, Object> headers();

    Map<String, Object> bodys();

}