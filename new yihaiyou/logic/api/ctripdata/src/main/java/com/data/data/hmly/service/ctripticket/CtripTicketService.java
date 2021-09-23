package com.data.data.hmly.service.ctripticket;

import com.data.data.hmly.service.ctripticket.dao.CtripScenicSpotDao;
import com.data.data.hmly.service.ctripticket.dao.CtripTicketDao;
import com.data.data.hmly.service.ctripticket.dao.CtripTicketPreOrderClientInfoDao;
import com.data.data.hmly.service.ctripticket.dao.CtripTicketPreOrderDao;
import com.data.data.hmly.service.ctripticket.dao.CtripTicketPreOrderTicketInfoDao;
import com.data.data.hmly.service.ctripticket.dao.CtripTicketPriceDailyDao;
import com.data.data.hmly.service.ctripticket.dao.CtripTicketResourceDao;
import com.data.data.hmly.service.ctripticket.entity.CtripScenicSpot;
import com.data.data.hmly.service.ctripticket.entity.CtripTicket;
import com.data.data.hmly.service.ctripticket.entity.CtripTicketPreOrder;
import com.data.data.hmly.service.ctripticket.entity.CtripTicketPreOrderClientInfo;
import com.data.data.hmly.service.ctripticket.entity.CtripTicketPreOrderTicketInfo;
import com.data.data.hmly.service.ctripticket.entity.CtripTicketPriceDaily;
import com.data.data.hmly.service.ctripticket.entity.CtripTicketResource;
import com.data.data.hmly.service.ctripticket.entity.OrderStatus;
import com.data.data.hmly.service.ctripticket.entity.RowStatus;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpClientDTO;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpMasterTicketDTO;
import com.data.data.hmly.service.ctripticket.pojo.req.TocfpTicketOrderCreateRequest;
import com.data.data.hmly.service.ctripticket.pojo.resp.APIProtocolPackageType;
import com.data.data.hmly.service.ctripticket.pojo.resp.ProductListItemDTOType;
import com.data.data.hmly.service.ctripticket.pojo.resp.ResourceListItemDTOType;
import com.data.data.hmly.service.ctripticket.pojo.resp.ScenicSpotListItemDTOType;
import com.data.data.hmly.service.ctripticket.pojo.resp.ScenicSpotSearchResponseType;
import com.data.data.hmly.service.ctripticket.pojo.resp.TocbResourceCanBookingResponse;
import com.data.data.hmly.service.ctripticket.pojo.resp.TrpaiTicketPriceDailyDTO;
import com.data.data.hmly.service.ctripticket.pojo.resp.TrpaiTicketPriceSearchResponse;
import com.data.data.hmly.service.ctripticket.pojo.resp.TrpaiTicketPriceSearchResponseDetail;
import com.zuipin.util.DateUtils;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

//@Service
@Deprecated
public class CtripTicketService {
	private Log log = LogFactory.getLog(this.getClass());
    private static final String TICKET_URL = "http://openapi.ctrip.com/vacations/OpenServer.ashx";
	// 门票景点列表
    private static final String TICKET_INTERFACE = "TicketSenicSpotSearch";	
    // 门票资源价格库
    private static final String TICKET_PRICEANDINVENTORY_INTERFACE = "TicketResourcePriceAndInventory";	
    // 门票可订性
    private static final String TICKET_ORDERCANBOOKING_INTERFACE = "TicketOrderCanBooking";	
    // 创建门票订单
    private static final String TICKET_ORDER_PREPAY_INTERFACE = "TicketOrderCreateForPrepay_V2";	
    
	@Resource
	private CtripScenicSpotDao ctripScenicSpotDao;
	@Resource
	private CtripTicketDao ctripTicketDao;
	@Resource
	private CtripTicketResourceDao ctripTicketResourceDao;
	@Resource
	private CtripTicketPriceDailyDao ctripTicketPriceDailyDao;
	@Resource
	private CtripTicketPreOrderDao ctripTicketPreOrderDao;
	@Resource
	private CtripTicketPreOrderClientInfoDao ctripTicketPreOrderClientInfoDao;
	@Resource
	private CtripTicketPreOrderTicketInfoDao ctripTicketPreOrderTicketInfoDao;
	@Resource
	private PropertiesManager propertiesManager;
    
