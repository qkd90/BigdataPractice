package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.OrderInsuranceDao;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderInsurance;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-07-11,0011.
 */
@Service
public class OrderInsuranceService {
    @Resource
    private OrderInsuranceDao orderInsuranceDao;
    @Resource
    private InsuranceService insuranceService;

    public void save(OrderInsurance orderInsurance) {
        orderInsuranceDao.save(orderInsurance);
    }

    public void delete(OrderInsurance orderInsurance) {
        orderInsuranceDao.delete(orderInsurance);
    }

    public List<OrderInsurance> createLxbOrderInsurances(List<Object> ids, Order order) {
        List<OrderInsurance> orderInsurances = Lists.newArrayList();
        if (order.getOrderInsurances() != null) {
            for (OrderInsurance orderInsurance : order.getOrderInsurances()) {
                delete(orderInsurance);
            }
        }
        for (Object id : ids) {
            Insurance insurance = insuranceService.get(Long.valueOf(id.toString()));
            if (insurance != null) {
                OrderInsurance orderInsurance = new OrderInsurance();
                orderInsurance.setOrder(order);
                orderInsurance.setInsurance(insurance);
                orderInsurance.setCreateTime(new Date());
                orderInsurance.setModifyTime(new Date());
                save(orderInsurance);
                orderInsurances.add(orderInsurance);
            }
        }
        return orderInsurances;
    }
}
