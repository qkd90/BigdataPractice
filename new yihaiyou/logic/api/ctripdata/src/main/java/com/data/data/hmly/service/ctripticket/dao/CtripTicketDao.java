package com.data.data.hmly.service.ctripticket.dao;

import com.data.data.hmly.service.ctripticket.entity.CtripTicket;
import com.data.data.hmly.service.ctripticket.entity.RowStatus;
import com.framework.hibernate.DataAccess;
import com.zuipin.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Repository
public class CtripTicketDao extends DataAccess<CtripTicket> {
	/**
	 * 批量更新行状态
	 * @author caiys
	 * @date 2015年12月3日 上午11:40:53
	 * @param rowStatus
	 */
	public void updateRowStatus(RowStatus rowStatus, Integer districtID) {
		List<Object> params = new ArrayList<Object>();
		String hql = "update CtripTicket ct set ct.rowStatus = ?, updateTime = now()";
		params.add(rowStatus);
		if (districtID != null && districtID > 0) {
			hql = hql + " where exists(select 1 from CtripScenicSpot css where ct.scenicSpotId = css.id and css.districtID = ?)";
			params.add(districtID);
		}
		updateByHQL(hql, params.toArray());
	}
	
	/**
	 * 更新携程同步的门票数据到本地数据结构
	 * 要求：scenic表的ctrip_scenic_id需已赋值
	 * @author caiys
	 * @date 2015年12月29日 下午5:25:42
	 */
	public void updateTicket() {
		// 更新product
		String sql = "update product p INNER JOIN (select ct.id, ct.name, ct.marketPrice, ct.rowStatus, s.city_code " +
				"from ctrip_ticket ct inner JOIN scenic s on ct.scenicSpotId = s.ctrip_scenic_id) t on t.id = p.ctripId " +
				"set p.name = t.name, p.price = t.marketPrice, p.status = (case when t.rowStatus = 'DELETE' then 'DEL' else p.status end), p.cityId = t.city_code where p.proType = 'scenic'";
		updateBySQL(sql);
		sql = "insert into product(name,price,status,userid,companyUnitId,proType,cityId,supplierId,topId,ctripId) " +
				"select ct.name, ct.marketPrice, (case when ct.rowStatus = 'DELETE' then 'DEL' else 'UP' end), 1, 1, 'scenic', s.city_code, null, LAST_INSERT_ID(), ct.id " +
				"from ctrip_ticket ct inner JOIN scenic s on ct.scenicSpotId = s.ctrip_scenic_id " +
				"where not exists (select 1 from product p where p.ctripId = ct.id and p.proType = 'scenic')";
		updateBySQL(sql);
		// 暂时找不到获取自动生成id的值，只能多添加更新操作
		sql = "update product p set p.topId = p.id where p.proType = 'scenic' and p.ctripId is not null and p.topId <> p.id";
		updateBySQL(sql);
		
		// 更新ticket
		sql = "update ticket t INNER JOIN (select ct.id, ct.name, css.address, css.commentUserCount, css.orderCount, (case when isNull(css.star) or trim(css.star) = '' then 'OA' else CONCAT(css.star,'A') end) as star " +  
				"from ctrip_ticket ct INNER JOIN ctrip_scenic_spot css on ct.scenicSpotId = css.id) tt on t.ctripTicketId = tt.id " +
				"set t.ticketName = tt.name, t.address = tt.address, t.popCounts = tt.commentUserCount, t.orderCounts = tt.orderCount, t.sceAji = tt.star, t.ctripSyncDate = now()";
		updateBySQL(sql);
		sql = "insert into ticket(productId,ticketType,ticketName,address,ticketImgUrl,agreeRule,payway,orderConfirm,validOrderDay,addTime,popCounts,orderCounts,scenicInfoId,sceAji,ctripTicketId,ctripSyncDate) " +
				"select p.id, 'scenic', ct.name, css.address, s.cover, '0', 'allpay', 'noconfirm', 15, now(), css.commentUserCount, css.orderCount, s.id, (case when isNull(css.star) or trim(css.star) = '' then 'OA' else CONCAT(css.star,'A') end) as star, ct.id, now() " +
				"from ctrip_ticket ct inner JOIN scenic s on ct.scenicSpotId = s.ctrip_scenic_id INNER JOIN ctrip_scenic_spot css on ct.scenicSpotId = css.id INNER JOIN product p on ct.id = p.ctripId " +
				"where not exists (select 1 from ticket t where t.ctripTicketId = ct.id)";
		updateBySQL(sql);
		
		// 更新ticketprice
		sql = "update ticketprice tp INNER JOIN (select ctr.id, ctr.name, ctr.marketPrice-ctr.price as commission, ctr.marketPrice " +
				"from ctrip_ticket_resource ctr ) t on tp.ctripTicketResourceId = t.id " +
				"set tp.name = t.name, tp.commission = t.commission, tp.discountPrice = t.marketPrice, tp.ctripSyncDate = now()";
		updateBySQL(sql);
		sql = "insert into ticketprice(name,type,getTicket,commission,ticketId,userid,addTime,discountPrice,status,ctripTicketResourceId, ctripSyncDate) " +
				"select ctr.name, 'other', 'otherget', ctr.marketPrice-ctr.price as commission, t.productId, 1, now(), ctr.marketPrice, (case when ctr.rowStatus = 'DELETE' then 0 else 1 end), ctr.id, now() " +
				"from ctrip_ticket_resource ctr INNER JOIN ticket t on ctr.ticketId = t.ctripTicketId " +
				"where not exists (select 1 from ticketprice tp where tp.ctripTicketResourceId = ctr.id)";
		updateBySQL(sql);
	}
	
	/**
	 * 更新携程同步的门票价格数据到本地数据结构
	 * @author caiys
	 * @date 2015年12月29日 下午5:56:25
	 * @param startDateStr
	 * @param endDateStr
	 */
	public void updateTicketDatePrice(String startDateStr, String endDateStr) {
		Date startDate = DateUtils.getDate(startDateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
    	Date endDate = DateUtils.getDate(endDateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		// 更新ticketdateprice
		String sql = "delete from ticketdateprice where huiDate >= ? and huiDate <= ? and ctripTicketPriceDailyId is not null";
		updateBySQL(sql, startDate, endDate);
		sql = "insert into ticketdateprice(ticketPriceId, huiDate, priPrice, rebate, ctripTicketPriceDailyId, ctripSyncDate) " +
				"select tp.id, ctpd.date, ctpd.marketPrice, ctpd.marketPrice-price, ctpd.id, now() " +
				"from ctrip_ticket_price_daily ctpd INNER JOIN ticketprice tp on ctpd.ticketResourceId = tp.ctripTicketResourceId " +
				"where ctpd.date >= ? and ctpd.date <= ?";
		updateBySQL(sql, startDate, endDate);
	}
	
}
