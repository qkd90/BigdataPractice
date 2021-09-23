package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.builder.TrafficBuilder;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
//import com.data.data.hmly.service.outOrder.OutOrderDispatchService;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.transportation.TransportationService;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.data.hmly.service.translation.train.Qunar.PassBy;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.FileUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Sane on 2016/01/03.
 */
public class TrafficWebAction extends JxmallAction {

    private static final int DESTINATION_NUMBER_SHOW_ON_INDEX = 7;
    @Resource
    private TrafficService trafficService;
    @Resource
    private TransportationService transportationService;
    @Resource
    private AreaService areaService;
    @Resource
    private TrafficBuilder trafficBuilder;
    @Resource
    private LabelService labelService;
    @Resource
    private TrafficPriceService trafficPriceService;
//    @Resource
//    private OutOrderDispatchService outOrderDispatchService;

    public TrafficType trafficType;
    public String cityCode;
    //车次
    public String trafficCode;
    //站点名
    public String name;
    //出发站点
    public String leavePort;
    //出发站点
    public String leavePortName;
    //到达站点
    public String arrivePort;
    //到达站点
    public String arrivePortName;
    //    第一天出发日期
    public String firstLeaveDate;
    //出发日期
    public String leaveDate;
    //出发城市
    public Long leaveCity;
    //城市名
    public String leaveCityName;

    //到达城市
    public Long arriveCity;
    //到达城市名
    public String arriveCityName;

    //中转日期(火车)
//    public String transitDate;
    //中转城市(火车)
    public Long transitCity;
    //城市名(火车)
    public String transitCityName;

    //返程日期
    public String returnDate;

    //出发日期(第一程)
    public String leaveDate1;
    //出发日期(第二程)
    public String leaveDate2;
    //    //出发城市(第二程)
//    public Long leaveCity2;
//    //城市名(第二程)
//    public String leaveCityName2;
    //到达城市(第二程)
    public Long arriveCity2;
    //到达城市名(第二程)
    public String arriveCityName2;


    // 单程与往返机票
    public String singleTrafficId; //已经改成hashCode(String)
    public String returnTrafficId; //已经改成hashCode(String)
    public String singleTrafficPriceId; //已经改成hashCode(String)
    public String returnTrafficPriceId; //已经改成hashCode(String)
    public String trafficKey;
    public String returnTrafficKey;

    //planBooking相关 参数
    public String code;
    public String json;

    private List<TbArea> hotDestinations;
    private List<Map<String, List<Object>>> trafficLetterSortAreas;

