package com.lotus.model.web_response;

import com.lotus.appBase.AppBaseWebServiceBaseResponseModel;
import com.lotus.model.AccountStatementModel;

import java.util.List;

public class AccountStatementResponseModel extends AppBaseWebServiceBaseResponseModel {

    private String tot_credit;
    private String tot_debit;
    private String tot_balance;
    private int count;
    private List<AccountStatementModel> data;

    public double getTot_credit() {
        return Double.parseDouble(getValidStringForBal(tot_credit));
    }

    public void setTot_credit(String tot_credit) {
        this.tot_credit = tot_credit;
    }

    public double getTot_debit() {
        return Double.parseDouble(getValidStringForBal(tot_debit));
    }

    public void setTot_debit(String tot_debit) {
        this.tot_debit = tot_debit;
    }

    public double getTot_balance() {
        return Double.parseDouble(getValidStringForBal(tot_balance));
    }

    public void setTot_balance(String tot_balance) {
        this.tot_balance = tot_balance;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AccountStatementModel> getAccountList() {
        return data;
    }

    public void setAccountList(List<AccountStatementModel> data) {
        this.data = data;
    }

    public double getCurrentPageTotalCredit() {
        if (data==null)return 0.0;

        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double p_l = Double.parseDouble(data.get(i).getCredit());
            total = total + p_l;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }

    public double getCurrentPageTotalDebit() {
        if (data==null)return 0.0;

        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double profit = Double.parseDouble(data.get(i).getDebit());
            total = total + profit;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }

    public double getCurrentPageTotalBalance() {
        if (data==null)return 0.0;

        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            double liability = Double.parseDouble(data.get(i).getBalance());
            total = total + liability;
        }
        return Double.parseDouble(getValidDecimalFormat(total));
    }
}
