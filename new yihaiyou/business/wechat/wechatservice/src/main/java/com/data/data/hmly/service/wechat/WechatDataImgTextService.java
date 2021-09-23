package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.wechat.dao.WechatDataImgTextDao;
import com.data.data.hmly.service.wechat.dao.WechatDataItemDao;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WechatDataImgTextService {
    @Resource
    private WechatDataImgTextDao wechatDataImgTextDao;
    @Resource
    private WechatDataItemDao itemDao;

    /**
     * 查询列表
     *
     * @param wechatDataText
     * @return
     * @author caiys
     * @date 2015年11月25日 下午4:56:00
     */
    public List<WechatDataNews> findBy(WechatDataNews wechatDataImgText, Integer limit) {
        return wechatDataImgTextDao.findBy(wechatDataImgText, limit);
    }

    public void save(WechatDataNews dataNews) {
        wechatDataImgTextDao.save(dataNews);

    }

    public WechatDataNews findNewsById(long id) {
        return wechatDataImgTextDao.load(id);
    }

    public void update(WechatDataNews wechatDataNews) {
        wechatDataImgTextDao.update(wechatDataNews);
    }

    public List<WechatDataNews> findList(SysUser user, String keyword, Page page) {

        Criteria<WechatDataNews> criteria = new Criteria<WechatDataNews>(WechatDataNews.class);


        formatter(criteria, keyword);
        criteria.eq("user", user);


        return wechatDataImgTextDao.findByCriteria(criteria, page);
    }

    private void formatter(Criteria<WechatDataNews> criteria, String keyword) {
        if (!StringUtils.isEmpty(keyword)) {
            criteria.like("title", "%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.like("author", "%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.like("abstractText", "%" + keyword + "%");
        }
    }

    public List<WechatDataNews> findNewsListByItem(WechatDataItem item) {

        Criteria<WechatDataNews> criteria = new Criteria<WechatDataNews>(WechatDataNews.class);

        criteria.eq("dataItem", item);
        criteria.orderBy("id", "ASC");

        return wechatDataImgTextDao.findByCriteria(criteria);
    }

    public void delNewsByItem(WechatDataItem dataItem) {

        List<WechatDataNews> dataNews = findNewsListByItem(dataItem);
        for (WechatDataNews news : dataNews) {
            wechatDataImgTextDao.delete(news);
        }

    }

    public void delNewsById(long id) {
        WechatDataNews news = wechatDataImgTextDao.load(id);
        wechatDataImgTextDao.delete(news);
    }

    public List<WechatDataNews> findListBykeyword(String keyword,
                                                  SysUser sysUser) {

        Criteria<WechatDataNews> criteria = new Criteria<WechatDataNews>(WechatDataNews.class);
        if (!StringUtils.isEmpty(keyword)) {
            criteria.like("title", "%" + keyword + "%");
        }
        if (sysUser != null) {
            criteria.eq("user", sysUser);
        }


        return wechatDataImgTextDao.findByCriteria(criteria);
    }

    public List<WechatDataNews> findByCategory(Category category) {
        Criteria<WechatDataNews> criteria = new Criteria<WechatDataNews>(WechatDataNews.class);
        criteria.eq("category", category);
        return wechatDataImgTextDao.findByCriteria(criteria);
    }

    public List<WechatDataNews> findByItemId(long itemId) {
        return wechatDataImgTextDao.findByItemId(itemId);
    }
}
