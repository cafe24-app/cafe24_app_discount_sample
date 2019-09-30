var cafe24_discount_sample_app = (function () {
    'use strict';
    //TODO : discount_url, client_id 수정
    const discount_url = "[Discount url]";//<-------------------------------- [Discount url] 수정
    const client_id = "[App key]";        //<-------------------------------- [App key] 수정

    return {
        discount_do: function (params) {            //App의 할인 로직 호출
            var app_discount_order = {};

            app_discount_order.mall_id = params.ec_mall_id;
            app_discount_order.shop_no = params.shop_no;
            app_discount_order.member_id = params.member_id;
            app_discount_order.guest_key = params.guest_key;
            app_discount_order.member_group_no = params.group_no;
            app_discount_order.product = params.products;
            app_discount_order.time = Math.ceil(new Date().getTime() / 1000);

            $.ajax({
                url: discount_url,
                type: 'POST',
                cache: false,
                data: JSON.stringify(app_discount_order),
                contentType: 'application/json; charset=utf-8',
                success: function (result) {
                    console.log('DM_discount Success!');
                    if (result.code === 200) {
                        AppDiscount.setAppDiscountPrice(result.data);
                    }
                },
                error: function (request, status, error) {
                    console.log('DM_discount Error!');
                }
            });
        },
        discount_init: function () {        //기본 정보 및 상품정보 세팅
            var app_discount_req_params = {};

            //장바구니 정보 조회
            CAFE24API.getCartItemList(function (err, res) {
                if (err) {
                    console.log(err);
                } else {
                    if (res.items.length > 0) {
                        app_discount_req_params.products  = res.items;
                    } else {
                        console.log("There is no product in the basket.");
                    }
                }
            });

            //CAFE24FrontAPI 활용 기본정보 조회
            (function (CAFE24API) {
                app_discount_req_params.ec_mall_id = CAFE24API.MALL_ID;
                app_discount_req_params.shop_no = CAFE24API.SHOP_NO;

                // 회원정보 조회
                CAFE24API.getMemberInfo(function (res) {
                    app_discount_req_params.group_no = Number(res.id.group_no);

                    if (res.id.member_id == null) {
                        app_discount_req_params.member_id = null;
                        app_discount_req_params.guest_key = res.id.guest_id;
                    } else {
                        app_discount_req_params.member_id = res.id.member_id;
                    }

                    cafe24_discount_sample_app.discount_do(app_discount_req_params);
                });
            })(CAFE24API.init(client_id));    //<-------------------------------- [App key] 수정
        }
    }
})();

//window.onload 확인 후 이벤트 리스너에 등록
if (document.readyState == 'complete') {
    $("body").bind("EC_ORDER_ORDERFORM_CHANGE", function (e, oParam) {
        if (oParam.event_type !== 'product_change') {
            return;
        }
        cafe24_discount_sample_app.discount_init();
    });

    cafe24_discount_sample_app.discount_init();
} else {
    window.addEventListener('load', cafe24_discount_sample_app.discount_init);
}
