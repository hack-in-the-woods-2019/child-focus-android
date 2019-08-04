package com.example.childfocus.utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.entity.BasicHttpEntity;
import cz.msebera.android.httpclient.entity.HttpEntityWrapper;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

public class HttpUtils {

    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "http://10.20.163.47:8080/";

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Header[] headers = new Header[] {new BasicHeader("Authorization", token)};
        client.get(null, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, String token, Object payLoad, AsyncHttpResponseHandler responseHandler) {
        try {
            Header[] headers = new Header[] {new BasicHeader("Authorization", token)};
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(payLoad));
            client.post(null, getAbsoluteUrl(url), headers, entity, APPLICATION_JSON_CHARSET_UTF_8, responseHandler);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("The pay load couldn't be serialized to JSON", e);
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static AsyncHttpResponseHandler dumbResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = responseBody.length > 0 ? new String(responseBody) : "Success";
                Log.d("SUBSCRIBE_MISSIONS", response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = responseBody.length > 0 ? new String(responseBody) : "Failure";
                 Log.d("SUBSCRIBE_MISSIONS", response);
            }
        };
    }
}
