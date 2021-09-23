package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.ProValidCodeDao;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/12/22.
 */
@Service
public class ProValidCodeService {

    @Resource
    private ProValidCodeDao proValidCodeDao;


    public Map<String, Object> getCheckValidateCodeInfo(ProValidCode proValidCode) {
        StringBuffer sql = new StringBuffer();
        /*SELECT
        pvc.product_type_name as productTypeName,
                pvc.order_no as orderNo,
        pvc.buyer_name as buyerName,
                pvc.buyer_name as buyerMobile,
        count(pvc.order_detail_id) AS totalNum,
        count(case when pvc.used = 0 then pvc.used end) as usedNum,
        tod.price as totalPrice,
                tod.status as orderStatus,
        tod.createTime as createTime
                FROM
        product_validate_code pvc
        LEFT JOIN torder tod ON tod.id = pvc.order_id
        left join torderdetail toda on toda.id = pvc.order_detail_id
        WHERE
        pvc.supplier_id = 1000340
        AND pvc.order_detail_id = 4776
        and pvc.order_id = 3159
        GROUP BY
        pvc.order_detail_id;*/
        List param = new ArrayList();
        sql.append("SELECT\n" +
                "        pvc.product_type_name as productTypeName,\n" +
                "                pvc.order_no as orderNo,\n" +
                "        pvc.buyer_name as buyerName,\n" +
                "                pvc.buyer_mobile as buyerMobile,\n" +
                "        count(pvc.order_detail_id) AS totalNum,\n" +
                "        count(case when pvc.used = 0 then pvc.used end) as unUsedNum,\n" +
                "        tod.price as totalPrice,\n" +
                "                toda.status as orderDetailStatus,\n" +
                "        tod.createTime as orderCreateTime\n" +
                "                FROM\n" +
                "        product_validate_code pvc\n" +
                "        LEFT JOIN torder tod ON tod.id = pvc.order_id\n" +
                "        left join torderdetail toda on toda.id = pvc.order_detail_id\n" +
                "        WHERE\n" +
                "        pvc.supplier_id = ?\n" +
                "        AND pvc.order_detail_id = ?\n" +
                "        GROUP BY\n" +
                "        pvc.order_detail_id");
        param.add(proValidCode.getSupplierId());
        param.add(proValidCode.getOrderDetailId());
        Page pageInfo = new Page(1, 10);
        List<Map<String, Object>> resultMapList = proValidCodeDao.findEntitiesBySQL4(sql.toString(), pageInfo, param.toArray());

        if (resultMapList == null || resultMapList.isEmpty()) {
            return null;
        }
        Map<String, Object> resultMap = resultMapList.get(0);

        proValidCode.setUsed(1);
        List<ProValidCode> productValidateCodeList = getValidateInfoList(proValidCode, null);
        resultMap.put("usedValidCodeList", productValidateCodeList);

        doFormatMap(resultMap);

        return resultMap;

    }

