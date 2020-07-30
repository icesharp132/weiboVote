package cn.hui.vote.integration.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * httputil
 */
public class OkHttpUtils {
    private static final Logger LOGGER          = LoggerFactory.getLogger(OkHttpUtils.class);
    //读超时
    private static final int       READ_TIMEOUT    = 1000;
    //连接超时
    private static final int       CONNECT_TIMEOUT = 1000;
    private static final MediaType JSON_TYPE       = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM            = MediaType.parse("application/x-www-form-urlencoded");
    private static OkHttpClient    client;

    static {
        List<Protocol> protocol = Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2, Protocol.SPDY_3);
        client = new OkHttpClient();
        client = client.newBuilder().protocols(protocol).readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS).build();
    }

    public static String get(String url, Map<String, Object> params) {
        String newUrl = buildUrl(url, params);
        Request request = new Request.Builder().addHeader("Connection", "Keep-Alive").url(newUrl).build();
        return executeHttpRequest(url, request);
    }

    private static String executeHttpRequest(String url, Request request) {
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            LOGGER.info("http request, request:{}, response:{}", request.toString(), responseBody);
            return responseBody;
        } catch (IOException e) {
            LOGGER.info("http request fail, request:{}", request.toString(), e);
            throw new RuntimeException("error post data to " + url, e);
        }
    }

    public static String post(String url, Map<String, Object> params, String data) {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, data);
        Request request = new Request.Builder().addHeader("Connection", "Keep-Alive").url(url).post(requestBody)
            .build();
        String newUrl = buildUrl(url, params);
        String body = executeHttpRequest(newUrl, request);
        return body;
    }

    public static String postJson(String url, Map<String, Object> paramMap) {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(paramMap));
        Request request = new Request.Builder().addHeader("Connection", "Keep-Alive").url(url).post(requestBody)
            .build();
        String newUrl = buildUrl(url, paramMap);
        String body = executeHttpRequest(newUrl, request);
        LOGGER.info("Post json url: " + url + " ,result: " + body);
        return body;
    }

    public static String postForm(String url, Map<String, Object> params) {
        String postContent = buildPostContent(params);
        RequestBody requestBody = RequestBody.create(FORM, postContent);
        LOGGER.info("Post Form url: " + url + " Post Content: " + postContent);
        Request request = new Request.Builder().addHeader("Connection", "Keep-Alive").url(url).post(requestBody)
            .build();
        String newUrl = buildUrl(url, params);
        String body = executeHttpRequest(newUrl, request);
        return body;
    }

    private static String buildPostContent(Map<String, Object> params) {
        if (params != null) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, Object> e : params.entrySet()) {
                builder.append(e.getKey()).append('=').append(e.getValue()).append('&');
            }
            return builder.toString();
        }
        return null;
    }

    public static String buildUrl(String url, Map<String, Object> params) {
        if (params != null) {
            StringBuilder builder = new StringBuilder(url).append('?');
            for (Map.Entry<String, Object> e : params.entrySet()) {
                builder.append(e.getKey()).append('=').append(e.getValue()).append('&');
            }
            url = builder.toString();
        }
        return url;
    }

}
