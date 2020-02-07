package com.lotus.ui.main.dialog.search.autofilte;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Manish Kumar
 * @since 28/9/17
 */


/**
 * This class is use for setup auto complete using web service
 */
public abstract class AutoCompleteHelper implements Filterable, WebServiceResponseListener {

    Context context;

    //WebRequest for autocomplete
    WebRequest webRequest;

    //this is call when webRequest is completed successfully.
    ResultAvailableListener resultAvailableListener;

    //use for perform task and return result
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (webRequest != null) {
                webRequest.cancel();
            }
            final FilterResults results = new FilterResults();
            if (constraint != null && !constraint.toString().trim().isEmpty()) {

                webRequest = getWebRequest(constraint.toString());
                onWebRequestCall(webRequest);
                try {
                    Response<ResponseBody> response = webRequest.generateCall(context).execute();
                    int responseCode = -1;
                    HashSet<String> cookies = null;
                    String webRequestResponse = null;
                    if (response != null) {
                        responseCode = response.code();
                        okhttp3.Response raw = response.raw();
                        if (!raw.headers("Set-Cookie").isEmpty()) {
                            cookies = new HashSet<>();
                            for (String header : raw.headers("Set-Cookie")) {
                                cookies.add(header);
                            }
                        }
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                webRequestResponse = response.body().string().trim();
                            } else if (response.errorBody() != null) {
                                webRequestResponse = response.errorBody().string().trim();
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    webRequest.onRequestCompleted(null, responseCode, webRequestResponse, cookies);
                    webRequest.printResponseLog();
                } catch (IOException|WebServiceException e) {
                    e.printStackTrace();
                    webRequest.onRequestCompleted(e, -1, null, null);
                    webRequest.printResponseLog();
                }
                results.values=parseResponse(webRequest);
//                onWebRequestResponse(webRequest);

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values == null || results.values instanceof WebServiceBaseResponseModel) {
                if (getResultAvailableListener() != null) {
                    getResultAvailableListener().onResultAvailable((WebServiceBaseResponseModel) results.values);
                }
            }
        }
    };


    /**
     * @param context
     */
    public AutoCompleteHelper(Context context) {
        this.context = context;
    }

    /**
     * use for get {@link AutoCompleteHelper#resultAvailableListener}
     *
     * @return
     */
    public ResultAvailableListener getResultAvailableListener() {
        return resultAvailableListener;
    }

    /**
     * use for set {@link ResultAvailableListener}
     *
     * @param resultAvailableListener
     */
    public void setResultAvailableListener(ResultAvailableListener resultAvailableListener) {
        this.resultAvailableListener = resultAvailableListener;
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    public abstract WebRequest getWebRequest(String text);
    public abstract WebServiceBaseResponseModel parseResponse (WebRequest webRequest);
    /**
     * ResultAvailableListener
     * use for notify when result is available
     */
    public interface ResultAvailableListener {
        void onResultAvailable(WebServiceBaseResponseModel baseWebServiceModelResponse);
    }

    @Override
    public void onWebRequestCall(WebRequest webRequest) {
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {

    }
}
