package com.lotus.model;

import com.models.BaseModel;

/**
 * Created by Azher on 23/10/18.
 */
public class BetPlaceModel extends BaseModel {


    /**
     * unique_id : 3351855_28970047
     * result : {"code":0,"error":false,"message":"Saved Successfully","data":{"RunnerValue":""}}
     */

    private String unique_id;
    private ResultData result;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public ResultData getResult() {
        return result;
    }

    public void setResult(ResultData result) {
        this.result = result;
    }

    public static class ResultData extends BaseModel {
        /**
         * code : 0
         * error : false
         * message : Saved Successfully
         * data : {"RunnerValue":""}
         */

        private int code;
        private boolean error;
        private String message;
        private Data data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMessage() {
            return getValidString(message);
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data extends BaseModel {
            /**
             * RunnerValue :
             */

            private String RunnerValue;

            public String getRunnerValue() {
                return getValidString(RunnerValue);
            }

            public void setRunnerValue(String RunnerValue) {
                this.RunnerValue = RunnerValue;
            }
        }
    }
}
