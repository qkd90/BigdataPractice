package com.data.hmly.service.translation.train.Kyfw12306;

import com.data.hmly.service.translation.train.Kyfw12306.entity.LeftTickets;
import com.data.hmly.service.translation.util.Http;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Sane on 16/1/6.
 */
public class Kyfw12306TrainService {
    private static String ticketsSearchApi = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=%s&leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=ADULT";

    /**
     * 查询余票
     *
     * @param date        出发日期
     * @param fromStation 出发站点(code)
     * @param toStation   到达站点(code)
     * @return
     */
    public static List<LeftTickets.DataEntity> searchTickets(String date, String fromStation, String toStation) {
        String json = Http.Send("GET", String.format(ticketsSearchApi, date, fromStation, toStation), "");
//        String json = CommonUtils.getJson(String.format(ticketsSearchApi, date, fromStation, toStation));
        if (json != null && !json.contains("\"status\":true")) {
            return null;
        }
        if (json != null) {
            LeftTickets result = new Gson().fromJson(json, LeftTickets.class);
            if (result != null)
                return result.getData();
        }
        return null;
    }


}
