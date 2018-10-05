package com.cafe24.app.discount.dto;

import java.util.List;

public class Script extends BaseModel {

    private String shop_no;
    private String script_no;
    private String client_id;
    private String src;
    private List<String> display_location;
    private String skin_no;
    private String created_date;
    private String updated_date;

    public String getShop_no() {
        return shop_no;
    }

    public void setShop_no(String shop_no) {
        this.shop_no = shop_no;
    }

    public String getScript_no() {
        return script_no;
    }

    public void setScript_no(String script_no) {
        this.script_no = script_no;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<String> getDisplay_location() {
        return display_location;
    }

    public void setDisplay_location(List<String> display_location) {
        this.display_location = display_location;
    }

    public String getSkin_no() {
        return skin_no;
    }

    public void setSkin_no(String skin_no) {
        this.skin_no = skin_no;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

}
