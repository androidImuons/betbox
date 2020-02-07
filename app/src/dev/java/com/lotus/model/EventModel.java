package com.lotus.model;

import com.models.BaseModel;

public class EventModel extends BaseModel {

    private String name;
    private long id;
    private String openDate;

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

    public String getOpenDate() {
        return getValidString(openDate);
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }
}
