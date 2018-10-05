package com.cafe24.app.discount.api;

import com.cafe24.app.discount.api.request.Cafe24Request;
import com.cafe24.app.discount.utils.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpClient {
    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * SSL 인증우회
     */
    private TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }
    };

    /**
     * Http 호출
     *
     * @param request
     * @return
     */
    protected String excute(Cafe24Request request) {
        log.info("HttpClient excute method : {}", request.method());
        log.info("HttpClient excute url : {}", request.url());

        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;

        try {
            byte[] postDataBytes;

            if (request.headers().containsKey("Content-Type") && request.headers().get("Content-Type").toString().indexOf("json") > 0) {
                postDataBytes = Commons.objectToJsonConvert(request.bodys()).getBytes("UTF-8");
            } else {
                postDataBytes = Commons.makeQueryString(request.bodys()).getBytes("UTF-8");
            }

            //SSL 인증우회
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, this.trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

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

            log.info("HttpClient comm msg : {}", conn.getResponseMessage());

            try (InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
                 BufferedReader br = new BufferedReader(in)) {

                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                log.info("HttpClient excute result : {}", sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return sb.toString();
    }

}
