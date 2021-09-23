package com.hmlyinfo.app.soutu.hotel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.domain.CtripImage;
import com.hmlyinfo.app.soutu.hotel.domain.HotelComment;
import com.hmlyinfo.app.soutu.hotel.domain.HotelIndex;
import com.hmlyinfo.app.soutu.hotel.mapper.CtripImageMapper;
import com.hmlyinfo.app.soutu.hotel.mapper.HotelCommentMapper;
import com.hmlyinfo.app.soutu.hotel.mapper.HotelIndexMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class HotelIndexService extends BaseService<HotelIndex, Long>{

	@Autowired
	private HotelIndexMapper<HotelIndex> mapper;
	@Autowired
	private HotelCommentMapper<HotelComment> hcMapper;
	@Autowired
	private CtripImageMapper<CtripImage> imgMapper;
	@Autowired
	private CtripHotelService ctHotelService;
	@Autowired
	private HotelCommentService hcService;
	@Autowired
	private CtripImageService imgService;

	@Override
	public BaseMapper<HotelIndex> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	
	public void updateIndex()
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> nameList = new ArrayList<String>();
		List<CtripHotel> hotelList = ctHotelService.listColumns(paramMap, Lists.newArrayList("id", "city_code", "hotel_name", "gcj_lng", "gcj_lat"));
		int sum = hotelList.size();
		int i = 1;
		for(CtripHotel hotel : hotelList){
			System.out.println("正在更新第" + i + "个，一共" + sum + "个");
			i++;
			long hotelId = hotel.getId();
			int cityCode = hotel.getCityCode();
			double lat = hotel.getGcjLat();
			double lng = hotel.getGcjLng();
			String hotelName = hotel.getHotelName();
			String codeName = cityCode + hotelName;
			
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("cityCode", cityCode);
			paraMap.put("hotelName", hotelName);
			List<HotelIndex> oldIndex = mapper.list(paraMap);
			if(oldIndex.size() == 0){
				HotelIndex newHotelIndex = new HotelIndex();
				newHotelIndex.setCityCode(cityCode);
				newHotelIndex.setHotelId(hotelId);
				newHotelIndex.setHotelName(hotelName);
				newHotelIndex.setHotelSource(HotelIndex.HOTEL_SOURCE_CTRIP);
				newHotelIndex.setGcjLng(lng);
				newHotelIndex.setGcjLat(lat);
					
				mapper.insert(newHotelIndex);
			}else {
				HotelIndex oldHotel = ListUtil.getSingle(oldIndex);
				oldHotel.setCityCode(cityCode);
				oldHotel.setHotelId(hotelId);
				oldHotel.setHotelName(hotelName);
				oldHotel.setHotelSource(HotelIndex.HOTEL_SOURCE_CTRIP);
				oldHotel.setGcjLng(lng);
				oldHotel.setGcjLat(lat);
				mapper.update(oldHotel);
			}
			
		}
	}
	
	

}
