package com.data.data.hmly.service;

import com.data.data.hmly.service.scenic.dao.ScenicInfoDao;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zzl on 2017/3/8.
 */
@Service
public class ScenicWebService {

    @Resource
    private ScenicInfoDao scenicInfoDao;
    @Resource
    private TicketPriceService ticketPriceService;


    public ScenicInfo findAllDetailById(Long id) {
        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);
        criteria.eq("id", id);
//        criteria.createCriteria("scenicAreas");
        ScenicInfo scenicInfo = scenicInfoDao.findUniqueByCriteria(criteria);
        if (scenicInfo != null) {
            // min price
            Float minPrice = ticketPriceService.findMinPriceByScenic(scenicInfo.getId(), new Date());
            if (minPrice != null) {
                scenicInfo.setMinPrice(Integer.toString(minPrice.intValue()));
            }
        }
        return scenicInfo;
    }
}
