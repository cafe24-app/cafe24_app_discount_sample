package com.cafe24.app.discount.dto;

import java.util.List;

public class ScriptRequest extends BaseModel {

    private String src;
    private List<String> display_location;

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

}
