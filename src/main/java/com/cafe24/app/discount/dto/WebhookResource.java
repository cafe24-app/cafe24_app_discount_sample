package com.cafe24.app.discount.dto;

public class WebhookResource {
    private String client_id;
    private String mall_id;
    private String event_datetime;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getMall_id() {
        return mall_id;
    }

    public void setMall_id(String mall_id) {
        this.mall_id = mall_id;
    }

    public String getEvent_datetime() {
        return event_datetime;
    }

    public void setEvent_datetime(String event_datetime) {
        this.event_datetime = event_datetime;
    }
}