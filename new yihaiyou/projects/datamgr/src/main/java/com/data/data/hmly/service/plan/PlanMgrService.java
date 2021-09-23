package com.data.data.hmly.service.plan;

import com.data.data.hmly.action.plan.vo.PlanMgrVo;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.base.util.ListUtil;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.dao.PlanDao;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zzl on 2016/4/28.
 */
@Service
public class PlanMgrService extends BaseService<Plan> {

    @Resource
    private PlanDao planDao;
    @Resource
    private AreaService areaService;
    @Resource
    private LabelItemService labelItemService;

    public void setCostAndCities(Plan plan, PlanMgrVo planMgrVo) {
        Set<String> cityNameSet = new HashSet<String>();
        StringBuilder sb = new StringBuilder();
        Float totalCost = 0F;
        for (PlanDay planDay : plan.getPlanDayList()) {
            cityNameSet.add(plan.getCity().getFullPath());
            Float ticketCost = planDay.getTicketCost() == null ? 0F : planDay.getTicketCost();
            Float trafficCost = planDay.getTrafficCost() == null ? 0F : planDay.getTrafficCost();
            Float hotelCost = planDay.getHotelCost() == null ? 0F : planDay.getHotelCost();
            Float foodCost = planDay.getFoodCost() == null ? 0F : planDay.getFoodCost();
            Float dayCost = ticketCost + trafficCost + hotelCost + foodCost;
            totalCost += dayCost;
        }
        for (String cityStr : cityNameSet) {
            sb.append(cityStr + "-");
        }
        sb.deleteCharAt(sb.length() - 1);
        planMgrVo.setCost(totalCost);
        planMgrVo.setCitys(sb.toString());
    }

    @Deprecated
    public String getCities(String cityIds) {
        if (StringUtils.hasText(cityIds)) {
            String[] cityIdArr = cityIds.split(",");
            if (cityIdArr.length == 1) {
                return areaService.get(Long.parseLong(cityIdArr[0])).getFullPath();
            }
            Set<Long> cityCodeSet = new HashSet<Long>();
            cityCodeSet.addAll(ListUtil.stringArrayTOLongArray(cityIdArr));
            List<TbArea> areas = areaService.getByIds(cityCodeSet);
            StringBuilder sb = new StringBuilder();
            for (TbArea area : areas) {
                sb.append(area.getFullPath()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return "";
    }

    @Override
    public Criteria<Plan> makeCriteria(Map<String, Object> paramMap, Criteria<Plan> c) {
        if (paramMap.containsKey("cityCode") && StringUtils.hasText(paramMap.get("cityCode").toString())) {
            DetachedCriteria dc_area = c.createCriteria("city", "city");
            dc_area.add(Restrictions.like("cityCode", paramMap.get("cityCode") + "%"));
            if (paramMap.get("isChina") != null && Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 6"));
            } else if (paramMap.get("isChina") != null && !Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 7"));
            }
        }
        if (paramMap.containsKey("planName") && StringUtils.hasText(paramMap.get("planName").toString())) {
            c.like("name", paramMap.get("planName").toString(), MatchMode.ANYWHERE);
        }
        if (paramMap.containsKey("userName") && StringUtils.hasText(paramMap.get("userName").toString())) {
            c.createCriteria("user", "user");
            c.like("user.userName", paramMap.get("userName").toString(), MatchMode.ANYWHERE);
        }
        if (paramMap.containsKey("nickName") && StringUtils.hasText(paramMap.get("nickName").toString())) {
            c.createCriteria("user", "user");
            c.like("user.nickName", paramMap.get("nickName").toString(), MatchMode.ANYWHERE);
        }
        if (paramMap.containsKey("orderProperty") && paramMap.containsKey("orderType")
                && StringUtils.hasText(paramMap.get("orderProperty").toString())
                && StringUtils.hasText(paramMap.get("orderType").toString())) {
            c.orderBy(paramMap.get("orderProperty").toString(), paramMap.get("orderType").toString());
        }
        return c;
    }

    @Override
    public DataAccess<Plan> getDao() {
        return planDao;
    }

    public void delLabelItems(Plan plan) {
        labelItemService.delItemListByTargetIdId(plan.getId(), TargetType.PLAN);
    }
}
