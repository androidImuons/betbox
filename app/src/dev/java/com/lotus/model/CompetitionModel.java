package com.lotus.model;

import com.models.BaseModel;

public class CompetitionModel extends BaseModel {

    private String name;
    private long id;

    public String getName() {
        return getValidString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
