package com.lotus.model.request_model;

import com.medy.retrofitwrapper.RequestJSON;
import com.medy.retrofitwrapper.WebServiceBaseRequestModel;

import java.util.List;

@RequestJSON
public class StackRequestModel extends WebServiceBaseRequestModel {

    public List<String> one_click_stake;
}
