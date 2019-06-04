package com.cafe24.app.discount.dto;


public class WebhookRemove extends BaseModel{

    private String id;
    private String resource_type;
    private String event_type;
    private String version;
    private String issue_datetime;
    private WebhookResource resource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public WebhookResource getResource() {
        return resource;
    }

    public void setResource(WebhookResource resource) {
        this.resource = resource;
    }
}
