package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

/**
 * @author Manish Kumar
 * @since 9/6/18
 */

public class MatchScoreModel extends BaseModel {


    /**
     * status : {"statusCode":"0","statusDesc":"Success"}
     * result : {"messageId":"0069cc51-a3d9-48ec-9615-413e7db18e1b","eventId":28970048,"battingTeam":{"name":"India B","partnerShipBalls":0,"partnerShipScore":0,"runRate":4.44,"score":185,"wickets":8,"overs":41,"balls":4,"requiredRunRate":null,"requiredRuns":null,"projScr":222,"target":null},"currentOver":{"bowlerName":"Vijay Shankar","batsNames":["Hanuman Vihari","Deepak Chahar"],"overNumber":42,"runs":1,"score":185,"wickets":8,"balls":[{"number":1,"value":"1","type":"INFIELDBOUNDARY"},{"number":2,"value":"0","type":"INFIELDBOUNDARY"},{"number":3,"value":"0","type":"INFIELDBOUNDARY"},{"number":4,"value":"W","type":"WICKET"}]},"lastOver":{"bowlerName":"Papu Roy","batsNames":["Deepak Chahar","Hanuman Vihari"],"overNumber":41,"runs":2,"score":184,"wickets":7,"balls":[{"number":1,"value":"1","type":"INFIELDBOUNDARY"},{"number":2,"value":"0","type":"INFIELDBOUNDARY"},{"number":3,"value":"0","type":"INFIELDBOUNDARY"},{"number":4,"value":"0","type":"INFIELDBOUNDARY"},{"number":5,"value":"1","type":"INFIELDBOUNDARY"},{"number":6,"value":"0","type":"INFIELDBOUNDARY"}]},"lastBatsmanOut":{"name":"Deepak Chahar","batsManRuns":3,"balls":9},"timeStamp":"2018-10-24T06:27:12.8841872Z","tossWinner":{"name":"India B","decision":"optToBat"},"remainingBalls":50,"currentInning":1,"oversPerInning":50,"result":null,"status":"InPlay","batsmen":[{"name":"Hanuman Vihari","isOnStrike":false,"balls":86,"batsmanRuns":68,"fours":5,"sixes":0,"strikeRate":79.07},{"name":"Jaydev Unadkat","isOnStrike":true,"balls":0,"batsmanRuns":0,"fours":0,"sixes":0,"strikeRate":null}],"bowler":{"name":"Vijay Shankar","overs":7.4,"maidens":0,"wickets":2,"bowlerRuns":34,"economy":4.43},"adjustedOvers":false}
     * success : true
     */

