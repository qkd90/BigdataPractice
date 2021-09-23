package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.scenic.dao.ScenicThemeDao;
import com.data.data.hmly.service.scenic.entity.ScenicTheme;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScenicThemeService {

    Logger logger = Logger.getLogger(ScenicThemeService.class);

    @Resource
    private ScenicThemeDao scenicThemeDao;

    public List<ScenicTheme> listAll() {
        return list(new ScenicTheme(), null);
    }

    public List<ScenicTheme> list(ScenicTheme scenicTheme, Page page, String... orderProperties) {
        Criteria<ScenicTheme> criteria = createCriteria(scenicTheme, orderProperties);
        if (page == null) {
            return scenicThemeDao.findByCriteria(criteria);
        }
        return scenicThemeDao.findByCriteria(criteria, page);
        }

    public Criteria<ScenicTheme> createCriteria(ScenicTheme scenicTheme, String... orderProperties) {
        Criteria<ScenicTheme> criteria = new Criteria<ScenicTheme>(ScenicTheme.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(scenicTheme.getName())) {
            criteria.like("name", scenicTheme.getName());
        }
        return criteria;
    }

}
