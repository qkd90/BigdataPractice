package com.data.data.hmly.service.ticket.dao;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TicketPriceDao extends DataAccess<TicketPrice> {

    /**
     * 查询景点门票，线路组合使用
     */
    public List<TicketPrice> listTicketPriceForLine(TicketPrice ticketPrice, Page page, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new TicketPrice(tp.id, t.scenicInfo.id, t.name, tp.name, tp.minDiscountPrice) ");
        hql.append("from TicketPrice tp inner join tp.ticket t where 1 = 1 ");
        hql.append("and t.status = :ticketStatus ");
        params.put("ticketStatus", ProductStatus.UP);
//        hql.append("and t.source is null ");
        if (ticketPrice.getScenicId() != null) {
            hql.append("and t.scenicInfo.id = :scenicId ");
            params.put("scenicId", ticketPrice.getScenicId());
        }
        if (StringUtils.isNotBlank(ticketPrice.getTicketName())) {
            hql.append("and t.name like '%'||:ticketName||'%' ");
            params.put("ticketName", ticketPrice.getTicketName());
        }
        // 排除已经被选择的记录
        hql.append("and not exists(select 1 from LinedaysProductPrice lp where lp.productType = :productType and lp.linedays.id = :linedaysId and lp.priceId = tp.id) ");
        params.put("linedaysId", ticketPrice.getLinedaysId());
        params.put("productType", ProductType.scenic);
        return findByHQL2ForNew(hql.toString(), page, params);
    }


    public void delPrice(Ticket ticket) {


    }


    /**
     * 获取类别报价中最小优惠价，日期范围第二天和下个月最后一天
     *
     * @param tickettypepriceId
     * @param dateStart
     * @param dateEnd
     * @return
     * @author caiys
     * @date 2015年10月21日 下午3:50:34
     */
    public Float findMinValue(Long tickettypepriceId, Date dateStart, Date dateEnd, String prop) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        criteria.eq("ticketPriceId.id", tickettypepriceId);
        criteria.ge("huiDate", dateStart);
        criteria.le("huiDate", dateEnd);
        criteria.setProjection(Projections.min(prop));
        return (Float) findUniqueCriteria(criteria);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object findUniqueCriteria(final Criteria<TicketDateprice> criteria) {
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                List<Object> list = hibernateCriteria.list();
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        });
    }

    public List<TicketPrice> findTickettypepriceList(TicketPrice ticketPrice) {

        Criteria<TicketPrice> criteria = new Criteria<TicketPrice>(TicketPrice.class);
        criteria.createCriteria("ticket", "ticket", JoinType.INNER_JOIN);
        foramtCond(ticketPrice, criteria);
        criteria.orderBy("discountPrice", "asc");
        criteria.isNotNull("type");
        if (ticketPrice.getTicket() != null && ticketPrice.getTicket().getScenicInfo() != null) {
            criteria.eq("ticket.scenicInfo.id", ticketPrice.getTicket().getScenicInfo().getId());
            criteria.eq("ticket.status", ticketPrice.getTicket().getStatus());
        }
        return findByCriteria(criteria);

    }

    /**
     * 根据景点id查出景点门票的TicketPrice类型
     *
     * @param scenicId 景点id
     * @return
     */
    public List<TicketPrice> findTicketPriceTypeByScenicId(Long scenicId) {
        String hql = "select new TicketPrice(tp.type) from TicketPrice tp, Ticket t where tp.ticket.id=t.id and"
                + " tp.ticket.status='UP' and t.scenicInfo.id=? and type is not null group by tp.type order by tp.discountPrice asc";
        return findByHQL(hql, scenicId);
    }

    /**
     * 功能描述：查询条件拼接
     *
     * @param ticketPrice
     * @param criteria
     * @author caiys
     * @date 2015年10月16日 上午9:10:28
     */
    public void foramtCond(TicketPrice ticketPrice, Criteria<TicketPrice> criteria) {
        // 线路标识
        if (ticketPrice.getTicket() != null && ticketPrice.getTicket().getId() != null) {
            criteria.eq("ticket.id", ticketPrice.getTicket().getId());
        }
    }

    public List<Object> findTicketPriceAddInfo(TicketPrice ticketPrice) {
        String sql = "select a.id,a.subTitle from nctrip_scenic_spot_resource r,nctrip_resource_addinfo a "
                + "where r.ctripResourceId = " + ticketPrice.getCtripResourceId() + " and r.productId = "
                + ticketPrice.getCtripTicketId() + " and a.resourceId = r.id ";
        return findBySQL(sql);
    }

    public List<String> findTicketPriceAddInfoDetail(BigInteger id) {
        String sql = "select d.descDetail from nctrip_addinfo_detail d where d.targetType = 'TICKET_RESCOURCE' and d.targetId = " + id;
        return findBySQL(sql);
    }

    /**
     * 根据景点id查出当前日期有价格的门票列表
     *
     * @param scenicId 景点id
     * @return
     */
    public List<TicketPrice> findTicketPriceBy(Long scenicId) {
        String hql = "select tp from TicketPrice tp, Ticket t where tp.ticket.id = t.id "
                + "and t.status='UP' and t.scenicInfo.id = ? and type is not null and tp.status = 'UP' "
                + "and exists (select 1 from TicketDateprice d where d.ticketPriceId = tp.id and d.huiDate > ?) "
                + "order by tp.showOrder asc";
        return findByHQL(hql, scenicId, new Date());
    }

    /**
     * 根据景点id查出景点关联下的最小价格
     * @param scenicId
     * @return
     */
    public Float findTicketMinPriceBy(Long scenicId) {
        String hql = "select min(tp.discountPrice) from TicketPrice tp, Ticket t where tp.ticket.id = t.id "
                + "and t.status='UP' and t.scenicInfo.id = ? and type is not null "
                + "and exists (select 1 from TicketDateprice d where d.ticketPriceId = tp.id) "
                + "order by tp.discountPrice asc";

        Double result = findDoubleByHQL(hql, scenicId);
        return result.floatValue();
    }
}
