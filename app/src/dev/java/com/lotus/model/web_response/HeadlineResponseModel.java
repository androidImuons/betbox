package com.lotus.model.web_response;

import com.lotus.model.HeadlineModel;

import java.util.List;

public class HeadlineResponseModel extends BaseWebServiceModelResponse {

    List<HeadlineModel> marqueMsg;

    public String getHeadlines() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isValidList(marqueMsg)) {
            for (HeadlineModel headlineModel : marqueMsg) {
                if (isValidString(headlineModel.getMarquee())) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(" | ");
                    }
                    stringBuilder.append(headlineModel.getMarquee());
                }
            }
        }
        return stringBuilder.toString().trim();
    }
}
