package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

public class OddsByMarketIdsModel extends BaseModel {

    private String id;
    private String name;
    private List<RunnersModel> runners;
    private String eventTypeId;
    private boolean inPlay;
    private String status;

    public String getId() {
        return getValidString(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return getValidString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RunnersModel> getRunners() {
        return runners;
    }

    public void setRunners(List<RunnersModel> runners) {
        this.runners = runners;
    }

    public String getEventTypeId() {
        return getValidString(eventTypeId);
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public boolean isInPlay() {
        return inPlay;
    }

    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
    }

    public String getStatus() {
        return getValidString(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