    public void doFormatMap(Map<String, Object> resultMap) {
        if (resultMap.get("orderDetailStatus") != null
                && "SUCCESS".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "预订成功");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "UNCONFIRMED".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "待确认");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "WAITING".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "待支付");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "PAYED".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "已支付");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "FAILED".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "预订失败");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "CANCELED".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "已取消");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "REFUNDED".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "已退款");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "CHECKIN".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "已入住");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "CHECKOUT".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "已退房");
        }
        if (resultMap.get("orderDetailStatus") != null
                && "INVALID".equals(resultMap.get("orderDetailStatus").toString())) {
            resultMap.put("orderDetailStatus", "无效订单");
        }
        if (resultMap.get("orderCreateTime") != null) {
            resultMap.put("orderCreateTime", DateUtils.format((Date) resultMap.get("orderCreateTime"), "yyyy-MM-dd HH:mm:ss"));
        }
    }

    public List<ProValidCode> getYhyValidateCodeList(ProValidCode proValidCode, String ticketType, Page pageInfo) {
        List param = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append("select p.id as id, p.name as productName, pvcd.order_no as orderNo, pvcd.order_id as orderId, pvcd.buyer_name as buyerName, pvcd.buyer_mobile as buyerMobile, pvcd.supplier_name as supplierName from product_validate_code pvcd " +
                "left join torderdetail toad on toad.orderid = pvcd.order_id " +
                "left join product p on p.id = toad.proId");

        String sqlQueryParams = "";
        if (StringUtils.isNotBlank(proValidCode.getOrderNo())) {
            if (sqlQueryParams.length() > 0) {
                sqlQueryParams += " and pvcd.order_no like '%" + proValidCode.getOrderNo() + "%'";
            } else {
                sqlQueryParams += " where pvcd.order_no like '%" + proValidCode.getOrderNo() + "%'";
            }
        }

        if (StringUtils.isNotBlank(proValidCode.getProductName())) {
            if (sqlQueryParams.length() > 0) {
                sqlQueryParams += " and p.name like '%" + proValidCode.getProductName() + "%'";
            } else {
                sqlQueryParams += " where p.name like '%" + proValidCode.getProductName() + "%'";
            }
        }

        if (proValidCode.getUsed() != null) {
            if (sqlQueryParams.length() > 0) {
                sqlQueryParams += " and pvcd.used =?";
            } else {
                sqlQueryParams += " where pvcd.used =?";
            }
            param.add(proValidCode.getUsed());
        }

        if (sqlQueryParams.length() > 0) {
            sql.append(sqlQueryParams);
        }
        sql.append(" group by pvcd.order_id");
        sql.append(" order by pvcd.create_time desc");
        List<Map<String, Object>> resultMapList = proValidCodeDao.findEntitiesBySQL4(sql.toString(), pageInfo, param.toArray());
        List<ProValidCode> resultList = Lists.newArrayList();

        for (Map<String, Object> map : resultMapList) {
            ProValidCode validateCode = new ProValidCode();
            if (map.get("orderId") == null) {
                break;
            }
            proValidCode.setOrderId(Long.parseLong(map.get("orderId").toString()));
            List<ProValidCode> productValidateCodeList = getValidateInfoList(proValidCode, null);
            validateCode.setProValidCodeList(productValidateCodeList);
            if (map.get("productName") != null) {
                validateCode.setProductName(map.get("productName").toString());
            }
            if (map.get("orderId") != null) {
                validateCode.setOrderId(Long.parseLong(map.get("orderId").toString()));
            }
            if (map.get("buyerName") != null) {
                validateCode.setBuyerName((String) map.get("buyerName"));
            }
            if (map.get("buyerMobile") != null) {
                validateCode.setBuyerMobile((String) map.get("buyerMobile"));
            }
            if (map.get("supplierName") != null) {
                validateCode.setSupplierName((String) map.get("supplierName"));
            }
            if (map.get("orderNo") != null) {
                validateCode.setOrderNo((String) map.get("orderNo"));
            }
            resultList.add(validateCode);
        }
        return resultList;
    }

    public List<ProValidCode> getByOrder(Long orderId) {
        Criteria<ProValidCode> criteria = new Criteria<ProValidCode>(ProValidCode.class);
        criteria.eq("orderId", orderId);
        return proValidCodeDao.findByCriteria(criteria);
    }

    public List<ProValidCode> getValidateInfoList(ProValidCode productValidateCode, Page page) {
        Criteria<ProValidCode> criteria = new Criteria<ProValidCode>(ProValidCode.class);
        if (productValidateCode.getOrderId() != null) {
            criteria.eq("orderId", productValidateCode.getOrderId());
        }
        if (productValidateCode.getOrderDetailId() != null) {
            criteria.eq("orderDetailId", productValidateCode.getOrderDetailId());
        }
        if (productValidateCode.getUsed() != null) {
            criteria.eq("used", productValidateCode.getUsed());
        }
        if (productValidateCode.getSupplierId() != null) {
            criteria.eq("supplierId", productValidateCode.getSupplierId());
        }
        if (StringUtils.isNotBlank(productValidateCode.getSearchKeyword())) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("code", productValidateCode.getSearchKeyword(), MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("orderNo", productValidateCode.getSearchKeyword(), MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }
        criteria.orderBy("updateTime", "desc");
        if (page != null) {
            return proValidCodeDao.findByCriteria(criteria, page);
        }
        return proValidCodeDao.findByCriteria(criteria);
    }

    /**
     * 获取唯一验证码记录
     * @param code
     * @param validateCode
     * @return
     */
    public ProValidCode checkVliadateCode(String code, ProValidCode validateCode) {
        Criteria<ProValidCode> criteria = new Criteria<ProValidCode>(ProValidCode.class);
        criteria.eq("code", code);
        if (validateCode.getUsed() != null) {
            criteria.eq("used", validateCode.getUsed());
        }
        if (validateCode.getSupplierId() != null) {
            criteria.eq("supplierId", validateCode.getSupplierId());
        }
        ProValidCode resultPvCode = proValidCodeDao.findUniqueByCriteria(criteria);
        if (resultPvCode != null) {
            return resultPvCode;
        } else {
            return null;
        }
    }

    public ProValidCode findByOrder(Long orderId, Long orderDetailId, Integer used) {
        Criteria<ProValidCode> criteria = new Criteria<ProValidCode>(ProValidCode.class);
        if (orderId != null) {
            criteria.eq("orderId", orderId);
        }
        if (orderDetailId != null) {
            criteria.eq("orderDetailId", orderDetailId);
        }
        if (used != null) {
            criteria.eq("used", used);
        }
        return proValidCodeDao.findUniqueByCriteria(criteria);
    }

    public void update(ProValidCode pvCode) {
        pvCode.setUpdateTime(new Date());
        proValidCodeDao.update(pvCode);
    }

    public void save(ProValidCode pvCode) {
        pvCode.setCreateTime(new Date());
        proValidCodeDao.save(pvCode);
    }
}
