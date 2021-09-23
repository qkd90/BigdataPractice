package com.data.data.hmly.service;

import com.data.data.hmly.action.mobile.response.*;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.*;
import com.data.data.hmly.service.line.entity.*;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-07-22,0022.
 */
@Service
public class LineMobileService {
    @Resource
    private LineService lineService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private LineexplainService lineexplainService;
    @Resource
    private LinetypepricedateService linetypepricedateService;
    @Resource
    private LineImagesService lineImagesService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private LinedaysService linedaysService;
    @Resource
    private LineDaysPlanInfoService lineDaysPlanInfoService;

    public LineResponse lineDetail(Long lineId) {
        Line line = lineService.loadLine(lineId);
        LineResponse response = new LineResponse(line);

        TbArea startCity = tbAreaService.getById(line.getStartCityId());
        if (startCity != null) {
            response.setStartCity(startCity.getName());
        }

        response.setArriveCityId(line.getArriveCityId());

        Lineexplain lineexplain = lineexplainService.getByLine(line.getId());
        if (lineexplain != null) {
            response.setLineLightPoint(lineexplain.getLineLightPoint());
        }

        List<Linetypeprice> linetypeprices = Lists.newArrayList(line.getLinetypeprices());
        List<Long> typepriceIds = Lists.transform(linetypeprices, new Function<Linetypeprice, Long>() {
            @Override
            public Long apply(Linetypeprice input) {
                return input.getId();
            }
        });
        Date date = new Date();
        Float min = Float.MAX_VALUE;
        for (Long typepriceId : typepriceIds) {
            List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(typepriceId, date, null);
            for (Linetypepricedate linetypepricedate : linetypepricedates) {
                min = Math.min(min, linetypepricedate.getDiscountPrice() + linetypepricedate.getRebate());
            }
        }
        if (!min.equals(Float.MAX_VALUE)) {
            response.setPrice(min);
        }

        List<LineImages> images = lineImagesService.listImagesByLineId(line.getId(), LineImageType.home);
        if (!images.isEmpty()) {
            response.setCover(images.get(0).getImageUrl());
        }

        Linetypeprice linetypepriceCondition = new Linetypeprice();
        linetypepriceCondition.setLine(line);
        List<Linetypeprice> linetypepriceList = linetypepriceService.findLinetypepriceList(linetypepriceCondition);
        response.setLinetypepriceList(Lists.transform(linetypepriceList, new Function<Linetypeprice, LineTypePriceResponse>() {
            @Override
            public LineTypePriceResponse apply(Linetypeprice input) {
                return new LineTypePriceResponse(input);
            }
        }));

        return response;
    }

    public List<LineDayResponse> lineDayList(Long lineId) {
        Linedays linedaysCondition = new Linedays();
        linedaysCondition.setLineId(lineId);
        List<Linedays> linedaysList = linedaysService.list(linedaysCondition, null);
        List<LineDayResponse> lineDayResponses = Lists.newArrayList();
        for (Linedays linedays : linedaysList) {
            LineDayResponse lineDayResponse = new LineDayResponse(linedays);
            List<LineDayPlanResponse> lineDayPlanResponses = Lists.newArrayList();
            for (Linedaysplan linedaysplan : linedays.getLinedaysplan()) {
                LineDayPlanResponse lineDayPlanResponse = new LineDayPlanResponse(linedaysplan);
                lineDayPlanResponse.setPlanInfoList(Lists.transform(lineDaysPlanInfoService.listByLineDaysPlanId(linedaysplan.getId()), new Function<LineDaysPlanInfo, LineDayPlanInfoResponse>() {
                    @Override
                    public LineDayPlanInfoResponse apply(LineDaysPlanInfo input) {
                        return new LineDayPlanInfoResponse(input);
                    }
                }));
                lineDayPlanResponse.setPlanInfoImagesList(Lists.transform(lineImagesService.listByLineDaysPlanId(linedaysplan.getId(), LineImageType.detail), new Function<LineImages, LineDayPlanImageResponse>() {
                    @Override
                    public LineDayPlanImageResponse apply(LineImages input) {
                        return new LineDayPlanImageResponse(input);
                    }
                }));
                lineDayPlanResponses.add(lineDayPlanResponse);
            }
            lineDayResponse.setLinedaysplan(lineDayPlanResponses);
            lineDayResponses.add(lineDayResponse);
        }
        return lineDayResponses;
    }
}
