package com.lotus.appBase;

import com.lotus.R;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.Locale;

public class AppBaseWebServiceBaseResponseModel extends WebServiceBaseResponseModel {
    public int getTotalPages(int count) {
        return (int) Math.ceil(count / 15d);
    }

    public String getValidStringForBal(String data) {
        return data != null && !data.isEmpty() ? data : "0";
    }

    public int getAmountTextColor(double amount) {
        if (amount > 0) {
            return R.color.color_green;
        } else if (amount < 0)
            return R.color.color_red;
        else
            return R.color.et_text_color;
    }

    public String getValidDecimalFormat(String value) {
        if (!isValidString(value)) {
            return "0.00";
        }
        double netValue = Double.parseDouble(value);
        return getValidDecimalFormat(netValue);
    }

    public String getValidDecimalFormat(double value) {
        return String.format(Locale.ENGLISH, "%.2f", value);
    }

}
