package com.cafe24.app.discount.service;

import com.cafe24.app.discount.api.request.ScriptTagRequest;
import com.cafe24.app.discount.api.request.ScriptTagsRequest;
import com.cafe24.app.discount.core.AppEnv;
import com.cafe24.app.discount.core.store.StoreDiscount;
import com.cafe24.app.discount.core.store.StoreToken;
import com.cafe24.app.discount.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    StoreToken storeToken;

    @Autowired
    StoreDiscount storeDiscount;

    /**
     * 할인 정보 조회
     *
     * @param mall_id
     * @return
     */
    public List<DiscountInfo> getDiscounts(String mall_id) {
        return storeDiscount.get(mall_id);
    }

    /**
     * 할인 정보 등록
     *
     * @param mall_id
     * @param discounts
     */
    public void saveDiscounts(String mall_id, List<DiscountInfo> discounts) {
        storeDiscount.put(mall_id, discounts);
    }

    /**
     * 스크립트 태그 조회
     *
     * @param mall_id
     * @return
     */
    public ScriptTags getScripttags(String mall_id) {
        ScriptTagsRequest request = new ScriptTagsRequest(mall_id, HttpMethod.GET);
        request.addHeader("Authorization", storeToken.authorization(mall_id));

        ScriptTags scripttags = request.apiCall();
        log.info("getScripttags result :  {}", scripttags.toString());

        return scripttags;
    }

    /**
     * 스크립트 태그 삭제
     *
     * @param mall_id
     * @return
     */
    public ScriptTags deleteScriptags(String mall_id) {
        ScriptTags scripttags = getScripttags(mall_id);
        log.info("deleteScriptags result :  {}", scripttags.toString());

        for (Script script : scripttags.getScripttags()) {
            ScriptTagsRequest del_request = new ScriptTagsRequest(mall_id, HttpMethod.DELETE);

            del_request.addHeader("Authorization", storeToken.authorization(mall_id));
            del_request.addPath(script.getScript_no());

            ScriptTags scriptResponse = del_request.apiCall();
            log.info("deleteScriptags result :  {}", scriptResponse.toString());
        }

        return scripttags;
    }

    /**
     * 스크립트 태그 등록
     *
     * @param mall_id
     * @return
     */
    public ScriptTags createdScripttag(String mall_id) {
        ScriptTagRequest request = new ScriptTagRequest(mall_id);

        ScriptRequest scriptRequest = new ScriptRequest();

        scriptRequest.setSrc(AppEnv.APP_JS_URL);
        scriptRequest.setDisplay_location(Arrays.asList("ORDER_BASKET", "ORDER_ORDERFORM"));

        request.addHeader("Authorization", storeToken.authorization(mall_id));
        request.addHeader("Content-Type", "application/json");

        request.addBody("shop_no", 1);
        request.addBody("request", scriptRequest);

        ScriptTag result = request.apiCall();
        log.info("createdScripttag result : {}", result.getScripttag().toString());

        ScriptTags scriptTags = new ScriptTags();
        scriptTags.setScripttags(Arrays.asList(result.getScripttag()));

        return scriptTags;
    }

}