    /**
     * 查询携程门票景点
     * @author caiys
     * @date 2015年12月2日 下午9:42:20
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public ScenicSpotSearchResponseType getTicketSenicSpotSearch(Integer pageIndex, Integer pageSize, String keyword, Integer saleCityID) {
    	try {
    		StringBuilder paramSb = new StringBuilder();
    		paramSb.append("<ScenicSpotSearchRequest><DistributionChannel>9</DistributionChannel>");
    		if (pageIndex != null && pageSize != null) {
    			paramSb.append("<PagingParameter>");
    			paramSb.append("<PageIndex>").append(pageIndex).append("</PageIndex>");
    			paramSb.append("<PageSize>").append(pageSize).append("</PageSize>");
    			paramSb.append("</PagingParameter>");
    		}
    		if (StringUtils.isNotBlank(keyword) || saleCityID != null) {
    			paramSb.append("<SearchParameter>");
    			if (StringUtils.isNotBlank(keyword)) {
    				paramSb.append("<Keyword>").append(keyword).append("</Keyword>");
    			}
    			if (saleCityID != null) {
    				paramSb.append("<SaleCityID>").append(saleCityID).append("</SaleCityID>");
    			}
    			paramSb.append("</SearchParameter>");
    		}
    		paramSb.append("</ScenicSpotSearchRequest>");
    		String requestJson = buildRequestJson(TICKET_INTERFACE, paramSb.toString());
    		String resultStr = post(TICKET_URL, requestJson);
    		if (StringUtils.isNotBlank(resultStr)) {
    			// 处理返回结果
    			JAXBContext context = JAXBContext.newInstance(APIProtocolPackageType.class);
    			Unmarshaller unmarshaller = context.createUnmarshaller();
    			APIProtocolPackageType result = (APIProtocolPackageType) unmarshaller.unmarshal(new StringReader(resultStr));
    			if (Boolean.valueOf(result.getIsError())) {
    				log.error("错误信息："+result.getErrorMessage());
    				return null;
    			}
    			String responseBody = result.getResponseBody();
    			// 处理返回结果中内嵌的xml
//    			responseBody = StringEscapeUtils.unescapeXml(responseBody);
    			responseBody = responseBody.replaceAll("&lt;", "<");
    			responseBody = responseBody.replaceAll("&gt;", ">");
    			responseBody = responseBody.replaceAll("<ProductManagerRecommand>", "<ProductManagerRecommand><![CDATA[");
    			responseBody = responseBody.replaceAll("</ProductManagerRecommand>", "]]></ProductManagerRecommand>");
    			responseBody = responseBody.replaceAll("<Address>", "<Address><![CDATA[");
    			responseBody = responseBody.replaceAll("</Address>", "]]></Address>");
    			log.info("返回体："+responseBody);
    			JAXBContext subXmlContext = JAXBContext.newInstance(ScenicSpotSearchResponseType.class);
    			Unmarshaller subXmlUnmarshaller = subXmlContext.createUnmarshaller();
    			ScenicSpotSearchResponseType scenicSpotSearchResponse = (ScenicSpotSearchResponseType) subXmlUnmarshaller.unmarshal(new StringReader(responseBody));
    			return scenicSpotSearchResponse;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 批量设置门票景点相关数据状态
     * @author caiys
     * @date 2015年12月9日 下午5:59:28
     */
    public void updateScenicStatus(RowStatus rowStatus, Integer districtID) {
    	// 先批量设置行状态
    	ctripScenicSpotDao.updateRowStatus(rowStatus, districtID);
    	ctripTicketDao.updateRowStatus(rowStatus, districtID);
    	ctripTicketResourceDao.updateRowStatus(rowStatus, districtID);
    }
    
