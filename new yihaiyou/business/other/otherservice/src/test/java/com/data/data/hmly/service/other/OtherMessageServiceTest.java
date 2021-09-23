package com.data.data.hmly.service.other;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.other.entity.OtherMessage;
import com.data.data.hmly.service.other.enums.MsgType;
import com.framework.hibernate.util.Page;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherMessageServiceTest {
	@Resource
	OtherMessageService otherMessageService;
	static MsgType msgType = MsgType.notify;
	static Long[] toUserIds = new Long[]{1L, 1L};
	static List<OtherMessage> otherMessages = new ArrayList<OtherMessage>();

	/**
	 * 创建消息
	 * @author caiys
	 * @date 2015年12月24日 下午6:21:21
	 */
	@Test
	public void aCreateOtherMessage() {
		for (int i = 0; i < toUserIds.length; i++) {
			OtherMessage otherMessage = new OtherMessage();
			otherMessage.setMsgType(msgType);
			otherMessage.setTitle("通知");
			otherMessage.setContent("通知内容"+i);
			otherMessage.setFromUser(null);
			Member user = new Member();
			user.setId(toUserIds[i]);
			otherMessage.setToUser(user);
			otherMessage.setCreateTime(new Date());
			otherMessage.setReadFlag(false);
			otherMessage.setDeleteFlag(false);
			otherMessageService.doCreateOtherMessage(otherMessage);
			otherMessages.add(otherMessage);
		}
	}
	
	/**
	 * 根据标识批量设置消息已读
	 * @author caiys
	 * @date 2015年12月24日 下午6:21:38
	 */
	@Test
	public void bBatchReadMessage() {
		otherMessageService.doBatchSetMessage(String.valueOf(otherMessages.get(0).getId()), true, toUserIds[0]);
	}

	/**
	 * 根据标识批量设置消息未读
	 * @author caiys
	 * @date 2015年12月24日 下午6:22:04
	 */
	@Test
	public void cBatchUnReadMessage() {
		otherMessageService.doBatchSetMessage(String.valueOf(otherMessages.get(0).getId()), false, toUserIds[0]);
	}
	
	/**
	 * 读取单条消息
	 * @author caiys
	 * @date 2015年12月24日 下午6:32:36
	 */
	@Test
	public void dReadMessage() {
		otherMessageService.doReadMessage(otherMessages.get(0).getId(), toUserIds[0]);
	}

	/**
	 * 分页查询
	 * @author caiys
	 * @date 2015年12月24日 下午6:22:25
	 */
	@Test
	public void eFindOtherMessageList() {
		OtherMessage om = new OtherMessage();
		om.setMsgType(msgType);
//		om.setTitle(title);
		om.setDeleteFlag(false);
		Member user = new Member();
		user.setId(toUserIds[0]);
		om.setToUser(user);
		Page pageInfo = new Page(1, 10);
		List<OtherMessage> list = otherMessageService.findOtherMessageList(om, pageInfo);
		Assert.assertEquals(otherMessages.size(), list.size());
	}

	/**
	 * 根据标识清除消息
	 * @author caiys
	 * @date 2015年12月24日 下午6:22:46
	 */
	@Test
	public void fClearMessageBy() {
		otherMessageService.doClearMessageBy(String.valueOf(otherMessages.get(0).getId()), toUserIds[0]);
	}

	/**
	 * 批量清除消息
	 * @author caiys
	 * @date 2015年12月24日 下午6:22:48
	 */
	@Test
	public void gBatchClearMessageBy() {
		otherMessageService.doClearMessageBy(msgType, toUserIds[0]);
	}

}
