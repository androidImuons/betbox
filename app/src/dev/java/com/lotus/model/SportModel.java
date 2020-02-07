package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

public class SportModel extends BaseModel {

    private String sportname;
    private long SportId;
    private List<MatchDetailModel> values;

    public String getSportname() {
        return sportname;
    }

    public void setSportname(String sportname) {
        this.sportname = sportname;
    }

    public long getSportId() {
        return SportId;
    }

    public void setSportId(long sportId) {
        SportId = sportId;
    }

    public List<MatchDetailModel> getValues() {
        return values;
    }

    public void setValues(List<MatchDetailModel> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof SportModel) {
                return ((SportModel) obj).getSportId() == getSportId();
            }

        }
        return false;
    }

}
