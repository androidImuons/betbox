package com.lotus.model;

import com.models.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MatchDetailModel extends BaseModel {
    private String matchName;
    private String series_name;
    private String matchid;
    private String matchdate;
    private String sportname;
    private long SportId;
    private String sport_image;
    private String socket_url;
    private String result;
    private boolean inPlay = false;

    List<MatchMarketModel> markets;
    List<IndianSessionModel> sessions;

    private List<BetDataModel.BetUserData> matchedBet;
    private List<BetDataModel.BetUserData> unMatchedBet;
    private List<BetDataModel.BetUserData> fancyMatchedBet;

    List<MatchVolumeModel> volumeLimit;
    List<SelectionModel> selection;


    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof MatchDetailModel) {
                return ((MatchDetailModel) obj).getMatchid().equals(getMatchid());
            }
        }
        return false;
    }

    public boolean isInPlay() {
        return inPlay;
    }

    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
    }

    public String getMatchName() {
        return getValidString(matchName);
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getSeries_name() {
        return getValidString(series_name);
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getMatchid() {
        return getValidString(matchid);
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }


    public void setMatchdate(String matchdate) {
        this.matchdate = matchdate;
    }

    public String getSportname() {
        return getValidString(sportname);
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

    public String getSport_image() {
        return getValidString(sport_image);
    }

    public void setSport_image(String sport_image) {
        this.sport_image = sport_image;
    }

    public String getSocket_url() {
        return getValidString(socket_url);
    }

    public void setSocket_url(String socket_url) {
        this.socket_url = socket_url;
    }


    public String getResult() {
        return getValidString(result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<MatchMarketModel> getMarkets() {
        return markets;
    }

    public void setMarkets(List<MatchMarketModel> markets) {
        this.markets = markets;
    }

    public List<IndianSessionModel> getSessions() {
        return sessions;
    }

    public void setSessions(List<IndianSessionModel> sessions) {
        this.sessions = sessions;
    }

    public List<BetDataModel.BetUserData> getMatchedBet() {
        return matchedBet;
    }

    public void setMatchedBet(List<BetDataModel.BetUserData> matchedBet) {
        this.matchedBet = matchedBet;
    }

    public List<BetDataModel.BetUserData> getUnMatchedBet() {
        return unMatchedBet;
    }

    public void setUnMatchedBet(List<BetDataModel.BetUserData> unMatchedBet) {
        this.unMatchedBet = unMatchedBet;
    }

    public List<BetDataModel.BetUserData> getFancyMatchedBet() {
        return fancyMatchedBet;
    }

    public void setFancyMatchedBet(List<BetDataModel.BetUserData> fancyMatchedBet) {
        this.fancyMatchedBet = fancyMatchedBet;
    }

    public List<MatchVolumeModel> getVolumeLimit() {
        return volumeLimit;
    }

    public void setVolumeLimit(List<MatchVolumeModel> volumeLimit) {
        this.volumeLimit = volumeLimit;
    }

    public List<SelectionModel> getSelection() {
        return selection;
    }

    public void setSelection(List<SelectionModel> selection) {
        this.selection = selection;
    }

    public String getServerMatchDate() {
        String date= getValidString(matchdate);
        if(date.equalsIgnoreCase(""))
        return date;
        if(!date.endsWith(".000Z") && !date.endsWith("+0000")){
            date=date.substring(0, date.length() - 1)+".000Z";
        }
        return date;
    }

    public String getMatchdate() {
        String matchDate = getServerMatchDate();
        if (isValidString(matchDate)) {
            if (matchDate.endsWith("Z")) {
                matchDate = matchDate.substring(0, matchDate.length() - 1) + "+0000";
            }
        }
        return getValidString(matchDate);
    }

    public String getFormattedMatchdate() {
        try {
            String SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
            Date date = new SimpleDateFormat(SERVER_PATTERN).parse(getMatchdate());
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            //instance.setTimeZone(Calendar.getInstance().getTimeZone());
            return getFormattedCalendar(DATE_TIME_ONE, instance.getTimeInMillis() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getFormattedMatchdate2() {
        try {
            String SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
            Date date = new SimpleDateFormat(SERVER_PATTERN).parse(getMatchdate());
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            //instance.setTimeZone(Calendar.getInstance().getTimeZone());
            return getFormattedCalendar(DATE_TIME_TWO, instance.getTimeInMillis() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Calendar getDateCalendar() {
        Calendar instance = Calendar.getInstance();
        try {
            String SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
            Date date = new SimpleDateFormat(SERVER_PATTERN).parse(getMatchdate());
            instance.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return instance;
    }


    public List<IndianSessionModel> updateIndianSessions(MatchBetFairSessionModel matchBetFairSessionModel) {
        List<IndianSessionModel> oldDataList = null;
        if (sessions != null) {
            oldDataList = new ArrayList<>();
            for (IndianSessionModel session : sessions) {
                oldDataList.add(session.copyOf(session));
            }
        }
        if (matchBetFairSessionModel != null) {
            List<String> activeSelectionIds = new ArrayList<>();
            List<MatchBetFairSessionModel.ValueBean.SessionBean> newDataList = matchBetFairSessionModel.getValue().getSession();
            if (newDataList != null) {
                for (int i = 0; i < newDataList.size(); i++) {
                    MatchBetFairSessionModel.ValueBean.SessionBean newData = newDataList.get(i);
                    activeSelectionIds.add(newData.getSelectionId());
                    if (oldDataList != null) {
                        for (IndianSessionModel oldData : oldDataList) {
                            if (oldData.getInd_fancy_selection_id().equals(newData.getSelectionId())) {
                                if (oldData.isIndianFancy() && oldData.isAutoMaticFancy()) {
                                    oldData.setSessInptYes(newData.getBackPrice1());
                                    oldData.setSessInptNo(newData.getLayPrice1());
                                    oldData.setYesValume(newData.getBackSize1());
                                    oldData.setNoValume(newData.getLaySize1());
                                    if (isValidString(newData.getGameStatus())) {
                                        oldData.setActive(4);
                                        oldData.setDisplayMsg(newData.getGameStatus());
                                    } else {
                                        oldData.setActive(1);
                                        oldData.setDisplayMsg("");

                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if (oldDataList != null) {
                for (IndianSessionModel indianSessionModel : oldDataList) {
                    if (indianSessionModel.isIndianFancy() && indianSessionModel.isAutoMaticFancy()) {
                        int index = activeSelectionIds.indexOf(indianSessionModel.getInd_fancy_selection_id());
                        if (index == -1) {
                            indianSessionModel.setActive(4);
                            indianSessionModel.setDisplayMsg("Result Awaiting");
                        }
                    }
                }
            }
        }
        return oldDataList;


    }

    public boolean checkContentSame(MatchDetailModel newData) {
        if (getMarkets() != null && getMarkets().size() > 0) {
            if (!this.getMarkets().get(0).getIs_favourite().equals(newData.getMarkets().get(0).getIs_favourite())) {
                return false;
            }
            if (this.getMarkets().get(0).getVisibility() != newData.getMarkets().get(0).getVisibility()) {
                return false;

            }
            return true;
        } else {
            return false;
        }

    }

    public boolean checkContentSame2(MatchDetailModel newData) {
        if (getMarkets() != null && getMarkets().size() > 0) {
            if (this.getMarkets().get(0).isMatchDisable() != newData.getMarkets().get(0).isMatchDisable()) {
                return false;
            }
            if (this.getMarkets().get(0).isInPlay() != newData.getMarkets().get(0).isInPlay()) {
                return false;
            }
            if (!this.getMarkets().get(0).getStatus().equals(newData.getMarkets().get(0).getStatus())) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    public MatchDetailModel copyOf(MatchDetailModel matchDetailModel) {

        MatchDetailModel copyData = new MatchDetailModel();

        copyData.matchName = matchDetailModel.getMatchName();
        copyData.series_name = matchDetailModel.getSeries_name();
        copyData.matchid = matchDetailModel.getMatchid();
        copyData.matchdate = matchDetailModel.getServerMatchDate();
        copyData.sportname = matchDetailModel.getSportname();
        copyData.SportId = matchDetailModel.getSportId();
        copyData.sport_image = matchDetailModel.getSport_image();
        copyData.socket_url = matchDetailModel.getSocket_url();
        copyData.result = matchDetailModel.getResult();
        copyData.inPlay = matchDetailModel.isInPlay();
        List<MatchMarketModel> copyMarkets = new ArrayList<>();
        for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
            copyMarkets.add(matchMarketModel.copyOf(matchMarketModel));
        }
        copyData.setMarkets(copyMarkets);
        copyData.setSessions(matchDetailModel.getSessions());

        copyData.setMatchedBet(matchDetailModel.getMatchedBet());
        copyData.setUnMatchedBet(matchDetailModel.getUnMatchedBet());
        copyData.setFancyMatchedBet(matchDetailModel.getFancyMatchedBet());
        copyData.setVolumeLimit(matchDetailModel.getVolumeLimit());
        copyData.setSelection(matchDetailModel.getSelection());

        return copyData;
    }

    public String getAllMarketIds() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getMarkets() != null) {
            for (MatchMarketModel matchMarketModel : getMarkets()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(matchMarketModel.getMarketid());
            }

        }
        return stringBuilder.toString();
    }

    public String getUniqueIdForFav() {
        return getMatchid() + "_" + getMarkets().get(0).getMarketid();
    }

    public String getEmptySelectionsIds() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getMarkets() != null && getMarkets().size() > 0) {
            for (MatchMarketModel matchMarketModel : getMarkets()) {
                if (matchMarketModel.getRunners() != null && matchMarketModel.getRunners().size() > 0) {
                    for (RunnersModel runnersModel : matchMarketModel.getRunners()) {
                        if (!runnersModel.isValidString(runnersModel.getRunnerName())) {
                            if (stringBuilder.length() > 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(matchMarketModel.getMarketid()).append("-").append(runnersModel.getSelectionId());
                        }
                    }

                }
            }
        }
        return stringBuilder.toString();
    }
}
