package com.data.data.hmly.action.traffic;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sane on 2015/12/28.
 */
public class TrafficAction extends FrameBaseAction {
    @Resource
    private TrafficService trafficService;
    @Resource
    private TrafficPriceService trafficPriceService;

    private Traffic traffic = new Traffic();
    private TrafficPrice trafficPrice = new TrafficPrice();
    private Integer page = 1;
    private Integer rows = 10;
    private Map<String, Object> map = new HashMap<String, Object>();
    private String dateStartStr;
    private String dateEndStr;
    private Long productId;


    public Result upOrDownTraffic() {

        if (traffic.getId() != null) {
            Traffic tempTraffic = trafficService.get(traffic.getId());
            tempTraffic.setStatus(traffic.getStatus());
            trafficService.update(tempTraffic);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, true, "");
        }

        return jsonResult(map);
    }

    public Result delTraffic() {
        if (productId != null) {
            traffic.setId(productId);
            trafficService.delTraffic(traffic);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }

    public Result saveTraffic() {
        if (traffic.getId() == null) {
            traffic.setStatus(ProductStatus.DOWN);
            if (traffic.getTrafficType() == TrafficType.TRAIN) {
                traffic.setProType(ProductType.train);
            } else if(traffic.getTrafficType() == TrafficType.SHIP) {
                traffic.setProType(ProductType.ship);
            } else if (traffic.getTrafficType() == TrafficType.AIRPLANE) {
                traffic.setProType(ProductType.flight);
            }  else if (traffic.getTrafficType() == TrafficType.BUS) {
                traffic.setProType(ProductType.bus);
            }
            TbArea leaveCity = new TbArea();
            leaveCity.setId(traffic.getLeaveCity().getId());
            TbArea arriveCity = new TbArea();
            arriveCity.setId(traffic.getArriveCity().getId());
            traffic.setLeaveCity(leaveCity);
            traffic.setArriveCity(arriveCity);

            traffic.setCompanyUnit(getCompanyUnit());
            traffic.setUser(getLoginUser());
            traffic.setTopProduct(traffic);
            traffic.setCityId(leaveCity.getId());
            traffic.setCreateTime(new Date());
            traffic.setUpdateTime(new Date());
            trafficService.save(traffic);
            map.put("id", traffic.getId());
            simpleResult(map, true, "");
        } else {
            Traffic tempTraffic = trafficService.get(traffic.getId());

            tempTraffic.setName(traffic.getName());
            tempTraffic.setStatus(ProductStatus.DOWN);

            if (traffic.getTrafficType() == TrafficType.TRAIN) {
                tempTraffic.setProType(ProductType.train);
            } else if(traffic.getTrafficType() == TrafficType.SHIP) {
                tempTraffic.setProType(ProductType.ship);
            } else if (traffic.getTrafficType() == TrafficType.AIRPLANE) {
                tempTraffic.setProType(ProductType.flight);
            }  else if (traffic.getTrafficType() == TrafficType.BUS) {
                tempTraffic.setProType(ProductType.bus);
            }

            TbArea leaveCity = new TbArea();
            leaveCity.setId(traffic.getLeaveCity().getId());
            TbArea arriveCity = new TbArea();
            arriveCity.setId(traffic.getArriveCity().getId());
            tempTraffic.setLeaveCity(leaveCity);
            tempTraffic.setArriveCity(arriveCity);

            tempTraffic.setCityId(traffic.getLeaveCity().getId());
            tempTraffic.setLeaveTransportation(traffic.getLeaveTransportation());
            tempTraffic.setArriveTransportation(traffic.getArriveTransportation());

            tempTraffic.setLeaveTime(traffic.getLeaveTime());
            tempTraffic.setArriveTime(traffic.getArriveTime());
            tempTraffic.setFlightTime(traffic.getFlightTime());

            tempTraffic.setCompany(traffic.getCompany());
            tempTraffic.setCompanyCode(traffic.getCompanyCode());

            tempTraffic.setTrafficType(traffic.getTrafficType());
            tempTraffic.setTrafficCode(traffic.getTrafficCode());

            tempTraffic.setUpdateTime(new Date());

            trafficService.update(tempTraffic);
            map.put("id", tempTraffic.getId());
            simpleResult(map, true, "");
        }
        return jsonResult(map);
    }

    public Result addStep3() {
        if (productId != null) {
            traffic = trafficService.get(productId);
        }
        return dispatch();
    }

    public Result addStep21() {

//        String productId = (String) getParameter("productId");
        if (productId != null) {
            traffic = trafficService.get(productId);
        }
        return dispatch();
    }

    public Result addStep2() {

        String typePriceId = (String) getParameter("typePriceId");

        if (StringUtils.isNotBlank(typePriceId)) {
            trafficPrice = trafficPriceService.get(Long.parseLong(typePriceId));
        }

        if (productId != null) {
            traffic = trafficService.get(productId);
            trafficPrice.setTraffic(traffic);
        }

        // 起始时间 - 当前时间第二天
        Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
        // 结束时间 - 当前时间次月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");

        return dispatch();
    }

    public Result addStep1() {
        return dispatch();
    }
    public Result addWizard() {
        return dispatch();
    }

    public Result editWizard() {
        if (productId != null) {
            productId = productId;
        }
        return dispatch();
    }
    public Result editStep1() {
        if (productId != null) {
            traffic = trafficService.get(productId);
        }
        return dispatch();
    }


    public Result editStep2() {
        String typePriceId = (String) getParameter("typePriceId");

        if (StringUtils.isNotBlank(typePriceId)) {
            trafficPrice = trafficPriceService.get(Long.parseLong(typePriceId));
        }

        if (productId != null) {
            traffic = trafficService.get(productId);
            trafficPrice.setTraffic(traffic);
        }

        // 起始时间 - 当前时间第二天
        Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
        // 结束时间 - 当前时间次月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");
        return dispatch();
    }
    public Result editStep21() {
//        String productId = (String) getParameter("productId");
        if (productId != null) {
            traffic = trafficService.get(productId);
        }
        return dispatch();
    }

    public Result editStep3() {
        if (productId != null) {
            traffic = trafficService.get(productId);
        }
        return dispatch();
    }

    public Result list() {
        Page pageInfo = new Page(page, rows);
        if (traffic.getLeaveTransportation() != null) {
            System.out.println(traffic.getLeaveTransportation().getId());
        }
        List<Traffic> traffics = trafficService.list(traffic, pageInfo, "updateTime");
        String[] includes = new String[]{"leaveTransportation", "arriveTransportation"};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includes);
        return datagrid(traffics, pageInfo.getTotalCount(), jsonConfig);
    }


