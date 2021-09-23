package com.data.data.hmly.service;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.TbArea;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TbAreaService {

    @Resource
    private TbAreaDao tbAreaDao;

    @Resource
    private LabelItemService labelItemService;

    /**
     * 通过cityCode获取区域实体
     *
     * @param cityCode
     * @return
     */
    public TbArea getAreaByCityCode(String cityCode) {

        // Criteria<TbArea> criteria = new Criteria<>(TbArea.class);
        // criteria.eq("cityCode", cityCode);

        String hql = "from TbArea where id =" + cityCode;

        return tbAreaDao.findByHQLWithUniqueObject(hql);
    }

    public TbArea getArea(Long id) {
        return tbAreaDao.load(id);
    }

    public TbArea getById(Long id) {
        return tbAreaDao.getById(id);
    }

    public void updateAll(List<TbArea> tbAreas) {
        tbAreaDao.updateAll(tbAreas);
    }
    public List<TbArea> getByCityCodes(List<String> cityCodeList) {
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        c.in("cityCode", cityCodeList);
        return tbAreaDao.findByCriteria(c);
    }

    public List<TbArea> list(TbArea tbArea, Order order, Page page) {
        if (page == null) {
            return list(tbArea, order);
        }
        Criteria<TbArea> criteria = createCriteria(tbArea, order);
        return tbAreaDao.findByCriteria(criteria, page);
    }

    public List<TbArea> list(TbArea tbArea, Order order) {
        Criteria<TbArea> criteria = createCriteria(tbArea, order);
        return tbAreaDao.findByCriteria(criteria);
    }

    private Criteria<TbArea> createCriteria(TbArea tbArea, Order order) {
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        if (order != null) {
            criteria.orderBy(order);
        }
        if (tbArea == null) {
            return criteria;
        }
        if (!StringUtils.isBlank(tbArea.getName())) {
            criteria.like(("name"), "%" + tbArea.getName() + "%");
        }
        if (tbArea.getFather() != null && tbArea.getFather().getId() != null) {
            criteria.eq(("father.id"), tbArea.getFather().getId());
        }
        if (tbArea.getLevel() != null) {
            criteria.eq(("level"), tbArea.getLevel());
        }
        if (tbArea.isRecommended()) {
            criteria.eq("recommended", tbArea.isRecommended());
        }
        if (tbArea.getLabelItems() != null && !tbArea.getLabelItems().isEmpty()) {
            DetachedCriteria dc_labelItem = criteria.createCriteria("labelItems", "labelItem");
            for (LabelItem labelItem : tbArea.getLabelItems()) {
                dc_labelItem.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
            }
            dc_labelItem.addOrder(Order.asc("order"));
        }
        return criteria;
    }

    /**
     * 获取省
     *
     * @return
     */
    public List<TbArea> getFatherArea() {

        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.eq("level", 1);
        return tbAreaDao.findByCriteria(criteria);
    }

    public List<TbArea> getProByFather(String father) {

        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);

        Long parentId = Long.parseLong(father);

        criteria.eq("father.id", parentId);
        criteria.ne("level", 0);

        List<TbArea> areas = tbAreaDao.findByCriteria(criteria);

        return areas;
    }

    public List<TbArea> getCityByPro(String father, Integer level) {

        new Criteria<TbArea>(TbArea.class);

        Long parentId = null;
        String hql = "";

        if (StringUtils.isNotBlank(father)) {
            parentId = Long.parseLong(father);
            hql = "Select pd from TbArea pd where pd.father.id = " + parentId + " and pd.level = " + level;
        } else {
            hql = "Select pd from TbArea pd where pd.level = " + level;
        }


        // criteria.eq("father.id", parentId);
        // criteria.eq("level", level);
        // List<TbArea> areas = tbAreaDao.findByCriteria(criteria);


        List<TbArea> areas = tbAreaDao.findByHQL(hql);

        return areas;

    }

    public List<TbArea> findProvince() {
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.eq("level", 1);
        return tbAreaDao.findByCriteria(criteria);
    }

    /**
     * 查询国内省份
     * @return
     */
    public List<TbArea> findCNProvince() {
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.eq("level", 1);
        criteria.lt("id", 1000000L);
        return tbAreaDao.findByCriteria(criteria);
    }

    public List<TbArea> findCity(long provinceId) {
        // TODO Auto-generated method stub
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.eq("level", 2);
        criteria.eq("father.id", provinceId);
        return tbAreaDao.findByCriteria(criteria);
    }

    public List<TbArea> findArea(long cityId) {
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        c.eq("level", 3);
        c.eq("father.id", cityId);
        return tbAreaDao.findByCriteria(c);
    }

    /**
     * 为了不产生N+1查询（查询TbAreaExtend），用hql进行查询需要的字段
     * @param parentId
     * @param name
     * @param level
     * @return
     */
    public List<TbArea> findArea(Long parentId, String name, Integer level) {
        StringBuilder hql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        hql.append("select new TbArea(t.id, t.name, t.cityCode) ");
        hql.append("from TbArea t where 1 = 1 and t.id < 1000000L ");

        if (parentId != null) {
            hql.append("and t.father.id = ? ");
            params.add(parentId);
        }
        if (StringUtils.isNotBlank(name)) {
            hql.append("and t.name like ? ");
            params.add("%" + name + "%");
        }
        if (level != null) {
            hql.append("and t.level = ? ");
            params.add(level);
        }
        return tbAreaDao.findByHQL(hql.toString(), params.toArray());
    }

    /**
     * 查找pinyin字段为空的
     * @param page
     * @return
     */
    public List<TbArea> findAreaWithoutPinyin(Page page) {
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        c.or(Restrictions.isNull("pinyin"), Restrictions.eq("pinyin", ""));
        if (page != null) {
            return tbAreaDao.findByCriteria(c, page);
        } else {
            return tbAreaDao.findByCriteria(c);
        }
    }

    public List<TbArea> getAreaByName(String name) {

        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.like("name", "%" + name + "%");
        return tbAreaDao.findByCriteria(criteria);
    }

    public void doRecommendCities(List<Long> ids) {
        tbAreaDao.doRecommendCities(ids);
    }

    public List<TbArea> listAreaWithPage(Page page) {
        if (page != null) {
            return tbAreaDao.findAll(page);
        }
        return null;
    }

    public List<TbArea> listAllArea(TbArea areaCondition, Order asc) {
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        c.not("level", 3);
        c.orderBy(Order.asc("pinyin"));
        return tbAreaDao.findByCriteria(c);
    }

    public List<TbArea> listLevel2Area(TbArea areaCondition, List<Long> aIdList, Order asc) {

        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        c.eq("level", areaCondition.getLevel());
        c.isNotNull("pinyin");
        if (!aIdList.isEmpty()) {
            c.in("id", aIdList);
        }
        c.orderBy(Order.asc("pinyin"));
        return tbAreaDao.findByCriteria(c);
    }

    /**
     * 列出需要索引的目的地(必须分页查询)
     * 不索引4个直辖市省份!!
     * @param page
     * @return
     */
    public List<TbArea> listIndexArea(Page page) {
        Criteria<TbArea> criteria = new Criteria<>(TbArea.class);
        criteria.lt("level", 3);
        List<Long> filterAreaIds = new ArrayList<>();
        // 过滤4个直辖市省份!!
        filterAreaIds.add(110000L);
        filterAreaIds.add(120000L);
        filterAreaIds.add(310000L);
        filterAreaIds.add(500000L);
        criteria.notin("id", filterAreaIds);
        criteria.isNotNull("pinyin");
        criteria.orderBy(Order.asc("pinyin"));
        return tbAreaDao.findByCriteria(criteria, page);
    }

    public List<TbArea> getAreaListByName(List<String> areaNames) {
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        c.in("name", areaNames);
        c.le("level", 2);
        c.notin("id", Lists.newArrayList(Long.valueOf(110100), Long.valueOf(120100), Long.valueOf(310100), Long.valueOf(500100)));
        c.orderBy(Order.asc("pinyin"));
        return tbAreaDao.findByCriteria(c);
    }

    public List<TbArea> getByIds(List<Long> ids) {
        String hql = "from TbArea where id in (:ids)";
        Map<String, Object> data = Maps.newHashMap();
        data.put("ids", ids);
        return tbAreaDao.findByHQL2(hql, data);
    }

    public List<TbArea> findAreaListByFather(TbArea father) {
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        if (father.getLevel() != null) {
            c.eq("level", father.getLevel());
        }
        c.eq("father.id", father.getId());
        if (StringUtils.isNotBlank(father.getName())) {
            c.ne("name", father.getName());
        }
        return tbAreaDao.findByCriteria(c);
    }

    public List<TbArea> findAreasByLabelName(Label label, Page pageInfo) {

        List<TbArea> areaList = new ArrayList<TbArea>();

        List<LabelItem> itemList = labelItemService.findTargIdsByLabel(label, TargetType.CITY, pageInfo);

        List<Long> areaIds = new ArrayList<Long>();

        for (LabelItem item : itemList) {
            areaIds.add(item.getTargetId());
        }
        Criteria<TbArea> c = new Criteria<TbArea>(TbArea.class);
        if (areaIds.isEmpty()) {
            return areaList;
        }
        c.in("id", areaIds);
        areaList = tbAreaDao.findByCriteria(c);
        return areaList;
    }
}
