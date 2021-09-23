package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipVisaDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CruiseShipVisaService {
    @Resource
    private CruiseShipVisaDao cruiseShipVisaDao;
}
