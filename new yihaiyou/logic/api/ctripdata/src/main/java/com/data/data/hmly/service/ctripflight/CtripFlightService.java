package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.dao.CtripFlightDao;
import com.data.data.hmly.service.ctripflight.dao.FltCityInfoDao;
import com.data.data.hmly.service.ctripflight.entity.CtripFlightInfo;
import com.data.data.hmly.service.ctripflight.entity.FltCityInfo;
import com.data.data.hmly.service.ctripflight.entity.enums.InventoryType;
import com.data.data.hmly.service.ctripflight.entity.enums.PriceType;
import com.data.data.hmly.service.ctripflight.entity.enums.ProductType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchHotelRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchPriceTypeOptionsType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRequest;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRequestBodyType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRouteType;
import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchRoutesType;
import com.data.data.hmly.service.ctripflight.response.pojo.DomesticFlightDataType;
import com.data.data.hmly.service.ctripflight.response.pojo.FlightResponseType;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CtripFlightService {
	private Log log = LogFactory.getLog(this.getClass());
    
    private static final String FLIGHT_SEARCH_REQUEST_TYPE = "OTA_FlightSearch";


    private static final String FLIGHT_SEARCH_URL = "http://openapi.ctrip.com/Flight/DomesticFlight/OTA_FlightSearch.asmx";

    @Resource
	private FltCityInfoDao cityInfoDao;
    @Resource
	private CtripFlightDao fightDao;
	@Resource
	private PropertiesManager propertiesManager;
    
	
	
	public void saveFlightDataFromCtrip(String departCity,String arriveCity
			,String searchType,String earliestDepartTime,String lastestDepartTime
			,String departDate,String pricetTypeOpiton) throws ParseException{
		
		
		List<DomesticFlightDataType> flightDataTypes = getFlightList(departCity,arriveCity
				,searchType,earliestDepartTime,lastestDepartTime
				,departDate,pricetTypeOpiton);
		
		int count = fightDao.findAll().size();
		
		if(flightDataTypes.size()>0){
			for(DomesticFlightDataType dataType:flightDataTypes){
				count=count+1;
				CtripFlightInfo flightInfo = new CtripFlightInfo();
				flightInfo.setId(count);
				flightInfo.setArriveCityCode(dataType.getArriveCityCode());
				if(dataType.getAdultOilFee()!=null){
					flightInfo.setAdultOilFee(Integer.parseInt(dataType.getAdultOilFee()));
				}
				flightInfo.setAdultTax(dataType.getAdultTax());
				flightInfo.setAirlineCode(dataType.getAirlineCode());
				flightInfo.setAllowCPType(dataType.getAllowCPType());
				if(dataType.getAPortBuildingCheckInTime()!=null){
					
					flightInfo.setaPortBuildingCheckInTime(Integer.parseInt(dataType.getAPortBuildingCheckInTime()));
				}
				flightInfo.setaPortBuildingID(dataType.getAPortBuildingID());
				flightInfo.setaPortCode(dataType.getAPortCode());
				flightInfo.setArriveCityCode(dataType.getArriveCityCode());
				if(dataType.getArriveTime()!=null){
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
                    Date arriveTime = sdf.parse(dataType.getArriveTime());
//					String arriveTimeStr = dataType.getArriveTime();
//					arriveTimeStr = arriveTimeStr.replace("T", " ");
//					Date arriveTime = new Date(arriveTimeStr);
					flightInfo.setArriveTime(arriveTime);
				}
				if(dataType.getBabyOilFee()!=null){
					flightInfo.setBabyOilFee(Integer.parseInt(dataType.getBabyOilFee()));
				}
				flightInfo.setBabyStandardPrice(dataType.getBabyStandardPrice());
				flightInfo.setBabyTax(dataType.getBabyTax());
				if(dataType.getBeforeFlyDate()!=null){
					flightInfo.setBeforeFlyDate(Integer.parseInt(dataType.getBeforeFlyDate()));
				}
				if(dataType.getCanAirlineCounter()!=null){
					flightInfo.setCanAirlineCounter(Boolean.parseBoolean(dataType.getCanAirlineCounter()));
				}
				if(dataType.getCanNoDefer()!=null){
					flightInfo.setCanNoDefer(Boolean.parseBoolean(dataType.getCanNoDefer()));
				}
				flightInfo.setClazz(dataType.getClazz());
				if(dataType.getCanPost()!=null){
					flightInfo.setCanPost(Boolean.parseBoolean(dataType.getCanPost()));
				}
				if(dataType.getCanNoDefer()!=null){
					flightInfo.setCanNoDefer(Boolean.parseBoolean(dataType.getCanNoDefer()));
				}
				if(dataType.getCanSendGet()!=null){
					flightInfo.setCanSendGet(Boolean.parseBoolean(dataType.getCanSendGet()));
				}
				if(dataType.getCanSeparateSale()!=null){
					flightInfo.setCanSeparateSale(Boolean.parseBoolean(dataType.getCanSeparateSale()));
				}
				if(dataType.getCanUpGrade()!=null){
					flightInfo.setCanUpGrade(Boolean.parseBoolean(dataType.getCanUpGrade()));
				}
				if(dataType.getChildOilFee()!=null){
					flightInfo.setChildOilFee(Integer.parseInt(dataType.getChildOilFee()));
				}
				flightInfo.setChildStandardPrice(dataType.getChildStandardPrice());
				flightInfo.setChildTax(dataType.getChildTax());
				flightInfo.setCraftType(dataType.getCraftType());
				flightInfo.setDepartCityCode(dataType.getDepartCityCode());
				flightInfo.setDisplaySubclass(dataType.getDisplaySubclass());
				if(dataType.getDPortBuildingCheckInTime()!=null){
					flightInfo.setdPortBuildingCheckInTime(Integer.parseInt(dataType.getDPortBuildingCheckInTime()));
				}
				flightInfo.setdPortBuildingID(dataType.getDPortBuildingID());
				flightInfo.setdPortCode(dataType.getDPortCode());
				flightInfo.setEndnote(dataType.getEndnote());
				flightInfo.setFlight(dataType.getFlight());
				if(dataType.getIsFlyMan()!=null){
					flightInfo.setFlyMan(Boolean.parseBoolean(dataType.getIsFlyMan()));
				}
//				flightInfo.setInventoryType(InventoryType.valueOf(dataType.getInventoryType()));
				if(dataType.getInventoryType()!=null){
					if("FAV".equals(dataType.getInventoryType())){
						flightInfo.setInventoryType(InventoryType.FAV);
					}else{
						flightInfo.setInventoryType(InventoryType.FIX);
					}
				}
				
				if(dataType.getIsLowestCZSpecialPrice()!=null){
					flightInfo.setLowestCZSpecialPrice(Boolean.parseBoolean(dataType.getIsLowestCZSpecialPrice()));
				}
				if(dataType.getIsLowestPrice()!=null){
					flightInfo.setLowestPrice(Boolean.parseBoolean(dataType.getIsLowestPrice()));
				}
				flightInfo.setMealType(dataType.getMealType());
				flightInfo.setNeedApplyString(dataType.getNeedApplyString());
				flightInfo.setNonend(dataType.getNonend());
				flightInfo.setNonref(dataType.getNonref());
				flightInfo.setNonrer(dataType.getNonrer());
				if(dataType.getOnlyOwnCity()!=null){
					flightInfo.setOnlyOwnCity(Boolean.parseBoolean(dataType.getOnlyOwnCity()));
				}
				if(dataType.getOutOfAirlineCounterTime()!=null){
					flightInfo.setOutOfAirlineCounterTime(Boolean.parseBoolean(dataType.getOutOfAirlineCounterTime()));
				}
				if(dataType.getOutOfPostTime()!=null){
					flightInfo.setOutOfPostTime(Boolean.parseBoolean(dataType.getOutOfPostTime()));
				}
				if(dataType.getOutOfSendGetTime()!=null){
					flightInfo.setOutOfSendGetTime(Boolean.parseBoolean(dataType.getOutOfSendGetTime()));
				}
				flightInfo.setPolicyID(dataType.getPolicyID());
				flightInfo.setPrice(dataType.getPrice());
				
				
//				flightInfo.setPriceType(PriceType.valueOf(dataType.getPriceType()));
				if(dataType.getPriceType()!=null){
					if("NormalPrice".equals(dataType.getPriceType())){
						flightInfo.setPriceType(PriceType.NormalPrice);
					}else if("SingleTripPrice".equals(dataType.getPriceType())){
						flightInfo.setPriceType(PriceType.SingleTripPrice);
					}else{
						flightInfo.setPriceType(PriceType.CZSpecialPrice);
					}
				}
				flightInfo.setProductSource(dataType.getProductSource());
//				flightInfo.setProductType(ProductType.valueOf(dataType.getProductType()));
				if(dataType.getProductType()!=null){
					if("Normal".equals(dataType.getProductType())){
						flightInfo.setProductType(ProductType.Normal);
					}else if("YOUNGMAN".equals(dataType.getProductType())){
						flightInfo.setProductType(ProductType.YOUNGMAN);
					}else{
						flightInfo.setProductType(ProductType.OLDMAN);
					}
				}
				
				flightInfo.setPunctualityRate(dataType.getPunctualityRate());
				flightInfo.setQuantity(dataType.getQuantity());
				flightInfo.setRate(dataType.getRate());
				if(dataType.getIsRebate()!=null){
					flightInfo.setRebate(Boolean.parseBoolean(dataType.getIsRebate()));
				}
				flightInfo.setRebateAmount(dataType.getRebateAmount());
				flightInfo.setRebateCPCity(dataType.getRebateCPCity());
				flightInfo.setRecommend(dataType.getRecommend());
				flightInfo.setRefnote(dataType.getRefnote());
				flightInfo.setRefundFeeFormulaID(dataType.getRefundFeeFormulaID());
				flightInfo.setRemarks(dataType.getRemarks());
				flightInfo.setRernote(dataType.getRernote());
				if(dataType.getRouteIndex()!=null){
					flightInfo.setRouteIndex(Integer.parseInt(dataType.getRouteIndex()));
				}
				flightInfo.setStandardPrice(dataType.getStandardPrice());
				if(dataType.getStopTimes()!=null){
					flightInfo.setStopTimes(Integer.parseInt(dataType.getStopTimes()));
				}
				flightInfo.setSubClass(dataType.getSubClass());
				if(dataType.getTakeOffTime()!=null){
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
                    Date takeOffTime = sdf.parse(dataType.getTakeOffTime());
					
//					String takeOffTimeStr = dataType.getTakeOffTime();
//					takeOffTimeStr = takeOffTimeStr.replace("T", " ");
//					
//					Date takeOffTime = new Date(takeOffTimeStr);
					flightInfo.setTakeOffTime(takeOffTime);
				}
				flightInfo.setTicketType(dataType.getTicketType());
				
//				fightDao.setHibernateTemplate(hibernateTemplate);
				fightDao.save(flightInfo);
			}
		}
		
		
		
		
		
	}
	
	public List<DomesticFlightDataType> getFlightList(String departCity,String arriveCity
			,String searchType,String earliestDepartTime,String lastestDepartTime
			,String departDate,String pricetTypeOpiton){
		 
		 
		 FlightSearchHotelRequestType hotelRequest = new FlightSearchHotelRequestType();
		 
		
		 FlightSearchRequestBodyType bodyType = new FlightSearchRequestBodyType();
		 
		 FlightSearchRequestType searchRequest = new FlightSearchRequestType();
		 
//		 searchRequest.setSendTicketCity("SHA");
		 searchRequest.setDirection("ASC");
		 searchRequest.setSearchType(searchType);
		 
		 FlightSearchPriceTypeOptionsType optionsType = new FlightSearchPriceTypeOptionsType();
		 optionsType.setString(pricetTypeOpiton);
		 searchRequest.setPriceTypeOptions(optionsType);
//		 searchRequest.setIsLowestPrice("false");
//		 searchRequest.setIsSimpleResponse("false");
//		 searchRequest.setProductTypeOptions("ProductTypeOptions");
		 searchRequest.setOrderBy("DepartTime");
		 
		 FlightSearchRoutesType routesType = new FlightSearchRoutesType();
		 
		 FlightSearchRouteType flightRoute = new FlightSearchRouteType();
//		 flightRoute.setAirlineDibitCode("CA");
		 flightRoute.setDepartCity(departCity);
		 flightRoute.setArriveCity(arriveCity);
		 flightRoute.setDepartDate(departDate);
		 flightRoute.setEarliestDepartTime(earliestDepartTime);
		 flightRoute.setLatestDepartTime(lastestDepartTime);
		 routesType.setFlightRoute(flightRoute);
		 
		 searchRequest.setRoutes(routesType);
		 
		 bodyType.setFlightSearchRequest(searchRequest);
		 hotelRequest.setRequestBody(bodyType);
		 
		 FlightResponseType searchResponse =getFlightSearchResponse(hotelRequest);
		 
		 List<DomesticFlightDataType> dataTypes = new ArrayList<DomesticFlightDataType>();
		 if(searchResponse!=null){
			 if(searchResponse.getFlightSearchResponse()!=null){
				 if(searchResponse.getFlightSearchResponse().getFlightRoutes()!=null){
					 if(searchResponse.getFlightSearchResponse().getFlightRoutes().getDomesticFlightRoute()!=null){
						 if(searchResponse.getFlightSearchResponse().getFlightRoutes().getDomesticFlightRoute().getFlightsList()!=null){
							 if(searchResponse.getFlightSearchResponse().getFlightRoutes().getDomesticFlightRoute().getFlightsList().getDomesticFlightData()!=null){
								 dataTypes = searchResponse.getFlightSearchResponse().getFlightRoutes().getDomesticFlightRoute().getFlightsList().getDomesticFlightData();
								 return dataTypes;
							 }
						 }
					 }
				 }
			 }
		 }
		 
		 
		 return dataTypes;
		 
	 }
	
	public void doFlightByCtrip() throws ParseException{
		
		String[] searchTypeArr = new String[]{"S","D","M"};		//搜索条件：单程，联程，往返程
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		String dateStr = date.format(new Date());
		String timeStr = time.format(new Date());
		
		String earliestDepartTime = dateStr+"T"+timeStr;	//当前起飞时间
		String lastestDepartTime = dateStr+"T23:59:59";		//最晚起飞时间
		
		String departDate = date.format(new Date());	//出发日期
		
		String[] priceTypeArr = new String[]{"NormalPrice","SingleTripPrice"};		//价格类型
		List<DomesticFlightDataType> dataTypes = new ArrayList<DomesticFlightDataType>();	
		
		
		List<String> cityCodeList = getCityCodeList();
				
		for(String departCity:cityCodeList){
			
			for(String arriveCity:cityCodeList){
				
				if(!departCity.equals(arriveCity)){
//							searchFlightByCtrip( departCity, arriveCity);
					
					for(int j=0;j<searchTypeArr.length;j++){
						String searchType = searchTypeArr[j];
//						String searchType = "S";
							
						for(int i=0;i<priceTypeArr.length;i++){
							String pricetTypeOpiton = priceTypeArr[i];
								
							saveFlightDataFromCtrip(departCity,arriveCity
									,searchType,earliestDepartTime,lastestDepartTime
									,departDate,pricetTypeOpiton);		
							
						}
					}
					
					
					
					
				}
				
			}
			
		}
		
		
	}
	
	/**
	 * 设置不重复的出发城市和到达城市
	 */
	public void setDepartAndArriveCity(){
		List<String> cityCodeList = getCityCodeList();
		
		for(String departCity:cityCodeList){
			
			for(String arriveCity:cityCodeList){
				
				if(!departCity.equals(arriveCity)){
//					searchFlightByCtrip( departCity, arriveCity);
				}
				
			}
			
		}
		
	}
	
	/**
 	 * 获取城市三字码
 	 * @return
 	 */
	public List<String> getCityCodeList() {
		
		
		List<FltCityInfo> cityInfos = cityInfoDao.getCityCodeList();
		
		List<String> cityCodeList = new ArrayList<String>();
		
		for(FltCityInfo cityInfo:cityInfos){
			if(cityInfo.getCityCode()!=null){
				cityCodeList.add(cityInfo.getCityCode());
			}
		}
		
		return cityCodeList;
	}
	 	
	    /**
	     * @param searchRequest 携程的航班信息请求类型
	     * @return 航班信息
	     */
	    public static FlightResponseType getFlightSearchResponse(FlightSearchHotelRequestType hotelRequestType) {
	    	
	        FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
	        flightSearchRequest.setHeader(CtripRequestService.getRequestHead(FLIGHT_SEARCH_REQUEST_TYPE));
	        flightSearchRequest.setHotelRequest(hotelRequestType);
	        try {
	            JAXBContext context = JAXBContext.newInstance(FlightSearchRequest.class);
	            //将请求转换为xml
	            Marshaller marshaller =  context.createMarshaller();
	            StringWriter requestData = new StringWriter();
	            marshaller.marshal(flightSearchRequest, requestData);
	            //提交请求，获得xml响应
	            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_SEARCH_URL,requestData.toString());
	            FlightResponseType searchResponse = null;
	            if(xmlResponse!=null){
	            	
	            	xmlResponse = xmlResponse.replaceAll("&lt;", "<");
	            	xmlResponse = xmlResponse.replaceAll("&gt;", ">");
	            	
	            	
	            	JAXBContext searchResponseContext = JAXBContext.newInstance(FlightResponseType.class);
	    			Unmarshaller unmarshaller = searchResponseContext.createUnmarshaller();
	    			searchResponse = (FlightResponseType) unmarshaller.unmarshal(new StringReader(xmlResponse));
	    			
	            }
	            
	            
	            return searchResponse;
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	
	
}
