package com.lotus.model;

import com.models.BaseModel;

/**
 * @author Sunil kumar Yadav
 * @Since 23/5/18
 */
public class AvailableToBackModel extends BaseModel {

    private String price;
    private String size;
    private MatchVolumeModel matchVolumeModel;

    public void setMatchVolumeModel(MatchVolumeModel matchVolumeModel) {
        this.matchVolumeModel = matchVolumeModel;
    }

    public MatchVolumeModel getMatchVolumeModel() {
        return matchVolumeModel;
    }

    public String getPrice() {
        return price;
    }

    public Double getFormattedPrice() {

        try {
            if (matchVolumeModel != null) {
                float y = matchVolumeModel.getOddsLimit();
                return getValidDecimalFormatDouble(Double.parseDouble(price) + y);
            }
            return getValidDecimalFormatDouble(Double.parseDouble(price));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPriceText() {
        Double formattedPrice = getFormattedPrice();
        if (formattedPrice == null) {
            return getPrice();
        }
        return String.valueOf(formattedPrice);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public String getSizeText() {
        Double formattedSize = getFormattedSize();
        if (formattedSize == null) {
            return getSize();
        }
        return String.valueOf(Math.round(formattedSize));
    }

    public Double getFormattedSize() {
        try {
            if (matchVolumeModel != null) {
                float y = matchVolumeModel.getVolumeLimit();
                return Double.parseDouble(size) * y;
            }
            return Double.parseDouble(size);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public boolean isMatched(double placedPrice) {
        Double formattedPrice = getFormattedPrice();
        if (formattedPrice == null) return false;
        return placedPrice <= formattedPrice;
    }

    public boolean checkContentSame(AvailableToBackModel availableToBackModel) {
        if (!getPrice().equals(availableToBackModel.getPrice())) {
            return false;
        }

        if (!getSize().equals(availableToBackModel.getSize())) {
            return false;
        }

        return true;
    }

    public AvailableToBackModel copyOf(AvailableToBackModel availableToBackModel){
        AvailableToBackModel newData=new AvailableToBackModel();
        newData.setPrice(availableToBackModel.getPrice());
        newData.setSize(availableToBackModel.getSize());
        newData.setMatchVolumeModel(availableToBackModel.getMatchVolumeModel());
        return newData;

    }
}
