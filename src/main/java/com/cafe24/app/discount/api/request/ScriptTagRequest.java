package com.cafe24.app.discount.api.request;

import com.cafe24.app.discount.dto.ScriptTag;
import org.springframework.http.HttpMethod;

/**
 * Script Tag 등록
 */
public class ScriptTagRequest extends Cafe24RequestBase<ScriptTag> {

    public ScriptTagRequest(String mall_id) {
        super(mall_id);
        setMethod(HttpMethod.POST);
        setPath("/api/v2/admin/scripttags");
    }

}
