package com.data.data.hmly.service.scenic;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.ScenicAreaService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.scenic.dao.ScenicInfoDao;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicCommentVo;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.data.hmly.service.scenic.vo.SolrDis;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.data.data.hmly.util.Clock;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
public class ScenicInfoService extends SolrIndexService<ScenicInfo, ScenicSolrEntity> {

    Logger logger = Logger.getLogger(ScenicInfoService.class);

    @Resource
    private ScenicInfoDao scenicInfoDao;
    @Resource
    private LabelService labelService;
    @Resource
    private ScenicAreaService scenicAreaService;

    @Resource
    private TicketService ticketService;

    @Resource
    private TicketPriceService ticketPriceService;

    @Resource
    private TbAreaDao areaDao;

    @Resource
    private MemberService memberService;

    public List<ScenicInfo> listAll() {
        return scenicInfoDao.findAll();
    }

    public ScenicInfo get(Long id) {
        return scenicInfoDao.get(id);
    }

    @Resource
    private MulticoreSolrTemplate solrTemplate;

    public Integer getStatus(Long scenicId) {
        String sql = "select status as result from scenic where id=?";
        BigDecimal status = scenicInfoDao.findIntegerBySQL(sql, scenicId);
        return status == null ? 0 : status.intValue();
    }

    /**
     * @param keyword
     * @return
     */
    public List<ScenicInfo> listByKeyword(String keyword, ScenicInfoType scenicType, Page page) {
        return scenicInfoDao.list(keyword, scenicType, page);
    }

    public List<ScenicInfo> list(ScenicInfo scenicInfo, Page page, String... orderProperties) {
        Criteria<ScenicInfo> criteria = createCriteria(scenicInfo, orderProperties);
        if (page != null) {
            return scenicInfoDao.findByCriteria(criteria, page);
        }
        return scenicInfoDao.findByCriteria(criteria);
    }

    /**
     * 获取游记中经过的最热门前3景点
     * 备份方法, 请勿调用
     *
     * @param scenicIds
     * @return
     */
    @Deprecated
    public String getBestPassScenics(Set<Long> scenicIds) {
        String passScenics = "";
        List<ScenicInfo> scenicInfos = new ArrayList<ScenicInfo>();
        Iterator<Long> iterator = scenicIds.iterator();
        while (iterator.hasNext()) {
            String hql = "select new ScenicInfo(id,name,score) from ScenicInfo where id=?";
            ScenicInfo scenicInfo = scenicInfoDao.findByHQLWithUniqueObject(hql, iterator.next());
            if (scenicInfo != null) {
                scenicInfos.add(scenicInfo);
            }
        }
        // 按评分和排名进行综合排序
        Collections.sort(scenicInfos, new Comparator<ScenicInfo>() {
            @Override
            public int compare(ScenicInfo o1, ScenicInfo o2) {
                Integer weightA = (o1.getScore() != null ? o1.getScore() : 0) - (o1.getRanking() != null ? o1.getRanking() : 9999);
                Integer weightB = (o2.getScore() != null ? o2.getScore() : 0) - (o2.getRanking() != null ? o2.getRanking() : 9999);
                return weightA - weightB;
            }
        });
        if (scenicInfos.size() > 3) {
            for (int i = 0; i < 3; i++) {
                passScenics += scenicInfos.get(i).getName() + "-";
            }
        } else if (!scenicInfos.isEmpty() && scenicInfos.size() <= 3) {
            Iterator<ScenicInfo> scenicInfoIterator = scenicInfos.iterator();
            while (scenicInfoIterator.hasNext()) {
                passScenics += scenicInfoIterator.next().getName() + "-";
            }
        }
        if (passScenics.length() > 0) {
            passScenics = passScenics.substring(0, passScenics.length() - 1);
        }
        return passScenics;
    }

    public Integer getScenicScore(Long scenicId) {
        String hql = "select new ScenicInfo(id, score) from ScenicInfo where id=?";
        ScenicInfo scenicInfo = scenicInfoDao.findByHQLWithUniqueObject(hql, scenicId);
        if (scenicInfo == null || scenicInfo.getScore() <= 0) {
            return 100;
        }
        return scenicInfo.getScore();
    }

    public List<ScenicInfo> getSeasonRecommendScenic() {
        Label label = new Label();
        label.setName("景点当季精选");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        ScenicInfo scenicInfo = new ScenicInfo();
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        scenicInfo.setLabelItems(list);
        return list(scenicInfo, null);
    }

