package com.cafe24.app.discount.service;

import com.cafe24.app.discount.core.store.StoreDiscount;
import com.cafe24.app.discount.core.store.StoreToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {
    @Autowired
    StoreToken storeToken;

    @Autowired
    StoreDiscount storeDiscount;

    public void remove(String key) {
        //토큰정보 삭제
        storeToken.remove(key);
        //할인정보 삭제
        storeDiscount.remove(key);
    }
}
