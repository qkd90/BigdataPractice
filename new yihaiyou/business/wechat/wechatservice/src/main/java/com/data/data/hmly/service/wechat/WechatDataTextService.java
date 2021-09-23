package com.data.data.hmly.service.wechat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;


import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.dao.WechatDataItemDao;
import com.data.data.hmly.service.wechat.dao.WechatDataTextDao;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataText;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.gson.inf.MsgTypes;

@Service
public class WechatDataTextService {
    @Resource
    private WechatDataTextDao textDao;
    @Resource
    private WechatDataItemDao itemDao;

    public void saveOrUpdate(WechatDataText wechatDataText, SysUser sysUser, SysUnit sysUnit, MsgTypes text) {

        if (wechatDataText.getId() != null) {

            WechatDataText dataText = textDao.load(wechatDataText.getId());

            WechatDataItem dataItem = itemDao.load(dataText.getDataItem().getId());

            dataItem.setCompany(sysUnit);
            dataItem.setUpdateTime(new Date());
            itemDao.update(dataItem);

            dataText.setTitle(wechatDataText.getTitle());
            dataText.setContent(wechatDataText.getContent());
            dataText.setCategory(wechatDataText.getCategory());
            dataText.setUpdateTime(new Date());
            dataText.setUser(sysUser);
            dataText.setDataItem(dataItem);
            textDao.update(dataText);
        } else {
            WechatDataItem dataItem = new WechatDataItem();
            dataItem.setCompany(sysUnit);
            dataItem.setCreateTime(new Date());
            dataItem.setType(text);
            dataItem.setUpdateTime(new Date());
            itemDao.save(dataItem);
            wechatDataText.setDataItem(dataItem);
            wechatDataText.setUpdateTime(new Date());
            wechatDataText.setCreateTime(new Date());
            wechatDataText.setUser(sysUser);
            textDao.save(wechatDataText);
        }

    }

    public List<WechatDataText> getTextList(MsgTypes type, String keyword,
                                            SysUnit companyUnit, SysUser loginUser, Page page) {

        List<WechatDataItem> dataItems = itemDao.findItemList(type, companyUnit);

        Criteria<WechatDataText> criteria = new Criteria<WechatDataText>(WechatDataText.class);

        if (!StringUtils.isEmpty(keyword)) {
            criteria.like("title", "%" + keyword + "%");
        }
        if (loginUser != null) {
            criteria.eq("user", loginUser);
        }
        if (dataItems.size() > 0) {
            criteria.in("dataItem", dataItems);
        }


        return textDao.findByCriteria(criteria, page);
    }

    public WechatDataText getTextById(long id) {
        return textDao.load(id);
    }


    /**
     * 查询列表
     *
     * @param wechatDataText
     * @return
     * @author caiys
     * @date 2015年11月25日 下午4:56:00
     */
    public List<WechatDataText> findBy(WechatDataText wechatDataText) {
        return textDao.findBy(wechatDataText);
    }

    public void delTextById(long id) {

        WechatDataText dataText = getTextById(id);
        textDao.delete(dataText);
        itemDao.delete(dataText.getDataItem());

    }

    public List<WechatDataText> findByItemId(Long itemid) {


        return textDao.findByItemId(itemDao.load(itemid));
    }

    /**
     * 全局管理员获取所有站点和公司的公众号素材信息
     *
     * @param text
     * @param keyword
     * @param pageInfo
     * @return
     */
    public List<WechatDataText> getTextList(MsgTypes type, String keyword,
                                            Page page) {
        List<WechatDataItem> dataItems = itemDao.findItemList(type, null);

        Criteria<WechatDataText> criteria = new Criteria<WechatDataText>(WechatDataText.class);

        if (!StringUtils.isEmpty(keyword)) {
            criteria.like("title", "%" + keyword + "%");
        }
//		if(loginUser != null){
//			criteria.eq("user", loginUser);
//		}
        if (dataItems.size() > 0) {
            criteria.in("dataItem", dataItems);
        }

        criteria.orderBy("createTime", "ASC");

        return textDao.findByCriteria(criteria, page);
    }

    /**
     * 站点管理员过滤文本素材
     *
     * @param type
     * @param sysUnList
     * @param keyword
     * @param pageInfo
     * @return
     */
    public List<WechatDataText> getTextList(MsgTypes type,
                                            List<SysUnit> sysUnList, String keyword, Page pageInfo) {
        List<WechatDataItem> dataItems = itemDao.findItemListByUlist(type, sysUnList);

        Criteria<WechatDataText> criteria = new Criteria<WechatDataText>(WechatDataText.class);

        if (!StringUtils.isEmpty(keyword)) {
            criteria.like("title", "%" + keyword + "%");
        }
//		if(loginUser != null){
//			criteria.eq("user", loginUser);
//		}
        if (dataItems.size() > 0) {
            criteria.in("dataItem", dataItems);
        }
        criteria.orderBy("createTime", "ASC");

        return textDao.findByCriteria(criteria, pageInfo);
    }

    public String findContentByTitle(String title) {
        Criteria<WechatDataText> criteria = new Criteria<WechatDataText>(WechatDataText.class);
        criteria.like("title", title, MatchMode.EXACT);
        List<WechatDataText> dataTextList = textDao.findByCriteria(criteria);
        return dataTextList.get(0).getContent();
    }
}
