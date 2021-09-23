package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.line.LineDepartureInfoService;
import com.data.data.hmly.service.line.LineDepartureService;
import com.data.data.hmly.service.line.LineInsuranceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.LineDeparture;
import com.data.data.hmly.service.line.entity.LineDepartureInfo;
import com.data.data.hmly.service.line.entity.LineInsurance;
import com.data.data.hmly.service.line.entity.enums.LineInsuranceStatus;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/6/21.
 */
public class LineDepartureAction  extends FrameBaseAction {

    @Resource
    private LineDepartureService lineDepartureService;

    @Resource
    private LineDepartureInfoService lineDepartureInfoService;

    private LineDeparture lineDeparture = new LineDeparture();
    private LineDepartureInfo lineDepartureInfo = new LineDepartureInfo();
    private Line line = new Line();
    private Long departureId;
    private Long departureInfoId;

    private Map<String, Object> map = new HashMap<String, Object>();


    public Result delDepartureInfo() {
        if (lineDepartureInfo.getId() != null) {
            lineDepartureInfoService.delDepartureInfo(lineDepartureInfo);
            simpleResult(map, true, "");
        }
        return jsonResult(map);
    }

    public Result delContact() {
        if (lineDeparture.getId() != null) {
            lineDepartureService.delDepartureInfo(lineDeparture);
            simpleResult(map, true, "");
        }
        return jsonResult(map);
    }

    public Result getBoarLocationList() {

        List<LineDepartureInfo> list = new ArrayList<LineDepartureInfo>();

        if (lineDepartureInfo.getDeparture().getId() == null) {
            return datagrid(list);
        } else {
            list = lineDepartureInfoService.getDepartureList(lineDepartureInfo);
            return datagrid(list);
        }

    }

    public Result saveDepartureInfo() {
        if (lineDepartureInfo.getId() == null) {
            if (StringUtils.isNotBlank(lineDepartureInfo.getStartTime())) {
                Date departureDate = DateUtils.getDate(lineDepartureInfo.getStartTime().toString(), "HH:mm:ss");
                lineDepartureInfo.setDepartureDate(departureDate);
            }

            lineDepartureInfoService.saveDepartureInfo(lineDepartureInfo);
            if (lineDepartureInfo.getId() != null) {
                simpleResult(map, true, "");
            } else {
                simpleResult(map, false, "");
            }
        }
        return jsonResult(map);
    }

    @Resource
    private LineInsuranceService lineInsuranceService;


    public Result saveDeparture() {
        final HttpServletRequest request = getRequest();
        if (line.getId() != null) {
            lineDeparture.setLine(line);
            // 保存线路保险方案
            String[] insuranceIds = request.getParameterValues("insuranceIds");
            String[] insuranceDelIds = request.getParameterValues("insuranceDelIds");
            // 推荐保险ID
            String recInsuranceIdStr = request.getParameter("recInsuranceId");
            if (insuranceIds != null && insuranceIds.length > 0) {
                List<LineInsurance> lineInsuranceList = new ArrayList<>();
                for (String insuranceIdStr : insuranceIds) {
                    Insurance insurance = new Insurance();
                    insurance.setId(Long.parseLong(insuranceIdStr));
                    LineInsurance lineInsurance = new LineInsurance();
                    lineInsurance.setInsurance(insurance);
                    lineInsurance.setLine(line);
                    lineInsurance.setStatus(LineInsuranceStatus.up);
                    lineInsurance.setCreateTime(new Date());
                    lineInsuranceList.add(lineInsurance);
                }
                lineInsuranceService.saveAll(lineInsuranceList);
            }
            // 删除线路保险方案
            if (insuranceDelIds != null && insuranceDelIds.length > 0) {
                for (String insuranceDelIdStr : insuranceDelIds) {
                    LineInsurance lineInsurance = lineInsuranceService.getUnique(line.getId(), Long.parseLong(insuranceDelIdStr));
                    lineInsuranceService.delete(lineInsurance);
                }
            }
            // 处理保险推荐方案
            List<LineInsurance> lineInsuranceList = lineInsuranceService.getByLine(line.getId());
            for (LineInsurance lineInsurance : lineInsuranceList) {
                if (StringUtils.hasText(recInsuranceIdStr)) {
                    if (lineInsurance.getInsurance().getId() == Long.parseLong(recInsuranceIdStr)) {
                        lineInsurance.setIsRec(true);
                    } else {
                        lineInsurance.setIsRec(false);
                    }
                } else {
                    lineInsurance.setIsRec(false);
                }
            }
            // 更新线路保险方案
            lineInsuranceService.updateAll(lineInsuranceList);
        } else {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        if (lineDeparture.getId() != null) {
            LineDeparture departure = lineDepartureService.loadDeparture(lineDeparture.getId());
            departure.setAutoSendInfo(lineDeparture.getAutoSendInfo());
            departure.setDepartureDesc(lineDeparture.getDepartureDesc());
            departure.setEmergencyContact(lineDeparture.getEmergencyContact());
            departure.setEmergencyPhone(lineDeparture.getEmergencyPhone());
            departure.setHasTransfer(lineDeparture.getHasTransfer());
            departure.setSignInfo(lineDeparture.getSignInfo());
            departure.setTransferDesc(lineDeparture.getTransferDesc());
            lineDepartureService.updateDeparture(departure);
            map.put("departureId", lineDeparture.getId());
            simpleResult(map, true, "");
        } else {
            lineDepartureService.saveDeparture(lineDeparture);
            map.put("departureId", lineDeparture.getId());
            simpleResult(map, true, "");
        }
        return jsonResult(map);
    }


    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public LineDeparture getLineDeparture() {
        return lineDeparture;
    }

    public void setLineDeparture(LineDeparture lineDeparture) {
        this.lineDeparture = lineDeparture;
    }

    public LineDepartureInfo getLineDepartureInfo() {
        return lineDepartureInfo;
    }

    public void setLineDepartureInfo(LineDepartureInfo lineDepartureInfo) {
        this.lineDepartureInfo = lineDepartureInfo;
    }

    public Long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Long departureId) {
        this.departureId = departureId;
    }

    public Long getDepartureInfoId() {
        return departureInfoId;
    }

    public void setDepartureInfoId(Long departureInfoId) {
        this.departureInfoId = departureInfoId;
    }
}
