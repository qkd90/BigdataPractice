package com.data.data.hmly.service.wechat.dao;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.wechat.entity.EntityData.StatisticsQrcode;
import com.data.data.hmly.service.wechat.entity.WechatQrcode;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.gson.inf.EventTypes;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/11/26.
 */

@Repository
public class WechatQrcodeDao extends DataAccess<WechatQrcode> {

    public WechatQrcode get(Long id) {
        Criteria<WechatQrcode> criteria = new Criteria<WechatQrcode>(WechatQrcode.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }


    public List<WechatQrcode> getList(Long accountId, Page page) {
        Criteria<WechatQrcode> criteria = new Criteria<WechatQrcode>(WechatQrcode.class);
        criteria.eq("wechatAccount.id", accountId);
        criteria.orderBy(Order.desc("createTime"));

        if (page != null) {
            return findByCriteria(criteria, page);
        } else {
            return findByCriteria(criteria);
        }

    }

    public WechatQrcode findQrcodeByAccountAndSceneStr(Long accountId, String sceneStr) {
        Criteria<WechatQrcode> criteria = new Criteria<WechatQrcode>(WechatQrcode.class);
        criteria.eq("wechatAccount.id", accountId);
        criteria.eq("sceneStr", sceneStr);
        criteria.orderBy(Order.desc("createTime"));
        return findUniqueByCriteria(criteria);
    }

    public List<WechatQrcode> findQrcodeList(Long accountId, WechatQrcode qrcode, EventTypes eventType, SysUnit companyUnit, Boolean supperAdmin, Boolean siteAdmin, Page pageInfo) {

/*
        SELECT * FROM WechatQrcode q WHERE EXISTS ( SELECT 1 FROM WechatReceiveMsgLog r WHERE r.eventKey = q.sceneStr
                AND r.createTime >= '2016-05-17 15:44:18'
                AND r.createTime <= '2016-05-17 16:38:18'
        );
 */

        List param = new ArrayList();
        StringBuffer sb = new StringBuffer();
        sb.append("FROM WechatQrcode q WHERE");
        if (!supperAdmin) {
            if (!siteAdmin) {
                sb.append(" q.wechatAccount.companyUnit.id=?");
                param.add(companyUnit.getId());
//                criteria.createCriteria("account.companyUnit", "companyUnit", JoinType.LEFT_OUTER_JOIN);
//                criteria.eq("companyUnit.id", companyUnit.getId());
            } else {
                sb.append(" q.wechatAccount.companyUnit.sysSite.id=?");
                param.add(companyUnit.getSysSite().getId());
//                criteria.createCriteria("account.companyUnit", "companyUnit", JoinType.LEFT_OUTER_JOIN);
//                criteria.createCriteria("companyUnit.sysSite", "sysSite", JoinType.LEFT_OUTER_JOIN);
//                criteria.eq("sysSite.id", companyUnit.getSysSite().getId());
            }
        }
        if (qrcode.getId() != null) {
            sb.append(" and q.id=?");
            param.add(qrcode.getId());
        }
        if (accountId != null) {
            sb.append(" and q.wechatAccount.id=?");
            param.add(accountId);
        }
        sb.append(" and EXISTS (SELECT 1 FROM WechatReceiveMsgLog r WHERE r.eventKey = q.sceneStr");

        if (qrcode.getStartTime() != null) {
            sb.append(" and r.createTime >=?");
            param.add(qrcode.getStartTime());
        }
        if (qrcode.getEndTime() != null) {
            sb.append(" and r.createTime <=?");
            param.add(qrcode.getEndTime());
        }
        if (eventType != null) {
            sb.append(" and r.event =?");
            param.add(eventType);
        }
        sb.append(")");
        return findByHQL(sb.toString(), pageInfo, param.toArray());
    }

    /**
     * 统计带参数二维码关注度、取消关注数
     * @param accountId
     * @param qrcode
     * @param companyUnit
     * @param supperAdmin
     * @param siteAdmin
     * @param pageInfo
     * @return
     */
    public List<StatisticsQrcode> statisticsQrcode(Long accountId, WechatQrcode qrcode, SysUnit companyUnit, Boolean supperAdmin, Boolean siteAdmin, Page pageInfo) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sql.append("select q.id, q.`name`, q.scene_str, t.id as accountId, t.account, t.scrs, t.lastScTime, t.unscrs, t.lastUnscTime from wx_qrcode q, ");
        sql.append("(select a.id, a.account, l.eventKey, count(sc.openId) as scrs, max(sc.firstTime) as lastScTime, count(unsc.openId) as unscrs, max(unsc.unscMt) as lastUnscTime ");
        sql.append("from wx_receive_msg_log l inner join wx_account a on l.originalId = a.originalId ");
        sql.append("inner join (select originalId, openId, min(createTime) as firstTime ");
        sql.append("from wx_receive_msg_log ");
        sql.append("where `event` = '").append(EventTypes.subscribe).append("' ");
        sql.append("group by originalId, openId) sc on l.originalId = sc.originalId and l.openId = sc.openId and l.createTime = sc.firstTime ");
        sql.append("left join (select unscl.originalId, unscl.openId, min(unscl.createTime) as unscMt ");
        sql.append("from wx_receive_msg_log unscl, ");
        sql.append("(select originalId, openId, min(createTime) as firstTime ");
        sql.append("from wx_receive_msg_log ");
        sql.append("where `event` = '").append(EventTypes.subscribe).append("' ");
        sql.append("group by originalId, openId) mt ");
        sql.append("where unscl.originalId = mt.originalId and unscl.openId = mt.openId and unscl.createTime > mt.firstTime ");
        sql.append("and unscl.`event` = '").append(EventTypes.unsubscribe).append("' ");
        sql.append("group by unscl.originalId, unscl.openId) unsc on l.originalId = unsc.originalId and l.openId = unsc.openId ");
        sql.append("where l.`event` = '").append(EventTypes.subscribe).append("' ");
        if (accountId != null) {
            sql.append("and a.id = ? ");
            params.add(accountId);
        }
        if (StringUtils.isNotBlank(qrcode.getSceneStr())) {
            sql.append("and l.eventKey = ? ");
            params.add(qrcode.getSceneStr());
        }
        if (qrcode.getStartTime() != null) {
            sql.append("and l.createTime >= ? ");
            params.add(qrcode.getStartTime());
        }
        if (qrcode.getEndTime() != null) {
            sql.append("and l.createTime <= ? ");
            params.add(qrcode.getEndTime());
        }
        sql.append("group by a.id, a.account, l.eventKey) t ");
        sql.append("where q.scene_str = t.eventKey ");
        sql.append("order by t.scrs desc ");

        List<Object> list = findBySQL(sql.toString(), pageInfo, params.toArray());
        List<StatisticsQrcode> qrcodes = new ArrayList<StatisticsQrcode>();
        for (Object obj : list) {
            Object[] array = (Object[]) obj;
            StatisticsQrcode sq = new StatisticsQrcode();
            sq.setId(((BigInteger) array[0]).longValue());
            sq.setName((String) array[1]);
            sq.setSceneStr((String) array[2]);
            sq.setAccountId(((BigInteger) array[3]).longValue());
            sq.setAccount((String) array[4]);
            sq.setSubCount(((BigInteger) array[5]).intValue());
            sq.setSubLastTime((Date) array[6]);
            sq.setUnsubCount(((BigInteger) array[7]).intValue());
            sq.setUnsubLastTime((Date) array[8]);
            qrcodes.add(sq);
        }
        return qrcodes;
    }
}
