package com.lotus.ui.main.dialog.search.autofilte;

import android.content.Context;

import com.lotus.model.request_model.SearchRequestModel;
import com.lotus.model.web_response.SearchSuggestionResponseModel;
import com.lotus.rest.WebRequestConstants;
import com.lotus.ui.MyApplication;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

/**
 * @author Manish Kumar
 * @since 28/9/17
 */


/**
 * This class is use for search menu suggestion
 */
public class SearchAutoComplete extends AutoCompleteHelper implements WebRequestConstants {


    public SearchAutoComplete(Context context) {
        super(context);
    }

    @Override
    public WebRequest getWebRequest(String text) {

        WebRequest webRequest = null;
//        String url = String.format(URL_MATCH_AUTOCOMPLETE, URLEncoder.encode(text, "UTF-8"));
        SearchRequestModel requestModel = new SearchRequestModel();
        requestModel.search = text;
        webRequest = new WebRequest(ID_MATCH_AUTOCOMPLETE, URL_MATCH_AUTOCOMPLETE, WebRequest.POST_REQ, 10 * 1000);
        webRequest.setRequestModel(requestModel);
        webRequest.setProgressMessage("");

        return webRequest;
    }

    /**
     * Parse response from {@link WebRequest} and check Success if success {@link SearchSuggestionResponseModel}
     * return other wise null
     *
     * @param webRequest
     * @return
     */
    @Override
    public WebServiceBaseResponseModel parseResponse(WebRequest webRequest) {
        if (webRequest.checkSuccess()) {
            SearchSuggestionResponseModel searchSuggestionResponseModel = webRequest.getResponsePojo(SearchSuggestionResponseModel.class);
            if (searchSuggestionResponseModel != null) {
                return searchSuggestionResponseModel;
            }
        }
        return null;
    }

    @Override
    public void onWebRequestCall(WebRequest webRequest) {
        ((MyApplication)context.getApplicationContext()).onWebRequestCall(webRequest);
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {

    }
}