    /**
     * 更新门票景点，先把记录都设置为删除，存在则更新，不存在则插入
     * @author caiys
     * @date 2015年12月3日 上午11:36:55
     */
    public ScenicSpotSearchResponseType updateTicketSenicSpot(Integer pageIndex, Integer pageSize, String keyword, Integer saleCityID) {
    	ScenicSpotSearchResponseType scenicSpotSearchResponse = getTicketSenicSpotSearch(pageIndex, pageSize, keyword, saleCityID);
    	List<ScenicSpotListItemDTOType> scenicSpotListItemDTO = scenicSpotSearchResponse.getScenicSpotListItemList().getScenicSpotListItemDTO();
    	for (ScenicSpotListItemDTOType scenicSpotItemDTO : scenicSpotListItemDTO) {	// 景点列表
    		CtripScenicSpot ctripScenicSpot = ctripScenicSpotDao.load(Integer.valueOf(scenicSpotItemDTO.getID()));
    		if (ctripScenicSpot == null) {
    			ctripScenicSpot = new CtripScenicSpot();
        		ctripScenicSpot.setId(Integer.valueOf(scenicSpotItemDTO.getID()));
        		ctripScenicSpot.setCreateTime(new Date());
        		ctripScenicSpot.setRowStatus(RowStatus.INSERT);
    		} else {
    			ctripScenicSpot.setUpdateTime(new Date());
    			ctripScenicSpot.setRowStatus(RowStatus.UPDATE);
    		}
    		ctripScenicSpot.setName(scenicSpotItemDTO.getName());
    		ctripScenicSpot.setStar(Integer.valueOf(scenicSpotItemDTO.getStar()));
    		ctripScenicSpot.setAddress(scenicSpotItemDTO.getAddress());
    		ctripScenicSpot.setDistrictID(Integer.valueOf(scenicSpotItemDTO.getDistrictID()));
    		ctripScenicSpot.setDistrictName(scenicSpotItemDTO.getDistrictName());
    		ctripScenicSpot.setProvinceID(Integer.valueOf(scenicSpotItemDTO.getProvinceID()));
    		ctripScenicSpot.setProvinceName(scenicSpotItemDTO.getProvinceName());
    		ctripScenicSpot.setCountryID(Integer.valueOf(scenicSpotItemDTO.getCountryID()));
    		ctripScenicSpot.setCountryName(scenicSpotItemDTO.getCountryName());
    		ctripScenicSpot.setProductManagerRecommand(scenicSpotItemDTO.getProductManagerRecommand());
    		ctripScenicSpot.setCommentGrade(Float.valueOf(scenicSpotItemDTO.getCommentGrade()));
    		ctripScenicSpot.setCommentUserCount(Integer.valueOf(scenicSpotItemDTO.getCommentUserCount()));
    		ctripScenicSpot.setOrderCount(Integer.valueOf(scenicSpotItemDTO.getOrderCount()));
    		if (RowStatus.INSERT == ctripScenicSpot.getRowStatus()) {
    			ctripScenicSpotDao.save(ctripScenicSpot);
    		} else {
    			ctripScenicSpotDao.update(ctripScenicSpot);
    		}
    		
    		List<ProductListItemDTOType> productListItemDTO = scenicSpotItemDTO.getProductListItemList().getProductListItemDTO();	
    		for (ProductListItemDTOType productItemDTO : productListItemDTO) {	// 门票产品列表
    			CtripTicket ctripTicket = ctripTicketDao.load(Integer.valueOf(productItemDTO.getID()));
    			if (ctripTicket == null) {
    				ctripTicket = new CtripTicket();
    				ctripTicket.setId(Integer.valueOf(productItemDTO.getID()));
    				ctripTicket.setCreateTime(new Date());
    				ctripTicket.setRowStatus(RowStatus.INSERT);
        		} else {
        			ctripTicket.setUpdateTime(new Date());
        			ctripTicket.setRowStatus(RowStatus.UPDATE);
        		}
    			ctripTicket.setName(productItemDTO.getName());
    			ctripTicket.setMarketPrice(Integer.valueOf(productItemDTO.getMarketPrice()));
    			ctripTicket.setPrice(Integer.valueOf(productItemDTO.getPrice()));
    			ctripTicket.setIsReturnCash(Boolean.valueOf(productItemDTO.getIsReturnCash()));
    			ctripTicket.setReturnCashAmount(Integer.valueOf(productItemDTO.getReturnCashAmount()));
    			ctripTicket.setScenicSpotId(ctripScenicSpot.getId());
    			if (RowStatus.INSERT == ctripTicket.getRowStatus()) {
    				ctripTicketDao.save(ctripTicket);
        		} else {
        			ctripTicketDao.update(ctripTicket);
        		}
    			
    			List<ResourceListItemDTOType> resourceListItemDTO = productItemDTO.getResourceListItemList().getResourceListItemDTO();
    			for (ResourceListItemDTOType resourceItemDTO : resourceListItemDTO) {	// 门票资源列表
    				CtripTicketResource ctripTicketResource = ctripTicketResourceDao.load(Integer.valueOf(resourceItemDTO.getID()));
    				if (ctripTicketResource == null) {
        				ctripTicketResource = new CtripTicketResource();
        				ctripTicketResource.setId(Integer.valueOf(resourceItemDTO.getID()));
        				ctripTicketResource.setCreateTime(new Date());
        				ctripTicketResource.setRowStatus(RowStatus.INSERT);
            		} else {
            			ctripTicketResource.setUpdateTime(new Date());
            			ctripTicketResource.setRowStatus(RowStatus.UPDATE);
            		}
    				ctripTicketResource.setName(resourceItemDTO.getName());
    				ctripTicketResource.setMarketPrice(Integer.valueOf(resourceItemDTO.getMarketPrice()));
    				ctripTicketResource.setPrice(Integer.valueOf(resourceItemDTO.getPrice()));
    				ctripTicketResource.setIsReturnCash(Boolean.valueOf(resourceItemDTO.getIsReturnCash()));
    				ctripTicketResource.setReturnCashAmount(Integer.valueOf(resourceItemDTO.getReturnCashAmount()));
    				ctripTicketResource.setTicketId(ctripTicket.getId());
    				if (RowStatus.INSERT == ctripTicketResource.getRowStatus()) {
    					ctripTicketResourceDao.save(ctripTicketResource);
            		} else {
            			ctripTicketResourceDao.update(ctripTicketResource);
            		}
    			}
    		}
    	}
    	return scenicSpotSearchResponse;
    }

