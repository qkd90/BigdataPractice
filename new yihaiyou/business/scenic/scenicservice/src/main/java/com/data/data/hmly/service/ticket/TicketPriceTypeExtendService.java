package com.data.data.hmly.service.ticket;

import com.data.data.hmly.service.ticket.dao.TicketDatepriceDao;
import com.data.data.hmly.service.ticket.dao.TicketPriceDao;
import com.data.data.hmly.service.ticket.dao.TicketPriceTypeExtendDao;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;
import com.framework.hibernate.util.Criteria;
import com.google.common.collect.Lists;
import com.zuipin.util.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TicketPriceTypeExtendService {
    @Resource
    private TicketPriceTypeExtendDao typeExtendDao;


    public void deleteByPriceId(Long priceId) {
        String sql = "delete from tciket_price_type_extend where type_price_id=?";
        typeExtendDao.updateBySQL(sql, priceId);
    }

    public void delExtendByPriceTypeId(Long typeId) {
        Criteria<TicketPriceTypeExtend> criteria = new Criteria<TicketPriceTypeExtend>(TicketPriceTypeExtend.class);
        criteria.eq("ticketPrice.id", typeId);
        List<TicketPriceTypeExtend> extendList = typeExtendDao.findByCriteria(criteria);
        typeExtendDao.deleteAll(extendList);
    }

    public void saveAll(List<TicketPriceTypeExtend> tempTicketPriceTypeList) {
        typeExtendDao.save(tempTicketPriceTypeList);
    }

    public void doHandlerPriceExtend(TicketPrice orginTicketPrice, TicketPrice ticketPrice) {
        List<TicketPriceTypeExtend> ticketPriceTypeExtends = getListByPriceId(ticketPrice.getId());
        for (TicketPriceTypeExtend ticketPriceTypeExtend : ticketPriceTypeExtends) {
//            TicketPriceTypeExtend tempTicketPriceTypeExtend = new TicketPriceTypeExtend();
//            BeanUtils.copyProperties(ticketPriceTypeExtend, tempTicketPriceTypeExtend, false, null, "id", );
           ticketPriceTypeExtend.setTicketPrice(orginTicketPrice);
            typeExtendDao.update(ticketPriceTypeExtend);
        }
    }

    public List<TicketPriceTypeExtend> getListByPriceId(Long priceId) {
        Criteria<TicketPriceTypeExtend> criteria = new Criteria<TicketPriceTypeExtend>(TicketPriceTypeExtend.class);
        criteria.eq("ticketPrice.id", priceId);
        return typeExtendDao.findByCriteria(criteria);
    }
}
