package com.lotus.rest;

import com.lotus.ui.MyApplication;
import com.utilities.Utils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author Manish Kumar
 * @since 10/10/18
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept (Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            Utils.printLog(MyApplication.getInstance(), "ReceivedCookiesInterceptor", cookies.toString());
        }
        return originalResponse;
    }
}