    public List<ScenicInfo> getRecommendScenic() {
        Label label = new Label();
        label.setName("热门景点");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        ScenicInfo scenicInfo = new ScenicInfo();
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        scenicInfo.setLabelItems(list);
        return list(scenicInfo, null);
    }

    public List<ScenicInfo> getRecommendScenicWithImg() {
        Label label = new Label();
        label.setName("热门景点带图片");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        ScenicInfo scenicInfo = new ScenicInfo();
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        scenicInfo.setLabelItems(list);
        return list(scenicInfo, null);
    }

    public List<ScenicInfo> getTop10Scenic(Long cityId) {
        Label label = new Label();
        label.setName("top10");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        TbArea city = new TbArea();
        city.setId(cityId / 100);
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setCity(city);
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        scenicInfo.setLabelItems(list);
        return list(scenicInfo, null);
    }

    /**
     * 通过任意标签名称查询景点列表
     * @param labelName
     * @param page
     * @return
     */
    public List<ScenicInfo> getScenicInfoByLabel(String labelName, Page page) {
        Label label = new Label();
        label.setName(labelName);
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> labelItems = new HashSet<LabelItem>();
        labelItems.add(labelItem);
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setLabelItems(labelItems);
        return this.list(scenicInfo, page);
    }

    /**
     * 首页-门票-推荐景点
     *
     * @return
     * @author caiys
     * @date 2015年12月31日 下午1:56:23
     */
    public List<ScenicInfo> getHomeRecommendScenic() {
        Label label = new Label();
        label.setName("首页推荐景点");
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        ScenicInfo scenicInfo = new ScenicInfo();
        Set<LabelItem> list = new HashSet<LabelItem>();
        list.add(labelItem);
        scenicInfo.setLabelItems(list);
        return list(scenicInfo, new Page(1, 6));
    }

    public List<String> getScenicTheme() {
        Label parent = new Label();
        parent.setName("公共标签_景点主题");
        parent = labelService.list(parent, null).get(0);
        Label label = new Label();
        label.setParent(parent);
        label.setStatus(LabelStatus.USE);
        List<Label> labels = labelService.list(label, null);
        List<String> scenicTheme = Lists.transform(labels, new Function<Label, String>() {
            @Override
            public String apply(Label input) {
                return input.getName();
            }
        });
        return scenicTheme;
    }

    public void indexScenicInfoAll(ScenicInfo scenicInfo) {
        Clock clock = new Clock();

        int pageNo = 1;
        int pageSize = 100;
        int processed = 0;
        int total;
        //ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setStatus(1);
        while (true) {
            Page page = new Page(pageNo, pageSize);
            List<ScenicInfo> list = list(scenicInfo, page);
            processed += list.size();
            total = page.getTotalCount();
            if (list.isEmpty()) {
                break;
            }
            List<ScenicSolrEntity> entities = Lists.transform(list, new Function<ScenicInfo, ScenicSolrEntity>() {
                @Override
                public ScenicSolrEntity apply(ScenicInfo scenicInfo) {
                    System.out.println("scenicId: " + scenicInfo.getId());
                    ScenicSolrEntity entity = new ScenicSolrEntity(scenicInfo);
                    entity.setTicketNum(ticketService.findTicketNumBy(scenicInfo.getId()));
                    entity.setPrice(ticketPriceService.findMinPriceByScenic(scenicInfo.getId(), new Date()));
                    return entity;
                }
            });
            index(entities);
            logger.info("save 100 record cost " + clock.elapseTime() + "ms");
            if (processed >= total) {
                break;
            }
            pageNo += 1;
            list.clear();
            entities.clear();
        }
        logger.info("finish in " + clock.totalTime() + "ms with " + processed);
    }

    public void indexScenicInfo(ScenicInfo scenicInfo) {
        try {
            if (scenicInfo.getStatus() != 1) {
                // 删除状态不为正常的景点索引
                UpdateResponse updateResponse = deleteIndexByEntityId(scenicInfo.getId(), SolrType.scenic_info);
                if (updateResponse.getStatus() != 0) {
                    logger.error("发生错误: scenic_info#" + scenicInfo.getId() + "删除索引失败!");
                } else {
                    logger.info("操作成功: scenic_info#" + scenicInfo.getId() + "删除索引成功!");
                }
            } else {

                Float minPrice = ticketPriceService.findMinPriceByScenic(scenicInfo.getId(), new Date());
                Integer ticketCount = ticketService.findTicketNumBy(scenicInfo.getId());
                scenicInfo.setTicketNum(ticketCount);
                scenicInfo.setPrice(minPrice);
                scenicInfoDao.update(scenicInfo);
                ScenicSolrEntity entity = new ScenicSolrEntity(scenicInfo);
                List<ScenicSolrEntity> entities = Lists.newArrayList(entity);
                index(entities);
            }
        } catch (Exception e) {
            logger.error("未知异常，scenic_info#" + scenicInfo.getId() + "索引失败", e);
        }

    }

