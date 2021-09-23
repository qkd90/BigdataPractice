package com.hmlyinfo.app.soutu.weixin.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.domain.WxToken;
import com.hmlyinfo.app.soutu.weixin.domain.Qrcode;
import com.hmlyinfo.app.soutu.weixin.mapper.QrcodeMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.HttpClientUtils;

@Service
public class QrcodeService extends BaseService<Qrcode, Long>{

	@Autowired
	private QrcodeMapper<Qrcode> mapper;
	@Autowired
	private WeiXinService weiXinService;
	
	private static final Log logs = LogFactory.getLog(QrcodeService.class);
	
	/**
	 * 生成带参数二维码
	 * @param actioName
	 * @param sceneId
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public String createQrcode(String actioName, String sceneId) throws KeyManagementException, NoSuchAlgorithmException 
	{
		WxToken token = weiXinService.getToken();
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token.getAccessToken();

		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"action_name\":\"").append(actioName).append("\",\"action_info\":{\"scene\":{\"scene_id\":")
		.append(sceneId).append("}}}");

		String resultStr = HttpClientUtils.postHttpsJSON(tokenUrl, buffer.toString());
		logs.info(resultStr);

		return resultStr;
	}
	
	@Override
	public BaseMapper<Qrcode> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
}
