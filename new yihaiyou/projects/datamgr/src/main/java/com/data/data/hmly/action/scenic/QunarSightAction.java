package com.data.data.hmly.action.scenic;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.scenic.QunarSightMgrService;
import com.data.data.hmly.service.scenic.SolrScenicMgrService;
import com.data.data.hmly.service.scenic.entity.QunarSight;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;

import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/5/29.
 * Updated by zzl on 2015/12/09.
 */
public class QunarSightAction extends FrameBaseAction {

    @Resource
    private QunarSightMgrService qunarSightMgrService;
    @Resource
    private SolrScenicMgrService solrScenicMgrService;

    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<QunarSight> result = qunarSightMgrService.page(QunarSight.class, paramMap);
        return json(JSONObject.fromObject(result));
    }

    public Result toList() {
        return dispatch("/WEB-INF/jsp/qunar/sightList.jsp");
    }

    public Result getSimilar() {
        final HttpServletRequest request = getRequest();
        long sid = Long.parseLong(request.getParameter("sid"));
        String sname = request.getParameter("sname");
        List<ScenicInfo> scenics = null;
        try {
            scenics = solrScenicMgrService.findScenicInfoByTitle(sname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("sid", sid);
        request.setAttribute("scenics", scenics);
        return dispatch("/WEB-INF/jsp/qunar/sightSimilar.jsp");
    }

    public Result saveRelation() throws IOException {
        final HttpServletRequest request = getRequest();
        long sightId = Long.parseLong(request.getParameter("sightId"));
        long scenicId = Long.parseLong(request.getParameter("scenicId"));
        QunarSight old = qunarSightMgrService.selById(sightId);
        if (old != null) {
//            QunarSight sight = new QunarSight();
            old.setId(sightId);
            old.setScenicId(scenicId);
            qunarSightMgrService.updateOldSight(old);
            return json(JSONObject.fromObject(ActionResult.createSuccess()));
        } else {
            return json(JSONObject.fromObject(ActionResult.createFail(-1)));
        }
    }
}
