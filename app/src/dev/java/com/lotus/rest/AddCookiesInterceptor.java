package com.lotus.rest;

import com.lotus.ui.MyApplication;
import com.utilities.Utils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Manish Kumar
 * @since 10/10/18
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept (Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = new HashSet<>();
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Utils.printLog(MyApplication.getInstance(), "AddCookiesInterceptor", cookie);
        }
        return chain.proceed(builder.build());
    }
}
