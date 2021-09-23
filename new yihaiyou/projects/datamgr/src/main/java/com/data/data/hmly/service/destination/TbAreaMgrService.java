package com.data.data.hmly.service.destination;

import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.TbArea;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/1/23.
 */
@Service
public class TbAreaMgrService extends BaseService<TbArea> {

    @Resource
    private TbAreaDao tbAreaDao;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private AreaService areaService;




    @Override
    public DataAccess<TbArea> getDao() {
        return tbAreaDao;
    }
    @Override
    public Criteria<TbArea> makeCriteria(Map<String, Object> paramMap, Criteria<TbArea> c) {
        DetachedCriteria dcExtend = c.createCriteria("tbAreaExtend", "te");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("name") && !"".equals(paramMap.get("name"))) {
            c.or(Restrictions.like("name", "%" + paramMap.get("name") + "%"),
                 Restrictions.like("pinyin", "%" + paramMap.get("name") + "%"));
        }
        if (paramMap.containsKey("hasCover")) {
            Integer hasCove = Integer.parseInt(paramMap.get("hasCover").toString());
            switch (hasCove) {
                case 0:
                    dcExtend.add(Restrictions.or(Restrictions.isNull("cover"), Restrictions.eq("cover", "")));
                    break;
                case 1:
                    dcExtend.add(Restrictions.and(Restrictions.isNotNull("cover"), Restrictions.ne("cover", "")));
                    break;
                default:
                    break;
            }
        }
        if (paramMap.containsKey("hasBestVisitTime")) {
            Integer hasCove = Integer.parseInt(paramMap.get("hasBestVisitTime").toString());
            switch (hasCove) {
                case 0:
                    dcExtend.add(Restrictions.or(Restrictions.isNull("bestVisitTime"), Restrictions.eq("bestVisitTime", "")));
                    break;
                case 1:
                    dcExtend.add(Restrictions.and(Restrictions.isNotNull("bestVisitTime"), Restrictions.ne("bestVisitTime", "")));
                    break;
                default:
                    break;
            }
        }
        if (paramMap.containsKey("hasAdviceTime")) {
            Integer hasCove = Integer.parseInt(paramMap.get("hasAdviceTime").toString());
            switch (hasCove) {
                case 0:
                    dcExtend.add(Restrictions.or(Restrictions.isNull("adviceTime"), Restrictions.eq("adviceTime", "")));
                    break;
                case 1:
                    dcExtend.add(Restrictions.and(Restrictions.isNotNull("adviceTime"), Restrictions.ne("adviceTime", "")));
                    break;
                default:
                    break;
            }
        }
        if (paramMap.containsKey("hasAbs")) {
            Integer hasCove = Integer.parseInt(paramMap.get("hasAbs").toString());
            switch (hasCove) {
                case 0:
                    dcExtend.add(Restrictions.or(Restrictions.isNull("abs"), Restrictions.eq("abs", "")));
                    break;
                case 1:
                    dcExtend.add(Restrictions.and(Restrictions.isNotNull("abs"), Restrictions.ne("abs", "")));
                    break;
                default:
                    break;
            }
        }
        if (paramMap.containsKey("cityCode") && !"".equals(paramMap.get("cityCode"))) {
            c.like("cityCode", paramMap.get("cityCode").toString(), MatchMode.START);
            if (paramMap.get("isChina") != null && Boolean.valueOf(paramMap.get("isChina").toString())) {
                c.add(Restrictions.sqlRestriction("length(city_code) = 6"));
            } else if (paramMap.get("isChina") != null && !Boolean.valueOf(paramMap.get("isChina").toString())) {
                c.add(Restrictions.sqlRestriction("length(city_code) = 7"));
            }
        }
        return c;
    }
}
