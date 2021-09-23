package com.data.data.hmly.service.restaurant;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.base.util.StringUtil;
import com.data.data.hmly.service.build.LvXBangBuildService;
import com.data.data.hmly.service.restaurant.dao.DelicacyDao;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/12/4.
 */
@Service
public class DelicacyMgrService extends BaseService<Delicacy> {

    @Resource
    private DelicacyDao delicacyDao;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private LvXBangBuildService lvXBangBuildService;
    @Resource
    private LabelItemService labelItemService;
    /**
     * 自动构建静态页面和自动索引
     * @param delicacy
     */
    public void buildAndIndex(Delicacy delicacy) {
        // build 和 index
        lvXBangBuildService.buildOneDelicacy(delicacy.getId());
        lvXBangBuildService.buildDelicacyIndex();
        delicacyService.indexDelicacy(delicacy);
    }

    public Delicacy info(Long id) {
        return delicacyDao.get(Delicacy.class, id);
    }

    public List<String> selDeliResInfo(Map<String, Object> map) {
        String sql = "select d.id as delicacy_id,d.food_name as food_name,"
                + "r.id as res_id,r.res_name as res_name,d.price as price,"
                + "de.recommend_reason as recommendReason,r.city_code as cityCode"
                + "re.res_address as address "
                + "from delicacy d"
                + "inner join delicacy_extend de on d.id = de.id"
                + "inner join delicacy_restaurant dr on d.id = dr.delicacy_id"
                + "inner join restaurant r on r.id = dr.res_id"
                + "inner join restaurant_extend re on r.id = re.id"
                + "where 1 = ?";
        if (map.containsKey("cityCodeStr") && !"".equals(map.get("cityCodeStr"))) {
            sql += " and d.city_code like " + map.get("cityCodeStr") + "%";
        }
        if (map.containsKey("foodName") && !"".equals(map.get("foodName"))) {
            sql += "and d.food_name like %" + map.get("foodName") + "%";
        }
        if (map.containsKey("resName") && !"".equals(map.get("resName"))) {
            sql += "and r.name like %" + map.get("resName") + "%";
        }
        return delicacyDao.findObjectBySQL(sql, Transformers.ALIAS_TO_ENTITY_MAP, 1);
    }

    public Delicacy selById(Long id) {
        return delicacyDao.get(Delicacy.class, id);
    }

    public List<Map<String, String>> listName(Map<String, Object> paramMap) {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        Criteria<Delicacy> c = new Criteria<Delicacy>(Delicacy.class);
        c = makeCriteria(paramMap, c);
        List<Delicacy> delicacies = delicacyDao.findByCriteria(c);
        if (delicacies != null && delicacies.size() > 0) {
            for (Delicacy delicacy : delicacies) {
                Map<String, String> itemMap = new HashMap<String, String>();
                itemMap.put("id", Long.toString(delicacy.getId()));
                itemMap.put("foodName", delicacy.getName());
                resultList.add(itemMap);
            }
        }
        return resultList;
    }

    @Override
    public DataAccess<Delicacy> getDao() {
        return delicacyDao;
    }

    @Override
    public Criteria<Delicacy> makeCriteria(Map<String, Object> paramMap, Criteria<Delicacy> c) {
        DetachedCriteria dc_area = c.createCriteria("city", "a");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("foodName") && !"".equals(paramMap.get("foodName"))) {
            c.like("name", paramMap.get("foodName").toString());
        }
        if (paramMap.containsKey("cityId") && !"".equals(paramMap.get("cityId"))) {
            dc_area.add(Restrictions.like("cityCode", paramMap.get("cityId") + "%"));
            if (paramMap.get("isChina") != null && Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 6"));
            } else if (paramMap.get("isChina") != null && !Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 7"));
            }
        }
        if (paramMap.containsKey("relationed") && StringUtils.isNotBlank(paramMap.get("relationed").toString())
                && Integer.parseInt(paramMap.get("relationed").toString()) == -1) {
            String sql = "select distinct(delicacy_id) from delicacy_restaurant where delicacy_id is not null";
            List<Number> numbers = delicacyDao.findBySQL(sql);
            List<Long> ids = new ArrayList<Long>();
            for (Number n : numbers) {
                ids.add(n.longValue());
            }
            c.in("id", ids);
        }
        if (paramMap.containsKey("relationed") && StringUtils.isNotBlank(paramMap.get("relationed").toString())
                && Integer.parseInt(paramMap.get("relationed").toString()) == 1) {
            String sql = "select distinct(delicacy_id) from delicacy_restaurant where delicacy_id is not null";
            List<Number> numbers = delicacyDao.findBySQL(sql);
            List<Long> ids = new ArrayList<Long>();
            for (Number n : numbers) {
                ids.add(n.longValue());
            }
            c.notin("id", ids);
        }
        if (paramMap.containsKey("needCoverImage") && StringUtils.isNotBlank(paramMap.get("needCoverImage").toString())) {
            c.isNotNull("cover");
            c.ne("cover", "");
        }
        if (paramMap.containsKey("notNeedCoverImage") && StringUtils.isNotBlank(paramMap.get("notNeedCoverImage").toString())) {
            c.or(Restrictions.isNull("cover"), Restrictions.eq("cover", ""));
        }
        return c;
    }

    public void delLabelItems(Delicacy delicacy) {
        labelItemService.delItemListByTargetIdId(delicacy.getId(), TargetType.DELICACY);
    }
}
