package com.cafe24.app.discount.api.request;

import com.cafe24.app.discount.dto.ScriptTags;
import org.springframework.http.HttpMethod;

/**
 * Script Tag 조회/삭제
 */
public class ScriptTagsRequest extends Cafe24RequestBase<ScriptTags> {

    public ScriptTagsRequest(String mall_id, HttpMethod method) {
        super(mall_id);
        setMethod(method);
        setPath("/api/v2/admin/scripttags");
    }

}
