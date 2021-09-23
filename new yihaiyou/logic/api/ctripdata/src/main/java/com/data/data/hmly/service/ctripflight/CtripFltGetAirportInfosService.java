package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.dao.FltAirportInfoDao;
import com.data.data.hmly.service.ctripflight.entity.AirportInfo;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirportInfosRequest;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirportInfosRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetAirportInfoEntityType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetAirportInfosResponseType;
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
public class CtripFltGetAirportInfosService {
	private Log log = LogFactory.getLog(this.getClass());
    
    private static final String FLIGHT_AIRPORTINFOS_TYPE = "OTA_FltGetAirportInfos";
    private static final String FLIGHT_AIRPORTINFOS_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetAirportInfos.asmx";
	@Resource
	private PropertiesManager propertiesManager;
	@Resource
	private FltAirportInfoDao airportDao;
	 	
	    /**
	     * @param cityInfosList 查询机票机场信息 
	     * @return 机场结果
	     */
	    public static  List<GetAirportInfoEntityType> getAriportInfosResponse(GetAirportInfosRequestType requestType) {
	    	
	    	GetAirportInfosRequest request = new GetAirportInfosRequest();
	    	request.setHeader(CtripRequestService.getAirportInfosHeader(FLIGHT_AIRPORTINFOS_TYPE));
	    	request.setGetAirportInfosRequest(requestType);
	        try {
	            JAXBContext context = JAXBContext.newInstance(GetAirportInfosRequest.class);
	            //将请求转换为xml
	            Marshaller marshaller =  context.createMarshaller();
	            StringWriter requestData = new StringWriter();
	            marshaller.marshal(request, requestData);
	            //提交请求，获得xml响应
	            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_AIRPORTINFOS_URL,requestData.toString());
	            GetAirportInfosResponseType response = null;
	            List<GetAirportInfoEntityType> cityInfosList = new ArrayList<GetAirportInfoEntityType>();
	            if(xmlResponse!=null){
	            	
	            	xmlResponse = xmlResponse.replaceAll("&lt;", "<");
	            	xmlResponse = xmlResponse.replaceAll("&gt;", ">");
	            	
//	            	xmlResponse = RemoveSoapShell(xmlResponse);
	            	
	            	JAXBContext responseContext = JAXBContext.newInstance(GetAirportInfosResponseType.class);
	    			Unmarshaller unmarshaller = responseContext.createUnmarshaller();
	    			response = (GetAirportInfosResponseType) unmarshaller.unmarshal(new StringReader(xmlResponse));
//	    			List<GetCityInfoEntityType> infoList = new ArrayList<GetCityInfoEntityType>();
	    			
	    			if(response != null){
	    				cityInfosList = response.getGetAirportInfos().getAirportInfosList().getAirportInfoEntity();
	    			}
	            }
	            
	            return cityInfosList;
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

		public void saveAriportInfo(
				GetAirportInfosRequestType requestType) {
			
			
			 List<GetAirportInfoEntityType> cityInfosList = getAriportInfosResponse(requestType);
			 int i=1;
			 for(GetAirportInfoEntityType airportInfo:cityInfosList){
				 
				 AirportInfo entityType = new AirportInfo();
				 entityType.setId(i);
				 entityType.setAirPort(airportInfo.getAirPort());
				 entityType.setAirPortName(airportInfo.getAirPortName());
				 entityType.setCityName(airportInfo.getCityName());
				 entityType.setShortName(airportInfo.getShortName());
				 if(airportInfo.getCityId()!=null){
					 entityType.setCityId(Integer.parseInt(airportInfo.getCityId()));
				 }
				 airportDao.save(entityType);
				 i=i+1;
			 }
			
			
		}
	
    
    
}
