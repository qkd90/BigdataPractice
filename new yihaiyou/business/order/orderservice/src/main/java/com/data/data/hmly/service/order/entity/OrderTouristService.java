package com.data.data.hmly.service.order.entity;


import com.data.data.hmly.service.order.dao.OrderTouristDao;
import com.data.data.hmly.service.order.entity.enums.OrderTouristIdType;
import com.data.data.hmly.service.order.entity.enums.OrderTouristPeopleType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/29.
 */
@Service
public class OrderTouristService {

    Logger logger = Logger.getLogger(OrderTouristService.class);

    @Resource
    private OrderTouristDao orderTouristDao;

    public void save(OrderTourist orderTourist) {
        orderTouristDao.save(orderTourist);
    }

    public void saveAll(List<OrderTourist> orderTourists) {
        orderTouristDao.save(orderTourists);
    }

    public void updateAll(List<OrderTourist> orderTourists) {
        orderTouristDao.updateAll(orderTourists);
    }

    public List<OrderTourist> createLxbOrderTourist(List<Map<String, Object>> touristMapList, Order order, OrderDetail orderDetail) {
        List<OrderTourist> touristList = formatData(touristMapList);
        for (OrderTourist orderTourist : touristList) {
            orderTourist.setOrder(order);
            orderTourist.setOrderDetail(orderDetail);
        }
        orderTouristDao.save(touristList);
        return touristList;
    }

    public List<OrderTourist> formatData(List<Map<String, Object>> data) {
        List<OrderTourist> touristList = new ArrayList<OrderTourist>();
        for (Map<String, Object> touristMap : data) {
            OrderTourist orderTourist = new OrderTourist();
            try {
                if (touristMap.get("name") != null) {
                    orderTourist.setName(touristMap.get("name").toString());
                }
                // 'ADULT','CHILD'
                if (touristMap.get("peopleType") != null) {
                    if ("ADULT".equals(touristMap.get("peopleType").toString())) {
                        orderTourist.setPeopleType(OrderTouristPeopleType.ADULT);
                    } else if ("CHILD".equals(touristMap.get("peopleType").toString())) {
                        orderTourist.setPeopleType(OrderTouristPeopleType.CHILD);
                    }
                }
                // 'IDCARD','STUDENTCARD','PASSPORT','OTHER'
                if (touristMap.get("idType") != null && !"".equals(touristMap.get("idType").toString())) {
                    String idType = touristMap.get("idType").toString();
                    if ("IDCARD".equals(idType)) {
                        orderTourist.setIdType(OrderTouristIdType.IDCARD);
                    } else if ("STUDENTCARD".equals(idType)) {
                        orderTourist.setIdType(OrderTouristIdType.STUDENTCARD);
                    } else if ("PASSPORT".equals(idType)) {
                        orderTourist.setIdType(OrderTouristIdType.PASSPORT);
                    } else {
                        orderTourist.setIdType(OrderTouristIdType.OTHER);
                    }
                }
                if (touristMap.get("idNum") != null) {
                    orderTourist.setIdNumber(touristMap.get("idNum").toString());
                }
                if (touristMap.get("phone") != null) {
                    orderTourist.setTel(touristMap.get("phone").toString());
                }
            } catch (Exception e) {
                // 信息不完整
                logger.error("旅客信息不完整");
            }
            touristList.add(orderTourist);
        }
        return touristList;
    }

    public List<OrderTourist> getByOrderDetailId(Long orderDetailId) {
        //
        Criteria<OrderTourist> criteria = new Criteria<OrderTourist>(OrderTourist.class);
        criteria.eq("orderDetail.id", orderDetailId);
        return orderTouristDao.findByCriteria(criteria);
    }

    public List<OrderTourist> getByOrderId(Long orderId) {
        //
        Criteria<OrderTourist> criteria = new Criteria<OrderTourist>(OrderTourist.class);
        criteria.eq("order.id", orderId);
        return orderTouristDao.findByCriteria(criteria);
    }

    public Map<Long, OrderTourist> orderTouristToMap(List<OrderTourist> orderTourists) {
        Map<Long, OrderTourist> toristMap = new HashMap<Long, OrderTourist>();
        for (OrderTourist tourist : orderTourists) {
            toristMap.put(tourist.getId(), tourist);
        }
        return toristMap;
    }

    public List<OrderTourist> getOrderTouristByOrder(Order order) {

        List params = Lists.newArrayList();

        StringBuffer sb = new StringBuffer();

        sb.append("select * from tordertourist tt where tt.orderId=? group by tt.name");
        params.add(order.getId());

        return orderTouristDao.findEntitiesBySQL3(sb.toString(), new Page(1, 100), OrderTourist.class, params.toArray());
    }
}
