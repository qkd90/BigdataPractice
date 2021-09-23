package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.request.pojo.FltCancelOrderRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltCancelRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.FltCancelOrderRequestResultType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Service
public class CtripFltCancelOrderService {
    private Log log = LogFactory.getLog(this.getClass());
    private static final String TICKET_URL = "http://openapi.ctrip.com/vacations/OpenServer.ashx";


    private static final String FLIGHT_CANCELORDER_TYPE = "OTA_FltCancelOrder";


    private static final String FLIGHT_CANCELORDER_URL = "http://openapi.ctrip.com/Flight/DomesticFlight/OTA_FltCancelOrder.asmx";

    /**
     * @param searchRequest 携程机票取消订单
     * @return 取消订单结果
     */
    public static FltCancelOrderRequestResultType getCancelFltOrderResponse(FltCancelOrderRequestType cancelOrderRequest) {

        FltCancelRequestType cancelRequest = new FltCancelRequestType();
        cancelRequest.setHeader(CtripRequestService.getCancelOrderHead(FLIGHT_CANCELORDER_TYPE));
        cancelRequest.setFltCancelOrderRequest(cancelOrderRequest);
        try {
            JAXBContext context = JAXBContext.newInstance(FltCancelRequestType.class);
            //将请求转换为xml
            Marshaller marshaller = context.createMarshaller();
            StringWriter requestData = new StringWriter();
            marshaller.marshal(cancelRequest, requestData);
            //提交请求，获得xml响应
            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_CANCELORDER_URL, requestData.toString());
            FltCancelOrderRequestResultType cancelOrderResponse = null;
//	            FltSaveOrderResponseType fltSaveOrderResponse = null;
            if (xmlResponse != null) {

                xmlResponse = xmlResponse.replaceAll("&lt;", "<");
                xmlResponse = xmlResponse.replaceAll("&gt;", ">");

                JAXBContext fltCancelOrderResultContext = JAXBContext.newInstance(FltCancelOrderRequestResultType.class);
                Unmarshaller unmarshaller = fltCancelOrderResultContext.createUnmarshaller();
                cancelOrderResponse = (FltCancelOrderRequestResultType) unmarshaller.unmarshal(new StringReader(xmlResponse));

                if (cancelOrderResponse != null) {
//	    				fltSaveOrderResponse = cancelOrderResponse.getFltSaveOrderResponse();
                }
            }

            return cancelOrderResponse;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
