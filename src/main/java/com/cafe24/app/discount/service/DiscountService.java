package com.cafe24.app.discount.service;

import com.cafe24.app.discount.core.AppEnv;
import com.cafe24.app.discount.core.store.StoreDiscount;
import com.cafe24.app.discount.dto.*;
import com.cafe24.app.discount.utils.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DiscountService {
    private static final Logger log = LoggerFactory.getLogger(DiscountInfo.class);

    @Autowired
    StoreDiscount storeDiscount;

    /**
     * 할인 적용
     *
     * @param order
     * @param trace_no
     * @return
     */
    public ResOrder doDiscount(OrderInfo order, String trace_no) {
        int product_sale_no = 100;
        int order_discount_no = 500;
        String mall_id = order.getMall_id();

        List<OrderDiscount> order_discounts = new ArrayList<>();
        List<AppDiscount> app_discount_info = new ArrayList<>();
        List<Product> products = order.getProduct();
        List<DiscountInfo> discountInfos = storeDiscount.contains(mall_id) ? storeDiscount.get(mall_id) : new ArrayList<>();

        /* 사용가능한 모든 할인을 돌면서 Product에 적용 */
        for (DiscountInfo disInfo : discountInfos) {
            int sum_order_price = 0;
            int sum_order_opt_price = 0;
            List<String> item_codes = new ArrayList<>();
            boolean is_discount = isDiscount(order, disInfo, trace_no);

            /* 요청된 모든 상품을 조회 */
            for (Product product : products) {
                int sum_price_n_optPrice = product.getOpt_price() + product.getProduct_price();
                int discount_price = getDiscount_price(disInfo.getValue_type(), disInfo.getValue(), sum_price_n_optPrice);

                /* 상품 할인의 경우에 요청된 모든 상품에 할인 금액 세팅 */
                if (is_discount && "P".equals(disInfo.getAllocation_method())) {
                    product.setDiscount_price(discount_price + product.getDiscount_price());

                    if (product.getApp_discount_info() == null)
                        product.setApp_discount_info(new ArrayList<>());

                    product.getApp_discount_info().add(new AppDiscountInfo(product_sale_no, discount_price));
                }

                item_codes.add(product.getItem_code());

                sum_order_price += product.getProduct_price() * product.getQuantity();
                sum_order_opt_price += product.getOpt_price();
            }

            Config config = new Config();

            config.setValue(disInfo.getValue());
            config.setValue_type(disInfo.getValue_type());

            /* 할인정보세팅 - 주문할인의 경우 */
            if (is_discount && "O".equals(disInfo.getAllocation_method())) {
                int sum_price_n_optPrice = sum_order_price + sum_order_opt_price;
                int discount_price = getDiscount_price(disInfo.getValue_type(), disInfo.getValue(), sum_price_n_optPrice);

                OrderDiscount discountVo = new OrderDiscount(String.valueOf(order_discount_no), String.valueOf(discount_price), String.join(",", item_codes));
                order_discounts.add(discountVo);

                //주문할인 정보 생성
                AppDiscount appDisc = new AppDiscount();
                appDisc.setNo(order_discount_no);
                appDisc.setType("O");
                appDisc.setName(disInfo.getTitle());
                appDisc.setIcon("http://placehold.it/32x32");
                appDisc.setConfig(config);

                app_discount_info.add(appDisc);
                order_discount_no++;
            }
            /* 할인정보세팅 - 상품 할인의 경우 */
            else if (is_discount && "P".equals(disInfo.getAllocation_method())) {
                //주문할인 정보 생성
                AppDiscount appDisc = new AppDiscount();
                appDisc.setNo(product_sale_no);
                appDisc.setType("P");
                appDisc.setName(disInfo.getTitle());
                appDisc.setIcon("http://placehold.it/32x32");
                appDisc.setConfig(config);

                app_discount_info.add(appDisc);
                product_sale_no++;
            }
        }

        //최종 응답 생성
        ResOrder resOrder = new ResOrder();
        resOrder.setMall_id(mall_id);
        resOrder.setShop_no(order.getShop_no());
        resOrder.setMember_id(order.getMember_id());
        resOrder.setMember_group_no(order.getMember_group_no());
        resOrder.setGuest_key(order.getGuest_key());
        resOrder.setProduct_discount(products);
        resOrder.setOrder_discount(order_discounts);
        resOrder.setApp_discount_info(app_discount_info);
        resOrder.setTime(order.getTime());
        resOrder.setTrace_no(trace_no);
        resOrder.setApp_key(AppEnv.CLIENT_ID);

        return resOrder;
    }

    /**
     * hmac 등록
     *
     * @param orderInfo
     * @param trace_no
     * @return
     */
    public ResOrder setHmac(ResOrder orderInfo, String trace_no) {
        if (orderInfo == null) return null;

        log.info("[{}]setHmac orderInfo: {}", trace_no, orderInfo.toString());

        if (orderInfo.getMember_id() != null && !orderInfo.getMember_id().isEmpty()) {
            log.info("[{}]setHmac member_id() : {}", trace_no, orderInfo.getMember_id());
            orderInfo.setGuest_key(Commons.getEncMD5(orderInfo.getMember_id()));
        }
        /* Hmac 작성시 Json String에 제일 마지막에 guest_key가 있어야합니다. 순서보장을 위해 LinkedHashMap()등을 사용하세요. */
        String str_hmac = Commons.makeHmac(orderInfo.toString(), AppEnv.SERVICE_KEY);

        //Hmac은 Json String에 제일 마지막에 있어야합니다.
        orderInfo.setHmac(str_hmac);

        //Hmac 추가 후 Guest_key 삭제
        orderInfo.setGuest_key(null);

        return orderInfo;
    }

    /**
     * 할인 계산
     *
     * @param value_type
     * @param value
     * @param sum_price_n_optPrice
     * @return
     */
    private int getDiscount_price(String value_type, int value, int sum_price_n_optPrice) {
        //Value_type이 P이면 정률제계산식 적용
        int discount_price;
        if ("P".equals(value_type)) {            //정률제 계산식
            discount_price = sum_price_n_optPrice * value / 100;
        } else {                                //정액제 계산식
            discount_price = value;
        }

        return discount_price;
    }

    /**
     * 할인 가능한 주문인지 확인
     *
     * @param discInfo
     * @return boolean 가능여부
     */
    private boolean isDiscount(OrderInfo orderInfo, DiscountInfo discInfo, String trace_no) {
        //해당 shop_no 할인 정책이 있는 지 확인
        if (orderInfo.getShop_no() != discInfo.getShop_no()) {
            log.info("[{}] {}에 대한 할인 정책이 없습니다.(Shop_no)", trace_no, orderInfo.getShop_no());

            return false;
        }

        //할인 적용 일시 확인
        Date time = new Date(Long.parseLong((orderInfo.getTime())));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
        if (discInfo.getStarts_at() != null)
            if (localDateTime.isBefore(LocalDateTime.parse(discInfo.getStarts_at())) || localDateTime.isAfter(LocalDateTime.parse(discInfo.getEnds_at()))) {
                log.info("[{}] {}에 대한 할인 정책이 없습니다.(할인 기간)", trace_no, localDateTime);

                return false;
            }

        //선택한 요일 확인
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day_num = calendar.get(Calendar.DAY_OF_WEEK);
        if (day_num != discInfo.getCondition()) {
            log.info("[{}] {}에 대한 할인 정책이 없습니다.(condition - day_num)", trace_no, day_num);
            log.info("[{}] {}에 대한 할인 정책이 없습니다.(condition - discInfo.getCondition())", trace_no, discInfo.getCondition());

            return false;
        }

        return true;
    }

}
