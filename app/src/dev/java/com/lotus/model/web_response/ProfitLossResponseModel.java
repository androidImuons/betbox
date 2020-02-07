package com.lotus.model.web_response;

import com.lotus.appBase.AppBaseWebServiceBaseResponseModel;
import com.lotus.model.ProfitLossModel;

import java.util.List;

public class ProfitLossResponseModel extends AppBaseWebServiceBaseResponseModel {


    private String tot_PnL;
    private String tot_Comm;
    private int count;
    private List<ProfitLossModel> data;

    public double getTot_PnL() {
        return Double.parseDouble(getValidStringForBal(tot_PnL));
    }

    public void setTot_PnL(String tot_PnL) {
        this.tot_PnL = tot_PnL;
    }

    public double getTot_Comm() {
        return Double.parseDouble(getValidStringForBal(tot_Comm));
    }

    public void setTot_Comm(String tot_Comm) {
        this.tot_Comm = tot_Comm;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProfitLossModel> getPLList() {
        return data;
    }

    public void setPLList(List<ProfitLossModel> data) {
        this.data = data;
    }

    public double getCurrentPageTotalP_L() {
        if (data==null)return 0.0;

        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double p_l = Double.parseDouble(data.get(i).getPnL());
            total = total + p_l;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }

    public double getCurrentPageCommission() {
        if (data==null)return 0.0;

        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double profit = Double.parseDouble(data.get(i).getComm());
            total = total + profit;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }
}
