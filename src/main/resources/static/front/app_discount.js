//App의 할인 로직 호출
function app_do_sale(params) {
    var app_discount_order = {};

    app_discount_order.mall_id = params.ec_mall_id;
    app_discount_order.shop_no = params.shop_no;
    app_discount_order.member_id = params.member_id;
    app_discount_order.guest_key = params.guest_key;
    app_discount_order.member_group_no = params.group_no;
    app_discount_order.product = params.products;
    app_discount_order.time = new Date().getTime();

    var opts = {
        method: 'POST',
        body: JSON.stringify(app_discount_order),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    };
    fetch('https://saleapp.cafe24.com/discount/order', opts).then(function(response) {
        return response.json();
    }).then(function(result) {
        console.log('App_discount success!');
        console.log(JSON.stringify(result));

        AppDiscount.setAppDiscountPrice(JSON.stringify(result.data));

        console.log('App_discount OK!');
    });
}

//기본 정보 및 상품정보 세팅
var app_init_sale = function () {
    var app_discount_req_params = {};
    var app_discount_products;

    if (typeof(sPage) == 'undefined') {              //sPage 변수가 없을때
        console.log("sPage 변수가 존재하지 않습니다.");
        return;
    } else if (sPage == 'ORDER_BASKET') {            //장바구니
        app_discount_products = aBasketProductData;
    } else if (sPage == 'ORDER_ORDERFORM') {        // 주문서
        app_discount_products = aBasketProductOrderData;
    }

    //담긴 상품이 없으면 종료
    if (app_discount_products.length <= 0) {
        console.log("상품이 존재하지 않습니다.");
        return;
    }

    app_discount_req_params.products = app_discount_products;

    //CAFE24FrontAPI 활용 기본정보 조회
    (function (CAFE24API) {
        app_discount_req_params.ec_mall_id = CAFE24API.MALL_ID;
        app_discount_req_params.shop_no = CAFE24API.SHOP_NO;

        // 회원정보 조회
        CAFE24API.getMemberInfo(function (res) {
            app_discount_req_params.member_id = res.id.member_id;
            app_discount_req_params.group_no = Number(res.id.group_no);

            if (app_discount_req_params.member_id == null) {
                app_discount_req_params.guest_key = res.id.guest_id;
            }

            app_do_sale(app_discount_req_params);
        });
    })(CAFE24API.init('[App key]'));//<-------------------------------- [App key] 수정

};

//window.onload 확인 후 이벤트 리스너에 등록
if (document.readyState == 'complete') {
    app_init_sale();
} else {
    window.addEventListener('load', app_init_sale);
}