    private StatusBean status;
    private ResultBean result;
    private boolean success;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class StatusBean extends BaseModel {
        /**
         * statusCode : 0
         * statusDesc : Success
         */

        private String statusCode;
        private String statusDesc;

        public String getStatusCode() {
            return getValidString(statusCode);
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusDesc() {
            return getValidString(statusDesc);
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }
    }

    public static class ResultBean extends BaseModel {
        /**
         * messageId : 0069cc51-a3d9-48ec-9615-413e7db18e1b
         * eventId : 28970048
         * battingTeam : {"name":"India B","partnerShipBalls":0,"partnerShipScore":0,"runRate":4.44,"score":185,"wickets":8,"overs":41,"balls":4,"requiredRunRate":null,"requiredRuns":null,"projScr":222,"target":null}
         * currentOver : {"bowlerName":"Vijay Shankar","batsNames":["Hanuman Vihari","Deepak Chahar"],"overNumber":42,"runs":1,"score":185,"wickets":8,"balls":[{"number":1,"value":"1","type":"INFIELDBOUNDARY"},{"number":2,"value":"0","type":"INFIELDBOUNDARY"},{"number":3,"value":"0","type":"INFIELDBOUNDARY"},{"number":4,"value":"W","type":"WICKET"}]}
         * lastOver : {"bowlerName":"Papu Roy","batsNames":["Deepak Chahar","Hanuman Vihari"],"overNumber":41,"runs":2,"score":184,"wickets":7,"balls":[{"number":1,"value":"1","type":"INFIELDBOUNDARY"},{"number":2,"value":"0","type":"INFIELDBOUNDARY"},{"number":3,"value":"0","type":"INFIELDBOUNDARY"},{"number":4,"value":"0","type":"INFIELDBOUNDARY"},{"number":5,"value":"1","type":"INFIELDBOUNDARY"},{"number":6,"value":"0","type":"INFIELDBOUNDARY"}]}
         * lastBatsmanOut : {"name":"Deepak Chahar","batsManRuns":3,"balls":9}
         * timeStamp : 2018-10-24T06:27:12.8841872Z
         * tossWinner : {"name":"India B","decision":"optToBat"}
         * remainingBalls : 50
         * currentInning : 1
         * oversPerInning : 50
         * result : null
         * status : InPlay
         * batsmen : [{"name":"Hanuman Vihari","isOnStrike":false,"balls":86,"batsmanRuns":68,"fours":5,"sixes":0,"strikeRate":79.07},{"name":"Jaydev Unadkat","isOnStrike":true,"balls":0,"batsmanRuns":0,"fours":0,"sixes":0,"strikeRate":null}]
         * bowler : {"name":"Vijay Shankar","overs":7.4,"maidens":0,"wickets":2,"bowlerRuns":34,"economy":4.43}
         * adjustedOvers : false
         */

        private String messageId;
        private String eventId;
        private BattingTeamBean battingTeam;
        private CurrentOverBean currentOver;
        private LastOverBean lastOver;
        private LastBatsmanOutBean lastBatsmanOut;
        private String timeStamp;
        private TossWinnerBean tossWinner;
        private String remainingBalls;
        private String currentInning;
        private String oversPerInning;
        private Object result;
        private String status;
        private Object bowler;
        private boolean adjustedOvers;
        private List<BatsmenBean> batsmen;

        public String getMessageId() {
            return getValidString(messageId);
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getEventId() {
            return getValidString(eventId);
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public BattingTeamBean getBattingTeam() {
            return battingTeam;
        }

        public void setBattingTeam(BattingTeamBean battingTeam) {
            this.battingTeam = battingTeam;
        }

        public CurrentOverBean getCurrentOver() {
            return currentOver;
        }

        public void setCurrentOver(CurrentOverBean currentOver) {
            this.currentOver = currentOver;
        }

        public LastOverBean getLastOver() {
            return lastOver;
        }

        public void setLastOver(LastOverBean lastOver) {
            this.lastOver = lastOver;
        }

        public LastBatsmanOutBean getLastBatsmanOut() {
            return lastBatsmanOut;
        }

        public void setLastBatsmanOut(LastBatsmanOutBean lastBatsmanOut) {
            this.lastBatsmanOut = lastBatsmanOut;
        }

        public String getTimeStamp() {
            return getValidString(timeStamp);
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public TossWinnerBean getTossWinner() {
            return tossWinner;
        }

        public void setTossWinner(TossWinnerBean tossWinner) {
            this.tossWinner = tossWinner;
        }

        public String getRemainingBalls() {
            return getValidString(remainingBalls);
        }

        public void setRemainingBalls(String remainingBalls) {
            this.remainingBalls = remainingBalls;
        }

        public String getCurrentInning() {
            return getValidString(currentInning);
        }

        public void setCurrentInning(String currentInning) {
            this.currentInning = currentInning;
        }

        public String getOversPerInning() {
            return getValidString(oversPerInning);
        }

        public void setOversPerInning(String oversPerInning) {
            this.oversPerInning = oversPerInning;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public String getStatus() {
            return getValidString(status);
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getBowler() {
            return bowler;
        }

        public void setBowler(Object bowler) {
            this.bowler = bowler;
        }

        public boolean isAdjustedOvers() {
            return adjustedOvers;
        }

        public void setAdjustedOvers(boolean adjustedOvers) {
            this.adjustedOvers = adjustedOvers;
        }

        public List<BatsmenBean> getBatsmen() {
            return batsmen;
        }

        public void setBatsmen(List<BatsmenBean> batsmen) {
            this.batsmen = batsmen;
        }

        public static class BattingTeamBean extends BaseModel {
            /**
             * name : India B
             * partnerShipBalls : 0
             * partnerShipScore : 0
             * runRate : 4.44
             * score : 185
             * wickets : 8
             * overs : 41
             * balls : 4
             * requiredRunRate : null
             * requiredRuns : null
             * projScr : 222
             * target : null
             */

            private String name;
            private String partnerShipBalls;
            private String partnerShipScore;
            private String runRate;
            private String score;
            private String wickets;
            private String overs;
            private String balls;
            private String requiredRunRate;
            private String requiredRuns;
            private String projScr;
            private String target;

            public String getName() {
                return getValidString(name);
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPartnerShipBalls() {
                return getValidString(partnerShipBalls);
            }

            public void setPartnerShipBalls(String partnerShipBalls) {
                this.partnerShipBalls = partnerShipBalls;
            }

            public String getPartnerShipScore() {
                return getValidString(partnerShipScore);
            }

            public void setPartnerShipScore(String partnerShipScore) {
                this.partnerShipScore = partnerShipScore;
            }

            public String getRunRate() {
                return getValidString(runRate);
            }

            public void setRunRate(String runRate) {
                this.runRate = runRate;
            }

            public String getScore() {
                return getValidString(score);
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getWickets() {
                return getValidString(wickets);
            }

            public void setWickets(String wickets) {
                this.wickets = wickets;
            }

            public String getOvers() {
                return getValidString(overs);
            }

            public void setOvers(String overs) {
                this.overs = overs;
            }

            public String getBalls() {
                return getValidString(balls);
            }

            public void setBalls(String balls) {
                this.balls = balls;
            }

            public String getRequiredRunRate() {
                return getValidString(requiredRunRate);
            }

            public void setRequiredRunRate(String requiredRunRate) {
                this.requiredRunRate = requiredRunRate;
            }

            public String getRequiredRuns() {
                return getValidString(requiredRuns);
            }

            public void setRequiredRuns(String requiredRuns) {
                this.requiredRuns = requiredRuns;
            }

            public String getProjScr() {
                return getValidString(projScr);
            }

            public void setProjScr(String projScr) {
                this.projScr = projScr;
            }

            public String getTarget() {
                return getValidString(target);
            }

            public void setTarget(String target) {
                this.target = target;
            }
        }

        public static class CurrentOverBean extends BaseModel {
            /**
             * bowlerName : Vijay Shankar
             * batsNames : ["Hanuman Vihari","Deepak Chahar"]
             * overNumber : 42
             * runs : 1
             * score : 185
             * wickets : 8
             * balls : [{"number":1,"value":"1","type":"INFIELDBOUNDARY"},{"number":2,"value":"0","type":"INFIELDBOUNDARY"},{"number":3,"value":"0","type":"INFIELDBOUNDARY"},{"number":4,"value":"W","type":"WICKET"}]
             */

            private String bowlerName;
            private String overNumber;
            private String runs;
            private String score;
            private String wickets;
            private List<String> batsNames;
            private List<BallsBean> balls;

            public String getBowlerName() {
                return getValidString(bowlerName);
            }

            public void setBowlerName(String bowlerName) {
                this.bowlerName = bowlerName;
            }

            public String getOverNumber() {
                return getValidString(overNumber);
            }

            public void setOverNumber(String overNumber) {
                this.overNumber = overNumber;
            }

            public String getRuns() {
                return getValidString(runs);
            }

            public void setRuns(String runs) {
                this.runs = runs;
            }

            public String getScore() {
                return getValidString(score);
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getWickets() {
                return getValidString(wickets);
            }

            public void setWickets(String wickets) {
                this.wickets = wickets;
            }

            public List<String> getBatsNames() {
                return batsNames;
            }

            public void setBatsNames(List<String> batsNames) {
                this.batsNames = batsNames;
            }

            public List<BallsBean> getBalls() {
                return balls;
            }

            public void setBalls(List<BallsBean> balls) {
                this.balls = balls;
            }

            public static class BallsBean extends BaseModel {
                /**
                 * number : 1
                 * value : 1
                 * type : INFIELDBOUNDARY
                 */

                private String number;
                private String value;
                private String type;

                public String getNumber() {
                    return getValidString(number);
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getValue() {
                    return getValidString(value);
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getType() {
                    return getValidString(type);
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class LastOverBean extends BaseModel {
            /**
             * bowlerName : Papu Roy
             * batsNames : ["Deepak Chahar","Hanuman Vihari"]
             * overNumber : 41
             * runs : 2
             * score : 184
             * wickets : 7
             * balls : [{"number":1,"value":"1","type":"INFIELDBOUNDARY"},{"number":2,"value":"0","type":"INFIELDBOUNDARY"},{"number":3,"value":"0","type":"INFIELDBOUNDARY"},{"number":4,"value":"0","type":"INFIELDBOUNDARY"},{"number":5,"value":"1","type":"INFIELDBOUNDARY"},{"number":6,"value":"0","type":"INFIELDBOUNDARY"}]
             */

            private String bowlerName;
            private String overNumber;
            private String runs;
            private String score;
            private String wickets;
            private List<String> batsNames;
            private List<BallsBeanX> balls;

            public String getBowlerName() {
                return getValidString(bowlerName);
            }

            public void setBowlerName(String bowlerName) {
                this.bowlerName = bowlerName;
            }

            public String getOverNumber() {
                return getValidString(overNumber);
            }

            public void setOverNumber(String overNumber) {
                this.overNumber = overNumber;
            }

            public String getRuns() {
                return getValidString(runs);
            }

            public void setRuns(String runs) {
                this.runs = runs;
            }

            public String getScore() {
                return getValidString(score);
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getWickets() {
                return getValidString(wickets);
            }

            public void setWickets(String wickets) {
                this.wickets = wickets;
            }

            public List<String> getBatsNames() {
                return batsNames;
            }

            public void setBatsNames(List<String> batsNames) {
                this.batsNames = batsNames;
            }

            public List<BallsBeanX> getBalls() {
                return balls;
            }

            public void setBalls(List<BallsBeanX> balls) {
                this.balls = balls;
            }

            public static class BallsBeanX extends BaseModel {
                /**
                 * number : 1
                 * value : 1
                 * type : INFIELDBOUNDARY
                 */

                private String number;
                private String value;
                private String type;

                public String getNumber() {
                    return getValidString(number);
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getValue() {
                    return getValidString(value);
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getType() {
                    return getValidString(type);
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class LastBatsmanOutBean extends BaseModel {
            /**
             * name : Deepak Chahar
             * batsManRuns : 3
             * balls : 9
             */

            private String name;
            private String batsManRuns;
            private String balls;

            public String getName() {
                return getValidString(name);
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBatsManRuns() {
                return getValidString(batsManRuns);
            }

            public void setBatsManRuns(String batsManRuns) {
                this.batsManRuns = batsManRuns;
            }

            public String getBalls() {
                return getValidString(balls);
            }

            public void setBalls(String balls) {
                this.balls = balls;
            }
        }

        public static class TossWinnerBean extends BaseModel {
            /**
             * name : India B
             * decision : optToBat
             */

            private String name;
            private String decision;

            public String getName() {
                return getValidString(name);
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDecision() {
                return getValidString(decision);
            }

            public void setDecision(String decision) {
                this.decision = decision;
            }
        }

        public static class Bowler extends BaseModel {
            /**
             * name : Vijay Shankar
             * overs : 7.4
             * maidens : 0
             * wickets : 2
             * bowlerRuns : 34
             * economy : 4.43
             */

            private String name;
            private double overs;
            private String maidens;
            private String wickets;
            private String bowlerRuns;
            private double economy;

            public String getName() {
                return getValidString(name);
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getOvers() {
                return overs;
            }

            public void setOvers(double overs) {
                this.overs = overs;
            }

            public String getMaidens() {
                return getValidString(maidens);
            }

            public void setMaidens(String maidens) {
                this.maidens = maidens;
            }

            public String getWickets() {
                return getValidString(wickets);
            }

            public void setWickets(String wickets) {
                this.wickets = wickets;
            }

            public String getBowlerRuns() {
                return getValidString(bowlerRuns);
            }

            public void setBowlerRuns(String bowlerRuns) {
                this.bowlerRuns = bowlerRuns;
            }

            public double getEconomy() {
                return economy;
            }

            public void setEconomy(double economy) {
                this.economy = economy;
            }
        }

        public static class BatsmenBean extends BaseModel {
            /**
             * name : Hanuman Vihari
             * isOnStrike : false
             * balls : 86
             * batsmanRuns : 68
             * fours : 5
             * sixes : 0
             * strikeRate : 79.07
             */

            private String name;
            private boolean isOnStrike;
            private String balls;
            private String batsmanRuns;
            private String fours;
            private String sixes;
            private String strikeRate;

            public String getName() {
                return getValidString(name);
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isIsOnStrike() {
                return isOnStrike;
            }

            public void setIsOnStrike(boolean isOnStrike) {
                this.isOnStrike = isOnStrike;
            }

            public String getBalls() {
                return getValidString(balls);
            }

            public void setBalls(String balls) {
                this.balls = balls;
            }

            public String getBatsmanRuns() {
                return getValidString(batsmanRuns);
            }

            public void setBatsmanRuns(String batsmanRuns) {
                this.batsmanRuns = batsmanRuns;
            }

            public String getFours() {
                return getValidString(fours);
            }

            public void setFours(String fours) {
                this.fours = fours;
            }

            public String getSixes() {
                return getValidString(sixes);
            }

            public void setSixes(String sixes) {
                this.sixes = sixes;
            }

            public String getStrikeRate() {
                return getValidString(strikeRate);
            }

            public void setStrikeRate(String strikeRate) {
                this.strikeRate = strikeRate;
            }
        }
    }
}
