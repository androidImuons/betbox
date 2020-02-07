package com.lotus.model;

import com.models.BaseModel;

/**
 * @author Manish Kumar
 * @since 14/6/18
 */

public class MatchVolumeModel extends BaseModel {
    String match_id;
    String result;
    float oddsLimit;
    float volumeLimit;

    @Override
    public boolean equals (Object obj) {
        if (obj != null && obj instanceof MatchVolumeModel) {
            return ((MatchVolumeModel) obj).getMatch_id().equals(getMatch_id());
        }

        return false;
    }

    public String getMatch_id () {
        return getValidString(match_id);
    }

    public void setMatch_id (String match_id) {
        this.match_id = match_id;
    }

    public String getResult () {
        return getValidString(result);
    }

    public void setResult (String result) {
        this.result = result;
    }

    public float getOddsLimit () {
        return oddsLimit;
    }

    public void setOddsLimit (float oddsLimit) {
        this.oddsLimit = oddsLimit;
    }

    public float getVolumeLimit () {
        return volumeLimit;
    }

    public void setVolumeLimit (float volumeLimit) {
        this.volumeLimit = volumeLimit;
    }
}
