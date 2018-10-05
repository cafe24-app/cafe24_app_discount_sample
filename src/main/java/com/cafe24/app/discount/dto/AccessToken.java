package com.cafe24.app.discount.dto;

import java.util.List;


public class AccessToken extends BaseModel {

    private String access_token;
    private String expires_at;
    private String refresh_token;
    private String refresh_token_expires_at;
    private String client_id;
    private String mall_id;
    private String user_id;
    private List<String> scopes;
    private String issued_at;

    public String authorizationString() {
        return String.format("Bearer %s", access_token);
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token_expires_at() {
        return refresh_token_expires_at;
    }

    public void setRefresh_token_expires_at(String refresh_token_expires_at) {
        this.refresh_token_expires_at = refresh_token_expires_at;
    }

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public String getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(String issued_at) {
        this.issued_at = issued_at;
    }

}
