package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

public class StackModel extends BaseModel {


    private List<String> match_stake;
    private List<String> one_click_stake;

    public List<String> getMatch_stake() {
        return match_stake;
    }

    public void setMatch_stake(List<String> match_stake) {
        this.match_stake = match_stake;
    }

    public List<String> getOne_click_stake() {
        return one_click_stake;
    }

    public void setOne_click_stake(List<String> one_click_stake) {
        this.one_click_stake = one_click_stake;
    }
}
