package com.data.data.hmly.action.recplan;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.ErrorCode;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.constants.BizConstants;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.base.util.Validate;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.vo.RecommendPlanCountInfo;
import com.data.data.hmly.service.recplan.RecplanMgrService;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RecplanAction extends FrameBaseAction {

    @Resource
    private RecplanMgrService recplanMgrService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private AreaService areaService;


    /**
     * 删除
     * <ul>
     * <li>必选:标识{id}<li>
     * <li>url:/api/recplan/del</li>
     * </ul>
     *
     * @return
     */
    public Result del() {
        final HttpServletRequest request = getRequest();
        String id  = request.getParameter("id");
        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        Long recplanId = Long.parseLong(id);
        recommendPlanService.delById(recplanId);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    public Result toList() {
        return dispatch("/WEB-INF/jsp/recplan/recplanList.jsp");
    }


    /**
     * 查询列表
     * <ul>
     * <li>可选：分页大小{pageSize=10}</li>
     * <li>可选：请求页码{page=1}</li>
     * <li>url:/api/recplan/list</li>
     * </ul>
     *
     * @return
     */
    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<Map<String, Object>> result = recplanMgrService.pageRecplan(paramMap);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 统计行程信息<br>
     * 包括上/下架,草稿等等
     *
     * @return
     */
    public Result countRecplanInfo() {
        ResultModel<RecommendPlanCountInfo> result = recplanMgrService.countRecplan();
        return json(JSONObject.fromObject(result));
    }


    /**
     * 批量删除
     * @return
     */
    public Result batchDel() {
        final HttpServletRequest request = getRequest();
        String ids = request.getParameter("ids");
        Validate.notNull(ids, ErrorCode.ERROR_50001, "标识{ids}不能为空");
        recplanMgrService.batchDel(ids);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    /**
     * 批量上架
     * @return
     */
    public Result batchUpSell() {
        final HttpServletRequest request = getRequest();
        String ids = request.getParameter("ids");
        Validate.notNull(ids, ErrorCode.ERROR_50001, "标识{ids}不能为空");
        recplanMgrService.batchOpt(ids, BizConstants.RECPLAN_STATUS_UP);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    /**
     * 批量下架
     * @return
     */
    public Result batchDownSell() {
        final HttpServletRequest request = getRequest();
        String ids = request.getParameter("ids");
        Validate.notNull(ids, ErrorCode.ERROR_50001, "标识{ids}不能为空");
        recplanMgrService.batchOpt(ids, BizConstants.RECPLAN_STATUS_DOWN);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }
}
