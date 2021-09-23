package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.dao.FltAirlineInfoDao;
import com.data.data.hmly.service.ctripflight.entity.AirlineInfo;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirlineInfosRequest;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirlineInfosRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetAirlineInfoEntityType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetAirlineInfosResponse;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class CtripFltGetAirlineInfosService {
	private Log log = LogFactory.getLog(this.getClass());
    
    private static final String FLIGHT_AIRLINEINFOS_TYPE = "OTA_FltGetAirlineInfos"; 
    private static final String FLIGHT_AIRLINEINFOS_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetAirlineInfos.asmx";
	@Resource
	private PropertiesManager propertiesManager;
	@Resource
	private FltAirlineInfoDao airlineDao;
	 	
	    /**
	     * @param cityInfosList 查询机票机场信息 
	     * @return 机场结果
	     */
	    public static   List<GetAirlineInfoEntityType>  getArilineInfosResponse(GetAirlineInfosRequestType requestType) {
	    	
	    	GetAirlineInfosRequest request = new GetAirlineInfosRequest();
	    	request.setHeader(CtripRequestService.getAirlineInfosHeader(FLIGHT_AIRLINEINFOS_TYPE));
	    	request.setGetAirlineInfosRequest(requestType);
	        try {
	            JAXBContext context = JAXBContext.newInstance(GetAirlineInfosRequest.class);
	            //将请求转换为xml
	            Marshaller marshaller =  context.createMarshaller();
	            StringWriter requestData = new StringWriter();
	            marshaller.marshal(request, requestData);
	            //提交请求，获得xml响应
	            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_AIRLINEINFOS_URL,requestData.toString());
	            GetAirlineInfosResponse response = null;
	            List<GetAirlineInfoEntityType> infosList = new ArrayList<GetAirlineInfoEntityType>();
	            if(xmlResponse!=null){
	            	
	            	xmlResponse = xmlResponse.replaceAll("&lt;", "<");
	            	xmlResponse = xmlResponse.replaceAll("&gt;", ">");
	            	
//	            	xmlResponse = RemoveSoapShell(xmlResponse);
	            	
	            	JAXBContext responseContext = JAXBContext.newInstance(GetAirlineInfosResponse.class);
	    			Unmarshaller unmarshaller = responseContext.createUnmarshaller();
	    			response = (GetAirlineInfosResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
//	    			List<GetCityInfoEntityType> infoList = new ArrayList<GetCityInfoEntityType>();
	    			
	    			if(response != null){
	    				infosList = response.getGetAirlineInfosResponse().getAirlineInfosList().getAirlineInfoEntity();
	    			}
	            }
	            
	            return infosList;
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	    
	
	 	private static String RemoveSoapShell(String source)
		{
			String result = "";
			int indexElementBegin = source.indexOf("<RequestResult>");
			int indexElementEnd = source.indexOf("</RequestResult>");
			if(indexElementBegin > 0 && indexElementEnd > 0)
			{
				result = source.substring(indexElementBegin + "<RequestResult>".length(), indexElementEnd);
			}
			return result;
		}

		public void saveArilineInfo(
				GetAirlineInfosRequestType requestType) {
			
			
			List<GetAirlineInfoEntityType> infosList = getArilineInfosResponse(requestType);
			 int i=1;
			 for(GetAirlineInfoEntityType airlineType:infosList){
				 AirlineInfo airline = airlineDao.load(i);
				 
				 if(airline!=null){
					 airline.setAddonPriceprivate(airlineType.getAddonPriceProtected());
					 airline.setAirLine(airlineType.getAirLine());
					 airline.setAirLineName(airlineType.getAirLineName());
					 airline.setAirLineCode(airlineType.getAirLineCode());
					 airline.setOnlineCheckinUrl(airlineType.getOnlineCheckinUrl());
					 airline.setShortName(airlineType.getShortName());
					 airline.setStrictType(airlineType.getStrictType());
					 if(airlineType.getIsSupportAirPlus()!=null){
						 airline.setSupportAirPlus(Boolean.parseBoolean(airlineType.getIsSupportAirPlus()));
					 }
					 
					 if(airlineType.getGroupId()!=null){
						 airline.setGroupId(Integer.parseInt(airlineType.getGroupId()));
					 }
					 airlineDao.update(airline);
				 }else{
					 airline = new AirlineInfo();
				 
					 airline.setAddonPriceprivate(airlineType.getAddonPriceProtected());
					 airline.setAirLine(airlineType.getAirLine());
					 airline.setAirLineName(airlineType.getAirLineName());
					 airline.setAirLineCode(airlineType.getAirLineCode());
					 airline.setOnlineCheckinUrl(airlineType.getOnlineCheckinUrl());
					 airline.setShortName(airlineType.getShortName());
					 airline.setStrictType(airlineType.getStrictType());
					 if(airlineType.getIsSupportAirPlus()!=null){
						 airline.setSupportAirPlus(Boolean.parseBoolean(airlineType.getIsSupportAirPlus()));
					 }
					 airline.setId(i);
					 if(airlineType.getGroupId()!=null){
						 airline.setGroupId(Integer.parseInt(airlineType.getGroupId()));
					 }
					 
					 
					 
					 airlineDao.save(airline);
				 }
				 i=i+1;
			 }
			
			
		}
	
    
    
}
