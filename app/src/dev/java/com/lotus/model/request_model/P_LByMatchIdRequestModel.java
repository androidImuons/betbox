package com.lotus.model.request_model;

import com.medy.retrofitwrapper.RequestJSON;
import com.medy.retrofitwrapper.WebServiceBaseRequestModel;

@RequestJSON
public class P_LByMatchIdRequestModel extends WebServiceBaseRequestModel {

    public long user_id;
    public String match_id;
}
