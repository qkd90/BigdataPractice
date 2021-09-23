package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.dao.FltCraftInfoDao;
import com.data.data.hmly.service.ctripflight.entity.CraftInfo;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCraftInfosRequest;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCraftInfosRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCraftInfoEntityType;
import com.data.data.hmly.service.ctripflight.response.pojo.GetCraftInfosResponse;
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
public class CtripFltGetCraftInfosService {
	private Log log = LogFactory.getLog(this.getClass());
    
    private static final String FLIGHT_CRAFTINFOS_TYPE = "OTA_FltGetCraftInfos"; 
    private static final String FLIGHT_CRAFTINFOS_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetCraftInfos.asmx";
	@Resource
	private PropertiesManager propertiesManager;
	@Resource
	private FltCraftInfoDao craftDao;
	 	
	    /**
	     * @param cityInfosList 查询机票机场信息 
	     * @return 机场结果
	     */
	    public static   List<GetCraftInfoEntityType>  getCraftInfosResponse(GetCraftInfosRequestType requestType) {
	    	
	    	GetCraftInfosRequest request = new GetCraftInfosRequest();
	    	request.setHeader(CtripRequestService.getCraftInfosHeader(FLIGHT_CRAFTINFOS_TYPE));
	    	request.setGetCraftInfosRequest(requestType);
	        try {
	            JAXBContext context = JAXBContext.newInstance(GetCraftInfosRequest.class);
	            //将请求转换为xml
	            Marshaller marshaller =  context.createMarshaller();
	            StringWriter requestData = new StringWriter();
	            marshaller.marshal(request, requestData);
	            //提交请求，获得xml响应
	            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_CRAFTINFOS_URL,requestData.toString());
	            GetCraftInfosResponse response = null;
	            List<GetCraftInfoEntityType> infosList = new ArrayList<GetCraftInfoEntityType>();
	            if(xmlResponse!=null){
	            	
	            	xmlResponse = xmlResponse.replaceAll("&lt;", "<");
	            	xmlResponse = xmlResponse.replaceAll("&gt;", ">");
	            	
//	            	xmlResponse = RemoveSoapShell(xmlResponse);
	            	
	            	JAXBContext responseContext = JAXBContext.newInstance(GetCraftInfosResponse.class);
	    			Unmarshaller unmarshaller = responseContext.createUnmarshaller();
	    			response = (GetCraftInfosResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
	    			
	    			if(response != null){
	    				infosList = response.getGetCraftInfos().getCraftInfosList().getCraftInfoEntity();
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
				GetCraftInfosRequestType requestType) {
			
			
			List<GetCraftInfoEntityType> infosList = getCraftInfosResponse(requestType);
			 int i=1;
			 for(GetCraftInfoEntityType craftType:infosList){
				 CraftInfo craftInfo = craftDao.load(i);
				 
				 if(craftInfo!=null){
//					 craftInfo.setId(i);
					 craftInfo.setCraftKind(craftType.getCraftKind());
					 craftInfo.setCraftType(craftType.getCraftType());
					 craftInfo.setCraftTypeName(craftType.getCraftTypeName());
					 craftInfo.setMaxSeats(craftType.getMaxSeats());
					 craftInfo.setMinSeats(craftType.getMinSeats());
					 craftInfo.setNote(craftType.getNote());
					 craftInfo.setWidthLevel(craftType.getWidthLevel());
					 craftDao.update(craftInfo);
				 }else{
					 craftInfo = new CraftInfo();
				 
					 craftInfo.setId(i);
					 craftInfo.setCraftKind(craftType.getCraftKind());
					 craftInfo.setCraftType(craftType.getCraftType());
					 craftInfo.setCraftTypeName(craftType.getCraftTypeName());
					 craftInfo.setMaxSeats(craftType.getMaxSeats());
					 craftInfo.setMinSeats(craftType.getMinSeats());
					 craftInfo.setNote(craftType.getNote());
					 craftInfo.setWidthLevel(craftType.getWidthLevel());
					 craftDao.save(craftInfo);
				 }
				 i=i+1;
			 }
			
			
		}
	
    
    
}
