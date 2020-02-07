package com.lotus.model.web_response;

import com.lotus.appBase.AppBaseWebServiceBaseResponseModel;
import com.lotus.model.LedgerModel;

import java.util.List;

public class LedgerResponseModel extends AppBaseWebServiceBaseResponseModel {


    private List<LedgerModel> chips_lgr;

    public List<LedgerModel> getChips_lgr() {
        return chips_lgr;
    }

    public void setChips_lgr(List<LedgerModel> chips_lgr) {
        this.chips_lgr = chips_lgr;
    }

    public double getCurrentPageTotalCredit() {
        if (chips_lgr == null) return 0.0;
        double total = 0;
        for (int i = 0; i < chips_lgr.size(); i++) {
            double p_l = chips_lgr.get(i).getCredit();
            total = total + p_l;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }

    public double getCurrentPageTotalDebit() {
        if (chips_lgr == null) return 0.0;

        double total = 0;
        for (int i = 0; i < chips_lgr.size(); i++) {
            double profit = chips_lgr.get(i).getDebit();
            total = total + profit;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }
}
