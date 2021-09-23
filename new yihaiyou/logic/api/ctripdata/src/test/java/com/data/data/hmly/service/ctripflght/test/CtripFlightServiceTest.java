package com.data.data.hmly.service.ctripflght.test;

import com.data.data.hmly.service.ctripflight.CtripFlightService;
import com.data.data.hmly.service.ctripflight.CtripFltCancelOrderService;
import com.data.data.hmly.service.ctripflight.CtripFltGetAirlineInfosService;
import com.data.data.hmly.service.ctripflight.CtripFltGetAirportInfosService;
import com.data.data.hmly.service.ctripflight.CtripFltGetCityInfosService;
import com.data.data.hmly.service.ctripflight.CtripFltGetCraftInfosService;
import com.data.data.hmly.service.ctripflight.CtripFltOrderService;
import com.data.data.hmly.service.ctripflight.CtripFltViewOrderService;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchHotelRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchPriceTypeOptionsType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRequestBodyType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRouteType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRoutesType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltCancelOrderIDType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltCancelOrderRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderContactInfoType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderCreditCardInfoType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderDeliverInfoType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderFlightInfoListType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderFlightInfoRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderPassengerListType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderPassengerRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderPayInfoType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltSaveOrderRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltViewOrderOrderIDType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltViewOrderRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirlineInfosRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirportInfosRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCityInfosRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCraftInfosRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.FlightResponseType;
import com.data.data.hmly.service.ctripflight.response.pojo.FltCancelOrderRequestResultType;
import com.data.data.hmly.service.ctripflight.response.pojo.FltSaveOrderResponseType;
import com.data.data.hmly.service.ctripflight.response.pojo.FltViewOrderResponse;
import com.data.data.hmly.service.ctripflight.response.pojo.GetAirlineInfoEntityType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetAirportInfoEntityType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCityInfosListType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCraftInfoEntityType;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.util.List;

@Ignore
public class CtripFlightServiceTest extends TestCase {
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml") ;
	private CtripFlightService ctripFlightService = (CtripFlightService) applicationContext.getBean("ctripFlightService") ;
	private CtripFltOrderService ctripFltOrderService= (CtripFltOrderService) applicationContext.getBean("ctripFltOrderService") ;
	private CtripFltCancelOrderService ctripFltCancelOrderService= (CtripFltCancelOrderService) applicationContext.getBean("ctripFltCancelOrderService") ;
	private CtripFltViewOrderService ctripFltViewOrderService= (CtripFltViewOrderService) applicationContext.getBean("ctripFltViewOrderService") ;
	private CtripFltGetCityInfosService ctripFltGetCityInfosService= (CtripFltGetCityInfosService) applicationContext.getBean("ctripFltGetCityInfosService") ;
	private CtripFltGetAirportInfosService ctripFltGetAirportInfosService= (CtripFltGetAirportInfosService) applicationContext.getBean("ctripFltGetAirportInfosService") ;
	private CtripFltGetAirlineInfosService ctripFltGetAirlineInfosService= (CtripFltGetAirlineInfosService) applicationContext.getBean("ctripFltGetAirlineInfosService") ;
	private CtripFltGetCraftInfosService ctripFltGetCraftInfosService= (CtripFltGetCraftInfosService) applicationContext.getBean("ctripFltGetCraftInfosService") ;
	
