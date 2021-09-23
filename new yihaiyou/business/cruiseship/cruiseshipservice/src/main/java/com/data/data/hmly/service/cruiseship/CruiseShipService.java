package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.cruiseship.dao.CruiseShipDao;
import com.data.data.hmly.service.cruiseship.dao.CruiseShipExtendDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipExtend;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.cruiseship.vo.CruiseShipSolrEntity;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CruiseShipService extends SolrIndexService<CruiseShip, CruiseShipSolrEntity> {
    @Resource
    private CruiseShipDao cruiseShipDao;
    @Resource
    private CruiseShipExtendDao cruiseShipExtendDao;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;
    @Resource
    private TbAreaDao areaDao;

    /**
     * 分页查询
     *
     * @param cruiseShip
     * @param page
     * @param sysUser
     * @param isSiteAdmin
     * @param isSupperAdmin
     * @return
     */
    public List<CruiseShip> pageCruiseShips(CruiseShip cruiseShip, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        return cruiseShipDao.pageCruiseShips(cruiseShip, page, sysUser, isSiteAdmin, isSupperAdmin);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    public CruiseShip findById(Long id) {
        return cruiseShipDao.load(id);
    }

    /**
     * 保存
     *
     * @param cruiseShip
     * @param cruiseShipExtend
     */
    public void saveCruiseShipInfo(CruiseShip cruiseShip, CruiseShipExtend cruiseShipExtend) {
        cruiseShip.setCruiseShipExtend(cruiseShipExtend);
        cruiseShipExtend.setCruiseShip(cruiseShip);
        cruiseShipDao.save(cruiseShip);
        cruiseShip.setProductNo(cruiseShip.getId().toString()); // 设置产品号
        cruiseShipDao.update(cruiseShip);
        cruiseShipExtendDao.save(cruiseShipExtend);
    }

    /**
     * 更新
     *
     * @param cruiseShip
     * @param cruiseShipExtend
     */
    public void updateCruiseShipInfo(CruiseShip cruiseShip, CruiseShipExtend cruiseShipExtend) {
        cruiseShipDao.update(cruiseShip);
        cruiseShipExtendDao.update(cruiseShipExtend);
    }

    public Map<String, Object> batchUpdateStatus(Map<String, Object> result, String ids, ProductStatus status) {
        String[] idArray = ids.split(",");
        List<CruiseShip> cruiseShipList = new ArrayList<CruiseShip>();
        for (String idStr : idArray) {
            Long cruiseShipId = Long.parseLong(idStr);
            CruiseShip cruiseShip = cruiseShipDao.load(cruiseShipId);
            if (cruiseShip != null) {
                if (ProductStatus.UP.equals(status)) {
                    // 上架需要操作检查邮轮数据完整, 如报价
                    result = cruiseShipDateService.checkPriceExists(result, cruiseShip);
                    if (!(Boolean) result.get("success")) {
                        return result;
                    }
                    cruiseShip.setStatus(status);
                    cruiseShipList.add(cruiseShip);
                } else {
                    cruiseShip.setStatus(status);
                    cruiseShipList.add(cruiseShip);
                }
            } else {
                result.put("success", false);
                result.put("msg", "id为:[ " + cruiseShipId + "]的邮轮数据不存在, 可能已被删除, 请刷新列表后重试!");
                return result;
            }
        }
        cruiseShipDao.updateAll(cruiseShipList);
        result.put("success", true);
        result.put("msg", "邮轮状态更新成功!");
        return result;
    }


    public void indexAll() {
        Criteria<CruiseShip> criteria = new Criteria<CruiseShip>(CruiseShip.class);
        criteria.eq("status", ProductStatus.UP);
        List<CruiseShip> list = cruiseShipDao.findByCriteria(criteria);
        List<CruiseShipSolrEntity> entities = Lists.newArrayList();
        for (CruiseShip cruiseShip : list) {
            entities.addAll(cruiseShipToEntity(cruiseShip));
        }
        List<CruiseShipSolrEntity> solrEntities = Lists.newArrayList();
        for (CruiseShipSolrEntity entity : entities) {
            if (entity.getPrice() == 0) {
                deleteIndexByEntityId(entity.getId(), SolrType.cruise_ship);
            } else {
                solrEntities.add(entity);
            }
        }
        index(solrEntities);
    }

    public void index(CruiseShip cruiseShip) {
        List<CruiseShipSolrEntity> entities = cruiseShipToEntity(cruiseShip);
        if (!cruiseShip.getStatus().equals(ProductStatus.UP)) {
            for (CruiseShipSolrEntity entity : entities) {
                deleteIndexByEntityId(entity.getId(), SolrType.cruise_ship);
            }
        } else {
            List<CruiseShipSolrEntity> solrEntities = Lists.newArrayList();
            for (CruiseShipSolrEntity entity : entities) {
                if (entity.getPrice() == 0) {
                    deleteIndexByEntityId(entity.getId(), SolrType.cruise_ship);
                } else {
                    solrEntities.add(entity);
                }
            }
            index(solrEntities);
        }
    }

    public Float getMinPriceByCruiseShip(CruiseShip cruiseShip) {
        List<CruiseShipDate> dateList = cruiseShipDateService.listCruiseShipDates(cruiseShip.getId(), new Date(), null);
        List<Float> priceList = Lists.transform(dateList, new Function<CruiseShipDate, Float>() {
            @Override
            public Float apply(CruiseShipDate cruiseShipDate) {
                return cruiseShipDate.getMinDiscountPrice();
            }
        });
        return Collections.min(priceList);
    }



    private List<CruiseShipSolrEntity> cruiseShipToEntity(final CruiseShip cruiseShip) {
        List<CruiseShipDate> dateList = cruiseShipDateService.listByCruiseShipId(cruiseShip.getId(), new Date());
        List<CruiseShipSolrEntity> entities = Lists.transform(dateList, new Function<CruiseShipDate, CruiseShipSolrEntity>() {
            @Override
            public CruiseShipSolrEntity apply(CruiseShipDate input) {
                input.setCruiseShip(cruiseShip);
                CruiseShipSolrEntity entity = new CruiseShipSolrEntity(input);
                List<CruiseShipRoomDate> dates = cruiseShipRoomDateService.findByDateId(input.getId(), "salePrice", "asc");
                if (!dates.isEmpty()) {
                    entity.setPrice(dates.get(0).getSalePrice());
                } else {
                    entity.setPrice(0f);
                }
                return entity;
            }
        });
        entities.removeAll(Collections.singleton(null));
        return entities;
    }

    public List<CruiseShip> list(CruiseShip cruiseShip, Page page) {
        Criteria<CruiseShip> criteria = new Criteria<CruiseShip>(CruiseShip.class);
        createCriteria(cruiseShip, criteria);
        criteria.ne("status", ProductStatus.DEL);
        return cruiseShipDao.findByCriteria(criteria, page);
    }



    public List<CruiseShip> getCheckingList(CruiseShip cruiseShip, SysUser loginUser, Page pageInfo, Boolean siteAdmin) {
        Criteria<CruiseShip> criteria = new Criteria<CruiseShip>(CruiseShip.class);
        createCriteria(cruiseShip, criteria);

        if (!siteAdmin) {
            criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
            criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
        }
        return cruiseShipDao.findByCriteria(criteria, pageInfo);
    }

    private void createCriteria(CruiseShip cruiseShip, Criteria<CruiseShip> criteria) {
        if (cruiseShip.getStatus() != null) {
            criteria.eq("status", cruiseShip.getStatus());
        }

        if (cruiseShip.getId() != null) {
            criteria.eq("id", cruiseShip.getId());
        }

        if (StringUtils.isNotBlank(cruiseShip.getName())) {
            criteria.like("name", cruiseShip.getName(), MatchMode.ANYWHERE);
        }
        criteria.ne("status", ProductStatus.DEL);
    }

    public List<CruiseShip> getCruiseShipLabels(CruiseShip info, TbArea area, String tagIds, Page pageInfo) {
        List<CruiseShip> infos = new ArrayList<CruiseShip>();
        Criteria<CruiseShip> criteria = new Criteria<CruiseShip>(CruiseShip.class);
//		String hql = "from CruiseShip h INNER JOIN Product p on p_.productId = h_.id ";
        if (info != null && StringUtils.isNotBlank(info.getName())) {
            criteria.like("name", "%" + info.getName() + "%");
        }
        criteria.eq("status", ProductStatus.UP);
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                Long[] aIds = new Long[areas.size()];
                for (int i = 0; i < areas.size(); i++) {
                    aIds[i] = areas.get(i).getId();
                }
                criteria.in("cityId", aIds);
            } else if (area.getLevel() == 2) {
                criteria.eq("cityId", area.getId());
            }

        }
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
//			criteria.createCriteria("product",JoinType.INNER_JOIN);
            criteria.in("id", ids);
            infos = cruiseShipDao.findByCriteria(criteria, pageInfo);
//			cruiseShipDao.findByHQL(hqlstr, page);
        } else if (tagIds == null) {
            infos = cruiseShipDao.findByCriteria(criteria, pageInfo);
        }
        return infos;
    }

    public List<CruiseShip> getCruiseShipListIndexData(CruiseShip cruiseShip, Page page, String... orderProperties) {

        /*SELECT
        p.id as id,
                p.name as name,
        tba.name as startCity,
                ca.name as lineName,
        min(csd.date) as date,
        min(csd.minDiscountPrice) AS price,
        avg(cosr.score)	as score
        FROM
        product p
        LEFT JOIN cruise_ship_date csd ON csd.cruiseShipId = p.id
        LEFT JOIN cruise_ship cs on cs.productId = p.id
        LEFT JOIN tb_area tba on tba.id = cs.startCityId
        LEFT JOIN category ca on ca.id = cs.route
        LEFT JOIN comment co on co.targetId = p.id
        LEFT JOIN comment_score cosr on cosr.commentId = co.id
        WHERE
        p. STATUS = 'UP'
        AND p.proType = 'cruiseship'
        AND csd.date > '2017-01-09 00:00:00'
        GROUP BY
        p.id;*/

        List params = Lists.newArrayList();

        StringBuffer sql = new StringBuffer("SELECT");
        sql.append(" p.id as id,").append(" p.name as name,").append(" tba.name as startCity,")
                .append(" ca.name as routeName,").append(" csd.date as startDate,")
                .append(" csd.minSalePrice AS price,").append(" cs.coverImage as coverImage,")
                .append(" csd.id as dateId,").append(" avg(cosr.score) as score");

        sql.append(" FROM cruise_ship_date csd");
        sql.append(" LEFT JOIN product p ON csd.cruiseShipId = p.id");
        sql.append(" LEFT JOIN cruise_ship cs on cs.productId = csd.cruiseShipId");
        sql.append(" LEFT JOIN tb_area tba on tba.id = cs.startCityId");
        sql.append(" LEFT JOIN category ca on ca.id = cs.route");
        sql.append(" LEFT JOIN comment co on co.targetId = csd.id");
        sql.append(" LEFT JOIN comment_score cosr on cosr.commentId = co.id");

        sql.append(" WHERE");
        sql.append(" p. STATUS = ?");
        params.add(ProductStatus.UP.toString());
        sql.append(" AND p.proType = ?");
        params.add(ProductType.cruiseship.toString());

        if (cruiseShip != null && cruiseShip.getStartDateRanges() != null
                && cruiseShip.getStartDateRanges().length > 0
                && cruiseShip.getStartDateRanges().length == 1) {
            sql.append(" AND csd.date > ?");
            params.add(cruiseShip.getStartDateRanges()[0]);
        } else if (cruiseShip != null && cruiseShip.getStartDateRanges() != null
                && cruiseShip.getStartDateRanges().length > 0
                && cruiseShip.getStartDateRanges().length == 2) {
            sql.append(" AND (csd.date > ? OR csd.date >= ?)");
            params.add(cruiseShip.getStartDateRanges()[0]);
            params.add(cruiseShip.getStartDateRanges()[1]);
        }
        sql.append(" GROUP BY csd.id");
        if (orderProperties != null && orderProperties.length == 1) {
            sql.append(" ORDER BY " + orderProperties[0] + " asc");
        } else if (orderProperties != null && orderProperties.length == 2) {
            sql.append(" ORDER BY " + orderProperties[0] + " " + orderProperties[1] + "");
        }


        List<Map<String, Object>> mapList = cruiseShipDao.findEntitiesBySQL4(sql.toString(), page, params.toArray());
        List<CruiseShip> cruiseShipList = Lists.transform(mapList, new Function<Map<String, Object>, CruiseShip>() {
            @Override
            public CruiseShip apply(Map<String, Object> map) {
                try {
                    return (CruiseShip) com.zuipin.util.MapUtils.mapToObject(map, CruiseShip.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new CruiseShip();
            }
        });
        return cruiseShipList;
    }

    public List<CruiseShip> getInIdRange(Long startId, Long endId, int size) {
        StringBuilder hql = new StringBuilder("from CruiseShip where id>:startId");
        Map<String, Object> data = Maps.newHashMap();
        data.put("startId", startId);
        if (endId != null) {
            hql.append(" and id<=:endId");
            data.put("endId", endId);
        }
        hql.append(" and status=:status");
        data.put("status", ProductStatus.UP);

        return cruiseShipDao.findByHQL2(hql.toString(), new Page(1, size), data);
    }

    public List<CruiseShip> findCruiseshipList(CruiseShip cruiseShip, Page page) {
        Criteria<CruiseShip> criteria = new Criteria<CruiseShip>(CruiseShip.class);
        createCriteria(cruiseShip, criteria);
        criteria.ne("status", ProductStatus.DEL);
        if (page == null) {
            return cruiseShipDao.findByCriteria(criteria);
        }
        return cruiseShipDao.findByCriteria(criteria, page);
    }
}
