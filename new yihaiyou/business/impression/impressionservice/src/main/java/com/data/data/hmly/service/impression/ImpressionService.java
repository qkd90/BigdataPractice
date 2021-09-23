package com.data.data.hmly.service.impression;

import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.impression.dao.ImpressionDao;
import com.data.data.hmly.service.impression.entity.Impression;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-19,0019.
 */
@Service
public class ImpressionService {
    @Resource
    private ImpressionDao impressionDao;
    @Resource
    private SolrIndexService solrIndexService;

    public void save(Impression impression) {
        impressionDao.saveOrUpdate(impression, impression.getId());
    }

    public Impression get(Long id) {
        return impressionDao.load(id);
    }

    public void delete(Impression impression) {
        impression.setDeleteFlag(1);
        impressionDao.update(impression);
    }

    public List<Impression> listMyImpression(Impression impression, Page page) {
        Criteria<Impression> criteria = new Criteria<Impression>(Impression.class);
        criteria.eq("user.id", impression.getUser().getId());
        criteria.eq("type", 1);
        criteria.eq("deleteFlag", 0);
        criteria.orderBy(Order.desc("createTime"));
        return impressionDao.findByCriteria(criteria, page);
    }

    public List<Impression> list(Page page) {
        Criteria<Impression> criteria = new Criteria<Impression>(Impression.class);
        criteria.eq("type", 1);
        criteria.eq("deleteFlag", 0);
        criteria.orderBy(Order.desc("createTime"));
        return impressionDao.findByCriteria(criteria, page);
    }

    public List<SuggestionEntity> listPlace(String name, Page page) {
        try {
            String keywordToken = IKTokenUtils.token(name);
            StringBuilder sb = new StringBuilder();
            String[] arr = keywordToken.split(" +");
            sb.append("(");
            for (String s : arr) {
                sb.append("name:").append(s).append("*").append(" OR ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(")").append(" AND (type:").append(SolrType.scenic_info).append(" OR type:").append(SolrType.hotel).append(" OR type:").append(SolrType.restaurant).append(")");
            return solrIndexService.listPlace(sb.toString(), page);
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public Impression addCollect(Long id) {
        Impression impression = get(id);
        Integer collectNum = impression.getCollectNum();
        if (collectNum == null) {
            collectNum = 0;
        }
        collectNum++;
        impression.setCollectNum(collectNum);
        impressionDao.update(impression);
        return impression;
    }

    public Integer deleteCollect(Long id) {
        Impression impression = get(id);
        Integer collectNum = impression.getCollectNum();
        if (collectNum == null || collectNum == 0) {
            collectNum = 0;
        }
        collectNum++;
        impression.setCollectNum(collectNum);
        impressionDao.update(impression);
        return collectNum;
    }

    public void addBrowsingNum(Long id) {
        Impression impression = get(id);
        Integer browsingNum = impression.getBrowsingNum();
        if (browsingNum == null) {
            browsingNum = 0;
        }
        browsingNum++;
        impression.setBrowsingNum(browsingNum);
        impressionDao.update(impression);
    }

    public Integer getBrowsingNum(Long id) {
        Impression impression = get(id);
        Integer browsingNum = impression.getBrowsingNum();
        if (browsingNum == null) {
            browsingNum = 0;
        }
        return browsingNum;
    }
}
