package com.data.data.hmly.service.ctripdestination;

import com.data.data.hmly.service.ctripdestination.dao.CtripDestinationCityDao;
import com.data.data.hmly.service.ctripdestination.entity.CtripDestinationCity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CtripDestinationCityService {
    @Autowired
    private CtripDestinationCityDao CtripDestinationCityDao;

    public void saveAll(List<CtripDestinationCity> CtripDestinationCity) {
        CtripDestinationCityDao.save(CtripDestinationCity);
    }

    public void save(CtripDestinationCity CtripDestinationCity) {
        CtripDestinationCityDao.save(CtripDestinationCity);
    }

    public void update(CtripDestinationCity CtripDestinationCity) {
        CtripDestinationCityDao.update(CtripDestinationCity);
    }

    public List<CtripDestinationCity> gets(int size) {
        Page page = new Page(1, size);
        Criteria<CtripDestinationCity> c = new Criteria<CtripDestinationCity>(CtripDestinationCity.class);
        return CtripDestinationCityDao.findByCriteria(c, page);
    }


    public List<CtripDestinationCity> gets(int size, Criteria<CtripDestinationCity> c) {
        Page page = new Page(1, size);
        return CtripDestinationCityDao.findByCriteria(c, page);
    }
}
