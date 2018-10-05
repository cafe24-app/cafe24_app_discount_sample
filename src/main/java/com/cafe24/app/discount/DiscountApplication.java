package com.cafe24.app.discount;

import com.cafe24.app.discount.core.store.StoreDiscount;
import com.cafe24.app.discount.core.store.StoreToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiscountApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscountApplication.class, args);
    }

    /**
     * 객체 등록 - 토큰 저장소
     *
     * @return
     */
    @Bean
    public StoreToken storeToken() {
        return new StoreToken();
    }

    /**
     * 객체 등록 - 할인 저장소
     *
     * @return
     */
    @Bean
    public StoreDiscount storeDiscount() {
        return new StoreDiscount();
    }
}
