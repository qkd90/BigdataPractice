package com.hmlyinfo.app.soutu.hotel.service;

import com.hmlyinfo.app.soutu.hotel.domain.CtripImage;
import com.hmlyinfo.app.soutu.hotel.mapper.CtripImageMapper;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.persistent.PageDto;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CtripImageService extends BaseService<CtripImage, Long> {

    static int HOTEL_IMAGE_OUTER = 1;
    static int HOTEL_IMAGE_INNER = 2;
    static int HOTEL_IMAGE_ROOM = 3;

    
    @Autowired
    private CtripImageMapper<CtripImage> mapper;
    @Autowired
    private HotelCommentService hotelCommentService;
    
    @Override
    public BaseMapper<CtripImage> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

   
    
    
    //查询图片列表
    public List<CtripImage> listImg(Map<String, Object> params){
    	if(params.get("hotelId") != null){
    		long indexId = hotelCommentService.getIndexId(params);
    		params.remove("hotelId");
    		params.put("indexId", indexId);
    		return mapper.list(params);
    	}else {
			return mapper.list(params);
		}
    }
    
    //查询图片数量
    public ActionResult imgCount(Map<String, Object> params){
    	if(params.get("hotelId") != null){
    		long indexId = hotelCommentService.getIndexId(params);
    		params.remove("hotelId");
    		params.put("indexId", indexId);
    		return countAsResult(params);
    	}else {
			return countAsResult(params);
		}
    }
    
    public Map<String, Object> listAll(Map<String, Object> params) {
        PageDto pageDto = (PageDto) params.get("pageDto");
        pageDto.setPageSize(Integer.MAX_VALUE);
        
        //把参数中的酒店id经过一系列转换变成对应的图片id列表然后查询
        long indexId = hotelCommentService.getIndexId(params);
        params.remove("hotelId");
        params.put("indexId", indexId);
        List<CtripImage> data = list(params);
        
        List<CtripImage> outList = new ArrayList<CtripImage>();
        List<CtripImage> inList = new ArrayList<CtripImage>();
        List<CtripImage> roomList = new ArrayList<CtripImage>();
        for (CtripImage ctripImage : data) {
            if (ctripImage.getType() == HOTEL_IMAGE_OUTER) {
                outList.add(ctripImage);
            } else if (ctripImage.getType() == HOTEL_IMAGE_INNER) {
                inList.add(ctripImage);
            } else {
                roomList.add(ctripImage);
            }
        }
        Map<String, Object> typeData = new HashMap<String, Object>();
        typeData.put("outer", outList);
        typeData.put("inner", inList);
        typeData.put("room", roomList);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", data);
        result.put("type", typeData);
        result.put("count", data.size());
        return result;
    }

    //统计每种类型的图片数量
    public List<Map<String, Object>> countByType(Map<String, Object> params) {
    	
        params.put("withType", true);
        long indexId = hotelCommentService.getIndexId(params);
        params.remove("hotelId");
        params.put("indexId", indexId);
        return mapper.countWithType(params);
    }

}
