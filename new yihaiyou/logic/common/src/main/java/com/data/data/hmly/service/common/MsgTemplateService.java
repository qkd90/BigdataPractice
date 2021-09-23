package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.MsgTemplateDao;
import com.data.data.hmly.service.common.entity.MsgTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/12/19.
 */
@Service
public class MsgTemplateService {

    @Resource
    private MsgTemplateDao msgTemplateDao;

    public MsgTemplate get(Long id) {
        return msgTemplateDao.load(id);
    }

    public void save(MsgTemplate msgTemplate) {
        msgTemplateDao.save(msgTemplate);
    }

    public void delete(MsgTemplate msgTemplate) {
        msgTemplateDao.delete(msgTemplate);
    }

    public void delete(Long id) {
        msgTemplateDao.delete(id, MsgTemplate.class);
    }

    public void update(MsgTemplate msgTemplate) {
        msgTemplateDao.update(msgTemplate);
    }

    public MsgTemplate findByKey(String templateKey) {
        Criteria<MsgTemplate> criteria = new Criteria<MsgTemplate>(MsgTemplate.class);
        criteria.eq("templateKey", templateKey);
        return msgTemplateDao.findUniqueByCriteria(criteria);
    }

    public List<MsgTemplate> getMsgTemplateList(MsgTemplate condition, Page page, String...orderProperties) {
        Criteria<MsgTemplate> criteria = createCriteria(condition, orderProperties);
        if (page != null) {
            return msgTemplateDao.findByCriteria(criteria, page);
        }
        return msgTemplateDao.findByCriteria(criteria);
    }

    public Criteria<MsgTemplate> createCriteria(MsgTemplate condition, String...orderProperties) {
        Criteria<MsgTemplate> criteria = new Criteria<MsgTemplate>(MsgTemplate.class);
        if (orderProperties != null) {
            if (orderProperties.length > 1 && orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && orderProperties[0] != null) {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (condition == null) {
            return criteria;
        }
        if (StringUtils.hasText(condition.getTemplateKey())) {
            criteria.eq("templateKey", condition.getTemplateKey());
        }
        if (StringUtils.hasText(condition.getTitle())) {
            criteria.like("title", condition.getTitle(), MatchMode.ANYWHERE);
        }
        if (StringUtils.hasText("content")) {
            criteria.like("content", condition.getContent(), MatchMode.ANYWHERE);
        }
        if (condition.getType() != null) {
            criteria.eq("type", condition.getType());
        }
        if (StringUtils.hasText(condition.getSearchContent())) {
            criteria.or(Restrictions.like("title", condition.getSearchContent(), MatchMode.ANYWHERE),
                    Restrictions.like("content", condition.getSearchContent(), MatchMode.ANYWHERE));
        }
        return criteria;
    }
}
