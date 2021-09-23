package com.data.data.hmly.service.order;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.dao.LvjiOrderDao;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.LvjiUtil;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/8/18.
 */
@Service
public class LvjiOrderService {
    @Resource
    private LvjiOrderDao lvjiOrderDao;

    public LvjiOrder saveOrder(LvjiOrder order) {
        Date date = new Date();
        order.setCreateTime(date);
        order.setModifyTime(date);
        lvjiOrderDao.save(order);
        return order;
    }

    public LvjiOrder query(Long id){

        return lvjiOrderDao.get(LvjiOrder.class, id);
    }

    public void updateOrder(LvjiOrder order) {
        lvjiOrderDao.update(order);
    }

    public Map<String, Object> doRefundLjOrder(LvjiOrder lvjiOrder, Member member) {
        Map<String, Object> map = LvjiUtil.refund(lvjiOrder);
        if ("0".equals(map.get("status"))) {   // 提交申请成功
            lvjiOrder.setStatus(OrderStatus.CANCELING);
            lvjiOrderDao.save(lvjiOrder);
        }
        return map;
    }

}
