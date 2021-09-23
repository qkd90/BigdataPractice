package com.data.data.hmly.service.elong.service;

import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.CreditCardValidateResult;
import com.data.data.hmly.service.elong.pojo.HotelIDListCondition;
import com.data.data.hmly.service.elong.pojo.HotelIdListResult;
import com.data.data.hmly.service.elong.pojo.Reviews;
import com.data.data.hmly.service.elong.pojo.statics.hotelDetail.HotelType;
import com.data.data.hmly.service.elong.service.result.HotelDetail;
import com.data.data.hmly.service.elong.service.result.HotelOrderDetailResult;
import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sane on 16/1/27.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class ElongHotelServiceTest {
    @Resource
    private ElongHotelService elongHotelService;

    @Test
    public void testGetStaticsHotelList() throws Exception {
//        elongHotelService.getStaticsHotelList(1453737600000L, true);
//        elongHotelService.getStaticsHotelList(1453910400000L, true);
//        elongHotelService.getStaticsHotelList(1453824000000L, true);//1月27日
//        List<Long> hotelIds = elongHotelService.getStaticsHotelList(1453908600000L, true);//1月27日 23点30分
//        for (Long id : hotelIds) {
//            System.out.println(id);
//        }
    }

    @Test
    public void testGetStaticsHotelDetail() throws Exception {
        HotelType hotel = elongHotelService.getStaticsHotelDetail("40101025");
        System.out.println(hotel.getDetail().getName());
        System.out.println(hotel.getDetail().getAddress());
        System.out.println(hotel.getDetail().getBaiduLat());
        System.out.println(hotel.getDetail().getBaiduLon());
    }

    @Test
    public void testGetHotelIdList() throws Exception {

        Date arriveDate = null;
        try {
            arriveDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-29");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date(new Date().getTime() + 3600000);
        if (arriveDate.getTime() < now.getTime()) {
            arriveDate = now;
        }
        Date leaveDate = new Date(arriveDate.getTime() + 3600000 * 48);

        HotelIDListCondition idListCondition = new HotelIDListCondition();
        idListCondition.setCityId("2505");
        idListCondition.setArrivalDate(arriveDate);
        idListCondition.setDepartureDate(leaveDate);


        HotelIdListResult idListResult = elongHotelService.getHotelIdList(idListCondition);
        System.out.println(idListResult);

        String[] arr = idListResult.getResult().getHotelIds().split(",");
        for (String str : arr) {
            System.out.println(str);
        }
    }

    @Test
    public void testGetHotelList() throws Exception {

    }

    @Test
    public void testGetHotelDetail() throws Exception {
        HotelDetail hotel = elongHotelService.getHotelDetail(90200261L);
        System.out.println(hotel.getResult().getHotels().size());
        System.out.println(hotel.getResult().getHotels().get(0).getGuaranteeRules().get(0).getChangeRule());
//        System.out.println(hotel.getResult().getHotels().get(0).getDetail().getAddress());
//        System.out.println(hotel.getResult().getHotels().get(0).getDetail().getHotelName());
//        System.out.println(hotel.getResult().getHotels().get(0).getRooms().size());
//        System.out.println(hotel.getResult().getHotels().get(0).getRooms().get(0).getName());
//        System.out.println(hotel.getResult().getHotels().get(0).getRooms().get(0).getBedType());
    }

    @Test
    public void testGetDetailOptions() throws Exception {

    }

    @Test
    public void testGetHotelDetail1() throws Exception {

    }

    @Test
    public void testCreateOrder() throws Exception {

    }

    @Test
    public void testCancelOrder() throws Exception {

    }

    @Test
    public void testCancelOrder1() throws Exception {

    }

    @Test
    public void testGetOrderDetail() throws Exception {
        //366299140
        HotelOrderDetailResult result = elongHotelService.getOrderDetail(366299140L);
        System.out.println(new Gson().toJson(result));

    }

    @Test
    public void testGetOrderDetail1() throws Exception {

    }

    @Test
    public void testValidateCreditCard() throws Exception {
        CreditCardValidateResult result = elongHotelService.validateCreditCart("3568891127926087");
        System.out.println("是否可用:" + result.getResult().isIsValid());
        System.out.println("是否需要cvv码:" + result.getResult().isIsNeedVerifyCode());
    }

    @Test
    public void testGetHotelComments() throws Exception {

        Date after = new SimpleDateFormat("yyyy-MM-dd").parse("2014-5-1");
        List<Reviews.CommentsEntity> commentsEntities = elongHotelService.getCommentLimitTime(40101022, after);

        for (Reviews.CommentsEntity commentsEntity : commentsEntities) {
            System.out.println("好评:" + commentsEntity.getIsMarrow() + "\t" + commentsEntity.getCommentDateTime() + "\t" + commentsEntity.getUserName() + "\t" + commentsEntity.getContent());
        }


    }


}