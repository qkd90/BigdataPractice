package com.data.data.hmly.service.ticket;

import com.data.data.hmly.service.ticket.dao.TicketDatepriceDao;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.enmus.TicketPriceStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Maps;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TicketDatepriceService {
    @Resource
    private TicketDatepriceDao datepriceDao;

    @Resource
    private TicketPriceService ticketPriceService;

    public TicketDateprice getTicketDatePrice(Long ticketPriceId, Date playDate) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPriceId);
        criteria.eq("huiDate", playDate);
        return datepriceDao.findUniqueByCriteria(criteria);
    }

    public void update(TicketDateprice ticketDateprice) {
        datepriceDao.update(ticketDateprice);
    }
/*
    public void save(String dayPriceStr, TicketPrice ticketPrice) {

        dayPriceStr = dayPriceStr.substring(0, dayPriceStr.length() - 1);
        String[] dayprice = dayPriceStr.split(",");
        for (int i = 0; i < dayprice.length; i++) {

            String[] days = dayprice[i].split("#");

            TicketDateprice dateprice = new TicketDateprice();

            String dateTime = days[0];

            Date date = new Date(Long.valueOf(dateTime));

            dateprice.setHuiDate(date);
            dateprice.setTicketPriceId(ticketPrice);
            dateprice.setPriPrice(Float.parseFloat(days[1]));

            datepriceDao.save(dateprice);
        }
    }*/

    public Date findMinDateByPrice(TicketPrice ticketPrice) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        criteria.setProjection(Projections.min("huiDate"));
        Date min = (Date) datepriceDao.findUniqueValue(criteria);
        return min;
    }


    public Date findMaxDateByPrice(TicketPrice ticketPrice) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        criteria.setProjection(Projections.max("huiDate"));
        Date min = (Date) datepriceDao.findUniqueValue(criteria);
        return min;
    }

    public List<TicketDateprice> findTicketList(
            TicketDateprice ticketDateprice, Page pageInfo) {
        return datepriceDao.findTicketList(ticketDateprice, pageInfo);
    }

    public List<TicketDateprice> findTypePriceDate(Long ticketpriceId,
                                                   Date dateStart, Date dateEnd) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketpriceId);
        if (dateStart != null) {
            criteria.ge("huiDate", dateStart);
        }
        if (dateEnd != null) {
            criteria.le("huiDate", dateEnd);
        }
        criteria.orderBy("huiDate", "asc");

        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

        return dateprices;
    }

    public List<TicketDateprice> findTypePriceDate(Long ticketpriceId,
                                                   Date dateStart, Date dateEnd, Integer num, String... orderParams) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketpriceId);
        if (dateStart != null) {
            criteria.ge("huiDate", dateStart);
        }
        if (dateEnd != null) {
            criteria.le("huiDate", dateEnd);
        }
        if (num != null) {
            criteria.or(Restrictions.eq("inventory", -1), Restrictions.ge("inventory", num), Restrictions.isNull("inventory"));
        }
        if (orderParams != null && orderParams.length > 0) {
            if (orderParams.length == 1) {
                criteria.orderBy(Order.asc(orderParams[0]));
            } else {
                criteria.orderBy(orderParams[0], orderParams[1]);
            }
        } else {
            criteria.orderBy("huiDate", "asc");
        }

        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

        return dateprices;
    }

    public void delAllByprice(TicketPrice tPrice) {

        String hql = " delete TicketDateprice where ticketPriceId.id = ?";
        datepriceDao.updateByHQL(hql, tPrice.getId());

//        String hql = "delete from TicketDateprice where ticketPriceId.id=" + tPrice.getId();
//        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
//        criteria.eq("ticketPriceId.id", tPrice.getId());
//
//        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

//        datepriceDao.deleteAll(dateprices);

    }

    public void deleteAll(TicketPrice ticketPrice) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());

        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

        datepriceDao.deleteAll(dateprices);
    }

    public void deleteAll(TicketPrice ticketPrice, Date startDate) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        if (startDate != null) {
            criteria.gt("huiDate", startDate);
        }
        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

        datepriceDao.deleteAll(dateprices);
    }

    public String findListByPrice(TicketPrice ticketPrice) throws ParseException {

        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());

        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

        String priceStr = "";

        if (dateprices.size() > 0) {
            for (TicketDateprice datas : dateprices) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(datas.getHuiDate().toString());
                priceStr += date.getTime() + "#" + datas.getPriPrice() + ",";
            }
            priceStr = priceStr.substring(0, priceStr.length() - 1);
        }


        return priceStr;
    }

    public TicketDateprice getLowestPrice(Long ticketId) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.createCriteria("ticketPriceId", "ticketPrice", JoinType.INNER_JOIN);
        criteria.eq("ticketPrice.ticket.id", ticketId);
        criteria.orderBy(Order.asc("priPrice"));
        List<TicketDateprice> ticketDateprices = datepriceDao.findByCriteria(criteria, new Page(0, 1));
        if (ticketDateprices.isEmpty()) {
            return null;
        }
        return ticketDateprices.get(0);
    }

    public Float findMinPriceByTicketId(Long ticketId, Date startDate, Date endDate, String prop) {
        Criteria<TicketDateprice> ticketDatepriceCriteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        DetachedCriteria ticketPriceCriteria = ticketDatepriceCriteria.createCriteria("ticketPriceId", "ticketPrice", JoinType.INNER_JOIN);
        ticketPriceCriteria.createCriteria("ticket", "ticket");
        ticketPriceCriteria.add(Restrictions.eq("ticket.id", ticketId));
        ticketPriceCriteria.add(Restrictions.eq("status", TicketPriceStatus.UP));
        if (startDate != null) {
            ticketDatepriceCriteria.ge("huiDate", startDate);
        }
        if (endDate != null) {
            ticketDatepriceCriteria.le("huiDate", endDate);
        }
        ticketDatepriceCriteria.setProjection(Projections.min(prop));
        return (Float) datepriceDao.findUniqueValue(ticketDatepriceCriteria);
    }

    public Float findMinPrice(TicketPrice ticketPrice, Date start) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        if (start != null) {
            criteria.ge("huiDate", start);
        }
        criteria.setProjection(Projections.min("priPrice"));
        return (Float) datepriceDao.findUniqueValue(criteria);
    }

    public Date findEndDate(TicketPrice ticketPrice) {

        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        criteria.ge("huiDate", new Date());
        criteria.orderBy(Order.desc("huiDate"));
        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

        if (!dateprices.isEmpty()) {
            return dateprices.get(0).getHuiDate();
        }

        return null;

    }


    public List<TicketDateprice> findAvailableByType(TicketPrice ticketPrice) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        criteria.gt("huiDate", new Date());
        criteria.orderBy(Order.asc("huiDate"));
        return datepriceDao.findByCriteria(criteria);
    }

    public TicketDateprice load(Long id) {
        return datepriceDao.load(id);
    }

    public void save(List<TicketDateprice> ticketPriceDates) {
        datepriceDao.save(ticketPriceDates);
    }

    public void saveAll(List<TicketDateprice> ticketPriceDates) {
        datepriceDao.saevAll(ticketPriceDates);
    }

    public void save(List<TicketDateprice> ticketPriceDates, TicketPrice ticketPrice) {
        datepriceDao.save(ticketPriceDates);
        ticketPriceService.update(ticketPrice);
//        Date startDate = ticketPriceDates.get(0).getHuiDate();
//        Date endDate = ticketPriceDates.get(ticketPriceDates.size() - 1 ).getHuiDate();
//        TicketPrice ticketPrice = ticketPriceService.getPrice(ticketPriceId);
//        TicketMinData ticketMinData = datepriceDao.findMinPrice(ticketPriceId, startDate, endDate, "priPrice");
//        if (ticketMinData != null) {
//            ticketPrice.setMinDiscountPrice(ticketMinData.getMinPriPrice().doubleValue());
//            ticketPrice.setDiscountPrice(ticketMinData.getMinPriPrice().floatValue());
//            ticketPrice.setCommission(ticketMinData.getMinRebate());
//            ticketPrice.setPrice(ticketMinData.getMinPriPrice().doubleValue());
//
//        }
    }



    // 根据价格ID和使用日期查询票价信息
    public TicketDateprice getByDate(Long ticketPriceId, Date date) {
        //
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPriceId);
        criteria.eq("huiDate", date);
        return datepriceDao.findUniqueByCriteria(criteria);
    }

    public List<TicketDateprice> getForPlanOrder(Date date, List<Long> scenicIds) {
        //
        String sIds = scenicIds.toString();
        sIds = sIds.replace("[", "(");
        sIds = sIds.replace("]", ")");
        String hql = "select p from TicketDateprice p where p.huiDate = ? and p.ticketPriceId.ticket.status = 'UP' and p.ticketPriceId.ticket.scenicInfo.id in "
                + sIds
                + " GROUP BY p.ticketPriceId.ticket.scenicInfo.id order by min(p.priPrice)";
        return datepriceDao.findByHQL(hql, date);
    }

    public List<TicketDateprice> getSamePriceByPriceId(Long costId, Date playDate, Integer needNum) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        TicketDateprice sourceTicketDateprice = this.getTicketDatePrice(costId, playDate);
        criteria.createCriteria("ticketPriceId");
        criteria.eq("ticketPriceId.id", costId);
        Date nowDate = new Date();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(nowDate);
        endCalendar.add(Calendar.DAY_OF_YEAR, 7);
        Date endDate = endCalendar.getTime();
        criteria.le("huiDate", endDate);
        criteria.gt("huiDate", nowDate);
        criteria.ne("huiDate", playDate);
        criteria.eq("priPrice", sourceTicketDateprice.getPriPrice());
        criteria.eq("price", sourceTicketDateprice.getPrice());
        criteria.eq("maketPrice", sourceTicketDateprice.getMaketPrice());
        criteria.ge("inventory", needNum);
        return datepriceDao.findByCriteria(criteria);
    }

    public List<TicketDateprice> getByScenicIdsAndDate(List<Long> scenicIds, Date date) {
        String sIds = scenicIds.toString();
        sIds = sIds.replace("[", "(");
        sIds = sIds.replace("]", ")");
        String hql = "select p from TicketDateprice p where p.huiDate = ? and p.ticketPriceId.ticket.status = 'UP' and p.ticketPriceId.ticket.scenicInfo.id in "
                + sIds;
        return datepriceDao.findByHQL(hql, date);
    }

    public List<TicketDateprice> getByPriceIdsAndDate(List<Long> priceIds, Date date) {
        String sIds = priceIds.toString();
        sIds = sIds.replace("[", "(");
        sIds = sIds.replace("]", ")");
        String hql = "select p from TicketDateprice p where p.huiDate = ? and p.ticketPriceId.ticket.status = 'UP' and p.ticketPriceId.id in "
                + sIds;
        return datepriceDao.findByHQL(hql, date);
    }

    public TicketDateprice findFirstAvailableByPriceType(TicketPrice ticketPrice, Page page) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        criteria.gt("huiDate", new Date());
        criteria.orderBy(Order.asc("huiDate"));
        List<TicketDateprice> datepriceList = datepriceDao.findByCriteria(criteria, page);
        TicketDateprice ticketDateprice = new TicketDateprice();
        if (datepriceList != null && !datepriceList.isEmpty()) {
            ticketDateprice = datepriceList.get(0);
        } else {
            ticketDateprice = null;
        }
        return ticketDateprice;
    }

    public Map<String, Date> getFirstAndEndDate(TicketPrice ticketPrice) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", ticketPrice.getId());
        criteria.orderBy(Order.asc("huiDate"));
        List<TicketDateprice> datepriceList = datepriceDao.findByCriteria(criteria);
        Map<String, Date> dateMap = Maps.newHashMap();
        if (datepriceList.isEmpty()) {
            return dateMap;
        }
        dateMap.put("start", datepriceList.get(0).getHuiDate());
        dateMap.put("end", datepriceList.get(datepriceList.size() - 1).getHuiDate());
        return dateMap;
    }
}
