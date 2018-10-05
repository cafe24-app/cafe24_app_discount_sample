package com.cafe24.app.discount.controller;


import com.cafe24.app.discount.dto.DiscountInfo;
import com.cafe24.app.discount.dto.Discounts;
import com.cafe24.app.discount.dto.ReturnMsg;
import com.cafe24.app.discount.dto.ScriptTags;
import com.cafe24.app.discount.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService service;

    /**
     * 할인 정보 조회
     *
     * @param mall_id
     * @return
     */
    @GetMapping("/{mall_id}/discounts")
    public String discounts(@PathVariable(name = "mall_id") String mall_id) {
        log.info("discounts Mall_id : {}", mall_id);

        List<DiscountInfo> discounts = service.getDiscounts(mall_id);

        return new ReturnMsg(discounts).toString();
    }

    /**
     * 할인 정보 등록
     *
     * @param mall_id
     * @param discounts
     * @return
     */
    @PostMapping("/{mall_id}/discounts")
    public String createDiscounts(@PathVariable String mall_id, @RequestBody Discounts discounts) {
        log.info("createDiscounts Entry mall_id : {}", mall_id);
        log.info("createDiscounts discounts {}", discounts.toString());

        service.saveDiscounts(mall_id, discounts.getDiscounts());

        log.info("discounts result {}", discounts.toString());

        return new ReturnMsg().toString();
    }

    /**
     * 설치 스크립트 리스트 조회
     *
     * @param mall_id
     * @return
     */
    @GetMapping("/{mall_id}/scripttags")
    public String getScripttags(@PathVariable String mall_id) {
        log.info("getScripttags : {}", mall_id);

        ScriptTags scriptTags = service.getScripttags(mall_id);

        ReturnMsg returnMsg;
        if (scriptTags.getScripttags().size() == 0) {
            returnMsg = new ReturnMsg(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.name());
        } else {
            returnMsg = new ReturnMsg(scriptTags);
        }

        return returnMsg.toString();
    }

    /**
     * 스크립트 설치
     *
     * @param mall_id
     * @return
     */
    @PostMapping("/{mall_id}/scripttags")
    public String createScripttags(@PathVariable String mall_id) {
        log.info("createScripttags : {}", mall_id);

        ScriptTags scriptTags = service.createdScripttag(mall_id);

        return new ReturnMsg(scriptTags).toString();
    }

    /**
     * 스크립트 삭제
     *
     * @param mall_id
     * @return
     */
    @DeleteMapping("/{mall_id}/scripttags")
    public String deleteScripttags(@PathVariable String mall_id) {
        log.info("deleteScripttags : {}", mall_id);

        ScriptTags scriptTags = service.deleteScriptags(mall_id);

        return new ReturnMsg(scriptTags).toString();
    }

}
