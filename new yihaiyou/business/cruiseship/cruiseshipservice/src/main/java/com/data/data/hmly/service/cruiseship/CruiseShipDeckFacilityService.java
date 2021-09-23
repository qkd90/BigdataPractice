package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipDeckFacilityDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CruiseShipDeckFacilityService {
    @Resource
    private CruiseShipDeckFacilityDao cruiseShipDeckFacilityDao;
}
