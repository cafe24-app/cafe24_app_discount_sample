package com.cafe24.app.discount.controller;

import com.cafe24.app.discount.dto.OrderInfo;
import com.cafe24.app.discount.dto.ResOrder;
import com.cafe24.app.discount.dto.ReturnMsg;
import com.cafe24.app.discount.service.DiscountService;
import com.cafe24.app.discount.utils.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController {
    private static final Logger log = LoggerFactory.getLogger(DiscountController.class);

    @Autowired
    DiscountService service;

    /**
     * 할인 실행
     *
     * @param order
     * @return
     */
    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PATCH})
    @RequestMapping(path = "/order")
    public String app_discount(@RequestBody(required = false) OrderInfo order) {
        String trace_no = Commons.makeTrace_no();
        log.info("[{}]app_discount order : {}", trace_no, order.toString());

        //할인 적용
        ResOrder resOrder = service.doDiscount(order, trace_no);
        log.info("[{}]app_discount resOrder : {}", trace_no, resOrder.toString());

        //Hmac 적용
        service.setHmac(resOrder, trace_no);
        log.info("[{}]app_discount resOrder(+hmac) : {}", trace_no, resOrder.toString());

        return new ReturnMsg(resOrder).toString();
    }
}
