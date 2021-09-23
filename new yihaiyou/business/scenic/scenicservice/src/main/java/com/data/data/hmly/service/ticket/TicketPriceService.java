package com.data.data.hmly.service.ticket;

import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.ticket.dao.TicketDatepriceDao;
import com.data.data.hmly.service.ticket.dao.TicketPriceDao;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketMinData;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.enmus.TicketPriceStatus;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TicketPriceService {
    @Resource
    private TicketPriceDao priceDao;
    @Resource
    private TicketDatepriceDao datepriceDao;
    @Resource
    private TicketDatepriceService datepriceService;
    @Resource
    private ProductimageService productimageService;

    public TicketPriceStatus getPriceStatus(Long id) {
        String sql = "select status from ticketprice where id=?";
        List<?> s = priceDao.findBySQL(sql, id);
        if (s != null && !s.isEmpty() && s.get(0) != null) {
            return TicketPriceStatus.valueOf(s.get(0).toString());
        }
        return null;
    }

    public ShowStatus getShowStatus(Long id) {
        String sql = "select show_status from ticketprice where id=?";
        List<?> s = priceDao.findBySQL(sql, id);
        if (s != null && !s.isEmpty() && s.get(0) != null) {
            return ShowStatus.valueOf(s.get(0).toString());
        }
        return null;
    }


    /**
     * 查询景点门票，线路组合使用
     */
    public List<TicketPrice> listTicketPriceForLine(TicketPrice ticketPrice, Page page, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin) {
        return priceDao.listTicketPriceForLine(ticketPrice, page, sysUser, isSiteAdmin, isSupperAdmin);
    }

    /**
     * 获取类别报价中最小优惠价，日期范围第二天和下个月最后一天
     *
     * @param dateStart
     * @param dateEnd
     * @return
     * @author caiys
     * @date 2015年10月21日 下午3:50:34
     */
    public Float findMinValue(Long tickettypepriceId, Date dateStart, Date dateEnd, String prop) {
        return priceDao.findMinValue(tickettypepriceId, dateStart, dateEnd, prop);
    }

    @SuppressWarnings("null")
    public List<TicketMinData> findMinPrice(List<TicketPrice> ticketPrices, Date dateStart,
                                            Date dateEnd, String prop) {
//		List<Long> longs = new ArrayList<Long>();
        List<TicketMinData> minData = new ArrayList<TicketMinData>();
        List<String> priceStrs = new ArrayList<String>();
        if (ticketPrices.size() > 0) {
            for (TicketPrice tp : ticketPrices) {
                TicketMinData min = datepriceDao.findMinPrice(tp.getId(), dateStart, dateEnd, prop);
//                if (min != null) {
                minData.add(min);
//                }
            }
        }
        return minData;
    }


    public TicketPrice findById(Long ticketPriceId) {

        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);

        criteria.eq("id", ticketPriceId);

        return priceDao.findUniqueByCriteria(criteria);
    }

    public List<TicketPrice> findTicketList(Long ticketId,
                                            Page pageInfo) {

        String hql = "from TicketPrice where ticketId=" + ticketId;

        if (pageInfo == null) {
            return priceDao.findByHQL(hql);
        }
//		return priceDao.findByCriteria(criteria, pageInfo);
        return priceDao.findByHQL(hql, pageInfo);
    }

    public int getCountsByTid(Long ticketId) {

        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);

        criteria.eq("ticketId", ticketId);

        String hql = "from TicketPrice where ticketId=" + ticketId;

        List<TicketPrice> tList = priceDao.findByHQL(hql);
