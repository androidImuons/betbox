package com.lotus.model.request_model;

import com.medy.retrofitwrapper.WebServiceBaseRequestModel;

public class TransferRequestModel extends WebServiceBaseRequestModel {

    public long user_id;
    public String from_date;
    public String to_date;
    public long user_type;
    public int type;
}
