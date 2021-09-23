package com.data.data.hmly.service.other;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.other.dao.OtherVisitHistoryDao;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OtherVisitHistoryService {
	@Resource
	private OtherVisitHistoryDao otherVisitHistoryDao;

	/**
	 * 更新浏览历史，先更新可能存在的相同浏览历史为无效，然后新增浏览历史记录
	 * @author caiys
	 * @date 2015年12月22日 上午11:51:33
	 * @param otherVisitHistory
	 */
	public void doModifyOtherVisitHistory(OtherVisitHistory otherVisitHistory) {
		otherVisitHistoryDao.clearHistoryBy(otherVisitHistory.getCookieId(), otherVisitHistory.getUserId(), otherVisitHistory.getResType(), otherVisitHistory.getResObjectId());
		// 根据类型设置冗余信息title、introduction、imgPath
		/*if (otherVisitHistory.getResType() == ProductType.scenic) {
			
		} */
		otherVisitHistoryDao.save(otherVisitHistory);
	}
	
	/**
	 * 根据标识清除浏览历史
	 * @author caiys
	 * @date 2015年12月22日 上午11:51:44
	 * @param ids	多个逗号分隔
	 */
	public void doClearHistoryBy(String ids, String cookieId, Long userId) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				OtherVisitHistory otherVisitHistory = otherVisitHistoryDao.load(Long.valueOf(id));
				// 是否当前用户判断
				if (otherVisitHistory.getCookieId().equals(cookieId) || (otherVisitHistory.getUserId() != null && otherVisitHistory.getUserId().equals(userId))) {
					otherVisitHistory.setDeleteFlag(true);
					otherVisitHistoryDao.save(otherVisitHistory);
				}
			}
		}
	}
	
	/**
	 * 批量清除个人浏览历史
	 * @author caiys
	 * @date 2015年12月22日 上午11:52:07
	 * @param cookieId
	 */
	public void doClearHistoryBy(String cookieId, Long userId, ProductType resType) {
		otherVisitHistoryDao.clearHistoryBy(cookieId, userId, resType, null);
	}
	
	/**
	 * 查询列表
	 * @author caiys
	 * @date 2015年12月22日 下午2:04:40
	 * @param otherVisitHistory
	 * @param page
	 * @return
	 */
	public List<OtherVisitHistory> findOtherVisitHistoryList(OtherVisitHistory otherVisitHistory, Page page) {
		return otherVisitHistoryDao.findVisitHistoryResObject(otherVisitHistory, otherVisitHistory.getResType(), page);
	}
	
	/**
	 * 查询前N条
	 * @author caiys
	 * @date 2015年12月22日 下午2:06:29
	 * @param otherVisitHistory
	 * @param limit
	 * @return
	 */
	public List<OtherVisitHistory> findOtherVisitHistoryTop(OtherVisitHistory otherVisitHistory, int limit) {
		Page page = new Page(1, limit);
		return otherVisitHistoryDao.findVisitHistoryResObject(otherVisitHistory, otherVisitHistory.getResType(), page);
	}

	/**
	 * 统计访问次数最多的前N条，目前只考虑景点
	 * @param otherVisitHistory
	 * @param limit
	 * @return
	 */
	public List<OtherVisitHistory> findVisitScenicCountTop(OtherVisitHistory otherVisitHistory, Integer limit) {
		return otherVisitHistoryDao.findVisitScenicCountTop(otherVisitHistory, limit);
	}

	public List<OtherVisitHistory> findVisitLineCountTop(OtherVisitHistory otherVisitHistory, Integer limit) {
		return otherVisitHistoryDao.findVisitLineCountTop(otherVisitHistory, limit);
	}
}