	@Override
	protected void tearDown() throws Exception {
		((ClassPathXmlApplicationContext)applicationContext).close();
	}
	/**
	 * 测试携程机票取消订单
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestFltViewOrder(){
		 
		 
		 FltViewOrderRequestType viewOrderRequestType = new FltViewOrderRequestType();

		 FltViewOrderOrderIDType viewOrderOrderIDType = new FltViewOrderOrderIDType();
		 viewOrderOrderIDType.getInt().add("0");
//		 viewOrderOrderIDType.getInt().add("2");
		 
		 
		 viewOrderRequestType.setOrderID(viewOrderOrderIDType);
		 viewOrderRequestType.setUserID("86826d23-5fee-4f00-b66e-89f4b76119f2");
		 
		 
		 FltViewOrderResponse viewOrderResponse = ctripFltViewOrderService.getFltViewOrderResponse(viewOrderRequestType);
		 

	 }
	
	/**
	 * 测试携程机票取消订单
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestFltCancelOrder (){
		 
		 FltCancelOrderRequestType cancelOrderRequest =new  FltCancelOrderRequestType();
		 cancelOrderRequest.setUserID("86826d23-5fee-4f00-b66e-89f4b76119f2");
		 
		 FltCancelOrderIDType cancelOrderId = new FltCancelOrderIDType();
		 
		 cancelOrderId.setInt("0");
		 
		 cancelOrderRequest.setOrderID(cancelOrderId);
		 
		 FltCancelOrderRequestResultType cancelOrderRequestResultType = ctripFltCancelOrderService.getCancelFltOrderResponse(cancelOrderRequest);
		 

	 }
	
	
	/**
	 * 测试携程机票下单
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void testFltSaveOrder (){
		 
		 
		 FltSaveOrderRequestType saveOrderRequest = new FltSaveOrderRequestType();
		 
		 saveOrderRequest.setUID("86826d23-5fee-4f00-b66e-89f4b76119f2");
		 saveOrderRequest.setAmount("1550");
		 saveOrderRequest.setOrderType("ADU");
		 
		 /******************FltOrderFlightInfoListType*******************/
		 FltOrderFlightInfoListType fltOrder = new FltOrderFlightInfoListType();
		 FltOrderFlightInfoRequestType orderFlightInfoRequest = new FltOrderFlightInfoRequestType();
		 orderFlightInfoRequest.setDepartCityID("1"); 
		 orderFlightInfoRequest.setArriveCityID("30");
		 orderFlightInfoRequest.setDPortCode("PEK");
		 orderFlightInfoRequest.setAPortCode("CZ");
		 orderFlightInfoRequest.setAirlineCode("CZ");
		 orderFlightInfoRequest.setFlight("CZ3160");
		 orderFlightInfoRequest.setClazz("Y");
		 orderFlightInfoRequest.setSubClass("SubClass");
		 orderFlightInfoRequest.setTakeOffTime("2015-12-25T15:00:00");
		 orderFlightInfoRequest.setArrivalTime("2015-12-25T17:50:00");
		 orderFlightInfoRequest.setRate("0.8");
		 orderFlightInfoRequest.setPrice("1400");
		 orderFlightInfoRequest.setTax("50");
		 orderFlightInfoRequest.setOilFee("100.0000");
		 orderFlightInfoRequest.setNonRer("T");
		 orderFlightInfoRequest.setNonRef("T");
		 orderFlightInfoRequest.setNonEnd("T");
		 orderFlightInfoRequest.setRerNote("不得更改。");
		 orderFlightInfoRequest.setRefNote("不得退票。");
		 orderFlightInfoRequest.setEndNote("不得签转。");
		 orderFlightInfoRequest.setRemark("yeye特价产品");
		 orderFlightInfoRequest.setNeedAppl("F");
		 orderFlightInfoRequest.setRecommend("2");
		 orderFlightInfoRequest.setCanpost("T");
		 orderFlightInfoRequest.setCraftType("77A");
		 orderFlightInfoRequest.setQuantity("10");
		 orderFlightInfoRequest.setRefundFeeFormulaID("0");
		 orderFlightInfoRequest.setUpGrade("T");
		 orderFlightInfoRequest.setTicketType("1111");
		 orderFlightInfoRequest.setAllowCPType("1111");
		 orderFlightInfoRequest.setProductType("NORMAL");
		 orderFlightInfoRequest.setProductSource("1");
		 orderFlightInfoRequest.setInventoryType("Fav");
		 orderFlightInfoRequest.setPriceType("NormalPrice");
		 orderFlightInfoRequest.setOnlyowncity("false");
		 orderFlightInfoRequest.setRouteIndex("0");
		 fltOrder.setFlightInfoRequest(orderFlightInfoRequest);
		 
		 
		 saveOrderRequest.setFlightInfoList(fltOrder);

		 /******************FltOrderPassengerListType*******************/
		 FltOrderPassengerListType passengerList = new FltOrderPassengerListType();
		 FltOrderPassengerRequestType passengerRequest = new FltOrderPassengerRequestType();
		 passengerRequest.setPassengerName("携程");
		 passengerRequest.setBirthDay("1984-01-01");
		 passengerRequest.setPassportTypeID("1");
//		 passengerRequest.setContactTelephone(value);
		 passengerRequest.setPassportNo("342921198707062115");
		 passengerRequest.setGender("M");
		 passengerList.setPassengerRequest(passengerRequest);
		 
		 saveOrderRequest.setPassengerList(passengerList);
		 
