package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.scenic.dao.ScenicOtherDao;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScenicOtherService {

//    Logger logger = Logger.getLogger(ScenicOtherService.class);

    @Resource
    private ScenicOtherDao scenicOtherDao;

    public void save(ScenicOther scenicOther) {
        scenicOtherDao.save(scenicOther);
    }

    public List<ScenicOther> list(ScenicOther scenicOther, Page page, String... orderProperties) {
        Criteria<ScenicOther> criteria = createCriteria(scenicOther, orderProperties);
        if (page != null) {
            return scenicOtherDao.findByCriteria(criteria, page);
        }
        return scenicOtherDao.findByCriteria(criteria);
    }

    public List<ScenicOther> listByCriteria(Criteria<ScenicOther> criteria, Page page) {
        return scenicOtherDao.findByCriteria(criteria, page);
    }

    private Criteria<ScenicOther> createCriteria(ScenicOther scenicOther, String... orderProperties) {
        Criteria<ScenicOther> criteria = new Criteria<ScenicOther>(ScenicOther.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(scenicOther.getSource())) {
            criteria.like("source", scenicOther.getSource());
        }
        if (StringUtils.isNotBlank(scenicOther.getSourceId())) {
            criteria.eq("sourceId", scenicOther.getScenicInfo());
        }

        return criteria;
    }

}
