package com.data.data.hmly.service.restaurant;

import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.restaurant.dao.DelicacyDao;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.vo.DelicacySolrEntity;
import com.data.data.hmly.service.vo.ProductLabelVo;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jonathan.Guo
 */
@Service
public class DelicacyService extends SolrIndexService<Delicacy, DelicacySolrEntity> {

    Logger logger = Logger.getLogger(DelicacyService.class);

    @Resource
    private DelicacyDao delicacyDao;
    @Resource
    private LabelService labelService;
    @Resource
    private TbAreaDao areaDao;
    @Resource
    private TbAreaService tbAreaService;

    public Delicacy get(Long id) {
        return delicacyDao.load(id);
    }

    public List<Delicacy> listAll() {
        return list(new Delicacy(), null);
    }

    public List<Delicacy> list(Delicacy delicacy, Page page, String... orderProperties) {
        Criteria<Delicacy> criteria = createCriteria(delicacy, orderProperties);
        if (page != null) {
            return delicacyDao.findByCriteria(criteria, page);
        }
        return delicacyDao.findByCriteria(criteria);
    }

    public List<Delicacy> getRecommendDelicacy() {
        Label label = new Label();
        label.setName("推荐美食");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        Delicacy delicacy = new Delicacy();
        delicacy.setLabelItems(list);
        return list(delicacy, null);
    }

    public List<Delicacy> getFeaturedDelicacy(String cityCode) {
        Label label = new Label();
        label.setName("特色美食");
        TbArea tbArea = new TbArea();
        //tbArea.setCityCode(cityCode);
        tbArea.setId(Long.parseLong(cityCode));
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        Delicacy delicacy = new Delicacy();
        delicacy.setLabelItems(list);
        delicacy.setCity(tbArea);
        return list(delicacy, null);
    }

    public List<Delicacy> getTopDelicacy(Long cityCode) {
        Label label = new Label();
        label.setName("top10");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        Delicacy delicacy = new Delicacy();
        delicacy.setLabelItems(list);
        TbArea tbArea = tbAreaService.getArea(cityCode);
        delicacy.setCity(tbArea);
        return list(delicacy, null);
    }

    public Criteria<Delicacy> createCriteria(Delicacy delicacy, String... orderProperties) {
        Criteria<Delicacy> criteria = new Criteria<Delicacy>(Delicacy.class);
        criteria.eq("status", 1);
        if (orderProperties.length == 2) {
            if (orderProperties[0].startsWith("extend")) {
                criteria.createCriteria("extend", "extend");
            }
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            if (orderProperties[0].startsWith("extend")) {
                criteria.createCriteria("extend", "extend");
            }
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(delicacy.getName())) {
            criteria.like("name", delicacy.getName());
        }
        if (delicacy.getCity() != null) {
            criteria.eq("city.id", delicacy.getCity().getId());
            //criteria.eq("city.cityCode", delicacy.getCity().getCityCode());
        }
        if (delicacy.getCuisine() != null) {
            criteria.eq("cuisine", delicacy.getCuisine());
        }
        if (delicacy.getTaste() != null) {
            criteria.eq("taste", delicacy.getTaste());
        }
        if (delicacy.getEfficacy() != null) {
            criteria.eq("efficacy", delicacy.getEfficacy());
        }
        if (delicacy.getUser() != null) {
            criteria.eq("user.id", delicacy.getUser().getId());
        }
        if (delicacy.getLabelItems() != null && !delicacy.getLabelItems().isEmpty()) {
            DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
            for (LabelItem labelItem : delicacy.getLabelItems()) {
                criterion.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
            }
            criterion.addOrder(Order.asc("order"));
        }
        return criteria;
    }


    public void indexDelicacy() {
        Clock clock = new Clock();
        List<Delicacy> list = listAll();
        logger.info("start indexing delicacy, count: " + list.size());

        List<DelicacySolrEntity> entities = Lists.transform(list, new Function<Delicacy, DelicacySolrEntity>() {
            @Override
            public DelicacySolrEntity apply(Delicacy delicacy) {
                return new DelicacySolrEntity(delicacy);
            }
        });
        index(entities);
        logger.info("index delicacy success, cost: " + clock.elapseTime());
    }

    public void indexDelicacy(Delicacy delicacy) {
        try {
            // 删除状态不为正常的美食索引
            if (delicacy.getStatus() != 1) {
                UpdateResponse updateResponse = deleteIndexByEntityId(delicacy.getId(), SolrType.delicacy);
                if (updateResponse.getStatus() != 0) {
                    logger.error("发生错误: delicacy#" + delicacy.getId() + "删除索引失败!");
                } else {
                    logger.error("操作成功: delicacy#" + delicacy.getId() + "删除索引成功!");
                }
            } else {
                DelicacySolrEntity entity = new DelicacySolrEntity(delicacy);
                List<DelicacySolrEntity> entities = Lists.newArrayList(entity);
                index(entities);
            }
        } catch (Exception e) {
            logger.error("未知异常，delicacy#" + delicacy.getId() + "索引失败");
        }
    }

    public List<ProductLabelVo> findDelicacyList() {


        return null;
    }

    public List<Delicacy> getDelicacyLabels(Delicacy info, TbArea area, String tagIds, Page pageInfo) {
        Criteria<Delicacy> criteria = new Criteria<Delicacy>(Delicacy.class);


        if (info != null) {
            criteria.like("name", "%" + info.getName() + "%");
        }
        criteria.eq("status", 1);
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                criteria.in("city", areas);
            } else if (area.getLevel() == 2) {
                List<TbArea> citys = areaDao.findCityByCity(area.getId());
                citys.add(area);
                criteria.in("city", citys);
            }
        }

        List<Delicacy> infos = new ArrayList<Delicacy>();
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
            criteria.in("id", ids);
            infos = delicacyDao.findByCriteria(criteria, pageInfo);
        } else if (tagIds == null) {
            infos = delicacyDao.findByCriteria(criteria, pageInfo);
        }
        return infos;
    }


    /**
     * 通过某个表签查找目的地下的美食排行榜
     *
     * @param label
     * @return
     */
    public List<Delicacy> getTopDeliByDestination(List<TbArea> tbAreas, Page page) {

        Delicacy delicCodition = new Delicacy();

        return delicacyDao.getTopSceByDestination(delicCodition, null, tbAreas, page);
    }

    public Delicacy addCollect(Long id) {
        Delicacy delicacy = get(id);
        Integer collectNum = delicacy.getExtend().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        delicacy.getExtend().setCollectNum(collectNum);
        delicacyDao.update(delicacy);
        return delicacy;
    }

    public Integer deleteCollect(Long id) {
        Delicacy delicacy = get(id);
        Integer collectNum = delicacy.getExtend().getCollectNum();
        if (collectNum == null || collectNum == 0) {
            return 0;
        }
        collectNum--;
        delicacy.getExtend().setCollectNum(collectNum);
        delicacyDao.update(delicacy);
        return collectNum;
    }

    public Integer getCollectNum(Long id) {
        Delicacy delicacy = get(id);
        Integer collectNum = delicacy.getExtend().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        return collectNum;
    }

    //根据id获取状态
    public Integer getStatus(Long id) {
        String sql = "select status as result from delicacy where id=?";
        BigDecimal status = delicacyDao.findIntegerBySQL(sql, id);
        return status == null ? 0 : status.intValue();

    }
}
