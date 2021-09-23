package com.data.data.hmly.service.line;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.dao.HotelDao;
import com.data.data.hmly.service.hotel.dao.HotelPriceDao;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.line.dao.LinedaysProductPriceDao;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linedays;
import com.data.data.hmly.service.line.entity.LinedaysProductPrice;
import com.data.data.hmly.service.ticket.dao.TicketDao;
import com.data.data.hmly.service.ticket.dao.TicketPriceDao;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/8/17.
 */
@Service
public class LinedaysProductPriceService {
    @Resource
    private LinedaysProductPriceDao linedaysProductPriceDao;


    @Resource
    private HotelDao hotelDao;
    @Resource
    private HotelPriceDao hotelPriceDao;
    @Resource
    private TicketDao ticketDao;
    @Resource
    private TicketPriceDao ticketPriceDao;
    @Resource
    private LineService lineService;

    /**
     * 查询线路产品关联列表
     * @param linedaysProductPrice
     * @return
     */
    public List<LinedaysProductPrice> listLineProduct(LinedaysProductPrice linedaysProductPrice) {
        return linedaysProductPriceDao.listLineProduct(linedaysProductPrice);
    }

    /**
     * 线路关联产品
     * @param priceIds
     * @param lineId
     * @param linedaysId
     * @param productType
     */
    public void doRelateProduct(String priceIds, Long lineId, Long linedaysId, ProductType productType, SysUser sysUser) {
        if (StringUtils.isNotBlank(priceIds)) {
            String[] idsArray = priceIds.split(",");
            for (String priceId : idsArray) {
                LinedaysProductPrice linedaysProductPrice = new LinedaysProductPrice();
                linedaysProductPrice.setLineId(lineId);
                Linedays linedays = new Linedays();
                linedays.setId(linedaysId);
                linedaysProductPrice.setLinedays(linedays);
                linedaysProductPrice.setProductType(productType);
                if (productType == ProductType.hotel) {
                    HotelPrice hotelPrice = hotelPriceDao.load(Long.valueOf(priceId));
                    Hotel hotel = hotelPrice.getHotel();
                    linedaysProductPrice.setProductId(hotel.getId());
                    linedaysProductPrice.setProductName(hotel.getName());
                    linedaysProductPrice.setPriceId(hotelPrice.getId());
                    linedaysProductPrice.setPriceName(hotelPrice.getRoomName());
                } else if (productType == ProductType.scenic) {
                    TicketPrice ticketPrice = ticketPriceDao.load(Long.valueOf(priceId));
                    Ticket ticket = ticketPrice.getTicket();
                    linedaysProductPrice.setProductId(ticket.getId());
                    linedaysProductPrice.setProductName(ticket.getName());
                    linedaysProductPrice.setPriceId(ticketPrice.getId());
                    linedaysProductPrice.setPriceName(ticketPrice.getName());
                    linedaysProductPrice.setScenicInfoId(ticket.getScenicInfo().getId());
                }
                linedaysProductPrice.setUserId(sysUser.getId());
                linedaysProductPrice.setCreateTime(new Date());
                linedaysProductPriceDao.save(linedaysProductPrice);
            }
        }
    }

    /**
     * 取消线路产品关联
     */
    public void doUnRelateProduct(String linedaysProductPriceIds) {
        if (StringUtils.isNotBlank(linedaysProductPriceIds)) {
            String[] idsArray = linedaysProductPriceIds.split(",");
            for (String linedaysProductPriceId : idsArray) {
                linedaysProductPriceDao.delete(Long.valueOf(linedaysProductPriceId), LinedaysProductPrice.class);
            }
        }
    }

    public List<Line> listLine(Long productId, ProductType productType, Page page) {
        LinedaysProductPrice linedaysProductPrice = new LinedaysProductPrice();
        linedaysProductPrice.setProductId(productId);
        linedaysProductPrice.setProductType(productType);
        List<LinedaysProductPrice> linedaysProductPrices = list(linedaysProductPrice, page);
        List<Line> list = Lists.transform(linedaysProductPrices, new Function<LinedaysProductPrice, Line>() {
            @Override
            public Line apply(LinedaysProductPrice input) {
                return lineService.loadLine(input.getLineId());
            }
        });
        return lineService.completeLinePrice(list);
    }

    public List<LinedaysProductPrice> list(LinedaysProductPrice linedaysProductPrice, Page page) {
        Criteria<LinedaysProductPrice> criteria = createCriteria(linedaysProductPrice);
        if (page == null) {
            return linedaysProductPriceDao.findByCriteria(criteria);
        } else {
            return linedaysProductPriceDao.findByCriteria(criteria, page);
        }
    }

    private Criteria<LinedaysProductPrice> createCriteria(LinedaysProductPrice linedaysProductPrice) {
        Criteria<LinedaysProductPrice> criteria = new Criteria<LinedaysProductPrice>(LinedaysProductPrice.class);
        if (linedaysProductPrice.getLineId() != null) {
            criteria.eq("lineId", linedaysProductPrice.getLineId());
        }
        if (linedaysProductPrice.getLinedays() != null && linedaysProductPrice.getLinedays().getId() != null) {
            criteria.eq("linedays.id", linedaysProductPrice.getLinedays().getId());
        }
        if (linedaysProductPrice.getProductType() != null) {
            criteria.eq("productType", linedaysProductPrice.getProductType());
        }
        if (linedaysProductPrice.getProductId() != null) {
            criteria.eq("productId", linedaysProductPrice.getProductId());
        }
        return criteria;
    }

    public void delByNotExistDayIdList(List<Long> linedayIds, Long lineId) {
        String hql = "delete LinedaysProductPrice";
        List params = new ArrayList();
        if (!linedayIds.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            sb.append(" where linedays.id not in (");
            for (int i = 0; i < linedayIds.size(); i++) {
                if (i < linedayIds.size() - 1) {
                    sb.append("?,");
                    params.add(linedayIds.get(i));
                } else {
                    sb.append("?");
                    params.add(linedayIds.get(i));
                }
            }
            sb.append(")");
            if (lineId != null) {
                sb.append(" and lineId=?");
                params.add(lineId);
            }
            linedaysProductPriceDao.updateByHQL(hql + sb.toString(), params.toArray());
        }
    }
}
