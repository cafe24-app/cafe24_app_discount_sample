package com.cafe24.app.discount.utils;

import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class Commons {
    private static final Logger log = LoggerFactory.getLogger(Commons.class);

    /**
     * 트레이스 번호 생성
     *
     * @return String 20자리 : yyyyMMddHHmmss + 6자리 난수(영문소문자, 영문대문자, 숫자)
     */
    public static String makeTrace_no() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder trace_no = new StringBuilder(f.format(new Date()));
        Random rnd = new Random();

        for (int i = trace_no.length(); i < 20; i++) {
            int rIndex = rnd.nextInt(3);

            switch (rIndex) {
                case 0: // a-z
                    trace_no.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1: // A-Z
                    trace_no.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2: // 0-9
                    trace_no.append((rnd.nextInt(10)));
                    break;
            }
        }

        return trace_no.toString();
    }

    /**
     * 데이터 암호화한 값 작성(MAP->JSON->MAC->BASE64)
     *
     * @param plainText
     * @return String hmac값
     */
    public static String makeHmac(String plainText, String secretKey) {
        String CypHmac = "";

        log.info("makeHmac secretKey : {}", secretKey);

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));
            mac.update(plainText.getBytes("UTF-8"));

            CypHmac = Base64.encodeBase64String(mac.doFinal());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return CypHmac;
    }

    /**
     * 문자열을 MD-5 방식으로 암호화
     *
     * @param str 암호화 하려하는 문자열
     * @return String
     */
    public static String getEncMD5(String str) {
        String MD5;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            MD5 = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            MD5 = null;
        }

        return MD5;
    }

    /**
     * 쿼리 스트링 만들기
     *
     * @param pMap<String, Object> pMap
     * @return String 쿼리스트링으로 변환값
     */
    public static String makeQueryString(Map<String, Object> pMap) {
        StringBuilder postData = new StringBuilder();

        try {
            for (Map.Entry<String, Object> param : pMap.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }

                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        log.info("makeQueryString postData : " + postData.toString());

        return postData.toString();
    }

    /**
     * map -> json으로 변경
     *
     * @return String JSON String
     */
    public static String objectToJsonConvert(Map<String, Object> pMap) {
        String json = new Gson().toJson(pMap);

        log.info("objectToJsonConvert : json " + json);

        return json;
    }

}
