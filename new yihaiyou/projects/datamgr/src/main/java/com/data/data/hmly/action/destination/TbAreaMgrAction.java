package com.data.data.hmly.action.destination;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.AjaxList;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.destination.TbAreaMgrService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.TbAreaExtend;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * Created by zzl on 2016/1/23.
 */
public class TbAreaMgrAction extends FrameBaseAction {

    @Resource
    private TbAreaMgrService tbAreaMgrService;

    public TbArea tbArea = new TbArea();

    public Result toList() {
        return dispatch("/WEB-INF/jsp/destination/desList.jsp");
    }
    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<TbArea> result = tbAreaMgrService.page(TbArea.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("tbAreaExtend")));
    }

    public Result edit() {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        TbArea tbArea = tbAreaMgrService.load(id);
        request.setAttribute("tbArea", tbArea);
        return dispatch("/WEB-INF/jsp/destination/desEdit.jsp");
    }
    public Result saveDes() {
        final HttpServletRequest request = getRequest();
        if (tbArea.getId() != null && tbArea.getId() > 0) {
            TbArea sourceTbArea = tbAreaMgrService.load(tbArea.getId());
            createTbArea(sourceTbArea);
            tbAreaMgrService.update(sourceTbArea);
        }
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    private TbArea createTbArea(TbArea sourceTbArea) {
        sourceTbArea.setPinyin(tbArea.getPinyin());
//        sourceTbArea.setTbAreaExtend(getTbAreaExtend(sourceTbArea));
        getTbAreaExtend(sourceTbArea);
        return sourceTbArea;
    }

    private TbAreaExtend getTbAreaExtend(TbArea sourceTbArea) {
        TbAreaExtend tbAreaExtend = sourceTbArea.getTbAreaExtend();
        if (tbAreaExtend == null) {
            tbAreaExtend = new TbAreaExtend();
        }
        tbAreaExtend.setTbArea(sourceTbArea);
        if (tbArea.getTbAreaExtend().getBestVisitTime() != null) {
            tbAreaExtend.setBestVisitTime(tbArea.getTbAreaExtend().getBestVisitTime().trim());
        }
        if (tbArea.getTbAreaExtend().getAdviceTime() != null) {
            tbAreaExtend.setAdviceTime(tbArea.getTbAreaExtend().getAdviceTime().trim());
        }
        if (tbArea.getTbAreaExtend().getAbs() != null) {
            tbAreaExtend.setAbs(tbArea.getTbAreaExtend().getAbs().trim());
        }
        if (tbArea.getTbAreaExtend().getHistory() != null) {
            tbAreaExtend.setHistory(tbArea.getTbAreaExtend().getHistory().trim());
        }
        if (tbArea.getTbAreaExtend().getArt() != null) {
            tbAreaExtend.setArt(tbArea.getTbAreaExtend().getArt().trim());
        }
        if (tbArea.getTbAreaExtend().getWeather() != null) {
            tbAreaExtend.setWeather(tbArea.getTbAreaExtend().getWeather().trim());
        }
        if (tbArea.getTbAreaExtend().getGeography() != null) {
            tbAreaExtend.setGeography(tbArea.getTbAreaExtend().getGeography().trim());
        }
        if (tbArea.getTbAreaExtend().getEnvironment() != null) {
            tbAreaExtend.setEnvironment(tbArea.getTbAreaExtend().getEnvironment().trim());
        }
        if (tbArea.getTbAreaExtend().getCulture() != null) {
            tbAreaExtend.setCulture(tbArea.getTbAreaExtend().getCulture().trim());
        }
        if (tbArea.getTbAreaExtend().getLanguage() != null) {
            tbAreaExtend.setLanguage(tbArea.getTbAreaExtend().getLanguage().trim());
        }
        if (tbArea.getTbAreaExtend().getFestival() != null) {
            tbAreaExtend.setFestival(tbArea.getTbAreaExtend().getFestival().trim());
        }
        if (tbArea.getTbAreaExtend().getReligion() != null) {
            tbAreaExtend.setReligion(tbArea.getTbAreaExtend().getReligion().trim());
        }
        if (tbArea.getTbAreaExtend().getReligion() != null) {
            tbAreaExtend.setReligion(tbArea.getTbAreaExtend().getReligion().trim());
        }
        if (tbArea.getTbAreaExtend().getNation() != null) {
            tbAreaExtend.setNation(tbArea.getTbAreaExtend().getNation().trim());
        }
        if (tbArea.getTbAreaExtend().getCover() != null) {
            tbAreaExtend.setCover(tbArea.getTbAreaExtend().getCover());
        }
        return tbAreaExtend;
    }
    public Result upload() {
        final HttpServletRequest request = getRequest();
        MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;
        File file = multiPartRequestWrapper.getFiles("file")[0];
        if (file == null || file.length() > 10000000) {
            return json(JSONObject.fromObject(AjaxList.createFail()));
        }
        String filename = multiPartRequestWrapper.getParameter("name");
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = "destination/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(file, path);
        return json(JSONObject.fromObject(AjaxList.createSuccess(path)));
    }
    public TbArea getTbArea() {
        return tbArea;
    }

    public void setTbArea(TbArea tbArea) {
        this.tbArea = tbArea;
    }
}
