package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.SysUnitQualificationDao;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.UnitQualification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2016/12/26.
 */
@Service
public class SysUnitQualificationService {

    @Resource
    private SysUnitQualificationDao unitQualificationDao;

    public void saveQuanlificationAll(List<UnitQualification> qualificationList, SysUnit unit) {
        deleteByUnit(unit);
        for (UnitQualification sysUnitQualification : qualificationList) {
            UnitQualification qualification = new UnitQualification();
            qualification.setName(sysUnitQualification.getName());
            qualification.setPath(sysUnitQualification.getPath());
            qualification.setType(sysUnitQualification.getType());
            qualification.setSysUnit(unit);
            unitQualificationDao.save(qualification);
        }
    }

    public void deleteByUnit(SysUnit unit) {
        String sql = "delete from sys_unit_qualification where unitId=?";
        unitQualificationDao.updateBySQL(sql, unit.getId());
    }
}
