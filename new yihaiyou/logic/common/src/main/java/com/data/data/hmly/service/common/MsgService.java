package com.data.data.hmly.service.common;

import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.util.GenValidateCode;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 发送手机验证码服务
 * @author dy
 *
 */
@Service
public class MsgService {

	@Resource
	private SendingMsgService  sendingMsgService;
	@Resource
	private ProductValidateCodeService  productValidateCodeService;
	@Resource
	private ProValidCodeService proValidCodeService;
	@Resource
	private PropertiesManager propertiesManager;

	/**
	 * 通过订单编号，获取短信发送的次数
	 * @param orderNo
	 * @return
	 */
	public Integer getCountByOrderNo(String orderNo) {

		return sendingMsgService.getCountByOrderNo(orderNo);

	}
	
	public void addAndSendMsgCode(ProductValidateCode productValidateCode) {
		String code = checkCode(productValidateCode);
		productValidateCode.setCode(code);
		productValidateCode.setUsed(0);
		productValidateCode.setCreateTime(new Date());
		productValidateCodeService.save(productValidateCode);
		String content = "您已成功购买：" + productValidateCode.getProduct().getName() 
				+ "，数量：" + productValidateCode.getOrderCount() 
				+ "张，兑票验证码：" + code 
				+ "。温馨提示：有效期为2015.02.08-02.14，9:00-15:00，兑换当日单行有效。";
		sendSms(productValidateCode.getBuyerMobile(), content);
	}

	/**
	 * 添加并发送订单手机验证码
	 * @param productValidateCode
	 * @param tips
	 */
	public void addSendMsgCode(ProductValidateCode productValidateCode, String content, String tips) {

		if (productValidateCode.getBuyerName() != null) {
			String code = "";
			if (productValidateCode.getCode() == null) {
				code = checkCode(productValidateCode);
				productValidateCode.setCode(code);
				productValidateCode.setUsed(0);
				productValidateCode.setCreateTime(new Date());
				productValidateCode.setUpdateTime(new Date());
				productValidateCodeService.save(productValidateCode);
			} else {
				code = productValidateCode.getCode();
			}
			content = content + "兑票验证码：" + code
					+ tips;
			sendOrderSms(productValidateCode.getTicketNo(), productValidateCode.getBuyerMobile(), content);
		} else {
			sendOrderSms(productValidateCode.getTicketNo(), productValidateCode.getBuyerMobile(), tips);
		}


	}




	/**
	 * 生成订单验证码发送手机短信
	 * @author caiys
	 * @date 2016年1月7日 上午10:12:36
	 * @param phoneNo
	 */
	public void sendOrderSms(String orderNo, String phoneNo, String content) {
		SendingMsg sendingMsg = new SendingMsg();
		sendingMsg.setContext(content);
		sendingMsg.setReceivernum(phoneNo);
		sendingMsg.setStatus(SendStatus.newed);
		sendingMsg.setSendtime(new Date());
		sendingMsg.setOrderNo(orderNo);
//		sendingMsg.setSiteId(siteId);
		sendingMsgService.save(sendingMsg);
	}


	/**
	 * 旧：已经不用了
	 * 验证生成的验证码是否唯一
	 *
	 */
	public String checkCode(ProductValidateCode validateCode) {
		while (true) {
			String code =  GenValidateCode.generate(propertiesManager.getString("MACHINE_NO"));
			validateCode.setCode(code);
			ProductValidateCode productValidateCode = productValidateCodeService.checkVliadateCode(code, validateCode);
			if (productValidateCode == null) {
				return code;
			}
		}
	}

	/**
	 * 新：
	 * 验证生成的验证码是否唯一
	 */
	public String checkCode(ProValidCode validateCode) {
		while (true) {
			String code =  GenValidateCode.generate(propertiesManager.getString("MACHINE_NO"));
			validateCode.setCode(code);
			ProValidCode productValidateCode = proValidCodeService.checkVliadateCode(code, validateCode);
			if (productValidateCode == null) {
				return code;
			}
		}
	}
	
	/**
	 * 发送手机短信
	 * @author caiys
	 * @date 2016年1月7日 上午10:12:36
	 * @param phoneNo
	 */
	private void sendSms(String phoneNo, String content) {
		SendingMsg sendingMsg = new SendingMsg();
		sendingMsg.setContext(content);
		sendingMsg.setReceivernum(phoneNo);
		sendingMsg.setStatus(SendStatus.newed);
        sendingMsg.setSendtime(new Date());
//		sendingMsg.setSiteId(siteId);
		sendingMsgService.save(sendingMsg);
	}

	/**
	 * 发送普通短信
	 * @param mobile
	 * @param noCodeContent
	 */
	public void addSendMsg(String mobile, String noCodeContent) {
		sendSms(mobile, noCodeContent);
	}
}
