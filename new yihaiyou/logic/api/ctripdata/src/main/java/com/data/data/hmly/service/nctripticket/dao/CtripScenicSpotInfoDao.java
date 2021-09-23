package com.data.data.hmly.service.nctripticket.dao;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.ctripcommon.enums.AddInfoDetailType;
import com.data.data.hmly.service.ctripcommon.enums.TagTargetType;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotInfo;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/2/1.
 */
@Repository
public class CtripScenicSpotInfoDao extends DataAccess<CtripScenicSpotInfo> {

    /**
     * 查询区域景点的携程标识
     */
    public List<Long> findCtripScenicIdList(Long cityId) {
        // 中国城市范围id处理
        cityId = cityId / 100 * 100; // 后两位为00
        Long endId = cityId + 99;

        StringBuffer sb = new StringBuffer();
        sb.append("select distinct s.ctripScenicId from ScenicInfo s where s.ctripScenicId is not null ");
        sb.append(" and s.city.id >= ? ");
        sb.append(" and s.city.id <= ? ");
        List<Object> ctripScenicIdList = findByHQL(sb.toString(), cityId, endId);
        List<Long> ctripScenicIds = new ArrayList<Long>();
        for (Object id : ctripScenicIdList) {
            ctripScenicIds.add(((Integer) id).longValue());
        }
        return ctripScenicIds;
    }

    /**
     * 删除景点及相关子表信息
     *
     * @param scenicSpotId
     */
    public void deleteRelationInfo(Long scenicSpotId) {
        // 删除景点标签
        String hql = " delete CtripDisplayTag t where exists(select 1 from CtripDisplayTagGroup tg where tg.id = t.displayTagGroupId and tg.targetType = ? and tg.targetId = ?) ";
        updateByHQL(hql, TagTargetType.SCENIC, scenicSpotId);

        // 删除景点标签分组
        hql = " delete CtripDisplayTagGroup tg where tg.targetType = ? and tg.targetId = ? ";
        updateByHQL(hql, TagTargetType.SCENIC, scenicSpotId);

        // 删除poi信息
        hql = " delete CtripScenicSpotPoiInfo t where t.id = ? ";
        updateByHQL(hql, scenicSpotId);

        // 删除城市信息
        hql = " delete CtripScenicSpotCityInfo t where t.id = ? ";
        updateByHQL(hql, scenicSpotId);

        // 删除门票资源标签
        hql = " delete CtripDisplayTag t where exists(select 1 from CtripScenicSpotResource ssr, CtripDisplayTagGroup tg where ssr.id = tg.targetId and tg.id = t.displayTagGroupId and tg.targetType = ? and ssr.productId = ?) ";
        updateByHQL(hql, TagTargetType.TICKET_RESCOURCE, scenicSpotId);

        // 删除门票标签分组
        hql = " delete CtripDisplayTagGroup tg where exists(select 1 from CtripScenicSpotResource ssr where ssr.id = tg.targetId and tg.targetType = ? and ssr.productId = ?) ";
        updateByHQL(hql, TagTargetType.TICKET_RESCOURCE, scenicSpotId);

        // 附加信息描述删除速度太慢，采用先查询标识再进行删除
        hql = " select rai.id from CtripScenicSpotResource ssr, CtripResourceAddInfo rai where ssr.id = rai.resourceId and ssr.productId = ? ";
        List<Object> resourceAddInfoIds = findByHQL(hql, scenicSpotId);
        for (Object addInfoId : resourceAddInfoIds) {
            // 删除门票资源附加信息描述
            hql = " delete CtripAddInfoDetail aid where aid.targetType = ? and aid.targetId = ? ";
            updateByHQL(hql, AddInfoDetailType.TICKET_RESCOURCE, addInfoId);
            // 删除门票资源附加信息
            hql = " delete CtripResourceAddInfo rai where rai.id = ? ";
            updateByHQL(hql, addInfoId);
        }

        // 删除门票资源
        hql = " delete CtripScenicSpotResource t where t.productId = ? ";
        updateByHQL(hql, scenicSpotId);

        // 附加信息描述删除速度太慢，采用先查询标识再进行删除
        hql = " select pai.id from CtripProductAddInfo pai where pai.productId = ? ";
        List<Object> productAddInfoIds = findByHQL(hql, scenicSpotId);
        for (Object addInfoId : productAddInfoIds) {
            // 删除景点附加信息描述
            hql = " delete CtripAddInfoDetail aid where aid.targetType = ? and aid.targetId = ? ";
            updateByHQL(hql, AddInfoDetailType.PRODUCT, addInfoId);
            // 删除景点附加信息
            hql = " delete CtripProductAddInfo t where t.id = ? ";
            updateByHQL(hql, addInfoId);
        }

        // 删除产品信息
        hql = " delete CtripScenicSpotProduct t where t.id = ? ";
        updateByHQL(hql, scenicSpotId);

        // 删除景点信息
        hql = " delete CtripScenicSpotInfo t where t.id = ? ";
        updateByHQL(hql, scenicSpotId);

    }

