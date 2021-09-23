package com.data.hmly.service.translation.train;

import com.data.hmly.service.translation.train.Qunar.PassBy;
import com.data.hmly.service.translation.util.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/12/23.
 */
public class QunarTrainService {
    private final static String passByApi = "http://train.qunar.com/search/s2sMore.jsp?callback=jQuery&from=%s" +
            "&to=%s&format=js&date=%s&checi=%s&trainDateVersion=%s";

    private final static String suggestApi = "http://train.qunar.com/qunar/checiSuggest.jsp?" +
            "include_coach_suggest=false&lang=zh&q=%s&sa=true&format=js&callback=XQScript_19";

    private final static String infoApi = "http://train.qunar.com/qunar/checiInfo.jsp?method_name=buy" +
            "&ex_track=bd_aladin_train_num_title&q=%s&date=%s&format=json" +
            "&cityname=123456&ver=1454658707139&callback=XQScript_6";


    private String getQunarCode(String code) {
        String suggest = CommonUtils.getJson(String.format(suggestApi, code));
        String str = "\"key\":\"(.*?)\"";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(suggest);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * 获得经停站信息
     *
     * @param leavePortName  出发站点名
     * @param arrivePortName 到达站点名
     * @param trafficCode    车次(需要获取)
     * @param date           日期
     * @return
     */
    public List<PassBy> getPassBy(String leavePortName, String arrivePortName, String trafficCode, String date) {
        List<PassBy> passBy = new ArrayList<PassBy>();
        String getQunarCode = getQunarCode(trafficCode);
//        String result = CommonUtils.getJson(String.format(passByApi, leavePortName, arrivePortName, date, trafficCode, date));
        String result = CommonUtils.getJson(String.format(infoApi, getQunarCode, date));
        //[27,"青岛北","第一天","20:00","20:01","1分","1171公里",1,"0","773"]
        String str = "\\[\\d+,\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(result);
        int sum = 1;
        while (m.find()) {
            passBy.add(new PassBy(String.valueOf(sum++), m.group(1), m.group(4), m.group(3), m.group(5), getNum(m.group(2))));
        }
        //用出发时间排序
        Collections.sort(passBy, new Comparator<PassBy>() {
            public int compare(PassBy arg0, PassBy arg1) {
                return (arg0.getDay() + arg0.getLeave()).compareTo(arg1.getDay() + arg1.getLeave());
            }
        });
        return passBy;
    }

    private String getNum(String str) {
        return str.replace("半", "0.5").replace("一", "1").replace("二", "2").replace("三", "3")
                .replace("四", "4").replace("五", "5").replace("六", "6").replace("七", "7").replace("八", "8")
                .replace("九", "9").replace("十", "10");
    }


}