		 /******************FltOrderPassengerListType*******************/
		 FltOrderContactInfoType orderContactInfo = new FltOrderContactInfoType();
		 
		 orderContactInfo.setContactName("携程");
		 orderContactInfo.setConfirmOption("TEL");
		 orderContactInfo.setMobilePhone("13800138000");
		 orderContactInfo.setContactEMail("testAPI@ctrip.com");
		 
		 saveOrderRequest.setContactInfo(orderContactInfo);
		 
		 /******************FltOrderDeliverInfoType*******************/
		 FltOrderDeliverInfoType orderDeliverInfo = new FltOrderDeliverInfoType();
		 
		 orderDeliverInfo.setDeliveryType("PJN");
		 orderDeliverInfo.setSendTicketCityID("0");
		 orderDeliverInfo.setOrderRemark("");
		 
		 saveOrderRequest.setDeliverInfo(orderDeliverInfo);
		 
		 /******************FltOrderPayInfoType*******************/
		 FltOrderPayInfoType payInfo = new  FltOrderPayInfoType();
		 
		 FltOrderCreditCardInfoType creditCard = new  FltOrderCreditCardInfoType();
		 
		 creditCard.setCardInfoID("0");
		 creditCard.setCreditCardType("11");
		 creditCard.setCreditCardNumber("hiNI6GWod48+siR777rXyLgBgE0dF6f4");
		 creditCard.setValidity("gSb+/Uj5DEE=");
		 creditCard.setCardBin("uffYuvmpsOg=");
		 creditCard.setCardHolder("5/bqCaNz3yw=");
		 creditCard.setIdCardType("gBG1pcTHP+M=");
		 creditCard.setIdNumber("EDXkCmkgwpjSs25jdmMPk6hi0ZpuQ1mV");
		 creditCard.setCVV2No("0DXVwD+y96Y=");
		 creditCard.setEid("api");
		 creditCard.setIsClient("true");
		 creditCard.setCCardPayFee("0");
		 creditCard.setCCardPayFeeRate("0");
		 creditCard.setExponent("0");
		 
		 payInfo.setCreditCardInfo(creditCard);
		 
