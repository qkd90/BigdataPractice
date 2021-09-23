package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.request.PlanRecommendRequest;
import com.data.data.hmly.action.yihaiyou.request.PlanRecommendSimpleRequest;
import com.data.data.hmly.action.yihaiyou.response.TrafficRecommendResponse;
import com.data.data.hmly.action.yihaiyou.response.TrafficResponse;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.google.common.collect.Lists;
import com.zuipin.util.GlobalTheadPool;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class TrafficMobileService {
    @Resource
    private TrafficService trafficService;
    @Resource
    private TbAreaService tbAreaService;

    public List<TrafficRecommendResponse> recommendTraffics(PlanRecommendRequest request) throws ParseException {
        List<TrafficRecommendResponse> responseList = Lists.newArrayList();
        List<PlanRecommendSimpleRequest> plan = request.getPlan();
        Integer num = plan.size() * 2 + 2;
        CountDownLatch down = new CountDownLatch(num);
        Long fromCityId = request.getStartCityId();
        Date startDate = DateUtils.parseShortTime(request.getStartDate());
        for (PlanRecommendSimpleRequest simpleRequest : plan) {
            TrafficRecommendResponse response = new TrafficRecommendResponse();
            Long toCityId = simpleRequest.getCityId();
            TbArea fromCity = tbAreaService.getArea(fromCityId);
            TbArea toCity = tbAreaService.getArea(toCityId);
            response.setFromCityId(fromCity.getId());
            response.setFromCityName(fromCity.getName());
            response.setToCityId(toCity.getId());
            response.setToCityName(toCity.getName());
            response.setStartDate(DateUtils.formatShortDate(startDate));
            doFillPlane(response, startDate, down);
            doFillTrain(response, startDate, down);
            responseList.add(response);
            fromCityId = toCityId;
            Integer day = simpleRequest.getDay();
            startDate = DateUtils.add(startDate, Calendar.DATE, day + 1);
        }
        TrafficRecommendResponse response = new TrafficRecommendResponse();
        Long toCityId = request.getStartCityId();
        response.setFromCityId(fromCityId);
        response.setToCityId(toCityId);
        doFillPlane(response, startDate, down);
        doFillTrain(response, startDate, down);
        try {
            down.await();
            for (TrafficRecommendResponse recommendResponse : responseList) {
                List<TrafficResponse> list = recommendResponse.getTraffics();
                completeTrafficList(list, recommendResponse.getStartDate());
                recommendResponse.setTraffics(list);
            }
            completeTrafficList(response.getTraffics(), DateUtils.formatShortDate(startDate));
            responseList.get(responseList.size() - 1).setReturnTraffics(response.getTraffics());
        } catch (InterruptedException e) {

        }
        return responseList;
    }

    private void completeTrafficList(List<TrafficResponse> list, String leaveDate) {
        if (list.isEmpty()) {
            TrafficResponse plane = new TrafficResponse();
            plane.setTrafficType(TrafficType.AIRPLANE);
            plane.setLeaveDate(leaveDate);
            list.add(plane);
            TrafficResponse train = new TrafficResponse();
            train.setTrafficType(TrafficType.TRAIN);
            train.setLeaveDate(leaveDate);
            list.add(train);
        } else if (list.size() == 1) {
            TrafficResponse trafficResponse = new TrafficResponse();
            trafficResponse.setLeaveDate(leaveDate);
            if (list.get(0).getTrafficType() == TrafficType.AIRPLANE) {
                trafficResponse.setTrafficType(TrafficType.TRAIN);
            } else {
                trafficResponse.setTrafficType(TrafficType.AIRPLANE);
            }
            list.add(trafficResponse);
        }
        if (list.get(0).getTrafficType() == TrafficType.TRAIN) {
            Collections.swap(list, 0, 1);
        }
    }

    private void doFillPlane(final TrafficRecommendResponse response, final Date date, final CountDownLatch down) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return doFillPlaneTask(response, date, down);
            }
        });
    }

    private Object doFillPlaneTask(TrafficRecommendResponse response, Date date, CountDownLatch down) {
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            List<Traffic> planeList = trafficService.doQueryAndSaveByCityOrPort(response.getFromCityId(), "", response.getToCityId(), "", TrafficType.AIRPLANE, date);
            if (planeList == null || planeList.isEmpty()) {
                return null;
            }
            Collections.sort(planeList, new Comparator<Traffic>() {
                @Override
                public int compare(Traffic o1, Traffic o2) {
                    return getMinPrice(o1).intValue() - getMinPrice(o2).intValue();
                }
            });
            TrafficResponse trafficResponse = new TrafficResponse(planeList.get(0));
            trafficResponse.setTrafficType(TrafficType.AIRPLANE);
            response.getTraffics().add(trafficResponse);
            watch.stop();
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            down.countDown();
        }
    }

    private void doFillTrain(final TrafficRecommendResponse response, final Date date, final CountDownLatch down) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return doFillTrainTask(response, date, down);
            }
        });
    }

    private Object doFillTrainTask(TrafficRecommendResponse response, Date date, CountDownLatch down) {
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            List<Traffic> trainList = trafficService.doQueryAndSaveByCityOrPort(response.getFromCityId(), "", response.getToCityId(), "", TrafficType.TRAIN, date);
            if (trainList == null || trainList.isEmpty()) {
                return null;
            }
            Collections.sort(trainList, new Comparator<Traffic>() {
                @Override
                public int compare(Traffic o1, Traffic o2) {
                    return getMinPrice(o1).intValue() - getMinPrice(o2).intValue();
                }
            });
            TrafficResponse trafficResponse = new TrafficResponse(trainList.get(0));
            trafficResponse.setTrafficType(TrafficType.TRAIN);
            response.getTraffics().add(trafficResponse);
            watch.stop();
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            down.countDown();
        }
    }

    public Float getMinPrice(Traffic traffic) {
        List<TrafficPrice> list = traffic.getPrices();
        Collections.sort(list, new Comparator<TrafficPrice>() {
            @Override
            public int compare(TrafficPrice o1, TrafficPrice o2) {
                return o1.getPrice().intValue() - o2.getPrice().intValue();
            }
        });
        return list.get(0).getPrice();
    }
}