    /**
     * 更新携程同步的门票数据到本地数据结构
     * 要求：scenic表的ctrip_scenic_id需已赋值
     *
     * @author caiys
     * @date 2015年12月29日 下午5:25:42
     */
    public void updateTicket() {
        // 更新product
        String sql = "update product p INNER JOIN (select cs.id, cs.name, cs.marketPrice, cs.rowStatus, s.city_code "
                + " from nctrip_scenic_spot_info cs inner JOIN scenic s on cs.id = s.ctrip_scenic_id) t on t.id = p.ctripId "
                + " set p.name = t.name, p.price = t.marketPrice, p.status = (case when t.rowStatus = 'DELETE' then 'DEL' else p.status end), p.cityId = t.city_code where p.proType = 'scenic'";
        updateBySQL(sql);
        sql = "insert into product(name,price,status,userid,companyUnitId,proType,cityId,supplierId,topId,ctripId,source,sourceId,needConfirm,showOrder) "
                + " select cs.name, cs.marketPrice, (case when cs.rowStatus = 'DELETE' then 'DEL' else 'UP' end), 1, 1, 'scenic', s.city_code, null, LAST_INSERT_ID(), cs.id, '" + ProductSource.CTRIP.toString() + "', cs.id, 0, 999 "
                + " from nctrip_scenic_spot_info cs inner JOIN scenic s on cs.id = s.ctrip_scenic_id "
                + " where not exists (select 1 from product p where p.ctripId = cs.id and p.proType = 'scenic')";
        updateBySQL(sql);
        // topId赋值，暂时找不到获取自动生成id的值，只能多添加更新操作
        sql = "update product p set p.topId = p.id where p.proType = 'scenic' and p.ctripId is not null and p.topId <> p.id";
        updateBySQL(sql);

        // 更新ticket
        sql = "update ticket t INNER JOIN nctrip_scenic_spot_info cs on t.ctripTicketId = cs.id "
                + " set t.ticketName = cs.name, t.address = SUBSTRING(cs.address,1,200), "
                + " t.sceAji = (case when isNull(cs.star) or trim(cs.star) = '' then '0A' else CONCAT(cs.star,'A') end), t.ctripSyncDate = now() ";
        updateBySQL(sql);
        sql = "insert into ticket(productId,ticketType,ticketName,address,ticketImgUrl,agreeRule,payway,orderConfirm,validOrderDay,addTime,scenicInfoId,sceAji,ctripTicketId,ctripSyncDate) "
                + " select p.id, 'scenic', cs.name, SUBSTRING(cs.address,1,200) as address, s.cover, '0', 'allpay', 'noconfirm', 15, now(), s.id, (case when isNull(cs.star) or trim(cs.star) = '' then '0A' else CONCAT(cs.star,'A') end) as star, cs.id, now() "
                + " from nctrip_scenic_spot_info cs inner JOIN scenic s on cs.id = s.ctrip_scenic_id INNER JOIN product p on cs.id = p.ctripId "
                + " where not exists (select 1 from ticket t where t.ctripTicketId = cs.id)";
        updateBySQL(sql);

        // 更新ticketprice
        sql = "update ticketprice tp INNER JOIN nctrip_scenic_spot_resource ssr on tp.ctripResourceId = ssr.ctripResourceId and tp.ctripTicketId = ssr.productId "
                + "set tp.name = ssr.name, tp.maketPrice = ssr.marketPrice, tp.discountPrice = ssr.ctripPrice, tp.price = ssr.price, "
                + "tp.type = (case when ssr.ticketType = 4 then 'taopiao' when ssr.peopleGroup = '1' then 'adult' when ssr.peopleGroup = '2' then 'child' when ssr.peopleGroup = '4' then 'student' "
                + "when ssr.peopleGroup = '8' then 'oldman' when ssr.peopleGroup = '32' then 'team' else 'other' end), "
                + "tp.ctripTicketResourceId = ssr.id, tp.ctripSyncDate = now(), tp.advanceBookingDays = ssr.advanceBookingDays, tp.advanceBookingTime = ssr.advanceBookingTime";
        updateBySQL(sql);
        sql = "insert into ticketprice(name,type,getTicket,maketPrice,ticketId,userid,addTime,discountPrice,status,ctripTicketResourceId, ctripSyncDate, ctripResourceId, ctripTicketId, price, advanceBookingDays, advanceBookingTime, showOrder) "
                + " select ssr.name, (case when ssr.ticketType = 4 then 'taopiao' when ssr.peopleGroup = '1' then 'adult' when ssr.peopleGroup = '2' then 'child' when ssr.peopleGroup = '4' then 'student' "
                + " when ssr.peopleGroup = '8' then 'oldman' when ssr.peopleGroup = '32' then 'team' else 'other' end) as peopleGroup, 'messageget', "
                + " ssr.marketPrice, t.productId, 1, now(), ssr.ctripPrice, (case when ssr.rowStatus = 'DELETE' then 'DEL' else 'UP' end), ssr.id, now(), ssr.ctripResourceId, ssr.productId, ssr.price, ssr.advanceBookingDays, ssr.advanceBookingTime, 999 "
                + " from nctrip_scenic_spot_resource ssr INNER JOIN ticket t on ssr.productId = t.ctripTicketId "
                + " where not exists (select 1 from ticketprice tp where tp.ctripResourceId = ssr.ctripResourceId and tp.ctripTicketId = ssr.productId)";
        updateBySQL(sql);//where not exists (select 1 from ticketprice tp where tp.ctripTicketId = ssr.ctripResourceId and tp.ctripTicketId = ssr.productId)";
    }

