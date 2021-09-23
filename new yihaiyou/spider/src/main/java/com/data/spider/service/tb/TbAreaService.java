package com.data.spider.service.tb;

import com.data.spider.service.dao.TbAreaDao;
import com.data.spider.service.pojo.tb.TbArea;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbAreaService {

    @Resource
    private TbAreaDao tbAreaDao;

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
        if (tbArea.getId() != null) {
            criteria.eq(("id"), tbArea.getId());
        }
        if (!StringUtils.isBlank(tbArea.getName())) {
            criteria.eq(("name"), "%" + tbArea.getName() + "%");
        }
        if (tbArea.getFather() != null && tbArea.getFather().getId() != null) {
            criteria.eq(("father.id"), tbArea.getFather().getId());
        }
        if (tbArea.getLevel() != null) {
            criteria.eq(("level"), tbArea.getLevel());
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

        List<TbArea> areas = tbAreaDao.findByCriteria(criteria);

        return areas;
    }

    public List<TbArea> getCityByPro(String father, Integer level) {

        new Criteria<TbArea>(TbArea.class);

        Long parentId = Long.parseLong(father);

        // criteria.eq("father.id", parentId);
        // criteria.eq("level", level);
        // List<TbArea> areas = tbAreaDao.findByCriteria(criteria);

        String hql = "Select pd from TbArea pd where pd.father.id = " + parentId + " and pd.level = " + level;

        List<TbArea> areas = tbAreaDao.findByHQL(hql);

        return areas;

    }

    public List<TbArea> findProvince() {
        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.eq("level", 1);
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

    public List<TbArea> getAreaByName(String name) {

        Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
        criteria.like("name", "%" + name + "%");
        return tbAreaDao.findByCriteria(criteria);
    }

    public void doRecommendCities(List<Long> ids) {
        tbAreaDao.doRecommendCities(ids);
    }

    public void save(TbArea tbArea) {
        tbAreaDao.save(tbArea);
    }


    public List<TbArea> gets(int size, Criteria<TbArea> c) {
        Page page = new Page(1, size);
        List<TbArea> dis = tbAreaDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }
}
