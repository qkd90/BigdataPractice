package com.data.hmly.service.translation.flight.juhe;

import com.data.hmly.service.translation.flight.juhe.entity.CheckPriceResult;
import com.data.hmly.service.translation.flight.juhe.entity.CreateOrderResult;
import com.data.hmly.service.translation.flight.juhe.entity.FlightPolicy;
import com.data.hmly.service.translation.flight.juhe.entity.OrderFlights;
import com.data.hmly.service.translation.flight.juhe.entity.OrderPassenger;
import com.data.hmly.service.translation.flight.juhe.entity.PaymentResult;
import com.data.hmly.service.translation.flight.juhe.entity.TicketResult;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sane on 15/12/23.
 */
@Ignore
public class JuheFlightServiceTest {

    @Test
    public void testSearch() throws Exception {
//        System.out.println("-----------");
//        System.out.println("徐州到厦门");
//        FlightPolicy policy =  JuheFlightService.search("XUZ", "XMN", "2016-02-04", "", "eb0c096e69ecfce27bd9fbabf4f9ca48");
////        FlightPolicy policy =  JuheFlightService.search("XMN", "SHA", "2016-01-19", "MU", "eb0c096e69ecfce27bd9fbabf4f9ca48");
//        List<FlightPolicy.ResultEntity> results =policy.getResult();
//        for (FlightPolicy.ResultEntity result:results){
//            System.out.println(result.getAirlineName()+"\t"+result.getFlightNum()+"\t"+result.getLandingTime());
//            for (FlightPolicy.ResultEntity.CabinInfosEntity cabin:result.getCabinInfos()){
//                System.out.println("\t\t"+cabin.getFinallyPrice());
//            }
//        }
//        System.out.println("-----------");
//        System.out.println("厦门到三亚");
////        assertNotNull(results);
//
//        FlightPolicy policy1 =  JuheFlightService.search("XMN", "SYX", "2016-02-06", "", "eb0c096e69ecfce27bd9fbabf4f9ca48");
//        List<FlightPolicy.ResultEntity> results1 =policy1.getResult();
//        for (FlightPolicy.ResultEntity result:results1){
//            System.out.println(result.getAirlineName()+"\t"+result.getFlightNum()+"\t"+result.getLandingTime());
//            for (FlightPolicy.ResultEntity.CabinInfosEntity cabin:result.getCabinInfos()){
//                System.out.println("\t\t"+cabin.getFinallyPrice());
//            }
//        }
//        System.out.println("-----------");

        FlightPolicy policy2 =  JuheFlightService.search("XMN", "SHA", "2015-12-29", "", "eb0c096e69ecfce27bd9fbabf4f9ca48");
        List<FlightPolicy.ResultEntity> results2 =policy2.getResult();
        for (FlightPolicy.ResultEntity result:results2){
            System.out.println(result.getAirlineName() + "\t" + result.getFlightNum() + "\t" + result.getTakeoffTime() + "\t降落机场:" + result.getLandingPortName());
            for (FlightPolicy.ResultEntity.CabinInfosEntity cabin:result.getCabinInfos()){
                System.out.print("\t\t" + cabin.getFinallyPrice());
            }
            System.out.println("");
        }

//        FlightPolicy policy3 =  JuheFlightService.search("XMN", "PVG", "2015-12-29", "", "eb0c096e69ecfce27bd9fbabf4f9ca48");
//        List<FlightPolicy.ResultEntity> results3 =policy3.getResult();
//        for (FlightPolicy.ResultEntity result:results3){
//            System.out.println(result.getAirlineName()+"\t"+result.getFlightNum()+"\t"+result.getLandingTime()+"\t降落机场:"+result.getLandingPortName());
//            for (FlightPolicy.ResultEntity.CabinInfosEntity cabin:result.getCabinInfos()){
//                System.out.print("\t\t" + cabin.getFinallyPrice());
//            }
//            System.out.println("");
//        }
    }

    @Test
    public void testCheckPrice() throws Exception {
        CheckPriceResult result = JuheFlightService.checkPrice("XMN", "SHA", "2016-01-19", "MU5562","Z",460, "eb0c096e69ecfce27bd9fbabf4f9ca48");
        System.out.println(result.getResult().getResultPrice());
        System.out.println(result.getReason());
        assertEquals(result.getError_code(), "200");
        assertNotNull(result.getResult().getResultPrice());
    }

    @Test
    public void testCreateOrder() throws Exception {
        int finallyPrice = 400;
        int airportBuildFee = 50;
        OrderFlights flight = new OrderFlights("XMN", "SHA", "2016-01-19", "MU5562","Z");
        OrderPassenger passenger = new OrderPassenger("ADULT", "张三", "13344556677", "0", "352101199103194129", String.valueOf(finallyPrice), String.valueOf(airportBuildFee), "0");
        List<OrderPassenger> passengers = new ArrayList<OrderPassenger>();
        passengers.add(passenger);
        CreateOrderResult result = JuheFlightService.createOrder("eb0c096e69ecfce27bd9fbabf4f9ca48", "test-123-hmly-4567", finallyPrice + airportBuildFee, flight, passengers);
        assertEquals(result.getError_code(), "200");
    }

    @Test
    public void testPay() throws Exception {
        PaymentResult result = JuheFlightService.pay("eb0c096e69ecfce27bd9fbabf4f9ca48", "test-123-hmly-4567");
        assertEquals(result.getError_code(), "215205");
        assertEquals(result.getReason(), "账户余额不足");
    }

    @Test
    public void testRefundCheck() throws Exception {

    }

    @Test
    public void testGetTicket() throws Exception {
        TicketResult result = JuheFlightService.getTicket("eb0c096e69ecfce27bd9fbabf4f9ca48", "test-123-hmly-4567");
        assertEquals(result.getError_code(), "200");
        System.out.println("订单号："+result.getResult().getBillNum());
        assertNotNull(result.getResult().getBillNum());
        System.out.println("订单状态："+result.getResult().getBillStatus());
        assertNotNull(result.getResult().getBillStatus());
    }


}