//		return priceDao.findByCriteria(criteria, pageInfo);

        if (!tList.isEmpty()) {
            return tList.size();
        } else {
            return 0;
        }

    }


    public Float findTicketMinPrice(Ticket ticket) {

        List<TicketPrice> ticketPriceList = findTicketPriceListByTicket(ticket);

        List<Float> priceList = new ArrayList<Float>();

        for (TicketPrice price : ticketPriceList) {
            Float p = price.getDiscountPrice();
            if (p != null)
                priceList.add(p);
        }

        if (!priceList.isEmpty()) {
            Float minPrice = Collections.min(priceList);
            return minPrice;
        }


        return 0f;
    }

    public TicketMinData findTicketMinPrice(Long ticketId, Date startDate, Date endDate, String prop) {
        List<TicketPrice> ticketPriceList = findAvailablePriceList(ticketId, new Page(1, 100), startDate);
        List<TicketMinData> ticketMinDatas = findMinPrice(ticketPriceList, startDate,
                endDate, prop);
        List<Float> minRebateList = new ArrayList<Float>();
        List<Float> minPriPriceList = new ArrayList<Float>();
        List<Float> minMarketPriceList = new ArrayList<Float>();
        for (TicketMinData price : ticketMinDatas) {
            Float minRebate = price.getMinRebate();
            Float minPriPrice = price.getMinPriPrice();
            Float minMarketPrice = price.getMinMarketPrice();
            if (minRebate != null) {
                minRebateList.add(minRebate);
            }
            if (minPriPrice != null) {
                minPriPriceList.add(minPriPrice);
            }
            if (minMarketPrice != null) {
                minMarketPriceList.add(minMarketPrice);
            }
        }

        TicketMinData ticketMinData = new TicketMinData();

        if (!minRebateList.isEmpty()) {
            Float minRebate = Collections.min(minRebateList);
            ticketMinData.setMinRebate(minRebate);
        }
        if (!minPriPriceList.isEmpty()) {
            Float minPriPrice = Collections.min(minPriPriceList);
            ticketMinData.setMinPriPrice(minPriPrice);
        }
        if (!minMarketPriceList.isEmpty()) {
            Float minMarketPrice = Collections.min(minMarketPriceList);
            ticketMinData.setMinMarketPrice(minMarketPrice);
        }


        return ticketMinData;
    }


    public List<TicketPrice> findTicketPriceListByTicket(Ticket ticket) {

        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);

        criteria.eq("ticket", ticket);

        return priceDao.findByCriteria(criteria);

    }

    public List<TicketPrice> findTicketPriceTypeByScenicId(Long scenicId) {
        List<TicketPrice> list = priceDao.findTicketPriceTypeByScenicId(scenicId);
        for (TicketPrice ticketPrice : list) {
            ticketPrice.formatType();
        }
        return list;
    }

    public TicketPrice getPrice(long priceId) {
        return priceDao.load(priceId);
    }

    public TicketPrice findFullById(Long id) {
        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);
        criteria.eq("id", id);
        DetachedCriteria ticketCriteria = criteria.createCriteria("ticket");
        ticketCriteria.createCriteria("ticketPriceSet", JoinType.LEFT_OUTER_JOIN);
        ticketCriteria.createCriteria("labelItems", JoinType.LEFT_OUTER_JOIN);
        ticketCriteria.createCriteria("scenicInfo", JoinType.LEFT_OUTER_JOIN);
        return priceDao.findUniqueByCriteria(criteria);
    }

    public void update(TicketPrice tPrice) {
        priceDao.update(tPrice);
    }

    public void save(TicketPrice ticketPrice) {
        priceDao.save(ticketPrice);
    }

    public void delPrice(String priceIdStr) {

        TicketPrice price = priceDao.load(Long.parseLong(priceIdStr));

        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);

        criteria.eq("ticketPriceId.id", price.getId());

        List<TicketDateprice> dateprices = datepriceDao.findByCriteria(criteria);

        datepriceDao.deleteAll(dateprices);

        //明天继续

        priceDao.delete(price);
    }

    public void delPrice(Ticket ticket) {

        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);

        criteria.eq("ticket.id", ticket.getId());

        List<TicketPrice> prices = priceDao.findByCriteria(criteria);

        for (TicketPrice tp : prices) {

            datepriceService.deleteAll(tp);

        }
        priceDao.deleteAll(prices);

    }

    public List<TicketPrice> findTickettypepriceList(TicketPrice ticketPrice) {
        List<TicketPrice> list = priceDao.findTickettypepriceList(ticketPrice);
        for (TicketPrice price : list) {
            price.formatType();
            price.setAddInfoList(findTicketPriceAddInfoList(price));
        }
        return list;
    }

    public List<TicketPriceAddInfo> findTicketPriceAddInfoList(TicketPrice ticketPrice) {
        if (ticketPrice == null) {
            return Lists.newArrayList();
        }
        List<TicketPriceAddInfo> list = new ArrayList<TicketPriceAddInfo>();
        if (ticketPrice.getCtripTicketId() != null) {
            List<Object> stringList = priceDao.findTicketPriceAddInfo(ticketPrice);
            for (int i = 0; i < stringList.size(); i++) {
                TicketPriceAddInfo info = new TicketPriceAddInfo();
                Object[] objects = (Object[]) stringList.get(i);
                List<String> descDetails = priceDao.findTicketPriceAddInfoDetail((BigInteger) objects[0]);
                info.setSubTitle((String) objects[1]);
                info.setDescDetails(descDetails);
                list.add(info);
            }
        }
        // 不是来自携程或者携程附加信息为空
        if ((ticketPrice.getCtripTicketId() == null || list.isEmpty()) && StringUtils.isNotBlank(ticketPrice.getOrderKnow())) {
            TicketPriceAddInfo info = new TicketPriceAddInfo();
            info.setSubTitle("预定须知");
            info.setDescDetails(Lists.newArrayList(ticketPrice.getOrderKnow()));
            list.add(info);
        }

        return list;
    }

    /**
     * 根据景点id查出当前日期有价格的门票列表
     *
     * @param scenicId 景点id
     * @return
     */
    public List<TicketPrice> findTicketPriceBy(Long scenicId) {
        return priceDao.findTicketPriceBy(scenicId);
    }
    /**
     * 根据景点id查出景点关联下的最小价格
     * @param scenicId
     * @return
     */
    public Float findTicketMinPriceBy(Long scenicId) {
        return priceDao.findTicketMinPriceBy(scenicId);
    }

    public Float findMinPriceByScenic(Long scenicId, Date startDate) {
        return datepriceDao.findMinPriceByScenic(scenicId, startDate);
    }

    public List<TicketPrice> findAvailablePriceList(Long ticketId, Page page, Date date, String... orderProperties) {

/*
        SELECT * FROM TicketPrice tp
        WHERE tp.ticket.id = 5000521
        AND EXISTS (SELECT 1 FROM TicketDateprice tdp WHERE tdp.ticketPriceId.id = tp.id
        AND tdp.huiDate >= '2016-11-10 00:00:00'
        );*/

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT tp FROM TicketPrice tp");
        List params = Lists.newArrayList();
        if (ticketId != null) {
            sb.append(" WHERE tp.ticket.id=? AND tp.status = 'UP'");
            params.add(ticketId);
            sb.append(" AND EXISTS (SELECT 1 FROM TicketDateprice tdp WHERE tdp.ticketPriceId.id = tp.id");
            if (date != null) {
                sb.append(" AND tdp.huiDate>?");
                params.add(date);
            }
            sb.append(") ");
        }
        if (orderProperties.length == 2) {
            if ("showOrder".equals(orderProperties[0])) {
                sb.append("order by IF(ISNULL(tp.showOrder),1,0),tp.showOrder");
            } else {
                sb.append("order by tp.").append(orderProperties[0]).append(" ").append(orderProperties[1]);
            }
        }
        return priceDao.findByHQL(sb.toString(), page, params.toArray());
    }

    /**
     * 临时数据
     * @param ticket
     * @return
     */
    public List<TicketPrice> findTempTicketPriceList(Ticket ticket) {
        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);
        criteria.eq("ticket.id", ticket.getId());
        criteria.isNotNull("originId");
        return priceDao.findByCriteria(criteria);
    }

    /**
     * 原始数据
     * @param ticket
     * @return
     */
    public List<TicketPrice> findOriginTicketPriceList(Ticket ticket) {
        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);
        criteria.eq("ticket.id", ticket.getId());
        criteria.isNull("originId");
        return priceDao.findByCriteria(criteria);
    }

    /**
     * 删除临时数据
     * @param ticket
     */
    public void deleteTempTicketPriceList(Ticket ticket) {
        List<TicketPrice> ticketPriceList = findTempTicketPriceList(ticket);
        for (TicketPrice ticketPrice : ticketPriceList) {
            productimageService.delImages(ticket.getId(), ticketPrice.getId(), "ticket/ticketPrice/");   //删除临时数据图片
        }
        priceDao.deleteAll(ticketPriceList);
    }


    /**
     * 操作所有票型
     * @param ticket
     */
    public void doHandleHotelPriceAll(Ticket ticket, TicketPriceStatus status) {
        List<TicketPrice> hotelPriceList = findOrginalTicketPriceList(ticket);
        for (TicketPrice hotelPrice : hotelPriceList) {
            if (status == TicketPriceStatus.DOWN) {
                hotelPrice.setShowStatus(ShowStatus.SHOW);
                hotelPrice.setStatus(TicketPriceStatus.DOWN);
            } else if (status == TicketPriceStatus.DEL) {
                hotelPrice.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
                hotelPrice.setStatus(TicketPriceStatus.DEL);
            }
            priceDao.update(hotelPrice);
        }
    }

    /**
     * 获取所有房型的原始数据
     * @param ticket
     * @return
     */
    public List<TicketPrice> findOrginalTicketPriceList(Ticket ticket) {
        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);
        criteria.eq("ticket.id", ticket.getId());
        criteria.isNull("originId");
        return priceDao.findByCriteria(criteria);
    }

    /**
     * 操作原始数据
     * @param ticket
     */
    public void doDownTicketPriceAll(Ticket ticket) {
        List<TicketPrice> TicketPriceList = findOriginTicketPriceList(ticket);
        for (TicketPrice TicketPrice : TicketPriceList) {
            TicketPrice.setShowStatus(ShowStatus.SHOW);
            TicketPrice.setStatus(TicketPriceStatus.DOWN);
            priceDao.update(TicketPrice);
        }
    }

    public List<TicketPrice> list(TicketPrice ticketPrice, Page pageInfo, String... orderProperties) {

        Criteria<TicketPrice> criteria = createTicketPriceCritera(ticketPrice, orderProperties);

        if (orderProperties.length == 2) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }

