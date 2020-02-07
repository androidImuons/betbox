package com.lotus.model;

import com.models.BaseModel;

/**
 * Created by Sunil kumar on 12/10/18.
 */
public class UserBalanceModel extends BaseModel {

    double Liability;
    double Balance;
    double P_L;
    double FreeChip;
    double Chip;
    double sessionLiability;
    double unmatchliability;
    String match_stake;
    String one_click_stake;
    String is_confirm_bet;
    HeadlineModel marqueMsg;

    public double getLiability() {
        return Liability;
    }

    public void setLiability(double liability) {
        Liability = liability;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getLiabilityText() {
        return getValidDecimalFormat(getLiability());
    }

    public String getBalanceText() {
        return getValidDecimalFormat(getBalance());
    }

    public double getP_L() {
        return P_L;
    }

    public void setP_L(double p_L) {
        P_L = p_L;
    }

    public String getP_LText() {
        return getValidDecimalFormat(getP_L());
    }

    public double getFreeChip() {
        return FreeChip;
    }

    public void setFreeChip(double freeChip) {
        FreeChip = freeChip;
    }

    public String getFreeChipText() {
        return getValidDecimalFormat(getFreeChip());
    }

    public double getChip() {
        return Chip;
    }

    public void setChip(double chip) {
        Chip = chip;
    }

    public String getChipText() {
        return getValidDecimalFormat(getChip());
    }

    public double getSessionLiability() {
        return sessionLiability;
    }

    public void setSessionLiability(double sessionLiability) {
        this.sessionLiability = sessionLiability;
    }

    public String getSessionLiabilityText() {
        return getValidDecimalFormat(getSessionLiability());
    }


    public double getUnmatchliability() {
        return unmatchliability;
    }

    public void setUnmatchliability(double unmatchliability) {
        this.unmatchliability = unmatchliability;
    }

    public String getUnmatchliabilityText() {
        return getValidDecimalFormat(getUnmatchliability());
    }


    public String getMatch_stake() {
        return getValidString(match_stake);
    }

    public void setMatch_stake(String match_stake) {
        this.match_stake = match_stake;
    }

    public String getOne_click_stake() {
        return getValidString(one_click_stake);
    }

    public void setOne_click_stake(String one_click_stake) {
        this.one_click_stake = one_click_stake;
    }

    public String getIs_confirm_bet() {
        return getValidString(is_confirm_bet);
    }

    public void setIs_confirm_bet(String is_confirm_bet) {
        this.is_confirm_bet = is_confirm_bet;
    }

    public HeadlineModel getMarqueMsg() {
        return marqueMsg;
    }

    public void setMarqueMsg(HeadlineModel marqueMsg) {
        this.marqueMsg = marqueMsg;
    }

    public String getHeadlines() {
        StringBuilder stringBuilder = new StringBuilder();
        if (marqueMsg != null) {
            if (isValidString(marqueMsg.getMarquee())) {
                stringBuilder.append(marqueMsg.getMarquee());
            }
        }
        return stringBuilder.toString().trim();
    }

    public boolean isConfirmBet() {
        return getIs_confirm_bet().equalsIgnoreCase("Y") ? true : false;
    }

    public boolean needUpdateBalance(UserBalanceModel userBalanceModel) {
        if (getLiability() != userBalanceModel.getLiability()) {
            return true;
        }
        if (getBalance() != userBalanceModel.getBalance()) {
            return true;
        }
        if (getP_L() != userBalanceModel.getP_L()) {
            return true;
        }
        if (getFreeChip() != userBalanceModel.getFreeChip()) {
            return true;
        }
        if (getChip() != userBalanceModel.getChip()) {
            return true;
        }
        if (getSessionLiability() != userBalanceModel.getSessionLiability()) {
            return true;
        }
        if (getUnmatchliability() != userBalanceModel.getUnmatchliability()) {
            return true;
        }
        if (!getMatch_stake().equals(userBalanceModel.getMatch_stake())) {
            return true;
        }
        if (!getOne_click_stake().equals(userBalanceModel.getOne_click_stake())) {
            return true;
        }
        if (!getIs_confirm_bet().equals(userBalanceModel.getIs_confirm_bet())) {
            return true;
        }
        return false;
    }
}
