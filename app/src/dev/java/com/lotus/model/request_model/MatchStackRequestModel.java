package com.lotus.model.request_model;

import com.medy.retrofitwrapper.RequestJSON;
import com.medy.retrofitwrapper.WebServiceBaseRequestModel;

import java.util.List;

@RequestJSON
public class MatchStackRequestModel extends WebServiceBaseRequestModel {

    public List<String> match_stake;
}
