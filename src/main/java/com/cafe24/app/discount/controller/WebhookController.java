package com.cafe24.app.discount.controller;

import com.cafe24.app.discount.dto.WebhookRemove;
import com.cafe24.app.discount.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 개발자 센터에서 웹훅 등록 후 사용가능
 * 앱 삭제/만료 알림
 * 앱에서 가지고 있는 해당 몰정보 삭제
 */

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    @Autowired
    WebhookService webhookService;

    @PostMapping("/remove")
    public void remove(@RequestBody WebhookRemove webhook){
        log.info(webhook.toString());
        //앱삭제 혹은 만료시 토큰 삭제
        if ("APP".equals(webhook.getResource_type()) && ("MALL.APP.DELETED".equals(webhook.getEvent_type()) || "MALL.APP.EXPIRED".equals(webhook.getEvent_type()))){
            webhookService.remove(webhook.getResource().getMall_id());
        }
    }
}
