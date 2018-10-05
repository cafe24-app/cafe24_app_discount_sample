package com.cafe24.app.discount.api.request;

import com.cafe24.app.discount.api.HttpClient;
import com.cafe24.app.discount.utils.JSONFormatter;
import org.springframework.http.HttpMethod;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class Cafe24RequestBase<T> extends HttpClient implements Cafe24Request {

    private static final String API_BASE_URL = "https://{{mall_id}}.cafe24api.com";
    private HttpMethod method;
    private String path;
    private String host;
    private Map<String, Object> headers;
    private Map<String, Object> bodys;
    private transient T klazz;

    public Cafe24RequestBase(String mall_id) {
        this.host = this.API_BASE_URL.replace("{{mall_id}}", mall_id);
        this.headers = new HashMap<>();
        this.bodys = new HashMap<>();

        try {
            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            klazz = (T) Class.forName(clazz.getTypeName()).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    /**
     * http method 조회
     *
     * @return
     */
    @Override
    public HttpMethod method() {
        return this.method;
    }

    /**
     * url 조회
     *
     * @return
     */
    @Override
    public String url() {
        return this.host + this.path;
    }

    /**
     * http header 조회
     *
     * @return
     */
    @Override
    public Map<String, Object> headers() {
        return this.headers;
    }

    /**
     * http body 조회
     *
     * @return
     */
    @Override
    public Map<String, Object> bodys() {
        return this.bodys;
    }

    /**
     * http body 추가
     *
     * @param key
     * @param value
     */
    public void addBody(String key, Object value) {
        this.bodys.put(key, value);
    }

    /**
     * http header 조회
     *
     * @param key
     * @param value
     */
    public void addHeader(String key, Object value) {
        this.headers.put(key, value);
    }

    /**
     * Cafe24 api 호출
     *
     * @return
     */
    public T apiCall() {
        String result = excute(this);

        //제네릭 타입으로 결과 변환
        return (T) JSONFormatter.fromJSON(result, klazz.getClass());
    }

    /**
     * http method 세팅
     *
     * @param method
     */
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    /**
     * api endpoint 추가
     *
     * @param path
     */
    public void addPath(String path) {
        this.path += "/" + path;
    }

    /**
     * api endpoint 세팅
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

}
