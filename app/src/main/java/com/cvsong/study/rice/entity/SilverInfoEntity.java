package com.cvsong.study.rice.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 上海交易所现货白银数据查询 响应体
 * Created by chenweisong on 2018/5/31.
 */
@Data
public class SilverInfoEntity implements Serializable {

    /**
     * msg : success
     * result : [{"closePri":"3634.00","highPic":"3697.00","limit":"1.59%","lowPic":"3582.00","name":"白银延期","openPri":"3583.00","time":"2016-04-2015:29:57","totalTurnover":"36002865152.00","totalVol":"9885466.00","variety":"Ag(T+D)","yesDayPic":"3513.00"},{"closePri":"3648.00","highPic":"3648.00","limit":"5.43%","lowPic":"3648.00","name":"白银9999","openPri":"3648.00","time":"2016-04-2015:21:11","totalTurnover":"21888.00","totalVol":"6.00","variety":"Ag99.99","yesDayPic":"3460.00"}]
     * retCode : 200
     */

    private String msg;
    private String retCode;
    private List<ResultEntity> result;



    @Data
    public static class ResultEntity {
        /**
         * closePri : 3634.00
         * highPic : 3697.00
         * limit : 1.59%
         * lowPic : 3582.00
         * name : 白银延期
         * openPri : 3583.00
         * time : 2016-04-2015:29:57
         * totalTurnover : 36002865152.00
         * totalVol : 9885466.00
         * variety : Ag(T+D)
         * yesDayPic : 3513.00
         */

        private String closePri;
        private String highPic;
        private String limit;
        private String lowPic;
        private String name;
        private String openPri;
        private String time;
        private String totalTurnover;
        private String totalVol;
        private String variety;
        private String yesDayPic;

    }
}
