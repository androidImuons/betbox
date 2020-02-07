package com.lotus.model.request_model;

import com.medy.retrofitwrapper.RequestJSON;
import com.medy.retrofitwrapper.WebServiceBaseRequestModel;

@RequestJSON
public class HistoryRequestModel extends WebServiceBaseRequestModel{

    public long user_id;
    public String from_date;
    public String to_date;
    public String from_time;
    public String to_time;
    public int type;
    public int page_no;
    public long sport_id;

}
