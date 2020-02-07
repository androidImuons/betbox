package com.lotus.model;

import com.models.BaseModel;

public class SelectionNameModel extends BaseModel {

    String selection_id;
    String runnername;

    public String getSelection_id() {
        return getValidString(selection_id);
    }

    public void setSelection_id(String selection_id) {
        this.selection_id = selection_id;
    }

    public String getRunnername() {
        return getValidString(runnername);
    }

    public void setRunnername(String runnername) {
        this.runnername = runnername;
    }

    public String getActualMarketId() {
        return getSelection_id().split("-")[0];
    }

    public String getActualSelectionId() {
        return getSelection_id().split("-")[1];
    }

}
