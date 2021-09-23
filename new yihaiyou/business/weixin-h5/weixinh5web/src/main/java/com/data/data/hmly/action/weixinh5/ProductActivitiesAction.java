package com.data.data.hmly.action.weixinh5;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.weixinh5.ActivitiesService;
import com.data.data.hmly.service.weixinh5.ProductActivitieService;
import com.data.data.hmly.service.weixinh5.entity.Activities;
import com.data.data.hmly.service.weixinh5.entity.ProductActivity;
import com.data.data.hmly.service.weixinh5.entity.enums.ActivitySceneType;
import com.data.data.hmly.service.weixinh5.entity.enums.ActivityStatus;
import com.data.data.hmly.service.weixinh5.entity.enums.ActivityType;
import com.data.data.hmly.service.weixinh5.entity.enums.PromotWayType;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonConfigUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/2/16.
 */
public class ProductActivitiesAction extends FrameBaseAction implements ModelDriven<Activities> {

    private Activities activities = new Activities();
    private Integer			page				= 1;
    private Integer			rows				= 10;
    @Resource
    private LineService lineService;
    @Resource
    private ActivitiesService activitiesService;
    @Resource
    private ProductActivitieService productActivitieService;


    private Long lastId;

    Map<String, Object> map = new HashMap<String, Object>();
    List<Activities> activitiesList = new ArrayList<Activities>();

    List<ProductActivity> activitiesProductList = new ArrayList<ProductActivity>();


    public Result upOrDownAllActivity() {
        String idsStr = (String) getParameter("activityIds");
        String statusStr = (String) getParameter("statusStr");

        if (StringUtils.isNotBlank(idsStr) && StringUtils.isNotBlank(statusStr)) {

            String[] idStrArr = idsStr.split(",");
            if ("UP".equals(statusStr)) {
                for (String idStr : idStrArr) {
                    activities = activitiesService.load(Long.parseLong(idStr));
                    activities.setStatus(ActivityStatus.UP);
                    activitiesService.update(activities);
                }
            } else if ("DOWN".equals(statusStr)) {
                for (String idStr : idStrArr) {
                    activities = activitiesService.load(Long.parseLong(idStr));
                    activities.setStatus(ActivityStatus.DOWN);
                    activitiesService.update(activities);
                }
            }

            simpleResult(map, true, "");
            return jsonResult(map);
        }
        simpleResult(map, false, "");
        return jsonResult(map);
    }

    /**
     * 上架或下架商品
     * @return
     */
    public Result upOrDownActivity() {
        String activityIdStr = (String) getParameter("activityId");
        String typeStr = (String) getParameter("typeStr");
        if (StringUtils.isNotBlank(activityIdStr) && StringUtils.isNotBlank(typeStr)) {
            Activities activities = activitiesService.load(Long.parseLong(activityIdStr));
            if ("DOWN".equals(typeStr)) {
                activities.setStatus(ActivityStatus.DOWN);
            } else {
                activities.setStatus(ActivityStatus.UP);
            }
            activitiesService.update(activities);
            simpleResult(map, true, "");
            return jsonResult(map);
        }
        simpleResult(map, false, "");
        return jsonResult(map);
    }

