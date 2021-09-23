package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.quartz.ctripdata.NctripTicketQuartz;
import com.data.data.hmly.service.cruiseship.CruiseShipProjectClassifyService;
import com.data.data.hmly.service.cruiseship.CruiseShipProjectImageService;
import com.data.data.hmly.service.cruiseship.CruiseShipProjectService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProject;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProjectClassify;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProjectImage;
import com.data.data.hmly.service.ctripcommon.CtripApiLogService;
import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import com.data.data.hmly.service.ctripcommon.enums.CtripTicketIcode;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.nctripticket.CtripTicketService;
import com.data.data.hmly.service.nctripticket.entity.CtripResourcePriceCalendar;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotResource;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CruiseShipProjectAction extends FrameBaseAction {

    @Resource
    private CruiseShipProjectService cruiseShipProjectService;
    @Resource
    private CruiseShipProjectImageService cruiseShipProjectImageService;
    @Resource
    private CtripTicketApiService ctripTicketApiService;
    @Resource
    private CtripTicketService ctripTicketService;
    @Resource
    private CtripApiLogService ctripApiLogService;
    @Resource
    private NctripTicketQuartz nctripTicketQuartz;
    /**
     * 页面字段
     */
    private CruiseShipProject cruiseShipProject = new CruiseShipProject();
    private Long shipId;
    private String imgPaths;
    private Long classifyId;
    private Long id;
    private CruiseShipProjectImage cruiseShipProjectImage = new CruiseShipProjectImage();

    //测试
    public Result calendar() throws Exception {
        List<Long> idList = ctripTicketService.getTicketIdList();
      //  List<Long> idList = Lists.newArrayList(7360L,235L);
        Date startDate = DateUtils.date(2017,7 , 18); // 5月12日
        Date endDate = DateUtils.date(2017, 10, 18);
        // 如果不止一页，循环更新

                /**//**//****** 业务接口开始 ******//**//**/
        try {
//                    String uuid = UUID.randomUUID().toString();
            // List<Long> ctripResourceIdList = Lists.newArrayList(idList);
            List<CtripScenicSpotResource> resources = ctripTicketService.listResourceBy(idList);
            List<CtripResourcePriceCalendar> resourcePriceCalendars = ctripTicketApiService.doGetResourcePriceCalendar(idList, startDate, endDate);
            // 更新到本地数据库
            ctripTicketService.updateResourcePriceCalendar(resourcePriceCalendars, resources, startDate, endDate);
            // 更新日志状态
        } catch (Exception e) {
            e.printStackTrace();
        }

        /******
         * 业务接口结束
         ******/

        result.put("success", true);
        result.put("msg", "保存项目信息成功!");

        return json(JSONObject.fromObject(result));
    }

    //测试更新门票
    public void updateTicketDatePrice(Date startDate, Date endDate) {
        ctripTicketService.updateTicketDatePrice(startDate, endDate);
    }

    public void updateTicket() {
        ctripTicketService.updateTicket();
    }

    public void syncTicket() throws Exception {

        // 携程门票资源如果不存在，则清除对应的价格日历数据
        //    ctripTicketService.doClearPriceNotResource();
        Date priceCalendarStartTime = DateUtils.date(2017, 7, 18); // 5月12日
        Date priceCalendarEndTime = DateUtils.date(2017, 7, 25);
        updateTicket();
        updateTicketDatePrice(priceCalendarStartTime, priceCalendarEndTime);
    }
    // 景点静态页面生成
        /*String url = propertiesManager.getString("FG_DOMAIN") + "/build/lxb/buildScenicDetail.jhtml";
        doHttpRequestGet(url, null);*//*
    }*/


    //测试景点详情
    public Result test() throws Exception {


//        Integer total = scenicSpotInfos.size();
//        Integer pageSize = 20;
//        Integer index = 0;
//        while (index < total) {
//        Integer toIndex = index + pageSize;
//        if (toIndex > total) {  // 判断是否超过总数
//            toIndex = total;
//        }
        /****** 业务接口开始 ******/
        try {
            //           controlFrequence(CtripTicketIcode.SCENICSPOT_INFO);
//                List<Long> subScenicSpotIdList = scenicSpotIdList.subList(index, toIndex);
//                uuid = UUID.randomUUID().toString();


            List<CtripScenicSpotInfo> scenicSpotInfos = ctripTicketApiService.doGetScenicSpotInfo();

            // 更新到本地数据库
            ctripTicketService.updateScenicSpotInfo(scenicSpotInfos);

        } catch (Exception e) {
            e.printStackTrace();
            // 更新错误信息
//               ctripApiLogService.updateErrorInfo(uuid, "-1", "非接口异常");
        }
        /****** 业务接口结束 ******/
//            index = toIndex;

//       }

        result.put("success", true);
        result.put("msg", "保存项目信息成功!");

        return json(JSONObject.fromObject(result));

    }




//定时
    public Result quartz() throws Exception{
        nctripTicketQuartz.syncTicket();
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }






    //查询
    public Result list() {
        List<CruiseShipProject> cruiseShipProjects = new ArrayList<CruiseShipProject>();
        if (classifyId != null && shipId != null) {
            CruiseShipProject cruiseShipProject = new CruiseShipProject();
//            cruiseShipProject.setClassifyId(classifyId);
//            cruiseShipProject.setShipId(shipId);
            cruiseShipProjects = cruiseShipProjectService.listById(shipId, classifyId);
        }

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("cruiseShipProjectClassify");
        return datagrid(cruiseShipProjects, jsonConfig);
    }

    //保存或更新项目信息
    public Result save() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (cruiseShipProject.getId() != null) {
            // 更新项目信息
            CruiseShipProject sourceCruiseShipProject = cruiseShipProjectService.get(cruiseShipProject.getId());
            sourceCruiseShipProject.setLevel(cruiseShipProject.getLevel());
            sourceCruiseShipProject.setSuitType(cruiseShipProject.getSuitType());
            sourceCruiseShipProject.setCostStatus(cruiseShipProject.getCostStatus());
            sourceCruiseShipProject.setOpenStatus(cruiseShipProject.getOpenStatus());
            sourceCruiseShipProject.setCoverImage(cruiseShipProject.getCoverImage());
            sourceCruiseShipProject.setShipId(cruiseShipProject.getShipId());
            sourceCruiseShipProject.setIntroduction(cruiseShipProject.getIntroduction());
            sourceCruiseShipProject.setPeopleNum(cruiseShipProject.getPeopleNum());
            sourceCruiseShipProject.setCruiseShipProjectClassify(cruiseShipProject.getCruiseShipProjectClassify());
            sourceCruiseShipProject.setUpdateTime(new Date());
            cruiseShipProjectService.update(sourceCruiseShipProject);
            result.put("success", true);
            result.put("msg", "更新项目信息成功!");
        } else {
            // 新增项目信息
            cruiseShipProject.setCreateTime(new Date());
            cruiseShipProject.setShowStatus(true);
            cruiseShipProjectService.save(cruiseShipProject);
        }
        // 图片处理
        final HttpServletRequest request = getRequest();
        String[] imgPaths = request.getParameterValues("imgPaths");
        String[] imgDeleteIds = request.getParameterValues("imgDeleteIds");
        String coverImgIdStr = request.getParameter("coverImgId");

        // 处理增加图片
        if (imgPaths != null && imgPaths.length > 0) {
            List<CruiseShipProjectImage> cruiseShipProjectImageList = new ArrayList<CruiseShipProjectImage>();
            for (String path : imgPaths) {
                CruiseShipProjectImage cruiseShipProjectImage = new CruiseShipProjectImage();
                cruiseShipProjectImage.setCruiseShipProject(cruiseShipProject);
                cruiseShipProjectImage.setProjectId(cruiseShipProject.getId());
                if (path.equals(cruiseShipProject.getCoverImage())) {
                    // 先删除之前的封面
                    cruiseShipProjectImageService.doDelCoverByProject(cruiseShipProject.getId());
                    cruiseShipProjectImage.setCoverFlag(true);
                } else {
                    cruiseShipProjectImage.setCoverFlag(false);
                }
                cruiseShipProjectImage.setPath(path);
                cruiseShipProjectImage.setCreateTime(new Date());
                cruiseShipProjectImageList.add(cruiseShipProjectImage);
            }
            cruiseShipProjectImageService.saveAll(cruiseShipProjectImageList);
        }
        // 处理删除图片
        if (imgDeleteIds != null && imgDeleteIds.length > 0) {
            for (String s : imgDeleteIds) {
                cruiseShipProjectImageService.delById(Long.parseLong(s));
            }
        }
        // 处理封面
        if (StringUtils.isNotBlank(coverImgIdStr)) {
            Long coverImgId = Long.parseLong(coverImgIdStr);
            cruiseShipProjectImageService.doDelCoverByProject(cruiseShipProject.getId());
            cruiseShipProjectImageService.doSetCoverById(coverImgId);
        }

        result.put("success", true);
        result.put("msg", "保存项目信息成功!");

        return json(JSONObject.fromObject(result));
    }

    public Result getImageList() {

        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<CruiseShipProjectImage> cruiseShipProjectImageList = cruiseShipProjectImageService.findProjectimage(cruiseShipProjectImage, null);
        result.put("success", true);
        result.put("data", cruiseShipProjectImageList);

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("cruiseShipProjectImage");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result delProject() {
        if (id != null) {
            CruiseShipProject sourceCruiseShipProject = cruiseShipProjectService.get(id);
            sourceCruiseShipProject.setShowStatus(false);
            cruiseShipProjectService.update(sourceCruiseShipProject);
            result.put("success", true);
            result.put("msg", "删除成功!");
        }
        return json(JSONObject.fromObject(result));
    }

    public CruiseShipProject getCruiseShipProject() {
        return cruiseShipProject;
    }

    public void setCruiseShipProject(CruiseShipProject cruiseShipProject) {
        this.cruiseShipProject = cruiseShipProject;
    }

    public Long getshipId() {
        return shipId;
    }

    public void setshipId(Long shipId) {
        this.shipId = shipId;
    }

    public String getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(String imgPaths) {
        this.imgPaths = imgPaths;
    }

    public CruiseShipProjectService getCruiseShipProjectService() {
        return cruiseShipProjectService;
    }

    public void setCruiseShipProjectService(CruiseShipProjectService cruiseShipProjectService) {
        this.cruiseShipProjectService = cruiseShipProjectService;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CruiseShipProjectImage getCruiseShipProjectImage() {
        return cruiseShipProjectImage;
    }

    public void setCruiseShipProjectImage(CruiseShipProjectImage cruiseShipProjectImage) {
        this.cruiseShipProjectImage = cruiseShipProjectImage;
    }
}