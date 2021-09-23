package com.data.data.hmly.action.restaurant;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.AjaxList;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.restaurant.DelicacyMgrService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.DelicacyExtend;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZZL on 2015/11/30.
 */
public class DelicacyAction extends FrameBaseAction {

    @Resource
    private DelicacyMgrService delicacyMgrService;
    @Resource
    private TbAreaService tbAreaService;


    public Delicacy delicacy = new Delicacy();

    public Result toList() {
        return dispatch("/WEB-INF/jsp/restaurant/delicacyList.jsp");
    }

    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<Delicacy> result = delicacyMgrService.page(Delicacy.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("city", "extend")));
    }

    /**
     * 根据名称模糊查询美食
     *
     * @return
     */
    public Result listName() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        List<Map<String, String>> names = delicacyMgrService.listName(paramMap);
        return json(JSONObject.fromObject(ActionResult.createSuccess(names)));
    }

    public Result add() {
        final HttpServletRequest request = getRequest();
        Delicacy delicacy = new Delicacy();
        request.setAttribute("delicacy", delicacy);
        return dispatch("/WEB-INF/jsp/restaurant/delicacyEdit.jsp");
    }

    public Result edit() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        Delicacy delicacy = delicacyMgrService.info(id);
        request.setAttribute("delicacy", delicacy);
        return dispatch("/WEB-INF/jsp/restaurant/delicacyEdit.jsp");
    }

    public Result saveDelicacy() throws IOException {
        final HttpServletRequest request = getRequest();
        if (delicacy.getId() != null && delicacy.getId().longValue() > 0L) {
            Delicacy sourceDelicacy = delicacyMgrService.load(delicacy.getId());
            createDelicacy(sourceDelicacy, request);
            delicacyMgrService.update(sourceDelicacy);

        } else {
            delicacyMgrService.insert(delicacy);
//            createDelicacy(delicacy, request);
//            delicacyMgrService.update(delicacy);
        }
        //
        delicacyMgrService.buildAndIndex(delicacy);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    private Delicacy createDelicacy(Delicacy sourceDelicacy, final HttpServletRequest request) {
        Long cityCode = Long.parseLong(request.getParameter("cityId"));
        TbArea tbArea = tbAreaService.getArea(cityCode);
        sourceDelicacy.setCity(tbArea);
        sourceDelicacy.setCover(delicacy.getCover());
        sourceDelicacy.setName(delicacy.getName());
        sourceDelicacy.setPrice(delicacy.getPrice());
        sourceDelicacy.setCuisine(delicacy.getCuisine());
        sourceDelicacy.setTaste(delicacy.getTaste());
        getDelicacyExtend(sourceDelicacy, request);
        return delicacy;
    }

    private DelicacyExtend getDelicacyExtend(Delicacy sourceDelicacy, final HttpServletRequest request) {
        DelicacyExtend delicacyExtend = sourceDelicacy.getExtend();
        if (delicacyExtend == null) {
            delicacyExtend = new DelicacyExtend();
        }
        delicacyExtend.setIntroduction(delicacy.getExtend().getIntroduction());
        delicacyExtend.setRecommendNum(delicacy.getExtend().getRecommendNum());
        delicacyExtend.setAgreeNum(delicacy.getExtend().getAgreeNum());
        delicacyExtend.setShareNum(delicacy.getExtend().getShareNum());
        delicacyExtend.setRecommendReason(delicacy.getExtend().getRecommendReason());
        delicacyExtend.setDelicacy(sourceDelicacy);
        return delicacyExtend;
    }

    public  Result changeStatus() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("delicacyId")) && StringUtils.isNotBlank(request.getParameter("status"))) {
            Long delicacyId = Long.parseLong(request.getParameter("delicacyId"));
            Integer status = Integer.parseInt(request.getParameter("status"));
            Delicacy delicacy = delicacyMgrService.load(delicacyId);
            delicacy.setStatus(status);
            delicacyMgrService.update(delicacy);
            //
            delicacyMgrService.buildAndIndex(delicacy);
            delicacyMgrService.delLabelItems(delicacy);
            result.put("msg", "success");
            return json(JSONObject.fromObject(result));
        } else {
            result.put("msg", "error : no match parameter");
            return json(JSONObject.fromObject(result));
        }
    }

    public Result upload() throws IOException {
        final HttpServletRequest request = getRequest();
        MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;
        File file = multiPartRequestWrapper.getFiles("file")[0];
        String delicacyName = request.getParameter("foodName");
        if (delicacyName == null || file == null || file.length() > 10000000) {
            return json(JSONObject.fromObject(AjaxList.createFail()));
        }
        String filename = multiPartRequestWrapper.getParameter("name");
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = "delicacy/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(file, path);
        return json(JSONObject.fromObject(AjaxList.createSuccess(path)));
    }

    public Delicacy getDelicacy() {
        return delicacy;
    }
    public void setDelicacy(Delicacy delicacy) {
        this.delicacy = delicacy;
    }
}
