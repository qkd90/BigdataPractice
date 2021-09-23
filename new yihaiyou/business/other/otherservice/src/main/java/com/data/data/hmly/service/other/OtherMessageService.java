package com.data.data.hmly.service.other;

import com.data.data.hmly.service.common.MsgTemplateService;
import com.data.data.hmly.service.common.entity.MsgTemplate;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.other.dao.OtherMessageDao;
import com.data.data.hmly.service.other.entity.OtherMessage;
import com.data.data.hmly.service.other.enums.MsgType;
import com.data.data.hmly.util.MsgTemplateUtil;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OtherMessageService {
	@Resource
	private OtherMessageDao otherMessageDao;
    @Resource
    private MsgTemplateService msgTemplateService;

    /**
     * 商户后台系统消息发送
     * @param data
     * @param companyUnitId
     * @param templateKey
     */
    public void sendSiteMsg(Map<String, Object> data, Long companyUnitId, String templateKey) {
        // find msg template
        MsgTemplate msgTemplate = msgTemplateService.findByKey(templateKey);
        // construct msg content
        String msgContent = MsgTemplateUtil.createContent(data, msgTemplate.getContent());
        OtherMessage otherMessage = new OtherMessage();
        otherMessage.setContent(msgContent);
        otherMessage.setCompanyUnitId(companyUnitId);
        otherMessage.setReadFlag(false);
        otherMessage.setDeleteFlag(false);
        otherMessage.setMsgType(MsgType.notify);
        otherMessage.setCreateTime(new Date());
        this.doCreateOtherMessage(otherMessage);
    }
	
	/**
	 * 创建消息
	 * @author caiys
	 * @date 2015年12月24日 上午9:25:44
	 * @param otherMessage
	 */
	public void doCreateOtherMessage(OtherMessage otherMessage) {
		otherMessageDao.save(otherMessage);
	}
	
	/**
	 * 读取消息，如果是第一次读取设置为已读取
	 * @author caiys
	 * @date 2015年12月24日 上午9:25:44
	 */
	public void doReadMessage(Long id, Long userId) {
		OtherMessage otherMessage = otherMessageDao.load(Long.valueOf(id));
		if (userId.equals(otherMessage.getToUser().getId())) {	// 判断是否是登录人操作
			if (otherMessage.getReadFlag() == null || !otherMessage.getReadFlag()) {
				otherMessage.setReadFlag(true);
				otherMessage.setReadTime(new Date());
				otherMessageDao.save(otherMessage);
			}
		}
	}
	
	/**
	 * 根据标识清除消息
	 * @author caiys
	 * @date 2015年12月23日 下午3:33:48
	 * @param ids
	 */
	public void doClearMessageBy(String ids, Long userId) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				OtherMessage otherMessage = otherMessageDao.load(Long.valueOf(id));
				if (userId.equals(otherMessage.getToUser().getId())) {	// 判断是否是登录人操作
					otherMessage.setDeleteFlag(true);
					otherMessageDao.save(otherMessage);
				}
			}
		}
	}
	
	/**
	 * 批量清除消息
	 * @author caiys
	 * @date 2015年12月24日 上午9:37:11
	 * @param msgType
	 * @param userId
	 */
	public void doClearMessageBy(MsgType msgType, Long userId) {
		otherMessageDao.clearMessageBy(msgType, userId);
	}
	
	/**
	 * 批量设为已读，未读
	 * @author caiys
	 * @date 2015年12月24日 上午9:42:46
	 * @param ids
	 */
	public void doBatchSetMessage(String ids, boolean readFlag, Long userId) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				OtherMessage otherMessage = otherMessageDao.load(Long.valueOf(id));
				if (userId.equals(otherMessage.getToUser().getId())) {	// 判断是否是登录人操作
					otherMessage.setReadFlag(readFlag);
					otherMessage.setReadTime(new Date());
					otherMessageDao.save(otherMessage);
				}
			}
		}
	}
	
	/**
	 * 查询列表
	 * @author caiys
	 * @date 2015年12月22日 下午2:04:40
	 * @param otherMessage
	 * @param page
	 * @return
	 */
	public List<OtherMessage> findOtherMessageList(OtherMessage otherMessage, Page page) {
//		return otherMessageDao.findOtherMessageList(otherMessage, page);
		return otherMessageDao.findMessageAndObject(otherMessage, page);
	} 

	/**
	 * 获取未读消息数
	 */
	public Long noReadOtherMessageCount(Member user) {
		OtherMessage otherMessage = new OtherMessage();
		otherMessage.setDeleteFlag(false);
		otherMessage.setToUser(user);
		return otherMessageDao.countOtherMessage(otherMessage);
	}
}