		 saveOrderRequest.setPayInfo(payInfo);
		 
		 
		 FltSaveOrderResponseType fltSaveOrderResponse = ctripFltOrderService.getSaveFlightOrderResponse(saveOrderRequest);
		 if(fltSaveOrderResponse != null){
			 System.out.print("result:"+fltSaveOrderResponse.getResult());
			 System.out.print(",resultMsg:"+fltSaveOrderResponse.getResultMsg());
			 System.out.print(",tempOrderID:"+fltSaveOrderResponse.getTempOrderID());
			 System.out.print(",orderID:"+fltSaveOrderResponse.getOrderID());
		 }
		 
	 }
	
	 public void atestSaveFlight(){
		 try {
			ctripFlightService.doFlightByCtrip();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	 }
	
	/**
	 * 测试携程机票查询
	 * @author caiys
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestFlightList (){
		 
		 
		 FlightSearchHotelRequestType hotelRequest = new FlightSearchHotelRequestType();
		 
		
		 FlightSearchRequestBodyType bodyType = new FlightSearchRequestBodyType();
		 
		 FlightSearchRequestType searchRequest = new FlightSearchRequestType();
		 
		 searchRequest.setSendTicketCity("SHA");
		 searchRequest.setDirection("ASC");
		 searchRequest.setSearchType("S");
		 
		 FlightSearchPriceTypeOptionsType optionsType = new FlightSearchPriceTypeOptionsType();
		 optionsType.setString("NormalPrice");
		 searchRequest.setPriceTypeOptions(optionsType);
		 searchRequest.setIsLowestPrice("false");
		 searchRequest.setIsSimpleResponse("false");
//		 searchRequest.setProductTypeOptions("ProductTypeOptions");
		 searchRequest.setOrderBy("DepartTime");
		 
		 FlightSearchRoutesType routesType = new FlightSearchRoutesType();
		 
		 FlightSearchRouteType flightRoute = new FlightSearchRouteType();
		 flightRoute.setAirlineDibitCode("CA");
		 flightRoute.setDepartCity("SHA");
		 flightRoute.setArriveCity("BJS");
		 flightRoute.setDepartDate("2015-12-21");
		 flightRoute.setEarliestDepartTime("2015-12-21T00:00:00");
		 flightRoute.setLatestDepartTime("2015-12-21T12:00:00");
		 routesType.setFlightRoute(flightRoute);
		 
		 
		 searchRequest.setRoutes(routesType);
		 
		 
		 bodyType.setFlightSearchRequest(searchRequest);
		 hotelRequest.setRequestBody(bodyType);
		 FlightResponseType searchResponse = ctripFlightService.getFlightSearchResponse(hotelRequest);
		 System.out.println();
	 }
	 
	/**
		 * 抓去携程机票城市信息
		 * @author  dyy
		 * @date 2015年12月3日 下午1:43:29
		 */
		
		 public void saveOrUpdateCityInfos(){
			 
			 
			 GetCityInfosRequestType cityInfosRequest = new GetCityInfosRequestType();
			 
			 cityInfosRequest.setCityCode("");
			 cityInfosRequest.setCityId("");
			 cityInfosRequest.setCityName("");
			 
			 
			 ctripFltGetCityInfosService.saveOrUpdateCityInfos(cityInfosRequest);
			 
	
		 }
	/**
		 * 抓取携程机场信息
		 * @author  dyy
		 * @date 2015年12月3日 下午1:43:29
		 */
		
		 public void atestSaveCraftInfos(){
			 
			 
			 GetCraftInfosRequestType requestType = new GetCraftInfosRequestType();
			 
			 requestType.setCraftType("");
			 
			 ctripFltGetCraftInfosService.saveArilineInfo(requestType);
			 
	
		 }
	/**
		 * 测试携程机场信息
		 * @author  dyy
		 * @date 2015年12月3日 下午1:43:29
		 */
		
		 public void atestFltGetCraftInfos(){
			 
			 

			 
			 GetCraftInfosRequestType requestType = new GetCraftInfosRequestType();
			 
			 requestType.setCraftType("");
			 
			 List<GetCraftInfoEntityType> craftInfoEntityTypes =	 ctripFltGetCraftInfosService.getCraftInfosResponse(requestType);
			 
	
		 }
	/**
	 * 测试携程机票取消订单
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestFltGetCityInfos(){
		 
		 
	
		 
		 GetCityInfosRequestType cityInfosRequest = new GetCityInfosRequestType();
		 
		 cityInfosRequest.setCityCode("");
		 cityInfosRequest.setCityId("");
		 cityInfosRequest.setCityName("");
		 
		 
		 GetCityInfosListType cityInfosList =  ctripFltGetCityInfosService.getCityInfosResponse(cityInfosRequest);
		 
	
	 }
	/**
	 * 测试携程机场信息
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestFltGetAirportInfos(){
		 
		 
	
		 
		 GetAirportInfosRequestType requestType = new GetAirportInfosRequestType();
		 
		 requestType.setAirportCode("");
		 
		 List<GetAirportInfoEntityType> airportInfoEntityTypes =	 ctripFltGetAirportInfosService.getAriportInfosResponse(requestType);
		 
	
	 }
	/**
	 * 抓取携程机场信息
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestSaveAirportInfos(){
		 
		 
	
		 
		 GetAirportInfosRequestType requestType = new GetAirportInfosRequestType();
		 
		 requestType.setAirportCode("");
		 
		ctripFltGetAirportInfosService.saveAriportInfo(requestType);
		 
	
	 }
	/**
	 * 测试携程机场信息
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestFltGetAirlineInfos(){
		 
		 
	
		 
		 GetAirlineInfosRequestType requestType = new GetAirlineInfosRequestType();
		 
		 requestType.setAirLine("");
		 
		 List<GetAirlineInfoEntityType> airlineInfoEntityTypes =	 CtripFltGetAirlineInfosService.getArilineInfosResponse(requestType);
		 
	
	 }
	/**
	 * 抓取携程机场信息
	 * @author  dyy
	 * @date 2015年12月3日 下午1:43:29
	 */
	
	 public void atestSaveAirlineInfos(){
		 
	
		 
		 GetAirlineInfosRequestType requestType = new GetAirlineInfosRequestType();
		 
		 requestType.setAirLine("");
		 
		ctripFltGetAirlineInfosService.saveArilineInfo(requestType);
		 
	
	 }
	/**
		 * 测试携程门票景点数据获取
		 * @author caiys
		 * @date 2015年12月3日 下午1:43:29
		 */
		
		 public void atestCityCodeInfo(){
			 
			 List<String> cityCodeList = ctripFltGetCityInfosService.getCityCodeList();
			 
			 for(String str:cityCodeList){
				 
				 
			 }
			 
			 
			 
		 }

}
