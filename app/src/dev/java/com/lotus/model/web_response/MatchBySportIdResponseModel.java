package com.lotus.model.web_response;

import com.lotus.model.InplayModel;
import com.lotus.model.SeriesListModel;

import java.util.List;

public class MatchBySportIdResponseModel {

    List<InplayModel> inPlay;
    List<SeriesListModel> series;

    public List<InplayModel> getInPlay() {
        return inPlay;
    }

    public void setInPlay(List<InplayModel> inPlay) {
        this.inPlay = inPlay;
    }

    public List<SeriesListModel> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesListModel> series) {
        this.series = series;
    }
}
