package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.MsgHistoryDao;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.SendingMsgHis;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class MsgHistoryService {

	@Resource
	private MsgHistoryDao msgHistoryDao;
	
	public SendingMsgHis findSendingMsgHisById(Long msgId) {
		if (msgId != null) {
			return msgHistoryDao.get(SendingMsgHis.class, msgId);
		}
		return null;
	}
	
	/**
	 * 功能描述：查询短信信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:39:17
	 * @param user
	 * @param page
	 * @return
	 */
	public List<SendingMsgHis> findMsgHisList(SendingMsgHis msgHis, Page page){
		Criteria<SendingMsgHis> c = new Criteria<SendingMsgHis>(SendingMsgHis.class);
//		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		foramtCond(msgHis, c);
//		c.orderBy(Order.asc("seq"));
		c.orderBy("sendtime", "desc");
		return msgHistoryDao.findByCriteria(c, page);
	}
	
	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 * @param user
	 * @param c
	 */
	public void foramtCond(SendingMsgHis msgHis, Criteria<SendingMsgHis> c) {
		if (msgHis != null) {
			if (StringUtils.isNotBlank(msgHis.getReceivernum())) {
				c.like("receivernum", msgHis.getReceivernum());
			}
			if (StringUtils.isNotBlank(msgHis.getContext())) {
				c.like("context", msgHis.getContext());
			}
//			if(DateUtils.)日期函数
		}
	}
	
	@Transactional
	public void insert(SendingMsgHis msgHistory){
		msgHistoryDao.save(msgHistory);
	}
	
	public SendingMsgHis copy(SendingMsg sendingMsg, SendingMsgHis msgHistory){
		msgHistory.setId(sendingMsg.getId());
		msgHistory.setReceivernum(sendingMsg.getReceivernum());
		msgHistory.setSendtime(sendingMsg.getSendtime());
		msgHistory.setStatus(sendingMsg.getStatus());
		msgHistory.setContext(sendingMsg.getContext());
		return msgHistory;
	}
	
}
