package com.data.data.hmly.service.ctripticket.test;

import com.data.data.hmly.service.ctripticket.CtripTicketService;
import com.data.data.hmly.service.ctripticket.entity.CtripTicketResource;
import com.data.data.hmly.service.ctripticket.entity.RowStatus;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpClientDTO;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpClientInfoList;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpMasterTicketDTO;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpProductInfo;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpTicketInfo;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpTicketOrderCreateRequest;
import com.data.data.hmly.service.ctripticket.pojo.resp.ScenicSpotSearchResponseType;
import com.data.data.hmly.service.ctripticket.pojo.resp.TocbResourceCanBookingResponse;
import com.data.data.hmly.service.ctripuser.CtripUserService;
import com.zuipin.util.DateUtils;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Ignore
public class CtripTicketServiceTest extends TestCase {
	private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
	private final CtripTicketService ctripTicketService = (CtripTicketService) applicationContext.getBean("ctripTicketService");
	private final CtripUserService ctripUserService = (CtripUserService) applicationContext.getBean("ctripUserService");

	@Override
	protected void tearDown() throws Exception {
		((ClassPathXmlApplicationContext) applicationContext).close();
	}

	/**
	 * 测试携程门票景点数据获取
	 * 
	 * @author caiys
	 * @date 2015年12月3日 下午1:43:29
	 */
	/*public void atestGetTicketSenicSpotSearch() {
		Integer pageIndex = 1;
		Integer pageSize = 20;
		String keyword = "北京";
		Integer saleCityID = 21;	// 无效请求参数，只用于本地数据更新
		ctripTicketService.getTicketSenicSpotSearch(pageIndex, pageSize, keyword, saleCityID);
	}*/

