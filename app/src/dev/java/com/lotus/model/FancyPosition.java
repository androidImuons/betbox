package com.lotus.model;

import com.models.BaseModel;

public class FancyPosition extends BaseModel {


    /**
     * SessInptYes : 0-276
     * ResultValue : -3000
     */

    private String SessInptYes;
    private int ResultValue;

    public String getSessInptYes() {
        return getValidString(SessInptYes);
    }

    public void setSessInptYes(String SessInptYes) {
        this.SessInptYes = SessInptYes;
    }

    public int getResultValue() {
        return ResultValue;
    }

    public void setResultValue(int ResultValue) {
        this.ResultValue = ResultValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        FancyPosition employee = (FancyPosition) obj;

        if (ResultValue != employee.ResultValue) return false;
        if (!getSessInptYes().equalsIgnoreCase(employee.getSessInptYes())) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = ResultValue;
        result = result + (SessInptYes != null ? SessInptYes.hashCode() : 0);
        return result;
    }
}
