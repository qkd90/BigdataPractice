package com.data.hmly.service.translation.train.juhe;

import com.data.hmly.service.translation.train.juhe.entity.Passenger;
import com.data.hmly.service.translation.train.juhe.entity.SubmitOrderRequest;
import com.data.hmly.service.translation.train.juhe.entity.SubmitOrderResult;
import com.data.hmly.service.translation.train.juhe.entity.TicketsAvailable;
import com.data.hmly.service.translation.util.CommonUtils;
import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;



   /*
    *
    * 请求地址：http://op.juhe.cn/trainTickets/ticketsAvailable
请求参数：dtype=json&train_date=2016-01-18&from_station=XMS&to_station=XKS&key=ef79c0d3f12743a0f133738b12bf4963
请求方式：GET
    *
    * */

/**
 * Created by Sane on 16/1/7.
 */
@Ignore
public class JuheTrainServiceTest {
    @Test
    public void testApi() throws Exception{
        String url ="http://op.juhe.cn/trainTickets/submit";
        String param_api ="dtype=json&key=%s&user_orderid=%s&train_date=%s&from_station_code=%s&from_station_name=%s" +
                "&to_station_code=%s&to_station_name=%s&checi=%s&passengers=%s";
        String key="ef79c0d3f12743a0f133738b12bf4963";
        String orderId="hmly-test-123";
        String trainDate="2016-02-19";
        String fromCode="XMS";
        String fromName="厦门";
        String toCode="XKS";
        String toName="厦门北";
        String checi="D6204";
        List<Passenger> passengers = new ArrayList<Passenger>();
        Passenger passenger = new Passenger("张三","420205199207231234","8.5","O","二等座");
        passenger.setPassengerid(12345);
        passengers.add(passenger);
        String ps= new Gson().toJson(passengers);
        String request =String.format(param_api,key,orderId,trainDate,fromCode,fromName,toCode,toName,checi,ps);
        String json = CommonUtils.postJson(url,request);
        System.out.println(json);
    }

    @Test
    public void testSubmitOrder() throws Exception{
        String key="ef79c0d3f12743a0f133738b12bf4963";
        String orderId="hmly-test-123";
        String trainDate="2016-02-19";
        String fromCode="XMS";
        String fromName="厦门";
        String toCode="XKS";
        String toName="厦门北";
        String checi="D6204";
        SubmitOrderRequest orderRequest = new SubmitOrderRequest(trainDate,fromCode,fromName,toCode,toName,checi);
        List<Passenger> passengers = new ArrayList<Passenger>();
        Passenger passenger = new Passenger("张三","420205199207231234","8.5","O","二等座");
        passenger.setPassengerid(12345);
        passengers.add(passenger);
        SubmitOrderResult result = JuheTrainService.submitOrder(orderRequest,key,orderId,passengers);
        System.out.println(result.getResult());
    }



    @Test
    public void testSearchTickets() throws Exception {
//        System.out.println("长春到昆明 68小时");
//        List<TicketsAvailable.ResultEntity.Ticket> tickets = JuheTrainService.searchTickets("2016-02-19", "CCT", "KMM", "ef79c0d3f12743a0f133738b12bf4963");
//        System.out.println("厦门到厦门北");
        List<TicketsAvailable.ResultEntity.Ticket> tickets = JuheTrainService.searchTickets("2016-02-29", "XMS", "XKS", "ef79c0d3f12743a0f133738b12bf4963");

        for (TicketsAvailable.ResultEntity.Ticket ticket : tickets) {
            System.err.println("\t\t" + ticket.getTrain_code() + "\t" + ticket.getEdz_num() + "\t" + ticket.getEdz_price());
        }

        System.err.println("-----------");

//

    }



}