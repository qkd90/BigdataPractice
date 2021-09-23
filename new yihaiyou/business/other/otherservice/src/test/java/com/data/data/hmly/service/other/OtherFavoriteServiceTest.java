package com.data.data.hmly.service.other;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.framework.hibernate.util.Page;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherFavoriteServiceTest {
	@Resource
	OtherFavoriteService otherFavoriteService;
	static Long userId = 2L;
	static ProductType favoriteType = ProductType.scenic;
	static Long[] favoriteIds = new Long[]{1270L, 1271L};
	static List<OtherFavorite> otherFavorites = new ArrayList<OtherFavorite>();
	
	/**
	 * 添加收藏
	 * @author caiys
	 * @date 2015年12月23日 下午3:27:25
	 */
	@Test
	public void aAddOtherFavorite() {
		for (int i = 0; i < favoriteIds.length; i++) {
			OtherFavorite otherFavorite = new OtherFavorite();
			otherFavorite.setFavoriteType(favoriteType);
			otherFavorite.setTitle("景点"+i);
			otherFavorite.setContent("内容"+i);
			otherFavorite.setImgPath("/static/img"+i);
			otherFavorite.setFavoriteId(favoriteIds[i]);
			otherFavorite.setUserId(userId);
			otherFavorite.setCreateTime(new Date());
			otherFavorite.setDeleteFlag(false);
			otherFavoriteService.doAddOtherFavorite(otherFavorite);
			otherFavorites.add(otherFavorite);
		}
	}

	/**
	 * 检查是否已经收藏过
	 * @author caiys
	 * @date 2015年12月24日 下午6:02:13
	 */
	@Test
	public void bCheckExists() {
		boolean exists = otherFavoriteService.checkExists(favoriteType, favoriteIds[0], userId);
		Assert.assertTrue(exists);
	}

	/**
	 * 分页查询
	 * @author caiys
	 * @date 2015年12月24日 下午6:03:35
	 */
	@Test
	public void dFindOtherFavoriteList() {
		OtherFavorite of = new OtherFavorite();
		of.setFavoriteType(favoriteType);
//		of.setTitle(title);
		of.setDeleteFlag(false);
		of.setUserId(userId);
		Page pageInfo = new Page(1, 10);
		List<OtherFavorite> list = otherFavoriteService.findOtherFavoriteList(of, pageInfo);
		Assert.assertEquals(otherFavorites.size(), list.size());
	}

	/**
	 * 根据标识清除收藏夹
	 * @author caiys
	 * @date 2015年12月24日 下午6:07:05
	 */
	@Test
	public void eClearFavoriteBy() {
		otherFavoriteService.doClearFavoriteBy(String.valueOf(otherFavorites.get(0).getId()), userId);
	}

	/**
	 * 批量清除收藏夹
	 * @author caiys
	 * @date 2015年12月24日 下午6:09:28
	 */
	@Test
	public void fBatchClearFavoriteBy() {
		otherFavoriteService.doClearFavoriteBy(favoriteType, userId, null);
	}

}