//  /traffic/traffic/trafficManage.jhtml
    public Result trafficManage() {
        return dispatch();
    }


    public Result getTrafficsByCity() {
        String leaveCity = (String) getParameter("leaveCity");
        String arriveCity = (String) getParameter("arriveCity");
        String trafficType = (String) getParameter("trafficType");
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse((String) getParameter("leaveTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TrafficType type = TrafficType.AIRPLANE;
        if (trafficType == "1") {
            type = TrafficType.SHIP;
        } else if (trafficType == "2") {
            type = TrafficType.TRAIN;
        }
        List<Traffic> trafficList = trafficService.doQueryAndSaveByCity(Long.parseLong(leaveCity), Long.parseLong(arriveCity), type, date);
        return json(JSONArray.fromObject(trafficList));
    }

    public Traffic getTraffic() {
        return traffic;
    }

    public void setTraffic(Traffic traffic) {
        this.traffic = traffic;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public TrafficPrice getTrafficPrice() {
        return trafficPrice;
    }

    public void setTrafficPrice(TrafficPrice trafficPrice) {
        this.trafficPrice = trafficPrice;
    }

    public String getDateStartStr() {
        return dateStartStr;
    }

    public void setDateStartStr(String dateStartStr) {
        this.dateStartStr = dateStartStr;
    }

    public String getDateEndStr() {
        return dateEndStr;
    }

    public void setDateEndStr(String dateEndStr) {
        this.dateEndStr = dateEndStr;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
