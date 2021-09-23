package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.request.pojo.FltViewOrderRequest;
import com.data.data.hmly.service.ctripflight.request.pojo.FltViewOrderRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.FlightResponseType;
import com.data.data.hmly.service.ctripflight.response.pojo.FltViewOrderResponse;
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

@Service
public class CtripFltViewOrderService {
	private Log log = LogFactory.getLog(this.getClass());
    
    private static final String FLIGHT_VIVEORDER_TYPE = "OTA_FltViewOrder";

    private static final String FLIGHT_FLTVIEWORDER_URL = "http://openapi.ctrip.com/Flight/DomesticFlight/OTA_FltViewOrder.asmx";

	@Resource
	private PropertiesManager propertiesManager;
    
	
	
	 	
	    /**
	     * @param searchRequest 携程的航班信息请求类型
	     * @return 航班信息
	     */
	    public static FltViewOrderResponse getFltViewOrderResponse(FltViewOrderRequestType viewOrderRequestType) {
	    	
	       FltViewOrderRequest viewOrderRequest = new FltViewOrderRequest();
	       viewOrderRequest.setHeader(CtripRequestService.getFltViewOrder(FLIGHT_VIVEORDER_TYPE));
	       viewOrderRequest.setFltViewOrderRequest(viewOrderRequestType);
	        try {
	            JAXBContext context = JAXBContext.newInstance(FltViewOrderRequest.class);
	            //将请求转换为xml
	            Marshaller marshaller =  context.createMarshaller();
	            StringWriter requestData = new StringWriter();
	            marshaller.marshal(viewOrderRequest, requestData);
	            //提交请求，获得xml响应
	            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_FLTVIEWORDER_URL,requestData.toString());
	            FltViewOrderResponse viewOrderResponse = null;
	            if(xmlResponse!=null){
	            	
	            	xmlResponse = xmlResponse.replaceAll("&lt;", "<");
	            	xmlResponse = xmlResponse.replaceAll("&gt;", ">");
	            	
	            	
	            	JAXBContext searchResponseContext = JAXBContext.newInstance(FlightResponseType.class);
	    			Unmarshaller unmarshaller = searchResponseContext.createUnmarshaller();
	    			viewOrderResponse = (FltViewOrderResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
	    			
	            }
	            return viewOrderResponse;
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	
	
    
    
}
