package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.OrderInvoiceDao;
import com.data.data.hmly.service.order.entity.OrderInvoice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by huangpeijie on 2016-05-20,0020.
 */
@Service
public class OrderInvoiceService {
    @Resource
    private OrderInvoiceDao orderInvoiceDao;

    public void save(OrderInvoice orderInvoice) {
        orderInvoiceDao.save(orderInvoice);
    }
}
