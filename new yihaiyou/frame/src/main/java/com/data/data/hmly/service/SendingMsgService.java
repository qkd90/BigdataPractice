package com.data.data.hmly.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.data.hmly.service.dao.SendingMsgDao;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;

@Service
public class SendingMsgService {
	@Resource
	private SendingMsgDao sendingMsgDao;
	private final ExecutorService service = Executors.newCachedThreadPool();
	private final ScheduledExecutorService schdual = Executors.newScheduledThreadPool(1);

	public SendingMsg findSendingMsgById(Long msgId) {
		if (msgId != null) {
			return sendingMsgDao.get(SendingMsg.class, msgId);
		}
		return null;
	}

	/**
	 * 获取短信发送次数
	 * @param orderNo
	 * @return
	 */
	public Integer getCountByOrderNo(String orderNo) {
		Criteria<SendingMsg> c = new Criteria<SendingMsg>(SendingMsg.class);
		c.eq("orderNo", orderNo);
		return sendingMsgDao.findByCriteria(c).size();

	}

	/**
	 * 功能描述：查询短信信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:39:17
	 * @param msg
	 * @param page
	 * @return
	 */
	public List<SendingMsg> findMsgList(SendingMsg msg, Page page) {
		Criteria<SendingMsg> c = new Criteria<SendingMsg>(SendingMsg.class);
		// c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		foramtCond(msg, c);
		// c.orderBy(Order.asc("seq"));
        c.orderBy("sendtime", "desc");
		return sendingMsgDao.findByCriteria(c, page);
	}

	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 * @param msg
	 * @param c
	 */
	public void foramtCond(SendingMsg msg, Criteria<SendingMsg> c) {
		if (msg != null) {
			if (StringUtils.isNotBlank(msg.getReceivernum())) {
				c.like("receivernum", msg.getReceivernum());
			}
			if (StringUtils.isNotBlank(msg.getContext())) {
				c.like("context", msg.getContext());
			}
			// if(DateUtils.)日期函数
		}
	}

	// @PostConstruct
	public void findSendingMsg() {

		Page page = new Page(1, 100);
		Criteria<SendingMsg> criteria = new Criteria<SendingMsg>(SendingMsg.class);
		criteria.eq("status", SendStatus.newed);
		List<SendingMsg> msgs = sendingMsgDao.findByCriteria(criteria, page);
		for (SendingMsg sendingMsg : msgs) {
			// System.out.println(sendingMsg.getContext());
			service.submit(new SendMsgRunnable(sendingMsg));
		}
		// schdual.scheduleAtFixedRate(new Runnable() {
		//
		// @Override
		// public void run() {
		// }
		// }, 10, 10, TimeUnit.SECONDS);
	}

	@Transactional
	public void delete(SendingMsg sendingMsg) {
		// TODO Auto-generated method stub
		sendingMsgDao.delete(sendingMsg);
	}

	public void update(SendingMsg sendingMsg) {
		sendingMsgDao.update(sendingMsg);
	}

	public void save(SendingMsg sendingMsg) {
		sendingMsgDao.save(sendingMsg);
	}

}
