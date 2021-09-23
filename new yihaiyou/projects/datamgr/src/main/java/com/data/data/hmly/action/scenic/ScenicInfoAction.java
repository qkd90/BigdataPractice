package com.data.data.hmly.action.scenic;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.AjaxList;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.constants.BizConstants;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.base.util.JsonUtil;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.region.ScenicAreaMgrService;
import com.data.data.hmly.service.scenic.*;
import com.data.data.hmly.service.scenic.entity.ScenicGallery;
import com.data.data.hmly.service.scenic.entity.ScenicGeoinfo;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.data.data.hmly.service.scenic.entity.ScenicStatistics;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * updated By ZZL On 15/12/15
 */
public class ScenicInfoAction extends FrameBaseAction {
    @Resource
    private ScenicInfoMgrService scenicInfoMgrService;
//    @Resource
//    private ScenicInfoService scenicInfoService;
    @Resource
    private ScenicOtherMgrService scenicOtherMgrService;
    @Resource
    private ScenicGalleryMgrService scenicGalleryMgrService;
    @Resource
    private ScenicAreaMgrService scenicAreaMgrService;
    @Resource
    private ScenicGeoInfoMgrService scenicGeoInfoMgrService;
    @Resource
    private ScenicStatisticsMgrService scenicStatisticsMgrService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private ImageServiceCustom imageServiceCustom;

    private ScenicInfo scenicInfo = new ScenicInfo();

    /**
     * 返回景点列表页面
     *
     * @return
     */
    public Result toList() {

        return dispatch("/WEB-INF/jsp/scenic/scenicList.jsp");
    }

    public Result toSailboatList() {
        return dispatch("/WEB-INF/jsp/scenic/sailboatList.jsp");
    }

