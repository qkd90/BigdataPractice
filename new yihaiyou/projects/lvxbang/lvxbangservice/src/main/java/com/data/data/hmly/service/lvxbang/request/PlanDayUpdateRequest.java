package com.data.data.hmly.service.lvxbang.request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class PlanDayUpdateRequest {

	public Long id;
    public Long cityId;
    public Long hotelId;
    public Long trafficId;
	public List<PlanTripUpdateRequest> trips = new ArrayList<PlanTripUpdateRequest>();
}