    /**
     * 编辑判断活动是否有商品参加
     * @return
     */
    public Result isHasProduct() {

        String activityIdStr = (String) getParameter("activityId");
        List<Line> activityLineList = new ArrayList<Line>();

        if (StringUtils.isNotBlank(activityIdStr)) {
            Activities activities = activitiesService.load(Long.parseLong(activityIdStr));
            activityLineList = productActivitieService.findProductByAid(activities);        //取出已经被添加上的商品线路

            if (activityLineList.isEmpty()) {
                simpleResult(map, false, "");
                return jsonResult(map);
            } else {
                simpleResult(map, true, "");
                return jsonResult(map);
            }

        } else {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

    }

    /**
     * 跳转编辑页面
     * @return
     */
    public Result editActivity() {

        String activityIdStr = (String) getParameter("activityId");

        if (StringUtils.isNotBlank(activityIdStr)) {
            activities = activitiesService.load(Long.parseLong(activityIdStr));
        }
        return dispatch();

    }

    /**
     * 批量删除
     * @return
     */
    public Result delAllActivity() {

        String idsStr = (String) getParameter("activityIds");

        if (StringUtils.isNotBlank(idsStr)) {

            String[] idStrArr = idsStr.split(",");
            for (String idStr : idStrArr) {
                activities = activitiesService.load(Long.parseLong(idStr));
                productActivitieService.deleteByActivity(activities);
                activitiesService.delete(activities);
            }
            simpleResult(map, true, "");
            return jsonResult(map);
        }
        simpleResult(map, false, "");
        return jsonResult(map);
    }

    /**
     * 活动列表数据
     * @return
     */
    public Result activityList() {
        Page pageInfo = new Page(page, rows);

        String typeStr = (String) getParameter("typeStr");
        String statusStr = (String) getParameter("statusStr");
        String nameStr = (String) getParameter("nameStr");

        if (StringUtils.isNotBlank(typeStr)) {
            if ("coupon".equals(typeStr)) {
                activities.setType(ActivityType.coupon);
            } else {
                activities.setType(ActivityType.flashsale);
            }
        }
        if (StringUtils.isNotBlank(nameStr)) {

            activities.setName(nameStr);
        }
        if (StringUtils.isNotBlank(statusStr)) {
            if ("UP".equals(statusStr)) {
                activities.setStatus(ActivityStatus.UP);
            } else {
                activities.setStatus(ActivityStatus.DOWN);
            }
        }


        activitiesList = activitiesService.findActivitiesList(activities, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());

        return datagrid(activitiesList, pageInfo.getTotalCount());
    }

    /**
     * 保存活动
     * @return
     * @throws ParseException
     */
    public Result saveActivity() throws ParseException{

        String idStr = (String) getParameter("id");
        String typeStr = (String) getParameter("type");
        String nameStr = (String) getParameter("nameStr");
        String faceValueStr = (String) getParameter("faceValue");
        String numberStr = (String) getParameter("number");
        String startTimeStr = (String) getParameter("startTimeStr");
        String endTimeStr = (String) getParameter("endTimeStr");
        String lowPriceStr = (String) getParameter("lowPrice");
        String perCountsStr = (String) getParameter("perCounts");
        String productTypeStr = (String) getParameter("productType");
        String instructionsStr = (String) getParameter("instructions");
        String promotwayStr = (String) getParameter("promotway");
        String sceneTypeStr = (String) getParameter("sceneType");
        String statusStr = (String) getParameter("statusStr");


        if (StringUtils.isNotBlank(idStr)) {
            activities = activitiesService.load(Long.parseLong(idStr));
        }

        if (StringUtils.isNotBlank(nameStr)) {
            activities.setName(nameStr);
        }

        if (StringUtils.isNotBlank(typeStr)) {
            if ("coupon".equals(typeStr)) {
                activities.setType(ActivityType.coupon);
            } else {
                activities.setType(ActivityType.flashsale);
            }
        }
        if (StringUtils.isNotBlank(faceValueStr)) {
            activities.setFaceValue(Double.parseDouble(faceValueStr));
        } else {
            activities.setFaceValue(null);
        }
        if (StringUtils.isNotBlank(numberStr)) {
            activities.setNumber(Integer.parseInt(numberStr));
        } else {
            activities.setNumber(null);
        }
        if (StringUtils.isNotBlank(startTimeStr)) {
            activities.setStartTime(DateUtils.parse(startTimeStr, "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotBlank(endTimeStr)) {
            activities.setEndTime(DateUtils.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotBlank(lowPriceStr)) {
            activities.setLowestPrice(Double.parseDouble(lowPriceStr));
        } else {
            activities.setLowestPrice(null);
        }

        if (StringUtils.isNotBlank(perCountsStr)) {
            activities.setPerCounts(Integer.parseInt(perCountsStr));
        } else {
            activities.setPerCounts(null);
        }
        if (StringUtils.isNotBlank(productTypeStr)) {
            if ("line".equals(productTypeStr)) {
                activities.setProductType(ProductType.line);
            }
        }
        if (StringUtils.isNotBlank(instructionsStr)) {
            activities.setInstructions(instructionsStr);
        } else {
            activities.setInstructions(null);
        }

        if (StringUtils.isNotBlank(promotwayStr)) {
            if ("buyerget".equals(promotwayStr)) {
                activities.setPromotway(PromotWayType.buyerget);
            } else {
                activities.setPromotway(PromotWayType.sellersend);
            }
        }

        if (StringUtils.isNotBlank(statusStr)) {
            if ("UP".equals(statusStr)) {
                activities.setStatus(ActivityStatus.UP);
            } else {
                activities.setStatus(ActivityStatus.DOWN);
            }
        }

        if (StringUtils.isNotBlank(sceneTypeStr)) {
            if ("attention_shop".equals(sceneTypeStr)) {
                activities.setSceneType(ActivitySceneType.attention_shop);
            } else if ("buy_product".equals(sceneTypeStr)){
                activities.setSceneType(ActivitySceneType.buy_product);
            } else if ("comment_product".equals(sceneTypeStr)){
                activities.setSceneType(ActivitySceneType.comment_product);
            } else if ("ctrip_vip".equals(sceneTypeStr)){
                activities.setSceneType(ActivitySceneType.ctrip_vip);
            } else if ("customer_no_limit".equals(sceneTypeStr)){
                activities.setSceneType(ActivitySceneType.customer_no_limit);
            } else if ("index_self_get".equals(sceneTypeStr)){
                activities.setSceneType(ActivitySceneType.index_self_get);
            } else if ("old_buyer".equals(sceneTypeStr)){
                activities.setSceneType(ActivitySceneType.old_buyer);
            } else if ("user_register".equals(sceneTypeStr)){
                activities.setSceneType(ActivitySceneType.user_register);
            }
        }
        activitiesService.update(activities);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 点击新增时，保存新增临时记录
     * @return
     */
    public Result saveTempActivity() {

        activities.setUser(getLoginUser());
        activities.setSysUnit(getCompanyUnit());
        activities.setStatus(ActivityStatus.TEMP);

        activitiesService.insertTempNew(activities);

        map.put("aId", activities.getId());

        simpleResult(map, true, "");
        return jsonResult(map);

    }

    /**
     * 新增活动页面，并暂存临时记录
     * @return
     */
    public Result addActivity() {

        String activityIdStr = (String) getParameter("activityId");

        if (StringUtils.isNotBlank(activityIdStr)) {
            lastId = Long.parseLong(activityIdStr);
        }

        return dispatch();
    }

    /**
     * 全选保存活动商品关联信息
     * @return
     */
    public Result selectAllProduct() {
        String lineStr = (String) getParameter("lineIds");
        String activityIdStr = (String) getParameter("activityId");

        List<Line> lineList = new ArrayList<Line>();
        if (StringUtils.isNotBlank(lineStr) && StringUtils.isNotBlank(activityIdStr)) {

            String[] lineStrArr = lineStr.split(",");
            lineList = lineService.loadLineList(lineStrArr);
            activities = activitiesService.load(Long.parseLong(activityIdStr));
            productActivitieService.saveAll(activities, lineList);

            simpleResult(map, true, "");

            return jsonResult(map);
        }
        simpleResult(map, false, "");

        return jsonResult(map);

    }

    /**
     * 取消全选保存活动商品关联信息
     * @return
     */
    public Result cancelAllProduct() {
        String lineStr = (String) getParameter("lineIds");
        String activityIdStr = (String) getParameter("activityId");

        List<Line> lineList = new ArrayList<Line>();
        if (StringUtils.isNotBlank(lineStr) && StringUtils.isNotBlank(activityIdStr)) {

            String[] lineStrArr = lineStr.split(",");
            lineList = lineService.loadLineList(lineStrArr);
            activities = activitiesService.load(Long.parseLong(activityIdStr));
            productActivitieService.cancelAll(activities, lineList);

            simpleResult(map, true, "");

            return jsonResult(map);
        }
        simpleResult(map, false, "");

        return jsonResult(map);

    }


    /**
     * 添加商品活动关联信息
     * @return
     */
    public Result selectProduct() {
        String productIdStr = (String) getParameter("productId");
        String activityIdStr = (String) getParameter("activityId");

        Line line = new Line();
        ProductActivity productActivity = new ProductActivity();

        if (StringUtils.isNotBlank(productIdStr) && StringUtils.isNotBlank(activityIdStr)) {
            line = lineService.loadLine(Long.parseLong(productIdStr));
            activities = activitiesService.load(Long.parseLong(activityIdStr));
            productActivity.setActivitie(activities);
            productActivity.setLine(line);
            productActivitieService.save(productActivity);
            simpleResult(map, true, "");
            return jsonResult(map);
        }
        simpleResult(map, false, "");
        return jsonResult(map);


    }

    /**
     * 新增取消删除所有活动和商品
     * @return
     */
    public Result cancelSave() {
        String activityIdStr = (String) getParameter("activityId");
        if (StringUtils.isNotBlank(activityIdStr)) {
            activities = activitiesService.load(Long.parseLong(activityIdStr));
            productActivitieService.deleteByActivity(activities);
            activitiesService.delete(activities);
            simpleResult(map, true, "");
            return jsonResult(map);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 删除商品活动关联信息
     * @return
     */
    public Result cancelProduct() {

        String productIdStr = (String) getParameter("productId");
        String activityIdStr = (String) getParameter("activityId");
        String cancelAllStr = (String) getParameter("cancelAll");

        Line line = new Line();

        if (StringUtils.isNotBlank(cancelAllStr) && StringUtils.isNotBlank(activityIdStr)) {
            activities = activitiesService.load(Long.parseLong(activityIdStr));
            productActivitieService.deleteByActivity(activities);
            simpleResult(map, true, "");
            return jsonResult(map);
        } else {
            if (StringUtils.isNotBlank(productIdStr) && StringUtils.isNotBlank(activityIdStr)) {
                line = lineService.loadLine(Long.parseLong(productIdStr));
                activities = activitiesService.load(Long.parseLong(activityIdStr));
                productActivitieService.deleteByProduct(line, activities);
                simpleResult(map, true, "");
                return jsonResult(map);
            }
        }

        simpleResult(map, false, "");
        return jsonResult(map);
    }

    /**
     * 获取商品列表（包括已经加入优惠或限时抢购活动的商品）
     * @return
     */
    public Result productGrid() {


        Line line = new Line();

        String activityIdStr = (String) getParameter("lastId");
//        proType: $("#ipt_proType").combobox('getValue'),
//        productName: $("#ipt_productName").combobox('getValue')
        String proTypeStr = (String) getParameter("proType");
        String productNameStr = (String) getParameter("productName");



        if (StringUtils.isNotBlank(proTypeStr)) {
            if ("line".equals(proTypeStr)) {
                line.setProType(ProductType.line);
            }
        }
        if (StringUtils.isNotBlank(productNameStr)) {
            line.setName(productNameStr);
        }
        List<Line> activityLineList = new ArrayList<Line>();

        if (StringUtils.isNotBlank(activityIdStr)) {
            Activities activities = activitiesService.load(Long.parseLong(activityIdStr));
            activityLineList = productActivitieService.findProductByAid(activities);        //取出已经被添加上的商品线路
        }

        List<Line> lineList = new ArrayList<Line>();
        List<LineDataEntity> lineEntityList = new ArrayList<LineDataEntity>();


        Page pageInfo = new Page(page, rows);

        lineList = lineService.findLineList(line, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());

        lineEntityList = formartLineList(lineList, activityLineList);

        return datagrid(lineEntityList, pageInfo.getTotalCount());
    }



    /**
     * 拼装LineDataEntity实体，并标记已经参加该活动的商品
     * @param lineList
     * @param activityLineList
     * @return
     */
    public List<LineDataEntity> formartLineList(List<Line> lineList, List<Line> activityLineList) {
        List<LineDataEntity> lineEntityList = new ArrayList<LineDataEntity>();
        for (Line line : lineList) {
            LineDataEntity lineDataEntity = new LineDataEntity();
            for (Line aLine : activityLineList) {

                if (aLine.getId() == line.getId()) {
                    lineDataEntity.setIsChecked(1);
                }

            }
            lineDataEntity.setId(line.getId());
            lineDataEntity.setName(line.getName());
            lineEntityList.add(lineDataEntity);

        }

        return lineEntityList;
    }


    public Result manage() {
        return dispatch();
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
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

    public Activities getActivities() {
        return activities;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
    }

    @Override
    public Activities getModel() {
        return activities;
    }
}
