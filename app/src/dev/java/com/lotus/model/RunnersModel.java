package com.lotus.model;

import com.models.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunil kumar Yadav
 * @Since 23/5/18
 */
public class RunnersModel extends BaseModel {

    private String id;
    private String name;
    private String winValue;
    private String lossValue;
    List<AvailableToBackModel> back;
    List<AvailableToLayModel> lay;


    public String getSelectionId() {
        return getValidString(id);
    }

    public void setSelectionId(String selectionId) {
        this.id = selectionId;
    }

    public String getRunnerName() {
        return getValidString(name);
    }

    public void setRunnerName(String runnerName) {
        this.name = runnerName;
    }


    public String getWinValue() {
        String value = getValidString(winValue);
        return value.isEmpty() ? "0.0" : value;
    }

    public void setWinValue(String winValue) {
        this.winValue = winValue;
    }

    public String getLossValue() {
        String value = getValidString(lossValue);
        return value.isEmpty() ? "0.0" : value;
    }

    public void setLossValue(String lossValue) {
        this.lossValue = lossValue;
    }

    public List<AvailableToBackModel> getBack() {
        return back;
    }

    public void setBack(List<AvailableToBackModel> back) {
        this.back = back;
    }

    public List<AvailableToLayModel> getLay() {
        return lay;
    }

    public void setLay(List<AvailableToLayModel> lay) {
        this.lay = lay;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof RunnersModel)
                return ((RunnersModel) obj).getSelectionId().equals(getSelectionId());
        }
        return false;
    }


    public double getWinLossValue() {
        double win = 0.0;
        double loss = 0.0;
        try {
            win = Double.parseDouble(getWinValue());
        } catch (NumberFormatException e) {

        }
        try {
            loss = Double.parseDouble(getLossValue());
        } catch (NumberFormatException e) {

        }
        return win + loss;
    }


    public AvailableToBackModel getBackOdds() {
        if (getBack() == null || getBack().size() == 0) return null;
        return back.get(0);
    }

    public AvailableToLayModel getLayOdds() {
        if (getLay() == null || getLay().size() == 0) return null;
        return lay.get(0);
    }

    public boolean checkContentSame(RunnersModel runnersModel) {
        if (!getSelectionId().equals(runnersModel.getSelectionId())) {
            return false;
        }

        if (!getRunnerName().equals(runnersModel.getRunnerName())) {
            return false;
        }


        if (!getWinValue().equals(runnersModel.getWinValue())) {
            return false;
        }
        if (!getLossValue().equals(runnersModel.getLossValue())) {
            return false;
        }

        if ((this.getBackOdds() == null && runnersModel.getBackOdds() != null) ||
                this.getBackOdds() != null && runnersModel.getBackOdds() == null) {
            return false;
        }

        if ((this.getLayOdds() == null && runnersModel.getLayOdds() != null) ||
                this.getLayOdds() != null && runnersModel.getLayOdds() == null) {
            return false;
        }


        if (this.getBackOdds() != null && !this.getBackOdds().checkContentSame(runnersModel.getBackOdds())) {
            return false;
        }

        if (this.getLayOdds() != null && !this.getLayOdds().checkContentSame(runnersModel.getLayOdds())) {
            return false;
        }

        return true;
    }

    public RunnersModel copyOf(RunnersModel newData) {
        RunnersModel runnersModel = new RunnersModel();
        runnersModel.setSelectionId(newData.getSelectionId());
        runnersModel.setRunnerName(newData.getRunnerName());
        runnersModel.setWinValue(newData.getWinValue());
        runnersModel.setLossValue(newData.getLossValue());
        List<AvailableToBackModel> availableToBackModelList = new ArrayList<>();
        List<AvailableToLayModel> availableToLayModelList = new ArrayList<>();
        if (newData.getBack() != null) {
            for (AvailableToBackModel availableToBackModel : newData.getBack()) {
                availableToBackModelList.add(availableToBackModel.copyOf(availableToBackModel));
            }
        }

        if (newData.getLay() != null) {
            for (AvailableToLayModel availableToLayModel : newData.getLay()) {
                availableToLayModelList.add(availableToLayModel.copyOf(availableToLayModel));
            }
        }
        runnersModel.setBack(availableToBackModelList);
        runnersModel.setLay(availableToLayModelList);
        return runnersModel;
    }
}
