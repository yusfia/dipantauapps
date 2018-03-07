package id.towercontroller.org.towercontroller.connection;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import id.towercontroller.org.towercontroller.config.Constants;

/**
 * Created by Hafid on 11/28/2017.
 */

public class RestAPIControl {
    public static final String TAG = "[HomecareRestClient]";
    private static AsyncHttpClient client;

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.addHeader("api_key", Constants.api_key);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.addHeader("api_key", Constants.api_key);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.addHeader("api_key", Constants.api_key);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.addHeader("api_key", Constants.api_key);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Constants.BASE_URL + relativeUrl;
    }
}