    public Result index() {
//        setAttribute(LXBConstants.LVXBANG_TRAFFIC_BANNER_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_TRAFFIC_BANNER));
        setAttribute(LXBConstants.LVXBANG_TRAFFIC_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_TRAFFIC_INDEX));
        setAttribute(LXBConstants.LVXBANG_TRAFFIC_CITY_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_TRAFFIC_CITY));
        return dispatch();
    }

    public Result toFlight() {
        // 渲染地点选择面板
        hotDestinations = areaService.getHomeHotArea();
        if (hotDestinations.size() > DESTINATION_NUMBER_SHOW_ON_INDEX) {
            hotDestinations = hotDestinations.subList(0, DESTINATION_NUMBER_SHOW_ON_INDEX);
        }
        Label label = new Label();
        label.setName("飞机目的地");
        List<Label> labelList = labelService.list(label, null);
        if (!labelList.isEmpty()) {
            List<TbArea> flightSortAreas = areaService.getTrafficAreas(labelList.get(0).getId());
            List<Map<String, Object>> flightSortMap = trafficBuilder.sortAreasList(flightSortAreas);
            trafficLetterSortAreas = trafficBuilder.letterSortAreasList(flightSortMap);
        }

        //联程
//        if (leaveDate2 != null && transitCity != null) {
        if (transitCity != null) {
            if (arriveCity2 != null) {
                arriveCity = arriveCity2;
                arriveCityName = arriveCityName2;
                leaveDate = leaveDate1;
                returnDate = leaveDate2;
            }
            return dispatch("/WEB-INF/jsp/lvxbang/traffic/flight_zz.jsp");
        }
        //返程
        if (returnDate != null && !"".equals(returnDate)) {
            return dispatch("/WEB-INF/jsp/lvxbang/traffic/flight_fc.jsp");
        }
        return dispatch("/WEB-INF/jsp/lvxbang/traffic/flight_dc.jsp");
    }

    public Result planbookingFlight() {
        return dispatch("/WEB-INF/jsp/lvxbang/traffic/flight_booking.jsp");
    }

    public Result toTrain() {
        // 渲染地点选择面板
        hotDestinations = areaService.getHomeHotArea();
        if (hotDestinations.size() > DESTINATION_NUMBER_SHOW_ON_INDEX) {
            hotDestinations = hotDestinations.subList(0, DESTINATION_NUMBER_SHOW_ON_INDEX);
        }
        Label label = new Label();
        label.setName("火车目的地");
        List<Label> labelList = labelService.list(label, null);
        if (!labelList.isEmpty()) {
            List<TbArea> flightSortAreas = areaService.getTrafficAreas(labelList.get(0).getId());
            List<Map<String, Object>> flightSortMap = trafficBuilder.sortAreasList(flightSortAreas);
            trafficLetterSortAreas = trafficBuilder.letterSortAreasList(flightSortMap);
        }

        //联程(中转)
        if (transitCity != null) {
            return dispatch("/WEB-INF/jsp/lvxbang/traffic/train_zz.jsp");
        }
        //返程
        if (returnDate != null && !"".equals(returnDate)) {
            return dispatch("/WEB-INF/jsp/lvxbang/traffic/train_fc.jsp");
        }
        return dispatch("/WEB-INF/jsp/lvxbang/traffic/train_dc.jsp");
    }

    public Result planBoolingTrain() {
        return dispatch("/WEB-INF/jsp/lvxbang/traffic/train_booing.jsp");
    }

    public Result listTrafficPrice() {
        if (leaveDate == null || (leaveCity == null && leavePort == null)
                || (arriveCity == null && arrivePort == null) || trafficType == null)
            return null;
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(leaveDate);
        } catch (ParseException e) {
            return null;
        }
        List<Traffic> traffics = trafficService.doQueryAndSaveByCityOrPort(leaveCity, leavePort, arriveCity, arrivePort, trafficType, date);
        if (traffics == null)
            return null;
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(traffics, jsonConfig));
    }

    /**
     * 传入名称,搜索火车站
     *
     * @return
     */
    public Result listTrainPort() {
        if (name == null)
            return null;
        return listTrafficPort(1, name);
    }


    /**
     * 传入名称,搜索火车站
     *
     * @return
     */
    public Result listFlightPort() {
        if (name == null)
            return null;
        return listTrafficPort(2, name);
    }

    /**
     * 传入名称,搜索站点
     *
     * @return
     */
    private Result listTrafficPort(int type, String name) {
        Transportation transportation = new Transportation();
        transportation.setSearchName(name);
        transportation.setType(type);
        transportation.setStatus(1);
        List<Transportation> traffics = transportationService.list(transportation, new Page(0, 10), "cityCode");
        if (traffics == null)
            return null;
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return json(JSONArray.fromObject(traffics, jsonConfig));
    }


