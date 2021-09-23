package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.common.entity.interfaces.ProductPrice;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class ScenicBookingResponse extends BookingResponse {

    public List<TicketBookingResponse> tickets;

    public ScenicBookingResponse() {

    }

    public ScenicBookingResponse(ProductPrice productPrice) {
        super(productPrice);
    }

}
