package com.cafe24.app.discount.api;

import com.cafe24.app.discount.api.request.Cafe24Request;
import com.cafe24.app.discount.utils.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpClient {
    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * Http 호출
     *
     * @param request
     * @return
     */
    protected String excute(Cafe24Request request) {
        log.info("HttpClient excute method : {}, url : {}", request.method(), request.url());

        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;
        BufferedReader br = null;

        try {
            byte[] postDataBytes;

            if (request.headers().containsKey("Content-Type") && request.headers().get("Content-Type").toString().indexOf("json") > 0) {
                postDataBytes = Commons.objectToJsonConvert(request.bodys()).getBytes("UTF-8");
            } else {
                postDataBytes = Commons.makeQueryString(request.bodys()).getBytes("UTF-8");
            }

            URL url = new URL(request.url());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(request.method().name());

            for (Map.Entry<String, Object> header : request.headers().entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue().toString());
            }

            if (HttpMethod.POST.equals(request.method())) {
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);
            }
            log.info("HttpClient conn ResponseCode : {}, msg : {}", conn.getResponseCode(), conn.getResponseMessage());

            HttpStatus httpStatus = HttpStatus.valueOf(conn.getResponseCode());
            if (httpStatus.is2xxSuccessful()) {    // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {    // 에러 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            log.info("HttpClient excute result : {}", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {br.close();} catch (IOException e) {e.printStackTrace();}
            conn.disconnect();
        }

        return sb.toString();
    }
}