//    /**
//     * 测试数据
//     *
//     * @param leaveCity
//     * @param arriveCity
//     * @param date
//     * @return
//     */
//    public List<Traffic> getTestTraffics(Long leaveCity, Long arriveCity, Date date) {
//        List<Traffic> traffics = new ArrayList<Traffic>();
//        for (int i = 0; i < 5; i++) {
//            TbArea city = new TbArea();
//            city.setId(leaveCity);
//            city.setName("厦门");
//            TbArea arrive = new TbArea();
//            arrive.setId(arriveCity);
//            city.setName("厦门");
//            Transportation trasportation = new Transportation();
//            trasportation.setId(123L);
//            trasportation.setName("测试站点");
//            Traffic traffic = new Traffic();
//            traffic.setTrafficType(TrafficType.AIRPLANE);
//
//            traffic.setLeaveCity(city);
//            traffic.setLeaveTransportation(trasportation);
//            traffic.setLeaveTime("8:00");
//            traffic.setArriveCity(arrive);
//            traffic.setArriveTransportation(trasportation);
//            traffic.setArriveTime("9:00");
//            traffic.setFlightTime(100l);
//            traffic.setCompany(i + "");
//            traffic.setTrafficModel("TEST");
//            traffic.setTrafficCode("ABC");
//            traffic.setCompanyCode("FM");
//            traffic.setStatus(ProductStatus.UP);
//            traffic.setHashCode(MD5.MD5Encode(new Gson().toJson(result)));
//            List<TrafficPrice> priceList = new ArrayList<TrafficPrice>();
//            for (int j = 0; j < 3; j++) {
//                TrafficPrice price = new TrafficPrice();
//                price.setTraffic(traffic);
//                price.setPrice((float) (float) 100 - i);
//                price.setSeatNum("1");
//                price.setSeatCode("F");
//                price.setSeatType("一等座");
//                price.setSeatName("一等座");
//                price.setChangePolicy("退换规则");
//                price.setBackPolicy("退换规则");
//                price.setLeaveTime(date);
//                price.setArriveTime(date);
//                price.setHashCode(MD5.Encode(new Gson().toJson(System.currentTimeMillis() + Math.random())));
//                priceList.add(price);
//                TrafficService.PLAN_TRAFFIC_PRICES.put(price.getHashCode(), price);
//                TrafficService.TRAIN_TRAFFIC_PRICES.put(price.getHashCode(), price);
//            }
//            traffic.setPrices(priceList);
//            traffics.add(traffic);
//            String cacheKey = TrafficService.makeTrafficKey(city.getId(), city.getId(), date);
//            TrafficService.PLAN_TRAFFIC.put(cacheKey, traffics);
//            TrafficService.TRAIN_TRAFFIC.put(cacheKey, traffics);
//
//        }
//        return traffics;
//    }


    public Result listTrainPassBy() {
        if (leavePortName == null || arrivePortName == null || trafficCode == null || leaveDate == null)
            return null;
        List<PassBy> passBy = trafficService.getPassBies(leavePortName, arrivePortName, trafficCode, leaveDate);
        return json(JSONArray.fromObject(passBy));
    }


    public List<TbArea> getHotDestinations() {
        return hotDestinations;
    }

    public void setHotDestinations(List<TbArea> hotDestinations) {
        this.hotDestinations = hotDestinations;
    }

    public List<Map<String, List<Object>>> getTrafficLetterSortAreas() {
        return trafficLetterSortAreas;
    }

    public void setTrafficLetterSortAreas(List<Map<String, List<Object>>> trafficLetterSortAreas) {
        this.trafficLetterSortAreas = trafficLetterSortAreas;
    }


    public Result orderSingleFlight() {
        //保存并获取trafficId和priceId
        String[] keys = splite(trafficKey);
        List<Traffic> traffics = TrafficService.findTraffic(keys[0], keys[1], "TRAIN".equals(keys[2]) ? TrafficType.TRAIN : TrafficType.AIRPLANE, keys[3]);
        if ( traffics == null || traffics.size() < 1) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("success", false);
            result.put("errMsg", "查询时间间隔过长，请重新查询");
            return json(JSONObject.fromObject(result));
        }

        Traffic traffic = getTrafficByHashCode(traffics, singleTrafficId);
//        traffic.setUser(outOrderDispatchService.getAccountUser());
//        traffic.setCompanyUnit(outOrderDispatchService.getCompanyUnit());
        traffic.setStatus(ProductStatus.UP);
        traffic.setSource(ProductSource.JUHE);
        if (TrafficType.AIRPLANE.equals(traffic.getTrafficType())) {
            traffic.setProType(ProductType.flight);
        } else if (TrafficType.TRAIN.equals(traffic.getTrafficType())) {
            traffic.setProType(ProductType.train);
        }
        traffic.setName(traffic.getLeaveTransportation().getName() + "-" + traffic.getArriveTransportation().getName() + "(" + traffic.getTrafficCode() + ")");
        traffic.setCreateTime(new Date());
        trafficService.save(traffic);
        TrafficPrice trafficPrice = getTrafficPriceByHashCode(traffic.getPrices(), singleTrafficPriceId);
        trafficPriceService.save(trafficPrice);
        Long singleTrafficPriceId = trafficPrice.getId();
        return redirect("/lvxbang/order/orderSingleFlight.jhtml?singleTrafficPriceId=" + singleTrafficPriceId);
//        return forward("/order/orderSingleFlight");
    }


    public Result orderReturnFlight() {
        //保存并获取trafficId和priceId

        String[] keys = splite(trafficKey);
        List<Traffic> traffics = TrafficService.findTraffic(keys[0], keys[1], "TRAIN".equals(keys[2]) ? TrafficType.TRAIN : TrafficType.AIRPLANE, keys[3]);
        if ( traffics == null || traffics.size() < 1) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("success", false);
            result.put("errMsg", "查询时间间隔过长，请重新查询");
            return json(JSONObject.fromObject(result));
        }
        Traffic traffic = getTrafficByHashCode(traffics, singleTrafficId);