//        criteria.orderBy("modifyTime", "desc");
        if (pageInfo == null) {
            priceDao.findByCriteria(criteria);
        }
        return priceDao.findByCriteria(criteria, pageInfo);
    }

    private Criteria<TicketPrice> createTicketPriceCritera(TicketPrice ticketPrice,  String... orderProperties) {
        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);
        DetachedCriteria ticketCriteria = criteria.createCriteria("ticket", "ticket");
        ticketCriteria.createCriteria("companyUnit", "companyUnit");

        if (ticketPrice.getTicket() != null && ticketPrice.getTicket().getId() != null) {
            ticketCriteria.add(Restrictions.eq("id", ticketPrice.getTicket().getId()));
        }
        if (ticketPrice.getTicket() != null && ticketPrice.getTicket().getSource() != null) {
            ticketCriteria.add(Restrictions.eq("source", ticketPrice.getTicket().getSource()));
        }
        if (ticketPrice.getTicket() != null && ticketPrice.getTicket().getCompanyUnit() != null && ticketPrice.getTicket().getCompanyUnit().getId() != null) {
            ticketCriteria.add(Restrictions.eq("companyUnit.id", ticketPrice.getTicket().getCompanyUnit().getId()));
        }

        if (StringUtils.isNotBlank(ticketPrice.getTicketName())) {
            ticketCriteria.add(Restrictions.like("name", ticketPrice.getTicketName(), MatchMode.ANYWHERE));
        }

        if (ticketPrice.getTicket() != null && ticketPrice.getTicket().getTicketType() != null) {
            ticketCriteria.add(Restrictions.eq("ticketType", ticketPrice.getTicket().getTicketType()));
        }

        if (ticketPrice.getTicket() != null && ticketPrice.getTicket().getIncludeTicketTypeList() != null && !ticketPrice.getTicket().getIncludeTicketTypeList().isEmpty()) {
            Disjunction dis = Restrictions.disjunction();
            for (TicketType ticketType : ticketPrice.getTicket().getIncludeTicketTypeList()) {
                dis.add(Restrictions.eq("ticketType", ticketType));
            }
            ticketCriteria.add(dis);
        }

        if (ticketPrice.getShowStatus() != null) {
            criteria.eq("showStatus", ticketPrice.getShowStatus());
        }

        if (StringUtils.isNotBlank(ticketPrice.getName())) {
            criteria.like("name", ticketPrice.getName(), MatchMode.ANYWHERE);
        }

        if (ticketPrice.getStatus() != null) {
            criteria.eq("status", ticketPrice.getStatus());
        }



        criteria.ne("status", TicketPriceStatus.DEL);

        return criteria;
    }

    public List<TicketPrice> findByTicket(TicketPrice conditon, SysUnit companyUnit, Page page, String... orderProperties) {
        Criteria<TicketPrice> criteria = createTicketPriceCritera(conditon);
        List<TicketPriceStatus> notStatuses = Lists.newArrayList(TicketPriceStatus.DEL);
        criteria.notin("status", notStatuses);
        criteria.eq("showStatus", ShowStatus.SHOW);
        criteria.eq("companyUnit.id", companyUnit.getId());

        if (orderProperties.length == 2) {
            if (orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            }
        } else if (orderProperties.length == 1) {
            if (orderProperties[0] != null) {
                criteria.orderBy(Order.asc(orderProperties[0]));
            }
        }

        List<TicketPrice> ticketPriceList;
        if (page != null) {
            ticketPriceList = priceDao.findByCriteria(criteria, page);
        } else {
            ticketPriceList = priceDao.findByCriteria(criteria);
        }
        return ticketPriceList;
    }

    public List<TicketPrice> findByCompanyUnit(TicketPrice ticketPrice, SysUnit companyUnit, Page page) {
        Criteria<TicketPrice> criteria = createTicketPriceCritera(ticketPrice);
        List<TicketPriceStatus> notStatuses = Lists.newArrayList(TicketPriceStatus.DEL);
        criteria.notin("status", notStatuses);
        criteria.eq("showStatus", ShowStatus.SHOW);
        criteria.eq("companyUnit.id", companyUnit.getId());
        List<TicketPrice> ticketPriceList;
        if (page == null) {
            ticketPriceList = priceDao.findByCriteria(criteria);
        } else {
            ticketPriceList = priceDao.findByCriteria(criteria, page);
        }
        for (TicketPrice t : ticketPriceList) {
            t.setTelephone(t.getTicket().getCompanyUnit().getSysUnitDetail().getTelphone());
            t.setContactName(t.getTicket().getCompanyUnit().getSysUnitDetail().getContactName());
            t.setTicketType(t.getTicket().getTicketType().name());
        }
        return ticketPriceList;
    }

    public void delete(TicketPrice ticketPrice) {
        priceDao.delete(ticketPrice);
    }

    public List<TicketPrice> findMinTicketPriceByTicketId(Long ticketId, Page page) {

//        select tp.id as id, tp.name as name, min(tdp.priPrice) as discountPrice from ticketprice tp left join ticketdateprice tdp on tdp.ticketPriceId = tp.id where tp.ticketId = 5001046 and tdp.huiDate > '2016-11-11 00:00:00' group by tp.id;

        List params = Lists.newArrayList();
        StringBuffer sb = new StringBuffer("select tp.id as id, tp.name as name, min(tdp.priPrice) as discountPrice, tp.showOrder as showOrder from ticketprice tp left join ticketdateprice tdp on tdp.ticketPriceId = tp.id");
        sb.append(" where tp.ticketId =?");
        params.add(ticketId);
        sb.append(" and tdp.huiDate > '");
        sb.append(DateUtils.format(DateUtils.getStartDay(new Date(), 0), "yyyy-MM-dd HH:mm:ss").toString());
        sb.append("'");
        sb.append(" group by tp.id order by showOrder asc");
        return priceDao.findEntitiesBySQL3(sb.toString(), page, TicketPrice.class, params.toArray());
    }
}