    private Criteria<ScenicInfo> createCriteria(ScenicInfo scenicInfo, String... orderProperties) {
        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);

        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (scenicInfo.getId() != null && scenicInfo.getId() > 0) {
            criteria.eq("id", scenicInfo.getId());
        }
        if (StringUtils.isNotBlank(scenicInfo.getName())) {
            criteria.like("name", scenicInfo.getName());
        }
        if (StringUtils.isNotBlank(scenicInfo.getLevel())) {
            criteria.eq("level", scenicInfo.getLevel());
        }
        if (scenicInfo.getStar() != null) {
            criteria.eq("star", scenicInfo.getStar());
        }
        if (scenicInfo.getFather() != null) {
            criteria.eq("father.id", scenicInfo.getFather().getId());
        }
        if (scenicInfo.getStatus() != null) {
            criteria.eq("status", scenicInfo.getStatus());
        }
        if (scenicInfo.getCity() != null) {
            if (String.valueOf(scenicInfo.getCity().getId()).length() == 3) {
                criteria.between("city.id", scenicInfo.getCity().getId() * 1000, scenicInfo.getCity().getId() * 1000 + 999);
            } else if (String.valueOf(scenicInfo.getCity().getId()).length() == 4) {
                criteria.between("city.id", scenicInfo.getCity().getId() * 100, scenicInfo.getCity().getId() * 100 + 99);
            } else {
                criteria.eq("city.id", scenicInfo.getCity().getId());
            }
        }
        if (scenicInfo.getIsShow() != null) {
            criteria.eq("isShow", scenicInfo.getIsShow());
        }
        if (scenicInfo.getHasBus() != null) {
            criteria.eq("hasBus", scenicInfo.getHasBus());
        }
        if (scenicInfo.getHasTaxi() != null) {
            criteria.eq("hasTaxi", scenicInfo.getHasTaxi());
        }
        if (scenicInfo.getCreatedBy() != null) {
            criteria.eq("createdBy.id", scenicInfo.getCreatedBy().getId());
        }
        if (scenicInfo.getModifiedBy() != null) {
            criteria.eq("modifiedBy.id", scenicInfo.getModifiedBy().getId());
        }

        if (scenicInfo.getStartId() != null) {
            criteria.ge("id", scenicInfo.getStartId());
        }
        if (scenicInfo.getEndId() != null) {
            criteria.le("id", scenicInfo.getEndId());
        }

        if (scenicInfo.getScenicType() != null) {
            criteria.le("scenicType", scenicInfo.getScenicType());
        }

        if (scenicInfo.getLabelItems() != null && !scenicInfo.getLabelItems().isEmpty()) {
            DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
            for (LabelItem labelItem : scenicInfo.getLabelItems()) {
                criterion.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
            }
            criterion.addOrder(Order.asc("order"));
        }
        return criteria;
    }

    public List<ScenicInfo> getScenicLabels(ScenicInfo info, TbArea area, String tagIds, Page pageInfo) {

        List<ScenicInfo> infos = new ArrayList<ScenicInfo>();

        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);

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
//                criteria.eq("city", area);
            }
        }
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
            criteria.in("id", ids);
            infos = scenicInfoDao.findByCriteria(criteria, pageInfo);
        } else if (tagIds == null) {

            infos = scenicInfoDao.findByCriteria(criteria, pageInfo);
        }
        return infos;
    }

