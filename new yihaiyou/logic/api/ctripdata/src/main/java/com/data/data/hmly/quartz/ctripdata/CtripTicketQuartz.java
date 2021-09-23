package com.data.data.hmly.quartz.ctripdata;

import com.data.data.hmly.service.ctripticket.CtripTicketService;
import com.data.data.hmly.service.ctripticket.entity.CtripTicketResource;
import com.data.data.hmly.service.ctripticket.entity.RowStatus;
import com.data.data.hmly.service.ctripticket.pojo.resp.ScenicSpotSearchResponseType;
import com.zuipin.util.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 门票数据同步
 * @author caiys
 * @date 2015年12月29日 上午10:13:50
 */
@Deprecated
public class CtripTicketQuartz {
//	@Resource
	private CtripTicketService ctripTicketService;
	
	public void ticketTask() {
		Date startDate = new Date();
//		String startDateStr = DateUtils.format(startDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		String startDateStr = DateUtils.format(startDate, "yyyy-MM-dd'T'00:00:00.000000+08:00");
		Date endDate = DateUtils.add(startDate, Calendar.DAY_OF_MONTH, 15);	// 默认15天
		String endDateStr = DateUtils.format(endDate, "yyyy-MM-dd'T'23:59:59.999999+08:00");
		updateTicketSenicSpot();
		updateTicketPrice(startDateStr, endDateStr);
		ctripTicketService.updateTicket();
		ctripTicketService.updateTicketDatePrice(startDateStr, endDateStr);
	}
	
	/**
	 * 更新门票数据
	 * @author caiys
	 * @date 2015年12月29日 上午10:22:09
	 */
	private void updateTicketSenicSpot() {
		Integer pageIndex = 1;
		Integer pageSize = 50;
		String keyword = "";
		Integer saleCityID = 0;	// 无效请求参数，只用于本地数据更新
		ctripTicketService.updateScenicStatus(RowStatus.DELETE, saleCityID);
		ScenicSpotSearchResponseType scenicSpotSearchResponse = null;
		// 如果不止一页，循环更新
		do {
			int time = 1;	// 控制出错时，尝试次数
			int limit = 1;
			while (time <= limit) {
				try {
					if (scenicSpotSearchResponse == null) {
						scenicSpotSearchResponse = ctripTicketService.updateTicketSenicSpot(pageIndex, pageSize, keyword, saleCityID);
					} else {
						ctripTicketService.updateTicketSenicSpot(pageIndex, pageSize, keyword, saleCityID);
					}
					time ++;
					limit = 1;
				} catch (Exception e) {
					e.printStackTrace();
					time ++;
					if (limit <= 1) {
						limit = 3;
					}
				}
			}
			pageIndex ++;
		} while (pageIndex <= scenicSpotSearchResponse.getPagingCount());
		System.out.println("应更新记录数："+scenicSpotSearchResponse.getRowCount());
	}
	
	/**
	 * 更新门票价格库
	 * @author caiys
	 * @date 2015年12月29日 上午11:09:37
	 */
	private void updateTicketPrice(String startDateStr, String endDateStr) {
		Integer pageIndex = 1;
		Integer pageSize = 50;
		Integer saleCityID = 0;
		int count = ctripTicketService.countCtripTicketResourceBy(RowStatus.DELETE, saleCityID);
		int pageCount = count/pageSize;
		if (count%pageSize != 0) {
			pageCount = pageCount + 1;
		}
		int updateCount = 0;
		// 如果不止一页，循环更新
		do {
			List<CtripTicketResource> list = ctripTicketService.listCtripTicketResourceBy(pageIndex, pageSize, RowStatus.DELETE, saleCityID);
			for (CtripTicketResource r : list) {
				// Integer productId = 1920689;
				// Integer rescourceId = 1785921;
				updateCount += updateTicketPriceCircul(r.getTicketId(), r.getId(), startDateStr, endDateStr);
			}
			pageIndex ++;
		} while (pageIndex <= pageCount);
		System.out.println("更新记录数："+updateCount);
	}
	
	/**
	 * 出错时尝试循环执行
	 * @author caiys
	 * @date 2015年12月29日 上午11:08:53
	 * @param productId
	 * @param rescourceId
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	private int updateTicketPriceCircul(Integer productId, Integer rescourceId, String startDateStr, String endDateStr) {
		int time = 1;	// 控制出错时，尝试次数
		int limit = 1;
		int updateCount = 0;
		while (time <= limit) {
			try {
				updateCount += ctripTicketService.updateTicketPrice(productId, rescourceId, startDateStr, endDateStr);
				time ++;
				limit = 1;
			} catch (Exception e) {
				e.printStackTrace();
				time ++;
				if (limit <= 1) {
					limit = 3;
				}
			}
		}
		return updateCount;
	}

}