    /**
     * 查询携程门票资源价格库（价格日历）
     * @author caiys
     * @date 2015年12月3日 下午2:36:53
     * @param productId
     * @param rescourceId
     * @param startDate 格式如2013-12-29T11:27:25.3213163+08:00
     * @param endDate 格式如2013-12-27T11:27:25.3213163+08:00
     * @return
     */
    public TrpaiTicketPriceSearchResponse getTicketResourcePriceAndInventory(Integer productId, Integer rescourceId, String startDate, String endDate) {
    	try {
    		StringBuilder paramSb = new StringBuilder();
    		paramSb.append("<TicketPriceSearchRequest><DistributionChannel>9</DistributionChannel>");
			paramSb.append("<ProductID>").append(productId).append("</ProductID>");
			paramSb.append("<ResourcePriceAndInventoryRequestItemList><TicketPriceSearchRequestDetail>");
			paramSb.append("<EndDate>").append(endDate).append("</EndDate>");
			paramSb.append("<ResourceID>").append(rescourceId).append("</ResourceID>");
			paramSb.append("<StartDate>").append(startDate).append("</StartDate>");
			paramSb.append("</TicketPriceSearchRequestDetail></ResourcePriceAndInventoryRequestItemList>");
    		paramSb.append("</TicketPriceSearchRequest>");
    		String requestJson = buildRequestJson(TICKET_PRICEANDINVENTORY_INTERFACE, paramSb.toString());
    		String resultStr = post(TICKET_URL, requestJson);
    		if (StringUtils.isNotBlank(resultStr)) {
    			// 处理返回结果
    			JAXBContext context = JAXBContext.newInstance(APIProtocolPackageType.class);
    			Unmarshaller unmarshaller = context.createUnmarshaller();
    			APIProtocolPackageType result = (APIProtocolPackageType) unmarshaller.unmarshal(new StringReader(resultStr));
    			if (Boolean.valueOf(result.getIsError())) {
    				log.error("错误信息："+result.getErrorMessage());
    				return null;
    			}
    			String responseBody = result.getResponseBody();
    			log.info("返回体："+responseBody);
    			// 处理返回结果中内嵌的xml
//    			responseBody = StringEscapeUtils.unescapeXml(responseBody);
    			responseBody = responseBody.replaceAll("&lt;", "<");
    			responseBody = responseBody.replaceAll("&gt;", ">");
    			JAXBContext subXmlContext = JAXBContext.newInstance(TrpaiTicketPriceSearchResponse.class);
    			Unmarshaller subXmlUnmarshaller = subXmlContext.createUnmarshaller();
    			TrpaiTicketPriceSearchResponse trpaiTicketPriceSearchResponse = (TrpaiTicketPriceSearchResponse) subXmlUnmarshaller.unmarshal(new StringReader(responseBody));
    			return trpaiTicketPriceSearchResponse;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 更新门票资源价格库，先把记录删除，再做插入
     * @author caiys
     * @date 2015年12月3日 下午5:51:22
     * @param productId
     * @param rescourceId
     * @param startDate
     * @param endDate
     */
    public int updateTicketPrice(Integer productId, Integer rescourceId, String startDateStr, String endDateStr) {
    	int count = 0;
    	TrpaiTicketPriceSearchResponse ticketPriceSearchResponse = getTicketResourcePriceAndInventory(productId, rescourceId, startDateStr, endDateStr);
    	// 先删除符合条件的记录
    	Date startDate = DateUtils.getDate(startDateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
    	Date endDate = DateUtils.getDate(endDateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00");
    	ctripTicketPriceDailyDao.delByPriceIdAndDay(productId, rescourceId, startDate, endDate);
    	
    	TrpaiTicketPriceSearchResponseDetail ticketPriceSearchResponseDetail = ticketPriceSearchResponse.getResourcePriceAndInventoryList().getTicketPriceSearchResponseDetail();
    	if (ticketPriceSearchResponseDetail != null) {
    		List<TrpaiTicketPriceDailyDTO> ticketPriceDailyDTO = ticketPriceSearchResponseDetail.getResourcePriceAndInventoryDailyList().getTicketPriceDailyDTO();
    		count += ticketPriceDailyDTO.size();
    		for (TrpaiTicketPriceDailyDTO tpdDTO : ticketPriceDailyDTO) {
    			CtripTicketPriceDaily ctripTicketPriceDaily = new CtripTicketPriceDaily();
    			if (StringUtils.isNotBlank(tpdDTO.getDate())) {
    				ctripTicketPriceDaily.setDate(DateUtils.getDate(tpdDTO.getDate(), "yyyy-MM-dd'T'HH:mm:ss"));
    			}
    			ctripTicketPriceDaily.setPrice(tpdDTO.getPrice());
    			ctripTicketPriceDaily.setMarketPrice(tpdDTO.getMarketPrice());
    			ctripTicketPriceDaily.setCostPrice(tpdDTO.getCostPrice());
    			ctripTicketPriceDaily.setMinQuantity(tpdDTO.getMinQuantity());
    			ctripTicketPriceDaily.setMaxQuantity(tpdDTO.getMaxQuantity());
    			ctripTicketPriceDaily.setInventoryNum(tpdDTO.getInventoryNum());
    			ctripTicketPriceDaily.setCreateTime(new Date());
    			ctripTicketPriceDaily.setTicketId(productId);
    			ctripTicketPriceDaily.setTicketResourceId(rescourceId);
    			ctripTicketPriceDailyDao.save(ctripTicketPriceDaily);
    		}
    	}
    	return count;
    }

	/**
	 * 更新携程同步的门票数据到本地数据结构
	 * 要求：scenic表的ctrip_scenic_id需已赋值
	 * @author caiys
	 * @date 2015年12月29日 下午5:25:42
	 */
	public void updateTicket() {
		ctripTicketDao.updateTicket();
	}
	
	/**
	 * 更新携程同步的门票价格数据到本地数据结构
	 * @author caiys
	 * @date 2015年12月29日 下午5:56:25
	 * @param startDateStr
	 * @param endDateStr
	 */
	public void updateTicketDatePrice(String startDateStr, String endDateStr) {
		ctripTicketDao.updateTicketDatePrice(startDateStr, endDateStr);
	}

    /**
     * 门票可订性检查
     * @author caiys
     * @date 2015年12月3日 下午3:06:15
     * @param totalPrice
     * @param rescourceId
     * @param useDate	格式如2013-12-27T11:27:25.3213163+08:00
     * @param useQuantity
     * @return
     */
    public TocbResourceCanBookingResponse checkTicketOrderCanBooking(Integer orderTotalPrice, Integer totalPrice, Integer rescourceId, String useDate, Integer useQuantity) {
    	try {
    		StringBuilder paramSb = new StringBuilder();
    		paramSb.append("<ResourceCanBookingRequest><DistributionChannel>9</DistributionChannel>");
			paramSb.append("<OrderTotalPrice>").append(orderTotalPrice).append("</OrderTotalPrice>");
			paramSb.append("<ResourceCanBookingRequestItemList><ResourceCanBookingRequestItemDTO>");
			paramSb.append("<ResourceID>").append(rescourceId).append("</ResourceID>");
			paramSb.append("<TotalPrice>").append(totalPrice).append("</TotalPrice>");
			paramSb.append("<UseDate>").append(useDate).append("</UseDate>");
			paramSb.append("<UseQuantity>").append(useQuantity).append("</UseQuantity>");
			paramSb.append("</ResourceCanBookingRequestItemDTO></ResourceCanBookingRequestItemList>");
    		paramSb.append("</ResourceCanBookingRequest>");
    		String requestJson = buildRequestJson(TICKET_ORDERCANBOOKING_INTERFACE, paramSb.toString());
    		String resultStr = post(TICKET_URL, requestJson);
    		if (StringUtils.isNotBlank(resultStr)) {
    			// 处理返回结果
    			JAXBContext context = JAXBContext.newInstance(APIProtocolPackageType.class);
    			Unmarshaller unmarshaller = context.createUnmarshaller();
    			APIProtocolPackageType result = (APIProtocolPackageType) unmarshaller.unmarshal(new StringReader(resultStr));
    			if (Boolean.valueOf(result.getIsError())) {
    				log.error("错误信息："+result.getErrorMessage());
    				return null;
    			}
    			String responseBody = result.getResponseBody();
    			// 处理返回结果中内嵌的xml
//    			responseBody = StringEscapeUtils.unescapeXml(responseBody);
    			responseBody = responseBody.replaceAll("&lt;", "<");
    			responseBody = responseBody.replaceAll("&gt;", ">");
    			log.info("返回体："+responseBody);
    			JAXBContext subXmlContext = JAXBContext.newInstance(TocbResourceCanBookingResponse.class);
    			Unmarshaller subXmlUnmarshaller = subXmlContext.createUnmarshaller();
    			TocbResourceCanBookingResponse resourceCanBookingResponse = (TocbResourceCanBookingResponse) subXmlUnmarshaller.unmarshal(new StringReader(responseBody));
    			return resourceCanBookingResponse;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 创建携程门票订单
     * @author caiys
     * @date 2015年12月2日 下午9:42:20
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public Boolean createCtripTicketOrderPrepay(TocfpTicketOrderCreateRequest ticketOrderCreateRequest) {
    	boolean isSucceed = false;
    	try {
            JAXBContext marContext = JAXBContext.newInstance(TocfpTicketOrderCreateRequest.class);
            Marshaller marshaller =  marContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(ticketOrderCreateRequest, writer);
//            String paramJson = "<TicketOrderCreateRequest><AdultNumber>1</AdultNumber><Amount>220</Amount><ChildNumber>0</ChildNumber><IsBackCash>false</IsBackCash><ClientInfoList><ClientDTO><AgeType>ADU</AgeType><BirthDate>1990-05-01T00:00:00</BirthDate><ClientName>测试</ClientName><ClientName_E>test</ClientName_E><ContactInfo><ContactMobile>13511111111</ContactMobile><ContactName>测试</ContactName><ContactEmail>test@test.com</ContactEmail></ContactInfo><Gender>0</Gender><HzDate>0001-01-01T00:00:00</HzDate><HzNo>123022323</HzNo><IDCardNo>123423432</IDCardNo><IDCardTimelimit>0001-01-01T00:00:00</IDCardTimelimit><IDCardType>2</IDCardType><InfoID>0</InfoID><IssueDate>0001-01-01T00:00:00</IssueDate><PassportType>P</PassportType></ClientDTO></ClientInfoList><DistributorOrderID>jzc_test_000111</DistributorOrderID><ProductInfo><ProductID>1601442</ProductID></ProductInfo><Remark>特殊说明</Remark><SalesCity>21</SalesCity><MasterTicketDTO><Price>220</Price><TicketID>1601468</TicketID><UseDate>2015-12-12T13:20:13.5893645+08:00</UseDate><Quantity>1</Quantity><TicketType>0</TicketType></MasterTicketDTO><Uid>6b6b5ce1-573e-4f50-9a9d-36803a30c3f7</Uid></TicketOrderCreateRequest>";
            String paramJson = writer.toString();
//            paramJson = paramJson.replaceFirst("<\\?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"\\?>", "");
    		String requestJson = buildRequestJson(TICKET_ORDER_PREPAY_INTERFACE, paramJson);
    		String resultStr = post(TICKET_URL, requestJson);
    		if (StringUtils.isNotBlank(resultStr)) {
    			// 处理返回结果
    			JAXBContext context = JAXBContext.newInstance(APIProtocolPackageType.class);
    			Unmarshaller unmarshaller = context.createUnmarshaller();
    			APIProtocolPackageType result = (APIProtocolPackageType) unmarshaller.unmarshal(new StringReader(resultStr));
    			if (Boolean.valueOf(result.getIsError())) {
    				log.error("错误信息："+result.getErrorMessage());
    				isSucceed = false;
    			} else {
    				String responseBody = result.getResponseBody();
    				// 处理返回结果中内嵌的xml
    				//    			responseBody = StringEscapeUtils.unescapeXml(responseBody);
    				responseBody = responseBody.replaceAll("&lt;", "<");
    				responseBody = responseBody.replaceAll("&gt;", ">");
    				log.info("返回体："+responseBody);
    				if (StringUtils.isNotBlank(responseBody) && responseBody.indexOf("IsSucceed") > -1) {
    					String succeed = responseBody.substring(responseBody.indexOf("<IsSucceed>")+"<IsSucceed>".length(), responseBody.indexOf("</IsSucceed>"));
    					String ctripOrderId = responseBody.substring(responseBody.indexOf("<OrderId>")+"<OrderId>".length(), responseBody.indexOf("</OrderId>"));
    					isSucceed = Boolean.valueOf(succeed);
    			    	// 本地数据保存
    			    	doCreateTicketOrderPrepay(ticketOrderCreateRequest, isSucceed, ctripOrderId);
    				} else {
    					isSucceed = false;
    				}
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
			isSucceed = false;
    	}
    	return isSucceed;
    }
    
    /**
     * 创建携程门票订单后本地保存订单信息
     * @author caiys
     * @date 2015年12月4日 上午11:57:42
     * @param ticketOrderCreateRequest
     */
    public void doCreateTicketOrderPrepay(TocfpTicketOrderCreateRequest ticketOrderCreateRequest, Boolean isSucceed, String ctripOrderId) {
    	CtripTicketPreOrder ctripTicketPreOrder = new CtripTicketPreOrder();
    	ctripTicketPreOrder.setAdultNumber(ticketOrderCreateRequest.getAdultNumber());
    	ctripTicketPreOrder.setAmount(ticketOrderCreateRequest.getAmount());	
    	ctripTicketPreOrder.setChildNumber(0);
    	ctripTicketPreOrder.setDistributorOrderId(ticketOrderCreateRequest.getDistributorOrderID());
//    	ctripTicketPreOrder.setContactEmail(ticketOrderCreateRequest.getContactInfo().getContactEmail());
//    	ctripTicketPreOrder.setContactMobile(ticketOrderCreateRequest.getContactInfo().getContactMobile());
//    	ctripTicketPreOrder.setContactName(ticketOrderCreateRequest.getContactInfo().getContactName());
    	ctripTicketPreOrder.setIsBackCash(false);
    	ctripTicketPreOrder.setProductId(ticketOrderCreateRequest.getProductInfo().getProductID());
    	ctripTicketPreOrder.setRemark(ticketOrderCreateRequest.getRemark());
    	ctripTicketPreOrder.setSalesCity(ticketOrderCreateRequest.getSalesCity());
    	ctripTicketPreOrder.setUid(ticketOrderCreateRequest.getUid());
    	if (isSucceed) {
    		ctripTicketPreOrder.setOrderStatus(OrderStatus.PREPAY);
    	} else {
    		ctripTicketPreOrder.setOrderStatus(OrderStatus.FAIL);
    	}
    	ctripTicketPreOrder.setCreateTime(new Date());
    	ctripTicketPreOrder.setCtripOrderId(ctripOrderId);
    	ctripTicketPreOrderDao.save(ctripTicketPreOrder);
    	// 游客信息
    	List<TocfpClientDTO> tocfpClientDTO = ticketOrderCreateRequest.getClientInfoList().getClientDTO();
    	for (TocfpClientDTO clientDTO : tocfpClientDTO) {
    		CtripTicketPreOrderClientInfo clientInfo = new CtripTicketPreOrderClientInfo();
    		clientInfo.setAgeType(clientDTO.getAgeType());
    		clientInfo.setBirthDate(DateUtils.getDate(clientDTO.getBirthDate(), "yyyy-MM-dd'T'HH:mm:ss"));
    		clientInfo.setClientName(clientDTO.getClientName());
    		clientInfo.setClientNameEn(clientDTO.getClientNameE());
    		clientInfo.setContactInfo(clientDTO.getContactInfo());
    		clientInfo.setGender(clientDTO.getGender());
    		clientInfo.setHzDate(DateUtils.getDate(clientDTO.getHzDate(), "yyyy-MM-dd'T'HH:mm:ss"));
    		clientInfo.setHzNo(clientDTO.getHzNo());
    		clientInfo.setiDCardNo(clientDTO.getIDCardNo());
    		clientInfo.setiDCardTimelimit(DateUtils.getDate(clientDTO.getIDCardTimelimit(), "yyyy-MM-dd'T'HH:mm:ss"));
    		clientInfo.setiDCardType(clientDTO.getIDCardType());
    		clientInfo.setInfoId(clientDTO.getInfoID());
    		clientInfo.setIssueDate(DateUtils.getDate(clientDTO.getIssueDate(), "yyyy-MM-dd'T'HH:mm:ss"));
    		clientInfo.setPassportType(clientDTO.getPassportType());
    		clientInfo.setCreateTime(new Date());
    		clientInfo.setTicketPreOrderId(ctripTicketPreOrder.getId());
    		ctripTicketPreOrderClientInfoDao.save(clientInfo);
    	}
    	// 门票信息
    	List<TocfpMasterTicketDTO> tocfpMasterTicketDTO = ticketOrderCreateRequest.getTicketInfo().getMasterTicketDTO();
    	for (TocfpMasterTicketDTO masterTicketDTO : tocfpMasterTicketDTO) {
    		CtripTicketPreOrderTicketInfo ticketInfo = new CtripTicketPreOrderTicketInfo();
    		ticketInfo.setPrice(masterTicketDTO.getPrice());	
    		ticketInfo.setTicketResourceId(masterTicketDTO.getTicketID());
    		ticketInfo.setUseDate(DateUtils.getDate(masterTicketDTO.getUseDate(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS+08:00"));
    		ticketInfo.setQuantity(masterTicketDTO.getQuantity());
    		ticketInfo.setTicketType(masterTicketDTO.getTicketType());
    		ticketInfo.setCreateTime(new Date());
    		ticketInfo.setTicketPreOrderId(ctripTicketPreOrder.getId());
    		ctripTicketPreOrderTicketInfoDao.save(ticketInfo);
    	}
    }
    
    /**
     * 构造请求参数
     * @author caiys
     * @date 2015年12月3日 下午2:00:23
     * @param itf	接口名称
     * @param requestBody 	请求参数xml格式字符串
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String buildRequestJson(String itf, String requestBody) throws UnsupportedEncodingException {
    	String allianceId = propertiesManager.getString("CTRIP_ALLIANCE_ID");
	    String sid = propertiesManager.getString("CTRIP_SID");
	    String apiKey = propertiesManager.getString("CTRIP_API_KEY");
		JSONObject request = new JSONObject();
		request.put("AllianceID", allianceId);
		request.put("SID", sid);
		request.put("ProtocolType", 0);	// 请求类型0-xml 1-Json
		long timeStamp = System.currentTimeMillis();
		request.put("Signature", MD5.caiBeiMD5(timeStamp + allianceId + MD5.caiBeiMD5(apiKey).toUpperCase() + sid + itf).toUpperCase());
		request.put("TimeStamp", timeStamp);
		request.put("Channel", "Vacations");
		request.put("Interface", itf);
		request.put("IsError", false);
		request.put("RequestBody", requestBody);
		request.put("ResponseBody", "");
		request.put("ErrorMessage", "");

		log.info("请求体："+requestBody);
		String encoderJson = URLEncoder.encode(request.toString(), "GBK");
		return encoderJson;
    }
    
    /**
     * 执行请求并返回结果
     * @author caiys
     * @date 2015年12月2日 下午9:37:09
     * @param postUrl
     * @return
     */
    public String post(String url, String paramStr) {
		String postUrl = url + "?RequestJson=" + paramStr;
    	//创建HttpClientBuilder  
    	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
    	//HttpClient  
    	CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
    	HttpPost httpRequst = new HttpPost(postUrl);
    	try {  
    		//执行请求  
    		HttpResponse httpResponse = closeableHttpClient.execute(httpRequst);  
    		//获取响应消息实体  
    		HttpEntity entity = httpResponse.getEntity();  
    		//判断响应实体是否为空  
    		if (entity != null) {  
            	String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        		log.info("返回结果："+resultStr);
            	return resultStr;
    		}  
    	} catch (IOException e) {  
    		e.printStackTrace();  
    	} finally {  
    		try {  
    			//关闭流并释放资源  
    			closeableHttpClient.close();  
    		} catch (IOException e) {  
    			e.printStackTrace();  
    		}  
    	}  
    	return null;
    }
    
    public List<CtripTicketResource> listCtripTicketResourceBy(RowStatus neRowStatus, Integer districtID) {
    	return ctripTicketResourceDao.listCtripTicketResourceBy(neRowStatus, districtID);
    }
    
    public int countCtripTicketResourceBy(RowStatus neRowStatus, Integer districtID) {
    	return ctripTicketResourceDao.countCtripTicketResourceBy(neRowStatus, districtID);
    }
    
    public List<CtripTicketResource> listCtripTicketResourceBy(Integer pageIndex, Integer pageSize, RowStatus neRowStatus, Integer districtID) {
    	return ctripTicketResourceDao.listCtripTicketResourceBy(pageIndex, pageSize, neRowStatus, districtID);
    }
    
}