//	public List<ScenicInfo> getTopSceByDestination( List<TbArea> tbAreas, Page page) {
//		
//		Label label = new Label();
//		label.setName("必游景点排行榜-目的地");
//		
//		return this.getTopSceByDestination(label, tbAreas, page);
//	}

    /**
     * 通过某个表签查找目的地下的景点排行榜
     *
     * @param page
     * @return
     */
    public List<ScenicInfo> getTopSceByDestination(List<TbArea> tbAreas, Page page) {

        ScenicInfo sceCodition = new ScenicInfo();
//		List<Label> labels = labelService.list(label, null);
//		LabelItem labelItem = new LabelItem();
//		labelItem.setLabel(labels.get(0));
//		Set<LabelItem> labelItems = new HashSet<LabelItem>();
//		labelItems.add(labelItem);
//		sceCodition.setLabelItems(labelItems);
        return scenicInfoDao.getTopSceByDestination(sceCodition, tbAreas, page);
    }

    public List<ScenicInfo> getScenicByIds(List<Long> ids) {
        String hql = "from ScenicInfo where id in (:ids)";
        Map<String, Object> data = Maps.newHashMap();
        data.put("ids", ids);
        return scenicInfoDao.findByHQL2(hql, data);
    }

    public ScenicInfo addCollect(Long id) {
        ScenicInfo scenicInfo = get(id);
        Integer collectNum = scenicInfo.getScenicOther().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        scenicInfo.getScenicOther().setCollectNum(collectNum);
        scenicInfoDao.update(scenicInfo);
        return scenicInfo;
    }

    public Integer deleteCollect(Long id) {
        ScenicInfo scenicInfo = get(id);
        Integer collectNum = scenicInfo.getScenicOther().getCollectNum();
        if (collectNum == null || collectNum == 0) {
            return 0;
        }
        collectNum--;
        scenicInfo.getScenicOther().setCollectNum(collectNum);
        scenicInfoDao.update(scenicInfo);
        return collectNum;
    }

    public Integer getCollectNum(Long id) {
        ScenicInfo scenicInfo = get(id);
        Integer collectNum = scenicInfo.getScenicOther().getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        return collectNum;
    }

    public List<ScenicInfo> getInIdRange(Long startId, Long endId, int size) {
        StringBuilder hql = new StringBuilder("from ScenicInfo where id>:startId");
        Map<String, Object> data = Maps.newHashMap();
        data.put("startId", startId);
        if (endId != null) {
            hql.append(" and id<=:endId");
            data.put("endId", endId);
        }
        hql.append(" and status=:status");
        data.put("status", 1);
        return scenicInfoDao.findByHQL2(hql.toString(), new Page(1, size), data);
    }

    public void save(ScenicInfo scenicInfo) {
        scenicInfoDao.save(scenicInfo);
    }

    public void update(ScenicInfo scenicInfo) {
        scenicInfoDao.update(scenicInfo);
    }

    public ScenicInfo findNeaerScenic(final ScenicInfo from, List<ScenicInfo> scenicInfos) {
        SimpleQuery query = new SimpleQuery();
        org.springframework.data.solr.core.query.Criteria eidC = new org.springframework.data.solr.core.query.Criteria("e_id");
        List<Long> ids = Lists.transform(scenicInfos, new Function<ScenicInfo, Long>() {
            @Override
            public Long apply(ScenicInfo scenicInfo) {
                return scenicInfo.getId();
            }
        });
        eidC.in(ids);
        query.addCriteria(eidC);
        org.springframework.data.solr.core.query.Criteria sidC = new org.springframework.data.solr.core.query.Criteria("s_id");
        sidC.is(from.getId());
        query.addCriteria(sidC);
        Sort sort = new Sort(Sort.Direction.ASC, "endCost");
        query.addSort(sort);
        SolrDis dis = solrTemplate.queryForObject(query, SolrDis.class);
        if (dis != null) {
            return scenicInfoDao.load(dis.geteId());
        } else {
            final TreeMap<Double, ScenicInfo> maps = new TreeMap<Double, ScenicInfo>();
            for (ScenicInfo scenicInfo : scenicInfos) {
                maps.put(countDis(from, scenicInfo), scenicInfo);
            }
            return maps.firstEntry().getValue();
        }
    }

    private double countDis(ScenicInfo from, ScenicInfo to) {
        // TODO Auto-generated method stub
        double xpow = Math.pow(from.getScenicGeoinfo().getBaiduLat() - to.getScenicGeoinfo().getBaiduLat(), 2);
        double ypow = Math.pow(from.getScenicGeoinfo().getBaiduLng() - to.getScenicGeoinfo().getBaiduLng(), 2);
        return Math.sqrt(xpow + ypow);
    }

    public List<ScenicSolrEntity> findNearByScenic(Double longitude, Double latitude, Float distance, Page page) {
        SolrQuery query = new SolrQuery("type:" + SolrType.scenic_info);
        query.setStart(page.getFirstResult());
        query.setRows(page.getPageSize());
        QueryResponse response = solrTemplate.nearBy(query, latitude + "", longitude + "", ScenicSolrEntity.class, SolrQuery.ORDER.asc, distance);
        page.setTotalCount(Long.valueOf(response.getResults().getNumFound()).intValue());
        return solrTemplate.convertQueryResponseToBeans(response, ScenicSolrEntity.class);
    }

    public List<ScenicSolrEntity> findNearByScenic(ScenicSearchRequest request, Double lng, Double lat, Float distance, Page page) {
        SolrQuery query = new SolrQuery();
        query.setQuery(request.getSolrQueryStr());
        if (request.getOrderColumn() != null && request.getOrderType() != null) {
            query.setSort(request.getOrderColumn(), request.getOrderType());
        }
        query.setStart(page.getFirstResult());
        query.setRows(page.getPageSize());
        QueryResponse response = solrTemplate.nearBy(query, lat + "", lng + "", ScenicSolrEntity.class, SolrQuery.ORDER.asc, distance);
        page.setTotalCount(Long.valueOf(response.getResults().getNumFound()).intValue());
        return solrTemplate.convertQueryResponseToBeans(response, ScenicSolrEntity.class);
    }

    public List<ScenicSolrEntity> listScenicFromSolr(List<Long> ids) {
        SolrQuery query = new SolrQuery();
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ;i < ids.size();i++){
            if(sb.length() > 0){
                sb.append(" OR ");
            }
            sb.append("id:").append(ids.get(i));
        }
        query.setStart(0);
        query.setRows(ids.size());
        query.setQuery(String.format("type:%s",SolrType.scenic_info.name()));
        query.setFilterQueries(sb.toString());
        QueryResponse response = solrTemplate.query(query,"products");
        return solrTemplate.convertQueryResponseToBeans(response, ScenicSolrEntity.class);
    }

    public List<ScenicInfo> findByLabel(Label label, Page page) {
        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);
        DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
        criterion.add(Restrictions.eq("label.id", label.getId()));
        criterion.addOrder(Order.asc("order"));
        if (page == null) {
            return scenicInfoDao.findByCriteria(criteria);
        } else {
            return scenicInfoDao.findByCriteria(criteria, page);
        }
    }

    public List<ScenicInfo> findIndexScenic(Page page) {
        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);
        criteria.gt("price", 0f);
        criteria.eq("status", 1);
        criteria.orderBy(Order.asc("ranking"));
        if (page == null) {
            return scenicInfoDao.findByCriteria(criteria);
        } else {
            return scenicInfoDao.findByCriteria(criteria, page);
        }
    }

    public void addComment(Long id, Long ticketId, Integer score) {
        if (score == null || id == null) {
            return;
        }
        ScenicInfo scenicInfo = get(id);
        if (scenicInfo == null) {
            return;
        }
        ScenicOther scenicOther = scenicInfo.getScenicOther();
        if (scenicOther == null) {
            return;
        }
        Integer num = scenicOther.getCommentNum();
        if (num == null) {
            num = 0;
        }
        Integer oldScore = scenicInfo.getScore();
        if (oldScore == null) {
            oldScore = 0;
        }
        Integer newScore = (oldScore * num + score) / (num + 1);
        scenicInfo.setScore(newScore);
        num++;
        scenicOther.setCommentNum(num);
        update(scenicInfo);
        Ticket ticket = ticketService.loadTicket(ticketId);
        if (ticket.getTicketType().equals(TicketType.scenic)) {
            indexScenicInfo(scenicInfo);
        } else {
            ticketService.indexTicket(ticket);
        }
    }

    public List<ScenicInfo> getScenicListIndexData(ScenicInfo qryScenic, Page page, String... orderProperties) {
        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);

        criteria.eq("status", 1);
        if (qryScenic.getCityIdLong() != null) {
            String cityCode = qryScenic.getCityIdLong().toString();
            if (cityCode.length() == 4) {
                criteria.ge("city.id", Long.parseLong(cityCode + 00));
                criteria.le("city.id", Long.parseLong(cityCode + 99));
            } else if (cityCode.length() == 2) {
                criteria.ge("city.id", Long.parseLong(cityCode + 0000));
                criteria.le("city.id", Long.parseLong(cityCode + 9999));
            } else {
                criteria.eq("city.id", qryScenic.getCityIdLong());
            }
        }
        if (orderProperties != null && orderProperties.length > 0 && orderProperties.length == 1) {
            criteria.orderBy(orderProperties[0], "asc");
        } else if (orderProperties != null && orderProperties.length > 0 && orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        }
        return scenicInfoDao.findByCriteria(criteria, page);
    }
}
