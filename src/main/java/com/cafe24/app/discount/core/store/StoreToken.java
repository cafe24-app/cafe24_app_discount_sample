package com.cafe24.app.discount.core.store;

import com.cafe24.app.discount.api.request.AccessTokenRequest;
import com.cafe24.app.discount.core.AppEnv;
import com.cafe24.app.discount.dto.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 토큰 정보 저장소
 */
public class StoreToken implements Store<AccessToken> {
    private static final Logger log = LoggerFactory.getLogger(StoreToken.class);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");

    private Map<String, AccessToken> store = new ConcurrentHashMap<>();

    /**
     * AccessToken 조회
     *
     * @param key
     * @return
     */
    @Override
    public AccessToken get(String key) {
        return store.get(key);
    }

    /**
     * AccessToken 저장
     *
     * @param key
     * @param value
     */
    @Override
    public void put(String key, AccessToken value) {
        store.put(key, value);
    }

    /**
     * 토큰 유무 조회
     *
     * @param key
     * @return
     */
    @Override
    public boolean contains(String key) {
        return store.containsKey(key);
    }

    /**
     * Authorization 조회
     *
     * @param key
     * @return
     */
    public String authorization(String key) {

        boolean has_token = this.contains(key);
        log.info("authorization has_token : {}", has_token);

        if (has_token && !this.token_expired(key)) return this.get(key).authorizationString();    // Access_token 만료 전

        if (has_token && !this.refresh_token_expired(key)) {                // refresh_token 만료 전
            AccessTokenRequest request = new AccessTokenRequest(key);

            request.addHeader("Authorization", String.format("Basic %s", new String(Base64.getEncoder().encode((AppEnv.CLIENT_ID + ":" + AppEnv.SECRET_KEY).getBytes()))));
            request.addBody("grant_type", "refresh_token");
            request.addBody("refresh_token", this.store.get(key).getRefresh_token());

            AccessToken accessToken = request.apiCall();

            put(key, accessToken);
        } else {
            this.store.remove(key);
            log.error("Access Token not found. : mall_id({})", key);
        }

        return store.get(key).authorizationString();
    }

    /**
     * AccessToken 만료 조회 - true : 만료, false : 유효
     *
     * @param key
     * @return
     */
    public boolean token_expired(String key) {
        AccessToken accessToken = this.store.get(key);
        Date expires_at = null;

        try {
            expires_at = simpleDateFormat.parse(accessToken.getExpires_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date().after(expires_at);
    }

    /**
     * refresh_token 만료 조회 - true : 만료, false : 유효
     *
     * @param key
     * @return
     */
    public boolean refresh_token_expired(String key) {
        AccessToken accessToken = this.store.get(key);
        Date expires_at = null;

        try {
            expires_at = simpleDateFormat.parse(accessToken.getRefresh_token_expires_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date().after(expires_at);
    }

    /**
     * 인증 코드 요청 주소 생성
     *
     * @param mall_id
     * @param requestedSessionId
     * @return
     */
    public String getCodeRedirectUrl(String mall_id, String requestedSessionId) {

        String codeRedirectUrl = new StringBuilder()
                .append("redirect:")
                .append("https://")
                .append(mall_id)
                .append(".cafe24api.com")
                .append("/api/v2/oauth/authorize")
                .append("?response_type=").append("code")
                .append("&client_id=").append(AppEnv.CLIENT_ID)
                .append("&state=").append(requestedSessionId)
                .append("&redirect_uri=").append(AppEnv.APP_RETURN_URL)
                .append("&scope=").append(AppEnv.APP_SCOPE)
                .toString();

        log.info("getCodeRedirectUrl : {}", codeRedirectUrl);

        return codeRedirectUrl;
    }
}
