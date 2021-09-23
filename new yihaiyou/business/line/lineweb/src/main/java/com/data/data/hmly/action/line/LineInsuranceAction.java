package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.line.vo.LineInsuranceVo;
import com.data.data.hmly.service.line.LineInsuranceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.LineInsurance;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/7/7.
 */
public class LineInsuranceAction extends FrameBaseAction {

    private Long lineId;

    @Resource
    private LineInsuranceService lineInsuranceService;


    public Result getSimLineInsuranceData() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (lineId != null && lineId > 0) {
            LineInsurance condition = new LineInsurance();
            Line line = new Line();
            line.setId(lineId);
            condition.setLine(line);
            List<LineInsurance> lineInsuranceList = lineInsuranceService.list(condition, null);
            List<LineInsuranceVo> lineInsuranceVos = new ArrayList<LineInsuranceVo>();
            for (LineInsurance lineInsurance : lineInsuranceList) {
                LineInsuranceVo lineInsuranceVo = new LineInsuranceVo();
                lineInsuranceVo.setInsuranceId(lineInsurance.getInsurance().getId());
                lineInsuranceVo.setIsRec(lineInsurance.getIsRec());
                lineInsuranceVos.add(lineInsuranceVo);
            }
            result.put("success", true);
            result.put("data", lineInsuranceVos);
            result.put("msg", "成功获取线路保险数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        result.put("msg", "无法获取线路保险数据, 没有线路id");
        return json(JSONObject.fromObject(result));
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }
}
