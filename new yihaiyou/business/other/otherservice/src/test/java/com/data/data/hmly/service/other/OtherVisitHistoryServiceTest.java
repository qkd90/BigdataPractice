package com.data.data.hmly.service.other;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.framework.hibernate.util.Page;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherVisitHistoryServiceTest {
	@Resource
	OtherVisitHistoryService otherVisitHistoryService;
	static String cookieId = UUID.randomUUID().toString();
	static Long userId = 1L;
	static ProductType resType = ProductType.scenic;
	static Long[] resObjectIds = new Long[]{1270L, 1271L};
	static Long id;
	
	
	/**
	 * 新增浏览历史
	 * @author caiys
	 * @date 2015年12月24日 下午4:26:26
	 */
	@Test
	public void aAddOtherVisitHistory() {
		for (Long id : resObjectIds) {
			OtherVisitHistory otherVisitHistory = new OtherVisitHistory();
			otherVisitHistory.setResType(resType);
//			otherVisitHistory.setPath(uri);
			otherVisitHistory.setResObject("id");
			otherVisitHistory.setResObjectId(Long.valueOf(id));
//			otherVisitHistory.setVisitIp(ip);
			otherVisitHistory.setCookieId(cookieId);
			otherVisitHistory.setUserId(userId);
			otherVisitHistory.setCreateTime(new Date());
			otherVisitHistory.setDeleteFlag(false);
			otherVisitHistoryService.doModifyOtherVisitHistory(otherVisitHistory);
		}
	}
	
	/**
	 * 更新浏览历史
	 * @author caiys
	 * @date 2015年12月24日 下午4:26:53
	 */
	@Test
	public void bModifyOtherVisitHistory() {
		OtherVisitHistory otherVisitHistory = new OtherVisitHistory();
		otherVisitHistory.setResType(resType);
//		otherVisitHistory.setPath(uri);
		otherVisitHistory.setResObject("id");
		otherVisitHistory.setResObjectId(Long.valueOf(resObjectIds[0]));
//		otherVisitHistory.setVisitIp(ip);
		otherVisitHistory.setCookieId(cookieId);
		otherVisitHistory.setUserId(userId);
		otherVisitHistory.setCreateTime(new Date());
		otherVisitHistory.setDeleteFlag(false);
		otherVisitHistoryService.doModifyOtherVisitHistory(otherVisitHistory);
		id = otherVisitHistory.getId();
	}
	
	/**
	 * 查询前N条
	 * @author caiys
	 * @date 2015年12月24日 下午4:02:23
	 * @throws Exception
	 */
	@Test
	public void cFindOtherVisitHistoryTop() {
		OtherVisitHistory ovh = new OtherVisitHistory();
		ovh.setResType(resType);
//		ovh.setTitle("海洋");
		ovh.setCookieId(cookieId);
		ovh.setUserId(null);
		ovh.setDeleteFlag(false);
		List<OtherVisitHistory> otherVisitHistorys = otherVisitHistoryService.findOtherVisitHistoryTop(ovh, 10);
		Assert.assertEquals(2, otherVisitHistorys.size());
	}
	
	/**
	 * 分页查询列表
	 * @author caiys
	 * @date 2015年12月24日 下午4:02:23
	 * @throws Exception
	 */
	@Test
	public void dFindOtherVisitHistoryList() {
		OtherVisitHistory ovh = new OtherVisitHistory();
		ovh.setResType(resType);
//		ovh.setTitle("海洋");
		ovh.setCookieId(cookieId);
		ovh.setUserId(null);
		ovh.setDeleteFlag(false);
		Page pageInfo = new Page(1, 10);
		List<OtherVisitHistory> otherVisitHistorys = otherVisitHistoryService.findOtherVisitHistoryList(ovh, pageInfo);
		Assert.assertEquals(2, otherVisitHistorys.size());
	}
	
	/**
	 * 根据标识清除浏览历史
	 * @author caiys
	 * @date 2015年12月24日 下午5:13:34
	 */
	@Test
	public void eClearHistoryBy() {
		otherVisitHistoryService.doClearHistoryBy(String.valueOf(id), cookieId, userId);
	}
	
	/**
	 * 批量清除浏览历史
	 * @author caiys
	 * @date 2015年12月24日 下午5:13:36
	 */
	@Test
	public void fBathcClearHistoryBy() {
		otherVisitHistoryService.doClearHistoryBy(cookieId, userId, resType);
	}
}
