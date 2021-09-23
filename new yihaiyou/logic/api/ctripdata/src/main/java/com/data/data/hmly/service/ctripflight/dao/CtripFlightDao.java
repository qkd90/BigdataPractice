package com.data.data.hmly.service.ctripflight.dao;

import com.data.data.hmly.service.ctripflight.entity.CtripFlightInfo;
import com.data.data.hmly.service.ctripflight.entity.FltCityInfo;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderFlightInfoRequestType;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class CtripFlightDao extends DataAccess<CtripFlightInfo> {

	public CtripFlightInfo findFlightByInfo(
			FltOrderFlightInfoRequestType flightInfoRequest, FltCityInfo arriveCity, FltCityInfo departCity) {
		
		Criteria<CtripFlightInfo> criteria = new Criteria<CtripFlightInfo>(CtripFlightInfo.class);
		
		
		criteria.eq("departCityCode", departCity.getCityCode());
		criteria.eq("arriveCityCode", arriveCity.getCityCode());
		criteria.eq("flight", flightInfoRequest.getFlight());
		criteria.eq("craftType", flightInfoRequest.getCraftType());
		criteria.eq("airlineCode", flightInfoRequest.getAirlineCode());
//		criteria.eq("childStandardPrice", flightInfoRequest.get);
//		criteria.eq("airlineCode", flightInfoRequest.getAirlineCode());
//		criteria.eq("airlineCode", flightInfoRequest.getAirlineCode());
//		criteria.eq("airlineCode", flightInfoRequest.getAirlineCode());
		
		return null;
	}
}
