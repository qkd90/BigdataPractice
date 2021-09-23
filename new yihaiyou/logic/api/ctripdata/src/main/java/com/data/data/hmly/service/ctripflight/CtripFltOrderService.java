package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.dao.CtripContactInfoDao;
import com.data.data.hmly.service.ctripflight.dao.CtripCreditCardDao;
import com.data.data.hmly.service.ctripflight.dao.CtripFlightDao;
import com.data.data.hmly.service.ctripflight.dao.CtripOrderFlightDao;
import com.data.data.hmly.service.ctripflight.dao.CtripOrderFlightInfoDao;
import com.data.data.hmly.service.ctripflight.dao.CtripOrderInfoDao;
import com.data.data.hmly.service.ctripflight.dao.CtripOrderPjsDao;
import com.data.data.hmly.service.ctripflight.dao.FltCityInfoDao;
import com.data.data.hmly.service.ctripflight.entity.FltOrderDeliverInfo;
import com.data.data.hmly.service.ctripflight.entity.FltOrderFlight;
import com.data.data.hmly.service.ctripflight.entity.FltOrderInfo;
import com.data.data.hmly.service.ctripflight.entity.OrderContactInfo;
import com.data.data.hmly.service.ctripflight.entity.OrderCreditCardInfo;
import com.data.data.hmly.service.ctripflight.entity.OrderFlightInfo;
import com.data.data.hmly.service.ctripflight.entity.OrderPJS;
import com.data.data.hmly.service.ctripflight.entity.enums.OrderType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderFlightInfoRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderRequestType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltSaveOrderRequestType;
import com.data.data.hmly.service.ctripflight.response.pojo.FltSaveOrderResponseType;
import com.data.data.hmly.service.ctripflight.response.pojo.FtlOrderResponseType;
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
import java.util.Date;

@Service
public class CtripFltOrderService {
	private Log log = LogFactory.getLog(this.getClass());

    private static final String FLIGHT_SAVEORDER_REQUEST_TYPE = "OTA_FltSaveOrder";
    private static final String FLIGHT_SAVEORDER_URL = "http://openapi.ctrip.com/Flight/DomesticFlight/OTA_FltSaveOrder.asmx";


	@Resource
	private CtripCreditCardDao ctripCreditCardDao;
	@Resource
	private CtripOrderFlightDao orderFlightDao;
	@Resource
	private CtripOrderFlightInfoDao orderFlightInfoDao;
	@Resource
	private CtripOrderPjsDao orderPjsDao;
	@Resource
	private CtripOrderInfoDao orderInfoDao;
	@Resource
	private FltCityInfoDao cityInfoDao;
	@Resource
	private CtripFlightDao flightDao;
	@Resource
	private CtripContactInfoDao contactInfoDao;
    
