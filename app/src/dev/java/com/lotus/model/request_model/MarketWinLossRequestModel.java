package com.lotus.model.request_model;

import com.medy.retrofitwrapper.RequestJSON;

/**
 * @author Sunil kumar Yadav
 * @Since 17/5/18
 */

@RequestJSON
public class MarketWinLossRequestModel extends AppBaseRequestModel {

    public String matchId;
    public String MarketId;
}
