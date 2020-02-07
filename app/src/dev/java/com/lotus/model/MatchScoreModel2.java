package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

/**
 * @author Manish Kumar
 * @since 9/6/18
 */

public class MatchScoreModel2 extends BaseModel {
    int eventTypeId;
    String eventId;
    Score score;
    String description;
    String currentSet;
    String currentGame;
    String currentPoint;
    String currentDay;
    String matchType;
    String matchStatus;
    String timeElapsed;

    public int getEventTypeId() {
        return eventTypeId;
    }

    public String getEventId() {
        return getValidString(eventId);
    }

    public Score getScore() {
        return score;
    }

    public String getDescription() {
        return getValidString(description);
    }

    public String getCurrentSet() {
        return getValidString(currentSet);
    }

    public String getCurrentGame() {
        return getValidString(currentGame);
    }

    public String getCurrentPoint() {
        return getValidString(currentPoint);
    }

    public String getCurrentDay() {
        return getValidString(currentDay);
    }

    public String getMatchType() {
        return getValidString(matchType);
    }

    public String getMatchStatus() {
        return getValidString(matchStatus);
    }

    public String getTimeElapsed() {
        return getValidString(timeElapsed);
    }

    public String getGameStatus() {
        if (getEventTypeId() == 1) {
            return getTimeElapsed();
        } else if (getEventTypeId() == 2) {
            return "Set " + getCurrentSet() + " - Game " + getCurrentGame() + " - Point " + getCurrentPoint();
        }
        return "";
    }

    public static class Score extends BaseModel {
        Team home;
        Team away;

        public Team getHome() {
            return home;
        }

        public Team getAway() {
            return away;
        }
    }

    public static class Team extends BaseModel {
        String name;
        String score;
        String halfTimeScore;
        String fullTimeScore;
        String penaltiesScore;
        String games;
        String aces;
        String doubleFaults;
        boolean isServing;
        boolean highlight;
        Inning inning1;
        Inning inning2;
        List<String> gameSequence;

        public String getName() {
            return getValidString(name);
        }

        public String getScore() {
            return getValidString(score);
        }

        public String getAces() {
            return getValidString(aces);
        }

        public String getDoubleFaults() {
            return getValidString(doubleFaults);
        }

        public String getGames() {
            return getValidString(games);
        }

        public boolean isServing() {
            return isServing;
        }

        public String getHalfTimeScore() {
            return getValidString(halfTimeScore);
        }

        public String getFullTimeScore() {
            return getValidString(fullTimeScore);
        }

        public String getPenaltiesScore() {
            return getValidString(penaltiesScore);
        }

        public boolean isHighlight() {
            return highlight;
        }

        public Inning getInning1() {
            return inning1;
        }

        public Inning getInning2() {
            return inning2;
        }

        public List<String> getGameSequence() {
            return gameSequence;
        }

        public String getGameSequenceText() {
            if (gameSequence == null || gameSequence.size() == 0) return "";
            StringBuilder stringBuilder = new StringBuilder();
            for (String data : gameSequence) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("  ");
                }
                stringBuilder.append(data);
            }
            return stringBuilder.toString();
        }

        public String getInning1Score() {
            if (getInning1() != null) {
                return getInning1().toString();
            }
            return "";
        }

        public String getInning2Score() {
            if (getInning2() != null) {
                return getInning2().toString();
            }
            return "";
        }
    }


    public static class Inning extends BaseModel {
        String runs;
        String wickets;
        String overs;

        public String getRuns() {
            return getValidString(runs);
        }

        public String getWickets() {
            return getValidString(wickets);
        }

        public String getOvers() {
            return getValidString(overs);
        }

        @Override
        public String toString() {
            return getRuns() + " /" + getWickets() + " (" + getOvers() + ")";
        }
    }

    public String[] getTeamsName() {
        String[] data = new String[]{"", ""};
        if (getScore() != null) {
            if (getScore().getHome() != null) {
                data[0] = getScore().getHome().getName();
            }
            if (getScore().getAway() != null) {
                data[1] = getScore().getAway().getName();
            }
        }
        return data;
    }

    public String[] getTeamsScore(int eventTypeId) {
        String[] data = new String[]{"", "", "", ""};
        if (getScore() != null) {
            if (eventTypeId == 4) {
                String inning1ScoreT1 = "";
                String inning1ScoreT2 = "";
                String inning2ScoreT1 = "";
                String inning2ScoreT2 = "";
                if (getScore().getHome() != null) {
                    inning1ScoreT1 = getScore().getHome().getInning1Score();
                    inning2ScoreT1 = getScore().getHome().getInning2Score();
                }
                if (getScore().getAway() != null) {
                    inning1ScoreT2 = getScore().getAway().getInning1Score();
                    inning2ScoreT2 = getScore().getAway().getInning2Score();
                }
                data[0] = inning1ScoreT1;
                data[1] = inning1ScoreT2;
                data[2] = inning2ScoreT1;
                data[3] = inning2ScoreT2;
            } else if (eventTypeId == 1) {
                if (getScore().getHome() != null) {
                    data[0] = getScore().getHome().getScore();
                }
                if (getScore().getAway() != null) {
                    data[1] = getScore().getAway().getScore();
                }
            } else if (eventTypeId == 2) {
                if (getScore().getHome() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String sequence = getScore().getHome().getGameSequenceText();
                    if (isValidString(sequence)) {
                        stringBuilder.append(sequence);
                    }
                    if (isValidString(getScore().getHome().getGames())) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append(getScore().getHome().getGames());
                    }
                    if (isValidString(getScore().getHome().getDoubleFaults())) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append(getScore().getHome().getDoubleFaults());
                    }
                    if (isValidString(getScore().getHome().getScore())) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append(getScore().getHome().getScore());
                    }

                    data[0] = stringBuilder.toString();
                }
                if (getScore().getAway() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String sequence = getScore().getAway().getGameSequenceText();
                    if (isValidString(sequence)) {
                        stringBuilder.append(sequence);
                    }
                    if (isValidString(getScore().getAway().getGames())) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append(getScore().getAway().getGames());
                    }
                    if (isValidString(getScore().getAway().getDoubleFaults())) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append(getScore().getAway().getDoubleFaults());
                    }
                    if (isValidString(getScore().getAway().getScore())) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append(getScore().getAway().getScore());
                    }

                    data[1] = stringBuilder.toString();
                }
            }
        }
        return data;
    }

    public boolean[] getTeamsServing(int eventTypeId) {
        boolean[] data = new boolean[]{false, false};
        if (getScore() != null) {
            if (eventTypeId == 2) {
                if (getScore().getHome() != null) {
                    data[0] = getScore().getHome().isServing();
                }
                if (getScore().getAway() != null) {
                    data[1] = getScore().getAway().isServing();
                }
            }
        }
        return data;
    }


}