	/**
	 * 测试门票景点数据更新
	 * 
	 * @author caiys
	 * @date 2015年12月3日 下午1:44:20
	 */
	public void xtestUpdateTicketSenicSpot() {
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
	 * 测试携程门票资源价格库获取
	 * 
	 * @author caiys
	 * @date 2015年12月3日 下午1:43:29
	 */
	public void atestGetTicketResourcePriceAndInventory() {
		Integer productId = 1601149;
		Integer rescourceId = 1601153;
		Date startDate = DateUtils.date(2015, 11, 10);
		String startDateStr = DateUtils.format(startDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		Date endDate = DateUtils.date(2015, 11, 12);
		String endDateStr = DateUtils.format(endDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		ctripTicketService.getTicketResourcePriceAndInventory(productId, rescourceId, startDateStr, endDateStr);
	}

	/**
	 * 测试携程门票资源价格库更新
	 * 
	 * @author caiys
	 * @date 2015年12月3日 下午1:43:29
	 */
	public void atestUpdateTicketPrice() {
		Integer productId = 1773382;
		Integer rescourceId = 2057462;
		Date startDate = DateUtils.date(2015, 11, 10);
		String startDateStr = DateUtils.format(startDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		Date endDate = DateUtils.date(2015, 11, 12);
		String endDateStr = DateUtils.format(endDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		ctripTicketService.updateTicketPrice(productId, rescourceId, startDateStr, endDateStr);
	}

	/**
	 * 测试携程门票资源价格库更新（批量）
	 * 
	 * @author caiys
	 * @date 2015年12月4日 上午9:11:41
	 */
	public void testGetTicketResourcePriceAndInventory() {
		Integer pageIndex = 1;
		Integer pageSize = 50;
		Integer saleCityID = 0;
		int count = ctripTicketService.countCtripTicketResourceBy(RowStatus.DELETE, saleCityID);
		int pageCount = count/pageSize;
		if (count%pageSize != 0) {
			pageCount = pageCount + 1;
		}
		int updateCount = 0;
		Date startDate = new Date();
//		String startDateStr = DateUtils.format(startDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		String startDateStr = "2016-01-30T00:00:00.000000+08:00";
		Date endDate = DateUtils.add(startDate, Calendar.DAY_OF_MONTH, 15);	// 默认15天
		String endDateStr = DateUtils.format(endDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
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

	/**
	 * 测试门票可订性检查
	 * 
	 * @author caiys
	 * @date 2015年12月3日 下午3:26:38
	 */
	public void atestCheckTicketOrderCanBooking() {
		Integer orderTotalPrice = 220;
		Integer totalPrice = 220;	// 市场价
		Integer rescourceId = 1601468;
		Date useDate = DateUtils.date(2015, 11, 12);
		String useDateStr = DateUtils.format(useDate, "yyyy-MM-dd'T'HH:mm:ss+08:00");
		Integer useQuantity = 1;
		TocbResourceCanBookingResponse resourceCanBookingResponse = ctripTicketService.checkTicketOrderCanBooking(orderTotalPrice, totalPrice, rescourceId,
				useDateStr, useQuantity);
		System.out.println(resourceCanBookingResponse.isCode());
	}
	
	/**
	 * 测试门票订单创建
	 * @author caiys
	 * @throws Exception 
	 * @date 2015年12月3日 下午3:26:38
	 */
	public void atestCreateCtripTicketOrderPrepay() throws Exception {
		TocfpTicketOrderCreateRequest tocfpTicketOrderCreateRequest = new TocfpTicketOrderCreateRequest();
		tocfpTicketOrderCreateRequest.setAdultNumber(1);
		tocfpTicketOrderCreateRequest.setChildNumber(0);// 默认0
		tocfpTicketOrderCreateRequest.setAmount(220);
		tocfpTicketOrderCreateRequest.setDistributorOrderID("123123");
		tocfpTicketOrderCreateRequest.setRemark("特殊说明");
		tocfpTicketOrderCreateRequest.setSalesCity(21);	// 厦门
		String uid = ctripUserService.doGetUniqueUid("admin");
		tocfpTicketOrderCreateRequest.setUid(uid);
		// 联系人信息（加上后提示“错误的请求”）
		/*TocfpContactInfo tocfpContactInfo = new TocfpContactInfo();
		tocfpContactInfo.setContactEmail("test@test.com");
		tocfpContactInfo.setContactMobile("13511111111");
		tocfpContactInfo.setContactName("测试");
		tocfpTicketOrderCreateRequest.setContactInfo(tocfpContactInfo);*/
		// 产品ID
		TocfpProductInfo tocfpProductInfo = new TocfpProductInfo();
		tocfpProductInfo.setProductID(1601442);
		tocfpTicketOrderCreateRequest.setProductInfo(tocfpProductInfo);
		// 游客信息
		TocfpClientDTO tocfpClientDTO = new TocfpClientDTO();
		tocfpClientDTO.setAgeType("ADU");
		tocfpClientDTO.setBirthDate("1990-05-01T00:00:00");
		tocfpClientDTO.setClientName("测试");
		tocfpClientDTO.setClientNameE("test");
		tocfpClientDTO.setContactInfo("13511111111");
		tocfpClientDTO.setGender("0");
		tocfpClientDTO.setHzDate("0001-01-01T00:00:00");
		tocfpClientDTO.setHzNo("123022323");
		tocfpClientDTO.setIDCardNo("123423432");
		tocfpClientDTO.setIDCardTimelimit("0001-01-01T00:00:00");
		tocfpClientDTO.setIDCardType(2);
		tocfpClientDTO.setInfoID(0);
		tocfpClientDTO.setIssueDate("0001-01-01T00:00:00");
		tocfpClientDTO.setPassportType("P");
		TocfpClientInfoList tocfpClientInfoList = new TocfpClientInfoList();
		List<TocfpClientDTO> clientDTO = new ArrayList<TocfpClientDTO>();
		clientDTO.add(tocfpClientDTO);
		tocfpClientInfoList.setClientDTO(clientDTO);
		tocfpTicketOrderCreateRequest.setClientInfoList(tocfpClientInfoList);
		// 门票信息
		TocfpMasterTicketDTO tocfpMasterTicketDTO = new TocfpMasterTicketDTO();
		tocfpMasterTicketDTO.setPrice(220);
		tocfpMasterTicketDTO.setTicketID(1601468);
		tocfpMasterTicketDTO.setUseDate("2015-12-12T13:20:13.5893645+08:00");
		tocfpMasterTicketDTO.setQuantity(1);
		tocfpMasterTicketDTO.setTicketType("0");
		TocfpTicketInfo tocfpTicketInfo = new TocfpTicketInfo();
		List<TocfpMasterTicketDTO> masterTicketDTO = new ArrayList<TocfpMasterTicketDTO>();
		masterTicketDTO.add(tocfpMasterTicketDTO);
		tocfpTicketInfo.setMasterTicketDTO(masterTicketDTO);
		tocfpTicketOrderCreateRequest.setTicketInfo(tocfpTicketInfo);
		
		ctripTicketService.createCtripTicketOrderPrepay(tocfpTicketOrderCreateRequest);
	}
	
	/**
	 * 测试门票价格更新
	 * @author caiys
	 * @date 2015年12月29日 下午7:10:31
	 */
	public void xtestUpdateTicket() {
		Date startDate = new Date();
//		String startDateStr = DateUtils.format(startDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
		String startDateStr = DateUtils.format(startDate, "yyyy-MM-dd'T'00:00:00.000000+08:00");
		Date endDate = DateUtils.add(startDate, Calendar.DAY_OF_MONTH, 15);	// 默认15天
		String endDateStr = DateUtils.format(endDate, "yyyy-MM-dd'T'23:59:59.999999+08:00");
//		ctripTicketService.updateTicket();
		ctripTicketService.updateTicketDatePrice(startDateStr, endDateStr);
	}

}
