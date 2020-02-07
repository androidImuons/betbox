package com.lotus.model.web_response;

import com.lotus.appBase.AppBaseWebServiceBaseResponseModel;
import com.lotus.model.OnePageReportModel;

import java.util.List;

public class OnePageReportResponseModel extends AppBaseWebServiceBaseResponseModel {


    private String tot_p_l;
    private String tot_profit;
    private String tot_liability;
    private int count;
    private List<OnePageReportModel> data;

    public double getTot_p_l() {
        return Double.parseDouble(getValidStringForBal(tot_p_l));
    }

    public void setTot_p_l(String tot_p_l) {
        this.tot_p_l = tot_p_l;
    }

    public double getTot_profit() {
        return Double.parseDouble(getValidStringForBal(tot_profit));
    }

    public void setTot_profit(String tot_profit) {
        this.tot_profit = tot_profit;
    }

    public double getTot_liability() {
        return Double.parseDouble(getValidStringForBal(tot_liability));
    }

    public void setTot_liability(String tot_liability) {
        this.tot_liability = tot_liability;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OnePageReportModel> getPageReportList() {
        return data;
    }

    public void setPageReportList(List<OnePageReportModel> data) {
        this.data = data;
    }

    public double getCurrentPageTotalP_L() {
        if (data == null) return 0.0;
        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double p_l = Double.parseDouble(data.get(i).getP_L());
            total = total + p_l;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }

    public double getCurrentPageTotalProfit() {
        if (data == null) return 0.0;

        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double profit = Double.parseDouble(data.get(i).getProfit());
            total = total + profit;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }

    public double getCurrentPageTotalLiability() {
        if (data == null) return 0.0;

        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double liability = Double.parseDouble(data.get(i).getLiability());
            total = total + liability;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }


}
