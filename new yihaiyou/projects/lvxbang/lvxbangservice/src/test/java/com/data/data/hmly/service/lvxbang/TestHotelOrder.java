package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.Contact;
import com.data.data.hmly.service.elong.pojo.CreateOrderCondition;
import com.data.data.hmly.service.elong.pojo.CreateOrderResult;
import com.data.data.hmly.service.elong.pojo.CreateOrderRoom;
import com.data.data.hmly.service.elong.pojo.Customer;
import com.data.data.hmly.service.elong.pojo.EnumConfirmationType;
import com.data.data.hmly.service.elong.pojo.EnumCurrencyCode;
import com.data.data.hmly.service.elong.pojo.EnumGuestTypeCode;
import com.data.data.hmly.service.elong.pojo.EnumPaymentType;
import com.data.data.hmly.service.order.entity.OrderDetail;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sane on 16/2/29.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class TestHotelOrder {
    @Resource
    private  ElongHotelService elongHotelService;
    @Test
    public void testGetReplanByDelicacy() {
        OrderDetail orderDetail = new OrderDetail();
        CreateOrderCondition createOrderCondition = getCreateOrderCondition(orderDetail);
        CreateOrderResult createOrderResult = elongHotelService.createOrder(createOrderCondition);
        System.out.println("elong order id:" + createOrderResult.getCreateOrderResultDetail().getOrderId()
        );
    }

    private static CreateOrderCondition getCreateOrderCondition(OrderDetail orderDetail) {
        CreateOrderCondition condition = new CreateOrderCondition();

        Date arrive = null;
        Date departure = null;
        Date earliestArrive = null;
        Date lastArrival = null;
        try {
            arrive = new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-18");
            earliestArrive = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2016-04-18 10:00");
            lastArrival = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2016-04-18 12:00");
            departure = new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-19");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        condition.setArrivalDate(arrive);
        condition.setEarliestArrivalTime(earliestArrive);
        condition.setLatestArrivalTime(lastArrival);
        condition.setDepartureDate(departure);

        condition.setHotelId("10101129");
//        condition.setHotelId("90256739");
        condition.setRoomTypeId("0001");
        condition.setRatePlanId(2626178);
//        condition.setRatePlanId(573138);
        condition.setTotalPrice(new BigDecimal(150));
//        condition.setTotalPrice(new BigDecimal(138));
        condition.setAffiliateConfirmationId("swdaew2qeed");
        condition.setConfirmationType(EnumConfirmationType.SMS_cn);
        condition.setContact(getContact());
//			condition.setCreditCard(getCreditCard());
        condition.setCreditCard(null);
        condition.setCurrencyCode(EnumCurrencyCode.RMB);
        condition.setCustomerIPAddress("27.154.225.170");
        condition.setCustomerType(EnumGuestTypeCode.Chinese);
        condition.setExtendInfo(null);
        condition.setInvoice(null);
        condition.setIsForceGuarantee(false);
        condition.setIsGuaranteeOrCharged(false);
        condition.setIsNeedInvoice(false);
        condition.setNightlyRates(null);
        condition.setNoteToElong("");
        condition.setNoteToHotel(null);
        condition.setNumberOfCustomers(1);
        condition.setNumberOfRooms(1);
        condition.setOrderRooms( getRooms() );
        condition.setPaymentType(EnumPaymentType.SelfPay);
        condition.setSupplierCardNo(null);
        return condition;
    }

    static List<CreateOrderRoom> getRooms() {
        List<Customer> customers = new ArrayList<Customer>(1);
        Customer c = new Customer();
        c.setName("dasddsees3");
        c.setMobile("15021150003");
        customers.add(c);
        List<CreateOrderRoom> rooms = new ArrayList<CreateOrderRoom>(1);
        CreateOrderRoom room = new CreateOrderRoom();
        room.setCustomers(customers);
        rooms.add(room);
        return rooms;
    }

    static Contact getContact() {
        Contact c = new Contact();
        c.setName("sassa");
        c.setMobile("18050012593");
        return c;
    }
}
