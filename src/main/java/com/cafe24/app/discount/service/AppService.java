package com.cafe24.app.discount.service;

import com.cafe24.app.discount.api.request.AccessTokenRequest;
import com.cafe24.app.discount.core.AppEnv;
import com.cafe24.app.discount.core.store.StoreToken;
import com.cafe24.app.discount.dto.AccessToken;
import com.cafe24.app.discount.utils.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

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
     * QueryString 체크
     *
     * @param query_string
     * @param hmac_cafe
     * @return
     */
    public boolean validationCheck(String query_string, String hmac_cafe) {
        String plain_query = query_string.substring(0, query_string.lastIndexOf("&"));

        String made_hamc = Commons.makeHmac(plain_query, AppEnv.SECRET_KEY);

        log.info("validationCheck HMAC(cafe24) : {}", hmac_cafe);
        log.info("validationCheck HMAC(app) : {}", made_hamc);

        return hmac_cafe.equals(made_hamc);
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
            request.addBody("redirect_uri", AppEnv.APP_BASE_URL + "/admin");
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