    /**
     * 更新携程同步的门票价格数据到本地数据结构
     *
     * @author caiys
     * @date 2015年12月29日 下午5:56:25
     */
    public void updateTicketDatePrice(Date startDate, Date endDate) {
        // 更新ticketdateprice
        String sql = "delete from ticketdateprice where huiDate >= ? and huiDate <= ? and ctripTicketPriceDailyId is not null";
        updateBySQL(sql, startDate, endDate);
        sql = "insert into ticketdateprice(ticketPriceId, huiDate, priPrice, ctripTicketPriceDailyId, ctripSyncDate, maketPrice, price) "
                + "select tp.id, rpc.priceDate, rpc.ctripPrice, rpc.id, now(), rpc.marketPrice, rpc.price "
                + "from nctrip_resource_price_calendar rpc INNER JOIN ticketprice tp on rpc.resourceId = tp.ctripResourceId "
                + "where rpc.priceDate >= ? and rpc.priceDate <= ?";
        updateBySQL(sql, startDate, endDate);//from nctrip_resource_price_calendar rpc INNER JOIN ticketprice tp on rpc.resourceId = tp.ctripTicketId"

        updateBySQL("update ticketprice set status = 'UP'");
        // 如果价格日历已经在当天之前，状态设置为下架
        Date today = new Date();
        today = DateUtils.getDate(DateUtils.format(today, "yyyyMMdd"), "yyyyMMdd");
        sql = "update ticketprice tp set tp.status = ? where not exists ( "
                + "select 1 from ticketdateprice d where d.ticketPriceId = tp.id and d.huiDate >= ?) ";
        updateBySQL(sql, "DOWN", today);

        // 更新ticketprice最小价格字段minDiscountPrice
        sql = "update ticketprice tp INNER JOIN "
                + "(select ticketPriceId, min(priPrice) as minDiscountPrice from ticketdateprice "
                + "where huiDate >= date_sub(?,interval 1 day) and huiDate <= ? and ctripTicketPriceDailyId is not null "
                + "group by ticketPriceId) d on d.ticketPriceId = tp.id "
                + "set tp.minDiscountPrice = d.minDiscountPrice "
                + "where tp.status = ? ";
        updateBySQL(sql, startDate, endDate, "UP");
    }

}