//        traffic.setUser(outOrderDispatchService.getAccountUser());
//        traffic.setCompanyUnit(outOrderDispatchService.getCompanyUnit());
        traffic.setStatus(ProductStatus.UP);
        traffic.setSource(ProductSource.JUHE);
        if (TrafficType.AIRPLANE.equals(traffic.getTrafficType())) {
            traffic.setProType(ProductType.flight);
        } else if (TrafficType.TRAIN.equals(traffic.getTrafficType())) {
            traffic.setProType(ProductType.train);
        }
        traffic.setName(traffic.getLeaveTransportation().getName() + "-" + traffic.getArriveTransportation().getName() + "(" + traffic.getTrafficCode() + ")");
        traffic.setCreateTime(new Date());
        trafficService.save(traffic);
        TrafficPrice trafficPrice = getTrafficPriceByHashCode(traffic.getPrices(), singleTrafficPriceId);
        trafficPriceService.save(trafficPrice);
        Long singleTrafficPriceId = trafficPrice.getId();

        //保存并获取返程的trafficId和priceId
        String[] keys2 = splite(returnTrafficKey);
        List<Traffic> rtraffics = TrafficService.findTraffic(keys2[0], keys2[1], "TRAIN".equals(keys2[2]) ? TrafficType.TRAIN : TrafficType.AIRPLANE, keys2[3]);
        Traffic rtraffic = getTrafficByHashCode(rtraffics, returnTrafficId);
//        rtraffic.setUser(outOrderDispatchService.getAccountUser());
//        rtraffic.setCompanyUnit(outOrderDispatchService.getCompanyUnit());
        rtraffic.setStatus(ProductStatus.UP);
        rtraffic.setSource(ProductSource.JUHE);
        if (TrafficType.AIRPLANE.equals(rtraffic.getTrafficType())) {
            rtraffic.setProType(ProductType.flight);
        } else if (TrafficType.TRAIN.equals(rtraffic.getTrafficType())) {
            rtraffic.setProType(ProductType.train);
        }
        rtraffic.setName(rtraffic.getLeaveTransportation().getName() + "-" + rtraffic.getArriveTransportation().getName() + "(" + traffic.getTrafficCode() + ")");
        rtraffic.setCreateTime(new Date());
        trafficService.save(rtraffic);
        TrafficPrice rtrafficPrice = getTrafficPriceByHashCode(rtraffic.getPrices(), returnTrafficPriceId);
        trafficPriceService.save(rtrafficPrice);
        Long returnTrafficPriceId = rtrafficPrice.getId();
        return redirect("/lvxbang/order/orderReturnFlight.jhtml?singleTrafficPriceId=" + singleTrafficPriceId + "&returnTrafficPriceId=" + returnTrafficPriceId);
//        return forward("/order/orderReturnFlight");
    }

    public Result getTransPort() {
        String nameStr = (String) getParameter("nameStr");
        String typeStr = (String) getParameter("type");
        Integer type = null;
        if (StringUtils.isNotBlank(typeStr)) {
            type = Integer.valueOf(typeStr);
        }
        List<Transportation> list = transportationService.findBySearchName(nameStr, type);
        List<String> result = Lists.newArrayList();
        if (!list.isEmpty()) {
            result.add(list.get(0).getCityCode());
            result.add(list.get(0).getCode());
        }
        return json(JSONArray.fromObject(result));
    }

    private String[] splite(String key) {
        return key.split("##");
    }

    private TrafficPrice getTrafficPriceByHashCode(List<TrafficPrice> prices, String singleTrafficPriceId) {
        for (TrafficPrice price : prices) {
            if (price.getHashCode().equals(singleTrafficPriceId)) {
                return price;
            }
        }
        return null;
    }

    private Traffic getTrafficByHashCode(List<Traffic> traffics, String singleTrafficId) {
        for (Traffic traffic : traffics) {
            if (traffic.getHashCode().equals(singleTrafficId)) {
                return traffic;
            }
        }
        return null;
    }

    public SysUser getLoginUser() {
        User user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
        if (user != null) {
            SysUser sysUser = new SysUser();
            sysUser.setId(1l);
            return sysUser;
        } else {
            return null;
        }
    }
}