	@Resource
	private PropertiesManager propertiesManager;
    
	 	
	    /**
	     * @param searchRequest 携程机票订单生成程
	     * @return 订单结果
	     */
	    public  FltSaveOrderResponseType getSaveFlightOrderResponse(FltSaveOrderRequestType fltSaveOrderRequest) {
	    	
	    	FltOrderRequestType orderRequest = new FltOrderRequestType();
	    	orderRequest.setHeader(CtripRequestService.getOrderHead(FLIGHT_SAVEORDER_REQUEST_TYPE));
	    	orderRequest.setFltSaveOrderRequest(fltSaveOrderRequest);
	        try {
	            JAXBContext context = JAXBContext.newInstance(FltOrderRequestType.class);
	            //将请求转换为xml
	            Marshaller marshaller =  context.createMarshaller();
	            StringWriter requestData = new StringWriter();
	            marshaller.marshal(orderRequest, requestData);
	            //提交请求，获得xml响应
	            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_SAVEORDER_URL,requestData.toString());
	            FtlOrderResponseType orderResponse = null;
	            FltSaveOrderResponseType fltSaveOrderResponse = null;
	            if(xmlResponse!=null){
	            	
	            	xmlResponse = xmlResponse.replaceAll("&lt;", "<");
	            	xmlResponse = xmlResponse.replaceAll("&gt;", ">");
	            	
	            	JAXBContext orderResponseContext = JAXBContext.newInstance(FtlOrderResponseType.class);
	    			Unmarshaller unmarshaller = orderResponseContext.createUnmarshaller();
	    			orderResponse = (FtlOrderResponseType) unmarshaller.unmarshal(new StringReader(xmlResponse));
	    			
	    			
	    			if(orderResponse != null){
	    				fltSaveOrderResponse = orderResponse.getFltSaveOrderResponse();
	    				saveOrder(fltSaveOrderRequest);		//保存订单
	    			}
	            }
	            
	            return fltSaveOrderResponse;
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	    
	    
	    /**
	     * 
	     * @param orderRequest
	     */
	    public  void saveOrder(FltSaveOrderRequestType orderRequest){
	    	
	    	/***********************订单基本信息******************************/
	    	int orderCount = orderInfoDao.findAll().size();
	    	FltOrderInfo orderInfo = new FltOrderInfo();
	    	orderInfo.setId(orderCount+1);
	    	orderInfo.setAmount(Integer.parseInt(orderRequest.getAmount()));
	    	orderInfo.setUserId(orderRequest.getUID());
	    	
	    	if("ADU".equals(orderRequest.getOrderType())){
	    		orderInfo.setOrderType(OrderType.ADU);
	    	}else if("CHI".equals(orderRequest.getOrderType())){
	    		orderInfo.setOrderType(OrderType.CHI);
	    	}else{
	    		orderInfo.setOrderType(OrderType.BAB);
	    	}
	    	orderInfo.setCreateBy(1L);
	    	orderInfo.setCreateTime(new Date());
	    	orderInfo.setUpdateTime(new Date());
	    	
	    		/***********************订单联系人******************************/
		    	int contactCount = contactInfoDao.findAll().size();
		    	OrderContactInfo contactInfo = new OrderContactInfo();
		    	contactInfo.setId(contactCount+1);
		    	contactInfo.setConfirmOption(orderRequest.getContactInfo().getConfirmOption());
		    	contactInfo.setContactEMail(orderRequest.getContactInfo().getContactEMail());
		    	contactInfo.setContactFax(orderRequest.getContactInfo().getContactFax());
		    	contactInfo.setContactName(orderRequest.getContactInfo().getContactName());
		    	contactInfo.setContactTel(orderRequest.getContactInfo().getContactTel());
		    	contactInfo.setForeignMobile(orderRequest.getContactInfo().getForeignMobile());
		    	contactInfo.setMobileCountryFix(orderRequest.getContactInfo().getMobileCountryFix());
		    	contactInfo.setMobilePhone(orderRequest.getContactInfo().getMobilePhone());
		    	
		    	contactInfoDao.save(contactInfo);
	    	orderInfo.setContactId(contactInfo.getId());
	    	
	    		/***********************配送信息******************************/
		    	FltOrderDeliverInfo deliverInfo = new FltOrderDeliverInfo();
		    	
		    	deliverInfo.setDeliveryType(orderRequest.getDeliverInfo().getDeliveryType());
		    	deliverInfo.setOrderRemark(orderRequest.getDeliverInfo().getOrderRemark());
		    	deliverInfo.setSendTicketCityID(orderRequest.getDeliverInfo().getSendTicketCityID());
		    	/***********************邮寄行程单-邮寄信息实体******************************/
		    		OrderPJS pjs = new OrderPJS();
		    		int pjsCount = orderPjsDao.findAll().size();
		    		pjs.setId(pjsCount+1);
		    		pjs.setAddress(orderRequest.getDeliverInfo().getPJS().getAddress());
		    		pjs.setCanton(orderRequest.getDeliverInfo().getPJS().getCanton());
		    		pjs.setCity(orderRequest.getDeliverInfo().getPJS().getCity());
		    		pjs.setPostCode(orderRequest.getDeliverInfo().getPJS().getPostCode());
		    		pjs.setProvince(orderRequest.getDeliverInfo().getPJS().getProvince());
		    		pjs.setReceiver(orderRequest.getDeliverInfo().getPJS().getReceiver());
		    		orderPjsDao.save(pjs);
		    		
		    	deliverInfo.setPjsid(pjs.getId());
	    	
		    	/***********************支付信息******************************/
		    	OrderCreditCardInfo creditCard = new OrderCreditCardInfo();
		    	int creditCardCount = ctripCreditCardDao.findAll().size();
		    	creditCard.setId(creditCardCount+1);
		    	creditCard.setAgreementCode(orderRequest.getPayInfo().getCreditCardInfo().getAgreementCode());
		    	creditCard.setCardBin(orderRequest.getPayInfo().getCreditCardInfo().getCardBin());
		    	creditCard.setCardHolder(orderRequest.getPayInfo().getCreditCardInfo().getCardHolder());
		    	creditCard.setCardInfoID(orderRequest.getPayInfo().getCreditCardInfo().getCardInfoID());
		    	creditCard.setcCardPayFee(orderRequest.getPayInfo().getCreditCardInfo().getCCardPayFee());
		    	creditCard.setcCardPayFeeRate(orderRequest.getPayInfo().getCreditCardInfo().getCCardPayFeeRate());
		    	creditCard.setCreditCardNumber(orderRequest.getPayInfo().getCreditCardInfo().getCreditCardNumber());
		    	creditCard.setCreditCardType(orderRequest.getPayInfo().getCreditCardInfo().getCreditCardType());
		    	creditCard.setCvv2No(orderRequest.getPayInfo().getCreditCardInfo().getCVV2No());
		    	creditCard.setEid(orderRequest.getPayInfo().getCreditCardInfo().getEid());
		    	creditCard.setExchangeRate(orderRequest.getPayInfo().getCreditCardInfo().getExchangeRate());
		    	creditCard.setExponent(orderRequest.getPayInfo().getCreditCardInfo().getExponent());
		    	creditCard.setfAmount(orderRequest.getPayInfo().getCreditCardInfo().getFAmount());
		    	creditCard.setIdCardType(orderRequest.getPayInfo().getCreditCardInfo().getIdCardType());
		    	creditCard.setIsClient(orderRequest.getPayInfo().getCreditCardInfo().getIsClient());
		    	creditCard.setIdNumber(orderRequest.getPayInfo().getCreditCardInfo().getIdNumber());
		    	creditCard.setRemark(orderRequest.getPayInfo().getCreditCardInfo().getRemark());
		    	creditCard.setValidity(orderRequest.getPayInfo().getCreditCardInfo().getValidity());
		    	
		    	ctripCreditCardDao.save(creditCard);
		    	
		   orderInfo.setPayInfoId(creditCard.getId());
		   
	    	
		   orderInfoDao.save(orderInfo);		//保存基本机票订单信息
		   
		   
		   /***********************机票订单信息******************************/
		   FltOrderFlight orderFlight = new FltOrderFlight();
		   int orderFlightCount = orderFlightDao.findAll().size();
		   orderFlight.setId((orderFlightCount+1));
		   orderFlight.setOrderId(orderInfo.getId());
		   orderFlight.setCreateBy(1L);
		   orderFlight.setCreateTime(new Date());
		   orderFlight.setUpdateTime(new Date());
		   
			   OrderFlightInfo flightInfo = new OrderFlightInfo();
			   int flightInfoCount = orderFlightInfoDao.findAll().size();
			   flightInfo.setId(flightInfoCount+1);
			   FltOrderFlightInfoRequestType  fltInfoRequest = orderRequest.getFlightInfoList().getFlightInfoRequest();
			   flightInfo.setAirlineCode(fltInfoRequest.getAirlineCode());
			   flightInfo.setAllowCPType(fltInfoRequest.getAllowCPType());
			   flightInfo.setaPortCode(fltInfoRequest.getAPortCode());
			   flightInfo.setArrivalTime(fltInfoRequest.getArrivalTime());
			   flightInfo.setArriveCityID(fltInfoRequest.getArriveCityID());
			   flightInfo.setCanpost(fltInfoRequest.getCanpost());
			   flightInfo.setCanSeparateSale(fltInfoRequest.getCanSeparateSale());
			   flightInfo.setClazz(fltInfoRequest.getClazz());
			   flightInfo.setCraftType(fltInfoRequest.getCraftType());
			   flightInfo.setDepartCityID(fltInfoRequest.getDepartCityID());
			   flightInfo.setdPortCode(fltInfoRequest.getDPortCode());
			   flightInfo.setEndNote(fltInfoRequest.getEndNote());
			   flightInfo.setFlight(fltInfoRequest.getFlight());
			   flightInfo.setInventoryType(fltInfoRequest.getInventoryType());
			   flightInfo.setNeedAppl(fltInfoRequest.getNeedAppl());
			   flightInfo.setNonEnd(fltInfoRequest.getNonEnd());
			   flightInfo.setNonRef(fltInfoRequest.getNonRef());
			   flightInfo.setNonRer(fltInfoRequest.getNonRer());
			   flightInfo.setOilFee(fltInfoRequest.getOilFee());
			   flightInfo.setOnlyowncity(fltInfoRequest.getOnlyowncity());
			   flightInfo.setPrice(fltInfoRequest.getPrice());
			   flightInfo.setPriceType(fltInfoRequest.getPriceType());
			   flightInfo.setProductSource(fltInfoRequest.getProductSource());
			   flightInfo.setProductType(fltInfoRequest.getProductType());
			   flightInfo.setQuantity(fltInfoRequest.getQuantity());
			   flightInfo.setRate(fltInfoRequest.getRate());
			   flightInfo.setRecommend(fltInfoRequest.getRecommend());
			   flightInfo.setRefNote(fltInfoRequest.getRefNote());
			   flightInfo.setRefundFeeFormulaID(fltInfoRequest.getRefundFeeFormulaID());
			   flightInfo.setRemark(fltInfoRequest.getRemark());
			   flightInfo.setRerNote(fltInfoRequest.getRerNote());
			   flightInfo.setRouteIndex(fltInfoRequest.getRouteIndex());
			   flightInfo.setSubClass(fltInfoRequest.getSubClass());
			   flightInfo.setTakeOffTime(fltInfoRequest.getTakeOffTime());
			   flightInfo.setTax(fltInfoRequest.getTax());
			   flightInfo.setTicketType(fltInfoRequest.getTicketType());
			   flightInfo.setUpGrade(fltInfoRequest.getUpGrade());
			   orderFlightInfoDao.save(flightInfo);
		   orderFlight.setFlightId(flightInfo.getId());
		   orderFlightDao.save(orderFlight);
//	    	fltSaveOrderRequest.get
	    	
	    }
	
	
}
