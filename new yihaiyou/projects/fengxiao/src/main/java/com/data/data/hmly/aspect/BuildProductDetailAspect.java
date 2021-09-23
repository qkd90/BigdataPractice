package com.data.data.hmly.quartz;

import com.data.data.hmly.service.build.YhyBuildService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.zuipin.util.PropertiesManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2017/1/16.
 */
@Aspect
@Service
public class BuildProductDetailAspect {

    @Resource
    private TicketService ticketService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private YhyBuildService yhyBuildService;
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private PropertiesManager propertiesManager;

    @After("(execution (* com.data.data.hmly.service.ticket.dao.TicketDao.save(..))) " +
            "or (execution (* com.data.data.hmly.service.ticket.dao.TicketDao.insert(..))) " +
            "or (execution (* com.data.data.hmly.service.ticket.dao.TicketDao.update(..)))")
    public void ticketBuild(JoinPoint joinPoint) {
        try {
            Boolean bulidTicketDetail = propertiesManager.getBoolean("BUILD_TICKET_DETAIL");
            if (bulidTicketDetail != null && !bulidTicketDetail) {
                return;
            }
            Object[] objs = joinPoint.getArgs();
            Ticket ticket = (Ticket) objs[0];
            yhyBuildService.buildSailboatDetail(ticket.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("(execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDao.save(..))) " +
            "or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDao.insert(..))) " +
            "or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDao.update(..)))")
    public void cruiseShipBuild(JoinPoint joinPoint) {
        try {
            Boolean bulidCruiseDetail = propertiesManager.getBoolean("BUILD_CRUISE_DETAIL");
            if (bulidCruiseDetail != null && !bulidCruiseDetail) {
                return;
            }
            Object[] objs = joinPoint.getArgs();
            CruiseShip cruiseShip = (CruiseShip) objs[0];
            yhyBuildService.buildOneCruiseship(cruiseShip.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("(execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.save(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.saveAll(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.update(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipDateDao.updateAll(..)))")
    public void cruiseShipDateVists(JoinPoint joinPoint) {
        try {

            Boolean bulidCruiseDetail = propertiesManager.getBoolean("BUILD_CRUISE_DETAIL");
            if (bulidCruiseDetail != null && !bulidCruiseDetail) {
                return;
            }

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
                yhyBuildService.buildOneCruiseship(cruiseShip.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("(execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.save(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.saveAll(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.update(..))) or (execution(* com.data.data.hmly.service.cruiseship.dao.CruiseShipRoomDateDao.updateAll(..)))")
    public void cruiseShipRoomDateVisits(JoinPoint joinPoint) {
        try {

            Boolean bulidCruiseDetail = propertiesManager.getBoolean("BUILD_CRUISE_DETAIL");
            if (bulidCruiseDetail != null && !bulidCruiseDetail) {
                return;
            }
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
                yhyBuildService.buildOneCruiseship(cruiseShip.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
