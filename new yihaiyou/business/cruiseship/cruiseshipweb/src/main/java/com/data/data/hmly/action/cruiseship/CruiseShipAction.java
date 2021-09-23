package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.cruiseship.vo.CruiseshipLabel;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipPlanService;
import com.data.data.hmly.service.cruiseship.CruiseShipProjectService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipExtend;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProject;
import com.data.data.hmly.service.entity.*;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮轮
 * Created by caiys on 2016/9/13.
 */
public class CruiseShipAction extends FrameBaseAction {
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private CruiseShipPlanService cruiseShipPlanService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private LabelItemService labelItemService;
    @Resource
    private LabelService labelService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private CruiseShipProjectService cruiseShipProjectService;

    /**
     * 页面字段
     */
    private CruiseShip cruiseShip = new CruiseShip();
    private CruiseShipExtend cruiseShipExtend = new CruiseShipExtend();
    private Integer page = 1;
    private Integer rows = 20;
    private Long productId;
    private Long classifyId;
    private Long shipId;
    private Productimage productimage = new Productimage();
    private String childFolder;
    private List<CruiseShipPlan> cruiseShipPlans;
    private String today;
    private Long coverImgId;
    private Map<String, Object> map = new HashMap<String, Object>();
    private String fgDomain;

    /**
     * 功能首页
     */
    public Result manage() {
        return dispatch();
    }


    public Result yhyCruiseshipLabelList() {

        Page pageInfo = new Page(page, rows);
        String name = (String) getParameter("name");
        String labelId = (String) getParameter("labelId");
        String type = (String) getParameter("type");
        String tagIds = (String) getParameter("tagIds");
        TbArea area = tbAreaService.getArea(350200L);
        CruiseShip info = cruiseShip;

        if (StringUtils.isNotBlank(name)) {
            info.setName(name);
        }
        List<CruiseShip> scenics = new ArrayList<CruiseShip>();
        List<CruiseshipLabel> scenicLabels = new ArrayList<CruiseshipLabel>();
        if ("CRUISESHIP".equals(type)) {
            scenics = cruiseShipService.getCruiseShipLabels(info, area, tagIds, pageInfo);
            for (CruiseShip sInfo : scenics) {
                CruiseshipLabel slabel = new CruiseshipLabel();
                slabel.setId(sInfo.getId());
                slabel.setName(sInfo.getName());
                if (sInfo.getCityId() != null) {
                    slabel.setCityId(sInfo.getCityId());
                    slabel.setCityName(tbAreaService.getArea(sInfo.getCityId()).getFullPath());
                }
                slabel.setUpdateTime(DateUtils.format(sInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));

                List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(), TargetType.CRUISESHIP);

                List<String> labelNames = new ArrayList<String>();
                List<Integer> itemSorts = new ArrayList<Integer>();
                List<Long> itemIds = new ArrayList<Long>();
                List<Long> labIds = new ArrayList<Long>();
                for (LabelItem it : items) {
                    if ((it.getTargetId()).equals(sInfo.getId())) {
                        if (StringUtils.isNotBlank(labelId)) {
                            Long lId = Long.parseLong(labelId);
                            List<Label> labels = labelService.findLabelsByParent(lId);
                            if (labels.size() > 0) {
                                for (Label la : labels) {
                                    if ((la.getId()).equals(it.getLabel().getId())) {
                                        slabel.setSort(it.getOrder());
                                        itemSorts.add(it.getOrder());
                                        labelNames.add(it.getLabel().getName());
                                        itemIds.add(it.getId());
                                        labIds.add(it.getLabel().getId());
                                    }
                                }

                            } else {
                                if (lId.equals(it.getLabel().getId())) {
                                    slabel.setSort(it.getOrder());
                                    itemSorts.add(it.getOrder());
                                    labelNames.add(it.getLabel().getName());
                                    itemIds.add(it.getId());
                                    labIds.add(it.getLabel().getId());
                                }
                            }

                        } else {
                            slabel.setSort(it.getOrder());
                            itemSorts.add(it.getOrder());
                            labelNames.add(it.getLabel().getName());
                            itemIds.add(it.getId());
                            labIds.add(it.getLabel().getId());
                        }
                    }

                }

                slabel.setLabelNames(labelNames);
                slabel.setItemSort(itemSorts);
                slabel.setLabelItems(itemIds);
                slabel.setLabelIds(labIds);
                scenicLabels.add(slabel);
            }
        }

