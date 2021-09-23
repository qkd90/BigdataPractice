package com.data.data.hmly.service.recplan;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.constants.BizConstants;
import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.base.util.ListUtil;
import com.data.data.hmly.service.base.util.StringUtil;
import com.data.data.hmly.service.build.LvXBangBuildService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.dao.RecommendPlanDao;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.vo.RecommendPlanCountInfo;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RecplanMgrService extends BaseService<RecommendPlan> {

    @Resource
    private RecommendPlanDao recommendPlanDao;
    @Resource
    private AreaService areaService;
    @Resource
    private LvXBangBuildService lvXBangBuildService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private LabelItemService labelItemService;
    /**
     * 自动构建静态页面和自动索引
     * @param recommendPlan
     */
    public void buildAndIndex(RecommendPlan recommendPlan) {
        // build 和 index
        lvXBangBuildService.buildOneRecplan(recommendPlan.getId());
        lvXBangBuildService.buildRecplanIndex();
        recommendPlanService.indexRecommendPlan(recommendPlan);
    }

    /**
     * 分页显示
     *
     * @param paramMap
     * @return
     */
    public ResultModel<Map<String, Object>> pageRecplan(Map<String, Object> paramMap) {
        ResultModel<Map<String, Object>> result = new ResultModel<Map<String, Object>>();
        paramMap.put("deleteFlag", BizConstants.COMMON_FALSE);
        // 中间过渡数据
        ResultModel<RecommendPlan> recommendPlans = super.page(RecommendPlan.class, paramMap);
        int count = recommendPlans.getTotal();
        result.setTotal(count);
        if (count > 0) {
            List<Map<String, Object>> recplanLists = new ArrayList<Map<String, Object>>();
            for (RecommendPlan recommendPlan : recommendPlans.getRows()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", recommendPlan.getId());
                map.put("planName", recommendPlan.getPlanName());
                map.put("days", recommendPlan.getDays());
                map.put("cityIds", recommendPlan.getCityIds());
                map.put("userName", recommendPlan.getUser().getUserName());
                map.put("createTime", recommendPlan.getCreateTime());
                map.put("modifyTime", recommendPlan.getModifyTime());
                map.put("startTime", recommendPlan.getStartTime());
                map.put("status", recommendPlan.getStatus());
                recplanLists.add(map);
            }
            // 途径城市代码
            Set<Long> cityCodeSet = new HashSet<Long>();
            for (Map<String, Object> map : recplanLists) {
                String cityIds = (String) map.get("cityIds");
                if (StringUtil.isNotBlank(cityIds)) {
                    cityCodeSet.addAll(ListUtil.stringArrayTOLongArray(cityIds.split(",")));
                }
            }
            // 查询相应城市
            if (cityCodeSet.size() > 0) {
                paramMap.clear();
                paramMap.put("cityCodes", cityCodeSet);
                List<TbArea> areas = areaService.getByIds(cityCodeSet);
                // 代码转换名称
                if (areas.size() > 0) {
                    Map<String, String> areaMap = new HashMap<String, String>();
                    for (TbArea area : areas) {
                        areaMap.put(area.getCityCode(), area.getFullPath());
                    }
                    for (Map<String, Object> map : recplanLists) {
                        String cityIds = (String) map.get("cityIds");
                        String cityNames = getCityNames(cityIds, areaMap);
                        map.put("cityNames", cityNames);
                    }
                }
            }
            result.setRows(recplanLists);
        }
        return result;
    }

    /**
     * 统计行程信息
     *
     * @return
     */
    public ResultModel<RecommendPlanCountInfo> countRecplan() {
        ResultModel<RecommendPlanCountInfo> result = new ResultModel<RecommendPlanCountInfo>();
        String analyseSql = "select p.city_id as cityId,a.full_path as fullPath,count(p.id) as totalCount from recommend_plan p, tb_area a "
                + "where p.delete_flag = 2 and p.city_id = a.city_code group by p.city_id";
        List<RecommendPlanCountInfo> countRecplanInfos = recommendPlanDao.findEntitiesBySQL(analyseSql, RecommendPlanCountInfo.class);
        for (RecommendPlanCountInfo countRecplanInfo : countRecplanInfos) {
            Long cityId = countRecplanInfo.getCityId().longValue();
            String countOnSaleSql = "select count(p.id) as result from recommend_plan p,tb_area a "
                    + "where p.city_id = a.city_code and p.status = 2 and p.delete_flag = 2  and p.city_id =? group by p.city_id";
            String countOffSaleSql = "select count(p.id) as result from recommend_plan p,tb_area a "
                    + "where p.city_id = a.city_code and p.status = 3 and p.delete_flag = 2  and p.city_id =? group by p.city_id";
            String countDraftSql = "select count(p.id) as result from recommend_plan p,tb_area a "
                    + "where p.city_id = a.city_code and p.status = 1 and p.delete_flag = 2  and p.city_id =? group by p.city_id";
            BigDecimal onSaleNum = recommendPlanDao.findIntegerBySQL(countOnSaleSql, cityId);
            BigDecimal offSaleNum = recommendPlanDao.findIntegerBySQL(countOffSaleSql, cityId);
            BigDecimal draftNum = recommendPlanDao.findIntegerBySQL(countDraftSql, cityId);
            countRecplanInfo.setOnSaleCount(onSaleNum == null ? BigInteger.ZERO : onSaleNum.toBigInteger());
            countRecplanInfo.setOffSaleCount(offSaleNum == null ? BigInteger.ZERO : offSaleNum.toBigInteger());
            countRecplanInfo.setDraftCount(draftNum == null ? BigInteger.ZERO : draftNum.toBigInteger());
        }
        result.setRows(countRecplanInfos);
        return result;
    }

    /**
     * 获取城市名称
     *
     * @param cityCodes
     * @param areaMap
     * @return
     */
    public String getCityNames(String cityCodes, Map<String, String> areaMap) {
        if (StringUtil.isNotBlank(cityCodes)) {
            StringBuilder sb = new StringBuilder();
            String[] cityCodeArray = cityCodes.split(",");
            for (String cityCode : cityCodeArray) {
                String cityName = areaMap.get(cityCode);
                if (StringUtil.isBlank(cityName)) {
                    cityName = cityCode;
                }
                sb.append(cityName).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }

    /**
     * 批量删除
     *
     * @param idStrs 多个id用“,”分隔
     */
    public void batchDel(String idStrs) {
        if (StringUtil.isNotBlank(idStrs)) {
            String[] idsArray = idStrs.split(",");
            List<Long> ids = ListUtil.stringArrayTOLongArray(idsArray);
            Criteria<RecommendPlan> c = new Criteria<RecommendPlan>(RecommendPlan.class);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ids", ids);
            c = makeCriteria(paramMap, c);
            List<RecommendPlan> recommendPlans = recommendPlanDao.findByCriteria(c);
            for (RecommendPlan recommendPlan : recommendPlans) {
                recommendPlan.setDeleteFlag(1);
                recommendPlan.setModifyTime(new Date());
                recommendPlanDao.update(recommendPlan);
                //
                buildAndIndex(recommendPlan);
                delLabelItems(recommendPlan);
            }
//            recommendPlanDao.updateAll(recommendPlans);
        }
    }

    /**
     * 批量操作
     *
     * @param idStrs 多个id用“,”分隔
     */
    public void batchOpt(String idStrs, int status) {
        if (StringUtil.isNotBlank(idStrs)) {
            String[] idsArray = idStrs.split(",");
            List<Long> ids = ListUtil.stringArrayTOLongArray(idsArray);
            Criteria<RecommendPlan> c = new Criteria<RecommendPlan>(RecommendPlan.class);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ids", ids);
            c = makeCriteria(paramMap, c);
            List<RecommendPlan> recommendPlans = recommendPlanDao.findByCriteria(c);
            for (RecommendPlan recommendPlan : recommendPlans) {
                recommendPlan.setStatus(status);
                recommendPlan.setModifyTime(new Date());
                recommendPlanDao.update(recommendPlan);
                //
                buildAndIndex(recommendPlan);
                delLabelItems(recommendPlan);
            }
//            recommendPlanDao.updateAll(recommendPlans);
        }
    }

    public void delLabelItems(RecommendPlan recommendPlan) {
        labelItemService.delItemListByTargetIdId(recommendPlan.getId(), TargetType.RECOMMEND_PLAN);
    }

    @Override
    public DataAccess<RecommendPlan> getDao() {
        return recommendPlanDao;
    }

    @Override
    public Criteria<RecommendPlan> makeCriteria(Map<String, Object> paramMap, Criteria<RecommendPlan> c) {
        DetachedCriteria dc_area = c.createCriteria("city", "a");
        if (paramMap.containsKey("planName") && StringUtil.isNotBlank(paramMap.get("planName").toString())) {
            String planName = paramMap.get("planName").toString();
            if (planName.matches("^[1-9]\\d*$")) {
                Long recplanId = Long.parseLong(planName);
                c.or(Restrictions.eq("id", recplanId), Restrictions.like("planName", planName, MatchMode.ANYWHERE));
            } else {
                c.like("planName", paramMap.get("planName").toString(), MatchMode.ANYWHERE);
            }
        }
        if (paramMap.containsKey("deleteFlag") && StringUtil.isNotBlank(paramMap.get("deleteFlag").toString())) {
            c.eq("deleteFlag", Integer.parseInt(paramMap.get("deleteFlag").toString()));
        }
        if (paramMap.containsKey("status") && StringUtil.isNotBlank(paramMap.get("status").toString())) {
            c.eq("status", Integer.parseInt(paramMap.get("status").toString()));
        }
        if (paramMap.containsKey("cityIds") && StringUtil.isNotBlank(paramMap.get("cityIds").toString())) {
            c.like("cityIds", paramMap.get("cityIds").toString(), MatchMode.START);
        }
        if (paramMap.containsKey("cityCode") && !"".equals(paramMap.get("cityCode"))) {
            dc_area.add(Restrictions.like("cityCode", paramMap.get("cityCode") + "%"));
            if (paramMap.get("isChina") != null && Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 6"));
            } else if (paramMap.get("isChina") != null && !Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 7"));
            }
        }
        if (paramMap.containsKey("daysStart") && StringUtil.isNotBlank(paramMap.get("daysStart").toString())) {
            c.ge("days", Integer.parseInt(paramMap.get("daysStart").toString()));
        }
        if (paramMap.containsKey("daysEnd") && StringUtil.isNotBlank(paramMap.get("daysEnd").toString())) {
            c.le("days", Integer.parseInt(paramMap.get("daysEnd").toString()));
        }
        if (paramMap.containsKey("hasStartTime") && StringUtil.isNotBlank(paramMap.get("hasStartTime").toString())) {
            if (Integer.parseInt(paramMap.get("hasStartTime").toString()) == 0) {
                c.isNull("startTime");
            }
        }
        if (paramMap.containsKey("ids")) {
            ArrayList<?> ids = (ArrayList) paramMap.get("ids");
            if (ids.size() > 0) {
                c.in("id", ids);
            }
        }
        return c;
    }
}
