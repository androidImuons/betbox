package com.lotus.model.request_model;

import com.lotus.model.BetSlipModel;
import com.medy.retrofitwrapper.RequestJSON;

import java.util.ArrayList;
import java.util.List;

@RequestJSON
public class BetPlaceRequestModel extends AppBaseRequestModel {

    public String back_lay_ids;
    public List<BetSlipRequestModel> bet_slip;

    public BetSlipRequestModel addBet(BetSlipModel betSlipModel) {
        String selectionId = betSlipModel.isSession() ?
                "s" + betSlipModel.getIndian_fancy_selection_id() : betSlipModel.getSelectionId();
        String backLayId = betSlipModel.getMarketid() + "_" + selectionId + "_" + betSlipModel.getType().toLowerCase();
        if (back_lay_ids == null || back_lay_ids.trim().isEmpty()) {
            back_lay_ids = backLayId;
        } else {
            back_lay_ids += "," + backLayId;
        }

        BetSlipRequestModel betSlipRequestModel = new BetSlipRequestModel(betSlipModel);
        if (bet_slip == null) {
            bet_slip = new ArrayList<>();
        }
        bet_slip.add(betSlipRequestModel);
        return betSlipRequestModel;

    }

}