    /**
     * 取出景点列表并返回
     *
     * @return
     */
    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
        paramMap.put("isModified", 2);
        paramMap.put("notDraft", "1");
        ResultModel<ScenicInfo> result = scenicInfoMgrService.page(ScenicInfo.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("scenicOther", "scenicGeoinfo", "city")));
    }

    /**
     * 根据名称模糊查询景点
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result listName() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        if (paramMap.containsKey("name")) {
            String name = (String) paramMap.get("name");
            name = URLDecoder.decode(name, "UTF-8");
            paramMap.put("name", name);
        }
        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
        paramMap.put("isModified", 2);
        paramMap.put("status", 1);
        List<Map<String, String>> names = scenicInfoMgrService.listName(paramMap);
        return json(JSONObject.fromObject(ActionResult.createSuccess(names)));
    }

    /**
     * 根据名称查询模糊景点
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result listCoordinates() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
        paramMap.put("isModified", 2);
        paramMap.put("status", 1);
        List<Map<String, String>> names = scenicInfoMgrService.listCoordinates(paramMap);
        return json(JSONObject.fromObject(ActionResult.createSuccess(names)));
    }

    /**
     * 根据名称查询模糊景点
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result listGoogleCoordinates() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
        paramMap.put("isModified", 2);
        paramMap.put("status", 1);
        List<Map<String, String>> names = scenicInfoMgrService.listGoogleCoordinates(paramMap);
        return json(JSONObject.fromObject(ActionResult.createSuccess(names)));
    }

    public Result galleryList() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
        paramMap.put("type", "scenic");
        ResultModel<ScenicGallery> result = scenicGalleryMgrService.page(ScenicGallery.class, paramMap);
        return json(JSONObject.fromObject(request, JsonFilter.getIncludeConfig("scenicInfo")));
    }

    public Result scenicGalleryList() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
        paramMap.put("type", "scenic");
        ResultModel<ScenicGallery> result = scenicGalleryMgrService.page(ScenicGallery.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getFilterConfig("scenicInfo")));
    }

    public Result uploadImg() throws IOException {
        final HttpServletRequest request = getRequest();
        MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;
        File file = multiPartRequestWrapper.getFiles("file")[0];
        Long sid = Long.parseLong(multiPartRequestWrapper.getParameter("sid"));
        String type = multiPartRequestWrapper.getParameter("photoType");
        //没有获取到文件,或者无法读取文件或者文件容量太大等问题
        if (file == null || file.length() > 10000000) {
            return json(JSONObject.fromObject(AjaxList.createFail()));
        }
        //二级栏目类别为空问题
        if (type == null || "".equals(type)) {
            return json(JSONObject.fromObject(AjaxList.createFail()));
        }
        //获得上传的原始文件名称
        String filename = multiPartRequestWrapper.getParameter("name");
        String suffix = ""; //后缀
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = type + "/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(file, path);
        BufferedImage bi = ImageIO.read(file);
        int width = bi.getWidth();
        int height = bi.getHeight();
        String address = path;
        ScenicGallery galleryImage = new ScenicGallery();
//        ScenicInfo scenicInfo = scenicInfoMgrService.info(sid);
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setId(sid);
        galleryImage.setScenicInfo(scenicInfo);
        galleryImage.setImgUrl(address);
        galleryImage.setWidth(width);
        galleryImage.setHeight(height);
        scenicGalleryMgrService.insert(galleryImage);
        return json(JSONObject.fromObject(AjaxList.createSuccess(galleryImage), JsonFilter.getIncludeConfig("scenicInfo")));
    }

    public Result add() {
        final HttpServletRequest request = getRequest();
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setId(null);
        request.setAttribute("scenicInfo", scenicInfo);
        return dispatch("/WEB-INF/jsp/scenic/scenicEdit.jsp");
    }

    public Result addSailboat() {
        final HttpServletRequest request = getRequest();
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setId(null);
        request.setAttribute("scenicInfo", scenicInfo);
        return dispatch("/WEB-INF/jsp/scenic/sailboatEdit.jsp");
    }

    public Result edit() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        ScenicInfo scenicInfo = scenicInfoMgrService.info(id);
        request.setAttribute("scenicInfo", scenicInfo);
        return dispatch("/WEB-INF/jsp/scenic/scenicEdit.jsp");
    }

    public Result editSailboat() {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        ScenicInfo scenicInfo = scenicInfoMgrService.info(id);
        request.setAttribute("scenicInfo", scenicInfo);
        return dispatch("/WEB-INF/jsp/scenic/sailboatEdit.jsp");
    }

    public String audit() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        ScenicInfo scenicInfo = scenicInfoMgrService.info(id);
        request.setAttribute("scenicInfo", scenicInfo);
        return "mgrer/scenic/scenicAudit";
    }

    public ActionResult saveScenic() throws IOException {
        final HttpServletRequest request = getRequest();
        final HttpServletResponse response = getResponse();
        ScenicInfo sourceScenicInfo = null;
        String[] removedImgs = request.getParameterValues("removedImgs");
//        Long fatherId = Long.parseLong(request.getParameter("fatherId") == null || "".equals(request.getParameter("fatherId")) ? "0" : request.getParameter("fatherId"));
//        ////处理父子景点关联
//        if (fatherId != null && fatherId > 0) {
//            //查找父景点信息
//            ScenicInfo fatherScenic = scenicInfoMgrService.selById(fatherId);
//            if (fatherScenic != null) {
//                scenicInfo.setFather(fatherScenic);
//                fatherScenic.setIsThreeLevel(true);
//                scenicInfoMgrService.update(fatherScenic);
//            }
//        } else {
//            //取消关联状态,此时未更新信息,将在获取完整的景点信息后进行统一更新,以免新建景点时候出错
//            scenicInfo.setFather(null);
//        }
        //检查是否有要删除的景点图片,有则删除
        if (removedImgs != null) {
            if (removedImgs.length > 0) {
                for (String s : removedImgs) {
                    scenicGalleryMgrService.del(Long.parseLong(s));
                }
            }
        }
        //当新增景点时候
        if (scenicInfo.getId() == null) { //插入新数据
            scenicInfo.setCreateTime(new Date()); //新建景点时候,设置创建时间
            scenicInfo.setModifyTime(new Date()); //新建景点时候,设置修改时间同创建时间
            Long cityCode = Long.parseLong(scenicInfo.getCity().getCityCode());
            TbArea tbArea = tbAreaService.getArea(cityCode);
            scenicInfo.setCity(tbArea);
            scenicInfo.setCityCode(scenicInfo.getCity().getCityCode());
            ScenicOther scenicOther = scenicInfo.getScenicOther();
            ScenicGeoinfo scenicGeoinfo = scenicInfo.getScenicGeoinfo();

//            scenicInfo.setScenicOther(new ScenicOther());
//            scenicInfo.setScenicGeoinfo(new ScenicGeoinfo());
            if (scenicInfo.getFather().getId() != null && scenicInfo.getFather().getId() > 0) {
                ScenicInfo fatherScenic = scenicInfoMgrService.selById(scenicInfo.getFather().getId());
                scenicInfo.setFather(fatherScenic);
                fatherScenic.setIsThreeLevel(true);
                scenicInfoMgrService.update(fatherScenic);
            } else {
                scenicInfo.setFather(null);
            }

            scenicInfoMgrService.insert(scenicInfo); //新建景点时候,首先保存景点基本信息,以持久化新建景点对象
            scenicOther.setScenicInfo(scenicInfo);
            scenicOther.setId(scenicInfo.getId());
            scenicOtherMgrService.insert(scenicOther);
            scenicGeoinfo.setScenicInfo(scenicInfo);
            scenicGeoinfo.setId(scenicInfo.getId());
            scenicGeoInfoMgrService.insert(scenicGeoinfo);
            sourceScenicInfo = scenicInfoMgrService.load(scenicInfo.getId());
            ScenicStatistics scenicStatistics =  new ScenicStatistics();
            scenicStatistics.setOrderNum(doCreateRandomNum());
            scenicStatistics.setSatisfaction(doCreateSatisfactionNum());
            scenicStatistics.setScenicInfo(scenicInfo);
            scenicStatistics.setId(scenicInfo.getId());
//            scenicStatistics.setScenicInfo(sourceScenicInfo);
            scenicStatisticsMgrService.insert(scenicStatistics);

//            createScenicInfo(request); //保存景点的完整信息
//            scenicInfoMgrService.update(scenicInfo); //更新景点完整信息
        } else {
            //当更改现有景点的信息,直接更新
            sourceScenicInfo = scenicInfoMgrService.load(scenicInfo.getId());
            createScenicInfo(sourceScenicInfo, request);
            sourceScenicInfo.setModifyTime(new Date()); //修改景点时候,设置修改时间
            scenicInfoMgrService.update(sourceScenicInfo); //更新景点完整信息
        }
        //
        scenicInfoMgrService.buildAndIndex(scenicInfo);
        JsonUtil.jsonToResponse(ActionResult.createSuccess(), response);
        return null;
    }


    public Integer doCreateSatisfactionNum() {
        Random r = new Random();
        Integer resultNum = 0;
        while (true) {
            resultNum = r.nextInt(100);
            if (resultNum > 90) {
                break;
            }
        }
        return resultNum;
    }
    public Integer doCreateRandomNum() {
        Random r = new Random();
        Integer resultNum = 0;
        while (true) {
            resultNum = r.nextInt(1000);
            if (resultNum > 900) {
                break;
            }
        }
        return resultNum;
    }

    private ScenicInfo createScenicInfo(ScenicInfo sourceScenicInfo, final HttpServletRequest request) {
        if (scenicInfo.getFather().getId() != null && scenicInfo.getFather().getId() > 0) {
            //查找父景点信息
            ScenicInfo fatherScenic = scenicInfoMgrService.selById(scenicInfo.getFather().getId());
            sourceScenicInfo.setFather(fatherScenic);
            fatherScenic.setIsThreeLevel(true);
            scenicInfoMgrService.update(fatherScenic);
        } else {
            sourceScenicInfo.setFather(null);
        }
        sourceScenicInfo.setName(scenicInfo.getName());
        sourceScenicInfo.setStar(scenicInfo.getStar());
        sourceScenicInfo.setLevel(scenicInfo.getLevel());
        sourceScenicInfo.setScore(scenicInfo.getScore());
        sourceScenicInfo.setRanking(scenicInfo.getRanking());
        sourceScenicInfo.setPrice(scenicInfo.getPrice());
        sourceScenicInfo.setIntro(scenicInfo.getIntro());
        sourceScenicInfo.setCover(scenicInfo.getCover());
        sourceScenicInfo.setIsShow(scenicInfo.getIsShow());
        sourceScenicInfo.setHasTaxi(scenicInfo.getHasTaxi());
        sourceScenicInfo.setHasBus(scenicInfo.getHasBus());
        sourceScenicInfo.setStatus(scenicInfo.getStatus());
        sourceScenicInfo.setIsThreeLevel(scenicInfo.getIsThreeLevel());
        TbArea city = tbAreaService.getArea(Long.parseLong(scenicInfo.getCity().getCityCode()));
        sourceScenicInfo.setCityCode(scenicInfo.getCity().getCityCode());
        sourceScenicInfo.setCity(city);
        getScenicOther(sourceScenicInfo, request);
        getScenicGeoinfo(sourceScenicInfo);
//        sourceScenicInfo.setScenicArea(null); //景点创建/更新时候,不影响区域划分(如需划分到区域,需要在区域划分中重算)
//        sourceScenicInfo.setScenicStatistics(null); //未使用
//        sourceScenicInfo.setScenicGalleryList(null); //新增/更新景点时候,不对景点图册做改变,新建景点时候不能上传图片
//        sourceScenicInfo.setScenicCommentList(null); //暂未使用
//        sourceScenicInfo.setCreatedBy(null); //暂未使用
//        sourceScenicInfo.setModifiedBy(null); //暂未使用
        return sourceScenicInfo;
    }

    private ScenicOther getScenicOther(ScenicInfo sourceScenicInfo, final HttpServletRequest request) {
        ScenicOther scenicOther = sourceScenicInfo.getScenicOther();
        if (scenicOther == null) {
            scenicOther = new ScenicOther();
        }
        scenicOther.setScenicInfo(sourceScenicInfo);
        scenicOther.setAddress(scenicInfo.getScenicOther().getAddress());
        scenicOther.setTelephone(scenicInfo.getScenicOther().getTelephone());
        scenicOther.setHow(scenicInfo.getScenicOther().getHow());
        scenicOther.setOpenTime(scenicInfo.getScenicOther().getOpenTime());
        scenicOther.setAdviceTimeDesc(scenicInfo.getScenicOther().getAdviceTimeDesc());
        scenicOther.setAdviceTime(scenicInfo.getScenicOther().getAdviceTime());
        scenicOther.setDescription(scenicInfo.getScenicOther().getDescription());
//        scenicOther.setTicketDesc(scenicInfo.getScenicOther().getTicketDesc());
        scenicOther.setNotice(scenicInfo.getScenicOther().getNotice());
        scenicOther.setTrafficGuide(scenicInfo.getScenicOther().getTrafficGuide());
        scenicOther.setWebsite(scenicInfo.getScenicOther().getWebsite());
        scenicOther.setCustom(scenicInfo.getScenicOther().getCustom());
        scenicOther.setRecommendReason(scenicInfo.getScenicOther().getRecommendReason());
//        scenicOther.setCtripId(scenicInfo.getScenicOther().getCtripId());
        return scenicOther;
    }

    private ScenicGeoinfo getScenicGeoinfo(ScenicInfo sourceScenicInfo) {
        ScenicGeoinfo scenicGeoinfo = sourceScenicInfo.getScenicGeoinfo();
        if (scenicGeoinfo == null) {
            scenicGeoinfo = new ScenicGeoinfo();
        }
        scenicGeoinfo.setScenicInfo(sourceScenicInfo);
        scenicGeoinfo.setBaiduLng(scenicInfo.getScenicGeoinfo().getBaiduLng());
        scenicGeoinfo.setBaiduLat(scenicInfo.getScenicGeoinfo().getBaiduLat());
        scenicGeoinfo.setGoogleLng(scenicInfo.getScenicGeoinfo().getGoogleLng());
        scenicGeoinfo.setGoogleLat(scenicInfo.getScenicGeoinfo().getGoogleLat());
        return scenicGeoinfo;
    }

    public Result hide() {
        Long id = Long.parseLong(getRequest().getParameter("id"));
        ScenicInfo scenicInfo = scenicInfoMgrService.load(id);
        scenicInfo.setStatus(0);
        scenicInfoMgrService.update(scenicInfo);
        scenicInfoMgrService.buildAndIndex(scenicInfo);
        scenicInfoMgrService.delLabelItems(scenicInfo);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    public Result show() {
        Long id = Long.parseLong(getRequest().getParameter("id"));
        ScenicInfo scenicInfo = scenicInfoMgrService.load(id);
        scenicInfo.setStatus(1);
        scenicInfoMgrService.update(scenicInfo);
        //
        scenicInfoMgrService.buildAndIndex(scenicInfo);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    public Result decline() {
        Long id = Long.parseLong(getRequest().getParameter("id"));
        ScenicInfo scenicInfo = scenicInfoMgrService.load(id);
        scenicInfo.setStatus(0);
        scenicInfoMgrService.update(scenicInfo);
        //
        scenicInfoMgrService.buildAndIndex(scenicInfo);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

//    public Result gallery() throws Exception {
//        final HttpServletRequest request = getRequest();
//        Long scenicId = Long.parseLong(request.getParameter("scnicId"));
//        String category = request.getParameter("category");
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        if (category != null && !"".equals(category)) {
//            paramMap.put("category", category);
//        } else {
//            paramMap.put("category", "scenic");
//        }
//        paramMap.put("scenicId", scenicId);
//        request.setAttribute("scenicId", scenicId);
//        return dispatch("/WEB-INF/jsp/scenic/scenicGallery.jsp");
//
//    }

    public Result bargainPlanScenicInfo() throws Exception {
        final HttpServletRequest request = getRequest();
        String paramId = request.getParameter("paramId");
        Long id = Long.parseLong(paramId);
        ScenicInfo seInfo = scenicInfoMgrService.info(id);
        return json(JSONObject.fromObject(ActionResult.createSuccess(seInfo), JsonFilter.getIncludeConfig("scenicOther", "scenicArea", "scenicGeoinfo", "city")));
    }

    public Result updateOrder() throws IOException {
        final HttpServletRequest request = getRequest();
        String cityCode = request.getParameter("cityCode");
        Long id = Long.parseLong(request.getParameter("id"));
        Integer oldOrder = Integer.parseInt(request.getParameter("oldOrder"));
        Integer newOrder = Integer.parseInt(request.getParameter("newOrder"));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("cityCode", cityCode);
        paramMap.put("oldOrder", oldOrder);
        paramMap.put("newOrder", newOrder);
        if (oldOrder < newOrder) {
            scenicInfoMgrService.orderDown(paramMap);
        } else if (oldOrder > newOrder) {
            scenicInfoMgrService.orderRise(paramMap);
        }
        Map<String, Object> paramMap3 = new HashMap<String, Object>();
        paramMap3.put("id", id);
        paramMap3.put("newOrder", newOrder);
        scenicInfoMgrService.updateOrder(paramMap3);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    public ScenicInfo getScenicInfo() {
        return scenicInfo;
    }

    public void setScenicInfo(ScenicInfo scenicInfo) {
        this.scenicInfo = scenicInfo;
    }
}