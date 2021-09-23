package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.RefundLogDao;
import com.data.data.hmly.service.order.entity.RefundLog;
import com.data.data.hmly.service.order.entity.enums.RefundStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.taobao.core.AlipayErroCode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/5/30.
 */
@Service
public class RefundLogService {

    @Resource
    private RefundLogDao refundLogDao;

    private List<RefundLog> refundLogList = new ArrayList<RefundLog>();

    /**
     * 通过退款记录编号组织支付宝退款请求
     * @param ids
     * @return
     */
    public Map<String, Object> doMakeRefundRequest(List<Long> ids) {

        Map<String, Object> paraMap = new HashMap<String, Object>();

        List<RefundLog> refundLogs = getListByIds(ids);
        if (refundLogs == null && refundLogs.isEmpty()) {
            return null;
        }
        String refundDate = com.taobao.util.UtilDate.getDateFormatter();
        String batchNo = com.taobao.util.UtilDate.getOrderNum();
        String batchNum = refundLogs.size() + "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < refundLogs.size(); i++) {
            RefundLog refundLog = refundLogs.get(i);
            sb.append(refundLog.getTradeNo());
            sb.append("^");
            sb.append(refundLog.getTotalRefund());
            sb.append("^");
            sb.append(refundLog.getRefundDesc());
            if (i < (refundLogs.size() - 1)) {
                sb.append("|");
            }
        }
        String detailData = sb.toString();
        paraMap.put("batchNum", batchNum);
        paraMap.put("detailData", detailData);
        return paraMap;
    }


    /**
     * 保存或更新
     * @param refundLog
     */
    public void saveOrUpdate(RefundLog refundLog) {
        if (refundLog.getId() != null) {
            refundLog.setUpdateTime(new Date());
            refundLogDao.update(refundLog);
        } else {
            refundLog.setCreateTime(new Date());
            refundLog.setUpdateTime(new Date());
            refundLogDao.save(refundLog);
        }

    }

    public RefundLog findRefundLogByTradeNo(String tradeNo, RefundStatus unSuccess) {
        Criteria<RefundLog> criteria = new Criteria<RefundLog>(RefundLog.class);
        if (unSuccess != null) {
            criteria.ne("result", unSuccess);
        }
        criteria.eq("tradeNo", tradeNo);
        refundLogList = refundLogDao.findByCriteria(criteria);
        if (refundLogList.isEmpty()) {
            return null;
        }
        return refundLogList.get(0);
    }

    public List<RefundLog> getListByIds(List<Long> ids) {
        Criteria<RefundLog> criteria = new Criteria<RefundLog>(RefundLog.class);
        criteria.ne("result", RefundStatus.SUCCESS);
        criteria.in("id", ids);
        return refundLogDao.findByCriteria(criteria);
    }

    public List<RefundLog> findList(RefundLog refundLog, Page pageInfo) {

        Criteria<RefundLog> criteria = new Criteria<RefundLog>(RefundLog.class);

        if (refundLog.getOrderNo() != null) {
            criteria.eq("orderNo", refundLog.getOrderNo());
        }

        if (refundLog.getOrderName() != null) {
            criteria.like("orderName", refundLog.getOrderName(), MatchMode.ANYWHERE);
        }

        if (refundLog.getChannel() != null) {
            criteria.eq("channel", refundLog.getChannel());
        }

        if (refundLog.getStartTime() != null) {
            criteria.ge("createTime", refundLog.getStartTime());
        }

        if (refundLog.getEndTime() != null) {
            criteria.le("createTime", refundLog.getEndTime());
        }

        if (refundLog.getResult() != null) {
            criteria.eq("result", refundLog.getResult());
        }

        criteria.orderBy(Order.desc("createTime"));

        return refundLogDao.findByCriteria(criteria, pageInfo);
    }

    public void updateByBack(String refundDetails, boolean result) {

        String[] refunLogStrArr = refundDetails.split("#");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (String refundLogStr : refunLogStrArr) {
            refundLogStr = refundLogStr.replace("^", ",");
            String[] strings = refundLogStr.split(",");
            Map<String, Object> map = new HashMap<String, Object>();
            String tradeNo = null;
            Float refundFee = null;
            String refundResult = null;
            if (strings[0] != null) {
                tradeNo = strings[0];
            }
            if (strings[2] != null) {
                refundResult = strings[2];
            }
            map.put("tradeNo", tradeNo);
            map.put("refundResult", refundResult);
            mapList.add(map);
        }

        for (Map<String, Object> map : mapList) {
            String tradeNo = (String) map.get("tradeNo");
            String refundResult = (String) map.get("refundResult");
            RefundLog refundLog = findRefundLogByTradeNo(tradeNo, RefundStatus.SUCCESS);
            if ("SUCCESS".equals(refundResult) || "TRADE_HAS_CLOSED".equals(refundResult)) {

                refundLog.setResult(RefundStatus.SUCCESS);
                refundLog.setUpdateTime(new Date());
                refundLog.setRetrunMsg(AlipayErroCode.decodeErrorCode(refundResult));
            } else {
                refundLog.setResult(RefundStatus.FAIL);
                refundLog.setUpdateTime(new Date());
                refundLog.setRetrunMsg(AlipayErroCode.decodeErrorCode(refundResult));
            }
            saveOrUpdate(refundLog);
        }


    }

    public RefundLog get(Long id) {
        return refundLogDao.load(id);
    }
}
