package com.lotus.ui;


import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.lotus.model.StackModel;
import com.lotus.model.UserBalanceModel;
import com.lotus.model.UserModel;
import com.lotus.preferences.AppPrefs;
import com.lotus.preferences.UserPrefs;
import com.lotus.rest.WebRequestConstants;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebRequestInterface;
import com.medy.retrofitwrapper.WebServiceResponseListener;
import com.models.DeviceInfoModal;

import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sunil kumar yadav on 08/10/18.
 */

public class MyApplication extends MultiDexApplication implements UserPrefs.UserPrefsListener,
        UserPrefs.UserBalanceListener, WebServiceResponseListener, WebRequestConstants, UserPrefs.UserStackListener {

    private static MyApplication instance;
    private AppPrefs appPrefs;
    private UserPrefs userPrefs;
    private UserModel userModel;
    private UserBalanceModel userBalanceModel;
    private HashSet<String> cookies;
    private StackModel stackModel;

    public WebRequestInterface webRequestInterfaceDefault;


    public List<String> favouriteMarketIds;

    public static MyApplication getInstance() {
        return instance;
    }

    public AppPrefs getAppPrefs() {
        return appPrefs;
    }

    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public UserBalanceModel getUserBalanceModel() {
        return userBalanceModel;
    }

    public void updateCookies(HashSet<String> cookies) {
        if (cookies != null) {
            String data = "";
            for (String cookie : cookies) {
                if (cookie.startsWith("ci_session") || cookie.startsWith("PHPSESSID")) {
                    data = cookie.substring(0, cookie.indexOf(";"));
                    break;
                }
            }
            cookies.clear();
            cookies.add(data);

        }
        getUserPrefs().saveCookies(cookies);
        this.cookies = cookies;
    }

    public void updateUserProfile(UserModel userModel) {
        if (userModel != null) {
            getUserPrefs().updateLoggedInUser(userModel);
        }
    }

    public void updateUserBalance(UserBalanceModel userBalanceModel) {
        if (userBalanceModel != null) {
            getUserPrefs().updateLoggedInUserBalance(userBalanceModel);
        }
    }

    public StackModel getUserStack() {
        return stackModel;
    }

    public void updateUserStack(StackModel stackModel) {
        if (stackModel == null) return;
        getUserPrefs().updateLoggedInUserStack(stackModel);
        this.stackModel = stackModel;
    }

    public void clearLoggedInUser() {
        if (getUserPrefs() != null) {
            getUserPrefs().clearLoggedInUser();
        }
    }

    public WebRequestInterface getWebRequestInterfaceDefault() {
        if (webRequestInterfaceDefault == null) {
            OkHttpClient okHttpClient = getUnsafeOkHttpClient();
            webRequestInterfaceDefault = new Retrofit.Builder()
                    .baseUrl("http://www.test.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WebRequestInterface.class);
        }
        return webRequestInterfaceDefault;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseApp.initializeApp(this);
        Fabric.with(this, new Crashlytics());

        appPrefs = new AppPrefs(this);
        userPrefs = new UserPrefs(this);
        userPrefs.addListener(this);
        userPrefs.addBalanceListener(this);
        getUserPrefs().addStackListener(this);
        userModel = userPrefs.getLoggedInUser();
        userBalanceModel = userPrefs.getLoggedInUserBalance();
        cookies = userPrefs.getCookies();
    }


    @Override
    public void userLoggedIn(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void loggedInUserUpdate(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void loggedInUserClear() {
        this.userModel = null;
    }

    @Override
    public void onWebRequestCall(WebRequest webRequest) {
        DeviceInfoModal deviceInfoModal = new DeviceInfoModal(this);
        webRequest.addHeader(HEADER_KEY_DEVICE_TYPE, DEVICE_TYPE_ANDROID);
        webRequest.addHeader(HEADER_KEY_DEVICE_INFO, deviceInfoModal.toString());
        webRequest.addHeader(HEADER_KEY_APP_INFO, deviceInfoModal.getAppInfo());
        if (getUserModel() != null) {
            UserModel user = getUserModel();
            webRequest.setBasicAuth(user.getUser_name(), user.getMstrpassword());
            webRequest.setRequestCookies(cookies);
            webRequest.setWebRequestInterface(getWebRequestInterfaceDefault());
        }
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {

    }

    @Override
    public void userLoggedInBalanceUpdate(UserBalanceModel userBalanceModel) {
        this.userBalanceModel = userBalanceModel;
    }

    @Override
    public void userLoggedInStackUpdate(StackModel stackModel) {
        this.stackModel = stackModel;
    }


    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        okClientBuilder.connectTimeout(1 * 60 * 1000, TimeUnit.MILLISECONDS);
        okClientBuilder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        okClientBuilder.writeTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        return okClientBuilder.build();
    }

    public OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.connectTimeout(1 * 60 * 1000, TimeUnit.MILLISECONDS);
            builder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
            builder.writeTimeout(15 * 1000, TimeUnit.MILLISECONDS);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
