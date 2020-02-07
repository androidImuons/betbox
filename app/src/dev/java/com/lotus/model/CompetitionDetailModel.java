package com.lotus.model;

import com.models.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CompetitionDetailModel extends BaseModel {

    private String date;
    private List<MatchData> match;

    public String getDate() {
        return getValidString(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MatchData> getMatch() {
        return match;
    }

    public void setMatch(List<MatchData> match) {
        this.match = match;
    }

    public static class MatchData extends BaseModel {
        /**
         * volumeLimit : 1.00
         * oddsLimit : 0
         * matchName : Kabul Zwanan v Kandahar Knights
         * countryCode :
         * marketCount : 0
         * MstDate : 2018-10-11T12:00:00.000Z
         * name : Cricket
         * active : 1
         * SportID : 4
         * MstCode : 28946061
         * date : 11-10-2018
         */

        private String volumeLimit;
        private String oddsLimit;
        private String matchName;
        private String countryCode;
        private String marketCount;
        private String MstDate;
        private String name;
        private String active;
        private long SportID;
        private String MstCode;
        private String date;

        public String getVolumeLimit() {
            return getValidString(volumeLimit);
        }

        public void setVolumeLimit(String volumeLimit) {
            this.volumeLimit = volumeLimit;
        }

        public String getOddsLimit() {
            return getValidString(oddsLimit);
        }

        public void setOddsLimit(String oddsLimit) {
            this.oddsLimit = oddsLimit;
        }

        public String getMatchName() {
            return getValidString(matchName);
        }

        public void setMatchName(String matchName) {
            this.matchName = matchName;
        }

        public String getCountryCode() {
            return getValidString(countryCode);
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getMarketCount() {
            return getValidString(marketCount);
        }

        public void setMarketCount(String marketCount) {
            this.marketCount = marketCount;
        }

        public String getMstDate() {
            if (isValidString(MstDate)) {
                if (MstDate.endsWith("Z")) {
                    MstDate = MstDate.substring(0, MstDate.length() - 1) + "+0000";
                }
            }
            return getValidString(MstDate);
        }

        public void setMstDate(String MstDate) {
            this.MstDate = MstDate;
        }

        public String getName() {
            return getValidString(name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getActive() {
            return getValidString(active);
        }

        public void setActive(String active) {
            this.active = active;
        }

        public long getSportID() {
            return (SportID);
        }

        public void setSportID(long SportID) {
            this.SportID = SportID;
        }

        public String getMstCode() {
            return getValidString(MstCode);
        }

        public void setMstCode(String MstCode) {
            this.MstCode = MstCode;
        }

        public String getDate() {
            return getValidString(date);
        }

        public void setDate(String date) {
            this.date = date;
        }


        public String getFormattedMatchdate() {
            try {
                String SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
                Date date = new SimpleDateFormat(SERVER_PATTERN).parse(getMstDate());
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                //instance.setTimeZone(Calendar.getInstance().getTimeZone());
                return getFormattedCalendar(DATE_TIME_ONE, instance.getTimeInMillis() / 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        }


        public Calendar getDateCalendar() {
            Calendar instance = Calendar.getInstance();
            try {
                String SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
                Date date = new SimpleDateFormat(SERVER_PATTERN).parse(getMstDate());
                instance.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return instance;
        }


        public MatchDetailModel getMatchListModel() {
            MatchDetailModel matchDetailModel = new MatchDetailModel();
            matchDetailModel.setMatchid(getMstCode());
            matchDetailModel.setMatchName(getMatchName());
            matchDetailModel.setSportId(getSportID());
            matchDetailModel.setMatchdate(getMstDate());
            return matchDetailModel;
        }
    }

}
