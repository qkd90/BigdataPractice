package com.data.data.hmly.service.area;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.area.entity.SupplierCity;
import com.data.data.hmly.service.area.vo.TbAreaSolrEntity;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.util.Clock;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by guoshijie on 2015/11/26.
 */
@Service
public class AreaService extends SolrIndexService<TbArea, TbAreaSolrEntity> {

    Logger logger = Logger.getLogger(AreaService.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private TbAreaDao tbAreaDao;
    @Resource
    private SupplierCityService supplierCityService;
    @Resource
    private LabelService labelService;

    @Resource
    private LabelItemService labelItemService;

    public List<TbArea> listAllProvinceArea() {

        TbArea areaCondition = new TbArea();
        areaCondition.setLevel(1);
        TbArea father = new TbArea();
        father.setId(100000l);
        areaCondition.setFather(father);
        return tbAreaService.list(areaCondition, Order.asc("pinyin"));
    }

	/*
     * public List<TbArea> listAllArea() {
	 * 
	 * TbArea areaCondition = new TbArea(); areaCondition.setLevel(1);
	 * List<TbArea> oldList = tbAreaService.list(areaCondition,
	 * Order.asc("pinyin")); List<TbArea> newList = new ArrayList<TbArea>(); for
	 * (TbArea a : oldList) { String name = a.getName(); if
	 * ("黑龙江省".equals(name)) { name = name.substring(0, 3); } else { name =
	 * name.substring(0, 2); } a.setName(name); newList.add(a); }
	 * 
	 * return newList; }
	 */

    public TbArea get(Long id) {
        return tbAreaService.getArea(id);
    }

    public List<TbArea> getHomeHotArea() {
        Label label = new Label();
        label.setName("首页热门目的地");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getAbroadHotArea() {
        Label label = new Label();
        label.setName("城市选择境外热门");
        return getAreaByLabel(label);
    }

    public List<TbArea> getTrafficHotArea() {
        Label label = new Label();
        label.setName("交通旅行目的地");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getHotArea() {
        Label label = new Label();
        label.setName("热门目的地");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getDestinationInPlanModule() {
        Label label = new Label();
        label.setName("行程规划旅行目的地");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getSeasonHotArea() {
        Label label = new Label();
        label.setName("当季热门目的地");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getHotHotelArea() {
        Label label = new Label();
        label.setName("热门酒店目的地");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getDestinationList() {
        Label label = new Label();
        label.setName("通用目的地-国内");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getHandDrawCityList() {
        Label label = new Label();
        label.setName("手绘图城市");
        return this.getAreaByLabel(label);
    }

    public List<TbArea> getLineIndexArea(String labelName, Page page) {
        Label label = new Label();
        label.setName(labelName);
        return this.getAreaByLabel(label, page);
    }

    /**
     * 通过某个表签查找目的地
     *
     * @param label
     * @return
     */
    public List<TbArea> getAreaByLabel(Label label) {
        TbArea areaCodition = new TbArea();
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> labelItems = new HashSet<LabelItem>();
        labelItems.add(labelItem);
        areaCodition.setLabelItems(labelItems);
        return tbAreaService.list(areaCodition, null);
    }

    /**
     * 通过某个表签查找目的地
     *
     * @param label
     * @return
     */
    public List<TbArea> getAreaByLabel(Label label, Page page) {
        TbArea areaCodition = new TbArea();
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> labelItems = new HashSet<LabelItem>();
        labelItems.add(labelItem);
        areaCodition.setLabelItems(labelItems);
        return tbAreaService.list(areaCodition, null, page);
    }

    public List<TbArea> listLevel2Area() {
        TbArea areaCondition = new TbArea();
        areaCondition.setLevel(2);
        List<TbArea> list = tbAreaService.listLevel2Area(areaCondition, new ArrayList<Long>(),
                Order.asc("pinyin"));
        return list;
    }

    public void indexArea() {
        List<TbArea> list = new ArrayList<>();
        List<TbAreaSolrEntity> solrEntities = new ArrayList<>();
        Clock clock = new Clock();
        int pageNo = 1;
        int pageSize = 100;
        int processed = 0;
        int total;
        Page page;
        while (true) {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            page = new Page(pageNo, pageSize);
            list = tbAreaService.listIndexArea(page);
            // 已处理的记录数目
            processed += list.size();
            // 符合条件总记录数目
            total = page.getTotalCount();
            if (list.isEmpty()) {
                break;
            }
            solrEntities = Lists.transform(list, new Function<TbArea, TbAreaSolrEntity>() {
                        @Override
                        public TbAreaSolrEntity apply(TbArea tbArea) {
                            return new TbAreaSolrEntity(tbArea);
                        }
                    });
            super.index(solrEntities);
            logger.info("Save " + pageSize + " area records cost " + clock.elapseTime() + "ms");
            if (processed >= total) {
                break;
            }
            pageNo += 1;
            list.clear();
            solrEntities.clear();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        }
        logger.info("Index all area in " + clock.totalTime() + "ms with " + processed + "records");
    }

    public void indexArea(TbArea tbArea) {
        try {
            TbAreaSolrEntity areaSolrEntity = new TbAreaSolrEntity(tbArea);
            List<TbAreaSolrEntity> solrEntities = Lists
                    .newArrayList(areaSolrEntity);
            super.index(solrEntities);
        } catch (Exception e) {
            logger.error("未知异常，Area(目的地)#" + tbArea.getId() + "索引失败", e);
        }
    }

    public void doRecommendCities(List<Long> cityIds) {
        tbAreaService.doRecommendCities(cityIds);
    }

    public void saveSupplierCity(List<SupplierCity> list) {
        supplierCityService.save(list);
    }

    public List<SupplierCity> listSupplierCity(SysUnit sysUnit) {
        return supplierCityService.listBySupplier(sysUnit);
    }

    public List<TbArea> getAreaLabels(TbArea info, TbArea area, String tagIds,
                                      Page pageInfo) {
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);

//        criteria.eq("level", 2);
        if (info != null) {
            criteria.like("name", "%" + info.getName() + "%");
        }
//        if (area != null) {
//            if (area.getLevel() == 1) {
//                // List<TbArea> areas = tbAreaDao.findCityByPro(area.getId());
//                criteria.eq("father", area);
//            } else if (area.getLevel() == 2) {
//                criteria.eq("cityCode", area);
//            }
//        }
        if (area != null && area.getId() != null) {
            criteria.eq("id", area.getId());
        }

        List<TbArea> infos = new ArrayList<TbArea>();
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
            criteria.in("id", ids);
            infos = tbAreaDao.findByCriteria(criteria, pageInfo);
        } else if (tagIds == null) {
            infos = tbAreaDao.findByCriteria(criteria, pageInfo);
        }
        return infos;

    }

    public List<TbArea> getChildAreas(TbArea area) {

        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.eq("father", area);
        List<TbArea> areas = tbAreaDao.findByCriteria(criteria);
        List<TbArea> newAreas = new ArrayList<TbArea>();
        if (!areas.isEmpty()) {
            newAreas = getAllChildAreas(areas);
        }
        newAreas.addAll(areas);
        newAreas.add(area);
        List<TbArea> listTemp = new ArrayList<TbArea>();
        Iterator<TbArea> it = newAreas.iterator();
        while (it.hasNext()) {
            TbArea a = it.next();
            if (listTemp.contains(a)) {
                it.remove();
            } else {
                listTemp.add(a);
            }
        }
        return listTemp;
    }

    //获取3级的城市
    public List<TbArea> getAllChildAreas(List<TbArea> areas) {

        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);

        criteria.in("father", areas);

        return tbAreaDao.findByCriteria(criteria);
    }

    public List<TbArea> listAllArea() {
        TbArea areaCondition = new TbArea();
        // areaCondition.setLevel(1);
        List<TbArea> oldList = tbAreaService.listAllArea(areaCondition,
                Order.asc("pinyin"));

        return oldList;
    }

    public List<TbArea> getGroupsAreas() {
        TbArea areaCondition = new TbArea();

        List<LabelItem> labelItemList = labelItemService.getLabelItemByType(TargetType.CITY);
        Set<Long> aIdList = new HashSet<Long>();
        for (LabelItem item : labelItemList) {
            aIdList.add(item.getTargetId());
        }
        areaCondition.setLevel(2);
        List<TbArea> areaList = tbAreaService.listLevel2Area(areaCondition, new ArrayList<Long>(aIdList),
                Order.asc("pinyin"));
        return areaList;
    }

    public List<TbArea> getTrafficAreas(Long labelId) {
        List<LabelItem> labelItemList = labelItemService.getByLabel(labelId);
        Set<Long> aIdList = new HashSet<Long>();
        for (LabelItem item : labelItemList) {
            aIdList.add(item.getTargetId());
        }
        TbArea areaCondition = new TbArea();
        areaCondition.setLevel(2);
        List<TbArea> areaList = tbAreaService.listLevel2Area(areaCondition, new ArrayList<Long>(aIdList),
                Order.asc("pinyin"));
        return areaList;
    }

    public List<TbArea> getAreaListByName(List<String> areaNames) {

        List<String> aNames = new ArrayList<String>();

        for (String str : areaNames) {
            aNames.add(str);
        }
        return tbAreaService.getAreaListByName(aNames);
    }

    public List<TbArea> getByIds(String ids) {
        //
        String[] idsStringList = ids.split(",");
        List<Long> idList = new ArrayList<Long>();
        for (String idString : idsStringList) {
            //
            if (StringUtils.isNotBlank(idString)) {
                idList.add(Long.parseLong(idString));
            }
        }
        return getByIds(idList);

    }

    public List<TbArea> getByIds(List<Long> ids) {

        if (ids.isEmpty()) {
            return new ArrayList<TbArea>();
        }
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.in("id", ids);
        return tbAreaDao.findByCriteria(criteria);
    }

    public List<TbArea> getByIds(Set<Long> ids) {
        //
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.in("id", ids);
        return tbAreaDao.findByCriteria(criteria);
    }


    public TbArea getLocation(String ip) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        if (ip.matches("192.168.*.*") || ip.matches("127.0.0.1")) {
            List<TbArea> areas = tbAreaService.getAreaByName("厦门");
            if (!areas.isEmpty()) {
                return areas.get(0);
            }
        }
        String ipstr = HttpUtils.post("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip, Maps.<String, Object>newHashMap());
        try {
            Map<String, Object> ipMap = objectMapper.readValue(ipstr, Map.class);
            String cityName = (String) ipMap.get("city");
            List<TbArea> areas = tbAreaService.getAreaByName(cityName);
            if (areas.isEmpty()) {
                return null;
            }
            return areas.get(0);
        } catch (JsonMappingException e) {
            logger.error("根据IP地址获取城市信息失败，IP地址为：" + ip, e);
        } catch (JsonParseException e) {
            logger.error("根据IP地址获取城市信息失败，IP地址为：" + ip, e);
        } catch (IOException e) {
            logger.error("根据IP地址获取城市信息失败，IP地址为：" + ip, e);
        }
        return null;
    }

    public List<TbArea> getInIdRange(Long startId, Long endId, int size) {
        StringBuilder hql = new StringBuilder("from TbArea where id>:startId");
        Map<String, Object> data = Maps.newHashMap();
        data.put("startId", startId);
        if (endId != null) {
            hql.append(" and id<=:endId");
            data.put("endId", endId);
        }
        return tbAreaDao.findByHQL2(hql.toString(), new Page(1, size), data);
    }

    public List<TbArea> listCityByProvince(Long provinceId) {
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.eq("level", 2);
        criteria.eq("father.id", provinceId);
        return tbAreaDao.findByCriteria(criteria);
    }

    public List<TbArea> findByLabel(Label label, Page page) {
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
        criterion.add(Restrictions.eq("label.id", label.getId()));
        criterion.addOrder(Order.asc("order"));
        return tbAreaDao.findByCriteria(criteria, page);
    }
}
