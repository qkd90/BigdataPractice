package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.dao.FltCityInfoDao;
import com.data.data.hmly.service.ctripflight.entity.FltCityInfo;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCityInfosRequest;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCityInfosRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCityInfoEntityType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCityInfosListType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCityInfosRequestResultType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCityInfosResponse;
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
public class CtripFltGetCityInfosService {
	private Log log = LogFactory.getLog(this.getClass());
    private static final String TICKET_INTERFACE = "TicketSenicSpotSearch";
    private static final String TICKET_URL = "http://openapi.ctrip.com/vacations/OpenServer.ashx";
    
    private static final String FLIGHT_FltCITYINFOS_TYPE = "OTA_FltGetCityInfos";
    private static final String FLIGHT_CITYINFOS_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetCityInfos.asmx";
	@Resource
	private PropertiesManager propertiesManager;
	@Resource
	private FltCityInfoDao fltCityInfoDao;
	 	
		
		
	
	
	
	    /**
	     * @param cityInfosList 查询机票城市信息 
	     * @return 城市结果
	     */
	    public static GetCityInfosListType getCityInfosResponse(GetCityInfosRequestType cityInfosRequestType) {
	    	
	    	GetCityInfosRequest cityInfosRequest = new GetCityInfosRequest();
	    	cityInfosRequest.setHeader(CtripRequestService.getCityInfosHeader(FLIGHT_FltCITYINFOS_TYPE));
	    	cityInfosRequest.setGetCityInfosRequest(cityInfosRequestType);
	        try {
	            JAXBContext context = JAXBContext.newInstance(GetCityInfosRequest.class);
	            //将请求转换为xml
	            Marshaller marshaller =  context.createMarshaller();
	            StringWriter requestData = new StringWriter();
	            marshaller.marshal(cityInfosRequest, requestData);
	            //提交请求，获得xml响应
	            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_CITYINFOS_URL,requestData.toString());
	            GetCityInfosResponse response = null;
	            GetCityInfosListType cityInfosList = null;
	            if(xmlResponse!=null){
	            	
	            	xmlResponse = xmlResponse.replaceAll("&lt;", "<");
	            	xmlResponse = xmlResponse.replaceAll("&gt;", ">");
	            	
//	            	xmlResponse = RemoveSoapShell(xmlResponse);
	            	
	            	JAXBContext responseContext = JAXBContext.newInstance(GetCityInfosRequestResultType.class);
	    			Unmarshaller unmarshaller = responseContext.createUnmarshaller();
	    			response = (GetCityInfosResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
	    			List<GetCityInfoEntityType> infoList = new ArrayList<GetCityInfoEntityType>();
	    			
	    			if(response != null){
	    				cityInfosList = response.getGetCityInfosResponse().getCityInfosList();
	    			}
	            }
	            
	            return cityInfosList;
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	    /**
	     * 批量保存机票城市信息
	     * @param cityInfosRequestType
	     */
	    public void saveOrUpdateCityInfos(GetCityInfosRequestType cityInfosRequestType){
	    	
	    	 GetCityInfosListType cityInfosList = getCityInfosResponse(cityInfosRequestType);
	    	 
	    	 if(cityInfosList!=null){
	    		 int i=0;
	    		 for(GetCityInfoEntityType info : cityInfosList.getCityInfoEntity()){
	    			 
	    			 FltCityInfo cityInfo = new FltCityInfo();
	    			 if(info.getCityId()!=null){
	    				 cityInfo.setId(Integer.parseInt(info.getCityId()));
	    				 cityInfo.setCityId(Integer.parseInt(info.getCityId()));
	    			 }
	    			 if(info.getCountryId()!=null){
	    				 cityInfo.setCountryId(Integer.parseInt(info.getCountryId()));
	    			 }
	    			 if(info.getIsDomesticCity()!=null){
	    				 cityInfo.setDomesticCity(Boolean.parseBoolean(info.getIsDomesticCity()));
	    			 }
	    			 if(info.getProvinceId()!=null){
	    				 cityInfo.setProvinceId(Integer.parseInt(info.getProvinceId())); 
	    			 }
	    			 
	    			 cityInfo.setCityCode(info.getCityCode());
	    			 
	    			 cityInfo.setCityName(info.getCityName());
	    			 cityInfo.setCountryName(info.getCountryName());
	    			 
//	    			 System.out.println("i="+(i++)+",CityName="+info.getCityName()+",CityId="+info.getCityId());
	    			 
	    			 fltCityInfoDao.save(cityInfo);
	    			 
	    		 }
	    		 
	    	 }
	    	
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

	 	/**
	 	 * 获取城市三字码
	 	 * @return
	 	 */
		public List<String> getCityCodeList() {
			
			
			List<FltCityInfo> cityInfos = fltCityInfoDao.getCityCodeList();
			
			List<String> cityCodeList = new ArrayList<String>();
			
			for(FltCityInfo cityInfo:cityInfos){
				if(cityInfo.getCityCode()!=null){
					cityCodeList.add(cityInfo.getCityCode());
				}
			}
			
			return cityCodeList;
		}
	
    
    
}
