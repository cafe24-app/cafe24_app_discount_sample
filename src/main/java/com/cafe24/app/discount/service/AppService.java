package com.cafe24.app.discount.service;

import com.cafe24.app.discount.api.request.AccessTokenRequest;
import com.cafe24.app.discount.core.AppEnv;
import com.cafe24.app.discount.core.store.StoreToken;
import com.cafe24.app.discount.dto.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class AppService {
    private static final Logger log = LoggerFactory.getLogger(AppService.class);

    @Autowired
    StoreToken storeToken;

    /**
     * 인증 코드 요청 주소 생성
     *
     * @param mall_id
     * @param requestedSessionId
     * @return
     */
    public String getCodeRedirectUrl(String mall_id, String requestedSessionId) {
        return storeToken.getCodeRedirectUrl(mall_id, requestedSessionId);
    }

    /**
     * timestamp 검증 : Replay Attack방지를 위해서 생성된 시간부터 일정 시간이 지난 호출의 경우 요청 무효화 처리(필수)
     *
     * @param timestamp
     */
    public void validationCheckTimestamp(String timestamp) throws Exception {
        long time = new Date().getTime() / 1000;
        long diffHour = (time - Integer.parseInt(timestamp)) / 3600;

        // +-1 시간 이내의 요청만 허용
        if (!(Math.abs(diffHour) < 1)) {
            throw new Exception("request timed out");
        }
    }

    /**
     * hmac 검증 : 메시지의 무결성과 인증을 위해 메시지를 해싱하여 hmac값과 다를시 요청 무료화 처리(필수)
     *
     * @param query_string
     * @param hmac_cafe
     */
    public void validationCheckHmac(String query_string, String hmac_cafe) throws Exception {
        String made_hamc = "";
        String plain_query = query_string.substring(0, query_string.lastIndexOf("&"));
        String secretKey = AppEnv.SECRET_KEY;

        /**
         * TEST Data
         */
        // plain_query = "is_multi_shop=T&lang=ko_KR&mall_id=jhbaek02&nation=KR&shop_no=1&timestamp=1622513360&user_id=jhbaek02&user_name=jhbaek02&user_type=P";
        // hmac_cafe = "8%2BhYywQW5fBMpfbTlA1puAMpM91N0FYtrpzHYrdodDM%3D";
        // secretKey = "zoQxSUptApmiFLRl2ChaxB";

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));
            mac.update(plain_query.getBytes("UTF-8"));
            made_hamc = org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(mac.doFinal());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        if (!hmac_cafe.equals(made_hamc)) {
            throw new Exception("Authentication failed");
        }
    }

    /**
     * Access Token 세팅
     *
     * @param mall_id
     * @param code
     */
    public void setAccessToken(String mall_id, String code) {
        AccessTokenRequest request = new AccessTokenRequest(mall_id);

        boolean has_token = storeToken.contains(mall_id);
        log.info("setAccessToken has_token : {}", has_token);

        if (has_token && !storeToken.token_expired(mall_id)) return;    // Access_token 만료 전

        request.addHeader("Authorization", String.format("Basic %s", new String(Base64.getEncoder().encode((AppEnv.CLIENT_ID + ":" + AppEnv.SECRET_KEY).getBytes()))));

        if (has_token && !storeToken.refresh_token_expired(mall_id)) {  // refresh_token 만료 전
            request.addBody("grant_type", "refresh_token");
            request.addBody("refresh_token", storeToken.get(mall_id).getRefresh_token());
        } else {                                                         //token이 없거나 만료되었을 때!
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");

            request.addBody("grant_type", "authorization_code");
            request.addBody("redirect_uri", AppEnv.APP_RETURN_URL);
            request.addBody("code", code);
        }
        log.info("setAccessToken request : {}", request.toString());

        AccessToken accessToken = request.apiCall();

        log.info("setAccessToken mall_id : {}", mall_id);

        if (accessToken != null) {
            storeToken.put(mall_id, accessToken);
            log.info("setAccessToken AccessTokenInfo : {}", accessToken.toString());
        }

    }

    /**
     * Access Token 존재 확인
     *
     * @param mall_id
     * @return
     */
    public boolean isValidAccessToken(String mall_id) {
        /* 토큰이 있고 리프레시토큰이 유효하면 return true; */
        return storeToken.contains(mall_id) && !storeToken.refresh_token_expired(mall_id);
    }
}
