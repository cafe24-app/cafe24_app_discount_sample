package com.cafe24.app.discount.dto;

import java.util.List;

public class ScriptTags extends BaseModel {

    private List<Script> scripttags;

    public List<Script> getScripttags() {
        return scripttags;
    }

    public void setScripttags(List<Script> scripttags) {
        this.scripttags = scripttags;
    }

}