        return datagrid(scenicLabels, pageInfo.getTotalCount());

    }

    /**
     * 审核产品
     * @return
     */
    public Result doChecked() {
        if (productId != null) {
            CruiseShip cruiseShipTemp = cruiseShipService.findById(productId);
            if (cruiseShip.getStatus() == ProductStatus.UP) {
                cruiseShipTemp.setStatus(ProductStatus.UP);
                cruiseShipTemp.setAuditReason("审核成功！");
            } else {
                cruiseShipTemp.setStatus(ProductStatus.REFUSE);
                cruiseShipTemp.setAuditReason("审核不通过！");
            }
            cruiseShipTemp.setAuditBy(getLoginUser().getId());
            cruiseShipTemp.setAuditTime(new Date());
            cruiseShipService.updateCruiseShipInfo(cruiseShipTemp, cruiseShipTemp.getCruiseShipExtend());
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return jsonResult(map);
    }

    /**
     * 游轮审核列表
     * @return
     */
    public Result checkManage() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        return dispatch();
    }

    @AjaxCheck
    public Result checkingSearch() {
        Page pageInfo = new Page(page, rows);
        List<CruiseShip> cruiseShips = cruiseShipService.getCheckingList(cruiseShip, getLoginUser(), pageInfo, isSiteAdmin());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(cruiseShips, pageInfo.getTotalCount(), jsonConfig);
    }


    /**
     * 分页查询
     */
    @AjaxCheck
    public Result search() {
        // 参数
        String keyword = (String) getParameter("keyword");
        String statusStr = (String) getParameter("status");
        if (StringUtils.isNotBlank(keyword)) {
            cruiseShip.setKeyword(keyword);
        }
        if (StringUtils.isNotBlank(statusStr)) {
            cruiseShip.setStatus(ProductStatus.valueOf(statusStr));
        }

        Page pageInfo = new Page(page, rows);
        List<CruiseShip> cruiseShips = cruiseShipService.pageCruiseShips(cruiseShip, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(cruiseShips, pageInfo.getTotalCount(), jsonConfig);
    }

    public Result index() {
        cruiseShipService.indexAll();
        return text("索引结束");
    }

    /**
     * 修改线路向导页面
     */
    public Result editWizard() {
//        productId = (String) getParameter("productId");
        return dispatch();
    }

    /**
     * 第一步：邮轮描述
     */
    public Result editStep1() {
        if (productId != null) {
            cruiseShip = cruiseShipService.findById(productId);
            cruiseShipExtend = cruiseShip.getCruiseShipExtend();
        }
        return dispatch();
    }

    /**
     * 第二步：房型签证
     */
    public Result editStep2() {

        return dispatch();
    }

    /**
     * 第三步：行程内容
     */
    public Result editStep3() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        if (productId != null) {
            CruiseShipPlan cruiseShipPlan = new CruiseShipPlan();
            cruiseShipPlan.setCruiseShipId(productId);
            cruiseShipPlans = cruiseShipPlanService.listCruiseShipPlans(cruiseShipPlan, getLoginUser(), isSiteAdmin(), isSupperAdmin());
        }
        return dispatch();
    }

    /**
     * 第四步：出游计划
     */
    public Result editStep4() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        today = DateUtils.format(new Date(), "yyyy-MM-dd");
        return dispatch();
    }

    /**
     * 第五步：项目管理
     */
    public Result editStep5() {
       /* List<CruiseShipProject> cruiseShipProjects = new ArrayList<CruiseShipProject>();
        if (classifyId != null && shipId!= null) {
            CruiseShipProject cruiseShipProject = new CruiseShipProject();
            cruiseShipProject.setClassifyId(classifyId);
            cruiseShipProject.setShipId(shipId);
            cruiseShipProjects = cruiseShipProjectService.listCruiseShipProjects(cruiseShipProject);
        }*/
        return dispatch();
    }

    /**
     * 第六步：编辑完成
     */
    public Result editStep6() {
        if (productId != null) {
            cruiseShip = cruiseShipService.findById(productId);
        }
        return dispatch();
    }

    /**
     * 功能描述：保存线路基础信息
     *
     * @return
     * @author caiys
     * @date 2015年10月16日 下午4:51:43
     */
    @AjaxCheck
    public Result saveInfo() {
        // 基本信息
        if (cruiseShip.getId() != null) {   // 更新
            CruiseShip cruiseShipOld = cruiseShipService.findById(cruiseShip.getId());
            cruiseShipOld.setName(cruiseShip.getName());
            cruiseShipOld.setNeedConfirm(cruiseShip.getNeedConfirm());
            cruiseShipOld.setStartCityId(cruiseShip.getStartCityId());
            cruiseShipOld.setStartCity(cruiseShip.getStartCity());
            cruiseShipOld.setArriveCityId(cruiseShip.getArriveCityId());
            cruiseShipOld.setArriveCity(cruiseShip.getArriveCity());
            cruiseShipOld.setStatus(ProductStatus.DOWN);
            cruiseShipOld.setAttend(cruiseShip.getAttend());
            cruiseShipOld.setAttendNoPassport(cruiseShip.getAttendNoPassport());
            cruiseShipOld.setAttendNoVisa(cruiseShip.getAttendNoVisa());
            cruiseShipOld.setBrand(cruiseShip.getBrand());
            cruiseShipOld.setRoute(cruiseShip.getRoute());
            cruiseShipOld.setServices(cruiseShip.getServices());
            cruiseShipOld.setSatisfaction(cruiseShip.getSatisfaction());
            cruiseShipOld.setCommentNum(cruiseShip.getCommentNum());
            cruiseShipOld.setCollectionNum(cruiseShip.getCollectionNum());
            cruiseShipOld.setRemark(cruiseShip.getRemark());
            cruiseShipOld.setRecommend(cruiseShip.getRecommend());
            cruiseShipOld.setCityId(cruiseShip.getStartCityId());
            if (StringUtils.isNotBlank(cruiseShip.getCoverImage())) {
                cruiseShipOld.setCoverImage(cruiseShip.getCoverImage());
            }
            cruiseShipOld.setUpdateTime(new Date());
            CruiseShipExtend cruiseShipExtendOld = cruiseShipOld.getCruiseShipExtend();
            cruiseShipExtendOld.setVisaInfo(cruiseShipExtend.getVisaInfo());
            cruiseShipExtendOld.setLightPoint(cruiseShipExtend.getLightPoint());
            cruiseShipExtendOld.setQuoteContainDesc(cruiseShipExtend.getQuoteContainDesc());
            cruiseShipExtendOld.setQuoteNoContainDesc(cruiseShipExtend.getQuoteNoContainDesc());
            cruiseShipExtendOld.setOrderKnow(cruiseShipExtend.getOrderKnow());
            cruiseShipExtendOld.setHowToOrder(cruiseShipExtend.getHowToOrder());
            cruiseShipExtendOld.setSignWay(cruiseShipExtend.getSignWay());
            cruiseShipExtendOld.setPayWay(cruiseShipExtend.getPayWay());
            cruiseShipExtendOld.setUpdateTime(new Date());
            cruiseShipService.updateCruiseShipInfo(cruiseShipOld, cruiseShipExtendOld);
        } else {    // 新增
            cruiseShip.setTopProduct(cruiseShip); // 默认原线路是自己
            cruiseShip.setStatus(ProductStatus.DOWN);
            cruiseShip.setProType(ProductType.cruiseship);
            cruiseShip.setUser(getLoginUser());
            cruiseShip.setCompanyUnit(getCompanyUnit());
            cruiseShip.setCreateTime(new Date());
            cruiseShip.setUpdateTime(new Date());
            cruiseShip.setCityId(cruiseShip.getStartCityId());
            cruiseShipExtend.setCreateTime(new Date());
            cruiseShipExtend.setUpdateTime(new Date());
            cruiseShipService.saveCruiseShipInfo(cruiseShip, cruiseShipExtend);
        }
        // 图片处理
        final HttpServletRequest request = getRequest();
        String[] imgPaths = request.getParameterValues("imgPaths");
        String[] imgDeleteIds = request.getParameterValues("imgDeleteIds");
        SysUser loginUser = getLoginUser();
        SysUnit sysUnit = getCompanyUnit();

        // 处理增加图片
        if (imgPaths != null && imgPaths.length > 0) {
            List<Productimage> productimageList = new ArrayList<Productimage>();
            for (String path : imgPaths) {
                Productimage productimage = new Productimage();
                productimage.setProduct(cruiseShip);
                if (StringUtils.isNotBlank(childFolder)) {
                    productimage.setChildFolder(childFolder);
                }
                if (cruiseShip.getCoverImage().equals(path)) {
                    // 先删除之前的封面
                    productimageService.doDelCoverByProduct(cruiseShip.getId());
                    productimage.setCoverFlag(true);
                } else {
                    productimage.setCoverFlag(false);
                }
                productimage.setPath(path);
                productimage.setCreateTime(new Date());
                productimage.setProType(ProductType.cruiseship);
                productimage.setUserId(loginUser.getId());
                productimage.setCompanyUnitId(sysUnit.getId());
                productimageList.add(productimage);
            }
            productimageService.saveAll(productimageList);
        }
        // 处理删除图片
        if (imgDeleteIds != null && imgDeleteIds.length > 0) {
            for (String idStr : imgDeleteIds) {
                productimageService.delById(Long.parseLong(idStr));
            }
        }
        // 处理封面
        if (coverImgId != null) {
            productimageService.doDelCoverByProduct(cruiseShip.getId());
            productimageService.doSetCoverById(coverImgId);
        }
        result.put("id", cruiseShip.getId());
        simpleResult(result, true, "");
        return jsonResult(result);
    }

    public Result getImageList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (productimage.getProduct().getId() != null && StringUtils.isNotBlank(productimage.getChildFolder())) {
            List<Productimage> productimageList = productimageService.findProductimage(productimage, null);
            result.put("success", true);
            result.put("data", productimageList);
        } else {
            result.put("success", false);
            result.put("msg", "获取邮轮图片失败! 缺少产品id或子目录名称!");
            result.put("data", null);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result doUpdateStatus() {
        Map<String, Object> result = new HashMap<String, Object>();
        String ids = (String) getParameter("ids");
        String statusStr = (String) getParameter("status");
        if (StringUtils.isBlank(ids)) {
            result.put("success", false);
            result.put("msg", "邮轮id不可为空");
        } else if (StringUtils.isBlank(statusStr)) {
            result.put("success", false);
            result.put("msg", "需要更新的状态不可为空!");
        } else {
            result = cruiseShipService.batchUpdateStatus(result, ids, ProductStatus.valueOf(statusStr));
        }
        return json(JSONObject.fromObject(result));
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Productimage getProductimage() {
        return productimage;
    }

    public void setProductimage(Productimage productimage) {
        this.productimage = productimage;
    }

    public String getChildFolder() {
        return childFolder;
    }

    public void setChildFolder(String childFolder) {
        this.childFolder = childFolder;
    }

    public CruiseShipExtend getCruiseShipExtend() {
        return cruiseShipExtend;
    }

    public void setCruiseShipExtend(CruiseShipExtend cruiseShipExtend) {
        this.cruiseShipExtend = cruiseShipExtend;
    }

    public List<CruiseShipPlan> getCruiseShipPlans() {
        return cruiseShipPlans;
    }

    public void setCruiseShipPlans(List<CruiseShipPlan> cruiseShipPlans) {
        this.cruiseShipPlans = cruiseShipPlans;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public Long getCoverImgId() {
        return coverImgId;
    }

    public void setCoverImgId(Long coverImgId) {
        this.coverImgId = coverImgId;
    }

    public String getFgDomain() {
        return fgDomain;
    }

    public void setFgDomain(String fgDomain) {
        this.fgDomain = fgDomain;
    }

    public CruiseShipService getCruiseShipService() {
        return cruiseShipService;
    }

    public void setCruiseShipService(CruiseShipService cruiseShipService) {
        this.cruiseShipService = cruiseShipService;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }
}
