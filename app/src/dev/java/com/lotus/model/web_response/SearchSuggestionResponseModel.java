package com.lotus.model.web_response;

import com.lotus.model.SearchSuggestionModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.List;

/**
 * Created by Azher on 16/11/18.
 */
public class SearchSuggestionResponseModel extends WebServiceBaseResponseModel {

    List<SearchSuggestionModel> data;

    public List<SearchSuggestionModel> getData() {
        return data;
    }

    public void setData(List<SearchSuggestionModel> data) {
        this.data = data;
    }
}
