package com.hmlyinfo.app.soutu.scenicTicket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.TicketRenwoyou;
import com.hmlyinfo.app.soutu.scenicTicket.mapper.TicketRenwoyouMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class TicketRenwoyouService extends BaseService<TicketRenwoyou, Long> {

    @Autowired
    private TicketRenwoyouMapper<TicketRenwoyou> mapper;
    @Autowired
    private ScenicInfoService scenicInfoService;

    @Override
    public BaseMapper<TicketRenwoyou> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    @Override
    public List<TicketRenwoyou> list(Map<String, Object> paramMap) {
        Object scenicIdStr = paramMap.remove("scenicId");
        if (scenicIdStr != null) {
            List<Long> scenicIdList = new ArrayList<Long>();
            scenicInfoService.prepareScenic(scenicIdList, Long.valueOf(scenicIdStr.toString()));
            paramMap.put("scenicIds", scenicIdList);
            return super.list(paramMap);
        }
        
        return super.list(paramMap);
    }
    
    public List<TicketRenwoyou> listByScenicId(Map<String, Object> paramMap) 
    {
        return super.list(paramMap);
    }
}
