package com.data.data.hmly.service.cruiseship.aspect;

import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/9/23.
 */
@Aspect
@Service
public class CruiseShipAspect {

    @Resource
    private CruiseShipService cruiseShipService;

    @After("(execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDao.save(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDao.saveAll(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDao.update(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDao.updateAll(..)))")
    public void cruiseShipFinds(JoinPoint joinPoint) {
        try {
            Object[] objs = joinPoint.getArgs();
            // 判断是单条还是多条
            if (objs[0] instanceof List) {
                List<CruiseShip> cruiseShipList = (List<CruiseShip>) objs[0];
                for (CruiseShip cruiseShip : cruiseShipList) {
                    cruiseShipService.index(cruiseShip);
                }
            } else {
                // 单条情况
                CruiseShip cruiseShip = (CruiseShip) objs[0];
                cruiseShipService.index(cruiseShip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("(execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.save(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.saveAll(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.update(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.updateAll(..)))")
    public void cruiseShipDateVists(JoinPoint joinPoint) {
        try {
            Object[] objs = joinPoint.getArgs();
            CruiseShipDate cruiseShipDate = null;
            if (objs[0] instanceof List) {
                List<CruiseShipDate> cruiseShipDateList = (List<CruiseShipDate>) objs[0];
                cruiseShipDate = cruiseShipDateList.get(0);
            } else {
                cruiseShipDate = (CruiseShipDate) objs[0];
            }
            CruiseShip cruiseShip = cruiseShipDate.getCruiseShip();
            cruiseShip = cruiseShipService.findById(cruiseShip.getId());
            if (cruiseShip != null) {
                cruiseShipService.index(cruiseShip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("(execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.save(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.saveAll(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.update(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.updateAll(..)))")
    public void cruiseShipRoomDateVisits(JoinPoint joinPoint) {
        try {
            Object[] objs = joinPoint.getArgs();
            CruiseShipRoomDate cruiseShipRoomDate = null;
            Long cruiseShipId = null;
            if (objs[0] instanceof List) {
                List<CruiseShipRoomDate> cruiseShipRoomDateList = (List<CruiseShipRoomDate>) objs[0];
                cruiseShipRoomDate = cruiseShipRoomDateList.get(0);
            } else {
                cruiseShipRoomDate = (CruiseShipRoomDate) objs[0];
            }
            cruiseShipId = cruiseShipRoomDate.getCruiseShipId();
            if (cruiseShipId != null) {
                CruiseShip cruiseShip = cruiseShipService.findById(cruiseShipId);
                cruiseShipService.index(cruiseShip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
