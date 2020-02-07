package com.lotus.model.ui_model;

import com.models.BaseModel;


public class TypeModel extends BaseModel {

    private String name;
    private int id;
    private int sport_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSport_id() {
        return sport_id;
    }

    public void setSport_id(int sport_id) {
        this.sport_id = sport_id;
    }

    @Override
    public String toString() {
        return name;
    }
}
